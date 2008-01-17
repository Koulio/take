package nz.ac.massey.take.takeep.actionsSets.compileActions;

import java.net.URL;

import nz.ac.massey.take.takeep.Activator;
import nz.ac.massey.take.takeep.actionsSets.TakeAbstractAction;
import nz.ac.massey.take.takeep.actionsSets.wizards.TakeCompilerWizard;

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

		Shell shell =
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		WizardDialog dialog= new WizardDialog(shell, wizard);
		dialog.create();
		dialog.open();
	}



	





	
}
