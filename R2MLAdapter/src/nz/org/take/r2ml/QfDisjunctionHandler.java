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
import nz.org.take.r2ml.util.R2MLUtil;

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

	public Object importObject(Object obj) throws R2MLException {

		QfDisjunction formula = (QfDisjunction) obj;
		List<List<Prerequisite>> bodies = new ArrayList<List<Prerequisite>>();

		for (JAXBElement<? extends QfAndOrNafNegFormula> disjunct : formula
				.getQfAndOrNafNegFormula()) {
			XmlTypeHandler handler = R2MLDriver.get().getHandlerByXmlType(disjunct
					.getValue().getClass());
			Object object = handler.importObject(disjunct.getValue());
			if (object instanceof List) {
				bodies.add((List) object);
			} else {
				List<Prerequisite> list = new ArrayList<Prerequisite>();
				list.add(R2MLUtil.factAsPrerequisite((Fact) object));
				bodies.add(list);
			}
		} // for
		return bodies;
	}

}
