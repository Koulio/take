package nz.ac.massey.take.takeep.editor;

import nz.ac.massey.take.takeep.editor.TakeSourceViewerConfiguration.TAKE_TOKENS;
import nz.ac.massey.take.takeep.editor.tokens.TakePartitionScanner;
import nz.ac.massey.take.takeep.editor.tokens.TakePartitionScanner.TAKE_PARTITIONS;
import nz.ac.massey.take.takeep.outline.TakeOutline;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.editors.text.TextEditor;
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
	}


	@Override
	protected void editorSaved() {
		// TODO Auto-generated method stub
		super.editorSaved();
		if(fOutlinePage != null) fOutlinePage.update();
	}

	
	
}
