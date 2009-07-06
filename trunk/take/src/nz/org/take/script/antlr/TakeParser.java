// $ANTLR 3.1.1 /home/jens/development/take/workspace/take/grammar/Take.g 2008-12-12 12:53:19

package nz.org.take.script.antlr;

import java.util.HashMap;
import java.util.Map;
import nz.org.take.Constant;
import nz.org.take.DefaultKnowledgeBase;
import nz.org.take.KnowledgeBase;
import nz.org.take.Variable;


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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "ID", "LINE_TEXT", "NEWLINE", "LINE_COMMENT", "WHITESPACE", "'@@'", "'='", "'@'", "'var'", "','", "'ref'", "'aggregation'", "'avg'", "'min'", "'max'", "'sum'", "'count'", "'external'", "'.'", "'boolean'", "'char'", "'byte'", "'short'", "'int'", "'long'", "'float'", "'double'"
    };
    public static final int T__29=29;
    public static final int T__28=28;
    public static final int T__27=27;
    public static final int T__26=26;
    public static final int LINE_COMMENT=7;
    public static final int T__25=25;
    public static final int T__24=24;
    public static final int T__23=23;
    public static final int T__22=22;
    public static final int T__21=21;
    public static final int LINE_TEXT=5;
    public static final int T__20=20;
    public static final int WHITESPACE=8;
    public static final int ID=4;
    public static final int EOF=-1;
    public static final int T__9=9;
    public static final int T__30=30;
    public static final int T__19=19;
    public static final int T__16=16;
    public static final int T__15=15;
    public static final int NEWLINE=6;
    public static final int T__18=18;
    public static final int T__17=17;
    public static final int T__12=12;
    public static final int T__11=11;
    public static final int T__14=14;
    public static final int T__13=13;
    public static final int T__10=10;

    // delegates
    // delegators


        public TakeParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public TakeParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
            this.state.ruleMemo = new HashMap[41+1];
             
             
        }
        

    public String[] getTokenNames() { return TakeParser.tokenNames; }
    public String getGrammarFileName() { return "/home/jens/development/take/workspace/take/grammar/Take.g"; }


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




    // $ANTLR start "script"
    // /home/jens/development/take/workspace/take/grammar/Take.g:54:1: script : ( globalAnnotation | declaration | comment )* ;
    public final void script() throws RecognitionException {
        int script_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 1) ) { return ; }
            // /home/jens/development/take/workspace/take/grammar/Take.g:55:2: ( ( globalAnnotation | declaration | comment )* )
            // /home/jens/development/take/workspace/take/grammar/Take.g:55:4: ( globalAnnotation | declaration | comment )*
            {
            // /home/jens/development/take/workspace/take/grammar/Take.g:55:4: ( globalAnnotation | declaration | comment )*
            loop1:
            do {
                int alt1=4;
                switch ( input.LA(1) ) {
                case EOF:
                    {
                    int LA1_1 = input.LA(2);

                    if ( (synpred2_Take()) ) {
                        alt1=2;
                    }


                    }
                    break;
                case 9:
                    {
                    int LA1_2 = input.LA(2);

                    if ( (synpred1_Take()) ) {
                        alt1=1;
                    }
                    else if ( (synpred2_Take()) ) {
                        alt1=2;
                    }


                    }
                    break;
                case 11:
                case 12:
                case 14:
                case 15:
                case 21:
                    {
                    alt1=2;
                    }
                    break;
                case LINE_COMMENT:
                    {
                    int LA1_4 = input.LA(2);

                    if ( (synpred2_Take()) ) {
                        alt1=2;
                    }
                    else if ( (synpred3_Take()) ) {
                        alt1=3;
                    }


                    }
                    break;

                }

                switch (alt1) {
            	case 1 :
            	    // /home/jens/development/take/workspace/take/grammar/Take.g:55:6: globalAnnotation
            	    {
            	    pushFollow(FOLLOW_globalAnnotation_in_script57);
            	    globalAnnotation();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;
            	case 2 :
            	    // /home/jens/development/take/workspace/take/grammar/Take.g:56:5: declaration
            	    {
            	    pushFollow(FOLLOW_declaration_in_script63);
            	    declaration();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;
            	case 3 :
            	    // /home/jens/development/take/workspace/take/grammar/Take.g:57:5: comment
            	    {
            	    pushFollow(FOLLOW_comment_in_script69);
            	    comment();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 1, script_StartIndex); }
        }
        return ;
    }
    // $ANTLR end "script"

    protected static class declaration_scope {
        Map<String, String> annotations;
    }
    protected Stack declaration_stack = new Stack();


    // $ANTLR start "declaration"
    // /home/jens/development/take/workspace/take/grammar/Take.g:61:1: declaration : ( localAnnotation )* ( variable | reference | aggregation | query | factStore | rule ) ;
    public final void declaration() throws RecognitionException {
        declaration_stack.push(new declaration_scope());
        int declaration_StartIndex = input.index();
         ((declaration_scope)declaration_stack.peek()).annotations = new HashMap<String, String>(); 
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 2) ) { return ; }
            // /home/jens/development/take/workspace/take/grammar/Take.g:64:2: ( ( localAnnotation )* ( variable | reference | aggregation | query | factStore | rule ) )
            // /home/jens/development/take/workspace/take/grammar/Take.g:64:4: ( localAnnotation )* ( variable | reference | aggregation | query | factStore | rule )
            {
            // /home/jens/development/take/workspace/take/grammar/Take.g:64:4: ( localAnnotation )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==11) ) {
                    int LA2_2 = input.LA(2);

                    if ( (synpred4_Take()) ) {
                        alt2=1;
                    }


                }


                switch (alt2) {
            	case 1 :
            	    // /home/jens/development/take/workspace/take/grammar/Take.g:0:0: localAnnotation
            	    {
            	    pushFollow(FOLLOW_localAnnotation_in_declaration94);
            	    localAnnotation();

            	    state._fsp--;
            	    if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);

            // /home/jens/development/take/workspace/take/grammar/Take.g:65:3: ( variable | reference | aggregation | query | factStore | rule )
            int alt3=6;
            alt3 = dfa3.predict(input);
            switch (alt3) {
                case 1 :
                    // /home/jens/development/take/workspace/take/grammar/Take.g:65:5: variable
                    {
                    pushFollow(FOLLOW_variable_in_declaration101);
                    variable();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 2 :
                    // /home/jens/development/take/workspace/take/grammar/Take.g:66:5: reference
                    {
                    pushFollow(FOLLOW_reference_in_declaration107);
                    reference();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 3 :
                    // /home/jens/development/take/workspace/take/grammar/Take.g:67:5: aggregation
                    {
                    pushFollow(FOLLOW_aggregation_in_declaration113);
                    aggregation();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 4 :
                    // /home/jens/development/take/workspace/take/grammar/Take.g:68:5: query
                    {
                    pushFollow(FOLLOW_query_in_declaration119);
                    query();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 5 :
                    // /home/jens/development/take/workspace/take/grammar/Take.g:69:5: factStore
                    {
                    pushFollow(FOLLOW_factStore_in_declaration125);
                    factStore();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;
                case 6 :
                    // /home/jens/development/take/workspace/take/grammar/Take.g:70:5: rule
                    {
                    pushFollow(FOLLOW_rule_in_declaration131);
                    rule();

                    state._fsp--;
                    if (state.failed) return ;

                    }
                    break;

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 2, declaration_StartIndex); }
            declaration_stack.pop();
        }
        return ;
    }
    // $ANTLR end "declaration"


    // $ANTLR start "globalAnnotation"
    // /home/jens/development/take/workspace/take/grammar/Take.g:74:1: globalAnnotation : '@@' key= ID '=' value= LINE_TEXT ;
    public final void globalAnnotation() throws RecognitionException {
        int globalAnnotation_StartIndex = input.index();
        Token key=null;
        Token value=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 3) ) { return ; }
            // /home/jens/development/take/workspace/take/grammar/Take.g:75:2: ( '@@' key= ID '=' value= LINE_TEXT )
            // /home/jens/development/take/workspace/take/grammar/Take.g:75:4: '@@' key= ID '=' value= LINE_TEXT
            {
            match(input,9,FOLLOW_9_in_globalAnnotation146); if (state.failed) return ;
            key=(Token)match(input,ID,FOLLOW_ID_in_globalAnnotation150); if (state.failed) return ;
            match(input,10,FOLLOW_10_in_globalAnnotation152); if (state.failed) return ;
            value=(Token)match(input,LINE_TEXT,FOLLOW_LINE_TEXT_in_globalAnnotation156); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               knowledgeBase.addAnnotation((key!=null?key.getText():null), (value!=null?value.getText():null)); 
            }

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 3, globalAnnotation_StartIndex); }
        }
        return ;
    }
    // $ANTLR end "globalAnnotation"


    // $ANTLR start "localAnnotation"
    // /home/jens/development/take/workspace/take/grammar/Take.g:78:1: localAnnotation : '@' key= ID '=' value= LINE_TEXT ;
    public final void localAnnotation() throws RecognitionException {
        int localAnnotation_StartIndex = input.index();
        Token key=null;
        Token value=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 4) ) { return ; }
            // /home/jens/development/take/workspace/take/grammar/Take.g:79:2: ( '@' key= ID '=' value= LINE_TEXT )
            // /home/jens/development/take/workspace/take/grammar/Take.g:79:4: '@' key= ID '=' value= LINE_TEXT
            {
            match(input,11,FOLLOW_11_in_localAnnotation169); if (state.failed) return ;
            key=(Token)match(input,ID,FOLLOW_ID_in_localAnnotation173); if (state.failed) return ;
            match(input,10,FOLLOW_10_in_localAnnotation175); if (state.failed) return ;
            value=(Token)match(input,LINE_TEXT,FOLLOW_LINE_TEXT_in_localAnnotation179); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               ((declaration_scope)declaration_stack.peek()).annotations.put((key!=null?key.getText():null), (value!=null?value.getText():null)); 
            }

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 4, localAnnotation_StartIndex); }
        }
        return ;
    }
    // $ANTLR end "localAnnotation"


    // $ANTLR start "variable"
    // /home/jens/development/take/workspace/take/grammar/Take.g:82:1: variable : 'var' type ids+= ID ( ',' ids+= ID )* NEWLINE ;
    public final void variable() throws RecognitionException {
        int variable_StartIndex = input.index();
        Token ids=null;
        List list_ids=null;
        Class type1 = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 5) ) { return ; }
            // /home/jens/development/take/workspace/take/grammar/Take.g:83:2: ( 'var' type ids+= ID ( ',' ids+= ID )* NEWLINE )
            // /home/jens/development/take/workspace/take/grammar/Take.g:83:4: 'var' type ids+= ID ( ',' ids+= ID )* NEWLINE
            {
            match(input,12,FOLLOW_12_in_variable193); if (state.failed) return ;
            pushFollow(FOLLOW_type_in_variable195);
            type1=type();

            state._fsp--;
            if (state.failed) return ;
            ids=(Token)match(input,ID,FOLLOW_ID_in_variable199); if (state.failed) return ;
            if (list_ids==null) list_ids=new ArrayList();
            list_ids.add(ids);

            // /home/jens/development/take/workspace/take/grammar/Take.g:83:23: ( ',' ids+= ID )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( (LA4_0==13) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // /home/jens/development/take/workspace/take/grammar/Take.g:83:24: ',' ids+= ID
            	    {
            	    match(input,13,FOLLOW_13_in_variable202); if (state.failed) return ;
            	    ids=(Token)match(input,ID,FOLLOW_ID_in_variable206); if (state.failed) return ;
            	    if (list_ids==null) list_ids=new ArrayList();
            	    list_ids.add(ids);


            	    }
            	    break;

            	default :
            	    break loop4;
                }
            } while (true);

            match(input,NEWLINE,FOLLOW_NEWLINE_in_variable210); if (state.failed) return ;
            if ( state.backtracking==0 ) {

              			for (String id : list_ids) {
              				Variable variable = createVariable(id, type1);
              				variable.addAnnotations(knowledgeBase.getAnnotations());
              				variable.addAnnotations(((declaration_scope)declaration_stack.peek()).annotations);
              			}
              		
            }

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 5, variable_StartIndex); }
        }
        return ;
    }
    // $ANTLR end "variable"


    // $ANTLR start "reference"
    // /home/jens/development/take/workspace/take/grammar/Take.g:93:1: reference : 'ref' type ids+= ID ( ',' ids+= ID )* NEWLINE ;
    public final void reference() throws RecognitionException {
        int reference_StartIndex = input.index();
        Token ids=null;
        List list_ids=null;
        Class type2 = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 6) ) { return ; }
            // /home/jens/development/take/workspace/take/grammar/Take.g:94:2: ( 'ref' type ids+= ID ( ',' ids+= ID )* NEWLINE )
            // /home/jens/development/take/workspace/take/grammar/Take.g:94:4: 'ref' type ids+= ID ( ',' ids+= ID )* NEWLINE
            {
            match(input,14,FOLLOW_14_in_reference225); if (state.failed) return ;
            pushFollow(FOLLOW_type_in_reference227);
            type2=type();

            state._fsp--;
            if (state.failed) return ;
            ids=(Token)match(input,ID,FOLLOW_ID_in_reference231); if (state.failed) return ;
            if (list_ids==null) list_ids=new ArrayList();
            list_ids.add(ids);

            // /home/jens/development/take/workspace/take/grammar/Take.g:94:23: ( ',' ids+= ID )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==13) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // /home/jens/development/take/workspace/take/grammar/Take.g:94:24: ',' ids+= ID
            	    {
            	    match(input,13,FOLLOW_13_in_reference234); if (state.failed) return ;
            	    ids=(Token)match(input,ID,FOLLOW_ID_in_reference238); if (state.failed) return ;
            	    if (list_ids==null) list_ids=new ArrayList();
            	    list_ids.add(ids);


            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);

            match(input,NEWLINE,FOLLOW_NEWLINE_in_reference242); if (state.failed) return ;
            if ( state.backtracking==0 ) {

              			for (String id : list_ids) {
              				Constant reference = createReference(id, type2);
              				reference.addAnnotations(knowledgeBase.getAnnotations());
              				reference.addAnnotations(((declaration_scope)declaration_stack.peek()).annotations);
              			}
              		
            }

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 6, reference_StartIndex); }
        }
        return ;
    }
    // $ANTLR end "reference"


    // $ANTLR start "aggregation"
    // /home/jens/development/take/workspace/take/grammar/Take.g:104:1: aggregation : 'aggregation' ID '=' aggregateFunction ID aggregateCondition NEWLINE ;
    public final void aggregation() throws RecognitionException {
        int aggregation_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 7) ) { return ; }
            // /home/jens/development/take/workspace/take/grammar/Take.g:105:2: ( 'aggregation' ID '=' aggregateFunction ID aggregateCondition NEWLINE )
            // /home/jens/development/take/workspace/take/grammar/Take.g:105:4: 'aggregation' ID '=' aggregateFunction ID aggregateCondition NEWLINE
            {
            match(input,15,FOLLOW_15_in_aggregation257); if (state.failed) return ;
            match(input,ID,FOLLOW_ID_in_aggregation259); if (state.failed) return ;
            match(input,10,FOLLOW_10_in_aggregation261); if (state.failed) return ;
            pushFollow(FOLLOW_aggregateFunction_in_aggregation263);
            aggregateFunction();

            state._fsp--;
            if (state.failed) return ;
            match(input,ID,FOLLOW_ID_in_aggregation265); if (state.failed) return ;
            pushFollow(FOLLOW_aggregateCondition_in_aggregation267);
            aggregateCondition();

            state._fsp--;
            if (state.failed) return ;
            match(input,NEWLINE,FOLLOW_NEWLINE_in_aggregation269); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 7, aggregation_StartIndex); }
        }
        return ;
    }
    // $ANTLR end "aggregation"


    // $ANTLR start "aggregateFunction"
    // /home/jens/development/take/workspace/take/grammar/Take.g:108:1: aggregateFunction returns [Aggregations aggregation] : ( 'avg' | 'min' | 'max' | 'sum' | 'count' );
    public final Aggregations aggregateFunction() throws RecognitionException {
        Aggregations aggregation = null;
        int aggregateFunction_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 8) ) { return aggregation; }
            // /home/jens/development/take/workspace/take/grammar/Take.g:109:2: ( 'avg' | 'min' | 'max' | 'sum' | 'count' )
            int alt6=5;
            switch ( input.LA(1) ) {
            case 16:
                {
                alt6=1;
                }
                break;
            case 17:
                {
                alt6=2;
                }
                break;
            case 18:
                {
                alt6=3;
                }
                break;
            case 19:
                {
                alt6=4;
                }
                break;
            case 20:
                {
                alt6=5;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return aggregation;}
                NoViableAltException nvae =
                    new NoViableAltException("", 6, 0, input);

                throw nvae;
            }

            switch (alt6) {
                case 1 :
                    // /home/jens/development/take/workspace/take/grammar/Take.g:109:4: 'avg'
                    {
                    match(input,16,FOLLOW_16_in_aggregateFunction284); if (state.failed) return aggregation;
                    if ( state.backtracking==0 ) {
                       aggregation = Aggregations.AVG; 
                    }

                    }
                    break;
                case 2 :
                    // /home/jens/development/take/workspace/take/grammar/Take.g:110:4: 'min'
                    {
                    match(input,17,FOLLOW_17_in_aggregateFunction294); if (state.failed) return aggregation;
                    if ( state.backtracking==0 ) {
                       aggregation = Aggregations.MIN; 
                    }

                    }
                    break;
                case 3 :
                    // /home/jens/development/take/workspace/take/grammar/Take.g:111:4: 'max'
                    {
                    match(input,18,FOLLOW_18_in_aggregateFunction304); if (state.failed) return aggregation;
                    if ( state.backtracking==0 ) {
                       aggregation = Aggregations.MAX; 
                    }

                    }
                    break;
                case 4 :
                    // /home/jens/development/take/workspace/take/grammar/Take.g:112:4: 'sum'
                    {
                    match(input,19,FOLLOW_19_in_aggregateFunction314); if (state.failed) return aggregation;
                    if ( state.backtracking==0 ) {
                       aggregation = Aggregations.SUM; 
                    }

                    }
                    break;
                case 5 :
                    // /home/jens/development/take/workspace/take/grammar/Take.g:113:4: 'count'
                    {
                    match(input,20,FOLLOW_20_in_aggregateFunction324); if (state.failed) return aggregation;
                    if ( state.backtracking==0 ) {
                       aggregation = Aggregations.COUNT; 
                    }

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 8, aggregateFunction_StartIndex); }
        }
        return aggregation;
    }
    // $ANTLR end "aggregateFunction"


    // $ANTLR start "aggregateCondition"
    // /home/jens/development/take/workspace/take/grammar/Take.g:116:1: aggregateCondition : predicate ;
    public final void aggregateCondition() throws RecognitionException {
        int aggregateCondition_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 9) ) { return ; }
            // /home/jens/development/take/workspace/take/grammar/Take.g:117:2: ( predicate )
            // /home/jens/development/take/workspace/take/grammar/Take.g:117:4: predicate
            {
            pushFollow(FOLLOW_predicate_in_aggregateCondition338);
            predicate();

            state._fsp--;
            if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 9, aggregateCondition_StartIndex); }
        }
        return ;
    }
    // $ANTLR end "aggregateCondition"


    // $ANTLR start "query"
    // /home/jens/development/take/workspace/take/grammar/Take.g:120:1: query : ;
    public final void query() throws RecognitionException {
        int query_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 10) ) { return ; }
            // /home/jens/development/take/workspace/take/grammar/Take.g:121:2: ()
            // /home/jens/development/take/workspace/take/grammar/Take.g:122:2: 
            {
            }

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 10, query_StartIndex); }
        }
        return ;
    }
    // $ANTLR end "query"


    // $ANTLR start "factStore"
    // /home/jens/development/take/workspace/take/grammar/Take.g:124:1: factStore : 'external' ;
    public final void factStore() throws RecognitionException {
        int factStore_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 11) ) { return ; }
            // /home/jens/development/take/workspace/take/grammar/Take.g:125:2: ( 'external' )
            // /home/jens/development/take/workspace/take/grammar/Take.g:125:4: 'external'
            {
            match(input,21,FOLLOW_21_in_factStore358); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 11, factStore_StartIndex); }
        }
        return ;
    }
    // $ANTLR end "factStore"


    // $ANTLR start "rule"
    // /home/jens/development/take/workspace/take/grammar/Take.g:128:1: rule : ;
    public final void rule() throws RecognitionException {
        int rule_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 12) ) { return ; }
            // /home/jens/development/take/workspace/take/grammar/Take.g:128:5: ()
            // /home/jens/development/take/workspace/take/grammar/Take.g:129:2: 
            {
            }

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 12, rule_StartIndex); }
        }
        return ;
    }
    // $ANTLR end "rule"


    // $ANTLR start "predicate"
    // /home/jens/development/take/workspace/take/grammar/Take.g:131:1: predicate : ;
    public final void predicate() throws RecognitionException {
        int predicate_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 13) ) { return ; }
            // /home/jens/development/take/workspace/take/grammar/Take.g:132:2: ()
            // /home/jens/development/take/workspace/take/grammar/Take.g:133:2: 
            {
            }

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 13, predicate_StartIndex); }
        }
        return ;
    }
    // $ANTLR end "predicate"


    // $ANTLR start "type"
    // /home/jens/development/take/workspace/take/grammar/Take.g:135:1: type returns [Class type] : ( classOrInterfaceType | primitiveType );
    public final Class type() throws RecognitionException {
        Class type = null;
        int type_StartIndex = input.index();
        Class classOrInterfaceType3 = null;

        Class primitiveType4 = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 14) ) { return type; }
            // /home/jens/development/take/workspace/take/grammar/Take.g:136:2: ( classOrInterfaceType | primitiveType )
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==ID) ) {
                alt7=1;
            }
            else if ( ((LA7_0>=23 && LA7_0<=30)) ) {
                alt7=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return type;}
                NoViableAltException nvae =
                    new NoViableAltException("", 7, 0, input);

                throw nvae;
            }
            switch (alt7) {
                case 1 :
                    // /home/jens/development/take/workspace/take/grammar/Take.g:136:4: classOrInterfaceType
                    {
                    pushFollow(FOLLOW_classOrInterfaceType_in_type391);
                    classOrInterfaceType3=classOrInterfaceType();

                    state._fsp--;
                    if (state.failed) return type;
                    if ( state.backtracking==0 ) {
                       type = classOrInterfaceType3; 
                    }

                    }
                    break;
                case 2 :
                    // /home/jens/development/take/workspace/take/grammar/Take.g:137:4: primitiveType
                    {
                    pushFollow(FOLLOW_primitiveType_in_type399);
                    primitiveType4=primitiveType();

                    state._fsp--;
                    if (state.failed) return type;
                    if ( state.backtracking==0 ) {
                       type = primitiveType4; 
                    }

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 14, type_StartIndex); }
        }
        return type;
    }
    // $ANTLR end "type"


    // $ANTLR start "classOrInterfaceType"
    // /home/jens/development/take/workspace/take/grammar/Take.g:140:1: classOrInterfaceType returns [Class type] : ID ( '.' ID )* ;
    public final Class classOrInterfaceType() throws RecognitionException {
        Class type = null;
        int classOrInterfaceType_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 15) ) { return type; }
            // /home/jens/development/take/workspace/take/grammar/Take.g:141:2: ( ID ( '.' ID )* )
            // /home/jens/development/take/workspace/take/grammar/Take.g:141:4: ID ( '.' ID )*
            {
            match(input,ID,FOLLOW_ID_in_classOrInterfaceType424); if (state.failed) return type;
            // /home/jens/development/take/workspace/take/grammar/Take.g:141:7: ( '.' ID )*
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( (LA8_0==22) ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // /home/jens/development/take/workspace/take/grammar/Take.g:141:8: '.' ID
            	    {
            	    match(input,22,FOLLOW_22_in_classOrInterfaceType427); if (state.failed) return type;
            	    match(input,ID,FOLLOW_ID_in_classOrInterfaceType429); if (state.failed) return type;

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
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 15, classOrInterfaceType_StartIndex); }
        }
        return type;
    }
    // $ANTLR end "classOrInterfaceType"


    // $ANTLR start "primitiveType"
    // /home/jens/development/take/workspace/take/grammar/Take.g:144:1: primitiveType returns [Class type] : ( 'boolean' | 'char' | 'byte' | 'short' | 'int' | 'long' | 'float' | 'double' );
    public final Class primitiveType() throws RecognitionException {
        Class type = null;
        int primitiveType_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 16) ) { return type; }
            // /home/jens/development/take/workspace/take/grammar/Take.g:145:5: ( 'boolean' | 'char' | 'byte' | 'short' | 'int' | 'long' | 'float' | 'double' )
            int alt9=8;
            switch ( input.LA(1) ) {
            case 23:
                {
                alt9=1;
                }
                break;
            case 24:
                {
                alt9=2;
                }
                break;
            case 25:
                {
                alt9=3;
                }
                break;
            case 26:
                {
                alt9=4;
                }
                break;
            case 27:
                {
                alt9=5;
                }
                break;
            case 28:
                {
                alt9=6;
                }
                break;
            case 29:
                {
                alt9=7;
                }
                break;
            case 30:
                {
                alt9=8;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return type;}
                NoViableAltException nvae =
                    new NoViableAltException("", 9, 0, input);

                throw nvae;
            }

            switch (alt9) {
                case 1 :
                    // /home/jens/development/take/workspace/take/grammar/Take.g:145:9: 'boolean'
                    {
                    match(input,23,FOLLOW_23_in_primitiveType452); if (state.failed) return type;
                    if ( state.backtracking==0 ) {
                       type = Boolean.TYPE; 
                    }

                    }
                    break;
                case 2 :
                    // /home/jens/development/take/workspace/take/grammar/Take.g:146:9: 'char'
                    {
                    match(input,24,FOLLOW_24_in_primitiveType465); if (state.failed) return type;
                    if ( state.backtracking==0 ) {
                       type = Character.TYPE; 
                    }

                    }
                    break;
                case 3 :
                    // /home/jens/development/take/workspace/take/grammar/Take.g:147:9: 'byte'
                    {
                    match(input,25,FOLLOW_25_in_primitiveType481); if (state.failed) return type;
                    if ( state.backtracking==0 ) {
                       type = Byte.TYPE; 
                    }

                    }
                    break;
                case 4 :
                    // /home/jens/development/take/workspace/take/grammar/Take.g:148:9: 'short'
                    {
                    match(input,26,FOLLOW_26_in_primitiveType497); if (state.failed) return type;
                    if ( state.backtracking==0 ) {
                       type = Short.TYPE; 
                    }

                    }
                    break;
                case 5 :
                    // /home/jens/development/take/workspace/take/grammar/Take.g:149:9: 'int'
                    {
                    match(input,27,FOLLOW_27_in_primitiveType512); if (state.failed) return type;
                    if ( state.backtracking==0 ) {
                       type = Integer.TYPE; 
                    }

                    }
                    break;
                case 6 :
                    // /home/jens/development/take/workspace/take/grammar/Take.g:150:9: 'long'
                    {
                    match(input,28,FOLLOW_28_in_primitiveType529); if (state.failed) return type;
                    if ( state.backtracking==0 ) {
                       type = Long.TYPE; 
                    }

                    }
                    break;
                case 7 :
                    // /home/jens/development/take/workspace/take/grammar/Take.g:151:9: 'float'
                    {
                    match(input,29,FOLLOW_29_in_primitiveType545); if (state.failed) return type;
                    if ( state.backtracking==0 ) {
                       type = Float.TYPE; 
                    }

                    }
                    break;
                case 8 :
                    // /home/jens/development/take/workspace/take/grammar/Take.g:152:9: 'double'
                    {
                    match(input,30,FOLLOW_30_in_primitiveType560); if (state.failed) return type;
                    if ( state.backtracking==0 ) {
                       type = Double.TYPE; 
                    }

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 16, primitiveType_StartIndex); }
        }
        return type;
    }
    // $ANTLR end "primitiveType"


    // $ANTLR start "comment"
    // /home/jens/development/take/workspace/take/grammar/Take.g:155:1: comment : LINE_COMMENT ;
    public final void comment() throws RecognitionException {
        int comment_StartIndex = input.index();
        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 17) ) { return ; }
            // /home/jens/development/take/workspace/take/grammar/Take.g:156:2: ( LINE_COMMENT )
            // /home/jens/development/take/workspace/take/grammar/Take.g:156:4: LINE_COMMENT
            {
            match(input,LINE_COMMENT,FOLLOW_LINE_COMMENT_in_comment578); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 17, comment_StartIndex); }
        }
        return ;
    }
    // $ANTLR end "comment"

    // $ANTLR start synpred1_Take
    public final void synpred1_Take_fragment() throws RecognitionException {   
        // /home/jens/development/take/workspace/take/grammar/Take.g:55:6: ( globalAnnotation )
        // /home/jens/development/take/workspace/take/grammar/Take.g:55:6: globalAnnotation
        {
        pushFollow(FOLLOW_globalAnnotation_in_synpred1_Take57);
        globalAnnotation();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred1_Take

    // $ANTLR start synpred2_Take
    public final void synpred2_Take_fragment() throws RecognitionException {   
        // /home/jens/development/take/workspace/take/grammar/Take.g:56:5: ( declaration )
        // /home/jens/development/take/workspace/take/grammar/Take.g:56:5: declaration
        {
        pushFollow(FOLLOW_declaration_in_synpred2_Take63);
        declaration();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred2_Take

    // $ANTLR start synpred3_Take
    public final void synpred3_Take_fragment() throws RecognitionException {   
        // /home/jens/development/take/workspace/take/grammar/Take.g:57:5: ( comment )
        // /home/jens/development/take/workspace/take/grammar/Take.g:57:5: comment
        {
        pushFollow(FOLLOW_comment_in_synpred3_Take69);
        comment();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred3_Take

    // $ANTLR start synpred4_Take
    public final void synpred4_Take_fragment() throws RecognitionException {   
        // /home/jens/development/take/workspace/take/grammar/Take.g:64:4: ( localAnnotation )
        // /home/jens/development/take/workspace/take/grammar/Take.g:64:4: localAnnotation
        {
        pushFollow(FOLLOW_localAnnotation_in_synpred4_Take94);
        localAnnotation();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred4_Take

    // $ANTLR start synpred5_Take
    public final void synpred5_Take_fragment() throws RecognitionException {   
        // /home/jens/development/take/workspace/take/grammar/Take.g:65:5: ( variable )
        // /home/jens/development/take/workspace/take/grammar/Take.g:65:5: variable
        {
        pushFollow(FOLLOW_variable_in_synpred5_Take101);
        variable();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred5_Take

    // $ANTLR start synpred6_Take
    public final void synpred6_Take_fragment() throws RecognitionException {   
        // /home/jens/development/take/workspace/take/grammar/Take.g:66:5: ( reference )
        // /home/jens/development/take/workspace/take/grammar/Take.g:66:5: reference
        {
        pushFollow(FOLLOW_reference_in_synpred6_Take107);
        reference();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred6_Take

    // $ANTLR start synpred7_Take
    public final void synpred7_Take_fragment() throws RecognitionException {   
        // /home/jens/development/take/workspace/take/grammar/Take.g:67:5: ( aggregation )
        // /home/jens/development/take/workspace/take/grammar/Take.g:67:5: aggregation
        {
        pushFollow(FOLLOW_aggregation_in_synpred7_Take113);
        aggregation();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred7_Take

    // $ANTLR start synpred8_Take
    public final void synpred8_Take_fragment() throws RecognitionException {   
        // /home/jens/development/take/workspace/take/grammar/Take.g:68:5: ( query )
        // /home/jens/development/take/workspace/take/grammar/Take.g:68:5: query
        {
        pushFollow(FOLLOW_query_in_synpred8_Take119);
        query();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred8_Take

    // $ANTLR start synpred9_Take
    public final void synpred9_Take_fragment() throws RecognitionException {   
        // /home/jens/development/take/workspace/take/grammar/Take.g:69:5: ( factStore )
        // /home/jens/development/take/workspace/take/grammar/Take.g:69:5: factStore
        {
        pushFollow(FOLLOW_factStore_in_synpred9_Take125);
        factStore();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred9_Take

    // Delegated rules

    public final boolean synpred1_Take() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred1_Take_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred2_Take() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred2_Take_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred9_Take() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred9_Take_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred3_Take() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred3_Take_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred4_Take() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred4_Take_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred7_Take() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred7_Take_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred5_Take() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred5_Take_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred6_Take() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred6_Take_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred8_Take() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred8_Take_fragment(); // can never throw exception
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
    static final String DFA3_eotS =
        "\17\uffff";
    static final String DFA3_eofS =
        "\1\4\16\uffff";
    static final String DFA3_minS =
        "\1\7\10\0\6\uffff";
    static final String DFA3_maxS =
        "\1\25\10\0\6\uffff";
    static final String DFA3_acceptS =
        "\11\uffff\1\1\1\4\1\6\1\2\1\3\1\5";
    static final String DFA3_specialS =
        "\1\uffff\1\3\1\1\1\4\1\7\1\5\1\2\1\0\1\6\6\uffff}>";
    static final String[] DFA3_transitionS = {
            "\1\10\1\uffff\1\5\1\uffff\1\6\1\1\1\uffff\1\2\1\3\5\uffff\1"+
            "\7",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "",
            "",
            "",
            "",
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
            return "65:3: ( variable | reference | aggregation | query | factStore | rule )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA3_7 = input.LA(1);

                         
                        int index3_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_Take()) ) {s = 10;}

                        else if ( (synpred9_Take()) ) {s = 14;}

                        else if ( (true) ) {s = 11;}

                         
                        input.seek(index3_7);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA3_2 = input.LA(1);

                         
                        int index3_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred6_Take()) ) {s = 12;}

                        else if ( (synpred8_Take()) ) {s = 10;}

                        else if ( (true) ) {s = 11;}

                         
                        input.seek(index3_2);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA3_6 = input.LA(1);

                         
                        int index3_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_Take()) ) {s = 10;}

                        else if ( (true) ) {s = 11;}

                         
                        input.seek(index3_6);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA3_1 = input.LA(1);

                         
                        int index3_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_Take()) ) {s = 9;}

                        else if ( (synpred8_Take()) ) {s = 10;}

                        else if ( (true) ) {s = 11;}

                         
                        input.seek(index3_1);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA3_3 = input.LA(1);

                         
                        int index3_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_Take()) ) {s = 13;}

                        else if ( (synpred8_Take()) ) {s = 10;}

                        else if ( (true) ) {s = 11;}

                         
                        input.seek(index3_3);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA3_5 = input.LA(1);

                         
                        int index3_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_Take()) ) {s = 10;}

                        else if ( (true) ) {s = 11;}

                         
                        input.seek(index3_5);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA3_8 = input.LA(1);

                         
                        int index3_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_Take()) ) {s = 10;}

                        else if ( (true) ) {s = 11;}

                         
                        input.seek(index3_8);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA3_4 = input.LA(1);

                         
                        int index3_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_Take()) ) {s = 10;}

                        else if ( (true) ) {s = 11;}

                         
                        input.seek(index3_4);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 3, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

    public static final BitSet FOLLOW_globalAnnotation_in_script57 = new BitSet(new long[]{0x000000000020DA82L});
    public static final BitSet FOLLOW_declaration_in_script63 = new BitSet(new long[]{0x000000000020DA82L});
    public static final BitSet FOLLOW_comment_in_script69 = new BitSet(new long[]{0x000000000020DA82L});
    public static final BitSet FOLLOW_localAnnotation_in_declaration94 = new BitSet(new long[]{0x000000000020D800L});
    public static final BitSet FOLLOW_variable_in_declaration101 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_reference_in_declaration107 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_aggregation_in_declaration113 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_query_in_declaration119 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_factStore_in_declaration125 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rule_in_declaration131 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_9_in_globalAnnotation146 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_globalAnnotation150 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_10_in_globalAnnotation152 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_LINE_TEXT_in_globalAnnotation156 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_11_in_localAnnotation169 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_localAnnotation173 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_10_in_localAnnotation175 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_LINE_TEXT_in_localAnnotation179 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_12_in_variable193 = new BitSet(new long[]{0x000000007F800010L});
    public static final BitSet FOLLOW_type_in_variable195 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_variable199 = new BitSet(new long[]{0x0000000000002040L});
    public static final BitSet FOLLOW_13_in_variable202 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_variable206 = new BitSet(new long[]{0x0000000000002040L});
    public static final BitSet FOLLOW_NEWLINE_in_variable210 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_14_in_reference225 = new BitSet(new long[]{0x000000007F800010L});
    public static final BitSet FOLLOW_type_in_reference227 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_reference231 = new BitSet(new long[]{0x0000000000002040L});
    public static final BitSet FOLLOW_13_in_reference234 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_reference238 = new BitSet(new long[]{0x0000000000002040L});
    public static final BitSet FOLLOW_NEWLINE_in_reference242 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_15_in_aggregation257 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_aggregation259 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_10_in_aggregation261 = new BitSet(new long[]{0x00000000001F0000L});
    public static final BitSet FOLLOW_aggregateFunction_in_aggregation263 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_aggregation265 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_aggregateCondition_in_aggregation267 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_NEWLINE_in_aggregation269 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_16_in_aggregateFunction284 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_17_in_aggregateFunction294 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_18_in_aggregateFunction304 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_19_in_aggregateFunction314 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_20_in_aggregateFunction324 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_predicate_in_aggregateCondition338 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_21_in_factStore358 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_classOrInterfaceType_in_type391 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_primitiveType_in_type399 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_classOrInterfaceType424 = new BitSet(new long[]{0x0000000000400002L});
    public static final BitSet FOLLOW_22_in_classOrInterfaceType427 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_ID_in_classOrInterfaceType429 = new BitSet(new long[]{0x0000000000400002L});
    public static final BitSet FOLLOW_23_in_primitiveType452 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_24_in_primitiveType465 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_25_in_primitiveType481 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_26_in_primitiveType497 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_27_in_primitiveType512 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_28_in_primitiveType529 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_29_in_primitiveType545 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_30_in_primitiveType560 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LINE_COMMENT_in_comment578 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_globalAnnotation_in_synpred1_Take57 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_declaration_in_synpred2_Take63 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_comment_in_synpred3_Take69 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_localAnnotation_in_synpred4_Take94 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_variable_in_synpred5_Take101 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_reference_in_synpred6_Take107 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_aggregation_in_synpred7_Take113 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_query_in_synpred8_Take119 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_factStore_in_synpred9_Take125 = new BitSet(new long[]{0x0000000000000002L});

}