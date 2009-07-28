package test.nz.org.take.compiler.scenario0;

import java.util.*;
import nz.org.take.*;
import nz.org.take.mvel2.MVEL2ExpressionLanguage;

public class KBFactory {
	public static KnowledgeBase createKB() throws Exception {
		// build simple kb in memory - no script
		DefaultKnowledgeBase kb = new DefaultKnowledgeBase();
		
		Predicate father = new Predicate ();
		father.setName("father");
		father.setSlotNames(new String[]{"son","father"});
		father.setSlotTypes(new Class[]{String.class,String.class});
		
		Constant max = new Constant();
		max.setObject("max");
		Constant jens = new Constant();
		jens.setObject("jens");
		
		Fact f = new Fact();
		f.setId("fact1");
		f.setPredicate(father);
		f.setTerms(new Term[]{max,jens});
		
		Predicate son = new Predicate ();
		son.setName("son");
		son.setSlotNames(new String[]{"father","son"});
		son.setSlotTypes(new Class[]{String.class,String.class});		
		
		Variable x = new Variable();
		x.setName("x");
		x.setType(String.class);
		
		Variable y = new Variable();
		y.setName("y");
		y.setType(String.class);
		
		FactPrerequisite prereq1 = new FactPrerequisite();
		prereq1.setPredicate(father);
		prereq1.setTerms(new Variable[]{x,y});
		
		Map<String, Class> typeInfo = new HashMap<String,Class>();
		typeInfo.put("x",String.class);
		//typeInfo.put("y",String.class);
		ExpressionPrerequisite prereq2 = new ExpressionPrerequisite("x=='jens'",MVEL2ExpressionLanguage.class.getName(),typeInfo);
		
		Fact head = new Fact();
		head.setPredicate(son);
		head.setTerms(new Variable[]{y,x});
		
		DerivationRule r = new DerivationRule();
		List<Prerequisite> body = new ArrayList<Prerequisite>();
		body.add(prereq1);
		body.add(prereq2);
		r.setBody(body);
		r.setHead(head);
		r.setId("rule1");
		
		Query findFather = new Query();
		findFather.setPredicate(son);
		findFather.setInputParams(new boolean[]{false,true});
		findFather.setPublic(true);
		
		kb.add(r);
		kb.add(f);
		kb.add(findFather);
		
		return kb;
	}
}
