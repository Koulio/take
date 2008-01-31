package nz.ac.massey.take.takeep.actionsSets.verifyActions;

import nz.ac.massey.take.takeep.actionsSets.TakeAbstractAction;
import nz.ac.massey.take.takeep.actionsSets.panels.TakeCompileWizardPanel;
import nz.ac.massey.take.takeep.editor.TakeEditor;

import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;

public class TakeRunVerifiers extends TakeAbstractAction {

	@Override
	protected String getImageLocation() {
		// TODO Auto-generated method stub
		return "icons/fishhookVer.JPG";
	}

	@Override
	protected String getToolTip() {
		// TODO Auto-generated method stub
		return "Run Verifiers";
	}

	@Override
	public void run() {
		IWorkbenchPage iworkbenchpage = TakeCompileWizardPanel.getWorkbench();
		if (iworkbenchpage == null)
			return;
		IEditorPart activeEditor = iworkbenchpage.getActiveEditor();
		if (activeEditor instanceof TakeEditor) {
			((TakeEditor) activeEditor).runVerifier();
		}
	}

}
