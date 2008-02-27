/**
 * 
 */
package nz.org.take.strelka.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import nz.org.take.KnowledgeBase;
import nz.org.take.TakeException;
import nz.org.take.compiler.reference.DefaultCompiler;
import nz.org.take.compiler.util.DefaultLocation;
import nz.org.take.compiler.util.DefaultNameGenerator;
import nz.org.take.compiler.util.jalopy.JalopyCodeFormatter;
import nz.org.take.r2ml.DatatypeMapper;
import nz.org.take.r2ml.NameMapper;
import nz.org.take.r2ml.R2MLDriver;
import nz.org.take.r2ml.R2MLException;
import nz.org.take.r2ml.R2MLKnowledgeSource;
import nz.org.take.r2ml.util.RuleBaseFilter;
import nz.org.take.strelka.DomainClassLoader;
import nz.org.take.strelka.TakePlugin;
import nz.org.take.strelka.preferences.TakePluginPreferences;

import de.tu_cottbus.r2ml.DerivationRuleSet;
import de.tu_cottbus.r2ml.RuleBase;

/**
 * @author Bastian Schenke (bastian.schenke(at)googlemail.com)
 * 
 */
public abstract class CompileRules extends AbstractAction {

	private final class CompileWorker extends SwingWorker<Void, Void> {

		protected Void doInBackground() throws R2MLException {

			RuleBase rb = getRuleBase();

			if (rb == null) {
				TakePlugin.get().addMessage("Unable to generate RuleBase.",
						TakePlugin.WARN_CATEGORY);
				return null;
			}

			/* just a bit debugging stuff */
			// System.out.println("before [CompileRules]");
			// DerivationRuleSet rs = (DerivationRuleSet)
			// rb.getRuleSet().get(0).getValue();
			// AttributionAtom atom = (AttributionAtom)
			// rs.getDerivationRule().get(0).getConclusion().getAtom().getValue();
			// DatatypeFunctionTerm dtft = (DatatypeFunctionTerm)
			// atom.getDataValue().getDataTerm().getValue();
			// System.out.println(dtft.getDatatypeFunctionID());
			// TypedLiteral tl = (TypedLiteral)
			// dtft.getDataArguments().getDataTerm().get(0).getValue();
			// System.out.println(tl.getDatatypeID().getLocalPart() + " blub : "
			// + tl.getLexicalValue());

			for (RuleBaseFilter filter : TakePlugin.get().getRepairFilters()) {
				if (TakePlugin.logger.isDebugEnabled())
					TakePlugin.logger.debug("applying filter "
							+ filter.getClass().getSimpleName());
				filter.repair(rb);
			}

			// System.out.println("after [CompileRules]");
			// DerivationRuleSet rs2 = (DerivationRuleSet)
			// rb.getRuleSet().get(0).getValue();
			// AttributionAtom atom2 = (AttributionAtom)
			// rs2.getDerivationRule().get(0).getConclusion().getAtom().getValue();
			// DatatypeFunctionTerm dtft2 = (DatatypeFunctionTerm)
			// atom2.getDataValue().getDataTerm().getValue();
			// System.out.println(dtft2.getDatatypeFunctionID());
			// TypedLiteral tl2 = (TypedLiteral)
			// dtft2.getDataArguments().getDataTerm().get(0).getValue();
			// System.out.println(tl2.getDatatypeID() + " : " +
			// tl2.getLexicalValue());
			// if (true)
			// return null;

			if (TakePlugin.logger.isDebugEnabled()) {
				StringBuffer msg = new StringBuffer("import RuleBase (");
				msg.append(rb.toString()).append(")");
				msg.append("; ");
				msg.append("RuleBase has ").append(rb.getRuleSet().size())
						.append(" RuleSets. ");
				msg.append("The first RuleSet contains "
						+ ((DerivationRuleSet) rb.getRuleSet().get(0)
								.getValue()).getDerivationRule().size()
						+ " rules.");
				msg.append("\r\n");
				TakePlugin.logger.debug(msg.toString());
			}

			if (rb.getRuleSet().size() == 0) {
				if (TakePlugin.logger.isDebugEnabled())
					TakePlugin.logger
							.debug("No rules in current diagram and/or project.");
				// return null;
			}

			try {
				if (!generateKnowledgeBase(rb)) {
					JOptionPane
							.showMessageDialog(null,
									"Knowledge base NOT generated, see log for more details.");
					TakePlugin.get().addMessage(
							"Knowledge base NOT generated.",
							TakePlugin.WARN_CATEGORY);
				}

			} catch (TakeException e1) {
				TakePlugin.get().addMessage(
						"Error while compiling KnowledgeBase.",
						TakePlugin.WARN_CATEGORY);
				e1.printStackTrace();

				return null;
			}
			TakePlugin
					.get()
					.addMessage(
							"KnowledgeBase successfully exported and sourcecode generated.");
			return null;
		} // donInBackground()
	}

	/**
	 * 
	 */
	public CompileRules() {

	}

	/**
	 * @param name
	 */
	public CompileRules(String name) {
		super(name);
	}

	/**
	 * @param name
	 * @param icon
	 */
	public CompileRules(String name, Icon icon) {
		super(name, icon);
	}

	/**
	 * 
	 * @return the RuleBase
	 */
	protected abstract RuleBase getRuleBase();

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {

		TakePlugin.get().addMessage(
				"Starting compilation process. This may take a while.",
				TakePlugin.INFO_CATEGORY);

		SwingWorker worker = new CompileWorker();
		worker.execute();

	}

	private boolean generateKnowledgeBase(RuleBase rb) throws TakeException {
		String packageName = TakePluginPreferences.get().getPackageName();
		String kbName = TakePluginPreferences.get().getKnowledgeBaseName();

		String locationPath = TakePluginPreferences.get().getTargetDirectory();
		
		String datatypeMapperClazz = TakePluginPreferences.get()
				.getDatatypeMapperClazz();
		String nameMapperClazz = TakePluginPreferences.get()
				.getNameMapperClazz();
		String domainJarPath = TakePluginPreferences.get().getDomainCLasses();
		String queryGeneratorClazz = TakePluginPreferences.get().getQueryGenerator();//"example.nz.org.take.r2ml.userv.domain.UServQueryGenerator";

		DomainClassLoader domainLoader = new DomainClassLoader(domainJarPath);
		R2MLKnowledgeSource ksource = domainLoader.getKnowledgeSource(rb);

		ksource.setQueryGenerator(domainLoader
				.getQueryGenerator(queryGeneratorClazz));
		DatatypeMapper typeMapper = domainLoader
				.getDatatypeMapper(datatypeMapperClazz);
		NameMapper nameMapper = domainLoader.getNameMapper(nameMapperClazz);

		ksource.setDatatypeMapper(typeMapper);
		ksource.setSlotNameGenerator(nameMapper);
		//ksource.setSlotNameGenerator(nameMapper);
		ksource.setPropertyMode(R2MLDriver.INFER_PROPERTIES_MODE);
		//System.out.println("start kb generation");
		KnowledgeBase kb = ksource.getKnowledgeBase();
		//System.out.println("finish kb generation");
		if (kb == null) {
			TakePlugin.get().addMessage(
					"KnowledgeBase not generated :(",
					TakePlugin.ERROR_CATEGORY);
			return false;
		} else {
			TakePlugin.get().addMessage(
					"KnowledgeBase generated :)",
					TakePlugin.INFO_CATEGORY);
		}
		annotateKB(kb);

		TakePlugin.logger.debug("Compilation of the KnowledgeBase started!");
		nz.org.take.compiler.Compiler compiler = new DefaultCompiler();
		compiler.add(new JalopyCodeFormatter());
		compiler.setNameGenerator(new DefaultNameGenerator());
		compiler.setLocation(new DefaultLocation(locationPath));
		compiler.setPackageName(packageName);
		compiler.setClassName(kbName);
		compiler.setAutoAnnotate(false);
		compiler.compile(kb);
		TakePlugin.logger
				.debug("Compilation of the KnowledgeBase should be finished!");
		return true;
	}

	private void annotateKB(KnowledgeBase kb) {
		// this is done by the R2MLAdapter itself
	}

}
