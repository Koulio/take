package nz.ac.massey.take.takeep.editor;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

import nz.ac.massey.take.takeep.actionsSets.compileActions.TakeCompileToClasses;
import nz.ac.massey.take.takeep.actionsSets.compileActions.TakeCompileToInterfaces;
import nz.ac.massey.take.takeep.actionsSets.takeSourceActions.TakeCompilerAnnotations;
import nz.ac.massey.take.takeep.actionsSets.verifyActions.TakeRunVerifiers;
import nz.ac.massey.take.takeep.editor.TakeSourceViewerConfiguration.TAKE_TOKENS;
import nz.ac.massey.take.takeep.editor.tokens.TakePartitionScanner.TAKE_PARTITIONS;
import nz.ac.massey.take.takeep.outline.TakeOutline;
import nz.org.take.nscript.Parser;
import nz.org.take.nscript.ScriptException;

import org.apache.tools.ant.filters.StringInputStream;
import org.apache.tools.ant.util.ReaderInputStream;
import org.eclipse.core.runtime.CoreException;
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
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.internal.ActionSetMenuManager;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.texteditor.AbstractDecoratedTextEditorPreferenceConstants;
import org.eclipse.ui.texteditor.IElementStateListener;
import org.eclipse.ui.texteditor.ITextEditorActionConstants;
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


	public void runVerifier()
	{
		try {
			IAnnotationModel annotationModel = getDocumentProvider().getAnnotationModel(getEditorInput());
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
