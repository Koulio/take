/*
 * Copyright (C) 2007 Bastian Schenke (bastian.schenke(at)gmail.com) and 
 * <a href="http://www-ist.massey.ac.nz/JBDietrich" target="_top">Jens Dietrich</a>
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package nz.org.take.r2ml;

import java.util.Collection;
import java.util.List;

import javax.xml.bind.JAXBElement;

import nz.org.take.DefaultKnowledgeBase;
import nz.org.take.KnowledgeBase;
import nz.org.take.KnowledgeElement;

import de.tu_cottbus.r2ml.RuleBase;
import de.tu_cottbus.r2ml.RuleSet;

/**
 * @author Bastian Schenke (bastian.schenke@googlemail.com)
 * 
 */
class RuleBaseHandler implements XmlTypeHandler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nz.org.take.r2ml.XmlTypeHandler#importObject(java.lang.Object,
	 *      nz.org.take.r2ml.R2MLDriver)
	 */
	@SuppressWarnings("unchecked")
	public Object importObject(Object obj, MappingContext context, R2MLDriver driver)
			throws R2MLException {
		RuleBase ruleBase = (RuleBase) obj;
		context.enter(this);
		KnowledgeBase kb = new DefaultKnowledgeBase();
		for (JAXBElement<? extends RuleSet> ruleSet : ruleBase.getRuleSet()) {
			XmlTypeHandler ruleSetHandler = null;
			ruleSetHandler = driver.getHandlerByXmlType(ruleSet.getValue()
					.getClass());
			Collection<KnowledgeElement> rules = null;
			rules = (List<KnowledgeElement>) ruleSetHandler.importObject(
					ruleSet.getValue(), context, driver);
			for (KnowledgeElement rule : rules) {
				kb.add(rule);
			}
		}
		context.leave(this);

		return kb;
	}

}
