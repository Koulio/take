/**
 * 
 */
package nz.org.take.strelka.actions;

import javax.swing.Icon;
import javax.swing.JOptionPane;

import nz.org.take.strelka.TakePlugin;
import nz.org.take.strelka.preferences.TakePluginPreferences;

import de.tu_cottbus.r2ml.RuleBase;
import de.tu_cottbus.rule.r2ml.R2MLExport;
import de.uni_paderborn.fujaba.app.FrameMain;
import de.uni_paderborn.fujaba.uml.UMLProject;

/**
 * @author Bastian Schenke (bastian.schenke(at)googlemail.com)
 * 
 */
public class CompileRulesOfProject extends CompileRules {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8633595255923873216L;

	/**
	 * 
	 */
	public CompileRulesOfProject() {
	}

	/**
	 * @param name
	 */
	public CompileRulesOfProject(String name) {
		super(name);
	}

	/**
	 * @param name
	 * @param icon
	 */
	public CompileRulesOfProject(String name, Icon icon) {
		super(name, icon);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nz.org.take.strelka.actions.CompileRules#getRuleBase()
	 */
	@Override
	protected RuleBase getRuleBase() {
		// is there a current project; if not do nothing
		if (!UMLProject.isInitialized()) {
			JOptionPane.showMessageDialog(FrameMain.get(),
					"There is no project open.", "Okay", 2);
			if (TakePlugin.logger.isInfoEnabled())
				TakePlugin.logger.info("There is no active project.");
			return null;
		}

		RuleBase rb = null;

		String tmpFile = TakePluginPreferences.get().getTmpFile();
		try {
			R2MLExport ruleExport = new R2MLExport(UMLProject.get(), tmpFile);

			rb = ruleExport.getRuleBase();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return rb;
	}

}
