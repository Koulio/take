package nz.org.take.r2ml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBElement;

import nz.org.take.Fact;
import nz.org.take.Prerequisite;

import de.tu_cottbus.r2ml.Condition;
import de.tu_cottbus.r2ml.QfAndOrNafNegFormula;

public class ConditionHandler implements XmlTypeHandler {

	/**
	 * Maps a Condition to a List of Prerequisites.
	 * 
	 * TODO implement protected List<JAXBElement<? extends QfAndOrNafNegFormula>> qfAndOrNafNegFormula;
	 * 
	 * @param obj
	 *            a Condition
	 * @param driver
	 *            the used R2MLDriver
	 * @return a List<Prerequisite>
	 * 
	 * @see nz.org.take.r2ml.XmlTypeHandler#importObject(java.lang.Object,
	 *      nz.org.take.r2ml.R2MLDriver)
	 */
	public Object importObject(Object obj, MappingContext context, R2MLDriver driver)
			throws R2MLException {
		Condition condition = (Condition) obj;
		List<Prerequisite> body = new ArrayList<Prerequisite>();
		List<JAXBElement<? extends QfAndOrNafNegFormula>> terms = condition.getQfAndOrNafNegFormula();
		context.enter(this);
		for (JAXBElement<? extends QfAndOrNafNegFormula> item : terms) {
			XmlTypeHandler handler = driver.getHandlerByXmlType(item.getValue().getClass());
			Fact prereq = (Fact) handler.importObject(item.getValue(), context, driver);
			body.add(R2MLUtil.FactAsPrerequisite(prereq));
		}
		context.leave(this);
		return body;
	}

}
