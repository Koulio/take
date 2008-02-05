package nz.ac.massey.take.takeep.editor;

import java.io.File;
import java.io.Reader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import nz.ac.massey.take.takeep.actionsSets.compileActions.TakeCompileToClasses;
import nz.ac.massey.take.takeep.actionsSets.compileActions.TakeCompileToInterfaces;
import nz.ac.massey.take.takeep.actionsSets.panels.TakeCompileWizardPanel;
import nz.ac.massey.take.takeep.actionsSets.verifyActions.TakeRunVerifiers;
import nz.ac.massey.take.takeep.editor.TakeSourceViewerConfiguration.TAKE_TOKENS;
import nz.ac.massey.take.takeep.editor.tokens.TakePartitionScanner.TAKE_PARTITIONS;
import nz.ac.massey.take.takeep.outline.TakeOutline;
import nz.org.take.nscript.Parser;
import nz.org.take.nscript.ScriptException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.AnnotationRulerColumn;
import org.eclipse.jface.text.source.CompositeRuler;
import org.eclipse.jface.text.source.IVerticalRuler;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.texteditor.AbstractDecoratedTextEditorPreferenceConstants;
import org.eclipse.ui.texteditor.IElementStateListener;
import org.eclipse.ui.texteditor.ITextEditorActionConstants;
import org.eclipse.ui.texteditor.ResourceMarkerAnnotationModel;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

public class TakeEditor extends TextEditor {

	private TakeOutline fOutlinePage = null;

	@Override
	public Object getAdapter(Class adapter) {
		if (adapter.equals(IContentOutlinePage.class)) {
			if (this.fOutlinePage == null) {
				this.fOutlinePage = new TakeOutline(this);
			}
			return this.fOutlinePage;
		}
		return super.getAdapter(adapter);
	}

	public TakeEditor() {
		super();
		DesignManager cm = new DesignManager();
		cm.addColor(TAKE_PARTITIONS.TAKE_COMMENT.name(), new RGB(63, 127, 95));

		cm.addColor(TAKE_PARTITIONS.TAKE_GLOBAL_ANNOTATION.name(), new RGB(128,128, 128));
		cm.addStyle(TAKE_PARTITIONS.TAKE_GLOBAL_ANNOTATION.name(), SWT.ITALIC);

		cm.addColor(TAKE_TOKENS.TAKE_STRING_LITERAL.name(), new RGB(0, 0, 192));

		cm.addColor(TAKE_TOKENS.TAKE_KEYWORD.name(), new RGB(127, 0, 85));
		cm.addStyle(TAKE_TOKENS.TAKE_KEYWORD.name(), SWT.BOLD);

		cm.addColor(TAKE_PARTITIONS.TAKE_LOCAL_ANNOTATION.name(), new RGB(0, 0,192));

		this.setContentDescription("Take Editor");
		this.setSourceViewerConfiguration(new TakeSourceViewerConfiguration(
				this,cm));
		this.setDocumentProvider(new TakeDocumentProvider());

		getPreferenceStore()
		.setValue(
				AbstractDecoratedTextEditorPreferenceConstants.EDITOR_LINE_NUMBER_RULER,
				true);

		this.getDocumentProvider().addElementStateListener(
				new IElementStateListener() {

					@Override
					public void elementContentAboutToBeReplaced(Object element) {
					}

					@Override
					public void elementContentReplaced(Object element) {
					}

					@Override
					public void elementDeleted(Object element) {
					}

					@Override
					public void elementDirtyStateChanged(Object element,
							boolean isDirty) {
						if (!isDirty) {
							runVerifier();
						}
					}

					@Override
					public void elementMoved(Object originalElement,
							Object movedElement) {

					}
				});



	}

	public static URLClassLoader getProjectClassLoader(IJavaProject project) {
		List<URL> pathElements = getProjectClassPathURLs(project);
		URL urlPaths[] = pathElements.toArray(new URL[pathElements.size()]);

		return new URLClassLoader(urlPaths);
	}

	private static URL getRawLocationURL(IPath simplePath)
	throws MalformedURLException {
		File file = getRawLocationFile(simplePath);
		return file.toURI().toURL();
	}

	private static File getRawLocationFile(IPath simplePath) {
		org.eclipse.core.resources.IResource resource = ResourcesPlugin
		.getWorkspace().getRoot().findMember(simplePath);
		File file = null;
		if (resource != null) {
			file = resource.getRawLocation().toFile();
		} else {
			file = simplePath.toFile();
		}
		return file;
	}

	public static List<URL> getProjectClassPathURLs(IJavaProject project) {
		List<URL> pathElements = new LinkedList<URL>();
		try {
			IClasspathEntry[] paths = project.getResolvedClasspath(true);
			if (paths != null) {

				for (int i = 0; i < paths.length; i++) {
					IClasspathEntry path = paths[i];
					if (path.getEntryKind() == IClasspathEntry.CPE_LIBRARY) {
						URL url = getRawLocationURL(path.getPath());
						pathElements.add(url);
					}
				}
			}
			IPath location = getProjectLocation(project.getProject());
			IPath outputPath = location.append(project.getOutputLocation()
					.removeFirstSegments(1));
			pathElements.add(outputPath.toFile().toURL());

			// also add classpath of required projects
			String[] names = project.getRequiredProjectNames();
			for (int i = 0; i < names.length; i++) {
				String projectName = names[i];
				IProject reqProject = project.getProject().getWorkspace()
				.getRoot().getProject(projectName);
				if (reqProject != null) {
					IJavaProject reqJavaProject = JavaCore.create(reqProject);
					pathElements
					.addAll(getProjectClassPathURLs(reqJavaProject));
				}
			}
		} catch (JavaModelException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return pathElements;
	}

	public static IPath getProjectLocation(IProject project) {
		if (project.getRawLocation() == null) {
			return project.getLocation();
		} else {
			return project.getRawLocation();
		}
	}

	private HashMap<Annotation, IMarker> anno = new HashMap<Annotation, IMarker>();

	public void runVerifier() {


		IWorkbenchPage workbench = TakeCompileWizardPanel.getWorkbench();
		IProject project = TakeCompileWizardPanel
		.getProjectFromWorkbench(workbench);
		ClassLoader cl = getProjectClassLoader(JavaCore.create(project));

		final ResourceMarkerAnnotationModel annotationModel = (ResourceMarkerAnnotationModel) getDocumentProvider()
		.getAnnotationModel(getEditorInput());

		for (Annotation a : this.anno.keySet()) {
			annotationModel.removeAnnotation(a);
		}
		final IFile file = ((FileEditorInput) this.getEditorInput())
		.getFile();
		try {
			file.deleteMarkers(IMarker.PROBLEM, true, IResource.DEPTH_INFINITE);
		} catch (CoreException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		}
		this.anno.clear();

		final Parser p = new Parser();
		p.setClassLoader(cl);
		final Reader script;
		script = new StringReader(this.getDocumentProvider() .getDocument(this.getEditorInput()).get());

		try {
			List<ScriptException> check = p.check(script);

			for (ScriptException se : check) {

				IMarker marker;

				marker = file.createMarker(IMarker.PROBLEM);

				marker.setAttribute(IMarker.MESSAGE, se.getMessage());
				marker.setAttribute(IMarker.LOCATION, se.getLine());

				Annotation annotation = new Annotation(
						"org.eclipse.ui.workbench.texteditor.error", false, se
						.getMessage());
				anno.put(annotation, marker);
				IRegion lineInformation = getSourceViewer().getDocument()
				.getLineInformation(se.getLine() - 1);
				annotationModel.addAnnotation(annotation, new Position(
						lineInformation.getOffset(), lineInformation
						.getLength()));

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}





	@Override
	protected IVerticalRuler createVerticalRuler() {
		CompositeRuler ruler = new CompositeRuler();
		ruler.addDecorator(0, new AnnotationRulerColumn(VERTICAL_RULER_WIDTH));
		return ruler;
	}

	@Override
	protected void editorSaved() {
		// TODO Auto-generated method stub
		super.editorSaved();

		if (this.fOutlinePage != null)
			this.fOutlinePage.update();
	}

	@Override
	protected void createActions() {
		// TODO Auto-generated method stub
		super.createActions();

	}

	@Override
	protected void editorContextMenuAboutToShow(IMenuManager menu) {
		// TODO Auto-generated method stub
		super.editorContextMenuAboutToShow(menu);

		this.addGroup(menu, ITextEditorActionConstants.GROUP_EDIT, "Take");

		MenuManager compileMenu = new MenuManager("Take Compile");
		compileMenu.add(new TakeCompileToClasses());
		compileMenu.add(new TakeCompileToInterfaces());
		menu.appendToGroup("Take", compileMenu);

		MenuManager verifyMenu = new MenuManager("Take Verify");
		verifyMenu.add(new TakeRunVerifiers());
		menu.appendToGroup("Take", verifyMenu);

	}


	@Override
	protected void doSetInput(final IEditorInput input) throws CoreException {
		super.doSetInput(input);

		this.getDocumentProvider ().getDocument(input).addDocumentListener(new IDocumentListener(){

			private long lastRun = 0;
			@Override
			public void documentAboutToBeChanged(DocumentEvent event) {

			}

			@Override
			public void documentChanged(DocumentEvent event) {
				long i = System.currentTimeMillis();
				if(i > lastRun+1000)
				{
					lastRun = i;
					runVerifier();
				}
			}});
	}

}
