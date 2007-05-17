package nz.org.take.r2ml;

import javax.xml.namespace.QName;

/**
 * Defines a Mapping from qualified xml names to Java datatypes. Each type is identified by a <code>javax.xml.namespace.QName</code>.
 * 
 * @author Bastian Schenke (bastian.schenke@googlemail.com)
 *
 */
public interface DatatypeMapper {

	/**
	 * Get the Class that represents the provided qualified name.
	 * @param key a qualified name
	 * @return the 
	 */
	public Class getType(QName key);

	/**
	 * @param key
	 * @param value
	 */
	public void setType(QName key, Class value);

}