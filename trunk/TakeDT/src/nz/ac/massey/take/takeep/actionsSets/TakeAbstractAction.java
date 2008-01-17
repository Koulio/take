package nz.ac.massey.take.takeep.actionsSets;

import java.net.URL;

import nz.ac.massey.take.takeep.Activator;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;

public abstract class TakeAbstractAction extends Action implements IWorkbenchWindowActionDelegate{

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}
	@Override
	public void init(IWorkbenchWindow window) {
		// TODO Auto-generated method stub

	}
	protected abstract String getImageLocation();
	protected abstract String getToolTip();


	protected String getMenuText() {
		// TODO Auto-generated method stub
		return getToolTip();
	}



	public TakeAbstractAction() {
		super();
		if(getImageLocation() != null)
		{
			URL url = Activator.getDefault().getBundle().getEntry(getImageLocation());
			setImageDescriptor(ImageDescriptor.createFromURL(url));
		}
		this.setToolTipText(getToolTip());
		this.setId(this.getClass().toString());
		this.setText(getMenuText());
	}


	@Override
	public abstract void run();


	@Override
	public void runWithEvent(Event event) {

		run();
	}

	@Override
	public void run(IAction action) {
		run();

	}


	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub

	}


}
