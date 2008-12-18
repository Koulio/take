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
import nz.org.take.KnowledgeBase;
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
}

script returns [KnowledgeBase knowledgeBase]
@after { $knowledgeBase = knowledgeBase; }
	:	(	globalAnnotation
		|	declaration
		|	comment
		)*
	;

declaration
scope { Map<String, String> annotations; }
@init { $declaration::annotations = new HashMap<String, String>(); }
	:	localAnnotation*
		(	variableDeclaration
		|	referenceDeclaration
		|	aggregationDeclaration
		|	queryDeclaration
		|	factStoreDeclaration
		|	factDeclaration
		|	ruleDeclaration
		)
	;

globalAnnotation
	:	'@@' key=ID '=' value=LINE_TEXT { knowledgeBase.addAnnotation($key.text, $value.text); }
	;

localAnnotation
	:	'@' key=ID '=' value=LINE_TEXT  { $declaration::annotations.put($key.text, $value.text); }
	;

variableDeclaration
	:	'var' type ids+=ID (',' ids+=ID)* NEWLINE
		{
			for (Object id : $ids) {
				Variable variable = createVariable((String)id, $type.type);
				variable.addAnnotations(knowledgeBase.getAnnotations());
				variable.addAnnotations($declaration::annotations);
			}
		}
	;

referenceDeclaration
	:	'ref' type ids+=ID (',' ids+=ID)* NEWLINE
		{
			for (Object id : $ids) {
				Constant reference = createReference((String)id, $type.type);
				reference.addAnnotations(knowledgeBase.getAnnotations());
				reference.addAnnotations($declaration::annotations);
			}
		}
	;

aggregationDeclaration
	:	'aggregation' ID '=' aggregateFunction ID aggregateCondition NEWLINE
	;

aggregateFunction returns [Aggregations aggregation]
	:	'avg'    { $aggregation = Aggregations.AVG; }
	|	'min'    { $aggregation = Aggregations.MIN; }
	|	'max'    { $aggregation = Aggregations.MAX; }
	|	'sum'    { $aggregation = Aggregations.SUM; }
	|	'count'  { $aggregation = Aggregations.COUNT; }
	;

aggregateCondition
	:	predicate
	;

queryDeclaration
	:
	;

factStoreDeclaration
	:	'external'
	;

factDeclaration
	:	ID ':' condition
	;

ruleDeclaration
	:	ID ':' 'if' condition
	;
	
condition
	:	andCondition ('or' andCondition)*
	;
	
andCondition
	:	predicate ('and' predicate)*
	;
	
predicate
	:	ID '[' terms ']'
	;
	
terms returns [List<Term> terms]
	:	term (',' term)*
	;
	
term returns [Term term]
	:	
	;

type returns [Class type]
	:	classOrInterfaceType  { $type = $classOrInterfaceType.type; }
	|	primitiveType         { $type = $primitiveType.type; }
	;

classOrInterfaceType returns [Class type]
	:	ID ('.' ID )*
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
	:	LINE_COMMENT
	|	BLOCK_COMMENT
	;

ID	:	('a'..'z'|'A'..'Z'|'_')('a'..'z'|'A'..'Z'|'_'|'0'..'9')* ;

BLOCK_COMMENT
@init { $channel=HIDDEN; }
	:	'/*' ( options { greedy=false; } : . )* '*/'
	;

LINE_COMMENT
@init { $channel=HIDDEN; }
    :	'//' LINE_TEXT
    ;

fragment
LINE_TEXT
	:	( options { greedy=false; } : . )* NEWLINE
	;

NEWLINE
	:	'\r'? '\n'
	;

WHITESPACE
@init { $channel=HIDDEN; }
	:	(' '|'\t')+
	;
