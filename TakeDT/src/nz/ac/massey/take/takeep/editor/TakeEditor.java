package nz.ac.massey.take.takeep.editor;

import java.util.HashMap;
import java.util.LinkedList;

import nz.ac.massey.take.takeep.editor.TakeSourceViewerConfiguration.TAKE_TOKENS;
import nz.ac.massey.take.takeep.editor.tokens.TakePartitionScanner;
import nz.ac.massey.take.takeep.editor.tokens.TakePartitionScanner.TAKE_PARTITIONS;
import nz.ac.massey.take.takeep.outline.TakeOutline;

import org.eclipse.core.internal.resources.Marker;
import org.eclipse.core.internal.resources.MarkerSet;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.AnnotationPainter;
import org.eclipse.jface.text.source.AnnotationRulerColumn;
import org.eclipse.jface.text.source.CompositeRuler;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.text.source.IVerticalRuler;
import org.eclipse.jface.text.source.IVerticalRulerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.part.EditorActionBarContributor;
import org.eclipse.ui.texteditor.AbstractDecoratedTextEditorPreferenceConstants;
import org.eclipse.ui.texteditor.AnnotationPreference;
import org.eclipse.ui.texteditor.DefaultMarkerAnnotationAccess;
import org.eclipse.ui.texteditor.IMarkerUpdater;
import org.eclipse.ui.texteditor.MarkerAnnotation;
import org.eclipse.ui.texteditor.MarkerUtilities;
import org.eclipse.ui.texteditor.SourceViewerDecorationSupport;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

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









}
