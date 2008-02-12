package nz.org.take.r2ml;

import nz.org.take.Fact;
import de.tu_cottbus.r2ml.Atom;
import de.tu_cottbus.r2ml.Conclusion;

/**
 * @author Bastian Schenke (bastian.schenke(at)googlemail.com)
 *
 */
public class ConclusionHandler implements XmlTypeHandler {

	/**
	 * @param Object obj a Conclusion object
	 * 
	 * @return a Fact
	 */
	public Object importObject(Object obj) throws R2MLException {
		MappingContext.get().enter(this);
		Fact conclusionFact = null;
		try {
			Atom atom = ((Conclusion) obj).getAtom().getValue();
			XmlTypeHandler handler = R2MLDriver.get().getHandlerByXmlType(atom.getClass());
			conclusionFact = (Fact) handler.importObject(atom);
		} catch (R2MLException e) {
			e.printStackTrace();
		}
		MappingContext.get().leave(this);
		return conclusionFact;
	}

}
