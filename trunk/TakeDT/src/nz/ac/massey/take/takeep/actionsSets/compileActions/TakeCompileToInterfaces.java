package nz.ac.massey.take.takeep.actionsSets.compileActions;

import nz.ac.massey.take.takeep.actionsSets.TakeAbstractAction;
import nz.ac.massey.take.takeep.actionsSets.wizards.TakeCompilerWizard;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;

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
		TakeCompilerWizard wizard = new TakeCompilerWizard(true);
		wizard.getWp().setTitle(getToolTip());
		Shell shell =
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		WizardDialog dialog= new WizardDialog(shell, wizard);
		dialog.create();
		dialog.open();
	}




}
