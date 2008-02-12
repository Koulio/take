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
package test.nz.org.take.r2ml.b;

import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;

import nz.org.take.r2ml.DatatypeMapper;
import nz.org.take.r2ml.reference.DefaultDatatypeMapper;

public class DefaultMapper extends DefaultDatatypeMapper {
	
	public DefaultMapper () {
		super();
		setType("Person", Person.class);
		setType(new QName(javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI, "string"), String.class);
	}
	
	
}
