package nz.org.take.r2ml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBElement;


import nz.org.take.SimplePredicate;

import de.tu_cottbus.r2ml.GenericAtom;

/**
 * @author Bastian Schenke (bastian.schenke@googlemail.com)
 * 
 */
public class GenericAtomHandler implements XmlTypeHandler {

	/**
	 * Maps a GenericAtom to a Fact.
	 * 
	 * TODO implement protected String predicateType;
	 * 
	 * @param obj
	 *            a GenericAtom containing a Atom
	 * @param driver
	 *            the used R2MLDriver
	 * @return Fact representing the GenericAtom
	 * 
	 * @see nz.org.take.r2ml.XmlTypeHandler#importObject(java.lang.Object,
	 *      nz.org.take.r2ml.R2MLDriver)
	 */
	public Object importObject(Object obj, MappingContext context,
			R2MLDriver driver) throws R2MLException {
		GenericAtom genAtom = (GenericAtom) obj;
		List<JAXBElement<? extends de.tu_cottbus.r2ml.Term>> r2mlArgs = genAtom
				.getArguments().getTerm();
		nz.org.take.Fact takeFact = new nz.org.take.Fact();
		if (context.getPredicate(genAtom.getPredicateID().toString()) == null) {
			SimplePredicate takePredicate = new SimplePredicate();
			takePredicate.setName(genAtom.getPredicateID().toString());
			List<nz.org.take.Term> takeTerms = new ArrayList<nz.org.take.Term>();
			takePredicate.setSlotNames(driver.getSloNameGenerator()
					.getSlotNames(genAtom.getPredicateID()));
			List<Class> takeSlotTypes = new ArrayList<Class>();
			for (JAXBElement<? extends de.tu_cottbus.r2ml.Term> argElem : r2mlArgs) {
				de.tu_cottbus.r2ml.Term r2mlTerm = argElem.getValue();
				XmlTypeHandler handler = driver.getHandlerByXmlType(r2mlTerm
						.getClass());
				nz.org.take.Term takeArg = (nz.org.take.Term) handler
						.importObject(r2mlTerm, context, driver);
				takeTerms.add(takeArg);
				takeSlotTypes.add(takeArg.getType());
			}
			takePredicate.setSlotTypes(takeSlotTypes.toArray(new Class[0]));
			if (genAtom.isIsNegated() != null)
				takePredicate.setNegated(genAtom.isIsNegated());
			takeFact.setPredicate(takePredicate);
			takeFact.setTerms(takeTerms.toArray(new nz.org.take.Term[0]));
		} else {
			takeFact.setPredicate(context.getPredicate(genAtom.getPredicateID().toString()));
		}
		return takeFact;
	}
}
