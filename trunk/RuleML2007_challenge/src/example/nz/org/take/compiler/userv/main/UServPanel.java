/**
 * Copyright 2007 Jens Dietrich Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
 */
 

package example.nz.org.take.compiler.userv.main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.script.Bindings;
import javax.script.SimpleBindings;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import nz.org.take.deployment.KnowledgeBaseManager;
import nz.org.take.rt.*;
import nz.org.take.script.ScriptKnowledgeSource;
import example.nz.org.take.compiler.userv.domainmodel.*;
import example.nz.org.take.compiler.userv.spec.*;

/**
 * The main interface of the UServ application, used within UServApp.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class UServPanel extends JPanel {
	// models
	private Driver driver = new Driver();
	private Car car = new Car();
	private Policy policy = new Policy();
	private Map<String,List<DerivationLogEntry>> derivationLogs = new HashMap<String,List<DerivationLogEntry>> ();
	private UservRules kb = this.loadRules();
	
	// constants
	private final static String[] STATES = {
		"unknown","CA","FL","NY"
	};
	private final static String[] CAR_CATEGORIES = {
		"compact","sedan","luxury","svu","other"
	};
	
	// parts	
	JPanel panel = this;
	JPanel carPanel = new JPanel(new GridBagLayout());
	JPanel driverPanel = new JPanel(new GridBagLayout());
	JPanel resultPanel = new JPanel(new GridBagLayout());
	JPanel policyPanel = new JPanel(new GridBagLayout());
	JPanel driverEligibilityPanel = new JPanel(new GridBagLayout());
	JPanel policyPremiumPanel = new JPanel(new GridBagLayout());
	JPanel discountPanel = new JPanel(new GridBagLayout());
	
	int[] rowCounts = {-1,-1,-1,-1,-1,-1,-1,-1,-1};
	
	// output components
	JTextField txtDriverCategory = createOutputTextField();
	JTextField txtDriverEligibility = createOutputTextField();
	JTextField txtHasTrainingCertification = createOutputTextField();
	JTextField txtHighRiskDriver = createOutputTextField();
	MultiValueResultView multiOutPolicyEligibilityScore = new MultiValueResultView(this) {
		public int extractValue(Object object) {
			PolicyEligibilityScore pes = (PolicyEligibilityScore)object;
			return pes.score;
		}
	};
	JTextField txtInsuranceEligibility = createOutputTextField();
	JTextField txtLongTermClient = createOutputTextField();
	JTextField txtBasePremium = createOutputTextField();
	MultiValueResultView multiAdditionalPremium = new MultiValueResultView(this) {
		public int extractValue(Object object) {
			AdditionalPremium pes = (AdditionalPremium)object;
			return pes.premium;
		}
	};
	MultiValueResultView multiAdditionalDriverPremium = new MultiValueResultView(this) {
		public int extractValue(Object object) {
			AdditionalDriverPremium pes = (AdditionalDriverPremium)object;
			return pes.premium;
		}
	};
	MultiValueResultView multiPremiumDiscount = new MultiValueResultView(this) {
		public int extractValue(Object object) {
			PremiumDiscount pes = (PremiumDiscount)object;
			return pes.discount;
		}
	};
	public UServPanel() {
		super();
		init();
	}
	
	private JTextField createOutputTextField() {
		JTextField txtField = new JTextField();
		txtField.setEditable(false);
		return txtField;
	}
	


	private void init() {
		
		this.setLayout(new GridLayout(2,1));
		
		// input pane
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.setBorder(
				BorderFactory.createCompoundBorder(
						BorderFactory.createEmptyBorder(3,3,3,3), 
						BorderFactory.createTitledBorder(" input "))
		);
		this.add(tabbedPane);
		
		// driver editor
		activatePanel(tabbedPane,driverPanel, "driver details");
		
		add(createStringEditor(driver,"getId","setId"),"id",0,100);
		add(createIntEditor(driver,"getAge","setAge",0,100),"age",0,40);
		add(createSelectStringEditor(driver,"getLocation","setLocation",STATES),"location",0,100);
		add(createBooleanEditor(driver,"isMarried","setMarried"),"is married",0);
		add(createBooleanEditor(driver,"isMale","setMale"),"is male",0);
		
		add(createBooleanEditor(driver,"isElite","setElite"),"is elite",1);
		add(createBooleanEditor(driver,"isPreferred","setPreferred"),"is preferred",1);		
		add(createBooleanEditor(driver,"hasDriversTrainingFromSchool","setHasDriversTrainingFromSchool"),"has drivers training from school",1);
		add(createBooleanEditor(driver,"hasDriversTrainingFromLicensedDriverTrainingCompany","setHasDriversTrainingFromLicensedDriverTrainingCompany"),"<html><body align=\"right\">has drivers training from licensed<br> driver training company</body></html>",1);
		add(createBooleanEditor(driver,"hasTakenASeniorCitizenDriversRefresherCourse","setHasTakenASeniorCitizenDriversRefresherCourse"),"<html><body align=\"right\">has taken a senior citizen<br>drivers refresher course</body></html>",1);
		
		add(createIntEditor(driver,"getNumberOfAccidentsInvolvedIn","setNumberOfAccidentsInvolvedIn",0,30),"number of accidents involved in",2,40);
		add(createIntEditor(driver,"getNumberOfMovingViolationsInLastTwoYears","setNumberOfMovingViolationsInLastTwoYears",0,100),"<html><body align=\"right\">number of moving violations<br>in last two years</body></html>",2,40);
		add(createIntEditor(driver,"getNumberOfYearsWithUServ","setNumberOfYearsWithUServ",0,50),"number of years with UServ",2,40);
		
		// car editor
		activatePanel(tabbedPane,carPanel, "car details");
		add(createBooleanEditor(car,"isConvertible","setConvertible"),"is convertible",0);
		add(createSelectStringEditor(car,"getCategory","setCategory",CAR_CATEGORIES),"category",0);
		add(createIntEditor(car,"getAge","setAge",0,50),"age",0,40);
		add(createIntEditor(car,"getModelYear","setModelYear",1950,new GregorianCalendar().get(Calendar.YEAR)+1),"model year",0,80);
		add(createIntEditor(car,"getPrice","setPrice",1000,100000,100),"price",0,80);
		
		add(createBooleanEditor(car,"hasRollBar","setHasRollBar"),"has rollbar",1);
		add(createBooleanEditor(car,"hasDriversAirbag","setHasDriversAirbag"),"has drivers airbag",1);
		add(createBooleanEditor(car,"hasFrontPassengerAirbag","setHasFrontPassengerAirbag"),"has front passenger airbag",1);
		add(createBooleanEditor(car,"hasSidePanelAirbags","setHasSidePanelAirbags"),"has side panel airbags",1);
		add(createBooleanEditor(car,"hasAlarm","setHasAlarm"),"has alarm",1);

		// policy editor
		activatePanel(tabbedPane,policyPanel, "policy details");	
		add(createBooleanEditor(policy,"includesMedicalCoverage","setIncludesMedicalCoverage"),"includes medical coverage",0);
		add(createBooleanEditor(policy,"includesUninsuredMotoristCoverage","setIncludesUninsuredMotoristCoverage"),"includes uninsured motorist coverage",1);
		
		// output		
		tabbedPane = new JTabbedPane();
		tabbedPane.setBorder(
				BorderFactory.createCompoundBorder(
						BorderFactory.createEmptyBorder(3,3,3,3), 
						BorderFactory.createTitledBorder(" output "))
		);
		this.add(tabbedPane);
		
		// driver eligibility
		activatePanel(tabbedPane,driverEligibilityPanel, "driver eligibility");	
		
		resultPanel.setBorder(
				BorderFactory.createCompoundBorder(
						BorderFactory.createEmptyBorder(3,3,3,3), 
						BorderFactory.createTitledBorder(" results "))
		);
		addOut(this.txtDriverCategory,"driver category",0,100);
		addOut(this.txtDriverEligibility,"is eligible",0,40);
		addOut(this.txtHasTrainingCertification,"has training certification",0,40);
		addOut(this.txtHighRiskDriver,"is high risk driver",0,40);
		addOut(this.txtLongTermClient,"is long term client",0,40);
		addOut(this.multiOutPolicyEligibilityScore,"policy eligibility score",1,80);
		addOut(this.txtInsuranceEligibility,"insurance eligibility",1,200);
		
		// premium
		activatePanel(tabbedPane,policyPremiumPanel, "policy premium");		
		addOut(this.multiAdditionalPremium,"<html><body align=\"right\">additional<br/>premium</body></html>",0,80);
		addOut(this.txtBasePremium,"base premium",0,100); 
		addOut(this.multiAdditionalDriverPremium,"<html><body align=\"right\">additional<br/>driver<br/>premium</body></html>",1,80);
		addOut(this.multiPremiumDiscount,"<html><body align=\"right\">premium<br/>discounts</body></html>",2,80);
		
		// apply rules to initial settings
		this.applyRules();
	}
	
	private void activatePanel(JTabbedPane tabbedPane,JPanel panel,String name) {
		
		JPanel container = new JPanel(new FlowLayout(FlowLayout.CENTER));
		tabbedPane.add(new JScrollPane(container), name);
		container.add(panel);
		this.setPanel(panel);
	} 
	
	private void makeEqual() {
		int max = 0;
		for (int i:rowCounts) 
			max = Math.max(max,i);
		for (int i=0;i<rowCounts.length;i++)
			rowCounts[i]=max;
		
	}

	private void addSep(String label, int... cols) {
		GridBagConstraints c = new GridBagConstraints();
		c.ipadx = 5;
		c.ipady = 5;
		c.gridx = cols[0]*3;
		c.gridwidth = cols.length*3;
		c.anchor = c.WEST;
		c.fill = c.HORIZONTAL;
		JLabel jlabel = new JLabel();
		jlabel.setText(label);
		jlabel.setHorizontalAlignment(jlabel.CENTER);
		c.insets = new Insets(1,3,1,3);

		c.insets = new Insets(3,3,3,3);
		for (int col:cols)
			c.gridy = ++rowCounts[col];
		panel.add(new JLabel(),c);
		panel.add(jlabel,c);
		for (int col:cols)
			c.gridy = ++rowCounts[col];
		c.insets = new Insets(1,3,1,3);
		panel.add(new JSeparator(),c);
	
	}
	private void add(JComponent component,String label, int col) {
		add(component,label,col,-1);
	} 
	private void addOut(JComponent component,String label, int col) {
		addOut(component,label,col,-1);
	} 
	private void add(JComponent component,String label, int col, int width) {
		GridBagConstraints c = new GridBagConstraints();
		c.ipadx = 5;
		c.ipady = 5;
		c.insets = new Insets(3,3,3,3);
		c.gridx = 3*col;
		c.gridy = ++rowCounts[col];
		c.gridwidth = 1;
		c.gridheight = 1;
		c.anchor = c.EAST;
		c.fill = c.HORIZONTAL;
		JLabel jlabel = new JLabel();
		jlabel.setText(label);
		jlabel.setHorizontalAlignment(jlabel.RIGHT);
		panel.add(jlabel,c);
		c.anchor = c.WEST;
		c.gridx = 3*col+1;
		c.gridwidth = 2;
		c.weightx = 1;
		c.fill = c.NONE;
		if (width!=-1) {
			Dimension size = component.getPreferredSize();
			size.width=width;
			component.setPreferredSize(size);
			// component.setSize(size);
		}
		panel.add(component,c);
	} 
	private void addOut(JComponent component,final String label, int col, int width) {
		component.setName(label);
		GridBagConstraints c = new GridBagConstraints();
		c.ipadx = 5;
		c.ipady = 5;
		c.insets = new Insets(3,3,3,3);
		c.gridx = 3*col;
		c.gridy = ++rowCounts[col];
		c.gridwidth = 1;
		c.anchor = c.EAST;
		c.fill = c.NONE;
		JLabel jlabel = new JLabel();
		jlabel.setText(label);
		jlabel.setHorizontalAlignment(jlabel.RIGHT);
		panel.add(jlabel,c);
		c.anchor = c.WEST;
		c.gridx = 3*col+1;
		c.gridwidth = 1;
		c.weightx = 2;
		//c.fill = c.HORIZONTAL;
		if (width!=-1) {
			Dimension size = component.getPreferredSize();
			size.width=width;
			component.setPreferredSize(size);
			// component.setSize(size);
		}
		JPanel p = new JPanel(new BorderLayout(5,5));
		p.add(component,BorderLayout.CENTER);
		
		JButton butDetails = new JButton(" ? ");
		butDetails.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						List<DerivationLogEntry> logs = UServPanel.this.derivationLogs.get(label);
						displayUsedRules(logs);
					}
				}
		);
		butDetails.setBorder(null);
		p.add(butDetails,BorderLayout.EAST);
		panel.add(p,c);
	} 
	
	private void addOut(MultiValueResultView component,final String label, int col, int width) {
		component.setBorder(BorderFactory.createEtchedBorder());
		component.setName(label);
		GridBagConstraints c = new GridBagConstraints();
		c.ipadx = 5;
		c.ipady = 5;
		c.insets = new Insets(3,3,3,3);
		c.gridx = 3*col;
		c.gridy = ++rowCounts[col];
		c.gridwidth = 1;
		c.gridheight = 5;
		c.anchor = c.NORTHEAST;
		c.fill = c.NONE;
		JLabel jlabel = new JLabel();
		jlabel.setText(label);
		jlabel.setHorizontalAlignment(jlabel.RIGHT);
		panel.add(jlabel,c);
		c.anchor = c.WEST;
		c.gridx = 3*col+1;
		c.gridwidth = 2;
		c.weightx = 2;
		c.fill = c.VERTICAL;
		Dimension size = component.getPreferredSize();
		if (width!=-1) {
			size.width=width;
		}
		size.height=150;
		component.setPreferredSize(size);
		panel.add(component,c);
		
		for (int i=0;i<c.gridheight-1;i++) {
			++rowCounts[col];
		}

	}
	
	private void displayUsedRules(List<DerivationLogEntry> logs) {
		DerivationLogViewer.displayUsedRules(logs,this,kb);	
	}

	private JCheckBox createBooleanEditor(final Object bean, final String getter, final String setter) {
		final JCheckBox box = new JCheckBox();
		// set value
		try {
			Boolean value = (Boolean)readValue(bean,getter);
			box.setSelected(value);
		}
		catch (Exception x) {
			x.printStackTrace();
		}
		// listener
		box.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e) {
				writeValue(bean,setter,Boolean.TYPE,box.isSelected());
			}});
		
		return box;
	}
	
	private JComboBox createSelectStringEditor(final Object bean, final String getter, final String setter,String[] list) {
		final JComboBox box = new JComboBox(list);
		// set value
		try {
			String value = (String)readValue(bean,getter);
			box.setSelectedItem(value);
		}
		catch (Exception x) {
			x.printStackTrace();
		}
		// listener
		box.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e) {
				writeValue(bean,setter,String.class,box.getSelectedItem());
			}});
		
		return box;
	}
	
	private JTextField createStringEditor(final Object bean, final String getter, final String setter) {
		final JTextField txt = new JTextField();
		// set value
		try {
			String value = (String)readValue(bean,getter);
			txt.setText(value);
		}
		catch (Exception x) {
			x.printStackTrace();
		}
		// listener
		txt.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				writeValue(bean,setter,String.class,txt.getText());
			}
		});
		return txt;
	}
	
	private JSpinner createIntEditor(final Object bean, final String getter, final String setter, int min, int max) {
		return createIntEditor(bean,getter,setter,min,max,1);
	}
	
	private JSpinner createIntEditor(final Object bean, final String getter, final String setter, int min, int max,int step) {
		final JSpinner spinner = new JSpinner();

		// set value
		try {
			int value = (Integer)readValue(bean,getter);
			SpinnerNumberModel model = new SpinnerNumberModel(value, min, max, step); 
			spinner.setModel(model);
		}
		catch (Exception x) {
			x.printStackTrace();
		}
		// listener
		spinner.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e) {
				writeValue(bean,setter,Integer.TYPE,spinner.getValue());
			}});
		
		return spinner;
	}
	
	private Object readValue(Object bean,String getter) {
		try {
			return bean.getClass().getMethod(getter, new Class[]{}).invoke(bean,new Object[]{});
		}
		catch (Exception x) {
			x.printStackTrace();
		}
		return null;
	}
	private void writeValue(Object bean, String setter, Class type, Object value) {
		try {
			Method m = bean.getClass().getMethod(setter, new Class[]{type});
			m.invoke(bean,new Object[]{value});
		}
		catch (Exception x) {
			x.printStackTrace();
		}	
		// System.out.println("Setting " + bean + "#" + setter + " -> " + value);
		applyRules();
	}
	
	private void applyRules() {
		String driverCategory = "?";
		ResultSet<DriverCategory> rs1 = kb.getDriverCategory(driver);
		if (rs1.hasNext()) {
			driverCategory = rs1.next().category;
			this.derivationLogs.put(txtDriverCategory.getName(),rs1.getDerivationLog());
		}
		else {
			this.derivationLogs.put(txtDriverCategory.getName(),new ArrayList());
		}
		this.txtDriverCategory.setText(driverCategory);
		
		
		String driverEligibility = "no";
		ResultSet<DriverEligibility> rs2 = kb.getDriverEligibility(driver);
		if (rs2.hasNext()) {
			driverEligibility = "yes";
			this.derivationLogs.put(txtDriverEligibility.getName(),rs2.getDerivationLog());
		}
		else {
			this.derivationLogs.put(txtDriverEligibility.getName(),new ArrayList());
		}
		this.txtDriverEligibility.setText(driverEligibility);
		
		
		String trainingCertification = "no";
		ResultSet<TrainedDriver> rs3 = kb.hasTrainingCertification(driver);
		if (rs3.hasNext()) {
			trainingCertification = "yes";
			this.derivationLogs.put(txtHasTrainingCertification.getName(),rs3.getDerivationLog());
		}
		else {
			this.derivationLogs.put(txtHasTrainingCertification.getName(),new ArrayList());
		}
		this.txtHasTrainingCertification.setText(trainingCertification);
		
			
		String highRiskDriver = "no";
		ResultSet<HighRiskDriver> rs4 = kb.isHighRiskDriver(driver);
		if (rs4.hasNext()) {
			highRiskDriver = "yes";
			this.derivationLogs.put(txtHighRiskDriver.getName(),rs4.getDerivationLog());
		}
		else {
			this.derivationLogs.put(txtHighRiskDriver.getName(),new ArrayList());
		};
		this.txtHighRiskDriver.setText(highRiskDriver);

		ResultSet<PolicyEligibilityScore> rs5 = kb.getPolicyEligibilityScore(car, driver);
		this.multiOutPolicyEligibilityScore.setResultSet(rs5);
		
		String insuranceEligibility = "?";
		ResultSet<InsuranceEligibility> rs6 = kb.getPolicyEligibility(car, driver);
		if (rs6.hasNext())
			insuranceEligibility = rs6.next().status;
		this.txtInsuranceEligibility.setText(insuranceEligibility);
		this.derivationLogs.put(txtInsuranceEligibility.getName(),rs6.getDerivationLog());
		
		String basePremium = "?";
		ResultSet<BasePremium> rs7 = kb.getBasePremium(car);
		if (rs7.hasNext()) {
			basePremium = ""+rs7.next().premium;
			this.derivationLogs.put(txtBasePremium.getName(),rs7.getDerivationLog());
		}
		else {
			this.derivationLogs.put(txtBasePremium.getName(),new ArrayList());
		}
		this.txtBasePremium.setText(basePremium);
		
		ResultSet<AdditionalDriverPremium> rs8 = kb.getAdditionalDriverPremium(driver);
		this.multiAdditionalDriverPremium.setResultSet(rs8);
		
		ResultSet<AdditionalPremium> rs9 = kb.getAdditionalPremium(policy, car);
		this.multiAdditionalPremium.setResultSet(rs9);
		
		ResultSet<PremiumDiscount> rs10 = kb.getPremiumDiscount(car);
		this.multiPremiumDiscount.setResultSet(rs10);
		
		String longTermClient = "no";
		ResultSet<LongTermClient> rs11 = kb.isLongTermClient(driver);
		if (rs11.hasNext()) {
			longTermClient = "yes";
			this.derivationLogs.put(this.txtLongTermClient.getName(),rs11.getDerivationLog());
		}
		else {
			this.derivationLogs.put(txtLongTermClient.getName(),new ArrayList());
		}
		this.txtLongTermClient.setText(longTermClient);
	}
	
	private void setPanel(JPanel pane) {
		this.panel = pane;
		rowCounts = new int[]{-1,-1,-1,-1,-1,-1,-1,-1,-1};
	}
	private UservRules loadRules() {
		return doLoadRules(new File("data/rules/userv.take"));
	}
	private UservRules doLoadRules(File source) {
		KnowledgeBaseManager<UservRules> kbm = new KnowledgeBaseManager<UservRules>();

		Bindings bindings = new SimpleBindings();
		bindings.put("HighTheftProbabilityAutoList", HighTheftProbabilityAutoList.getList());
		bindings.put("CurrentYear",new GregorianCalendar().get(Calendar.YEAR));
		bindings.put("NextYear",new GregorianCalendar().get(Calendar.YEAR)+1);
		
		Bindings factStores = new SimpleBindings();
		factStores.put("DP_00x", new SpecialLocationsSource());
		factStores.put("DRCx", new DUIConvictionInfoSource());
		
		FileInputStream in = null;
		try {
			in = new FileInputStream(source);
			return kbm.getKnowledgeBase(
					UservRules.class, 
					new ScriptKnowledgeSource(in),
					bindings,
					factStores
			); 
		}
		catch (Exception x) {
			x.printStackTrace();
			return null;
		}
		finally {
			try {in.close();}
			catch (Exception x){}
		}
	}
	// load the rules from a file
	void loadRules(File f) {
		UservRules kb2 = doLoadRules(f);
		if (kb2==null) {
			JOptionPane.showMessageDialog(this,"Error loading rules, see console for details","", JOptionPane.ERROR_MESSAGE);
		}
		else {
			this.kb = kb2;
			applyRules();
			JOptionPane.showMessageDialog(this,"New rules loaded","", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	UservRules getKB() {
		return kb;
	}

}
