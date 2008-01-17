package nz.ac.massey.take.takeep.actionsSets.compileActions;

import nz.ac.massey.take.takeep.actionsSets.TakeAbstractAction;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

public class TakeCompileToInterfaces extends TakeAbstractAction {

	@Override
	protected String getImageLocation() {
		return "icons/fishhookInt.JPG";
	}

	@Override
	protected String getToolTip() {
		return "Compile to Interfaces";
	}

	@Override
	public void run() {
		
	}



}
