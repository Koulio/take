/**
 * 
 */
package nz.org.take.strelka.preferences.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import nz.org.take.strelka.TakePlugin;
import nz.org.take.strelka.preferences.TakePluginPreferences;

import de.uni_paderborn.fujaba.preferences.gui.PreferencesPanel;

/**
 * The JPanel for the preferences of the take plugin.
 * 
 * @author Bastian Schenke (bastian.schenke(at)googlemail.com)
 * 
 */
public class TakePluginPreferencesPanel extends PreferencesPanel implements
		ActionListener {

	private static final String CHOOSE_DOMAIN_JAR_ACTION = "ChooseDomainJarAction";

	private static final String CHOOSE_TARGET_DIRECTORY_ACTION = "chooseTargetDirectoryAction";

	private static final String CHOOSE_TEMP_FILE_ACTION = "ChooseTempFile";

	private static final long serialVersionUID = 3122792179981815674L;

	private static final String tabName = "take4strelka";

	private static final int TEXTFIELD_SIZE = 25;
	
	private JTextField tmpFileTfd;
	private JTextField targetDirTfd;
	private JTextField domainJarTfd;
	private JTextField domainDatatypeMapperTfd;
	private JTextField domainNameMapperTfd;
	private JTextField domainQueryGeneratorTfd;
	private JTextField kbNameTfd;
	private JTextField kbPackageTfd;

	/**
	 * 
	 */
	public TakePluginPreferencesPanel() {

		setLayout(new BorderLayout());

		add(compilerPrefUI(), BorderLayout.NORTH);

		add(domainPrefUI(), BorderLayout.CENTER);

		add(kbPrefUI(), BorderLayout.EAST);
		
	}

	/**
	 * @return the JPanel for all preferences concerning the knowledge base  
	 */
	private JPanel kbPrefUI() {
		/** ***************************** */
		/** UI for knowledgebase options  */
		/** ***************************** */
		JPanel knowledgeBasePanel = new JPanel();
		knowledgeBasePanel.setLayout(new BorderLayout());
		knowledgeBasePanel.setBorder(new TitledBorder("knowledge base options"));
		
		// knowledgebase name
		JPanel kbNamePanel = new JPanel();
		kbNamePanel.setLayout(new BorderLayout());
		
		JLabel kbNameLbl = new JLabel();
		kbNameLbl.setText("Name of the generated KnowledgeBase");
		kbNamePanel.add(kbNameLbl, BorderLayout.NORTH);
		
		kbNameTfd = new JTextField();
		kbNameTfd.setColumns(TEXTFIELD_SIZE);
		kbNamePanel.add(kbNameTfd, BorderLayout.SOUTH);
		
		knowledgeBasePanel.add(kbNamePanel, BorderLayout.NORTH);
		
		// package name
		JPanel kbPackagePanel = new JPanel();
		kbPackagePanel.setLayout(new BorderLayout());
		
		JLabel kbPackageLbl = new JLabel();
		kbPackageLbl.setText("Package of the generated code");
		kbPackagePanel.add(kbPackageLbl, BorderLayout.NORTH);
		
		kbPackageTfd = new JTextField();
		kbPackageTfd.setColumns(TEXTFIELD_SIZE);
		kbPackagePanel.add(kbPackageTfd, BorderLayout.SOUTH);
		
		knowledgeBasePanel.add(kbPackagePanel, BorderLayout.SOUTH);
		
		// dummy
		JPanel dummy = new JPanel();
		//dummy.add(new JLabel("   "));
		knowledgeBasePanel.add(dummy, BorderLayout.CENTER);
		return knowledgeBasePanel;
	}

	/**
	 * @return the JPanel for all preferences concerning the domain model
	 */
	private JPanel domainPrefUI() {
		/** ********************************** */
		/** UI for domain specific preferences */
		/** ********************************** */
		// archive with domain classes
		JPanel domainOptionsPanel = new JPanel();
		domainOptionsPanel.setLayout(new BorderLayout());
		domainOptionsPanel
				.setBorder(new TitledBorder("domain specific options"));

		JPanel domainJarPanel = new JPanel();
		domainJarPanel.setLayout(new BorderLayout());
		
		JLabel domainJarLbl = new JLabel("Choose the jar file containing all domain classes.");
		domainJarPanel.add(domainJarLbl, BorderLayout.NORTH);
		
		domainJarTfd = new JTextField();
		domainJarTfd.setColumns(TEXTFIELD_SIZE);
		domainJarPanel.add(domainJarTfd, BorderLayout.CENTER);
		
		JButton domainJarBtn = new JButton("Browse...");
		domainJarBtn.setActionCommand(CHOOSE_DOMAIN_JAR_ACTION);
		domainJarBtn.addActionListener(this);
		domainJarPanel.add(domainJarBtn, BorderLayout.EAST);

		domainOptionsPanel.add(domainJarPanel, BorderLayout.NORTH);

		JPanel domainOptionsCenterPanel = new JPanel();
		domainOptionsCenterPanel.setLayout(new BorderLayout());
		domainOptionsPanel.add(domainOptionsCenterPanel, BorderLayout.CENTER);
		// class of datatypemapper
		JPanel domainDatatypeMapperPanel = new JPanel();
		domainDatatypeMapperPanel.setLayout(new BorderLayout());

		JLabel domainDatatypeMapperLbl = new JLabel("DatatypeMaper (fully qualified class name)");
		
		domainDatatypeMapperTfd = new JTextField();
		domainDatatypeMapperTfd.setColumns(TEXTFIELD_SIZE);
		domainDatatypeMapperPanel.add(domainDatatypeMapperTfd, BorderLayout.CENTER);
		
		domainOptionsCenterPanel.add(domainDatatypeMapperPanel, BorderLayout.NORTH);
		
		// class of query generator
		JPanel domainQueryGeneratorPanel = new JPanel();
		domainQueryGeneratorPanel.setLayout(new BorderLayout());
		
		JLabel domainQueryGeneratorLbl = new JLabel("QueryGenerator (fully qualified class name)");
		domainQueryGeneratorPanel.add(domainQueryGeneratorLbl, BorderLayout.NORTH);
				
		domainQueryGeneratorTfd = new JTextField();
		domainQueryGeneratorTfd.setColumns(TEXTFIELD_SIZE);
		domainQueryGeneratorPanel.add(domainQueryGeneratorTfd, BorderLayout.CENTER);
		
		domainOptionsCenterPanel.add(domainQueryGeneratorPanel, BorderLayout.SOUTH);

		domainDatatypeMapperPanel.add(domainDatatypeMapperLbl, BorderLayout.NORTH);

		// class of namemapper
		JPanel domainNameMapperPanel = new JPanel();
		domainNameMapperPanel.setLayout(new BorderLayout());

		JLabel domainNameMapperLbl = new JLabel("NameMapper (fully qualified class name)");
		domainNameMapperPanel
				.add(domainNameMapperLbl, BorderLayout.NORTH);
		
		domainNameMapperTfd = new JTextField();
		domainNameMapperTfd.setColumns(TEXTFIELD_SIZE);
		domainNameMapperPanel.add(domainNameMapperTfd, BorderLayout.CENTER);
		
		domainOptionsPanel.add(domainNameMapperPanel, BorderLayout.SOUTH);
		return domainOptionsPanel;
	}

	/**
	 * @return  the JPanel for all preferences concerning the compiler
	 */
	private JPanel compilerPrefUI() {
		/** *************************** */
		/** UI for compiler preferences */
		/** *************************** */

		JPanel compilerPreferencesPanel = new JPanel();
		compilerPreferencesPanel
				.setBorder(new TitledBorder("compiler options"));

		// temp file
		JPanel tmpFilePanel = new JPanel();
		tmpFilePanel.setLayout(new BorderLayout());

		JLabel tmpFileLbl = new JLabel(
				"Select file for temporary R2ML content:");
		tmpFilePanel.add(tmpFileLbl, BorderLayout.NORTH);

		tmpFileTfd = new JTextField();
		tmpFileTfd.setColumns(TEXTFIELD_SIZE);
		tmpFilePanel.add(tmpFileTfd, BorderLayout.WEST);

		JButton tmpFileChooseBtn = new JButton("Browse...");
		tmpFileChooseBtn.addActionListener(this);
		tmpFileChooseBtn.setActionCommand(CHOOSE_TEMP_FILE_ACTION);
		tmpFilePanel.add(tmpFileChooseBtn, BorderLayout.CENTER);
		compilerPreferencesPanel.add(tmpFilePanel);

		// target directory
		JPanel targetDirPanel = new JPanel();
		targetDirPanel.setLayout(new BorderLayout());

		JLabel targetDirLbl = new JLabel(
				"Select target directory for generated code:");
		targetDirPanel.add(targetDirLbl, BorderLayout.NORTH);

		targetDirTfd = new JTextField();
		targetDirTfd.setColumns(TEXTFIELD_SIZE);
		targetDirPanel.add(targetDirTfd, BorderLayout.CENTER);

		JButton targetDirChooseBtn = new JButton("Browse...");
		targetDirChooseBtn.addActionListener(this);
		targetDirChooseBtn.setActionCommand(CHOOSE_TARGET_DIRECTORY_ACTION);
		targetDirPanel.add(targetDirChooseBtn, BorderLayout.EAST);

		compilerPreferencesPanel.add(targetDirPanel);
		return compilerPreferencesPanel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.uni_paderborn.fujaba.preferences.gui.PreferencesPanel#getPreferredTabName()
	 */
	@Override
	public String getPreferredTabName() {
		return tabName;
	}

	@Override
	public void okPressed() {

		// check file and directory properties
		checkTmpFile();
		checkTargetDir();
		checkJarArchive();
		
		// set "uncheckable" properties
		TakePluginPreferences.get().setDatatypeMapperClazz(domainDatatypeMapperTfd.getText());
		TakePluginPreferences.get().setNameMapper(domainNameMapperTfd.getText());
		TakePluginPreferences.get().setKnowledgeBaseName(kbNameTfd.getText());
		TakePluginPreferences.get().setPackageName(kbPackageTfd.getText());

		// and so on...

	}

	@Override
	public void setDefaults() {
		TakePluginPreferences.get().setDefaults();
		setPreferences();
	}

	@Override
	public void setPreferences() {
		// update UI elements
		// copy preferences to gui elements
		TakePluginPreferences prefs = TakePluginPreferences.get();

		tmpFileTfd.setText(prefs.getTmpFile());
		targetDirTfd.setText(prefs.getTargetDirectory());
		domainJarTfd.setText(prefs.getDomainCLasses());
		domainDatatypeMapperTfd.setText(prefs.getDatatypeMapperClazz());
		domainQueryGeneratorTfd.setText(prefs.getQueryGenerator());
		domainNameMapperTfd.setText(prefs.getNameMapperClazz());
		kbNameTfd.setText(prefs.getKnowledgeBaseName());
		kbPackageTfd.setText(prefs.getPackageName());
		
		// and so on...

	}

	public void actionPerformed(ActionEvent e) {
		JOptionPane.showMessageDialog(this, "Action occured: "
				+ e.getActionCommand());
		if (CHOOSE_TEMP_FILE_ACTION.equals(e.getActionCommand())) {
			chooseFileAction(tmpFileTfd);
		} else if (CHOOSE_TARGET_DIRECTORY_ACTION.equals(e.getActionCommand())) {
			chooseDirAction(targetDirTfd);
		} else if (CHOOSE_DOMAIN_JAR_ACTION.equals(e.getActionCommand())) {
			chooseFileAction(domainJarTfd);
		} else {
			TakePlugin.logger.warn("Unknown action command: "
					+ e.getActionCommand());
		}
	}

	private void chooseFileAction(JTextField destination) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		File currentDir = null;
		try {
			currentDir = new File(destination.getText());
		} catch (NullPointerException npe) {
			// do nothing
		}
		fileChooser.setCurrentDirectory(currentDir);
		int returnVal = fileChooser.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			try {
				destination
						.setText(fileChooser.getSelectedFile().getCanonicalPath());
			} catch (IOException e1) {
			}
		}
	}

	private void chooseDirAction(JTextField destination) {
		JFileChooser dirChooser = new JFileChooser();
		dirChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		File currentDir = null;
		try {
			currentDir = new File(destination.getText());
		} catch (NullPointerException npe) {
			// do nothing
		}
		dirChooser.setCurrentDirectory(currentDir);
		int returnVal = dirChooser.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			try {
				destination.setText(dirChooser.getSelectedFile()
						.getCanonicalPath());
			} catch (IOException e1) {
			}
		}
	}

	/**
	 * @param prefs
	 */
	private void checkTargetDir() {
		// check and write target directory preference
		File newDir = new File(targetDirTfd.getText());
		if (newDir.mkdirs())
			TakePlugin.logger.info("Directory for generated code created.");
		if (newDir != null && newDir.isDirectory() && newDir.canWrite()) {
			TakePluginPreferences.get().setTmpFile(targetDirTfd.getText());
		} else {
			JOptionPane
					.showMessageDialog(
							this,
							"The selected directory is not suitable as a target"
									+ " directory for code generation (can not be created,"
									+ " is not a directory or you have no write permission).",
							"Error: Unsuitable target directory",
							JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * @param prefs
	 */
	private void checkTmpFile() {
		// check and write temp file preference
		File newFile = new File(tmpFileTfd.getText());
		try {
			if (newFile.getParentFile().mkdirs())
				TakePlugin.logger.info("Directory for temporary file for generated R2ML markup created");
			if (newFile.createNewFile())
				TakePlugin.logger.info("Temporary file for generated R2ML markup created");
		} catch (IOException e) {
			TakePlugin.logger.warn("Unable to create temporary file for R2ML markup.", e);
		}
		//temporary file for generated R2ML markup created
		if (newFile != null && !newFile.isDirectory() && newFile.canWrite()) {
			TakePluginPreferences.get().setTmpFile(tmpFileTfd.getText());
		} else {
			JOptionPane.showMessageDialog(this,
					"The file you selected as a temporary file is"
							+ " not suitable (can not be created, is a"
							+ " directory or you have no write permission).",
					"Error: Unsuitable temp file", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void checkJarArchive() {
		// check and write jar archive
		File newFile = new File(domainJarTfd.getText());
		try {
			if (newFile.getParentFile().mkdirs())
				TakePlugin.logger.info("Directory for temporary file for generated R2ML markup created");
			if (newFile.createNewFile())
				TakePlugin.logger.info("Temporary file for generated R2ML markup created");
		} catch (IOException e) {
			TakePlugin.logger.warn("Unable to create temporary file for R2ML markup.", e);
		}
		
		if (newFile == null || !newFile.exists()) {
			JOptionPane.showMessageDialog(this,
					"The selected jar file doent exist!",
					"Error: Wrong jar file!", JOptionPane.ERROR_MESSAGE);
		} else {
			TakePluginPreferences.get().setDomainClasses(domainJarTfd.getText());
		}
		
	}

}
