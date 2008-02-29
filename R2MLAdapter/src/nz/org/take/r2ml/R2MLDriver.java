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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import nz.org.take.KnowledgeBase;
import nz.org.take.r2ml.reference.AssociationAsReferenceResolvPolicy;
import nz.org.take.r2ml.reference.CheckOnlyNormalizer;
import nz.org.take.r2ml.reference.DefaultDatatypeMapper;
import nz.org.take.r2ml.reference.DefaultNameMapper;
import nz.org.take.r2ml.util.ReplacePropertyFunctionTermFilter;
import nz.org.take.r2ml.util.RuleBaseFilter;
import nz.org.take.r2ml.util.TypeVariablesFilter;

import org.apache.commons.jxpath.JXPathContext;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import de.tu_cottbus.r2ml.AssociationAtom;
import de.tu_cottbus.r2ml.AttributeFunctionTerm;
import de.tu_cottbus.r2ml.AttributionAtom;
import de.tu_cottbus.r2ml.Conclusion;
import de.tu_cottbus.r2ml.Condition;
import de.tu_cottbus.r2ml.Conjunction;
import de.tu_cottbus.r2ml.DataClassificationAtom;
import de.tu_cottbus.r2ml.DataValue;
import de.tu_cottbus.r2ml.DataVariable;
import de.tu_cottbus.r2ml.DatatypeFunctionTerm;
import de.tu_cottbus.r2ml.DatatypePredicateAtom;
import de.tu_cottbus.r2ml.DerivationRule;
import de.tu_cottbus.r2ml.DerivationRuleSet;
import de.tu_cottbus.r2ml.Disjunction;
import de.tu_cottbus.r2ml.Documentation;
import de.tu_cottbus.r2ml.EqualityAtom;
import de.tu_cottbus.r2ml.GenericAtom;
import de.tu_cottbus.r2ml.InequalityAtom;
import de.tu_cottbus.r2ml.IntegrityRuleSet;
import de.tu_cottbus.r2ml.Negation;
import de.tu_cottbus.r2ml.NegationAsFailure;
import de.tu_cottbus.r2ml.ObjectClassificationAtom;
import de.tu_cottbus.r2ml.ObjectDescriptionAtom;
import de.tu_cottbus.r2ml.ObjectVariable;
import de.tu_cottbus.r2ml.PlainLiteral;
import de.tu_cottbus.r2ml.ProductionRuleSet;
import de.tu_cottbus.r2ml.PropertyAtom;
import de.tu_cottbus.r2ml.QfConjunction;
import de.tu_cottbus.r2ml.QfDisjunction;
import de.tu_cottbus.r2ml.ReactionRuleSet;
import de.tu_cottbus.r2ml.ReferencePropertyAtom;
import de.tu_cottbus.r2ml.ReferencePropertyFunctionTerm;
import de.tu_cottbus.r2ml.RuleBase;
import de.tu_cottbus.r2ml.RuleText;
import de.tu_cottbus.r2ml.StrongNegation;
import de.tu_cottbus.r2ml.Subject;
import de.tu_cottbus.r2ml.TypedLiteral;

/**
 * @author Bastian Schenke (bastian.schenke@googlemail.com)
 * 
 */
public class R2MLDriver {
	
	
	/**
	 * default mode 
	 */
	public static final int DEFAULT_PROPERTY_MODE = 0;
	
	/**
	 * Properties can be infered even if there are properties in the domainclasses
	 */
	public static final int INFER_PROPERTIES_MODE = 1;

	private int propertyMode = DEFAULT_PROPERTY_MODE; 

	public static final String ID = "R2MLAdapter v0.1";

	public static final String R2ML_NS = "http://www.rewerse.net/I1/2006/R2ML";

	public static final String R2ML_VOCABULARY_NS = "http://www.rewerse.net/I1/2006/R2ML/R2MLV";

	private static R2MLDriver singletonDriver = null;

	private Map<Class, XmlTypeHandler> typeHandler = new HashMap<Class, XmlTypeHandler>();

	private boolean initialized = false;

	private Normalizer normy = null;

	private NameMapper nameMapper = null;

	private DatatypeMapper datty = null;

	public Logger logger = Logger.getLogger("r2mlAdapter");

	private AssociationResolvPolicy associationResolvPolicy = new AssociationAsReferenceResolvPolicy();

	private RuleBase ruleBase;

	private RuleBaseHandler rbHandler;

	private List<RuleBaseFilter> ruleBaseFilter = new ArrayList<RuleBaseFilter>();

	private R2MLDriver() {
		super();
	}

	public static R2MLDriver get() {
		if (singletonDriver == null) {
			singletonDriver = new R2MLDriver();
			singletonDriver.addHandlers();
			singletonDriver.addFilter();
			singletonDriver.logger.setLevel(Level.ALL);
		}

		return singletonDriver;
	}

	/**
	 * @param ruleBase
	 *            a RuleBase unmarshalled out of a R2ML/XML file
	 * @return a KnowledgeBase representing the input RuleBase
	 * @throws R2MLException
	 */
	public KnowledgeBase importKB(RuleBase rb) throws R2MLException {
		MappingContext.reset();
		if (logger.isDebugEnabled())
			logger.debug("entering RuleBaseHandler");
		this.ruleBase = rb;
		for (RuleBaseFilter filter : ruleBaseFilter) {
			if (logger.isDebugEnabled()) {
				logger.debug("applying filter " + filter.getName());
			}
			filter.repair(ruleBase);
		}

		rbHandler = (RuleBaseHandler) getHandlerByXmlType(ruleBase.getClass());

		KnowledgeBase kb = (KnowledgeBase) rbHandler.importObject(ruleBase);

		assert MappingContext.get().isClean();
		return kb;

	}

	void addRuleToRuleBase(nz.org.take.DerivationRule rule)
			throws R2MLException {
		if (rbHandler == null) {
			throw new R2MLException(
					"Unable to add rule to non-existing rulebase.");
		}
		rbHandler.addRuleToKB(rule);
	}

	/**
	 * @param key
	 * @return
	 * @throws R2MLException
	 */
	public XmlTypeHandler getHandlerByXmlType(
			Class<? extends java.lang.Object> key) {

		XmlTypeHandler handler = typeHandler.get(key);
		if (logger.isDebugEnabled() && (handler == null)) {
			logger.warn("XmlTypeHandler not found for " + key.toString());
		}
		if (handler == null)
			throw new NullPointerException("There must be a handler for class " + key.getCanonicalName());
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

		typeHandler = new HashMap<Class, XmlTypeHandler>();
		add(AssociationAtom.class, new AssociationAtomHandler());
		add(AttributionAtom.class, new AttributionAtomHandler());
		add(AttributeFunctionTerm.class, new AttributeFunctionTermHandler());
		add(Conclusion.class, new ConclusionHandler());
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
		add(ReferencePropertyFunctionTerm.class,
				new ReferencePropertyFunctionTermHandler());
		add(RuleBase.class, new RuleBaseHandler());
		add(RuleText.class, new RuleTextHandler());
		add(StrongNegation.class, new StrongNegationHandler());
		add(Subject.class, new SubjectHandler());
		add(TypedLiteral.class, new TypedLiteralHandler());

	}

	private void addFilter() {
		ruleBaseFilter = new ArrayList<RuleBaseFilter>(); 

		if (propertyMode == INFER_PROPERTIES_MODE) {
			ruleBaseFilter.add(new ReplacePropertyFunctionTermFilter());
		}
		ruleBaseFilter.add(new TypeVariablesFilter());
	}

	public void setAssociationResolvPolicy(AssociationResolvPolicy policy) {
		this.associationResolvPolicy = policy;
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

	public int getPropertyMode() {
		return propertyMode;
	}

	public void setPropertyMode(int newPropertyMode) {
		this.propertyMode = newPropertyMode;
		addFilter();
	}

}
