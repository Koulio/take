package nz.ac.massey.take.takeep.outline;

import java.util.HashMap;
import java.util.LinkedList;

import nz.ac.massey.take.takeep.editor.TakeEditor;

import org.eclipse.core.runtime.preferences.IEclipsePreferences.INodeChangeListener;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.BadPositionCategoryException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.TypedRegion;
import org.eclipse.jface.text.projection.Segment;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ILazyTreeContentProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeNode;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.contentoutline.ContentOutlinePage;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

public class TakeOutline extends ContentOutlinePage
{
	private TakeEditor editor;
	private IEditorInput fInput;

	public TakeOutline(TakeEditor takeEditor) {
		editor = takeEditor;
		if (editor.getEditorInput() != null)
			fInput = editor.getEditorInput();

	}


	public void createControl(Composite parent) {


		super.createControl(parent);

		TreeViewer viewer= getTreeViewer();
		viewer.setContentProvider(new TreeHugger());
		viewer.setLabelProvider(new LabelProvider());
		viewer.addSelectionChangedListener(this);
		if (fInput != null)
			viewer.setInput(fInput);

		//makeTree(viewer.getTree());

	}


	public void selectionChanged(SelectionChangedEvent event) {

		super.selectionChanged(event);

		ISelection selection= event.getSelection();
		if (selection.isEmpty())
			editor.resetHighlightRange();
		else {

			Object object = (((IStructuredSelection) selection).getFirstElement());
			if(object instanceof ITypedRegion)
			{
				ITypedRegion it = (ITypedRegion) object;
				try {
					editor.setHighlightRange(it.getOffset(), it.getLength(), true);
				} catch (IllegalArgumentException x) {
					editor.resetHighlightRange();
				}
			}

		}
	}






	private class TreeHugger implements ITreeContentProvider
	{
		private HashMap<String,LinkedList<ITypedRegion>> regions = new HashMap<String, LinkedList<ITypedRegion>>();
		private TreeViewer viewer;
		private Object root;


		@Override
		public void dispose() {
			// TODO Auto-generated method stub

		}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			if(viewer instanceof TreeViewer)
			{
				this.viewer = (TreeViewer)viewer;
			}
			if (newInput != null) {
				IDocument document= editor.getDocumentProvider().getDocument(newInput);
				root = newInput;
				parse(document);
				this.viewer.getTree().setItemCount(regions.size());
			}
		}

		private void parse(IDocument document) {
			regions.clear();

			try {
				ITypedRegion[] re = document.computePartitioning(0, document.getLength());

				for(ITypedRegion ty : re)
				{
					if(regions.containsKey(ty.getType()))
					{
						regions.get(ty.getType()).add(ty);
					}
					else
					{
						regions.put(ty.getType(),new LinkedList<ITypedRegion>());
						regions.get(ty.getType()).add(ty);
					}
				}

			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		@Override
		public Object[] getChildren(Object parentElement) {
			if(parentElement == root)
			{
				return regions.keySet().toArray();
			}
			else if(parentElement instanceof String)
			{
				return regions.get(parentElement.toString()).toArray();
			}
			else if(parentElement instanceof ITypedRegion)
			{
				return new Object[0];
			}

			return new Object[0];

		}

		@Override
		public Object getParent(Object element) {
			System.out.println("getParent");
			return null;
		}

		@Override
		public boolean hasChildren(Object element) {
			if(element == root)
			{
				return regions.keySet().size() > 0;
			}
			else if(element instanceof String)
			{
				return regions.get(element.toString()).size() > 0;
			}

			return false;
		}

		@Override
		public Object[] getElements(Object inputElement) {
			if(inputElement == root)
			{
				return regions.keySet().toArray();
			}
			else if(inputElement instanceof String)
			{
				return regions.get(inputElement.toString()).toArray();
			}
			else if(inputElement instanceof ITypedRegion)
			{
				return new Object[0];
			}

			return new Object[0];
		}

	}







	public void update() {
		TreeViewer viewer = getTreeViewer();
		if (viewer != null)
		{
			Control control = viewer.getControl();
			if (control != null && !control.isDisposed())
			{
				control.setRedraw(false);
				viewer.setInput(editor.getEditorInput());
				control.setRedraw(true);
			}
		}
	}

}


