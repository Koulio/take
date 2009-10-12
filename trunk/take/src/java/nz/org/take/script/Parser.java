/**
 * Copyright 2008 Jens Dietrich Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
 */
package nz.org.take.script;

import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import org.antlr.runtime.ANTLRReaderStream;
import org.antlr.runtime.CommonTokenStream;
import nz.org.take.AbstractKnowledgeBaseVisitor;
import nz.org.take.ComplexTerm;
import nz.org.take.Constant;
import nz.org.take.ExpressionPrerequisite;
import nz.org.take.ExternalFactStore;
import nz.org.take.Fact;
import nz.org.take.FactPrerequisite;
import nz.org.take.KnowledgeBase;
import nz.org.take.Predicate;
import nz.org.take.Query;
import nz.org.take.Term;
import nz.org.take.Variable;

// Note: The Take parser and lexer classes are generated when the build script is run.
import nz.org.take.script.antlr.TakeLexer;
import nz.org.take.script.antlr.TakeParser;

/**
 * Wrapper class for the ANTLR based Take parser.
 */
public class Parser {
	
	private ClassLoader classLoader;
	
	/**
	 * The purpose of this class is to replace compelx terms defined by expressions
	 * by simpler constants or variables. 
	 * @author jens
	 */
	class TermReplacer extends AbstractKnowledgeBaseVisitor {

		private Fact context = null;
		private int position = 0;
		private Map<String,Constant> constants = null;
		private Map<String,Variable> variables = null;
		
		public TermReplacer(Map<String,Constant> constants,Map<String,Variable> variables) {
			super();
			this.constants = constants;
			this.variables = variables;
		}

		@Override
		public boolean visit(ExternalFactStore k) {
			return false;
		}

		@Override
		public boolean visit(Fact f) {
			return doVisitFact(f);
		}

		@Override
		public boolean visit(ComplexTerm t) {
			Term simplifiedTerm = simplify(t);
			if (simplifiedTerm!=null) {
				this.context.getTerms()[position] = simplifiedTerm;
			}
			this.position = position+1;
			return false;
		}

		private Term simplify(ComplexTerm t) {
			if (t.getInputSlots().size()==0) { // literals
				Constant c = new Constant();
				if (t.getType()==String.class) {
					// mvel expressions seem to contain double quotes - remove
					// FIXME this is dangerous and might be MVEL (version) specific
					String value = t.getExpression();
					if (value.startsWith("\"") && value.endsWith("\"") && value.length()>1) {
						value = value.substring(1,value.length()-1);
					}
					c.setObject(value);
					return c;
				}
				if (t.getType()==Integer.class) {
					try {c.setObject(Integer.parseInt(t.getExpression()));} catch (Exception x){}
					return c;
				}
			}
			else if (t.getInputSlots().size()==1) {
				String x = t.getInputSlots().get(0);
				Constant c = constants.get(x);
				if (c!=null) {
					return c;
				}
				Variable v = variables.get(x);
				if (v!=null) {
					return v;
				}
				
			}
			return null;
		}

		@Override
		public boolean visit(Constant t) {
			return false;
		}

		@Override
		public boolean visit(Variable t) {
			return false;
		}

		@Override
		public boolean visit(Query q) {
			return false;
		}

		@Override
		public boolean visit(ExpressionPrerequisite p) {
			return false;
		}

		@Override
		public boolean visit(FactPrerequisite p) {
			return doVisitFact(p);
		}
		
		private boolean doVisitFact(Fact f) {
			context = f;
			position = 0;
			return true;
		}

		@Override
		public void endVisit(KnowledgeBase kb) {
			this.context = null;
			this.position = 0;
			super.endVisit(kb);
		}
		
	};
	
	public Parser() {
		super();
	}
	
	public KnowledgeBase parse(Reader reader) throws Exception {	
		TakeLexer lexer = new TakeLexer(new ANTLRReaderStream(reader));
		TakeParser parser = new TakeParser(new CommonTokenStream(lexer));
		
		KnowledgeBase kb = parser.script();
		Map<String,Constant> constants = new HashMap<String,Constant>();
		for (Constant c:parser.getConstants()) {
			constants.put(c.getName(),c);
		};
		Map<String,Variable> variables = new HashMap<String,Variable>();
		for (Variable v:parser.getVariables()) {
			variables.put(v.getName(),v);
		};
		
		postParse(kb,constants,variables);
		
		return kb;
	}

	private void postParse(KnowledgeBase kb,Map<String,Constant> constants,Map<String,Variable> variables) {
		kb.accept(new TermReplacer(constants,variables));
		copyQueryAnnotations(kb);
	}
	// copy query annotations to predicates
	private void copyQueryAnnotations(KnowledgeBase kb) {
		for (Query q:kb.getQueries()) {
			Predicate p = q.getPredicate();
			p.addAnnotations(q.getAnnotations());
		}
	}
	
	
}
