/**
 * Copyright 2007 Jens Dietrich Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
 */


package nz.org.take.mandarax_migration_kit;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.log4j.BasicConfigurator;
import org.mandarax.kernel.ClauseSet;
import org.mandarax.kernel.ComplexTerm;
import org.mandarax.kernel.ConstantTerm;
import org.mandarax.kernel.Fact;
import org.mandarax.kernel.KnowledgeBase;
import org.mandarax.kernel.Predicate;
import org.mandarax.kernel.Prerequisite;
import org.mandarax.kernel.Query;
import org.mandarax.kernel.Rule;
import org.mandarax.kernel.SimplePredicate;
import org.mandarax.kernel.Term;
import org.mandarax.kernel.VariableTerm;
import org.mandarax.kernel.meta.JPredicate;

/**
 * Adapter to that turns a Mandarax knowledge base into a TAKE script.
 * There are some limitations, check the generated logs for details. 
 * A constantHandler is used to deal with the constants found in the
 * mandarax kb. These objects are represented by strings in the script
 * generated. 
 * @author Jens Dietrich
 */
public class Mandarax2Take {
	
	static {
		BasicConfigurator.configure();
	}
	protected static org.apache.log4j.Category LOGGER = org.apache.log4j.Logger.getInstance(Mandarax2Take.class);
	protected File outputFolder = new File("output");
	protected String kbScriptName = "kb.take";
	protected String compileScriptName = "compilescript.txt";

	private PrintStream out = null;
	protected int constantCounter = 0;
	protected int ruleCounter = 0;
	protected int factCounter = 0;
	protected Map<VariableTerm,String> variables = new HashMap<VariableTerm,String>();
	protected Map<ConstantTerm,String> constants = new HashMap<ConstantTerm,String>();
	
	
	protected ConstantHandler constantHandler = new XMLConstantHandler();
	
	public File getOutputFolder() {
		return outputFolder;
	}
	public void setOutputFolder(File outputFolder) {
		this.outputFolder = outputFolder;
	}

	/**
	 * Generate the script(s) for the mandarax knowledge base.
	 * The output folder will be outputFolder . 
	 * @param kb
	 * @throws Exception
	 */
	public void generateTakeScript(KnowledgeBase kb) throws Exception {
		
		if (!this.getOutputFolder().exists()) {
			this.getOutputFolder().mkdirs();
			LOGGER.info("Create folder " + this.getOutputFolder());
		}
			
		File f = new File(""+this.getOutputFolder() + File.separatorChar + this.kbScriptName);
		LOGGER.info("Creating take script " + f.getAbsolutePath());
		
		out = new PrintStream(new FileOutputStream(f));

		
		printVariableDeclarations(kb);
		printConstantDeclarations(kb);
		
		for (Iterator<Query> iter = kb.queries();iter.hasNext();) {
			generateScript(iter.next());
		}
		
		for (Object e:kb.getClauseSets()) {
			ClauseSet cs = (ClauseSet)e;
			if (cs instanceof Rule) {
				generateScript((Rule)cs);
			}
			else if (cs instanceof Fact) {
				generateScript((Fact)cs);
			}
			else {
				LOGGER.warn("Unsupported knowledge type (will be ignored): " + cs.getClass() );
			}
			
		}
		out.close();
		
		f = new File(""+this.getOutputFolder() + File.separatorChar + this.compileScriptName);
		LOGGER.info("Creating compilation script " + f.getAbsolutePath());
		out = new PrintStream(new FileOutputStream(f));
		
		generateCompilationScript();
		
		// generate constant script
		if (constantHandler!=null && this.constants.size()>0) {
			Map<String,Object> objects = new HashMap<String,Object>();
			for (Map.Entry<ConstantTerm,String> e:constants.entrySet()) {
				objects.put(e.getValue(),e.getKey().getObject());
			}
			constantHandler.generateScript(outputFolder,objects,LOGGER);
		}
		
		out.close();
	}
	
	protected void generateCompilationScript() {
		out.println("// this script can be used to generate the classes from the generated script " + this.kbScriptName);
		out.println("// some packages have to be imported manually, in Eclipse use Ctrl-Shift-O ");
		out.println("BasicConfigurator.configure()");
		
		out.println("BasicConfigurator.configure()");
		out.println("// insert the name of the class and package to be generated here:");
		out.println("String packageName = null; // TODO");
		out.println("String className = null; // TODO");
		out.println("DefaultLocation location = new DefaultLocation();");
		out.println("NameGenerator nameGenerator = new DefaultNameGenerator();");
		out.println("Compiler compiler = new DefaultCompiler();");
		out.println("compiler.add(new JalopyCodeFormatter());");
		out.println("compiler.setNameGenerator(nameGenerator);");
		out.println("InputStream script = new FileInputStream(\""+this.kbScriptName+"\");");
		out.println("ScriptKnowledgeSource ksource = new ScriptKnowledgeSource(script);");
		out.println("compiler.setLocation(location);");
		out.println("compiler.setPackageName(packageName);");
		out.println("compiler.setClassName(className);");
		out.println("compiler.compile(ksource.getKnowledgeBase());");
	}
	protected void generateScript(Query q) {
		beforeGenerateScript(q);
		out.println("// replace \"out\" by \"in\" for parameters that will be supplied by the application");
		out.print("query ");
		out.print(this.getJavaName(q.getFacts()[0].getPredicate().getName()));
		out.print('[');
		for (int i=0;i<q.getFacts()[0].getPredicate().getStructure().length;i++) {
			if (i>0)
				out.print(',');
			out.print("out");
		}
		out.println(']');
		afterGenerateScript(q);
	}
	private void afterGenerateScript(Query q) {
		// by default do nothing		
	}
	private void beforeGenerateScript(Query q) {
		// by default do nothing		
	}
	private void printVariableDeclarations (KnowledgeBase kb) {
		for (Object e:kb.getClauseSets()) {
			ClauseSet cs = (ClauseSet)e;
			if (cs instanceof Rule) {
				printVariableDeclarations((Rule)cs);
			}
			else if (cs instanceof Fact) {
				printVariableDeclarations((Fact)cs);
			}
			else {
				LOGGER.warn("Unsupported knowledge type (will be ignored): " + cs.getClass() );
			}
		}	
	}
	
	private void printVariableDeclarations(Fact f) {
		for (Term t:f.getTerms()) {
			printVariableDeclarations(t);
		}
	}
	private void printVariableDeclarations(Term t) {
		if (t instanceof VariableTerm) {
			VariableTerm var =  (VariableTerm)t;
			String name = this.variables.get(var);
			if (name==null) {
				name = this.getVarName(var.getName());
				variables.put(var,name);
				out.print("var ");
				out.print(var.getType().getName());
				out.print(' ');
				out.println(name);
			}
		}
		else if (t instanceof ComplexTerm) {
			ComplexTerm xt = (ComplexTerm)t;
			for (Term st:xt.getTerms()) {
				printVariableDeclarations(st);
			}
		}
	}
	private void printVariableDeclarations(Rule cs) {
		printVariableDeclarations(cs.getHead());
		List<Prerequisite> prereqs = cs.getBody();
		for (Prerequisite pre:prereqs) {
			printVariableDeclarations(pre);
		}
	}
	
	private void printConstantDeclarations (KnowledgeBase kb) {
		for (Object e:kb.getClauseSets()) {
			ClauseSet cs = (ClauseSet)e;
			if (cs instanceof Rule) {
				printConstantDeclarations((Rule)cs);
			}
			else if (cs instanceof Fact) {
				printConstantDeclarations((Fact)cs);
			}
			else {
				LOGGER.warn("Unsupported knowledge type (will be ignored): " + cs.getClass() );
			}
		}	
	}
	
	private void printConstantDeclarations(Fact f) {
		for (Term t:f.getTerms()) {
			printConstantDeclarations(t);
		}
	}
	private void printConstantDeclarations(Term t) {
		if (t instanceof ConstantTerm) {
			ConstantTerm c =  (ConstantTerm)t;
			String name = this.constants.get(c);
			if (name==null) {
				name = this.getConstantName(c);
				constants.put(c,name);
				out.print("ref ");
				out.print(c.getType().getName());
				out.print(' ');
				out.println(name);
			}
		}
		else if (t instanceof ComplexTerm) {
			ComplexTerm xt = (ComplexTerm)t;
			for (Term st:xt.getTerms()) {
				printConstantDeclarations(st);
			}
		}
	}
	protected String getConstantName(ConstantTerm c) {
		return "obj"+ ++this.constantCounter;
	}
	private void printConstantDeclarations(Rule cs) {
		printConstantDeclarations(cs.getHead());
		List<Prerequisite> prereqs = cs.getBody();
		for (Prerequisite pre:prereqs) {
			printConstantDeclarations(pre);
		}
	}
	
	protected void generateScript(Fact f) {
		beforeGenerateScript(f);
		out.print("fact");
		out.print(++factCounter);
		out.print(": ");
		print(f);
		out.println();		
		afterGenerateScript(f);
	}
	protected void afterGenerateScript(Fact f) {
		// by default do nothing
		
	}
	protected void beforeGenerateScript(Fact f) {
		// by default do nothing
		
	}
	private void print(Fact f) {
		print(f.getPredicate());
		out.print('[');
		boolean first = true;
		for (Term t:f.getTerms()) {
			if (first)
				first=false;
			else
				out.print(',');
			if (t instanceof VariableTerm) {
				print((VariableTerm)t);
			}
			else if (t instanceof ConstantTerm) {
				print((ConstantTerm)t);
			}
			else if (t instanceof ComplexTerm) {
				print((ComplexTerm)t);
			}
		}
		out.print(']');
	}
	private void print(ConstantTerm t) {
		String name = this.constants.get(t);
		out.print(name);
	}
	private void print(ComplexTerm t) {
		out.print(t.toString());
	}
	private void print(VariableTerm t) {
		String name = this.variables.get(t);
		out.print(name);
	}
	private void print(Predicate predicate) {
		if (predicate instanceof SimplePredicate) {
			out.print(this.getJavaName(((SimplePredicate)predicate).getName()));
		}
		else if (predicate instanceof JPredicate){
			JPredicate jp = (JPredicate)predicate;
			out.print(jp.getMethod().getName());
		}
		else {
			LOGGER.warn("Unsupported predicate type: " + predicate.getClass() + " in " + predicate.getName());
		}
		
	}
	
	protected void generateScript(Rule r) {
		
		beforeGenerateScript(r);
		
		out.print("// ");
		out.println(r);
		
		
		out.print("rule");
		out.print(++ruleCounter);
		out.print(": ");
		
		List<Prerequisite> body = r.getBody();
		Fact head = r.getHead();
		
		if (!body.isEmpty()) {
			out.print("if ");
		}
		boolean first = true;
		for (Prerequisite pre:body) {
			if (first)
				first=false;
			else
				out.print(" and ");
			if (pre.isNegatedAF())
				out.print("not ");
			print(pre);
		}
		
		if (!body.isEmpty()) {
			out.print(" then ");
		}
		print(head);
		out.println();	
		
		// post processing
		afterGenerateScript(r);
	}

	protected void afterGenerateScript(Rule r) {
		// by default do nothing
		
	}
	protected void beforeGenerateScript(Rule r) {
		// by default do nothing
	}

	
	private String getVarName(String name) {
		return name;
	}
	
	private String getJavaName(String name) {
		StringBuffer b = new StringBuffer();
		for (int i=0;i<name.length();i++) {
			char c = name.charAt(i);
			if (i==0 && Character.isJavaIdentifierStart(c)) 
				b.append(c);
			else if (i>0 && Character.isJavaIdentifierPart(c)) 
				b.append(c);
			else 
				b.append("_");
		}
		return b.toString();
	}
	public ConstantHandler getConstantHandler() {
		return constantHandler;
	}
	public void setConstantHandler(ConstantHandler constantHandler) {
		this.constantHandler = constantHandler;
	}

}
