package nz.ac.massey.take.takeep.actionsSets;

import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class TakeCompilerWizard extends Wizard{

	public TakeCompilerWizard() {
		super();
		this.setWindowTitle("Take Compiler Wizard");
		this.setNeedsProgressMonitor(false);
		
		
		WizardPage wp = new WizardPage("page 1"){

			@Override
			public void createControl(Composite parent) {
				
				Composite topLevel = new Composite(parent, SWT.NONE);
			    topLevel.setLayout(new FillLayout());

			    Label textLabel = new Label(topLevel, SWT.CENTER);
			    textLabel.setText("dsadf");

			    setControl(topLevel);
			    setPageComplete(true);
				
			}};
			this.addPage(wp);
	}

	@Override
	public boolean performFinish() {
		// TODO Auto-generated method stub
		return true;
	}





}
