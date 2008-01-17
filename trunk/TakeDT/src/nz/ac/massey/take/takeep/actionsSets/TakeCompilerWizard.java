package nz.ac.massey.take.takeep.actionsSets;

import java.util.LinkedList;
import java.util.StringTokenizer;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
public class TakeCompilerWizard extends Wizard{



	private TakeCompileWizardPanel wp;
	


	public TakeCompilerWizard() {
		super();
		this.setWindowTitle("Take Compiler Wizard");
		this.setNeedsProgressMonitor(false);
		
		wp = createWizardPage();
		this.addPage(wp);
	}

	private TakeCompileWizardPanel createWizardPage() {
		TakeCompileWizardPanel wp = new TakeCompileWizardPanel("TakeWizard");
		wp.setPageComplete(true);
		
		return wp;
	}


	
	@Override
	public boolean performFinish() {

		System.out.println("Package Name : " + wp.getPackageName());
		System.out.println("Class Name : " + wp.getClassName());
		System.out.println("Location : " + wp.getSourceOutputLocation());
		
		for(Object o : wp.getImportStatements())
		{
			System.out.println("import : " + o);
		}
		
		for(Object o : wp.getAdditionalInterfaces())
		{
			System.out.println("interface : " + o);
		}
		
		System.out.println("Auto annotate : " + wp.isAutoAnotate());
		System.out.println("Source Tranform: " + wp.isSourceTransform());
		return true;
	}

}

	