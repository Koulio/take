package nz.org.take.strelka.actions;

import javax.swing.JOptionPane;

import nz.org.take.strelka.preferences.TakePluginPreferences;
import de.tu_cottbus.r2ml.RuleBase;
import de.tu_cottbus.rule.r2ml.R2MLExport;
import de.uni_paderborn.fujaba.app.FrameMain;
import de.uni_paderborn.fujaba.uml.UMLProject;

public class CompileRulesOfDiagram extends CompileRules {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7800405664431118664L;

	@Override
	protected RuleBase getRuleBase() {
		// is there a current project; if not do nothing
		if (UMLProject.get() == null) {
			JOptionPane.showMessageDialog(FrameMain.get(),
					"There is no project open.", "Okay", 2);
			return null;
		}
		// is there a current diagram; if not do nothing
		if (UMLProject.get().getCurrentUMLDiagram() == null) {
			JOptionPane.showMessageDialog(FrameMain.get(),
					"There is no class diagram selected.", "Okay", 2);
			return null;
		}

		RuleBase rb = null;

		String tmpFile = TakePluginPreferences.get().getTmpFile();
		try {
			R2MLExport ruleExport = new R2MLExport(
					UMLProject.get().getCurrentUMLDiagram(), tmpFile);

			rb = ruleExport.getRuleBase();
			

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return rb;//rb;
	}

}
