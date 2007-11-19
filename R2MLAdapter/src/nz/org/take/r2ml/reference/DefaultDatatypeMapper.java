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
package nz.org.take.r2ml.reference;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;

import nz.org.take.r2ml.DatatypeMapper;

public class DefaultDatatypeMapper implements DatatypeMapper {
	
	static Map<QName, Class> types = new HashMap<QName, Class>();

	public DefaultDatatypeMapper () {
		super();
		setType(new QName(javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI, "boolean"), Boolean.class);
		setType(new QName(javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI, "string"), String.class);
		setType(new QName(javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI, "QName"), QName.class);
		setType(new QName(javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI, "date"), Date.class);
		setType(new QName(javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI, "time"), Date.class);
		setType(new QName(javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI, "dateTime"), Date.class);
		setType(new QName(javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI, "float"), Double.class);
		setType(new QName(javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI, "double"), Double.class);
		setType(new QName(javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI, "decimal"), BigDecimal.class);
		setType(new QName(javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI, "integer"), Integer.class);
//		setType(new QName(javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI, ""), .class);
		
		
	}
	
	/* (non-Javadoc)
	 * @see nz.org.take.r2ml.DatatypeMapper#getType(javax.xml.namespace.QName)
	 */
	public Class getType (QName fullName) {
		return types.get(fullName);
	}
	
	/* (non-Javadoc)
	 * @see nz.org.take.r2ml.DatatypeMapper#getType(java.lang.String)
	 */
	public Class getType (String localName) {
		return getType(new QName("", localName));
	}
	
	/* (non-Javadoc)
	 * @see nz.org.take.r2ml.DatatypeMapper#setType(javax.xml.namespace.QName, java.lang.Class)
	 */
	public void setType (QName key, Class value) {
		types.put(key, value);
	}

	/* (non-Javadoc)
	 * @see nz.org.take.r2ml.DatatypeMapper#setType(java.lang.String, java.lang.Class)
	 */
	public void setType(String name, Class clazz) {
		setType(new QName("", name), clazz);
	}
	
}