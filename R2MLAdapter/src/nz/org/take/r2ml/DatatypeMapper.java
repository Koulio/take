/*
 * Copyright (C) 2007 Bastian Schenke (bastian.schenke(at)gmail.com) and 
 * <a href="http://www-ist.massey.ac.nz/JBDietrich" target="_top">Jens Dietrich</a>
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
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
	 */
	public Class getType(QName classID);

	/**
	 * Get the Class that is associated to the provided local name.
	 * 
	 * @param classID
	 *            a local name
	 * @return the java type associated with the local name
	 *         <code>loacalName</code> or <code>null</code> if there is no
	 *         type associated.
	 */
	public Class getType(String localName);

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