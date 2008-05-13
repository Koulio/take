package nz.org.take.strelka;

import java.util.ArrayList;
import java.util.List;

import nz.org.take.r2ml.util.RuleBaseFilter;
import nz.org.take.strelka.filter.CleanupOpNsFilter;
import nz.org.take.strelka.filter.CleanupSwrlbNsFilter;
import nz.org.take.strelka.filter.StrelkaDisjunctionBugFilter;
import nz.org.take.strelka.filter.SubstituteRealFilter;
import nz.org.take.strelka.preferences.gui.TakePluginPreferencesPanel;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import de.uni_paderborn.fujaba.app.FrameMain;
import de.uni_paderborn.fujaba.messages.Message;
import de.uni_paderborn.fujaba.preferences.gui.PlugInsPreferencesDialog;

/**
 * An extension for the strelka fujaba-plugin. This plugin provides functionality
 * to use the take rule compiler directly from Fujaba/Strelka.
 * 
 * @author schenke
 * 
 */
public class TakePlugin extends de.upb.lib.plugins.AbstractPlugin {

	public static final Logger logger = Logger.getLogger ("take4strelka");
	
	public static final String INFO_CATEGORY = "take Status";
	public static final String WARN_CATEGORY = "take Warn";
	public static final String ERROR_CATEGORY = "take Error";
	
	private static TakePlugin thisPlugin = null;
	
	private List<RuleBaseFilter> ruleBaseFilters = new ArrayList<RuleBaseFilter>();

	static {
		logger.setLevel(Level.ALL);
	}
	
	
	public TakePlugin() {
		super();
		
	}
	
	@Override
	public boolean initialize() {

		thisPlugin = this;

		if (logger.isDebugEnabled())
			logger.debug("loading take4strelka plugin");
		
		// adding preference panel
		PlugInsPreferencesDialog.get().addToPreferencesPanels(new TakePluginPreferencesPanel());
		if (logger.isDebugEnabled())
			logger.debug("take preference panel added");
		
		addRepairFilters();
		
		return true;
	}
	
	public List<RuleBaseFilter> getRepairFilters() {
		return ruleBaseFilters;
	}
	
	public static TakePlugin get() {
		return thisPlugin;
	}
	
	private void addRepairFilters() {
		ruleBaseFilters.add(new StrelkaDisjunctionBugFilter());
		ruleBaseFilters.add(new SubstituteRealFilter());
		ruleBaseFilters.add(new CleanupOpNsFilter());
		ruleBaseFilters.add(new CleanupSwrlbNsFilter());
	}
	
	public void addMessage(String msg) {
		addMessage(msg, TakePlugin.INFO_CATEGORY);
	}

	public void addMessage(String msg, String category) {
		Message message = new Message();
		message.setMessageCategory(category);
		message.setText("Take: " + msg);
		FrameMain.get().getMessageView().addToMessages(message);
	}

}
