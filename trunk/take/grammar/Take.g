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
import nz.org.take.Constant;
import nz.org.take.DefaultKnowledgeBase;
import nz.org.take.KnowledgeBase;
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

script
	:	(	globalAnnotation
		|	declaration
		|	comment
		)*
	;

declaration
scope { Map<String, String> annotations; }
@init { $declaration::annotations = new HashMap<String, String>(); }
	:	localAnnotation*
		(	variable
		|	reference
		|	aggregation
		|	query
		|	factStore
		|	rule
		)
	;

globalAnnotation
	:	'@@' key=ID '=' value=LINE_TEXT { knowledgeBase.addAnnotation($key.text, $value.text); }
	;

localAnnotation
	:	'@' key=ID '=' value=LINE_TEXT  { $declaration::annotations.put($key.text, $value.text); }
	;

variable
	:	'var' type ids+=ID (',' ids+=ID)* NEWLINE
		{
			for (String id : $ids) {
				Variable variable = createVariable(id, $type.type);
				variable.addAnnotations(knowledgeBase.getAnnotations());
				variable.addAnnotations($declaration::annotations);
			}
		}
	;

reference
	:	'ref' type ids+=ID (',' ids+=ID)* NEWLINE
		{
			for (String id : $ids) {
				Constant reference = createReference(id, $type.type);
				reference.addAnnotations(knowledgeBase.getAnnotations());
				reference.addAnnotations($declaration::annotations);
			}
		}
	;

aggregation
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

query
	:
	;

factStore
	:	'external'
	;

rule:	
	;
	
predicate
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
	;

LINE_COMMENT
    :	'//' LINE_TEXT
    ;

fragment
LINE_TEXT
	:	( options { greedy=false; } : . )* NEWLINE
	;

NEWLINE
	:	'\r'? '\n'
	;
	
ID	:	('a'..'z'|'A'..'Z'|'_')('a'..'z'|'A'..'Z'|'_'|'0'..'9')* ;

WHITESPACE
	:	(' '|'\t')+ { $channel=HIDDEN; }
	;
