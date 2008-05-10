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

import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

import nz.org.take.Comparison;
import nz.org.take.Fact;
import nz.org.take.JPredicate;
import nz.org.take.TakeException;
import nz.org.take.Term;
import nz.org.take.r2ml.util.DataPredicates;
import nz.org.take.r2ml.util.R2MLUtil;
import de.tu_cottbus.r2ml.AttributeFunctionTerm;
import de.tu_cottbus.r2ml.AttributionAtom;
import de.tu_cottbus.r2ml.DatatypePredicateAtom;
import de.tu_cottbus.r2ml.ObjectTerm;
import de.tu_cottbus.r2ml.TypedLiteral;

/**
 * @author Bastian Schenke (bastian.schenke@googlemail.com) TODO implement this
 *         Handler
 */
class DatatypePredicateAtomHandler implements XmlTypeHandler {

	/**
	 * Map a DatatypePredicateAtom to a Comparison.
	 * 
	 * @param obj
	 *            a
	 * @return a
	 * 
	 * @see nz.org.take.r2ml.XmlTypeHandler#importObject(java.lang.Object,
	 *      nz.org.take.r2ml.R2MLDriver)
	 */
	public Object importObject(Object obj) throws R2MLException {
		DatatypePredicateAtom atom = (DatatypePredicateAtom) obj;

		if (atom.getDataArguments().getDataTerm().size() != 2) {
			throw new R2MLException(
					"DatatypePredicates are supported only for exactly two arguments.");
		}

		Fact fact = R2MLUtil.newFact();

		String symbol = DataPredicates.getComparisonSymbol(atom
				.getDatatypePredicateID());

		if (R2MLUtil.isBooleanPredicate(atom)) {
			buildAsJPredicate(atom, fact, symbol);
			return fact;
		}

		Term arg0 = null;
		Term arg1 = null;

		XmlTypeHandler h0 = R2MLDriver.get().getHandlerByXmlType(
				atom.getDataArguments().getDataTerm().get(0).getDeclaredType());
		XmlTypeHandler h1 = R2MLDriver.get().getHandlerByXmlType(
				atom.getDataArguments().getDataTerm().get(1).getDeclaredType());
		arg1 = (Term) h0.importObject(atom.getDataArguments().getDataTerm()
				.get(0).getValue());
		arg0 = (Term) h1.importObject(atom.getDataArguments().getDataTerm()
				.get(1).getValue());

		Comparison c = null;
		try {
			c = new Comparison(symbol);
			if ((Number.class.isAssignableFrom(arg0.getType()) && Number.class
					.isAssignableFrom(arg1.getType()))
					|| (arg0.getType().isPrimitive()
							&& arg1.getType().isPrimitive()
							&& arg0.getType() != Boolean.class && arg1
							.getType() != Boolean.class)) {
				if ((symbol.equals("==") || symbol.equals("!="))
						&& R2MLUtil.isNegated(atom))
					c = new Comparison(symbol.equals("==") ? "!=" : "==");
				if (!(symbol.equals("==") || symbol.equals("!=")))
					c.setNegated(R2MLUtil.isNegated(atom));

				c.setTypes(new Class[] { arg1.getType(), arg0.getType() });
				c.setSlotNames(R2MLDriver.get().getNameMapper().getSlotNames(
						atom.getDatatypePredicateID()));
				fact.setId(c.getName());
				fact.setPredicate(c);
			} else if ((symbol.equals("==") || symbol.equals("!="))
					&& ((arg0.getType() == Boolean.class && arg1.getType() == Boolean.class) || (arg0
							.getType() == String.class && arg1.getType() == String.class))) {
				// types are boolean or String and comparison symbol is == or !=
				if (R2MLUtil.isNegated(atom))
					try {
						c = new Comparison(symbol.equals("==") ? "!=" : "==");
					} catch (TakeException e) {
						throw new R2MLException(
								"unable to create comparison object", e);
					}

				c.setTypes(new Class[] { arg1.getType(), arg0.getType() });
				c.setSlotNames(R2MLDriver.get().getNameMapper().getSlotNames(
						atom.getDatatypePredicateID()));
				fact.setId(c.getName());
				fact.setPredicate(c);
				// JPredicate jp = new JPredicate();
				// jp.setNegated(symbol.equals("==") ? false : true);
				// jp.setMethod(R2MLUtil.getMethod(EQUALITY_CHECK_METHOD, new
				// Term[]
				// {
				// arg0, arg1 }));
				// fact.setId(jp.getName());
				// fact.setPredicate(jp);
			} else {
				throw new R2MLException("Unable to create Comparison.");
			}

		} catch (TakeException e) {
			throw new R2MLException("unable to create Comparison with symbol "
					+ symbol, e);
			// e.printStackTrace();
		}
		fact.setTerms(new Term[] { arg0, arg1 });

		return fact;
	}

	/**
	 * @param atom
	 * @param fact
	 * @param symbol
	 * @throws R2MLException
	 */
	private void buildAsJPredicate(DatatypePredicateAtom atom, Fact fact,
			String symbol) throws R2MLException {
		TypedLiteral tl = null;
		AttributeFunctionTerm aft = null;
		ObjectTerm objT = null;
		if (atom.getDataArguments().getDataTerm().get(0).getDeclaredType() == TypedLiteral.class) {
			aft = (AttributeFunctionTerm) (atom.getDataArguments()
					.getDataTerm().get(1).getValue());
			tl = (TypedLiteral) (atom.getDataArguments().getDataTerm().get(0)
					.getValue());
			objT = aft.getContextArgument().getObjectTerm().getValue();
		} else {
			aft = (AttributeFunctionTerm) (atom.getDataArguments()
					.getDataTerm().get(0).getValue());
			tl = (TypedLiteral) (atom.getDataArguments().getDataTerm().get(1)
					.getValue());
			objT = aft.getContextArgument().getObjectTerm().getValue();
		}
		// value of the typed literal
		boolean tlv = new Boolean(tl.getLexicalValue());
		XmlTypeHandler handler = R2MLDriver.get().getHandlerByXmlType(
				aft.getContextArgument().getObjectTerm().getDeclaredType());
		Term domain = (Term) handler.importObject(objT);

		String[] slotNames = null;// R2MLDriver.get().getNameMapper().getSlotNames(atom.getDatatypePredicateID());
		fact.setPredicate(AttributionAtomHandler.buildJPredciate(domain, aft
				.getAttributeID().getLocalPart(), isNegated(atom, symbol, tlv),
				slotNames));
		fact.setTerms(new Term[] { domain });
	}

	private boolean isNegated(DatatypePredicateAtom atom, String symbol,
			boolean tlv) {
		boolean negated = R2MLUtil.isNegated(atom);
		if (!tlv) {
			// flip negated if comparing to false
			negated = negated ? false : true;
		}
		if (symbol.equals("!=")) {
			// flip negated if the symbol is not-equal
			negated = negated ? false : true;
		}
		return negated;
	}

}
