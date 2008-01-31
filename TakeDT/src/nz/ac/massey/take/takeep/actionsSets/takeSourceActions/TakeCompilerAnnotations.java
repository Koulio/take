package nz.ac.massey.take.takeep.actionsSets.takeSourceActions;

import nz.ac.massey.take.takeep.actionsSets.TakeAbstractAction;
import nz.ac.massey.take.takeep.actionsSets.wizards.TakeCompilerAnnotationsWizard;

import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

public class TakeCompilerAnnotations extends TakeAbstractAction {

	@Override
	protected String getImageLocation() {
		return null;
	}

	@Override
	protected String getToolTip() {
		return "Compiler annotations";
	}

	@Override
	public void run() {
		TakeCompilerAnnotationsWizard wizard = new TakeCompilerAnnotationsWizard();

		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.getShell();
		WizardDialog dialog = new WizardDialog(shell, wizard);
		dialog.create();
		dialog.open();
	}

}
