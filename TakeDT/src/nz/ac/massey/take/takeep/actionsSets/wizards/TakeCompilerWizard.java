package nz.ac.massey.take.takeep.actionsSets.wizards;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.StringTokenizer;

import nz.ac.massey.take.takeep.actionsSets.panels.TakeCompileWizardPanel;
import nz.ac.massey.take.takeep.editor.TakeEditor;
import nz.org.take.TakeException;
import nz.org.take.compiler.CompilerException;
import nz.org.take.compiler.NameGenerator;
import nz.org.take.compiler.reference.DefaultCompiler;
import nz.org.take.compiler.util.DefaultLocation;
import nz.org.take.compiler.util.DefaultNameGenerator;
import nz.org.take.compiler.util.jalopy.JalopyCodeFormatter;
import nz.org.take.nscript.ScriptKnowledgeSource;


import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.text.source.LineRange;
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
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.part.FileEditorInput;

public class TakeCompilerWizard extends Wizard{



	private TakeCompileWizardPanel wp;

	private boolean interfaces = false;

	public TakeCompilerWizard() {
		super();
		this.setWindowTitle("Take Compiler Wizard");
		this.setNeedsProgressMonitor(false);
		wp = createWizardPage();
		this.addPage(wp);
	}

	public TakeCompilerWizard(boolean interfaces)
	{
		this();
		this.interfaces = interfaces;
	}
	
	private TakeCompileWizardPanel createWizardPage() {
		TakeCompileWizardPanel wp = new TakeCompileWizardPanel("TakeWizard");
		wp.setPageComplete(true);
		return wp;
	}



	@Override
	public boolean performFinish() {


		IWorkbenchPage workbench = TakeCompileWizardPanel.getWorkbench();
		IProject project = TakeCompileWizardPanel.getProjectFromWorkbench(workbench);
		
		ClassLoader cl = TakeEditor.getProjectClassLoader(JavaCore.create(project));
		
		DefaultLocation location = new DefaultLocation();


		NameGenerator nameGenerator = new DefaultNameGenerator();
		nz.org.take.compiler.Compiler compiler = new DefaultCompiler();

		if(wp.isSourceTransform())
		{
			compiler.add(new JalopyCodeFormatter());
		}
		compiler.setAutoAnnotate(wp.isAutoAnotate());
		

		compiler.setNameGenerator(nameGenerator);

		IEditorInput editorInput = workbench.getActiveEditor().getEditorInput();
		if(editorInput instanceof FileEditorInput)
		{
			try {
				InputStream script = ((FileEditorInput)editorInput).getFile().getContents();

				ScriptKnowledgeSource ksource = new ScriptKnowledgeSource(script);
				ksource.setClassLoader(cl);
				compiler.setLocation(location);

				location.setSrcFolder(((FileEditorInput)editorInput).getFile().getProject().getFolder(wp.getSourceOutputLocation()).getLocation().toString());

				compiler.setLocation(location);
				compiler.setImportStatements(wp.getImportStatements().toArray(new String[wp.getImportStatements().size()]));
				compiler.setInterfaceNames(wp.getAdditionalInterfaces().toArray(new String[wp.getAdditionalInterfaces().size()]));

				compiler.setPackageName(wp.getPackageName());
				compiler.setClassName(wp.getClassName());
				
				
				if(!interfaces)
				{
				compiler.compile(ksource.getKnowledgeBase());
				}
				else
				{
					compiler.compileInterface(ksource.getKnowledgeBase());
				}
				
				project.refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
			} catch (CompilerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TakeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		else
		{
			return false;
		}
		

		return true;
	}

	public TakeCompileWizardPanel getWp() {
		return wp;
	}


}

