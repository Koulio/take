/*
 * Copyright 2007 Bastian Schenke Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
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
import nz.org.take.PropertyPredicate;
import nz.org.take.SimplePredicate;
import nz.org.take.Term;
import nz.org.take.Variable;
import nz.org.take.r2ml.util.R2MLPropertyPredicate;

/**
 * @author schenke
 * 
 */
public abstract class AbstractPropertyHandler implements XmlTypeHandler {

	private Collection<String> existingPropertyPredicates = new ArrayList<String>();

	public Predicate buildPredicate(String attributeName, Term domain,
			Term range, boolean negated, String[] slotNames)
			throws R2MLException {
		R2MLDriver driver = R2MLDriver.get();
		MappingContext context = MappingContext.get();
		Predicate predicate = null;
		if (driver.getPropertyMode() == R2MLDriver.DEFAULT_PROPERTY_MODE) {
			predicate = context.getPredicate(attributeName);
			// if predicate doesnt exist create a new one
			if (predicate == null) {
				PropertyDescriptor property = buildProperty(attributeName,
						domain.getType());
				if (property != null) {
					PropertyPredicate propPredicate = null;
					propPredicate = new PropertyPredicate();
					propPredicate.setNegated(negated);
					propPredicate.getSlotTypes();
					propPredicate.setOwnerType(domain.getType());
					propPredicate.setProperty(property);
					propPredicate.setSlotNames(slotNames);
					propPredicate.getSlotNames();

					predicate = propPredicate;
					// existingPropertyPredicates.add(propPredicate.getName());

					// buildMappingRule(domain, range, simplePredicate,
					// propPredicate);
				} else {
					SimplePredicate simplePredicate = new SimplePredicate();
					simplePredicate.setName(attributeName);
					simplePredicate.setNegated(negated);
					simplePredicate.setSlotTypes(new Class[] {
							domain.getType(), range.getType() });
					simplePredicate.setSlotNames(slotNames);
					simplePredicate.getSlotNames();
					predicate = simplePredicate;
				}
				context.addPredicate(predicate);
			} // if predicate == null
		} else if (driver.getPropertyMode() == R2MLDriver.INFER_PROPERTIES_MODE) {
			// if predicate doesnt exist create a new one
			SimplePredicate simplePredicate;
			simplePredicate = (SimplePredicate) context.getPredicate(/*"s_" +*/attributeName);
			if (simplePredicate == null) {
				simplePredicate = new SimplePredicate();
				simplePredicate.setName(attributeName);
				simplePredicate.setNegated(negated);
				simplePredicate.setSlotTypes(new Class[] { domain.getType(),
						range.getType() });
				simplePredicate.setSlotNames(slotNames);
				simplePredicate.getSlotNames();
				context.addPredicate(simplePredicate);
			}
			predicate = simplePredicate;
			System.out.println();
			
			PropertyDescriptor property = buildProperty(attributeName, domain
					.getType());
			if (property != null
					&& !existingPropertyPredicates.contains(property.getName())) {
				R2MLPropertyPredicate propPredicate = new R2MLPropertyPredicate();
				propPredicate.setNegated(negated);
				propPredicate.setOwnerType(domain.getType());
				propPredicate.setProperty(property);
				propPredicate.setSlotNames(slotNames);
				propPredicate.getSlotNames();
				existingPropertyPredicates.add(propPredicate.getName());

				buildMappingRule(domain, range, simplePredicate, propPredicate);
			}
		}
		return predicate;

	}

	/**
	 * @param domain
	 * @param range
	 * @param simplePredicate
	 * @param propPredicate
	 * @throws R2MLException
	 */
	private void buildMappingRule(Term domain, Term range,
			SimplePredicate simplePredicate,
			R2MLPropertyPredicate propPredicate) throws R2MLException {
		// setup mappingrule
		if (MappingContext.get().getPropertyPredicateNames().contains(simplePredicate.getName())) {
			return;
		}
		DerivationRule mappingRule = new DerivationRule();
		Variable v = new Variable();
		v.setName("v" + propPredicate.getName());
		v.setType(range.getType());
		Fact head = new Fact();
		head.setPredicate(simplePredicate);
		head.setTerms(new Term[] { domain, v });
		head.setId("property_" + simplePredicate.getName());
		mappingRule.setHead(head);
		Prerequisite body = new Prerequisite();
		body.setPredicate(propPredicate);
		body.setTerms(new Term[] { domain, v });
		mappingRule.setBody(new ArrayList<Prerequisite>());
		mappingRule.getBody().add(body);
		mappingRule.setId("mapping_rule_" + simplePredicate.getName());
		R2MLDriver.get().addRuleToRuleBase(mappingRule);
		MappingContext.get()
				.addPredicatePropertyName(simplePredicate.getName());
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
