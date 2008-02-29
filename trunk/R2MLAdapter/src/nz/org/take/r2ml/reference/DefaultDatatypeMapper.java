/*
 * Copyright 2007 Bastian Schenke Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
 */
package nz.org.take.r2ml.reference;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;

import nz.org.take.r2ml.DatatypeMapper;
import nz.org.take.r2ml.R2MLException;

public class DefaultDatatypeMapper implements DatatypeMapper {
	
	static Map<QName, Class> types = new HashMap<QName, Class>();

	public DefaultDatatypeMapper () {
		super();
		setType(new QName(javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI, "boolean"), Boolean.class);
		setType(new QName(javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI, "integer"), Long.class);
		setType(new QName(javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI, "float"), Double.class);
		setType(new QName(javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI, "double"), Double.class);
		setType(new QName(javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI, "decimal"), BigDecimal.class);
		setType(new QName(javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI, "string"), String.class);
		setType(new QName(javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI, "QName"), QName.class);
		
		setType(new QName("", "xs:boolean"), Boolean.class);
		setType(new QName("", "xs:integer"), Long.class);
		setType(new QName("", "xs:float"), Double.class);
		setType(new QName("", "xs:double"), Double.class);
		setType(new QName("", "xs:decimal"), BigDecimal.class);
		setType(new QName("", "xs:string"), String.class);
		setType(new QName("", "xs:QName"), QName.class);
		
//		setType(new QName(javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI, "date"), Date.class);
//		setType(new QName(javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI, "time"), Date.class);
//		setType(new QName(javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI, "dateTime"), Date.class);
	}
	
	/* (non-Javadoc)
	 * @see nz.org.take.r2ml.DatatypeMapper#getType(javax.xml.namespace.QName)
	 */
	public Class getType (QName fullName) throws R2MLException {
		Class type = types.get(fullName);
//		System.out.println(fullName.toString() + " resolved to " + type==null?"nothing":type.getCanonicalName());
		if (type == null)
			throw new R2MLException("Type not found for class-id " + fullName);
//		System.out.println(fullName.toString() + " resolved to " + type.getCanonicalName());
		return type;
	}
	
	/* (non-Javadoc)
	 * @see nz.org.take.r2ml.DatatypeMapper#getType(java.lang.String)
	 */
	public Class getType (String localName) throws R2MLException {
		return getType(new QName("", localName));
	}
	
	/* (non-Javadoc)
	 * @see nz.org.take.r2ml.DatatypeMapper#setType(javax.xml.namespace.QName, java.lang.Class)
	 */
	public void setType (QName fullName, Class value) {
		types.put(fullName, value);
	}

	/* (non-Javadoc)
	 * @see nz.org.take.r2ml.DatatypeMapper#setType(java.lang.String, java.lang.Class)
	 */
	public void setType(String localName, Class clazz) {
		setType(new QName("", localName), clazz);
	}
	
}