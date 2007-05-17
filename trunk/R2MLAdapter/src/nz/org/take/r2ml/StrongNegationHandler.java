package nz.org.take.r2ml;

import nz.org.take.AbstractPredicate;
import nz.org.take.KnowledgeElement;
import de.tu_cottbus.r2ml.LogicalFormula;
import de.tu_cottbus.r2ml.StrongNegation;

public class StrongNegationHandler implements XmlTypeHandler {

	public Object importObject(Object obj, MappingContext context, R2MLDriver driver)
			throws R2MLException {
		context.enter(this);
		StrongNegation neg = (StrongNegation) obj;
		LogicalFormula argument = neg.getLogicalFormula().getValue();
		XmlTypeHandler handler = driver
				.getHandlerByXmlType(argument.getClass());
		KnowledgeElement ke = null;
		Object toNeg = null;
		try {
			toNeg = handler.importObject(argument, context, driver);
			ke = (KnowledgeElement) toNeg;
		} catch (ClassCastException e) {
			throw new R2MLException(
					"Only elements that are mapped to a KnowledgeElement could be negated (actual type is '"
							+ toNeg.getClass().getSimpleName() + "').");
		}
		if (ke.getPredicate() instanceof AbstractPredicate) {
			((AbstractPredicate) ke.getPredicate()).setNegated(true);
		} else {
			throw new R2MLException(
					"Negation of Predicates that do not extend AbstractPredicate are not supported.",
					R2MLException.FEATURE_NOT_SUPPORTED);
		}
		context.leave(this);
		return ke;
	}
}