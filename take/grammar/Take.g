/** 
 * A Take grammar for ANTLRv3. 
 */
grammar Take;

options {
backtrack=true;
memoize=true;
}

@lexer::header {
package nz.org.take.script.antlr;
}

@parser::header {
package nz.org.take.script.antlr;

import java.util.HashMap;
import java.util.Map;
import nz.org.take.Aggregations;
import nz.org.take.Constant;
import nz.org.take.DefaultKnowledgeBase;
import nz.org.take.DerivationRule;
import nz.org.take.Fact;
import nz.org.take.KnowledgeBase;
import nz.org.take.Predicate;
import nz.org.take.Prerequisite;
import nz.org.take.Term;
import nz.org.take.Variable;
}

@parser::members {
private KnowledgeBase knowledgeBase = new DefaultKnowledgeBase();

private Map<String, Variable> variables = new HashMap<String, Variable>();
private Map<String, Constant> references = new HashMap<String, Constant>();

private Variable createVariable(String id, Class type) {
    Variable variable = new Variable();
    variable.setName(id);
    variable.setType(type);
    
    variables.put(id, variable);
    
    return variable;
}

private Constant createReference(String id, Class type) {
    Constant reference = new Constant();
    reference.setRef(id);
    reference.setType(type);
    
    references.put(id, reference);
    
    return reference;
}

private Map<String, String> getGlobalAnnotations() {
    return knowledgeBase.getAnnotations();
}
}

script returns [KnowledgeBase knowledgeBase]
@after { $knowledgeBase = knowledgeBase; }
    :   (   globalAnnotation
        |   declaration
        |   comment
        )*
    ;

declaration
scope { Map<String, String> annotations; }
@init { $declaration::annotations = new HashMap<String, String>(); }
    :   localAnnotation*
        (   variableDeclarations
        |   referenceDeclarations
        |   aggregationDeclaration
        |   queryDeclaration
        |   factStoreDeclaration
        |   factDeclaration
        |   ruleDeclaration
        )
    ;

globalAnnotation
    :   '@@' key=Identifier '=' value=Line { knowledgeBase.addAnnotation($key.text, $value.text); }
    ;

localAnnotation
    :   '@' key=Identifier '=' value=Line  { $declaration::annotations.put($key.text, $value.text); }
    ;

variableDeclarations
    :   'var' type ids+=Identifier (',' ids+=Identifier)* Newline
        {
            for (Object id : $ids) {
                Variable variable = createVariable((String)id, $type.type);
                variable.addAnnotations(getGlobalAnnotations());
                variable.addAnnotations($declaration::annotations);
            }
        }
    ;

referenceDeclarations
    :   'ref' type ids+=Identifier (',' ids+=Identifier)* Newline
        {
            for (Object id : $ids) {
                Constant reference = createReference((String)id, $type.type);
                reference.addAnnotations(getGlobalAnnotations());
                reference.addAnnotations($declaration::annotations);
            }
        }
    ;

aggregationDeclaration
    :   'aggregation' Identifier '=' aggregateFunction variable predicate Newline
    ;

aggregateFunction returns [Aggregations aggregation]
    :   'avg'    { $aggregation = Aggregations.AVG; }
    |   'min'    { $aggregation = Aggregations.MIN; }
    |   'max'    { $aggregation = Aggregations.MAX; }
    |   'sum'    { $aggregation = Aggregations.SUM; }
    |   'count'  { $aggregation = Aggregations.COUNT; }
    ;

queryDeclaration
    :   'query'
    ;

factStoreDeclaration
    :   'external'
    ;

factDeclaration
    :   Identifier ':' predicate
    ;

ruleDeclaration
    :   Identifier ':' 'if' prerequisite 'then' predicate
    ;
    
prerequisite returns [Prerequisite prequisite]
    :   predicate ('and' predicate)*
    ;

predicate returns [Predicate predicate]
    :   negated='not'? Identifier '[' terms ']'
        {
            boolean isNegated = (negated != null);
        }
    ;
    
terms returns [List<Term> terms]
    :   term (',' term)*
    ;
    
term returns [Term term]
    :   constant     { $term = $constant.value; }  
    |   variable     { $term = $variable.value; }
    |   reference    { $term = $reference.value; }
//  |   complexTerm  { $term = $complexTerm.value; }
    ;
constant returns [Constant value]
    :   number       { $value = new Constant(); $value.setObject($number.value); }
    |   string       { $value = new Constant(); $value.setObject($string.value); }
    ;

number returns [Number value]
    :   IntegerLiteral  { return Integer.parseInt($IntegerLiteral.text); }
    |   LongLiteral     { return Long.parseLong($LongLiteral.text); }
    |   FloatLiteral    { return Double.parseDouble($FloatLiteral.text); }
    ;

string returns [String value]
    :   '"' contents=( options { greedy=false; }: . )* '"' { $value = $contents.text; }
    ;

variable returns [Variable value]
    :   Identifier { variables.containsKey($Identifier.text) }? => { $value = variables.get($Identifier.text); }
    ;

reference returns [Constant value]
    :   Identifier { references.containsKey($Identifier.text) }? => { $value = references.get($Identifier.text); }
    ;

type returns [Class type]
    :   classOrInterfaceType  { $type = $classOrInterfaceType.type; }
    |   primitiveType         { $type = $primitiveType.type; }
    ;

classOrInterfaceType returns [Class type]
    :   Identifier ('.' Identifier )*
    ;

primitiveType returns [Class type]
    :   'boolean'  { $type = Boolean.TYPE; }
    |   'char'     { $type = Character.TYPE; }
    |   'byte'     { $type = Byte.TYPE; }
    |   'short'    { $type = Short.TYPE; }
    |   'int'      { $type = Integer.TYPE; }
    |   'long'     { $type = Long.TYPE; }
    |   'float'    { $type = Float.TYPE; }
    |   'double'   { $type = Double.TYPE; }
    ;

comment
    :   LineComment
    |   BlockComment
    ;

Identifier 
    :   Letter (Letter|IDDigit)*
    ;

fragment
Letter
    :  'a'..'z'
    |  'A'..'Z'
    |  '_'
    ;

fragment
IDDigit
    :  '0'..'9'
    ;

IntegerLiteral
    :   '0' | '1'..'9' '0'..'9'*
    ;
    
LongLiteral
    :   IntegerLiteral LongTypeSyffix
    ;

fragment
LongTypeSyffix 
    :   'l'|'L' ;

FloatLiteral
    :   IntegerLiteral FloatTypeSuffix
    |   IntegerLiteral '.' ('0'..'9')* FloatTypeSuffix?
    |	'.' ('0'..'9')+ FloatTypeSuffix?
    ;

fragment
FloatTypeSuffix
    :   'f'|'F'|'d'|'D' ;

BlockComment
@init { $channel=HIDDEN; }
    :   '/*' ( options { greedy=false; } : . )* '*/'
    ;

LineComment
@init { $channel=HIDDEN; }
    :   '//' Line
    ;

fragment
Line
    :   ( options { greedy=false; } : . )* Newline
    ;

Newline
    :   '\r'? '\n'
    ;

Whitespace
@init { $channel=HIDDEN; }
    :   (' '|'\t')+
    ;
