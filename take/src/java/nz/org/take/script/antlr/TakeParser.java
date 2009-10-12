// $ANTLR 3.1.1 /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g 2009-10-12 23:51:37

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
import nz.org.take.script.DuplicateQueryException;
import nz.org.take.script.DuplicateSymbolException;
import nz.org.take.script.IOState;
import nz.org.take.script.NamedElementTable;
import nz.org.take.script.NoSuchPredicateException;
import nz.org.take.script.QueryBuilder;
import nz.org.take.script.QueryDeclaration;
import nz.org.take.script.QueryDeclarationTable;
import nz.org.take.script.TakeGrammarException;
import nz.org.take.script.TermList;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
/** 
 * A Take grammar for ANTLRv3. 
 */
public class TakeParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "GlobalAnnotationKey", "LocalAnnotationKey", "Newline", "AnnotationValue", "Identifier", "Expression", "HexLiteral", "DecimalLiteral", "OctalLiteral", "FloatingPointLiteral", "StringLiteral", "HexDigit", "IntegerTypeSuffix", "Exponent", "FloatTypeSuffix", "EscapeSequence", "UnicodeEscape", "OctalEscape", "AnnotationKey", "GlobalAnnotation", "LocalAnnotation", "NamespaceDelimiter", "Letter", "IDDigit", "Whitespace", "BlockComment", "LineComment", "'var'", "','", "'ref'", "'aggregation'", "'='", "'avg'", "'min'", "'max'", "'sum'", "'count'", "'query'", "'|'", "'in'", "'out'", "'external'", "':'", "'if'", "'then'", "'and'", "'not'", "'.'", "'boolean'", "'char'", "'byte'", "'short'", "'int'", "'long'", "'float'", "'double'"
    };
    public static final int FloatTypeSuffix=18;
    public static final int OctalLiteral=12;
    public static final int LineComment=30;
    public static final int Exponent=17;
    public static final int Newline=6;
    public static final int AnnotationValue=7;
    public static final int AnnotationKey=22;
    public static final int EOF=-1;
    public static final int HexDigit=15;
    public static final int Identifier=8;
    public static final int BlockComment=29;
    public static final int T__55=55;
    public static final int T__56=56;
    public static final int T__57=57;
    public static final int T__58=58;
    public static final int T__51=51;
    public static final int T__52=52;
    public static final int T__53=53;
    public static final int T__54=54;
    public static final int T__59=59;
    public static final int LocalAnnotationKey=5;
    public static final int IDDigit=27;
    public static final int Whitespace=28;
    public static final int T__50=50;
    public static final int NamespaceDelimiter=25;
    public static final int T__42=42;
    public static final int T__43=43;
    public static final int HexLiteral=10;
    public static final int T__40=40;
    public static final int T__41=41;
    public static final int T__46=46;
    public static final int T__47=47;
    public static final int T__44=44;
    public static final int T__45=45;
    public static final int IntegerTypeSuffix=16;
    public static final int T__48=48;
    public static final int T__49=49;
    public static final int DecimalLiteral=11;
    public static final int Expression=9;
    public static final int StringLiteral=14;
    public static final int T__31=31;
    public static final int T__32=32;
    public static final int T__33=33;
    public static final int T__34=34;
    public static final int T__35=35;
    public static final int LocalAnnotation=24;
    public static final int T__36=36;
    public static final int T__37=37;
    public static final int T__38=38;
    public static final int GlobalAnnotationKey=4;
    public static final int T__39=39;
    public static final int UnicodeEscape=20;
    public static final int FloatingPointLiteral=13;
    public static final int GlobalAnnotation=23;
    public static final int Letter=26;
    public static final int OctalEscape=21;
    public static final int EscapeSequence=19;

    // delegates
    // delegators


        public TakeParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public TakeParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
            this.state.ruleMemo = new HashMap[68+1];
             
             
        }
        

    public String[] getTokenNames() { return TakeParser.tokenNames; }
    public String getGrammarFileName() { return "/Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g"; }


    private KnowledgeBase knowledgeBase = new DefaultKnowledgeBase();

    private NamedElementTable<AggregationFunction> aggregationTable = new NamedElementTable<AggregationFunction>();
    private NamedElementTable<Constant> constantTable = new NamedElementTable<Constant>();
    private NamedElementTable<Clause> clauseTable = new NamedElementTable<Clause>();
    private NamedElementTable<Predicate> predicateTable = new NamedElementTable<Predicate>();
    private NamedElementTable<Variable> variableTable = new NamedElementTable<Variable>();
    private QueryDeclarationTable queryTable = new QueryDeclarationTable();


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
        return ((declaration_scope)declaration_stack.peek()).annotations;
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
            throw new TakeGrammarException(input, e);
        }
    }

    private FactPrerequisite createFactPrerequisite(Predicate predicate, TermList terms) {
        FactPrerequisite prerequisite = new FactPrerequisite();
        
        prerequisite.setPredicate(predicate);
        prerequisite.setTerms(terms.toArray());
        
        return prerequisite;
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
            throw new TakeGrammarException(input, e);
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
            throw new TakeGrammarException(input, e);
        }
        
        return function;
    }

    private Fact createFact(String name, Predicate predicate, TermList terms) throws TakeGrammarException {
        Fact fact = createAnonymousFact(predicate, terms);
        fact.setId(name);
        
        try {
            clauseTable.put(fact);
        } catch (DuplicateSymbolException e) {
            throw new TakeGrammarException(input, e);
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
            throw new TakeGrammarException(input, e);
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
            throw new TakeGrammarException(input, e);
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
            throw new TakeGrammarException(input, e);
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
            throw new TakeGrammarException(input, e);
        }
        
        return variable;
    }

    private void checkElementNameIsUnique(String name) throws TakeGrammarException {
        if (variableTable.containsName(name) || constantTable.containsName(name)) {
            throw new TakeGrammarException(input, new DuplicateSymbolException(name));
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
            throw new TakeGrammarException(input, e);
        }
    }

    private QueryDeclaration createQueryDeclaration(Token predicate, List<IOState> parameterStates) throws TakeGrammarException {
        QueryDeclaration query = new QueryDeclaration(predicate, parameterStates);
        
        try {
            queryTable.put(query);
        } catch (DuplicateQueryException e) {
            throw new TakeGrammarException(input, e);
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




    // $ANTLR start "script"
    // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:341:1: script returns [KnowledgeBase knowledgeBase] : ( globalAnnotation | declaration )* ;
    public final KnowledgeBase script() throws RecognitionException {
        KnowledgeBase knowledgeBase = null;
        int script_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 1) ) { return knowledgeBase; }
            // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:348:5: ( ( globalAnnotation | declaration )* )
            // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:348:9: ( globalAnnotation | declaration )*
            {
            // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:348:9: ( globalAnnotation | declaration )*
            loop1:
            do {
                int alt1=3;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==GlobalAnnotationKey) ) {
                    alt1=1;
                }
                else if ( (LA1_0==LocalAnnotationKey||LA1_0==Identifier||LA1_0==31||(LA1_0>=33 && LA1_0<=34)||LA1_0==41||LA1_0==45) ) {
                    alt1=2;
                }


                switch (alt1) {
            	case 1 :
            	    // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:348:13: globalAnnotation
            	    {
            	    pushFollow(FOLLOW_globalAnnotation_in_script106);
            	    globalAnnotation();

            	    state._fsp--;
            	    if (state.failed) return knowledgeBase;

            	    }
            	    break;
            	case 2 :
            	    // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:349:13: declaration
            	    {
            	    pushFollow(FOLLOW_declaration_in_script120);
            	    declaration();

            	    state._fsp--;
            	    if (state.failed) return knowledgeBase;

            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);


            }

            if ( state.backtracking==0 ) {

                  buildQueries();
                  importClausesIntoKnowledgeBase();
                  
                  knowledgeBase = this.knowledgeBase;

            }
        }

        catch (RecognitionException re) {
        	reportError(re);
        	throw re;
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 1, script_StartIndex); }
        }
        return knowledgeBase;
    }
    // $ANTLR end "script"

    protected static class declaration_scope {
        Map<String, String> annotations;
    }
    protected Stack declaration_stack = new Stack();


    // $ANTLR start "declaration"
    // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:353:1: declaration : ( localAnnotation )* ( variableDeclarations | constantDeclarations | aggregationDeclaration | queryDeclaration | factStoreDeclaration | factDeclaration | ruleDeclaration ) ( Newline )* ;
    public final void declaration() throws RecognitionException {
        declaration_stack.push(new declaration_scope());
        int declaration_StartIndex = input.index();
        Collection<Variable> variableDeclarations1 = null;

        Collection<Constant> constantDeclarations2 = null;

        QueryDeclaration queryDeclaration3 = null;

        ExternalFactStore factStoreDeclaration4 = null;

        Fact factDeclaration5 = null;

        DerivationRule ruleDeclaration6 = null;


         ((declaration_scope)declaration_stack.peek()).annotations = new HashMap<String, String>(); 
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 2) ) { return ; }
            // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:356:5: ( ( localAnnotation )* ( variableDeclarations | constantDeclarations | aggregationDeclaration | queryDeclaration | factStoreDeclaration | factDeclaration | ruleDeclaration ) ( Newline )* )
            // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:356:9: ( localAnnotation )* ( variableDeclarations | constantDeclarations | aggregationDeclaration | queryDeclaration | factStoreDeclaration | factDeclaration | ruleDeclaration ) ( Newline )*
            {
            // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:356:9: ( localAnnotation )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==LocalAnnotationKey) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:0:0: localAnnotation
            	    {
            	    pushFollow(FOLLOW_localAnnotation_in_declaration159);
            	    localAnnotation();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);

            // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:357:9: ( variableDeclarations | constantDeclarations | aggregationDeclaration | queryDeclaration | factStoreDeclaration | factDeclaration | ruleDeclaration )
            int alt3=7;
            alt3 = dfa3.predict(input);
            switch (alt3) {
                case 1 :
                    // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:357:13: variableDeclarations
                    {
                    pushFollow(FOLLOW_variableDeclarations_in_declaration174);
                    variableDeclarations1=variableDeclarations();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       annotate(variableDeclarations1); 
                    }

                    }
                    break;
                case 2 :
                    // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:358:13: constantDeclarations
                    {
                    pushFollow(FOLLOW_constantDeclarations_in_declaration193);
                    constantDeclarations2=constantDeclarations();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       annotate(constantDeclarations2); 
                    }

                    }
                    break;
                case 3 :
                    // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:359:13: aggregationDeclaration
                    {
                    pushFollow(FOLLOW_aggregationDeclaration_in_declaration212);
                    aggregationDeclaration();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 4 :
                    // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:360:13: queryDeclaration
                    {
                    pushFollow(FOLLOW_queryDeclaration_in_declaration226);
                    queryDeclaration3=queryDeclaration();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       annotate(queryDeclaration3); 
                    }

                    }
                    break;
                case 5 :
                    // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:361:13: factStoreDeclaration
                    {
                    pushFollow(FOLLOW_factStoreDeclaration_in_declaration249);
                    factStoreDeclaration4=factStoreDeclaration();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       annotate(factStoreDeclaration4); 
                    }

                    }
                    break;
                case 6 :
                    // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:362:13: factDeclaration
                    {
                    pushFollow(FOLLOW_factDeclaration_in_declaration268);
                    factDeclaration5=factDeclaration();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       annotate(factDeclaration5); 
                    }

                    }
                    break;
                case 7 :
                    // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:363:13: ruleDeclaration
                    {
                    pushFollow(FOLLOW_ruleDeclaration_in_declaration292);
                    ruleDeclaration6=ruleDeclaration();

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       annotate(ruleDeclaration6); 
                    }

                    }
                    break;

            }

            // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:364:11: ( Newline )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( (LA4_0==Newline) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:0:0: Newline
            	    {
            	    match(input,Newline,FOLLOW_Newline_in_declaration314); if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop4;
                }
            } while (true);


            }

        }

        catch (RecognitionException re) {
        	reportError(re);
        	throw re;
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 2, declaration_StartIndex); }
            declaration_stack.pop();
        }
        return ;
    }
    // $ANTLR end "declaration"


    // $ANTLR start "globalAnnotation"
    // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:367:1: globalAnnotation : GlobalAnnotationKey AnnotationValue ;
    public final void globalAnnotation() throws RecognitionException {
        int globalAnnotation_StartIndex = input.index();
        Token GlobalAnnotationKey7=null;
        Token AnnotationValue8=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 3) ) { return ; }
            // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:368:5: ( GlobalAnnotationKey AnnotationValue )
            // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:368:9: GlobalAnnotationKey AnnotationValue
            {
            GlobalAnnotationKey7=(Token)match(input,GlobalAnnotationKey,FOLLOW_GlobalAnnotationKey_in_globalAnnotation334); if (state.failed) return ;
            AnnotationValue8=(Token)match(input,AnnotationValue,FOLLOW_AnnotationValue_in_globalAnnotation336); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               knowledgeBase.addAnnotation((GlobalAnnotationKey7!=null?GlobalAnnotationKey7.getText():null), (AnnotationValue8!=null?AnnotationValue8.getText():null)); 
            }

            }

        }

        catch (RecognitionException re) {
        	reportError(re);
        	throw re;
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 3, globalAnnotation_StartIndex); }
        }
        return ;
    }
    // $ANTLR end "globalAnnotation"


    // $ANTLR start "localAnnotation"
    // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:371:1: localAnnotation : LocalAnnotationKey AnnotationValue ;
    public final void localAnnotation() throws RecognitionException {
        int localAnnotation_StartIndex = input.index();
        Token LocalAnnotationKey9=null;
        Token AnnotationValue10=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 4) ) { return ; }
            // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:372:5: ( LocalAnnotationKey AnnotationValue )
            // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:372:9: LocalAnnotationKey AnnotationValue
            {
            LocalAnnotationKey9=(Token)match(input,LocalAnnotationKey,FOLLOW_LocalAnnotationKey_in_localAnnotation357); if (state.failed) return ;
            AnnotationValue10=(Token)match(input,AnnotationValue,FOLLOW_AnnotationValue_in_localAnnotation359); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               ((declaration_scope)declaration_stack.peek()).annotations.put((LocalAnnotationKey9!=null?LocalAnnotationKey9.getText():null), (AnnotationValue10!=null?AnnotationValue10.getText():null)); 
            }

            }

        }

        catch (RecognitionException re) {
        	reportError(re);
        	throw re;
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 4, localAnnotation_StartIndex); }
        }
        return ;
    }
    // $ANTLR end "localAnnotation"


    // $ANTLR start "variableDeclarations"
    // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:375:1: variableDeclarations returns [Collection<Variable> values] : 'var' type ids+= Identifier ( ',' ids+= Identifier )* ;
    public final Collection<Variable> variableDeclarations() throws RecognitionException {
        Collection<Variable> values = null;
        int variableDeclarations_StartIndex = input.index();
        Token ids=null;
        List list_ids=null;
        Class type11 = null;


         values = new ArrayList<Variable>(); 
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 5) ) { return values; }
            // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:377:5: ( 'var' type ids+= Identifier ( ',' ids+= Identifier )* )
            // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:377:9: 'var' type ids+= Identifier ( ',' ids+= Identifier )*
            {
            match(input,31,FOLLOW_31_in_variableDeclarations389); if (state.failed) return values;
            pushFollow(FOLLOW_type_in_variableDeclarations391);
            type11=type();

            state._fsp--;
            if (state.failed) return values;
            ids=(Token)match(input,Identifier,FOLLOW_Identifier_in_variableDeclarations395); if (state.failed) return values;
            if (list_ids==null) list_ids=new ArrayList();
            list_ids.add(ids);

            // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:377:36: ( ',' ids+= Identifier )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==32) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:377:37: ',' ids+= Identifier
            	    {
            	    match(input,32,FOLLOW_32_in_variableDeclarations398); if (state.failed) return values;
            	    ids=(Token)match(input,Identifier,FOLLOW_Identifier_in_variableDeclarations402); if (state.failed) return values;
            	    if (list_ids==null) list_ids=new ArrayList();
            	    list_ids.add(ids);


            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);

            if ( state.backtracking==0 ) {

                          for (Token id : (List<Token>)list_ids) {
                          	Variable variable = createVariable(id.getText(), type11);
                          	values.add(variable);
                          }
                      
            }

            }

        }

        catch (RecognitionException re) {
        	reportError(re);
        	throw re;
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 5, variableDeclarations_StartIndex); }
        }
        return values;
    }
    // $ANTLR end "variableDeclarations"


    // $ANTLR start "constantDeclarations"
    // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:386:1: constantDeclarations returns [Collection<Constant> values] : 'ref' type ids+= Identifier ( ',' ids+= Identifier )* ;
    public final Collection<Constant> constantDeclarations() throws RecognitionException {
        Collection<Constant> values = null;
        int constantDeclarations_StartIndex = input.index();
        Token ids=null;
        List list_ids=null;
        Class type12 = null;


         values = new ArrayList<Constant>(); 
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 6) ) { return values; }
            // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:388:5: ( 'ref' type ids+= Identifier ( ',' ids+= Identifier )* )
            // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:388:9: 'ref' type ids+= Identifier ( ',' ids+= Identifier )*
            {
            match(input,33,FOLLOW_33_in_constantDeclarations442); if (state.failed) return values;
            pushFollow(FOLLOW_type_in_constantDeclarations444);
            type12=type();

            state._fsp--;
            if (state.failed) return values;
            ids=(Token)match(input,Identifier,FOLLOW_Identifier_in_constantDeclarations448); if (state.failed) return values;
            if (list_ids==null) list_ids=new ArrayList();
            list_ids.add(ids);

            // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:388:36: ( ',' ids+= Identifier )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==32) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:388:37: ',' ids+= Identifier
            	    {
            	    match(input,32,FOLLOW_32_in_constantDeclarations451); if (state.failed) return values;
            	    ids=(Token)match(input,Identifier,FOLLOW_Identifier_in_constantDeclarations455); if (state.failed) return values;
            	    if (list_ids==null) list_ids=new ArrayList();
            	    list_ids.add(ids);


            	    }
            	    break;

            	default :
            	    break loop6;
                }
            } while (true);

            if ( state.backtracking==0 ) {

                          for (Token id : (List<Token>)list_ids) {
                              Constant constant = createConstant(id.getText(), type12);
                              values.add(constant);
                          }
                      
            }

            }

        }

        catch (RecognitionException re) {
        	reportError(re);
        	throw re;
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 6, constantDeclarations_StartIndex); }
        }
        return values;
    }
    // $ANTLR end "constantDeclarations"


    // $ANTLR start "aggregationDeclaration"
    // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:397:1: aggregationDeclaration returns [AggregationFunction value] : 'aggregation' Identifier '=' aggregateFunction variable predicate ;
    public final AggregationFunction aggregationDeclaration() throws RecognitionException {
        AggregationFunction value = null;
        int aggregationDeclaration_StartIndex = input.index();
        Token Identifier14=null;
        TakeParser.predicate_return predicate13 = null;

        Aggregations aggregateFunction15 = null;

        Variable variable16 = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 7) ) { return value; }
            // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:398:5: ( 'aggregation' Identifier '=' aggregateFunction variable predicate )
            // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:398:9: 'aggregation' Identifier '=' aggregateFunction variable predicate
            {
            match(input,34,FOLLOW_34_in_aggregationDeclaration494); if (state.failed) return value;
            Identifier14=(Token)match(input,Identifier,FOLLOW_Identifier_in_aggregationDeclaration496); if (state.failed) return value;
            match(input,35,FOLLOW_35_in_aggregationDeclaration498); if (state.failed) return value;
            pushFollow(FOLLOW_aggregateFunction_in_aggregationDeclaration500);
            aggregateFunction15=aggregateFunction();

            state._fsp--;
            if (state.failed) return value;
            pushFollow(FOLLOW_variable_in_aggregationDeclaration502);
            variable16=variable();

            state._fsp--;
            if (state.failed) return value;
            pushFollow(FOLLOW_predicate_in_aggregationDeclaration504);
            predicate13=predicate();

            state._fsp--;
            if (state.failed) return value;
            if ( state.backtracking==0 ) {

                          Fact query = createAnonymousFact((predicate13!=null?predicate13.value:null), (predicate13!=null?predicate13.terms:null));
                          value = createAggregationFunction((Identifier14!=null?Identifier14.getText():null), aggregateFunction15, variable16, query);
                      
            }

            }

        }

        catch (RecognitionException re) {
        	reportError(re);
        	throw re;
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 7, aggregationDeclaration_StartIndex); }
        }
        return value;
    }
    // $ANTLR end "aggregationDeclaration"


    // $ANTLR start "aggregateFunction"
    // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:405:1: aggregateFunction returns [Aggregations value] : ( 'avg' | 'min' | 'max' | 'sum' | 'count' );
    public final Aggregations aggregateFunction() throws RecognitionException {
        Aggregations value = null;
        int aggregateFunction_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 8) ) { return value; }
            // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:406:5: ( 'avg' | 'min' | 'max' | 'sum' | 'count' )
            int alt7=5;
            switch ( input.LA(1) ) {
            case 36:
                {
                alt7=1;
                }
                break;
            case 37:
                {
                alt7=2;
                }
                break;
            case 38:
                {
                alt7=3;
                }
                break;
            case 39:
                {
                alt7=4;
                }
                break;
            case 40:
                {
                alt7=5;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return value;}
                NoViableAltException nvae =
                    new NoViableAltException("", 7, 0, input);

                throw nvae;
            }

            switch (alt7) {
                case 1 :
                    // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:406:9: 'avg'
                    {
                    match(input,36,FOLLOW_36_in_aggregateFunction537); if (state.failed) return value;
                    if ( state.backtracking==0 ) {
                       value = Aggregations.AVG; 
                    }

                    }
                    break;
                case 2 :
                    // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:407:9: 'min'
                    {
                    match(input,37,FOLLOW_37_in_aggregateFunction551); if (state.failed) return value;
                    if ( state.backtracking==0 ) {
                       value = Aggregations.MIN; 
                    }

                    }
                    break;
                case 3 :
                    // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:408:9: 'max'
                    {
                    match(input,38,FOLLOW_38_in_aggregateFunction565); if (state.failed) return value;
                    if ( state.backtracking==0 ) {
                       value = Aggregations.MAX; 
                    }

                    }
                    break;
                case 4 :
                    // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:409:9: 'sum'
                    {
                    match(input,39,FOLLOW_39_in_aggregateFunction579); if (state.failed) return value;
                    if ( state.backtracking==0 ) {
                       value = Aggregations.SUM; 
                    }

                    }
                    break;
                case 5 :
                    // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:410:9: 'count'
                    {
                    match(input,40,FOLLOW_40_in_aggregateFunction593); if (state.failed) return value;
                    if ( state.backtracking==0 ) {
                       value = Aggregations.COUNT; 
                    }

                    }
                    break;

            }
        }

        catch (RecognitionException re) {
        	reportError(re);
        	throw re;
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 8, aggregateFunction_StartIndex); }
        }
        return value;
    }
    // $ANTLR end "aggregateFunction"


    // $ANTLR start "variable"
    // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:413:1: variable returns [Variable value] : variableName= Identifier {...}?;
    public final Variable variable() throws RecognitionException {
        Variable value = null;
        int variable_StartIndex = input.index();
        Token variableName=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 9) ) { return value; }
            // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:414:5: (variableName= Identifier {...}?)
            // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:414:9: variableName= Identifier {...}?
            {
            variableName=(Token)match(input,Identifier,FOLLOW_Identifier_in_variable620); if (state.failed) return value;
            if ( !(( variableTable.containsName((variableName!=null?variableName.getText():null)) )) ) {
                if (state.backtracking>0) {state.failed=true; return value;}
                throw new FailedPredicateException(input, "variable", " variableTable.containsName($variableName.text) ");
            }
            if ( state.backtracking==0 ) {
               value = variableTable.get((variableName!=null?variableName.getText():null)); 
            }

            }

        }

        catch (RecognitionException re) {
        	reportError(re);
        	throw re;
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 9, variable_StartIndex); }
        }
        return value;
    }
    // $ANTLR end "variable"


    // $ANTLR start "queryDeclaration"
    // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:417:1: queryDeclaration returns [QueryDeclaration value] : 'query' queryName= Identifier '|' ioStates '|' ;
    public final QueryDeclaration queryDeclaration() throws RecognitionException {
        QueryDeclaration value = null;
        int queryDeclaration_StartIndex = input.index();
        Token queryName=null;
        List<IOState> ioStates17 = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 10) ) { return value; }
            // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:418:5: ( 'query' queryName= Identifier '|' ioStates '|' )
            // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:418:9: 'query' queryName= Identifier '|' ioStates '|'
            {
            match(input,41,FOLLOW_41_in_queryDeclaration647); if (state.failed) return value;
            queryName=(Token)match(input,Identifier,FOLLOW_Identifier_in_queryDeclaration651); if (state.failed) return value;
            match(input,42,FOLLOW_42_in_queryDeclaration653); if (state.failed) return value;
            pushFollow(FOLLOW_ioStates_in_queryDeclaration655);
            ioStates17=ioStates();

            state._fsp--;
            if (state.failed) return value;
            match(input,42,FOLLOW_42_in_queryDeclaration657); if (state.failed) return value;
            if ( state.backtracking==0 ) {

                          value = createQueryDeclaration(queryName, ioStates17);
                      
            }

            }

        }

        catch (RecognitionException re) {
        	reportError(re);
        	throw re;
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 10, queryDeclaration_StartIndex); }
        }
        return value;
    }
    // $ANTLR end "queryDeclaration"


    // $ANTLR start "ioStates"
    // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:424:1: ioStates returns [List<IOState> values] : firstState= ioState ( ',' extraState= ioState )* ;
    public final List<IOState> ioStates() throws RecognitionException {
        List<IOState> values = null;
        int ioStates_StartIndex = input.index();
        IOState firstState = null;

        IOState extraState = null;


         values = new ArrayList<IOState>(); 
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 11) ) { return values; }
            // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:426:5: (firstState= ioState ( ',' extraState= ioState )* )
            // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:426:9: firstState= ioState ( ',' extraState= ioState )*
            {
            pushFollow(FOLLOW_ioState_in_ioStates697);
            firstState=ioState();

            state._fsp--;
            if (state.failed) return values;
            if ( state.backtracking==0 ) {
               values.add(firstState); 
            }
            // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:426:64: ( ',' extraState= ioState )*
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( (LA8_0==32) ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:426:65: ',' extraState= ioState
            	    {
            	    match(input,32,FOLLOW_32_in_ioStates702); if (state.failed) return values;
            	    pushFollow(FOLLOW_ioState_in_ioStates706);
            	    extraState=ioState();

            	    state._fsp--;
            	    if (state.failed) return values;
            	    if ( state.backtracking==0 ) {
            	       values.add(extraState); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop8;
                }
            } while (true);


            }

        }

        catch (RecognitionException re) {
        	reportError(re);
        	throw re;
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 11, ioStates_StartIndex); }
        }
        return values;
    }
    // $ANTLR end "ioStates"


    // $ANTLR start "ioState"
    // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:429:1: ioState returns [IOState value] : ( 'in' | 'out' );
    public final IOState ioState() throws RecognitionException {
        IOState value = null;
        int ioState_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 12) ) { return value; }
            // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:430:5: ( 'in' | 'out' )
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==43) ) {
                alt9=1;
            }
            else if ( (LA9_0==44) ) {
                alt9=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return value;}
                NoViableAltException nvae =
                    new NoViableAltException("", 9, 0, input);

                throw nvae;
            }
            switch (alt9) {
                case 1 :
                    // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:430:7: 'in'
                    {
                    match(input,43,FOLLOW_43_in_ioState732); if (state.failed) return value;
                    if ( state.backtracking==0 ) {
                       value = IOState.IN; 
                    }

                    }
                    break;
                case 2 :
                    // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:431:9: 'out'
                    {
                    match(input,44,FOLLOW_44_in_ioState746); if (state.failed) return value;
                    if ( state.backtracking==0 ) {
                       value = IOState.OUT; 
                    }

                    }
                    break;

            }
        }

        catch (RecognitionException re) {
        	reportError(re);
        	throw re;
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 12, ioState_StartIndex); }
        }
        return value;
    }
    // $ANTLR end "ioState"


    // $ANTLR start "factStoreDeclaration"
    // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:434:1: factStoreDeclaration returns [ExternalFactStore value] : 'external' ;
    public final ExternalFactStore factStoreDeclaration() throws RecognitionException {
        ExternalFactStore value = null;
        int factStoreDeclaration_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 13) ) { return value; }
            // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:436:5: ( 'external' )
            // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:436:9: 'external'
            {
            match(input,45,FOLLOW_45_in_factStoreDeclaration777); if (state.failed) return value;

            }

            if ( state.backtracking==0 ) {
               throw new TakeGrammarException(input, new Exception("Factstores not yet implemented.")); 
            }
        }

        catch (RecognitionException re) {
        	reportError(re);
        	throw re;
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 13, factStoreDeclaration_StartIndex); }
        }
        return value;
    }
    // $ANTLR end "factStoreDeclaration"


    // $ANTLR start "factDeclaration"
    // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:439:1: factDeclaration returns [Fact value] : factName= Identifier ':' predicate ;
    public final Fact factDeclaration() throws RecognitionException {
        Fact value = null;
        int factDeclaration_StartIndex = input.index();
        Token factName=null;
        TakeParser.predicate_return predicate18 = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 14) ) { return value; }
            // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:440:5: (factName= Identifier ':' predicate )
            // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:440:9: factName= Identifier ':' predicate
            {
            factName=(Token)match(input,Identifier,FOLLOW_Identifier_in_factDeclaration803); if (state.failed) return value;
            match(input,46,FOLLOW_46_in_factDeclaration805); if (state.failed) return value;
            pushFollow(FOLLOW_predicate_in_factDeclaration807);
            predicate18=predicate();

            state._fsp--;
            if (state.failed) return value;
            if ( state.backtracking==0 ) {

                          value = createFact((factName!=null?factName.getText():null), (predicate18!=null?predicate18.value:null), (predicate18!=null?predicate18.terms:null));
                      
            }

            }

        }

        catch (RecognitionException re) {
        	reportError(re);
        	throw re;
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 14, factDeclaration_StartIndex); }
        }
        return value;
    }
    // $ANTLR end "factDeclaration"


    // $ANTLR start "ruleDeclaration"
    // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:446:1: ruleDeclaration returns [DerivationRule value] : ruleName= Identifier ':' 'if' body= prerequisites 'then' predicate ;
    public final DerivationRule ruleDeclaration() throws RecognitionException {
        DerivationRule value = null;
        int ruleDeclaration_StartIndex = input.index();
        Token ruleName=null;
        List<Prerequisite> body = null;

        TakeParser.predicate_return predicate19 = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 15) ) { return value; }
            // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:447:5: (ruleName= Identifier ':' 'if' body= prerequisites 'then' predicate )
            // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:447:9: ruleName= Identifier ':' 'if' body= prerequisites 'then' predicate
            {
            ruleName=(Token)match(input,Identifier,FOLLOW_Identifier_in_ruleDeclaration842); if (state.failed) return value;
            match(input,46,FOLLOW_46_in_ruleDeclaration844); if (state.failed) return value;
            match(input,47,FOLLOW_47_in_ruleDeclaration846); if (state.failed) return value;
            pushFollow(FOLLOW_prerequisites_in_ruleDeclaration850);
            body=prerequisites();

            state._fsp--;
            if (state.failed) return value;
            match(input,48,FOLLOW_48_in_ruleDeclaration852); if (state.failed) return value;
            pushFollow(FOLLOW_predicate_in_ruleDeclaration854);
            predicate19=predicate();

            state._fsp--;
            if (state.failed) return value;
            if ( state.backtracking==0 ) {

                          Fact head = createAnonymousFact((predicate19!=null?predicate19.value:null), (predicate19!=null?predicate19.terms:null));
                          value = createRule((ruleName!=null?ruleName.getText():null), body, head);
                      
            }

            }

        }

        catch (RecognitionException re) {
        	reportError(re);
        	throw re;
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 15, ruleDeclaration_StartIndex); }
        }
        return value;
    }
    // $ANTLR end "ruleDeclaration"


    // $ANTLR start "prerequisites"
    // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:454:1: prerequisites returns [List<Prerequisite> values] : firstPrerequisite= prerequisite ( 'and' extraPrerequisite= prerequisite )* ;
    public final List<Prerequisite> prerequisites() throws RecognitionException {
        List<Prerequisite> values = null;
        int prerequisites_StartIndex = input.index();
        Prerequisite firstPrerequisite = null;

        Prerequisite extraPrerequisite = null;


         values = new ArrayList<Prerequisite>(); 
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 16) ) { return values; }
            // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:456:5: (firstPrerequisite= prerequisite ( 'and' extraPrerequisite= prerequisite )* )
            // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:456:9: firstPrerequisite= prerequisite ( 'and' extraPrerequisite= prerequisite )*
            {
            pushFollow(FOLLOW_prerequisite_in_prerequisites898);
            firstPrerequisite=prerequisite();

            state._fsp--;
            if (state.failed) return values;
            if ( state.backtracking==0 ) {
               values.add(firstPrerequisite); 
            }
            // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:456:83: ( 'and' extraPrerequisite= prerequisite )*
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( (LA10_0==49) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:456:84: 'and' extraPrerequisite= prerequisite
            	    {
            	    match(input,49,FOLLOW_49_in_prerequisites903); if (state.failed) return values;
            	    pushFollow(FOLLOW_prerequisite_in_prerequisites907);
            	    extraPrerequisite=prerequisite();

            	    state._fsp--;
            	    if (state.failed) return values;
            	    if ( state.backtracking==0 ) {
            	       values.add(extraPrerequisite); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop10;
                }
            } while (true);


            }

        }

        catch (RecognitionException re) {
        	reportError(re);
        	throw re;
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 16, prerequisites_StartIndex); }
        }
        return values;
    }
    // $ANTLR end "prerequisites"


    // $ANTLR start "prerequisite"
    // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:459:1: prerequisite returns [Prerequisite value] : ( factPrerequisite | expressionPrerequisite );
    public final Prerequisite prerequisite() throws RecognitionException {
        Prerequisite value = null;
        int prerequisite_StartIndex = input.index();
        FactPrerequisite factPrerequisite20 = null;

        ExpressionPrerequisite expressionPrerequisite21 = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 17) ) { return value; }
            // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:460:5: ( factPrerequisite | expressionPrerequisite )
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==Identifier||LA11_0==50) ) {
                alt11=1;
            }
            else if ( (LA11_0==Expression) ) {
                alt11=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return value;}
                NoViableAltException nvae =
                    new NoViableAltException("", 11, 0, input);

                throw nvae;
            }
            switch (alt11) {
                case 1 :
                    // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:460:9: factPrerequisite
                    {
                    pushFollow(FOLLOW_factPrerequisite_in_prerequisite934);
                    factPrerequisite20=factPrerequisite();

                    state._fsp--;
                    if (state.failed) return value;
                    if ( state.backtracking==0 ) {
                       value = factPrerequisite20; 
                    }

                    }
                    break;
                case 2 :
                    // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:461:9: expressionPrerequisite
                    {
                    pushFollow(FOLLOW_expressionPrerequisite_in_prerequisite946);
                    expressionPrerequisite21=expressionPrerequisite();

                    state._fsp--;
                    if (state.failed) return value;
                    if ( state.backtracking==0 ) {
                       value = expressionPrerequisite21; 
                    }

                    }
                    break;

            }
        }

        catch (RecognitionException re) {
        	reportError(re);
        	throw re;
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 17, prerequisite_StartIndex); }
        }
        return value;
    }
    // $ANTLR end "prerequisite"


    // $ANTLR start "factPrerequisite"
    // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:464:1: factPrerequisite returns [FactPrerequisite value] : negatablePredicate ;
    public final FactPrerequisite factPrerequisite() throws RecognitionException {
        FactPrerequisite value = null;
        int factPrerequisite_StartIndex = input.index();
        TakeParser.negatablePredicate_return negatablePredicate22 = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 18) ) { return value; }
            // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:465:5: ( negatablePredicate )
            // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:465:9: negatablePredicate
            {
            pushFollow(FOLLOW_negatablePredicate_in_factPrerequisite971);
            negatablePredicate22=negatablePredicate();

            state._fsp--;
            if (state.failed) return value;
            if ( state.backtracking==0 ) {

                          value = createFactPrerequisite((negatablePredicate22!=null?negatablePredicate22.value:null), (negatablePredicate22!=null?negatablePredicate22.terms:null));
                  	
            }

            }

        }

        catch (RecognitionException re) {
        	reportError(re);
        	throw re;
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 18, factPrerequisite_StartIndex); }
        }
        return value;
    }
    // $ANTLR end "factPrerequisite"


    // $ANTLR start "expressionPrerequisite"
    // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:471:1: expressionPrerequisite returns [ExpressionPrerequisite value] : Expression {...}?;
    public final ExpressionPrerequisite expressionPrerequisite() throws RecognitionException {
        ExpressionPrerequisite value = null;
        int expressionPrerequisite_StartIndex = input.index();
        Token Expression23=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 19) ) { return value; }
            // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:472:5: ( Expression {...}?)
            // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:472:9: Expression {...}?
            {
            Expression23=(Token)match(input,Expression,FOLLOW_Expression_in_expressionPrerequisite1002); if (state.failed) return value;
            if ( !(( isValidPrerequisiteExpression((Expression23!=null?Expression23.getText():null)) )) ) {
                if (state.backtracking>0) {state.failed=true; return value;}
                throw new FailedPredicateException(input, "expressionPrerequisite", " isValidPrerequisiteExpression($Expression.text) ");
            }
            if ( state.backtracking==0 ) {

                          value = createExpressionPrerequisite((Expression23!=null?Expression23.getText():null));
                      
            }

            }

        }

        catch (RecognitionException re) {
        	reportError(re);
        	throw re;
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 19, expressionPrerequisite_StartIndex); }
        }
        return value;
    }
    // $ANTLR end "expressionPrerequisite"

    public static class negatablePredicate_return extends ParserRuleReturnScope {
        public Predicate value;
        public TermList terms;
    };

    // $ANTLR start "negatablePredicate"
    // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:478:1: negatablePredicate returns [Predicate value, TermList terms] : ( 'not' predicate | predicate );
    public final TakeParser.negatablePredicate_return negatablePredicate() throws RecognitionException {
        TakeParser.negatablePredicate_return retval = new TakeParser.negatablePredicate_return();
        retval.start = input.LT(1);
        int negatablePredicate_StartIndex = input.index();
        TakeParser.predicate_return predicate24 = null;

        TakeParser.predicate_return predicate25 = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 20) ) { return retval; }
            // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:479:2: ( 'not' predicate | predicate )
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==50) ) {
                alt12=1;
            }
            else if ( (LA12_0==Identifier) ) {
                alt12=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 12, 0, input);

                throw nvae;
            }
            switch (alt12) {
                case 1 :
                    // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:479:6: 'not' predicate
                    {
                    match(input,50,FOLLOW_50_in_negatablePredicate1034); if (state.failed) return retval;
                    pushFollow(FOLLOW_predicate_in_negatablePredicate1036);
                    predicate24=predicate();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                       (predicate24!=null?predicate24.value:null).setNegated(true); retval.value = (predicate24!=null?predicate24.value:null); retval.terms = (predicate24!=null?predicate24.terms:null); 
                    }

                    }
                    break;
                case 2 :
                    // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:480:6: predicate
                    {
                    pushFollow(FOLLOW_predicate_in_negatablePredicate1045);
                    predicate25=predicate();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                       retval.value = (predicate25!=null?predicate25.value:null); retval.terms = (predicate25!=null?predicate25.terms:null); 
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);

        }

        catch (RecognitionException re) {
        	reportError(re);
        	throw re;
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 20, negatablePredicate_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "negatablePredicate"

    public static class predicate_return extends ParserRuleReturnScope {
        public Predicate value;
        public TermList terms;
    };

    // $ANTLR start "predicate"
    // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:483:1: predicate returns [Predicate value, TermList terms] : ( existingPredicate | newPredicate );
    public final TakeParser.predicate_return predicate() throws RecognitionException {
        TakeParser.predicate_return retval = new TakeParser.predicate_return();
        retval.start = input.LT(1);
        int predicate_StartIndex = input.index();
        TakeParser.existingPredicate_return existingPredicate26 = null;

        TakeParser.newPredicate_return newPredicate27 = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 21) ) { return retval; }
            // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:484:5: ( existingPredicate | newPredicate )
            int alt13=2;
            alt13 = dfa13.predict(input);
            switch (alt13) {
                case 1 :
                    // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:484:9: existingPredicate
                    {
                    pushFollow(FOLLOW_existingPredicate_in_predicate1073);
                    existingPredicate26=existingPredicate();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                       retval.value = (existingPredicate26!=null?existingPredicate26.value:null); retval.terms = (existingPredicate26!=null?existingPredicate26.terms:null); 
                    }

                    }
                    break;
                case 2 :
                    // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:485:9: newPredicate
                    {
                    pushFollow(FOLLOW_newPredicate_in_predicate1086);
                    newPredicate27=newPredicate();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                       retval.value = (newPredicate27!=null?newPredicate27.value:null); retval.terms = (newPredicate27!=null?newPredicate27.terms:null); 
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);

        }

        catch (RecognitionException re) {
        	reportError(re);
        	throw re;
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 21, predicate_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "predicate"

    public static class existingPredicate_return extends ParserRuleReturnScope {
        public Predicate value;
        public TermList terms;
    };

    // $ANTLR start "existingPredicate"
    // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:488:1: existingPredicate returns [Predicate value, TermList terms] : predicateName= Identifier '|' params= terms '|' {...}?;
    public final TakeParser.existingPredicate_return existingPredicate() throws RecognitionException {
        TakeParser.existingPredicate_return retval = new TakeParser.existingPredicate_return();
        retval.start = input.LT(1);
        int existingPredicate_StartIndex = input.index();
        Token predicateName=null;
        TermList params = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 22) ) { return retval; }
            // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:489:2: (predicateName= Identifier '|' params= terms '|' {...}?)
            // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:489:6: predicateName= Identifier '|' params= terms '|' {...}?
            {
            predicateName=(Token)match(input,Identifier,FOLLOW_Identifier_in_existingPredicate1116); if (state.failed) return retval;
            match(input,42,FOLLOW_42_in_existingPredicate1118); if (state.failed) return retval;
            pushFollow(FOLLOW_terms_in_existingPredicate1122);
            params=terms();

            state._fsp--;
            if (state.failed) return retval;
            match(input,42,FOLLOW_42_in_existingPredicate1124); if (state.failed) return retval;
            if ( !(( predicateTable.containsName((predicateName!=null?predicateName.getText():null)) )) ) {
                if (state.backtracking>0) {state.failed=true; return retval;}
                throw new FailedPredicateException(input, "existingPredicate", " predicateTable.containsName($predicateName.text) ");
            }
            if ( state.backtracking==0 ) {

              	        retval.value = predicateTable.get((predicateName!=null?predicateName.getText():null));
              	        retval.terms = params;
              	    
            }

            }

            retval.stop = input.LT(-1);

        }

        catch (RecognitionException re) {
        	reportError(re);
        	throw re;
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 22, existingPredicate_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "existingPredicate"

    public static class newPredicate_return extends ParserRuleReturnScope {
        public Predicate value;
        public TermList terms;
    };

    // $ANTLR start "newPredicate"
    // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:496:1: newPredicate returns [Predicate value, TermList terms] : predicateName= Identifier '|' params= terms '|' ;
    public final TakeParser.newPredicate_return newPredicate() throws RecognitionException {
        TakeParser.newPredicate_return retval = new TakeParser.newPredicate_return();
        retval.start = input.LT(1);
        int newPredicate_StartIndex = input.index();
        Token predicateName=null;
        TermList params = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 23) ) { return retval; }
            // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:497:5: (predicateName= Identifier '|' params= terms '|' )
            // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:497:9: predicateName= Identifier '|' params= terms '|'
            {
            predicateName=(Token)match(input,Identifier,FOLLOW_Identifier_in_newPredicate1156); if (state.failed) return retval;
            match(input,42,FOLLOW_42_in_newPredicate1158); if (state.failed) return retval;
            pushFollow(FOLLOW_terms_in_newPredicate1162);
            params=terms();

            state._fsp--;
            if (state.failed) return retval;
            match(input,42,FOLLOW_42_in_newPredicate1164); if (state.failed) return retval;
            if ( state.backtracking==0 ) {

                          retval.value = createPredicate((predicateName!=null?predicateName.getText():null), params);
                          retval.terms = params;
                      
            }

            }

            retval.stop = input.LT(-1);

        }

        catch (RecognitionException re) {
        	reportError(re);
        	throw re;
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 23, newPredicate_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "newPredicate"


    // $ANTLR start "terms"
    // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:504:1: terms returns [TermList values] : firstTerm= term ( ',' extraTerm= term )* ;
    public final TermList terms() throws RecognitionException {
        TermList values = null;
        int terms_StartIndex = input.index();
        Term firstTerm = null;

        Term extraTerm = null;


         values = new TermList(); 
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 24) ) { return values; }
            // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:506:5: (firstTerm= term ( ',' extraTerm= term )* )
            // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:506:9: firstTerm= term ( ',' extraTerm= term )*
            {
            pushFollow(FOLLOW_term_in_terms1204);
            firstTerm=term();

            state._fsp--;
            if (state.failed) return values;
            if ( state.backtracking==0 ) {
               values.add(firstTerm); 
            }
            // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:506:59: ( ',' extraTerm= term )*
            loop14:
            do {
                int alt14=2;
                int LA14_0 = input.LA(1);

                if ( (LA14_0==32) ) {
                    alt14=1;
                }


                switch (alt14) {
            	case 1 :
            	    // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:506:60: ',' extraTerm= term
            	    {
            	    match(input,32,FOLLOW_32_in_terms1209); if (state.failed) return values;
            	    pushFollow(FOLLOW_term_in_terms1213);
            	    extraTerm=term();

            	    state._fsp--;
            	    if (state.failed) return values;
            	    if ( state.backtracking==0 ) {
            	       values.add(extraTerm); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop14;
                }
            } while (true);


            }

        }

        catch (RecognitionException re) {
        	reportError(re);
        	throw re;
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 24, terms_StartIndex); }
        }
        return values;
    }
    // $ANTLR end "terms"


    // $ANTLR start "term"
    // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:509:1: term returns [Term value] : complexTerm ;
    public final Term term() throws RecognitionException {
        Term value = null;
        int term_StartIndex = input.index();
        ComplexTerm complexTerm28 = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 25) ) { return value; }
            // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:510:5: ( complexTerm )
            // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:510:9: complexTerm
            {
            pushFollow(FOLLOW_complexTerm_in_term1244);
            complexTerm28=complexTerm();

            state._fsp--;
            if (state.failed) return value;
            if ( state.backtracking==0 ) {
               value = complexTerm28; 
            }

            }

        }

        catch (RecognitionException re) {
        	reportError(re);
        	throw re;
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 25, term_StartIndex); }
        }
        return value;
    }
    // $ANTLR end "term"


    // $ANTLR start "complexTerm"
    // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:513:1: complexTerm returns [ComplexTerm value] : t= ( Expression | Identifier | HexLiteral | DecimalLiteral | OctalLiteral | FloatingPointLiteral | StringLiteral ) {...}?;
    public final ComplexTerm complexTerm() throws RecognitionException {
        ComplexTerm value = null;
        int complexTerm_StartIndex = input.index();
        Token t=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 26) ) { return value; }
            // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:514:5: (t= ( Expression | Identifier | HexLiteral | DecimalLiteral | OctalLiteral | FloatingPointLiteral | StringLiteral ) {...}?)
            // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:514:9: t= ( Expression | Identifier | HexLiteral | DecimalLiteral | OctalLiteral | FloatingPointLiteral | StringLiteral ) {...}?
            {
            t=(Token)input.LT(1);
            if ( (input.LA(1)>=Identifier && input.LA(1)<=StringLiteral) ) {
                input.consume();
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return value;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            if ( !(( isValidComplexTermExpression((t!=null?t.getText():null)) )) ) {
                if (state.backtracking>0) {state.failed=true; return value;}
                throw new FailedPredicateException(input, "complexTerm", " isValidComplexTermExpression("+t.toString()+")");
            }
            if ( state.backtracking==0 ) {

                      	value = createComplexTerm((t!=null?t.getText():null));
                      
            }

            }

        }

        catch (RecognitionException re) {
        	reportError(re);
        	throw re;
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 26, complexTerm_StartIndex); }
        }
        return value;
    }
    // $ANTLR end "complexTerm"


    // $ANTLR start "type"
    // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:521:1: type returns [Class value] : ( classOrInterfaceType | primitiveType );
    public final Class type() throws RecognitionException {
        Class value = null;
        int type_StartIndex = input.index();
        Class classOrInterfaceType29 = null;

        Class primitiveType30 = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 27) ) { return value; }
            // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:522:5: ( classOrInterfaceType | primitiveType )
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==Identifier) ) {
                alt15=1;
            }
            else if ( ((LA15_0>=52 && LA15_0<=59)) ) {
                alt15=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return value;}
                NoViableAltException nvae =
                    new NoViableAltException("", 15, 0, input);

                throw nvae;
            }
            switch (alt15) {
                case 1 :
                    // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:522:9: classOrInterfaceType
                    {
                    pushFollow(FOLLOW_classOrInterfaceType_in_type1345);
                    classOrInterfaceType29=classOrInterfaceType();

                    state._fsp--;
                    if (state.failed) return value;
                    if ( state.backtracking==0 ) {
                       value = classOrInterfaceType29; 
                    }

                    }
                    break;
                case 2 :
                    // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:523:9: primitiveType
                    {
                    pushFollow(FOLLOW_primitiveType_in_type1358);
                    primitiveType30=primitiveType();

                    state._fsp--;
                    if (state.failed) return value;
                    if ( state.backtracking==0 ) {
                       value = primitiveType30; 
                    }

                    }
                    break;

            }
        }

        catch (RecognitionException re) {
        	reportError(re);
        	throw re;
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 27, type_StartIndex); }
        }
        return value;
    }
    // $ANTLR end "type"


    // $ANTLR start "classOrInterfaceType"
    // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:526:1: classOrInterfaceType returns [Class value] : qualifiedName {...}?;
    public final Class classOrInterfaceType() throws RecognitionException {
        Class value = null;
        int classOrInterfaceType_StartIndex = input.index();
        TakeParser.qualifiedName_return qualifiedName31 = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 28) ) { return value; }
            // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:527:5: ( qualifiedName {...}?)
            // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:527:9: qualifiedName {...}?
            {
            pushFollow(FOLLOW_qualifiedName_in_classOrInterfaceType1391);
            qualifiedName31=qualifiedName();

            state._fsp--;
            if (state.failed) return value;
            if ( !(( isValidClass((qualifiedName31!=null?input.toString(qualifiedName31.start,qualifiedName31.stop):null)) )) ) {
                if (state.backtracking>0) {state.failed=true; return value;}
                throw new FailedPredicateException(input, "classOrInterfaceType", " isValidClass($qualifiedName.text) ");
            }
            if ( state.backtracking==0 ) {

                          value = createClass((qualifiedName31!=null?input.toString(qualifiedName31.start,qualifiedName31.stop):null));
                      
            }

            }

        }

        catch (RecognitionException re) {
        	reportError(re);
        	throw re;
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 28, classOrInterfaceType_StartIndex); }
        }
        return value;
    }
    // $ANTLR end "classOrInterfaceType"

    public static class qualifiedName_return extends ParserRuleReturnScope {
    };

    // $ANTLR start "qualifiedName"
    // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:533:1: qualifiedName : Identifier ( '.' Identifier )* ;
    public final TakeParser.qualifiedName_return qualifiedName() throws RecognitionException {
        TakeParser.qualifiedName_return retval = new TakeParser.qualifiedName_return();
        retval.start = input.LT(1);
        int qualifiedName_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 29) ) { return retval; }
            // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:534:5: ( Identifier ( '.' Identifier )* )
            // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:534:9: Identifier ( '.' Identifier )*
            {
            match(input,Identifier,FOLLOW_Identifier_in_qualifiedName1422); if (state.failed) return retval;
            // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:534:20: ( '.' Identifier )*
            loop16:
            do {
                int alt16=2;
                int LA16_0 = input.LA(1);

                if ( (LA16_0==51) ) {
                    alt16=1;
                }


                switch (alt16) {
            	case 1 :
            	    // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:534:21: '.' Identifier
            	    {
            	    match(input,51,FOLLOW_51_in_qualifiedName1425); if (state.failed) return retval;
            	    match(input,Identifier,FOLLOW_Identifier_in_qualifiedName1427); if (state.failed) return retval;

            	    }
            	    break;

            	default :
            	    break loop16;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

        }

        catch (RecognitionException re) {
        	reportError(re);
        	throw re;
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 29, qualifiedName_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "qualifiedName"


    // $ANTLR start "primitiveType"
    // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:537:1: primitiveType returns [Class value] : ( 'boolean' | 'char' | 'byte' | 'short' | 'int' | 'long' | 'float' | 'double' );
    public final Class primitiveType() throws RecognitionException {
        Class value = null;
        int primitiveType_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 30) ) { return value; }
            // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:538:5: ( 'boolean' | 'char' | 'byte' | 'short' | 'int' | 'long' | 'float' | 'double' )
            int alt17=8;
            switch ( input.LA(1) ) {
            case 52:
                {
                alt17=1;
                }
                break;
            case 53:
                {
                alt17=2;
                }
                break;
            case 54:
                {
                alt17=3;
                }
                break;
            case 55:
                {
                alt17=4;
                }
                break;
            case 56:
                {
                alt17=5;
                }
                break;
            case 57:
                {
                alt17=6;
                }
                break;
            case 58:
                {
                alt17=7;
                }
                break;
            case 59:
                {
                alt17=8;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return value;}
                NoViableAltException nvae =
                    new NoViableAltException("", 17, 0, input);

                throw nvae;
            }

            switch (alt17) {
                case 1 :
                    // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:538:9: 'boolean'
                    {
                    match(input,52,FOLLOW_52_in_primitiveType1452); if (state.failed) return value;
                    if ( state.backtracking==0 ) {
                       value = Boolean.TYPE; 
                    }

                    }
                    break;
                case 2 :
                    // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:539:9: 'char'
                    {
                    match(input,53,FOLLOW_53_in_primitiveType1465); if (state.failed) return value;
                    if ( state.backtracking==0 ) {
                       value = Character.TYPE; 
                    }

                    }
                    break;
                case 3 :
                    // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:540:9: 'byte'
                    {
                    match(input,54,FOLLOW_54_in_primitiveType1481); if (state.failed) return value;
                    if ( state.backtracking==0 ) {
                       value = Byte.TYPE; 
                    }

                    }
                    break;
                case 4 :
                    // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:541:9: 'short'
                    {
                    match(input,55,FOLLOW_55_in_primitiveType1497); if (state.failed) return value;
                    if ( state.backtracking==0 ) {
                       value = Short.TYPE; 
                    }

                    }
                    break;
                case 5 :
                    // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:542:9: 'int'
                    {
                    match(input,56,FOLLOW_56_in_primitiveType1512); if (state.failed) return value;
                    if ( state.backtracking==0 ) {
                       value = Integer.TYPE; 
                    }

                    }
                    break;
                case 6 :
                    // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:543:9: 'long'
                    {
                    match(input,57,FOLLOW_57_in_primitiveType1529); if (state.failed) return value;
                    if ( state.backtracking==0 ) {
                       value = Long.TYPE; 
                    }

                    }
                    break;
                case 7 :
                    // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:544:9: 'float'
                    {
                    match(input,58,FOLLOW_58_in_primitiveType1545); if (state.failed) return value;
                    if ( state.backtracking==0 ) {
                       value = Float.TYPE; 
                    }

                    }
                    break;
                case 8 :
                    // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:545:9: 'double'
                    {
                    match(input,59,FOLLOW_59_in_primitiveType1560); if (state.failed) return value;
                    if ( state.backtracking==0 ) {
                       value = Double.TYPE; 
                    }

                    }
                    break;

            }
        }

        catch (RecognitionException re) {
        	reportError(re);
        	throw re;
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 30, primitiveType_StartIndex); }
        }
        return value;
    }
    // $ANTLR end "primitiveType"

    // $ANTLR start synpred22_Take
    public final void synpred22_Take_fragment() throws RecognitionException {   
        // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:484:9: ( existingPredicate )
        // /Users/carlos/Projects/Eclipse Workspace/take/grammar/Take.g:484:9: existingPredicate
        {
        pushFollow(FOLLOW_existingPredicate_in_synpred22_Take1073);
        existingPredicate();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred22_Take

    // Delegated rules

    public final boolean synpred22_Take() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred22_Take_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }


    protected DFA3 dfa3 = new DFA3(this);
    protected DFA13 dfa13 = new DFA13(this);
    static final String DFA3_eotS =
        "\12\uffff";
    static final String DFA3_eofS =
        "\12\uffff";
    static final String DFA3_minS =
        "\1\10\5\uffff\1\56\1\10\2\uffff";
    static final String DFA3_maxS =
        "\1\55\5\uffff\1\56\1\57\2\uffff";
    static final String DFA3_acceptS =
        "\1\uffff\1\1\1\2\1\3\1\4\1\5\2\uffff\1\7\1\6";
    static final String DFA3_specialS =
        "\12\uffff}>";
    static final String[] DFA3_transitionS = {
            "\1\6\26\uffff\1\1\1\uffff\1\2\1\3\6\uffff\1\4\3\uffff\1\5",
            "",
            "",
            "",
            "",
            "",
            "\1\7",
            "\1\11\46\uffff\1\10",
            "",
            ""
    };

    static final short[] DFA3_eot = DFA.unpackEncodedString(DFA3_eotS);
    static final short[] DFA3_eof = DFA.unpackEncodedString(DFA3_eofS);
    static final char[] DFA3_min = DFA.unpackEncodedStringToUnsignedChars(DFA3_minS);
    static final char[] DFA3_max = DFA.unpackEncodedStringToUnsignedChars(DFA3_maxS);
    static final short[] DFA3_accept = DFA.unpackEncodedString(DFA3_acceptS);
    static final short[] DFA3_special = DFA.unpackEncodedString(DFA3_specialS);
    static final short[][] DFA3_transition;

    static {
        int numStates = DFA3_transitionS.length;
        DFA3_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA3_transition[i] = DFA.unpackEncodedString(DFA3_transitionS[i]);
        }
    }

    class DFA3 extends DFA {

        public DFA3(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 3;
            this.eot = DFA3_eot;
            this.eof = DFA3_eof;
            this.min = DFA3_min;
            this.max = DFA3_max;
            this.accept = DFA3_accept;
            this.special = DFA3_special;
            this.transition = DFA3_transition;
        }
        public String getDescription() {
            return "357:9: ( variableDeclarations | constantDeclarations | aggregationDeclaration | queryDeclaration | factStoreDeclaration | factDeclaration | ruleDeclaration )";
        }
    }
    static final String DFA13_eotS =
        "\11\uffff";
    static final String DFA13_eofS =
        "\11\uffff";
    static final String DFA13_minS =
        "\1\10\1\52\1\10\1\40\1\10\1\0\1\40\2\uffff";
    static final String DFA13_maxS =
        "\1\10\1\52\1\16\1\52\1\16\1\0\1\52\2\uffff";
    static final String DFA13_acceptS =
        "\7\uffff\1\1\1\2";
    static final String DFA13_specialS =
        "\5\uffff\1\0\3\uffff}>";
    static final String[] DFA13_transitionS = {
            "\1\1",
            "\1\2",
            "\7\3",
            "\1\4\11\uffff\1\5",
            "\7\6",
            "\1\uffff",
            "\1\4\11\uffff\1\5",
            "",
            ""
    };

    static final short[] DFA13_eot = DFA.unpackEncodedString(DFA13_eotS);
    static final short[] DFA13_eof = DFA.unpackEncodedString(DFA13_eofS);
    static final char[] DFA13_min = DFA.unpackEncodedStringToUnsignedChars(DFA13_minS);
    static final char[] DFA13_max = DFA.unpackEncodedStringToUnsignedChars(DFA13_maxS);
    static final short[] DFA13_accept = DFA.unpackEncodedString(DFA13_acceptS);
    static final short[] DFA13_special = DFA.unpackEncodedString(DFA13_specialS);
    static final short[][] DFA13_transition;

    static {
        int numStates = DFA13_transitionS.length;
        DFA13_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA13_transition[i] = DFA.unpackEncodedString(DFA13_transitionS[i]);
        }
    }

    class DFA13 extends DFA {

        public DFA13(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 13;
            this.eot = DFA13_eot;
            this.eof = DFA13_eof;
            this.min = DFA13_min;
            this.max = DFA13_max;
            this.accept = DFA13_accept;
            this.special = DFA13_special;
            this.transition = DFA13_transition;
        }
        public String getDescription() {
            return "483:1: predicate returns [Predicate value, TermList terms] : ( existingPredicate | newPredicate );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA13_5 = input.LA(1);

                         
                        int index13_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred22_Take()) ) {s = 7;}

                        else if ( (true) ) {s = 8;}

                         
                        input.seek(index13_5);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 13, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

    public static final BitSet FOLLOW_globalAnnotation_in_script106 = new BitSet(new long[]{0x0000220680000132L});
    public static final BitSet FOLLOW_declaration_in_script120 = new BitSet(new long[]{0x0000220680000132L});
    public static final BitSet FOLLOW_localAnnotation_in_declaration159 = new BitSet(new long[]{0x0000220680000170L});
    public static final BitSet FOLLOW_variableDeclarations_in_declaration174 = new BitSet(new long[]{0x0000000000000042L});
    public static final BitSet FOLLOW_constantDeclarations_in_declaration193 = new BitSet(new long[]{0x0000000000000042L});
    public static final BitSet FOLLOW_aggregationDeclaration_in_declaration212 = new BitSet(new long[]{0x0000000000000042L});
    public static final BitSet FOLLOW_queryDeclaration_in_declaration226 = new BitSet(new long[]{0x0000000000000042L});
    public static final BitSet FOLLOW_factStoreDeclaration_in_declaration249 = new BitSet(new long[]{0x0000000000000042L});
    public static final BitSet FOLLOW_factDeclaration_in_declaration268 = new BitSet(new long[]{0x0000000000000042L});
    public static final BitSet FOLLOW_ruleDeclaration_in_declaration292 = new BitSet(new long[]{0x0000000000000042L});
    public static final BitSet FOLLOW_Newline_in_declaration314 = new BitSet(new long[]{0x0000000000000042L});
    public static final BitSet FOLLOW_GlobalAnnotationKey_in_globalAnnotation334 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_AnnotationValue_in_globalAnnotation336 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LocalAnnotationKey_in_localAnnotation357 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_AnnotationValue_in_localAnnotation359 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_31_in_variableDeclarations389 = new BitSet(new long[]{0x0FF0000000000100L});
    public static final BitSet FOLLOW_type_in_variableDeclarations391 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_Identifier_in_variableDeclarations395 = new BitSet(new long[]{0x0000000100000002L});
    public static final BitSet FOLLOW_32_in_variableDeclarations398 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_Identifier_in_variableDeclarations402 = new BitSet(new long[]{0x0000000100000002L});
    public static final BitSet FOLLOW_33_in_constantDeclarations442 = new BitSet(new long[]{0x0FF0000000000100L});
    public static final BitSet FOLLOW_type_in_constantDeclarations444 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_Identifier_in_constantDeclarations448 = new BitSet(new long[]{0x0000000100000002L});
    public static final BitSet FOLLOW_32_in_constantDeclarations451 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_Identifier_in_constantDeclarations455 = new BitSet(new long[]{0x0000000100000002L});
    public static final BitSet FOLLOW_34_in_aggregationDeclaration494 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_Identifier_in_aggregationDeclaration496 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_35_in_aggregationDeclaration498 = new BitSet(new long[]{0x000001F000000000L});
    public static final BitSet FOLLOW_aggregateFunction_in_aggregationDeclaration500 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_variable_in_aggregationDeclaration502 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_predicate_in_aggregationDeclaration504 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_36_in_aggregateFunction537 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_37_in_aggregateFunction551 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_38_in_aggregateFunction565 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_39_in_aggregateFunction579 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_40_in_aggregateFunction593 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Identifier_in_variable620 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_41_in_queryDeclaration647 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_Identifier_in_queryDeclaration651 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_42_in_queryDeclaration653 = new BitSet(new long[]{0x0000180000000000L});
    public static final BitSet FOLLOW_ioStates_in_queryDeclaration655 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_42_in_queryDeclaration657 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ioState_in_ioStates697 = new BitSet(new long[]{0x0000000100000002L});
    public static final BitSet FOLLOW_32_in_ioStates702 = new BitSet(new long[]{0x0000180000000000L});
    public static final BitSet FOLLOW_ioState_in_ioStates706 = new BitSet(new long[]{0x0000000100000002L});
    public static final BitSet FOLLOW_43_in_ioState732 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_44_in_ioState746 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_45_in_factStoreDeclaration777 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Identifier_in_factDeclaration803 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_46_in_factDeclaration805 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_predicate_in_factDeclaration807 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Identifier_in_ruleDeclaration842 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_46_in_ruleDeclaration844 = new BitSet(new long[]{0x0000800000000000L});
    public static final BitSet FOLLOW_47_in_ruleDeclaration846 = new BitSet(new long[]{0x0004000000000300L});
    public static final BitSet FOLLOW_prerequisites_in_ruleDeclaration850 = new BitSet(new long[]{0x0001000000000000L});
    public static final BitSet FOLLOW_48_in_ruleDeclaration852 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_predicate_in_ruleDeclaration854 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_prerequisite_in_prerequisites898 = new BitSet(new long[]{0x0002000000000002L});
    public static final BitSet FOLLOW_49_in_prerequisites903 = new BitSet(new long[]{0x0004000000000300L});
    public static final BitSet FOLLOW_prerequisite_in_prerequisites907 = new BitSet(new long[]{0x0002000000000002L});
    public static final BitSet FOLLOW_factPrerequisite_in_prerequisite934 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expressionPrerequisite_in_prerequisite946 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_negatablePredicate_in_factPrerequisite971 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Expression_in_expressionPrerequisite1002 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_50_in_negatablePredicate1034 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_predicate_in_negatablePredicate1036 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_predicate_in_negatablePredicate1045 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_existingPredicate_in_predicate1073 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_newPredicate_in_predicate1086 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Identifier_in_existingPredicate1116 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_42_in_existingPredicate1118 = new BitSet(new long[]{0x0000000000007F00L});
    public static final BitSet FOLLOW_terms_in_existingPredicate1122 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_42_in_existingPredicate1124 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Identifier_in_newPredicate1156 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_42_in_newPredicate1158 = new BitSet(new long[]{0x0000000000007F00L});
    public static final BitSet FOLLOW_terms_in_newPredicate1162 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_42_in_newPredicate1164 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_term_in_terms1204 = new BitSet(new long[]{0x0000000100000002L});
    public static final BitSet FOLLOW_32_in_terms1209 = new BitSet(new long[]{0x0000000000007F00L});
    public static final BitSet FOLLOW_term_in_terms1213 = new BitSet(new long[]{0x0000000100000002L});
    public static final BitSet FOLLOW_complexTerm_in_term1244 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_complexTerm1272 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_classOrInterfaceType_in_type1345 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_primitiveType_in_type1358 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_qualifiedName_in_classOrInterfaceType1391 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Identifier_in_qualifiedName1422 = new BitSet(new long[]{0x0008000000000002L});
    public static final BitSet FOLLOW_51_in_qualifiedName1425 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_Identifier_in_qualifiedName1427 = new BitSet(new long[]{0x0008000000000002L});
    public static final BitSet FOLLOW_52_in_primitiveType1452 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_53_in_primitiveType1465 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_54_in_primitiveType1481 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_primitiveType1497 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_56_in_primitiveType1512 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_57_in_primitiveType1529 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_58_in_primitiveType1545 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_59_in_primitiveType1560 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_existingPredicate_in_synpred22_Take1073 = new BitSet(new long[]{0x0000000000000002L});

}