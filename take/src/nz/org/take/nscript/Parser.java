/**
 * Copyright 2008 Jens Dietrich Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
 */
package nz.org.take.nscript;


/**
 * Script parser. Stateful, instances should not be shared. 
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Pattern;
import nz.org.take.AggregationFunction;
import nz.org.take.Annotatable;
import nz.org.take.Constant;
import nz.org.take.DefaultKnowledgeBase;
import nz.org.take.DerivationRule;
import nz.org.take.Fact;
import nz.org.take.KnowledgeBase;
import nz.org.take.Prerequisite;
import nz.org.take.SimplePredicate;
import nz.org.take.Term;
import nz.org.take.Variable;


public class Parser {
	public static final String _NAME1 = "[a-zA-Z][a-zA-Z0-9_]*"; // names for types
	public static final String _NAME2 = "[a-zA-Z_][a-zA-Z0-9_]*"; // names for var and ref declarations
	public static final Pattern TYPE_NAME = Pattern.compile(_NAME1+"(\\."+_NAME1+")*");
	public static final Pattern NAME = Pattern.compile(_NAME2);
	public static final Pattern LIST_OF_NAMES = Pattern.compile(_NAME2+"([\\s]*\\,[\\s]*"+_NAME2+")*");
	public static final Pattern COMMENT = Pattern.compile("//(.)*");
	public static final Pattern GLOBAL_ANNOTATION = Pattern.compile("@@(.)+=(.)+");
	public static final Pattern LOCAL_ANNOTATION = Pattern.compile("@[^@](.)*=(.)+");	
	//public static final Pattern VAR_DECLARATION = Pattern.compile("var( )+([a-zA-Z0-9_.]+)( )+([a-zA-Z0-9_,]+)");
	//public static final Pattern REF_DECLARATION = Pattern.compile("ref( )+([a-zA-Z0-9_.]+)( )+([a-zA-Z0-9_,]+)");
	public static final Pattern QUERY = Pattern.compile("(not[\\s]+)?"+_NAME2+"[\\s]*\\[[\\s]*(in|out)([\\s]*,[\\s]*(in|out))*[\\s]*\\]");
	//query potentialTheftRating[in,out]
	//public static final Pattern _REF_DECLARATION = Pattern.compile("ref( )+([a-zA-Z0-9_.]+)( )+([a-zA-Z0-9_,]+)");
	public static final Pattern RULE = Pattern.compile(_NAME1+":[\\s]+if[\\s]+(.)*");
	public static final Pattern FACT = Pattern.compile(_NAME1+":(.)*");
	public static final Pattern CONDITION1 = Pattern.compile(_NAME2+"\\[(.)*\\]");
	public static final Pattern STRING_LITERAL = Pattern.compile("\'(.)*\'"); // TODO handle escaped quotes
	
	private ClassLoader classloader = Parser.class.getClassLoader(); 
	
	private KnowledgeBase kb = null;
	private Map<String,Variable> variables = new HashMap<String,Variable>();
	private Map<String,Constant> constants = new HashMap<String,Constant>();
	private Map<String,AggregationFunction> aggregationFunctions = new HashMap<String,AggregationFunction>();
	private Map<String,String> localAnnotations = new HashMap<String,String>();
	private List<QuerySpec> querySpecs = new ArrayList<QuerySpec>();
	
	private List<ScriptException> issues = null;
	private boolean verificationMode = false;
	
	public List<ScriptException> check (Reader reader)  throws ScriptException {
		verificationMode = true;
		return this.issues = new ArrayList<ScriptException>();
	}
	
	public KnowledgeBase parse (Reader reader) throws ScriptException  {
		verificationMode = false;
		kb = new DefaultKnowledgeBase();
		LineNumberReader bufReader = new LineNumberReader(reader);
		String line = null;
		try {
			while ((line=bufReader.readLine())!=null) {
				int no = bufReader.getLineNumber();
				line = line.trim();
				if (line.startsWith("//")) {
					// comment, don't parse
				}
				else if (line.startsWith("@@")) {
					parseGlobalAnnotation(line,no);
				}
				else if (line.startsWith("@")) {
					System.out.println("parse line " + no + " as local annotation: " + line);
					parseLocalAnnotation(line,no);
				}
				else if (line.startsWith("var ")) {
					System.out.println("parse line " + no + " as var declaration: " + line);
					parseVarDeclaration(line,no);
				}
				else if (line.startsWith("ref ")) {
					System.out.println("parse line " + no + " as ref declaration: " + line);
					parseRefDeclaration(line,no);
				}
				else if (line.startsWith("query ")) {
					System.out.println("parse line " + no + " as query: " + line);
					parseQuery(line,no);
				}
				else if (RULE.matcher(line).matches()) {
					System.out.println("parse line " + no + " as rule: " + line);
					parseRule(line,no);
				}
				else {
					error(no,"Unable to parse this line (unknown syntax type): " + line);
				}
				
			}
		} catch (IOException e) {
			throw new ScriptException(e);
		}	
		return kb;
	}
	
	private void error(int no,String line,Pattern pattern,String... description) throws ScriptException{
		StringBuffer buf = new StringBuffer();
		buf.append("Parser exception at line ");
		buf.append(no);
		buf.append(' ');
		buf.append(line);
		buf.append(" does not match pattern ");
		buf.append(pattern.pattern());
		buf.append(' ');
		for (String t:description)
			buf.append(t);
		error(no,buf.toString());
	}
	private void error(int no,String message) throws ScriptException{
		if (this.verificationMode) {
			this.issues.add(new ScriptException(message,no));
		}
		else {
			throw new ScriptException(message,no);
		}
	}
	private void check(int no,String txt,Pattern pattern,String... errorMessage) throws ScriptException {
		if (!pattern.matcher(txt).matches()) 
			this.error(no,txt,pattern,errorMessage);
	}

	private void parseVarDeclaration(String line, int no) throws ScriptException {
		line = line.substring(4).trim();
		int sep = line.indexOf(' ');
		String type =line.substring(0,sep).trim();
		check(no,type,TYPE_NAME,"this is not a valid type name");
		Class clazz = this.classForName(type,no);
		String names=line.substring(sep+1).trim();
		String name = null;
		check(no,names,LIST_OF_NAMES,"this is not a valid (list of) name(s)");
		
		StringTokenizer t = new StringTokenizer(names,",");
		while (t.hasMoreTokens()) {
			name = t.nextToken().trim();
			Variable var = new Variable();
			var.setType(clazz);
			var.setName(name);
			this.variables.put(name,var);
		}
	}

	private void parseRefDeclaration(String line, int no) throws ScriptException {
		line = line.substring(4).trim();
		int sep = line.indexOf(' ');
		String type =line.substring(0,sep).trim();
		check(no,type,TYPE_NAME,"this is not a valid type name");
		Class clazz = this.classForName(type,no);
		String names=line.substring(sep+1).trim();
		String name = null;
		check(no,names,LIST_OF_NAMES,"this is not a valid (list of) name(s)");
		
		StringTokenizer t = new StringTokenizer(names,",");
		while (t.hasMoreTokens()) {
			name = t.nextToken().trim();
			Constant c = new Constant();
			c.setType(clazz);
			c.setRef(name);
			this.constants.put(name,c);
		}	
	}
	
	private void parseQuery(String line, int no) throws ScriptException {
		line = line.substring(6).trim();
		
		QuerySpec query = new QuerySpec();
		
		boolean isNegated = false;
		if (line.startsWith("not ")) {
			query.setNegated(true);
			line = line.substring(4).trim();
		}

		int sep = line.indexOf('[');
		String name = line.substring(0,sep).trim();
		String sign = line.substring(sep,line.length()-1).trim();
		
		query.setPredicate(name);
		
		// parse i/o
		StringTokenizer tok = new StringTokenizer(sign,",");
		while (tok.hasMoreTokens()) {
			String token = tok.nextToken().trim();
			query.getIoSpec().add("in".equals(token));
		}
		
		this.querySpecs.add(query);
	}
	
	private void parseRule(String line, int no) throws ScriptException {

		int sep = line.indexOf(":"); //take off label
		String label = line.substring(0,sep).trim();
		line = line.substring(sep);
		sep = line.indexOf("if ");
		line = line.substring(sep+3).trim();
		List<String> unparsedConditions = new ArrayList<String>();
		String unparsedHead = null;
		BitSet neg = new BitSet();
		
		boolean literalMode = false;
		StringBuffer b = new StringBuffer();
		
		List<String> tokens = Tokenizer.tokenize(line," and "," then ");
		unparsedHead = tokens.remove(tokens.size()-1).trim();
		for (int i=0;i<tokens.size();i++) {
			String token = tokens.get(i).trim();
			if (token.startsWith("not ")) {
				token = token.substring(3).trim();
				neg.set(i);
			}
			unparsedConditions.add(token);
		}
		
		DerivationRule rule = new DerivationRule();
		rule.setId(label);
		Fact head = this.parseCondition(unparsedHead,no,false,false);
		rule.setHead(head);
		for (int i=0;i<unparsedConditions.size();i++) {
			Prerequisite prereq = (Prerequisite)this.parseCondition(unparsedConditions.get(i),no,true,neg.get(i));
			rule.getBody().add(prereq);
		}
		this.consumeAnnotations(rule);
		this.kb.add(rule);
		
		
		// tmp test code
		for (int i=0;i<unparsedConditions.size();i++) {
		System.out.print("- ");
			if (neg.get(i)) System.out.print("+ ");
			else System.out.print("- ");
				
			System.out.println(unparsedConditions.get(i));
		}
		System.out.print("->");
		System.out.println(unparsedHead);

	}
	
	
	
	private void parseGlobalAnnotation(String line, int no) throws ScriptException {
		check(no,line,GLOBAL_ANNOTATION,"this is not a valid global annotation");
		line = line.substring(2).trim();
		int sep = line.indexOf('=');
		String key=line.substring(0,sep).trim();
		String value=line.substring(sep+1).trim();
		this.kb.addAnnotation(key, value);
	}
	
	private void parseLocalAnnotation(String line, int no) throws ScriptException {
		check(no,line,LOCAL_ANNOTATION,"this is not a valid local annotation");
		line = line.substring(1).trim();
		int sep = line.indexOf('=');
		String key=line.substring(0,sep).trim();
		String value=line.substring(sep+1).trim();
		this.localAnnotations.put(key, value);
	}

	private Class classForName(String type,int line) throws ScriptException {
		try {
			if ("char".equals(type))
				return Character.TYPE;
			if ("byte".equals(type))
				return Byte.TYPE;
			if ("int".equals(type))
				return Integer.TYPE;
			if ("short".equals(type))
				return Short.TYPE;
			if ("long".equals(type))
				return Long.TYPE;
			if ("double".equals(type))
				return Double.TYPE;
			if ("float".equals(type))
				return Float.TYPE;
			if ("boolean".equals(type))
				return Boolean.TYPE;
			return  this.classloader.loadClass(type);
		}
		catch (ClassNotFoundException x) {
			throw new ScriptException("Can not load the type " + type + " referenced in line " + line,x);
		}	
	}
	
	private Fact parseCondition (String s,int no, boolean isPrerequisite,boolean isNegated) throws ScriptException {
		Fact fact = isPrerequisite?new Prerequisite():new Fact();
		int sep = -1;
		if (CONDITION1.matcher(s).matches()) {
			// type of the condition is predicate[terms]
			sep=s.indexOf('[');
			String p = s.substring(0,sep);
			SimplePredicate predicate = new SimplePredicate(); 
			predicate.setName(p);
			
			String t = s.substring(sep+1,s.length()-1);
			List<String> unparsedTerms = Tokenizer.tokenize(t,",");
			List<Term> terms = new ArrayList<Term>();
			for (String ut:unparsedTerms) {
				terms.add(parseTerm(ut));
			}
			Class[] types = new Class[terms.size()];
			for (int i=0;i<terms.size();i++) {
				types[i]=terms.get(i).getType();
			}
			predicate.setSlotTypes(types);
			predicate.setNegated(isNegated);
			fact.setPredicate(predicate);
			fact.setTerms(terms.toArray(new Term[terms.size()]));
			return fact;
		}
		else {
			if (!isPrerequisite) {
				throw new ScriptException("Error in line " + no + " - the rule head must have the following form: predicate[term_1,..,term_n]");
			}
			// try to parse this condition as JSP-EL expression
		}
		return fact;
	}
	
	private Term parseTerm(String s) throws ScriptException {
		// try to parse literal
		Object o = null;
		try {o = Integer.valueOf(s);}
		catch (NumberFormatException x){
			try {o = Float.valueOf(s);}
			catch (NumberFormatException x1){
				try {o = Double.valueOf(s);}
				catch (NumberFormatException x2){};
			}
		}
		if (o==null && STRING_LITERAL.matcher(s).matches()) {o = s.substring(1,s.length()-1);}
		
		if (o!=null) {
			Constant c = new Constant();
			c.setObject(o);
			c.setType(o.getClass());
			return c;
		}
		// try to find name
		if (NAME.matcher(s).matches()) {
			Term t = null;
			t = constants.get(s);
			if (t!=null)
				return t;
			t = this.variables.get(s);
			if (t!=null)
				return t;
		}
		// FIXME parse EL if null
		return null;
	}

	
	
	private void consumeAnnotations(Annotatable a) {
		a.addAnnotations(this.localAnnotations);
		this.localAnnotations.clear();
	}
}
