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

import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;

public class DefaultDatatypeMapper implements DatatypeMapper {
	
	static Map<QName, Class> types = new HashMap<QName, Class>();

	public DefaultDatatypeMapper () {
		super();
		types.put(new QName("string"), String.class);
	}
	
	public Class getType (QName key) {
		return types.get(key);
	}
	
	public void setType (QName key, Class value) {
		types.put(key, value);
	}
	
}
