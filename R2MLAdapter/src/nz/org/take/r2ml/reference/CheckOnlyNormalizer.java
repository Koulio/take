/*
 * Copyright 2007 Bastian Schenke Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
 */
package nz.org.take.r2ml.reference;

import javax.xml.bind.JAXBElement;

import nz.org.take.r2ml.Normalizer;
import nz.org.take.r2ml.R2MLException;

import de.tu_cottbus.r2ml.Atom;
import de.tu_cottbus.r2ml.Condition;
import de.tu_cottbus.r2ml.QfAndOrNafNegFormula;
import de.tu_cottbus.r2ml.QfConjunction;
import de.tu_cottbus.r2ml.QfDisjunction;
import de.tu_cottbus.r2ml.QfNegation;
import de.tu_cottbus.r2ml.QfNegationAsFailure;
import de.tu_cottbus.r2ml.QfStrongNegation;

/**
 * This normalizer implementation checks if the provided formula is in DNF
 * without changing it. In the case of a non DNF formula an Exception is thrown.
 * 
 * @author Bastian Schenke (bastian.schenke@googlemail.com)
 * 
 */
public class CheckOnlyNormalizer implements Normalizer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nz.org.take.r2ml.Normalizer#normalize(de.tu_cottbus.r2ml.QfAndOrNafNegFormula)
	 */
	public Condition normalize(Condition condition)
			throws R2MLException {

		for (JAXBElement<? extends QfAndOrNafNegFormula> item : condition.getQfAndOrNafNegFormula()) {
			QfAndOrNafNegFormula formula = item.getValue();
			if (formula instanceof QfDisjunction && condition.getQfAndOrNafNegFormula().size() != 1)
				throw new R2MLException("Formulas are only supported in DNF. Add a Normalizer for more support.", R2MLException.FEATURE_NOT_SUPPORTED);
			else if (formula instanceof QfDisjunction) {
				for (JAXBElement<? extends QfAndOrNafNegFormula> part: ((QfDisjunction)formula).getQfAndOrNafNegFormula())
					check(part.getValue());
			} else {
				check(formula);
			}
		} // for
		return condition;
	} // normalize()

	/**
	 * Check if the formula contains any Disjunctions. If it does an Exception
	 * is thrown.
	 * 
	 * @param formula
	 * @throws R2MLException
	 *             if the formula is not in DNF
	 */
	private void check(QfAndOrNafNegFormula formula) throws R2MLException {

		if (formula instanceof Atom) {
			return;
		} else if (formula instanceof QfDisjunction)
			throw new R2MLException("Formulas are only accepted in DNF.",
					R2MLException.FEATURE_NOT_SUPPORTED);
		else if (formula instanceof QfConjunction)
			for (JAXBElement<? extends QfAndOrNafNegFormula> conjunct : ((QfConjunction) formula).getQfAndOrNafNegFormula())
				check(conjunct.getValue());
		else if (formula instanceof QfNegation)
			return;
		else if (formula instanceof QfStrongNegation)
			check(((QfStrongNegation)formula).getAtom().getValue());
		else if (formula instanceof QfNegationAsFailure)
			check(((QfNegationAsFailure)formula).getAtom().getValue());
		else
			throw new R2MLException("Unknown element type in formula", R2MLException.FEATURE_NOT_SUPPORTED);
		
	} // check()

} // class CheckOnlyNormalizer