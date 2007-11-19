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

import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;

import nz.org.take.KnowledgeBase;
import nz.org.take.r2ml.reference.AssociationAsReferenceResolvPolicy;
import nz.org.take.r2ml.reference.CheckOnlyNormalizer;
import nz.org.take.r2ml.reference.SimpleAssociationResolvPolicy;
import nz.org.take.r2ml.reference.DefaultDatatypeMapper;
import nz.org.take.r2ml.reference.DefaultNameMapper;

import de.tu_cottbus.r2ml.*;

/**
 * @author Bastian Schenke (bastian.schenke@googlemail.com)
 * 
 */
public class R2MLDriver {
	
	public static final String ID = "R2MLAdapter v0.1";

	public static final String R2ML_NS = "http://www.rewerse.net/I1/2006/R2ML";

	public static final String R2ML_VOCABULARY_NS = "http://www.rewerse.net/I1/2006/R2ML/R2MLV";
	
	private static R2MLDriver singletonDriver = null;

	private JAXBContext jc = null;

	private Unmarshaller um = null;

	private Map<Class, XmlTypeHandler> typeHandler = new HashMap<Class, XmlTypeHandler>();

	private boolean initialized = false;

	private Normalizer normy = null;
	private NameMapper nameMapper = null;
	private DatatypeMapper datty = null;

	public Logger logger = Logger.getLogger("nz.org.take.r2ml");

	private AssociationResolvPolicy associationResolvPolicy = new AssociationAsReferenceResolvPolicy();

	private RuleBase ruleBase;
	
	private R2MLDriver () {
		super();
	}
	
	public static R2MLDriver get() {
		if (singletonDriver == null) {
			singletonDriver = new R2MLDriver();
			singletonDriver.addHandlers();
		}
		return singletonDriver;
	}
	
	/**
	 * @param ruleBase a RuleBase unmarshalled out of a R2ML/XML file
	 * @return a KnowledgeBase representing the input RuleBase
	 * @throws R2MLException
	 */
	public KnowledgeBase importKB(RuleBase ruleBase) throws R2MLException {
		this.ruleBase = ruleBase;
		XmlTypeHandler rbHandler = getHandlerByXmlType(ruleBase.getClass());
		
		MappingContext context = new MappingContext();
		KnowledgeBase kb = (KnowledgeBase) rbHandler.importObject(ruleBase);
		
		assert context.isClean();
		return kb;
		
	}

	/**
	 * @param key
	 * @return
	 * @throws R2MLException
	 */
	public XmlTypeHandler getHandlerByXmlType(Class<? extends java.lang.Object> key) {

		XmlTypeHandler handler = typeHandler.get(key);
		if (logger.isDebugEnabled() && (handler == null)) {
			logger.warn("XmlTypeHandler not found for "
					+ key.toString());
		}
		return handler;

	}

	public NameMapper getNameMapper() {
		if (nameMapper == null)
			nameMapper = new DefaultNameMapper();
		return nameMapper;
	}

	public void setNameMapper(NameMapper newSlotty) {
		this.nameMapper = newSlotty;
	}

	public DatatypeMapper getDatatypeMapper() {
		if (datty == null)
			datty = new DefaultDatatypeMapper();
		return datty;
	}
	
	public void setDatatypeMapper(DatatypeMapper newDatty) {
		this.datty = newDatty;
	}
	
	private void add(Class clazz, XmlTypeHandler handler) {
		typeHandler.put(clazz, handler);
	}

	public Normalizer getNormalizer() {
		if (normy == null)
			normy = new CheckOnlyNormalizer();
		return this.normy;
	}

	public void setNormalizer(Normalizer normy) {
		this.normy = normy;
	}

	private void addHandlers() {

		add(AssociationAtom.class, new AssociationAtomHandler());
		add(AttributionAtom.class, new AttributionAtomHandler());
		add(AttributeFunctionTerm.class, new AttributeFunctionTermHandler());
		add(Condition.class, new ConditionHandler());
		add(Conjunction.class, new ConjunctionHandler());
		add(DataClassificationAtom.class, new DataClassificationAtomHandler());
		add(DatatypeFunctionTerm.class, new DatatypeFunctionTermHandler());
		add(DatatypePredicateAtom.class, new DatatypePredicateAtomHandler());
		add(DataVariable.class, new DataVariableHandler());
		add(DataValue.class, new DataValueHandler());
		add(DerivationRule.class, new DerivationRuleHandler());
		add(DerivationRuleSet.class, new DerivationRuleSetHandler());
		add(Disjunction.class, new DisjunctionHandler());
		add(Documentation.class, new DocumentationHandler());
		add(EqualityAtom.class, new EqualityAtomHandler());
		add(GenericAtom.class, new GenericAtomHandler());
		add(InequalityAtom.class, new InequalityAtomHandler());
		add(IntegrityRuleSet.class, new IntegrityRuleSetHandler());
		add(NegationAsFailure.class, new NegationAsFailureHandler());
		add(Negation.class, new NegationHandler());
		add(ObjectClassificationAtom.class,
				new ObjectClassificationAtomHandler());
		add(ObjectDescriptionAtom.class, new ObjectDescriptionAtomHandler());
		add(ObjectVariable.class, new ObjectVariableHandler());
		add(PlainLiteral.class, new PlainLiteralHandler());
		add(ProductionRuleSet.class, new ProductionRuleSetHandler());
		add(PropertyAtom.class, new PropertyAtomHandler());
		add(QfConjunction.class, new QfConjunctionHandler());
		add(QfDisjunction.class, new QfDisjunctionHandler());
		add(ReactionRuleSet.class, new ReactionRuleSetHandler());
		add(ReferencePropertyAtom.class, new ReferencePropertyAtomHandler());
		add(RuleBase.class, new RuleBaseHandler());
		add(RuleText.class, new RuleTextHandler());
		add(StrongNegation.class, new StrongNegationHandler());
		add(Subject.class, new SubjectHandler());
		add(TypedLiteral.class, new TypedLiteralHandler());

	}

	public void setAssociationResolvPolicy(AssociationResolvPolicy policy) {
		this.associationResolvPolicy =  policy;
	}
	public AssociationResolvPolicy getAssociationResolvPolicy() {
		return associationResolvPolicy;
	}

	protected boolean isInitialized() {
		return initialized;
	}

	public RuleBase getRuleBase() {
		return ruleBase;
	}

	public void setRuleBase(RuleBase ruleBase) {
		this.ruleBase = ruleBase;
	}

}