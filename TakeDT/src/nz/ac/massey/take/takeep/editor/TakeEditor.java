package nz.ac.massey.take.takeep.editor;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.tools.JavaCompiler;

import nz.ac.massey.take.takeep.actionsSets.compileActions.TakeCompileToClasses;
import nz.ac.massey.take.takeep.actionsSets.compileActions.TakeCompileToInterfaces;
import nz.ac.massey.take.takeep.actionsSets.panels.TakeCompileWizardPanel;
import nz.ac.massey.take.takeep.actionsSets.takeSourceActions.TakeCompilerAnnotations;
import nz.ac.massey.take.takeep.actionsSets.verifyActions.TakeRunVerifiers;
import nz.ac.massey.take.takeep.editor.TakeSourceViewerConfiguration.TAKE_TOKENS;
import nz.ac.massey.take.takeep.editor.tokens.TakePartitionScanner.TAKE_PARTITIONS;
import nz.ac.massey.take.takeep.outline.TakeOutline;
import nz.org.take.nscript.Parser;
import nz.org.take.nscript.ScriptException;

import org.apache.tools.ant.filters.StringInputStream;
import org.apache.tools.ant.util.ReaderInputStream;
import org.eclipse.core.internal.resources.Marker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.internal.compiler.tool.EclipseCompiler;
import org.eclipse.jdt.internal.core.ClasspathEntry;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.SubMenuManager;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.AnnotationRulerColumn;
import org.eclipse.jface.text.source.CompositeRuler;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.text.source.IVerticalRuler;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.internal.ActionSetMenuManager;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.texteditor.AbstractDecoratedTextEditorPreferenceConstants;
import org.eclipse.ui.texteditor.IElementStateListener;
import org.eclipse.ui.texteditor.ITextEditorActionConstants;
import org.eclipse.ui.texteditor.ResourceMarkerAnnotationModel;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

import com.sun.org.apache.xerces.internal.dom.DocumentImpl;

public class TakeEditor extends TextEditor{

	private TakeOutline fOutlinePage = null;

	@Override
	public Object getAdapter(Class adapter) {
		if(adapter.equals(IContentOutlinePage.class))
		{
			if (fOutlinePage == null) {
				fOutlinePage= new TakeOutline(this);
			}
			return fOutlinePage;	
		}
		return super.getAdapter(adapter);
	}





	public TakeEditor() {
		super();
		DesignManager cm = new DesignManager();
		cm.addColor(TAKE_PARTITIONS.TAKE_COMMENT.name(), new RGB(63,127,95));

		cm.addColor(TAKE_PARTITIONS.TAKE_GLOBAL_ANNOTATION.name(), new RGB(128,128,128));
		cm.addStyle(TAKE_PARTITIONS.TAKE_GLOBAL_ANNOTATION.name(), SWT.ITALIC);


		cm.addColor(TAKE_TOKENS.TAKE_STRING_LITERAL.name(), new RGB(0,0,192));

		cm.addColor(TAKE_TOKENS.TAKE_KEYWORD.name(), new RGB(127,0,85));
		cm.addStyle(TAKE_TOKENS.TAKE_KEYWORD.name(), SWT.BOLD);

		cm.addColor(TAKE_PARTITIONS.TAKE_LOCAL_ANNOTATION.name(), new RGB(0,0,192));

		this.setContentDescription("Take Editor");
		this.setSourceViewerConfiguration(new TakeSourceViewerConfiguration(cm));
		this.setDocumentProvider(new TakeDocumentProvider());

		getPreferenceStore().setValue(AbstractDecoratedTextEditorPreferenceConstants.EDITOR_LINE_NUMBER_RULER,true);

		this.getDocumentProvider().addElementStateListener(new IElementStateListener(){

			@Override
			public void elementContentAboutToBeReplaced(Object element) {
				// TODO Auto-generated method stub

			}

			@Override
			public void elementContentReplaced(Object element) {

			}

			@Override
			public void elementDeleted(Object element) {
				// TODO Auto-generated method stub

			}

			@Override
			public void elementDirtyStateChanged(Object element, boolean isDirty) {
				if(!isDirty)
				{
					runVerifier();
				}

			}

			@Override
			public void elementMoved(Object originalElement, Object movedElement) {
				// TODO Auto-generated method stub

			}});

	}

	private LinkedList<Annotation> anno = new LinkedList<Annotation>();







	public static URLClassLoader getProjectClassLoader(IJavaProject project) {
		List<URL> pathElements = getProjectClassPathURLs(project);
		URL urlPaths[] = (URL[]) pathElements.toArray(new URL[pathElements.size()]);
		return new URLClassLoader(urlPaths, Thread.currentThread().getContextClassLoader());
	}

	private static URL getRawLocationURL(IPath simplePath)
	throws MalformedURLException {
		File file = getRawLocationFile(simplePath);
		return file.toURI().toURL();
	}

	private static File getRawLocationFile(IPath simplePath) {
		org.eclipse.core.resources.IResource resource = ResourcesPlugin.getWorkspace().getRoot().findMember(simplePath);
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

				for ( int i = 0; i < paths.length; i++ ) {
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
			for ( int i = 0; i < names.length; i++ ) {
				String projectName = names[i];
				IProject reqProject = project.getProject().getWorkspace()
				.getRoot().getProject(projectName);
				if (reqProject != null) {
					IJavaProject reqJavaProject = JavaCore.create(reqProject);
					pathElements.addAll(getProjectClassPathURLs(reqJavaProject));
				}
			}
		} catch (JavaModelException e) {
			//DroolsIDEPlugin.log(e);
		} catch (MalformedURLException e) {
			//DroolsIDEPlugin.log(e);
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

	public void runVerifier()
	{
		try {
			IWorkbenchPage workbench = TakeCompileWizardPanel.getWorkbench();
			IProject project = TakeCompileWizardPanel.getProjectFromWorkbench(workbench);
			IJavaProject create = JavaCore.create(project);
			LinkedList<URL> urls = new LinkedList<URL>();
			ClassLoader cl = getProjectClassLoader(create);

			ResourceMarkerAnnotationModel annotationModel = (ResourceMarkerAnnotationModel)getDocumentProvider().getAnnotationModel(getEditorInput());
			for(Annotation a : anno)
			{
				annotationModel.removeAnnotation(a);

			}
			anno.clear();

			Parser p = new Parser();

			InputStream script;
			script = ((FileEditorInput)this.getEditorInput()).getFile().getContents();
			script = new StringInputStream(this.getDocumentProvider().getDocument(this.getEditorInput()).get());


			//p.parse(new InputStreamReader(script));
			List<ScriptException> check = p.check(new InputStreamReader(script));
			for(ScriptException se : check)
			{

				Annotation annotation = new Annotation("org.eclipse.ui.workbench.texteditor.error",false,"");

				annotation.setText(se.getMessage());
				anno.add(annotation);
				IRegion lineInformation = this.getSourceViewer().getDocument().getLineInformation(se.getLine()-1);
				annotationModel.addAnnotation(annotation, new Position(lineInformation.getOffset(),lineInformation.getLength()));

			}
//			System.out.println("LOL");
//			IAnnotationModel annotationModel = getDocumentProvider().getAnnotationModel(getEditorInput());


//			Annotation annotation = new Annotation("org.eclipse.ui.workbench.texteditor.error",false,"");
//			anno.add(annotation);
//			annotationModel.addAnnotation(annotation, new Position(ty.getOffset(),ty.getLength()));

		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	@Override
	protected IVerticalRuler createVerticalRuler() {
		CompositeRuler ruler= new CompositeRuler();
		ruler.addDecorator(0, new AnnotationRulerColumn(VERTICAL_RULER_WIDTH));
		return ruler;
	}





	@Override
	protected void editorSaved() {
		// TODO Auto-generated method stub
		super.editorSaved();


		if(fOutlinePage != null) fOutlinePage.update();
	}



	@Override
	protected void createActions() {
		// TODO Auto-generated method stub
		super.createActions();
//		this.setAction(TakeCompileToClasses.class.toString(),new TakeCompileToClasses());
//		this.setAction(TakeCompileToInterfaces.class.toString(),new TakeCompileToInterfaces());
//		this.setAction(TakeRunVerifiers.class.toString(),new TakeRunVerifiers());


	}



	@Override
	protected void editorContextMenuAboutToShow(IMenuManager menu) {
		// TODO Auto-generated method stub
		super.editorContextMenuAboutToShow(menu);

		this.addGroup(menu, ITextEditorActionConstants.GROUP_EDIT, "Take");
//		this.addAction(menu, "Take", TakeCompileToClasses.class.toString());
//		this.addAction(menu, "Take", TakeCompileToInterfaces.class.toString());
//		this.addAction(menu, "Take", TakeRunVerifiers.class.toString());


		MenuManager compileMenu = new MenuManager("Take Compile");
		compileMenu.add(new TakeCompileToClasses());
		compileMenu.add(new TakeCompileToInterfaces());
		menu.appendToGroup("Take", compileMenu);


		MenuManager verifyMenu = new MenuManager("Take Verify");
		verifyMenu.add(new TakeRunVerifiers());
		menu.appendToGroup("Take", verifyMenu);

		MenuManager takeSource = new MenuManager("Take Source");
		takeSource.add(new TakeCompilerAnnotations());
		menu.appendToGroup("Take", takeSource);

	}






}
