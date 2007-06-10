/*
 * Copyright (C) 2006 <A href="http://www-ist.massey.ac.nz/JBDietrich" target="_top">Jens Dietrich</a>
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


package nz.org.take.compiler.util;

import java.util.HashMap;
import java.util.Map;
import nz.org.take.AnnotationKeys;
import nz.org.take.Predicate;
import nz.org.take.Query;
import nz.org.take.compiler.NameGenerator;

/**
 * Default name generator implementation.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class DefaultNameGenerator implements NameGenerator {


	/* (non-Javadoc)
	 * @see org.mandarax.compiler.NameGenerator#getClassName(org.mandarax.kernel.Predicate)
	 */
	public String getClassName(Predicate p){
		String nameFromAnnnotation = p.getAnnotation(AnnotationKeys.TAKE_GENERATE_CLASS);
		if (nameFromAnnnotation!=null)
			return nameFromAnnnotation;
		
		String s = p.getName();
		return "_"+this.createJavaName(s,null);
	}

	/* (non-Javadoc)
	 * @see org.mandarax.compiler.NameGenerator#getAccessorNameForSlot(org.mandarax.kernel.Predicate, int)
	 */
	public String getAccessorNameForSlot(Predicate p,int slot) {
		String s = p.getSlotNames()[slot];
		return this.createJavaName(s,"get");
	}

	/* (non-Javadoc)
	 * @see org.mandarax.compiler.NameGenerator#getMutatorNameForSlot(org.mandarax.kernel.Predicate, int)
	 */
	public String getMutatorNameForSlot(Predicate p,int slot) {
		String s = p.getSlotNames()[slot];
		return this.createJavaName(s,"set");
	}

	/* (non-Javadoc)
	 * @see org.mandarax.compiler.NameGenerator#getVariableNameForSlot(org.mandarax.kernel.Predicate, int)
	 */
	public String getVariableNameForSlot(Predicate p,int slot) {
		String s = p.getSlotNames()[slot];
		return createJavaName(s,null);
	}
	
	
	/**
	 * Create a java name.
	 * @param s a string
	 * @param prefix a prefix
	 * @return a java name
	 */
	private String createJavaName(String s,String prefix) {
		StringBuffer b = new StringBuffer();
		if (prefix!=null)
			b.append(prefix);
		boolean firstToUpper = prefix!=null;
		//char[] cc = s.toCharArray();
		for (char c:s.toCharArray()) {
			if (Character.isWhitespace(c))
				c = '_';
			if (firstToUpper) {
				b.append(Character.toUpperCase(c));
				firstToUpper = false;
			} 
			else 
				b.append(c);
		}
		return b.toString();
	}
	
	public String getMethodName(Query q) {
		String nameFromAnnnotation = q.getAnnotation(AnnotationKeys.TAKE_GENERATE_METHOD);
		if (nameFromAnnnotation!=null)
			return nameFromAnnnotation;
		
		Predicate p = q.getPredicate();
		boolean[] inputParam = q.getInputParams();
		StringBuffer b = new StringBuffer();
		char[] name = p.getName().toCharArray();
		for (char ch : name)
			if (!Character.isWhitespace(ch))
				b.append(ch);
			else
				b.append("_");
		b.append("_");
		for (boolean f : inputParam)
			b.append( f ? "1" : "0" );
		return b.toString();
	}
	public String getConstantRegistryClassName() {
		return "Constants";
	}

	public String getFactStoreRegistryClassName() {
		return "FactStores";
	}
}
