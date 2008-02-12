/**
 * 
 */
package nz.org.take.r2ml;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Collection;

import nz.org.take.DerivationRule;
import nz.org.take.Fact;
import nz.org.take.Predicate;
import nz.org.take.Prerequisite;
import nz.org.take.SimplePredicate;
import nz.org.take.Term;
import nz.org.take.Variable;
import nz.org.take.r2ml.util.StrelkaPropertyPredicate;

/**
 * @author schenke
 * 
 */
public abstract class AbstractPropertyHandler implements XmlTypeHandler {

	private Collection<String> existingPropertyPredicates = new ArrayList<String>();
	
	public Predicate buildPredicate(String attributeName, Term domain,
			Term range, boolean negated, String[] slotNames)
			throws R2MLException {
		
		// if predicate doesnt exist create a new one
		MappingContext context = MappingContext.get();
		SimplePredicate simplePredicate = (SimplePredicate)context.getPredicate("s_" + attributeName); 
		if (simplePredicate == null) {
			simplePredicate = new SimplePredicate();
			simplePredicate.setName(attributeName);
			simplePredicate.setNegated(negated);
			simplePredicate.setSlotTypes(new Class[] { domain.getType(),
					range.getType() });
			simplePredicate.setSlotNames(slotNames);
			context.addPredicate(simplePredicate);
		}

		PropertyDescriptor property = buildProperty(attributeName, domain
				.getType());
		if (property != null && !existingPropertyPredicates.contains(property.getName())) {
			StrelkaPropertyPredicate propPredicate = new StrelkaPropertyPredicate();
			propPredicate.setNegated(negated);
			propPredicate.getSlotTypes();
			propPredicate.setOwnerType(domain.getType());
			propPredicate.setProperty(property);
			propPredicate.setSlotNames(slotNames);
			propPredicate.getSlotTypes();
			existingPropertyPredicates.add(propPredicate.getName());
			
			buildMappingRule(domain, range, simplePredicate, propPredicate);
			
		}

		return simplePredicate;
		
	}

	/**
	 * @param domain
	 * @param range
	 * @param simplePredicate
	 * @param propPredicate
	 * @throws R2MLException
	 */
	private void buildMappingRule(Term domain, Term range, SimplePredicate simplePredicate, StrelkaPropertyPredicate propPredicate) throws R2MLException {
		// setup mappingrule
		DerivationRule mappingRule = new DerivationRule();
		Variable v = new Variable();
		v.setName("v" + propPredicate.getName());
		v.setType(range.getType());
		Fact head = new Fact();
		head.setPredicate(simplePredicate);
		head.setTerms(new Term[] {domain, v} );
		head.setId("property_" + simplePredicate.getName());
		mappingRule.setHead(head);
		Prerequisite body = new Prerequisite();
		body.setPredicate(propPredicate);
		body.setTerms(new Term[] {domain, v});
		mappingRule.setBody(new ArrayList<Prerequisite>());
		mappingRule.getBody().add(body);
		mappingRule.setId("mapping_rule_" + simplePredicate.getName());
		R2MLDriver.get().addRuleToRuleBase(mappingRule);
		MappingContext.get().addPredicatePropertyName(simplePredicate.getName());
		MappingContext.get().addPredicatePropertyName(propPredicate.getName());
	}

	protected static PropertyDescriptor buildProperty(String name, Class clazz) {
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
			PropertyDescriptor[] properties = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor property : properties) {
				if (name.toLowerCase().equals(property.getName().toLowerCase())
						&& property.getReadMethod() != null) {
					return property;
				}
			}
		} catch (Exception e) {

		}
		// no property found or exception occured
		return null;
	}

}
