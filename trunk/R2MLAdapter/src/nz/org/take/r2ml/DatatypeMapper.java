/*
 * Copyright 2007 Bastian Schenke Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
 */
package nz.org.take.r2ml;

import javax.xml.namespace.QName;

/**
 * Defines a Mapping from qualified xml names to Java datatypes. Each type is
 * identified by a <code>javax.xml.namespace.QName</code>.
 * 
 * @author Bastian Schenke (bastian.schenke@googlemail.com)
 * 
 */
public interface DatatypeMapper {

	/**
	 * Get the Class that is associated to the provided qualified name.
	 * 
	 * @param classID
	 *            a qualified name
	 * @return the java type associated with the qualified name
	 *         <code>classID</code> or <code>null</code>
	 * @throws R2MLException if there is no datatype for the class-id
	 */
	public Class getType(QName classID) throws R2MLException;

	/**
	 * Get the Class that is associated to the provided local name.
	 * 
	 * @param classID
	 *            a local name
	 * @return the java type associated with the local name
	 *         <code>loacalName</code> or <code>null</code> if there is no
	 *         type associated.
	 * @throws R2MLException if there is no datatype for the class-id
	 */
	public Class getType(String localName) throws R2MLException;

	/**
	 * @param classID
	 *            a fully qualified type name
	 * @param type
	 *            a java type
	 */
	public void setType(QName classID, Class type);

	/**
	 * @param localName
	 *            a local type name
	 * @param type
	 *            a java type
	 */
	public void setType(String localName, Class type);

}