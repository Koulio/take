/**
 * 
 */
package nz.org.take.strelka.preferences;

import java.beans.PropertyChangeListener;

import de.uni_paderborn.fujaba.preferences.AbstractPreferences;
import de.uni_paderborn.fujaba.preferences.PreferencesProperties;

/**
 * @author Bastian Schenke (bastian.schenke(at)googlemail.com)
 * 
 */
public class TakePluginPreferences extends AbstractPreferences {

	public final static String TAKE_PROPERTY_LOCATION = "nz/org/take"
			+ "/strelka/take4strelka.properties";

	private static TakePluginPreferences preferences = null;

	private static final String PROPERTY_PREFIX = "take4strelka.";

	public static final String ASSOCIATION_RESLOLV_POLICY = PROPERTY_PREFIX
			+ "associationResolvPolicy";

	public static final String TMP_FILE = PROPERTY_PREFIX + "tmpFile";

	public static final String TARGET_DIRECTORY = PROPERTY_PREFIX
			+ "targetDirectory";

	public static final String DOMAIN_CLASSES = PROPERTY_PREFIX
			+ "domainClasses";

	public static final String DOMAIN_DATATYPE_MAPPER = PROPERTY_PREFIX
			+ "datatypeMapper";

	public static final String DOMAIN_NAME_MAPPER = PROPERTY_PREFIX
			+ "nameMapper";

	public static final String DOMAIN_QUERY_GENERATOR = PROPERTY_PREFIX
			+ "queryGenerator";

	public static final String KNOWLEDGEBASE_NAME = PROPERTY_PREFIX
			+ "knowledgebaseName";

	public static final String KNOWLEDGEBASE_PACKAGE_NAME = PROPERTY_PREFIX
			+ "packageName";

	/*
	 * default values for all preferences neccessary for the r2ml adapter and
	 * take compiler
	 */
	private static final String DEFAULT_ASSOCIATION_RESOLV_PROPERTY = "nz.org"
			+ ".take.r2ml.reference.AssociationAsPropertyResolvPolicy";

	private static final String DEFAULT_TMP_FILE = System
			.getProperty("user.home")
			+ "/take4strelka/.tmp/tmp.xml";

	private static final String DEFAULT_TARGET_DIRECTORY = System
			.getProperty("user.home")
			+ "/take4strelka/generated";

	private static final String DEFAULT_DOMAIN_CLASSES = "./plugins"
			+ "/take4strelka/lib/UServ4take.jar";

	private static final String DEFAULT_DOMAIN_DATATYPE_MAPPER = "example"
			+ ".nz.org.take.r2ml.userv.domain.UServDatatypeMapper";

	private static final String DEFAULT_DOMAIN_NAME_MAPPER = "example.nz"
			+ ".org.take.r2ml.userv.domain.UServNameMapper";

	private static final String DEFAULT_DOMAIN_QUERY_GENERATOR = "example.nz.org.take.r2ml.userv.domain.UServQueryGenerator";

	private static final String DEFAULT_KNOWLEDGEBASE_NAME = "UServKB";

	private static final String DEFAULT_KNOWLEDGEBASE_PACKAGE_NAME = "example.userv";

	/**
	 * This class is a singleton.
	 */
	private TakePluginPreferences() {

	}

	/**
	 * @return the singleton TakePluginPreferences
	 */
	public static TakePluginPreferences get() {
		if (preferences == null)
			preferences = new TakePluginPreferences();
		PreferencesProperties properties = PreferencesProperties
				.get(TAKE_PROPERTY_LOCATION);

		if (properties.getSetting(TMP_FILE) == null)
			preferences.setDefaults();
		return preferences;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.uni_paderborn.fujaba.preferences.AbstractPreferences#setDefaults()
	 */
	@Override
	public void setDefaults() {
		PreferencesProperties properties = PreferencesProperties
				.get(TAKE_PROPERTY_LOCATION);

		properties.putSetting(ASSOCIATION_RESLOLV_POLICY,
				DEFAULT_ASSOCIATION_RESOLV_PROPERTY);

		properties.putSetting(TMP_FILE, DEFAULT_TMP_FILE);
		properties.putSetting(TARGET_DIRECTORY, DEFAULT_TARGET_DIRECTORY);

		properties.putSetting(DOMAIN_CLASSES, DEFAULT_DOMAIN_CLASSES);
		properties.putSetting(DOMAIN_DATATYPE_MAPPER,
				DEFAULT_DOMAIN_DATATYPE_MAPPER);
		properties.putSetting(DOMAIN_NAME_MAPPER, DEFAULT_DOMAIN_NAME_MAPPER);
		properties.putSetting(DOMAIN_QUERY_GENERATOR,
				DEFAULT_DOMAIN_QUERY_GENERATOR);

		properties.putSetting(KNOWLEDGEBASE_NAME, DEFAULT_KNOWLEDGEBASE_NAME);
		properties.putSetting(KNOWLEDGEBASE_PACKAGE_NAME,
				DEFAULT_KNOWLEDGEBASE_PACKAGE_NAME);
	}

	/**
	 * @see de.uni_paderborn.fujaba.preferences.AbstractPreferences#addPropertyChangeListener(java.lang.String,
	 *      java.beans.PropertyChangeListener)
	 */
	@Override
	public void addPropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		PreferencesProperties.get(TAKE_PROPERTY_LOCATION)
				.addPropertyChangeListener(propertyName, listener);
	}

	/**
	 * @see de.uni_paderborn.fujaba.preferences.AbstractPreferences#removePropertyChangeListener(java.lang.String,
	 *      java.beans.PropertyChangeListener)
	 */
	@Override
	public void removePropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		PreferencesProperties.get(TAKE_PROPERTY_LOCATION)
				.removePropertyChangeListener(propertyName, listener);
	}

	/** ********************************************************************** */
	/** ***** getters/setters for all properties ***************************** */
	/** ********************************************************************** */

	/**
	 * @param dirPath
	 *            the target directory for the take compiler
	 */
	public void setTargetDirectory(String dirPath) {
		PreferencesProperties properties = PreferencesProperties
				.get(TAKE_PROPERTY_LOCATION);
		if (!properties.getSetting(TARGET_DIRECTORY).equals(dirPath)) {
			setModified(true);
			properties.putSetting(TARGET_DIRECTORY, dirPath);
		}
	}

	/**
	 * @param tmpFile
	 *            the path and name of the tmpFile
	 */
	public void setTmpFile(String tmpFile) {
		PreferencesProperties properties = PreferencesProperties
				.get(TAKE_PROPERTY_LOCATION);
		if (!properties.getSetting(TMP_FILE).equals(tmpFile)) {
			properties.putSetting(TARGET_DIRECTORY, tmpFile);
			setModified(true);
		}
	}

	/**
	 * @param domainClasses
	 *            the path and name of the jar-file containing the classes of
	 *            the domain model
	 */
	public void setDomainClasses(String domainClasses) {
		PreferencesProperties properties = PreferencesProperties
				.get(TAKE_PROPERTY_LOCATION);
		if (!properties.getSetting(DOMAIN_CLASSES).equals(domainClasses)) {
			properties.putSetting(DOMAIN_CLASSES, domainClasses);
			setModified(true);
		}
	}

	public void setDatatypeMapperClazz(String newValue) {
		PreferencesProperties properties = PreferencesProperties
				.get(TAKE_PROPERTY_LOCATION);
		if (!properties.getSetting(DOMAIN_DATATYPE_MAPPER).equals(newValue)) {
			properties.putSetting(DOMAIN_DATATYPE_MAPPER, newValue);
			setModified(true);
		}
	}

	public void setNameMapper(String newValue) {
		PreferencesProperties properties = PreferencesProperties
				.get(TAKE_PROPERTY_LOCATION);
		if (!properties.getSetting(DOMAIN_NAME_MAPPER).equals(newValue)) {
			properties.putSetting(DOMAIN_NAME_MAPPER, newValue);
			setModified(true);
		}
	}

	public void setQueryGenerator(String newValue) {
		PreferencesProperties properties = PreferencesProperties
				.get(TAKE_PROPERTY_LOCATION);
		if (!properties.getSetting(DOMAIN_QUERY_GENERATOR).equals(newValue)) {
			properties.putSetting(DOMAIN_QUERY_GENERATOR, newValue);
			setModified(true);
		}
	}

	public void setPackageName(String newValue) {
		PreferencesProperties properties = PreferencesProperties
				.get(TAKE_PROPERTY_LOCATION);
		if (!newValue.equals(properties.getSetting(KNOWLEDGEBASE_PACKAGE_NAME))) {
			properties.putSetting(KNOWLEDGEBASE_PACKAGE_NAME, newValue);
			setModified(true);
		}
	}

	public void setKnowledgeBaseName(String newValue) {
		PreferencesProperties properties = PreferencesProperties
				.get(TAKE_PROPERTY_LOCATION);
		if (!newValue.equals(properties.getSetting(KNOWLEDGEBASE_NAME))) {
			properties.putSetting(KNOWLEDGEBASE_NAME, newValue);
			setModified(true);
		}
	}

	/**
	 * @return the target directory
	 */
	public String getTargetDirectory() {
		PreferencesProperties properties = PreferencesProperties
				.get(TAKE_PROPERTY_LOCATION);
		return properties.getSetting(TARGET_DIRECTORY);
	}

	/**
	 * @return the name of the temp-file
	 */
	public String getTmpFile() {
		PreferencesProperties properties = PreferencesProperties
				.get(TAKE_PROPERTY_LOCATION);
		return properties.getSetting(TMP_FILE);
	}

	/**
	 * @return the path and name of the jar-file containing the classes of the
	 *         domain model
	 */
	public String getDomainCLasses() {
		PreferencesProperties properties = PreferencesProperties
				.get(TAKE_PROPERTY_LOCATION);
		return properties.getSetting(DOMAIN_CLASSES);
	}

	public String getDatatypeMapperClazz() {
		PreferencesProperties properties = PreferencesProperties
				.get((TAKE_PROPERTY_LOCATION));
		return properties.getSetting(DOMAIN_DATATYPE_MAPPER);
	}

	public String getNameMapperClazz() {
		PreferencesProperties properties = PreferencesProperties
				.get((TAKE_PROPERTY_LOCATION));
		return properties.getSetting(DOMAIN_NAME_MAPPER);
	}

	public String getQueryGenerator() {
		PreferencesProperties properties = PreferencesProperties
				.get(TAKE_PROPERTY_LOCATION);
		return properties.getSetting(DOMAIN_QUERY_GENERATOR);
	}

	public String getPackageName() {
		PreferencesProperties properties = PreferencesProperties
				.get((TAKE_PROPERTY_LOCATION));
		return properties.getSetting(KNOWLEDGEBASE_PACKAGE_NAME);
	}

	public String getKnowledgeBaseName() {
		PreferencesProperties properties = PreferencesProperties
				.get((TAKE_PROPERTY_LOCATION));
		return properties.getSetting(KNOWLEDGEBASE_NAME);
	}

}
