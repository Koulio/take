package nz.ac.massey.take.takeep.actionsSets.wizards;

import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;

import javax.script.ScriptException;

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
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;
import org.eclipse.ui.part.FileEditorInput;

public class TakeCompilerWizard extends Wizard {

	private TakeCompileWizardPanel wp;

	private boolean interfaces = false;

	public TakeCompilerWizard() {
		super();
		this.setWindowTitle("Take Compiler Wizard");
		this.setNeedsProgressMonitor(false);
		this.wp = createWizardPage();
		this.addPage(this.wp);
	}

	public TakeCompilerWizard(boolean interfaces) {
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
		IProject project = TakeCompileWizardPanel
				.getProjectFromWorkbench(workbench);

		ClassLoader cl = TakeEditor.getProjectClassLoader(JavaCore
				.create(project));

		DefaultLocation location = new DefaultLocation();

		NameGenerator nameGenerator = new DefaultNameGenerator();
		nz.org.take.compiler.Compiler compiler = new DefaultCompiler();

		if (this.wp.isSourceTransform()) {
			compiler.add(new JalopyCodeFormatter());
		}
		compiler.setAutoAnnotate(this.wp.isAutoAnotate());

		compiler.setNameGenerator(nameGenerator);
		IEditorInput editorInput = workbench.getActiveEditor().getEditorInput();
		IDocument document = ((TakeEditor)workbench.getActiveEditor()).getDocumentProvider() .getDocument(editorInput);
		MessageConsole mc = findConsole(editorInput.getName());
		
		MessageConsoleStream out = mc.newMessageStream();
		
		if (editorInput instanceof FileEditorInput) {
			try {
				Reader script;
				
				script = new StringReader(document.get());
				
				ScriptKnowledgeSource ksource = new ScriptKnowledgeSource(
						script);
				ksource.setClassLoader(cl);
				compiler.setLocation(location);

				location.setSrcFolder(((FileEditorInput) editorInput).getFile()
						.getProject().getFolder(
								this.wp.getSourceOutputLocation())
						.getLocation().toString());

				compiler.setLocation(location);
				compiler
						.setImportStatements(this.wp.getImportStatements()
								.toArray(
										new String[this.wp
												.getImportStatements().size()]));
				compiler.setInterfaceNames(this.wp.getAdditionalInterfaces()
						.toArray(
								new String[this.wp.getAdditionalInterfaces()
										.size()]));

				compiler.setPackageName(this.wp.getPackageName());
				compiler.setClassName(this.wp.getClassName());

				if (!this.interfaces) {
					compiler.compile(ksource.getKnowledgeBase());
				} else {
					compiler.compileInterface(ksource.getKnowledgeBase());
				}

				project.refreshLocal(IResource.DEPTH_INFINITE,
						new NullProgressMonitor());
			} catch (CompilerException e) {
				out.println(e.getMessage());
				mc.activate();
				e.printStackTrace();
				return false;
			} catch (TakeException e) {
				out.println(e.getMessage());
				mc.activate();
				e.printStackTrace();
				return false;
			} catch (CoreException e) {
				out.println(e.getMessage());
				mc.activate();
				e.printStackTrace();
				return false;
			}

		} else {
			return false;
		}

		out.println("Successfully compiled " + editorInput.getName() + " to " + wp.getSourceOutputLocation() + "/" + wp.getPackageName());
		mc.activate();
		return true;
	}

	
	  private MessageConsole findConsole(String name) {
		  
	      ConsolePlugin plugin = ConsolePlugin.getDefault();
	      IConsoleManager conMan = plugin.getConsoleManager();
	      IConsole[] existing = conMan.getConsoles();
	      for (int i = 0; i < existing.length; i++)
	      {
	    	  System.out.println(name);
	         if (name.equals(existing[i].getName()))
	         {
	            return (MessageConsole) existing[i];
	         }
	      }
	      //no console found, so create a new one
	      MessageConsole myConsole = new MessageConsole(name, null);
	      conMan.addConsoles(new IConsole[]{myConsole});
	      return myConsole;
	   }
	  
	  
	public TakeCompileWizardPanel getWp() {
		return this.wp;
	}

}
