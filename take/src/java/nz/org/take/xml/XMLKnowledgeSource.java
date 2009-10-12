package nz.org.take.xml;

import java.io.InputStream;
import java.io.Reader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import nz.org.take.*;
import nz.org.take.mvel2.MVEL2ExpressionLanguage;
import nz.org.take.xml.jaxb.Annotation;
import nz.org.take.xml.jaxb.Atom;
import nz.org.take.xml.jaxb.Bodyatom;
import nz.org.take.xml.jaxb.If;
import nz.org.take.xml.jaxb.Ref;
import nz.org.take.xml.jaxb.Rule;
import nz.org.take.xml.jaxb.RuleSet;
import nz.org.take.xml.jaxb.Then;
import nz.org.take.xml.jaxb.Var;

public class XMLKnowledgeSource implements KnowledgeSource {
	private static final String DEFAULT_EXPRESSION_LANGUAGE = MVEL2ExpressionLanguage.class.getName();
	private RuleSet ruleset = null;
	private KnowledgeBase kb = null;
	private Map<String, String> annotations = new HashMap<String,String>();
	private Map<String,Predicate> predicates = new HashMap<String,Predicate>();
	private Map<String,Predicate> npredicates = new HashMap<String,Predicate>();
	private HashMap<String, Class> typeInfo = new HashMap<String,Class>();
	private Set<Constant> constants = new HashSet<Constant>();
	private Set<Variable> vars = new HashSet<Variable>();
	// TODO create access
	private ClassLoader classLoader = XMLKnowledgeSource.class.getClassLoader(); 
	
	public XMLKnowledgeSource(Reader reader) throws Exception {
		super();
		ruleset = read(reader);
		buildKB();
	}
	
	public XMLKnowledgeSource(InputStream in) throws Exception {
		super();
		ruleset = read(in);
		buildKB();
	}
	
	private Unmarshaller createUnmarshaller()  throws JAXBException{
		JAXBContext jc = JAXBContext.newInstance("nz.org.take.xml.jaxb");
		return jc.createUnmarshaller();
	}
	private RuleSet read(Reader r) throws JAXBException {
		return (RuleSet) createUnmarshaller().unmarshal(r);
	}
	private RuleSet read(InputStream r) throws JAXBException {
		JAXBElement o = (JAXBElement)createUnmarshaller().unmarshal(r);
		return (RuleSet)o.getValue();
	}
	@Override
	public KnowledgeBase getKnowledgeBase() throws TakeException {
		return kb;
	} 
	
	private void buildKB() throws XMLKnowledgeSourceException {
		kb = new DefaultKnowledgeBase();
		extractConstants();
		extractVariables();
		buildTypeInfo();
		extractAnnotations();
		
		for (Object part:this.ruleset.getAnnotationOrVarOrRef()) {
			if (part instanceof Rule) {
				DerivationRule rule = buildRule((Rule)part);
				kb.add(rule);
			}
		}	
		
	}

	private void buildTypeInfo() {
		for (Variable v:vars) {
			typeInfo.put(v.getName(),v.getType());
		}
		for (Constant c:constants) {
			typeInfo.put(c.getRef(),c.getType());
		}
	}

	private DerivationRule buildRule(Rule r) throws XMLKnowledgeSourceException {
		DerivationRule rule = new DerivationRule();
		annotate(rule,extractAnnotations(r.getAnnotation()));
		// body
		If _if = r.getIf();
		for (Object o:_if.getExpressionOrRelation()) {
			if (o instanceof String) {
				String expression = (String)o;
				try {
					ExpressionPrerequisite prereq = new ExpressionPrerequisite(expression,DEFAULT_EXPRESSION_LANGUAGE,typeInfo);
					rule.getBody().add(prereq);
				} catch (ExpressionException e) {
					throw new XMLKnowledgeSourceException("Cannot built prerequisite from expression "+o,e);
				}
			}
			else if (o instanceof Bodyatom) {
				Bodyatom atom = (Bodyatom)o;
				boolean negated = atom.isNegated();
				FactPrerequisite prereq = new FactPrerequisite();
				Term[] terms = new Term[atom.getVarOrRefOrExpression().size()]; 
				int pos = 0;
				for (Object slot:atom.getVarOrRefOrExpression()) {
					slot = ((JAXBElement)slot).getValue();
					if (slot instanceof Var) {
						terms[pos] = buildVariable((Var)slot);
					}
					else if (slot instanceof Ref) {
						terms[pos] = buildConstant((Ref)slot);
					}
					else if (slot instanceof String) {
						terms[pos] = buildExpressionTerm((String)slot);
					}
					pos=pos+1;
				}
				prereq.setTerms(terms);
				Predicate p = findOrAddPredicate(atom.getName(),terms,negated);
				prereq.setPredicate(p);
				rule.getBody().add(prereq);
			}
		}
		// head
		Then _then = r.getThen();
		Atom atom = _then.getRelation();
		Fact concl = new Fact();
		Term[] terms = new Term[atom.getVarOrRef().size()]; 
		int pos = 0;
		for (Object slot:atom.getVarOrRef()) {
			slot = ((JAXBElement)slot).getValue();
			if (slot instanceof Var) {
				terms[pos] = buildVariable((Var)slot);
			}
			else if (slot instanceof Ref) {
				terms[pos] = buildConstant((Ref)slot);
			}
			pos=pos+1;
		}
		concl.setTerms(terms);
		Predicate p = findOrAddPredicate(atom.getName(),terms,false);
		concl.setPredicate(p);
		rule.setHead(concl);
		return rule;
	}

	private Predicate findOrAddPredicate(String name, Term[] terms, boolean negated) throws XMLKnowledgeSourceException {
		Class[] types = new Class[terms.length];
		for (int i=0;i<terms.length;i++) {
			types[i] = terms[i].getType();
		}
		Predicate p = this.predicates.get(name);
		Predicate np = this.npredicates.get(name);
		if (p==null) {
			p = new Predicate();
			p.setSlotTypes(types);
			p.setName(name);
			p.setNegated(false);
			this.predicates.put(name,p);
			
			np = new Predicate();
			np.setSlotTypes(types);
			np.setName(name);
			np.setNegated(true);
			this.npredicates.put(name,np);
		}
		else {
			if (p.getSlotTypes().length!=terms.length) {
				throw new XMLKnowledgeSourceException("The same predicate " + name + "has been encountered with different sets of parameters");
			}
			for (int i=0;i<terms.length;i++) {
				Class oldType = p.getSlotTypes()[i];
				Class newType = types[i];
				Class type = getMostGeneralType(oldType,newType);
				if (type!=oldType) {
					// replace types by more general types
					p.getSlotTypes()[i]=type;
					np.getSlotTypes()[i]=type;
				}
			}
		}
		
		return negated?np:p;
	}
	// TODO: compute least general common super type
	private Class getMostGeneralType(Class oldType, Class newType) {
		if (oldType==newType) return oldType;
		return newType.isAssignableFrom(oldType)?newType:oldType;
	}

	private Term buildExpressionTerm(String expression) throws XMLKnowledgeSourceException {
		try {
			return new ComplexTerm(expression,DEFAULT_EXPRESSION_LANGUAGE,typeInfo);
		} catch (ExpressionException e) {
			throw new XMLKnowledgeSourceException("Cannot built term from expression "+expression,e);
		}
	}

	private Term buildConstant(Ref slot) throws XMLKnowledgeSourceException {
		Constant c = new Constant();
		c.setRef(slot.getName());
		c.setType(getType(slot.getType()));
		return c;
	}

	private Term buildVariable(Var slot) throws XMLKnowledgeSourceException {
		Variable var = new Variable();
		var.setName(slot.getName());
		var.setType(getType(slot.getType()));
		return var;
	}

	private void annotate(Annotatable a,	Map<String, String> localAnnotations) {
		Map<String, String> ann = new HashMap<String, String>();
		ann.putAll(annotations); // global annotations
		ann.putAll(localAnnotations); // local may override global
		a.addAnnotations(ann);
		
	}

	private void extractAnnotations() {
		for (Object part:this.ruleset.getAnnotationOrVarOrRef()) {
			if (part instanceof Annotation) {
				Annotation ann = (Annotation)part;
				annotations.put(ann.getName(),ann.getValue());
			}
		}	
	}



	private Map<String, String> extractAnnotations(List<Annotation> annotations) {
		
		Map<String,String> map = new HashMap<String,String>();
		for (Annotation a:annotations) {
			map.put(a.getName(),a.getValue());
		}
		return map;
	}

	private void extractVariables() throws XMLKnowledgeSourceException {
		for (Object part:this.ruleset.getAnnotationOrVarOrRef()) {
			if (part instanceof Var) {
				Var v = (Var)part;
				Variable var = new Variable();
				var.setName(v.getName());
				var.setType(getType(v.getType()));
				this.vars.add(var);
			}
		}
	}

	private Class getType(String type) throws XMLKnowledgeSourceException {
		if ("char".equals(type)) return Character.TYPE;
		if ("byte".equals(type)) return Byte.TYPE;
		if ("int".equals(type))	return Integer.TYPE;
		if ("short".equals(type)) return Short.TYPE;
		if ("long".equals(type)) return Long.TYPE;
		if ("double".equals(type)) return Double.TYPE;
		if ("float".equals(type)) return Float.TYPE;
		if ("boolean".equals(type)) return Boolean.TYPE;
		
		// try whether this is a class name
		try {
			return this.classLoader.loadClass(type);
		}
		catch (ClassNotFoundException x){
			throw new XMLKnowledgeSourceException("Class not found: "+type,x);
		}
	}

	private void extractConstants() throws XMLKnowledgeSourceException {
		for (Object part:this.ruleset.getAnnotationOrVarOrRef()) {
			if (part instanceof Ref) {
				Ref r = (Ref)part;
				Constant con = new Constant();
				con.setRef(r.getName());
				con.setType(getType(r.getType()));
				constants.add(con);
			}
		}
	}
}
