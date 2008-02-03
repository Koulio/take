package nz.ac.massey.take.takeep.outline;

import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.StringTokenizer;

import nz.ac.massey.take.takeep.Activator;
import nz.ac.massey.take.takeep.editor.TakeEditor;
import nz.ac.massey.take.takeep.editor.tokens.TakePartitionScanner.TAKE_PARTITIONS;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.views.contentoutline.ContentOutlinePage;

public class TakeOutline extends ContentOutlinePage {
	private TakeEditor editor;
	private IEditorInput fInput;

	public TakeOutline(TakeEditor takeEditor) {
		this.editor = takeEditor;

		if (this.editor.getEditorInput() != null)
			this.fInput = this.editor.getEditorInput();
	}

	@Override
	public void createControl(Composite parent) {

		super.createControl(parent);

		TreeViewer viewer = getTreeViewer();

		TreeHugger treeHugger = new TreeHugger();
		viewer.setContentProvider(treeHugger);
		viewer.setLabelProvider(new TakeOutlineLabelProvider());
		viewer.addSelectionChangedListener(this);
		if (this.fInput != null)
			viewer.setInput(this.fInput);

	}

	@Override
	public void selectionChanged(SelectionChangedEvent event) {

		super.selectionChanged(event);

		ISelection selection = event.getSelection();
		if (selection.isEmpty())
			this.editor.resetHighlightRange();
		else {

			Object object = (((IStructuredSelection) selection)
					.getFirstElement());
			if (object instanceof ITypedRegion) {

				ITypedRegion it = (ITypedRegion) object;
				try {
					this.editor.setHighlightRange(it.getOffset(), it
							.getLength(), true);
				} catch (IllegalArgumentException x) {
					this.editor.resetHighlightRange();
				}
			}

		}
	}

	private class TakeOutlineLabelProvider extends LabelProvider {

		private int maxlength = 35;
		private HashMap<String, Image> images = new HashMap<String, Image>();

		@Override
		public Image getImage(Object element) {
			if (element instanceof ITypedRegion) {
				ITypedRegion region = (ITypedRegion) element;

				if (!this.images.containsKey(region.getType())) {
					Image img = null;
					URL url = null;
					if (region.getType() == TAKE_PARTITIONS.TAKE_LOCAL_ANNOTATION
							.name()) {
						url = Activator.getDefault().getBundle().getEntry(
								"/icons/partitions/localannotation.gif");

					} else if (region.getType() == TAKE_PARTITIONS.TAKE_GLOBAL_ANNOTATION
							.name()) {
						url = Activator.getDefault().getBundle().getEntry(
								"/icons/partitions/statement.gif");
					}

					if (url != null) {
						img = ImageDescriptor.createFromURL(url).createImage();
						this.images.put(region.getType(), img);
					} else {
						this.images.put(region.getType(), null);
					}
				}

				return this.images.get(region.getType());

			}
			return null;
		}

		@Override
		public String getText(Object element) {
			if (element instanceof ITypedRegion
					&& getTreeViewer() instanceof TreeViewer) {
				ITypedRegion region = (ITypedRegion) element;
				try {

					Object root = ((ITreeContentProvider) (getTreeViewer())
							.getContentProvider()).getParent(element);
					IDocument document = TakeOutline.this.editor
							.getDocumentProvider().getDocument(root);

					String line = document.get(region.getOffset(), region
							.getLength());

					line = line.trim();

					String processedLine = null;

					if (region.getType() == TAKE_PARTITIONS.TAKE_AGGREGATION.name() || region.getType() == TAKE_PARTITIONS.TAKE_IMPORT.name()) {
						// TAKE 2nd item
						StringTokenizer st = new StringTokenizer(line, " ");
						st.nextToken();
						processedLine = st.nextToken();
					} else if (region.getType() == TAKE_PARTITIONS.TAKE_REF
							.name()
							|| region.getType() == TAKE_PARTITIONS.TAKE_VAR
									.name()) {
						// TAKE 3rd item
						StringTokenizer st = new StringTokenizer(line, " ");
						st.nextToken();
						st.nextToken();
						processedLine = st.nextToken();
					} else if (region.getType() == TAKE_PARTITIONS.TAKE_COMMENT
							.name()) {

					} else if (region.getType() == TAKE_PARTITIONS.TAKE_EXTERNAL
							.name()) {
						StringTokenizer st = new StringTokenizer(line, " ");
						st.nextToken();
						processedLine = st.nextToken();
						if (processedLine.endsWith(":")) {
							processedLine = processedLine.substring(0,
									processedLine.length() - 1);
						}
					} else if (region.getType() == TAKE_PARTITIONS.TAKE_GLOBAL_ANNOTATION
							.name()
							|| region.getType() == TAKE_PARTITIONS.TAKE_LOCAL_ANNOTATION
									.name()) {
						StringTokenizer st = new StringTokenizer(line, "=");
						processedLine = st.nextToken();
						processedLine = processedLine.replaceAll("@", "");
					} else if (region.getType() == TAKE_PARTITIONS.TAKE_QUERY
							.name()) {
						StringTokenizer st = new StringTokenizer(line, " [");
						st.nextToken();
						processedLine = st.nextToken();
					} else if (region.getType() == TAKE_PARTITIONS.TAKE_RULE_OR_FACT
							.name()) {
						StringTokenizer st = new StringTokenizer(line, ":");

						processedLine = st.nextToken();
					}
		
					if (processedLine == null) {
						processedLine = line;
					}

					if (processedLine.length() > this.maxlength) {
						processedLine = processedLine.substring(0,
								this.maxlength - 2);
						processedLine += "..";
					}

					return processedLine;
				} catch (Exception e) {

					e.printStackTrace();
				}

			}
			return element.toString();
		}

		@Override
		public void dispose() {

			for (Image i : this.images.values()) {
				if (i != null) {
					i.dispose();
				}
			}
			super.dispose();
		}

	}

	private class TreeHugger implements ITreeContentProvider {

		private TreeViewer viewer;
		private Object root;
		private LinkedList<Object> regions = new LinkedList<Object>();

		@Override
		public void dispose() {
			// TODO Auto-generated method stub

		}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			if (viewer instanceof TreeViewer) {
				this.viewer = (TreeViewer) viewer;
			}

			if (newInput != null) {
				IDocument document = TakeOutline.this.editor
						.getDocumentProvider().getDocument(newInput);

				this.root = newInput;
				parse(document);
				this.viewer.getTree().setItemCount(this.regions.size());
			}
		}

		private void parse(IDocument document) {
			this.regions.clear();
			try {
				IAnnotationModel annotationModel = TakeOutline.this.editor
						.getDocumentProvider().getAnnotationModel(
								TakeOutline.this.editor.getEditorInput());

				ITypedRegion[] re = document.computePartitioning(0, document
						.getLength());

				for (ITypedRegion ty : re) {
					if (ty.getType() == IDocument.DEFAULT_CONTENT_TYPE) {

						continue;
					}
					this.regions.add(ty);
				}

			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		@Override
		public Object[] getChildren(Object parentElement) {
			if (parentElement == this.root) {
				return this.regions.toArray();
			}

			return new Object[0];

		}

		public Object getParent(Object element) {

			return this.root;
		}

		@Override
		public boolean hasChildren(Object element) {
			if (element == this.root) {
				return true;
			}
			return false;
		}

		@Override
		public Object[] getElements(Object inputElement) {

			if (inputElement == this.root) {
				return this.regions.toArray();
			}
			return new Object[0];
		}

	}

	public void update() {
		TreeViewer viewer = getTreeViewer();
		if (viewer != null) {
			Control control = viewer.getControl();
			if (control != null && !control.isDisposed()) {
				control.setRedraw(false);
				viewer.setInput(this.editor.getEditorInput());
				control.setRedraw(true);
			}
		}
	}

}
