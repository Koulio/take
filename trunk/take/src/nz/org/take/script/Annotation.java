/*
 * Copyright (C) 2007 <A href="http://www-ist.massey.ac.nz/JBDietrich" target="_top">Jens Dietrich</a>
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

package nz.org.take.script;

/**
 * Comment.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class Annotation   {

	private boolean isGlobal = false;
	private String key = null;
	private String value = null;
	public boolean isGlobal() {
		return isGlobal;
	}
	public void addToKey(String token) {
		if (key==null)
			key=token;
		else
			key = key+':'+token;
	}
	public void setGlobal(boolean isGlobal) {
		this.isGlobal = isGlobal;
	}
	public void setAnnotation(String annotation) {
		int sep = annotation.indexOf('=');
		if (sep==-1)
			throw new IllegalArgumentException("Invalid annotation format, annotation must have the format @key=value");
		this.key=annotation.substring(0,sep).trim();
		this.value=annotation.substring(sep+1).trim();
	}
	public String getValue() {
		return value;
	}
	public String getKey() {
		return key;
	}

	
}
