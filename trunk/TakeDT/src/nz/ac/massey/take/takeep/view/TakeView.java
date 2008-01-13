package nz.ac.massey.take.takeep.view;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.ViewPart;

public class TakeView extends ViewPart implements ISelectionListener{

	@Override
	public void createPartControl(Composite parent) {
		this.getSite().getPage().addSelectionListener(this);
		
	}

	@Override
	public void setFocus() {
		
		
	}

	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
				
				
	}


}
