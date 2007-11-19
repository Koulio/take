/**
 * 
 */
package nz.org.take.r2ml;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

import javax.xml.namespace.QName;

import nz.org.take.Predicate;
import nz.org.take.PropertyPredicate;
import nz.org.take.SimplePredicate;
import nz.org.take.Term;

/**
 * @author schenke
 *
 */
public abstract class AbstractPropertyHandler implements XmlTypeHandler {

	public static Predicate buildPredicate(String attributeName, Term domain,
			Term range, boolean negated, String[] slotNames)
			throws R2MLException {
		// if predicate already exists
		MappingContext context = MappingContext.get();
		if (context.getPredicate(attributeName) != null) {
			return context.getPredicate(attributeName);
		}
		PropertyDescriptor property = buildProperty(attributeName, domain
				.getType());
		// if attribute is beanproperty use it
		if (property != null) {
			PropertyPredicate propPredicate = new PropertyPredicate();
			propPredicate.setNegated(negated);
			propPredicate.setOne2One(true);
			propPredicate.setOwnerType(domain.getType());
			propPredicate.setProperty(property);
			propPredicate.setSlotNames(slotNames);
			context.addPredicate(propPredicate);
			return propPredicate;
		} else {
			SimplePredicate simplePredicate = new SimplePredicate();
			simplePredicate.setName(attributeName);
			simplePredicate.setNegated(negated);
			simplePredicate.setSlotTypes(new Class[] { domain.getType(),
					range.getType() });
			simplePredicate.setSlotNames(slotNames);
			context.addPredicate(simplePredicate);
			return simplePredicate;
		}
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
