package nz.org.take.r2ml;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.bind.JAXBElement;

import de.tu_cottbus.r2ml.Atom;
import de.tu_cottbus.r2ml.QfAndOrNafNegFormula;
import de.tu_cottbus.r2ml.QfDisjunction;

import nz.org.take.Fact;
import nz.org.take.Prerequisite;

/**
 * Map a quantifier free disjunction from R2ML (QfDisjunction) to a list of take
 * prerequisites (List<Prerequisite>).
 * 
 * @param obj
 *            a QfDisjunction object
 * @param driver
 *            the used R2MLDriver
 * @return a list of take prerequisites
 * 
 * @see nz.org.take.r2ml.XmlTypeHandler#importObject(java.lang.Object,
 *      nz.org.take.r2ml.R2MLDriver)
 */
public class QfDisjunctionHandler implements XmlTypeHandler {

	public Object importObject(Object obj, MappingContext context,
			R2MLDriver driver) throws R2MLException {

		QfDisjunction formula = (QfDisjunction) obj;
		Collection<Collection<Prerequisite>> bodies = new ArrayList<Collection<Prerequisite>>();

		for (JAXBElement<? extends QfAndOrNafNegFormula> disjunct : formula
				.getQfAndOrNafNegFormula()) {
			XmlTypeHandler handler = driver.getHandlerByXmlType(disjunct
					.getValue().getClass());
			Object object = handler.importObject(disjunct.getValue(), context,
					driver);
			if (object instanceof Collection) {
				bodies.add((Collection<Prerequisite>) object);
			} else {
				Collection<Prerequisite> list = new ArrayList<Prerequisite>();
				list.add(R2MLUtil.factAsPrerequisite((Fact) object));
				bodies.add(list);
			}
		} // for
		return bodies;
	}

}
