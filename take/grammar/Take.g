/** 
 * A Take grammar for ANTLRv3. 
 */
grammar Take;

options {
	backtrack=true;
	memoize=true;
}

tokens {
	GlobalAnnotationKey;
	LocalAnnotationKey;
}

@lexer::header {
package nz.org.take.script.antlr;

import java.util.List;
import java.util.LinkedList;
}

@lexer::members {
// Workaround untill getText(), and setText() work as exprected within lexer fragments
private String lastAnnotationValue = null;

private List<Token> tokens = new LinkedList<Token>();

public void emit(Token token) {
    state.token = token;
    tokens.add(token);
}

public Token nextToken() {
    super.nextToken();
    
    if (tokens.size() == 0) {
        return Token.EOF_TOKEN;
    }
    
    return tokens.remove(0);
}
}

@parser::header {
package nz.org.take.script.antlr;

import java.util.Collection;
import java.util.List;

import nz.org.take.Aggregations;
import nz.org.take.AggregationFunction;
import nz.org.take.Annotatable;
import nz.org.take.Clause;
import nz.org.take.ComplexTerm;
import nz.org.take.Constant;
import nz.org.take.DefaultKnowledgeBase;
import nz.org.take.DerivationRule;
import nz.org.take.ExpressionException;
import nz.org.take.ExpressionPrerequisite;
import nz.org.take.ExternalFactStore;
import nz.org.take.Fact;
import nz.org.take.KnowledgeBase;
import nz.org.take.Predicate;
import nz.org.take.FactPrerequisite;
import nz.org.take.Prerequisite;
import nz.org.take.Query;
import nz.org.take.Term;
import nz.org.take.Variable;
import nz.org.take.script.DuplicateSymbolException;
import nz.org.take.script.IOState;
import nz.org.take.script.NamedElementTable;
import nz.org.take.script.NoSuchPredicateException;
import nz.org.take.script.QueryBuilder;
import nz.org.take.script.QueryDeclaration;
import nz.org.take.script.TakeGrammarException;
import nz.org.take.script.TermList;
}

@parser::members {
private KnowledgeBase knowledgeBase = new DefaultKnowledgeBase();

private NamedElementTable<AggregationFunction> aggregationTable = new NamedElementTable<AggregationFunction>();
private NamedElementTable<Constant> constantTable = new NamedElementTable<Constant>();
private NamedElementTable<Clause> clauseTable = new NamedElementTable<Clause>();
private NamedElementTable<Predicate> predicateTable = new NamedElementTable<Predicate>();
private NamedElementTable<Variable> variableTable = new NamedElementTable<Variable>();
private NamedElementTable<QueryDeclaration> queryTable = new NamedElementTable<QueryDeclaration>();


public Iterable<Constant> getConstants() {
	return constantTable.getValues();
}

public Iterable<Variable> getVariables() {
	return variableTable.getValues();
}

private void annotate(Collection<? extends Annotatable> annotatables) {
    for (Annotatable annotatable : annotatables)
        annotate(annotatable);
}

private void annotate(Annotatable annotatable) {
    annotatable.addAnnotations(getGlobalAnnotations());
    annotatable.addAnnotations(getLocalAnnotations());
}

private Map<String, String> getGlobalAnnotations() {
    return knowledgeBase.getAnnotations();
}

private Map<String, String> getLocalAnnotations() {
    return $declaration::annotations;
}

private boolean isValidPrerequisiteExpression(String expression) {
    try {
        createExpressionPrerequisite(expression);
        return true;
    } catch (TakeGrammarException e) {
        return false;
    }
}

private ExpressionPrerequisite createExpressionPrerequisite(String expression) throws TakeGrammarException {
    try {
    	return new ExpressionPrerequisite(expression, getActiveExpressionLanguage(), getDeclaredElementTypeMap());
    } catch (ExpressionException e) {
        throw new TakeGrammarException(e);
    }
}

private boolean isValidComplexTermExpression(String expression) {
    try {
        createComplexTerm(expression);
        return true;
    } catch (TakeGrammarException e) {
        return false;
    }
}

private ComplexTerm createComplexTerm(String expression) throws TakeGrammarException {
    try {
    	return new ComplexTerm(expression, getActiveExpressionLanguage(), getDeclaredElementTypeMap());
    } catch (ExpressionException e) {
        throw new TakeGrammarException(e);
    }
}

private String getActiveExpressionLanguage() {
    return "nz.org.take.mvel2.MVEL2ExpressionLanguage";
}

private Map<String, Class> getDeclaredElementTypeMap() {
    Map<String, Class> typeMap = new HashMap<String, Class>();
    
    for (Variable variable : variableTable.getValues()) {
        typeMap.put(variable.getName(), variable.getType());
    }
    
    for (Constant constant : constantTable.getValues()) {
        typeMap.put(constant.getName(), constant.getType());
    }
    
    return typeMap;
}

private AggregationFunction createAggregationFunction(String name, Aggregations aggregation, Variable variable, Fact query) throws TakeGrammarException {
    AggregationFunction function = new AggregationFunction();
    function.setName(name);
    function.setAggregation(aggregation);
    function.setVariable(variable);
    function.setQuery(query);
    
    try {
        aggregationTable.put(function);
    } catch (DuplicateSymbolException e) {
        throw new TakeGrammarException(e);
    }
    
    return function;
}

private Fact createFact(String name, Predicate predicate, TermList terms) throws TakeGrammarException {
    Fact fact = createAnonymousFact(predicate, terms);
    fact.setId(name);
    
    try {
        clauseTable.put(fact);
    } catch (DuplicateSymbolException e) {
        throw new TakeGrammarException(e);
    }
    
    return fact;
}

private Fact createAnonymousFact(Predicate predicate, TermList terms) {
    Fact fact = new Fact();
    fact.setPredicate(predicate);
    fact.setTerms(terms.toArray());
    
    return fact;
}

private DerivationRule createRule(String name, List<Prerequisite> body, Fact head) throws TakeGrammarException {
    DerivationRule rule = new DerivationRule();
    rule.setId(name);
    rule.setBody(body);
    rule.setHead(head);
    
    try {
        clauseTable.put(rule);
    } catch (DuplicateSymbolException e) {
        throw new TakeGrammarException(e);
    }
    
    return rule;
}

private Predicate createPredicate(String name, TermList params) throws TakeGrammarException {
    Predicate predicate = new Predicate();
    predicate.setName(name);
    predicate.setSlotTypes(params.getArrayOfTypes());
    
    try {
        predicateTable.put(predicate);
    } catch (DuplicateSymbolException e) {
        throw new TakeGrammarException(e);
    }
     
    return predicate;
}

private Constant createConstant(String name, Class type) throws TakeGrammarException {
    checkElementNameIsUnique(name);
    
    Constant constant = new Constant();
    constant.setRef(name);
    constant.setType(type);
    
    try {
        constantTable.put(constant);
    } catch (DuplicateSymbolException e) {
        throw new TakeGrammarException(e);
    }
    
    return constant;
}

private Variable createVariable(String name, Class type) throws TakeGrammarException {
    checkElementNameIsUnique(name);
    
    Variable variable = new Variable();
    variable.setName(name);
    variable.setType(type);
    
    try {
        variableTable.put(variable);
    } catch (DuplicateSymbolException e) {
        throw new TakeGrammarException(e);
    }
    
    return variable;
}

private void checkElementNameIsUnique(String name) throws TakeGrammarException {
    if (variableTable.containsName(name) || constantTable.containsName(name)) {
        throw new TakeGrammarException(new DuplicateSymbolException(name));
    }
}

private boolean isValidClass(String className) {
    try {
        createClass(className);
        return true;
    } catch (TakeGrammarException e) {
        return false;
    }
}

private Class createClass(String className) throws TakeGrammarException {
    try {
        return Class.forName(className);
    } catch (ClassNotFoundException e) {
        throw new TakeGrammarException(e);
    }
}

private QueryDeclaration createQueryDeclaration(Token predicate, List<IOState> parameterStates) throws TakeGrammarException {
    QueryDeclaration query = new QueryDeclaration(predicate, parameterStates);
    
    try {
        queryTable.put(query);
    } catch (DuplicateSymbolException e) {
        throw new TakeGrammarException(e);
    }
    
    return query;
}

private void buildQueries() throws TakeGrammarException {
    QueryBuilder queryBuilder = new QueryBuilder(predicateTable);
    Iterable<Query> queries = null;
    
    try {
        queries = queryBuilder.buildQueries(queryTable.getValues());
    } catch (NoSuchPredicateException e) {
        throw new TakeGrammarException(e);
    }
    
    for (Query query : queries)
        knowledgeBase.add(query);  
}

private void importClausesIntoKnowledgeBase() {
    for (Clause clause : clauseTable.getValues())
        knowledgeBase.add(clause);
}

}

script returns [KnowledgeBase knowledgeBase]
@after {
    buildQueries();
    importClausesIntoKnowledgeBase();
    
    $knowledgeBase = this.knowledgeBase;
}
    :   (   globalAnnotation
        |   declaration
        )*
    ;

declaration
scope { Map<String, String> annotations; }
@init { $declaration::annotations = new HashMap<String, String>(); }
    :   localAnnotation*
        (   variableDeclarations    { annotate($variableDeclarations.values); }
        |   constantDeclarations    { annotate($constantDeclarations.values); }
        |   aggregationDeclaration
        |   queryDeclaration        { annotate($queryDeclaration.value); }
        |   factStoreDeclaration    { annotate($factStoreDeclaration.value); }
        |   factDeclaration         { annotate($factDeclaration.value); }
        |   ruleDeclaration         { annotate($ruleDeclaration.value); }
        )
    ;

globalAnnotation
    :   GlobalAnnotationKey AnnotationValue { knowledgeBase.addAnnotation($GlobalAnnotationKey.text, $AnnotationValue.text); }
    ;

localAnnotation
    :   LocalAnnotationKey AnnotationValue { $declaration::annotations.put($LocalAnnotationKey.text, $AnnotationValue.text); }
    ;

variableDeclarations returns [Collection<Variable> values]
@init { $values = new ArrayList<Variable>(); }
    :   'var' type ids+=Identifier (',' ids+=Identifier)* Newline
        {
            for (Token id : (List<Token>)$ids) {
            	Variable variable = createVariable(id.getText(), $type.value);
            	$values.add(variable);
            }
        }
    ;

constantDeclarations returns [Collection<Constant> values]
@init { $values = new ArrayList<Constant>(); }
    :   'ref' type ids+=Identifier (',' ids+=Identifier)* Newline
        {
            for (Token id : (List<Token>)$ids) {
                Constant constant = createConstant(id.getText(), $type.value);
                $values.add(constant);
            }
        }
    ;
    
aggregationDeclaration returns [AggregationFunction value]
    :   'aggregation' Identifier '=' aggregateFunction variable predicate Newline
        {
            Fact query = createAnonymousFact($predicate.value, $predicate.terms);
            $value = createAggregationFunction($Identifier.text, $aggregateFunction.value, $variable.value, query);
        }
    ;

aggregateFunction returns [Aggregations value]
    :   'avg'   { $value = Aggregations.AVG; }
    |   'min'   { $value = Aggregations.MIN; }
    |   'max'   { $value = Aggregations.MAX; }
    |   'sum'   { $value = Aggregations.SUM; }
    |   'count' { $value = Aggregations.COUNT; }
    ;

variable returns [Variable value]
    :   variableName=Identifier { variableTable.containsName($variableName.text) }? { $value = variableTable.get($variableName.text); }
    ;

queryDeclaration returns [QueryDeclaration value]
    :   'query' queryName=Identifier '|' ioStates '|'
        {
            $value = createQueryDeclaration($queryName, $ioStates.values);
        }
    ;

ioStates returns [List<IOState> values]
@init { $values = new ArrayList<IOState>(); }
    :   firstState=ioState { $values.add($firstState.value); } (',' extraState=ioState { $values.add($extraState.value); })*
    ;
 
ioState returns [IOState value]
    :	'in'   { $value = IOState.IN; }
    |   'out'  { $value = IOState.OUT; }
    ;

factStoreDeclaration returns [ExternalFactStore value]
    :   'external'
    ;

factDeclaration returns [Fact value]
    :   factName=Identifier ':' predicate
        {
            $value = createFact($factName.text, $predicate.value, $predicate.terms);
        }
    ;

ruleDeclaration returns [DerivationRule value]
    :   ruleName=Identifier ':' 'if' body=prerequisites 'then' predicate
        {
            Fact head = createAnonymousFact($predicate.value, $predicate.terms);
            $value = createRule($ruleName.text, $body.values, head);
        }
    ;
    
prerequisites returns [List<Prerequisite> values]
@init { values = new ArrayList<Prerequisite>(); }
    :   firstPrerequisite=prerequisite { $values.add($firstPrerequisite.value); } ('and' extraPrerequisite=prerequisite { $values.add($extraPrerequisite.value); })*
    ;

prerequisite returns [Prerequisite value]
    :   factPrerequisite { $value = $factPrerequisite.value; }
    |   expressionPrerequisite { $value = $expressionPrerequisite.value; }
    ;

factPrerequisite returns [FactPrerequisite value]
    :   negatablePredicate { $value = new FactPrerequisite(); }
    ;

expressionPrerequisite returns [ExpressionPrerequisite value]
    :   Expression { isValidPrerequisiteExpression($Expression.text) }?
        {
            $value = createExpressionPrerequisite($Expression.text);
        }
    ;

negatablePredicate returns [Predicate value, TermList terms]
	:   'not' predicate { $predicate.value.setNegated(true); $value = $predicate.value; $terms = $predicate.terms; }
	|   predicate       { $value = $predicate.value; $terms = $predicate.terms; }
	;

predicate returns [Predicate value, TermList terms]
    :   existingPredicate  { $value = $existingPredicate.value; $terms = $existingPredicate.terms; }
    |   newPredicate            { $value = $newPredicate.value; $terms = $newPredicate.terms; }
    ;

existingPredicate returns [Predicate value, TermList terms]
	:   predicateName=Identifier '|' params=terms '|' { predicateTable.containsName($predicateName.text) }?
	    {
	        $value = predicateTable.get($predicateName.text);
	        $terms = $params.values;
	    }
	;
	
newPredicate returns [Predicate value, TermList terms]
    :   predicateName=Identifier '|' params=terms '|'
        {
            $value = createPredicate($predicateName.text, $params.values);
            $terms = $params.values;
        }
    ;

terms returns [TermList values]
@init { $values = new TermList(); }
    :   firstTerm=term { $values.add($firstTerm.value); } (',' extraTerm=term { $values.add($extraTerm.value); })*
    ;
    
term returns [Term value]
    :   complexTerm  { $value = $complexTerm.value; }
    ;

complexTerm returns [ComplexTerm value]
    :   t=( Expression | Identifier | HexLiteral | DecimalLiteral | OctalLiteral | FloatingPointLiteral | StringLiteral ) 
        { isValidComplexTermExpression($t.text) }? 
        {
        	$value = createComplexTerm($t.text);
        }
    ;

type returns [Class value]
    :   classOrInterfaceType  { $value = $classOrInterfaceType.value; }
    |   primitiveType         { $value = $primitiveType.value; }
    ;

classOrInterfaceType returns [Class value]
    :   qualifiedName { isValidClass($qualifiedName.text) }?
        {
            $value = createClass($qualifiedName.text);
        }
    ;

qualifiedName
    :   Identifier ('.' Identifier)*
    ;

primitiveType returns [Class value]
    :   'boolean'  { $value = Boolean.TYPE; }
    |   'char'     { $value = Character.TYPE; }
    |   'byte'     { $value = Byte.TYPE; }
    |   'short'    { $value = Short.TYPE; }
    |   'int'      { $value = Integer.TYPE; }
    |   'long'     { $value = Long.TYPE; }
    |   'float'    { $value = Float.TYPE; }
    |   'double'   { $value = Double.TYPE; }
    ;

HexLiteral : '0' ('x'|'X') HexDigit+ IntegerTypeSuffix? ;

DecimalLiteral : ('0' | '1'..'9' '0'..'9'*) IntegerTypeSuffix? ;

OctalLiteral : '0' ('0'..'7')+ IntegerTypeSuffix? ;

fragment
HexDigit : ('0'..'9'|'a'..'f'|'A'..'F') ;

fragment
IntegerTypeSuffix : ('l'|'L') ;

FloatingPointLiteral
    :   ('0'..'9')+ '.' ('0'..'9')* Exponent? FloatTypeSuffix?
    |   '.' ('0'..'9')+ Exponent? FloatTypeSuffix?
    |   ('0'..'9')+ Exponent FloatTypeSuffix?
    |   ('0'..'9')+ FloatTypeSuffix
    ;

fragment
Exponent : ('e'|'E') ('+'|'-')? ('0'..'9')+ ;

fragment
FloatTypeSuffix : ('f'|'F'|'d'|'D') ;

StringLiteral
    :  '"' ( EscapeSequence | ~('\\'|'"') )* '"'
    ;

fragment
EscapeSequence
    :   '\\' ('b'|'t'|'n'|'f'|'r'|'\"'|'\''|'\\')
    |   UnicodeEscape
    |   OctalEscape
    ;

fragment
OctalEscape
    :   '\\' ('0'..'3') ('0'..'7') ('0'..'7')
    |   '\\' ('0'..'7') ('0'..'7')
    |   '\\' ('0'..'7')
    ;

fragment
UnicodeEscape
    :   '\\' 'u' HexDigit HexDigit HexDigit HexDigit
    ;

GlobalAnnotation
    :   '@@' key=Identifier '=' value=AnnotationValue
        {
            emit(new CommonToken(GlobalAnnotationKey, $key.text));
            emit(new CommonToken(AnnotationValue, lastAnnotationValue));
        }
    ;

LocalAnnotation
    :   '@' key=Identifier '=' value=AnnotationValue
        {
            emit(new CommonToken(LocalAnnotationKey, $key.text));
            emit(new CommonToken(AnnotationValue, lastAnnotationValue));
        }
    ;

fragment
AnnotationValue
    :   { int startIndex = getCharIndex(); }
        ( options { greedy=false; } : . )+ Whitespace Newline
        {
            int endIndex = getCharIndex() - 1;
            int whitespace = $Whitespace.text.length() + $Newline.text.length();
            
            // Workaround untill getText(), and setText() work as exprected within lexer fragments
            lastAnnotationValue = input.substring(startIndex, endIndex - whitespace);
        }
    ;

Expression
    :   '\'' ( EscapeSequence | ~('\\'|'\'') )+ '\''
        {
            String expression = getText().substring(1, getText().length() - 1); // strip braces
            setText(expression.replaceAll("\\'", "'"));
        }
    ;

Identifier 
    :   Letter (Letter|IDDigit)*
    ;

fragment
Letter
    :   'a'..'z'
    |   'A'..'Z'
    |   '_'
    ;

fragment
IDDigit
    :   '0'..'9'
    ;

Newline
    :   '\r'? '\n'
    ;

Whitespace
@init { $channel=HIDDEN; }
    :   (' '|'\t')+
    ;

BlockComment
@init { $channel=HIDDEN; }
    :   '/*' ( options { greedy=false; } : . )* '*/'
    ;

LineComment
@init { $channel=HIDDEN; }
    :   '//' ( options { greedy=false; } : . )* Newline
    ;
