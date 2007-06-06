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


public class DefaultNameMapper implements NameMapper {

	Map<QName, String[]> slotNames = new HashMap<QName, String[]>();
	Map<QName, String> attrPredNames = new HashMap<QName, String>();

	public DefaultNameMapper() {
	}

	public String[] getSlotNames(QName predicateID) {
		String[] names = slotNames.get(predicateID); 
		return names;
	}

	public void addSlotNames(QName key, String[] value) {
		slotNames.put(key, value);
	}

	public void addSlotNames(Map<QName, String[]> names) {
		slotNames.putAll(names);
	}
	
	public void setAttrPredName() {
		
	}

}
