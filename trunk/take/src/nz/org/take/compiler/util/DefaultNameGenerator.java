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

import nz.org.take.AggregationFunction;
import nz.org.take.AnnotationKeys;
import nz.org.take.Predicate;
import nz.org.take.Query;
import nz.org.take.compiler.NameGenerator;

/**
 * Default name generator implementation.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class DefaultNameGenerator implements NameGenerator {

	private Map<String,String> methodNames = new HashMap<String,String>();
	private Map<String,String> classNames = new HashMap<String,String>();
	
	/* (non-Javadoc)
	 * @see org.mandarax.compiler.NameGenerator#getClassName(org.mandarax.kernel.Predicate)
	 */
	public String getClassName(Predicate p){
		
		String key = this.createHash(p);
		String value = this.classNames.get(key);
		if (value==null) {			
			value = p.getAnnotation(AnnotationKeys.TAKE_GENERATE_CLASS);		
			if (value==null) {
				value = p.getName();
				value = this.createJavaName(value,null);
			}
			this.classNames.put(key, value);
		}
		return p.isNegated()?("not_"+value):value;
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
		String key = this.createHash(q);
		String value = this.methodNames.get(key);
		Predicate p = q.getPredicate();
		if (value==null)  {			
			String s = q.getAnnotation(AnnotationKeys.TAKE_GENERATE_METHOD);
			if (s==null) s = p.getAnnotation(AnnotationKeys.TAKE_GENERATE_METHOD);
			StringBuffer b = new StringBuffer();
			if (s==null) {		
				boolean[] inputParam = q.getInputParams();
				char[] name = p.getName().toCharArray();
				for (char ch : name)
					if (!Character.isWhitespace(ch))
						b.append(ch);
					else
						b.append("_");
				b.append("_");
				for (boolean f : inputParam)
					b.append( f ? "1" : "0" );
			}
			else {
				b.append(s);
			}
			value = b.toString();
			this.methodNames.put(key, value);
		}
		return p.isNegated()?("not_"+value):value;
	}

	public String getMethodName(AggregationFunction f) {
		StringBuffer b = new StringBuffer();		
		char[] name = f.getName().toCharArray();
		for (char ch : name)
			if (!Character.isWhitespace(ch))
				b.append(ch);
			else
				b.append("_");
		b.append("_");
		return b.toString();
	}
	public String getConstantRegistryClassName() {
		return "Constants";
	}
	
	public String getAggregationFunctionsRegistryClassName() {
		return "Aggregations";
	}

	public String getFactStoreRegistryClassName() {
		return "FactStores";
	}
	
	/**
	 * Generate the name of the class that has the methods
	 * generated for a query
	 * @param q a query
	 * @return a class name
	 */
	public synchronized String getKBFragementName(Query q) {
		return "KBFragement_"+getMethodName(q);
	}
	
	/**
	 * Create a string identifying a query.
	 * @param q
	 * @return
	 */
	private String createHash(Query q) {
		StringBuffer b = new StringBuffer();
		b.append(q.getPredicate().getName());
		b.append('_');
		for (boolean f:q.getInputParams()) {
			b.append(f?'1':'0');
		}
		return b.toString();
	} 
	/**
	 * Create a string identifying a predicate.
	 * @param p
	 * @return
	 */
	private String createHash(Predicate p) {
		StringBuffer b = new StringBuffer();
		b.append(p.getName());
		b.append('_');
		for (Class c:p.getSlotTypes()) {
			b.append('_');
			b.append(c.getName());
		}
		return b.toString();
	} 
	
	/**
	 * Reset cached information. Thsi method should be called before reusing name generators.
	 */
	public void reset(){
		this.methodNames.clear();
		this.classNames.clear();
	}

	
}
