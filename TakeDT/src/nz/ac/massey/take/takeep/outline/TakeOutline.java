package nz.ac.massey.take.takeep.outline;

import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.StringTokenizer;

import nz.ac.massey.take.takeep.Activator;
import nz.ac.massey.take.takeep.editor.TakeEditor;
import nz.ac.massey.take.takeep.editor.tokens.TakePartitionScanner;
import nz.ac.massey.take.takeep.editor.tokens.TakePartitionScanner.TAKE_PARTITIONS;

import org.eclipse.core.runtime.preferences.IEclipsePreferences.INodeChangeListener;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.BadPositionCategoryException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.TypedRegion;
import org.eclipse.jface.text.projection.Segment;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.viewers.BaseLabelProvider;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ILabelProvider;
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
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.texteditor.MarkerUtilities;
import org.eclipse.ui.views.contentoutline.ContentOutlinePage;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

import sun.awt.image.ImageDecoder;

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

		TreeHugger treeHugger = new TreeHugger();
		viewer.setContentProvider(treeHugger);
		viewer.setLabelProvider(new TakeOutlineLabelProvider());
		viewer.addSelectionChangedListener(this);
		if (fInput != null)
			viewer.setInput(fInput);

//		viewer.addFilter(new ViewerFilter(){

//		@Override
//		public boolean select(Viewer viewer, Object parentElement,
//		Object element) {
//		if(element instanceof ITypedRegion)
//		{
//		ITypedRegion it = (ITypedRegion) element;
//		if(it.getType() == TAKE_PARTITIONS.TAKE_RULE_OR_FACT.name())
//		{
//		return true;
//		}
//		}
//		return false;
//		}});

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


	private class TakeOutlineLabelProvider extends LabelProvider
	{

		private int maxlength = 35;
		private HashMap<String,Image> images = new HashMap<String, Image>();
		@Override
		public Image getImage(Object element) {
			if(element instanceof ITypedRegion)
			{
				ITypedRegion region = (ITypedRegion)element;

				if(!images.containsKey(region.getType()))
				{
					Image img = null;
					URL url = null;
					if(region.getType() == TAKE_PARTITIONS.TAKE_LOCAL_ANNOTATION.name())
					{
						url = Activator.getDefault().getBundle().getEntry("/icons/partitions/localannotation.gif");

					}
					else if(region.getType() == TAKE_PARTITIONS.TAKE_GLOBAL_ANNOTATION.name())
					{
						url = Activator.getDefault().getBundle().getEntry("/icons/partitions/statement.gif");
					}

					if(url != null)
					{
						img = ImageDescriptor.createFromURL(url).createImage();
						images.put(region.getType(), img);
					}
					else
					{
						images.put(region.getType(), null);
					}
				}

				return images.get(region.getType());

			}
			return null;
		}

		@Override
		public String getText(Object element) {
			if(element instanceof ITypedRegion && getTreeViewer() instanceof TreeViewer)
			{
				ITypedRegion region = (ITypedRegion)element;
				try {
						

					Object root = ((ITreeContentProvider)((TreeViewer)getTreeViewer()).getContentProvider()).getParent(element);
					IDocument document= editor.getDocumentProvider().getDocument(root);


					String line = document.get(region.getOffset(), region.getLength());

					line = line.trim();



					String processedLine = null;

					if(region.getType() == TAKE_PARTITIONS.TAKE_AGGREGATION.name())
					{
						//TAKE 2nd item
						StringTokenizer st = new StringTokenizer(line," ");
						st.nextToken();
						processedLine = st.nextToken();
					}
					else if(region.getType() == TAKE_PARTITIONS.TAKE_REF.name() || region.getType() == TAKE_PARTITIONS.TAKE_VAR.name())
					{
						//TAKE 3rd item
						StringTokenizer st = new StringTokenizer(line," ");
						st.nextToken();
						st.nextToken();
						processedLine = st.nextToken();
					}
					else if(region.getType() == TAKE_PARTITIONS.TAKE_COMMENT.name())
					{


					}
					else if(region.getType() == TAKE_PARTITIONS.TAKE_EXTERNAL.name())
					{
						StringTokenizer st = new StringTokenizer(line," ");
						st.nextToken();
						processedLine = st.nextToken();
						if(processedLine.endsWith(":"))
						{
							processedLine = processedLine.substring(0,processedLine.length()-1);
						}
					}
					else if(region.getType() == TAKE_PARTITIONS.TAKE_GLOBAL_ANNOTATION.name() || region.getType() == TAKE_PARTITIONS.TAKE_LOCAL_ANNOTATION.name())
					{
						StringTokenizer st = new StringTokenizer(line,"=");
						processedLine = st.nextToken();
						processedLine = processedLine.replaceAll("@", "");
					}
					else if(region.getType() == TAKE_PARTITIONS.TAKE_QUERY.name())
					{
						StringTokenizer st = new StringTokenizer(line," [");
						st.nextToken();
						processedLine = st.nextToken();
					}
					else if(region.getType() == TAKE_PARTITIONS.TAKE_RULE_OR_FACT.name())
					{
						StringTokenizer st = new StringTokenizer(line,":");

						processedLine = st.nextToken();
					}

					if(processedLine == null)
					{
						processedLine = line; 
					}

					if(processedLine.length() > maxlength)
					{
						processedLine = processedLine.substring(0, maxlength -2);
						processedLine += "..";
					}


					return processedLine;
				} catch (Exception e) {
					
					IAnnotationModel annotationModel = editor.getDocumentProvider().getAnnotationModel(editor.getEditorInput());
					Annotation annotation = new Annotation("org.eclipse.ui.workbench.texteditor.error",false,"");
					anno.add(annotation);
					annotationModel.addAnnotation(annotation, new Position(region.getOffset(),region.getLength()));
					
					e.printStackTrace();
				}


			}
			return element.toString();
		}

		@Override
		public void dispose() {
			
			for(Image i : images.values())
			{
				if(i != null)
				{
					i.dispose();
				}
			}
			super.dispose();
		}

	}

	private LinkedList<Annotation> anno = new LinkedList<Annotation>();

	private class TreeHugger implements ITreeContentProvider
	{

		private TreeViewer viewer;
		private Object root;
		private LinkedList<Object> regions = new LinkedList<Object>();

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
				IAnnotationModel annotationModel = editor.getDocumentProvider().getAnnotationModel(editor.getEditorInput());
				for(Annotation a : anno)
				{
					annotationModel.removeAnnotation(a);
					
				}
				anno.clear();
				ITypedRegion[] re = document.computePartitioning(0, document.getLength());

				for(ITypedRegion ty : re)
				{
					if(ty.getType() == IDocument.DEFAULT_CONTENT_TYPE)
					{
						
						Annotation annotation = new Annotation("org.eclipse.ui.workbench.texteditor.error",false,"");
						anno.add(annotation);
						annotationModel.addAnnotation(annotation, new Position(ty.getOffset(),ty.getLength()));
						continue;
					}
					regions.add(ty);
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
				return regions.toArray();
			}

			return new Object[0];

		}


		public Object getParent(Object element) {

			return root;
		}

		@Override
		public boolean hasChildren(Object element) {
			if(element == root)
			{
				return true;
			}
			return false;
		}

		@Override
		public Object[] getElements(Object inputElement) {

			if(inputElement == root)
			{
				return regions.toArray();
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


