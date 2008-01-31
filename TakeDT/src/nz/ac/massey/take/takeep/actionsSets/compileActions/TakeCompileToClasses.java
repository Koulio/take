package nz.ac.massey.take.takeep.actionsSets.compileActions;

import nz.ac.massey.take.takeep.actionsSets.TakeAbstractAction;
import nz.ac.massey.take.takeep.actionsSets.wizards.TakeCompilerWizard;

import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

public class TakeCompileToClasses extends TakeAbstractAction {

	@Override
	protected String getImageLocation() {
		// TODO Auto-generated method stub
		return "icons/fishhookCla.JPG";
	}

	@Override
	protected String getToolTip() {
		// TODO Auto-generated method stub
		return "Compile to Classes";
	}

	@Override
	public void run() {
		TakeCompilerWizard wizard = new TakeCompilerWizard();
		wizard.getWp().setTitle(getToolTip());
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.getShell();
		WizardDialog dialog = new WizardDialog(shell, wizard);
		dialog.create();
		dialog.open();
	}

}
