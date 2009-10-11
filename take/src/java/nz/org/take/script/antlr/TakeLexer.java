// $ANTLR 3.1.1 /home/jens/development/take/workspace2/take/grammar/Take.g 2009-10-12 10:36:28

package nz.org.take.script.antlr;

import java.util.List;
import java.util.LinkedList;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class TakeLexer extends Lexer {
    public static final int FloatTypeSuffix=18;
    public static final int OctalLiteral=12;
    public static final int LineComment=29;
    public static final int Exponent=17;
    public static final int Newline=8;
    public static final int AnnotationValue=6;
    public static final int EOF=-1;
    public static final int HexDigit=15;
    public static final int Identifier=7;
    public static final int T__55=55;
    public static final int BlockComment=28;
    public static final int T__56=56;
    public static final int T__57=57;
    public static final int T__58=58;
    public static final int T__51=51;
    public static final int T__52=52;
    public static final int T__53=53;
    public static final int T__54=54;
    public static final int LocalAnnotationKey=5;
    public static final int IDDigit=27;
    public static final int Whitespace=25;
    public static final int T__50=50;
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
    public static final int T__30=30;
    public static final int T__31=31;
    public static final int T__32=32;
    public static final int T__33=33;
    public static final int T__34=34;
    public static final int T__35=35;
    public static final int T__36=36;
    public static final int LocalAnnotation=24;
    public static final int T__37=37;
    public static final int GlobalAnnotationKey=4;
    public static final int T__38=38;
    public static final int T__39=39;
    public static final int UnicodeEscape=20;
    public static final int FloatingPointLiteral=13;
    public static final int AnnotationIdentifier=22;
    public static final int GlobalAnnotation=23;
    public static final int EscapeSequence=19;
    public static final int OctalEscape=21;
    public static final int Letter=26;

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


    // delegates
    // delegators

    public TakeLexer() {;} 
    public TakeLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public TakeLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "/home/jens/development/take/workspace2/take/grammar/Take.g"; }

    // $ANTLR start "T__30"
    public final void mT__30() throws RecognitionException {
        try {
            int _type = T__30;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jens/development/take/workspace2/take/grammar/Take.g:31:7: ( 'var' )
            // /home/jens/development/take/workspace2/take/grammar/Take.g:31:9: 'var'
            {
            match("var"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__30"

    // $ANTLR start "T__31"
    public final void mT__31() throws RecognitionException {
        try {
            int _type = T__31;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jens/development/take/workspace2/take/grammar/Take.g:32:7: ( ',' )
            // /home/jens/development/take/workspace2/take/grammar/Take.g:32:9: ','
            {
            match(','); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__31"

    // $ANTLR start "T__32"
    public final void mT__32() throws RecognitionException {
        try {
            int _type = T__32;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jens/development/take/workspace2/take/grammar/Take.g:33:7: ( 'ref' )
            // /home/jens/development/take/workspace2/take/grammar/Take.g:33:9: 'ref'
            {
            match("ref"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__32"

    // $ANTLR start "T__33"
    public final void mT__33() throws RecognitionException {
        try {
            int _type = T__33;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jens/development/take/workspace2/take/grammar/Take.g:34:7: ( 'aggregation' )
            // /home/jens/development/take/workspace2/take/grammar/Take.g:34:9: 'aggregation'
            {
            match("aggregation"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__33"

    // $ANTLR start "T__34"
    public final void mT__34() throws RecognitionException {
        try {
            int _type = T__34;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jens/development/take/workspace2/take/grammar/Take.g:35:7: ( '=' )
            // /home/jens/development/take/workspace2/take/grammar/Take.g:35:9: '='
            {
            match('='); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__34"

    // $ANTLR start "T__35"
    public final void mT__35() throws RecognitionException {
        try {
            int _type = T__35;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jens/development/take/workspace2/take/grammar/Take.g:36:7: ( 'avg' )
            // /home/jens/development/take/workspace2/take/grammar/Take.g:36:9: 'avg'
            {
            match("avg"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__35"

    // $ANTLR start "T__36"
    public final void mT__36() throws RecognitionException {
        try {
            int _type = T__36;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jens/development/take/workspace2/take/grammar/Take.g:37:7: ( 'min' )
            // /home/jens/development/take/workspace2/take/grammar/Take.g:37:9: 'min'
            {
            match("min"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__36"

    // $ANTLR start "T__37"
    public final void mT__37() throws RecognitionException {
        try {
            int _type = T__37;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jens/development/take/workspace2/take/grammar/Take.g:38:7: ( 'max' )
            // /home/jens/development/take/workspace2/take/grammar/Take.g:38:9: 'max'
            {
            match("max"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__37"

    // $ANTLR start "T__38"
    public final void mT__38() throws RecognitionException {
        try {
            int _type = T__38;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jens/development/take/workspace2/take/grammar/Take.g:39:7: ( 'sum' )
            // /home/jens/development/take/workspace2/take/grammar/Take.g:39:9: 'sum'
            {
            match("sum"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__38"

    // $ANTLR start "T__39"
    public final void mT__39() throws RecognitionException {
        try {
            int _type = T__39;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jens/development/take/workspace2/take/grammar/Take.g:40:7: ( 'count' )
            // /home/jens/development/take/workspace2/take/grammar/Take.g:40:9: 'count'
            {
            match("count"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__39"

    // $ANTLR start "T__40"
    public final void mT__40() throws RecognitionException {
        try {
            int _type = T__40;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jens/development/take/workspace2/take/grammar/Take.g:41:7: ( 'query' )
            // /home/jens/development/take/workspace2/take/grammar/Take.g:41:9: 'query'
            {
            match("query"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__40"

    // $ANTLR start "T__41"
    public final void mT__41() throws RecognitionException {
        try {
            int _type = T__41;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jens/development/take/workspace2/take/grammar/Take.g:42:7: ( '|' )
            // /home/jens/development/take/workspace2/take/grammar/Take.g:42:9: '|'
            {
            match('|'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__41"

    // $ANTLR start "T__42"
    public final void mT__42() throws RecognitionException {
        try {
            int _type = T__42;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jens/development/take/workspace2/take/grammar/Take.g:43:7: ( 'in' )
            // /home/jens/development/take/workspace2/take/grammar/Take.g:43:9: 'in'
            {
            match("in"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__42"

    // $ANTLR start "T__43"
    public final void mT__43() throws RecognitionException {
        try {
            int _type = T__43;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jens/development/take/workspace2/take/grammar/Take.g:44:7: ( 'out' )
            // /home/jens/development/take/workspace2/take/grammar/Take.g:44:9: 'out'
            {
            match("out"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__43"

    // $ANTLR start "T__44"
    public final void mT__44() throws RecognitionException {
        try {
            int _type = T__44;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jens/development/take/workspace2/take/grammar/Take.g:45:7: ( 'external' )
            // /home/jens/development/take/workspace2/take/grammar/Take.g:45:9: 'external'
            {
            match("external"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__44"

    // $ANTLR start "T__45"
    public final void mT__45() throws RecognitionException {
        try {
            int _type = T__45;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jens/development/take/workspace2/take/grammar/Take.g:46:7: ( ':' )
            // /home/jens/development/take/workspace2/take/grammar/Take.g:46:9: ':'
            {
            match(':'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__45"

    // $ANTLR start "T__46"
    public final void mT__46() throws RecognitionException {
        try {
            int _type = T__46;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jens/development/take/workspace2/take/grammar/Take.g:47:7: ( 'if' )
            // /home/jens/development/take/workspace2/take/grammar/Take.g:47:9: 'if'
            {
            match("if"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__46"

    // $ANTLR start "T__47"
    public final void mT__47() throws RecognitionException {
        try {
            int _type = T__47;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jens/development/take/workspace2/take/grammar/Take.g:48:7: ( 'then' )
            // /home/jens/development/take/workspace2/take/grammar/Take.g:48:9: 'then'
            {
            match("then"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__47"

    // $ANTLR start "T__48"
    public final void mT__48() throws RecognitionException {
        try {
            int _type = T__48;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jens/development/take/workspace2/take/grammar/Take.g:49:7: ( 'and' )
            // /home/jens/development/take/workspace2/take/grammar/Take.g:49:9: 'and'
            {
            match("and"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__48"

    // $ANTLR start "T__49"
    public final void mT__49() throws RecognitionException {
        try {
            int _type = T__49;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jens/development/take/workspace2/take/grammar/Take.g:50:7: ( 'not' )
            // /home/jens/development/take/workspace2/take/grammar/Take.g:50:9: 'not'
            {
            match("not"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__49"

    // $ANTLR start "T__50"
    public final void mT__50() throws RecognitionException {
        try {
            int _type = T__50;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jens/development/take/workspace2/take/grammar/Take.g:51:7: ( '.' )
            // /home/jens/development/take/workspace2/take/grammar/Take.g:51:9: '.'
            {
            match('.'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__50"

    // $ANTLR start "T__51"
    public final void mT__51() throws RecognitionException {
        try {
            int _type = T__51;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jens/development/take/workspace2/take/grammar/Take.g:52:7: ( 'boolean' )
            // /home/jens/development/take/workspace2/take/grammar/Take.g:52:9: 'boolean'
            {
            match("boolean"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__51"

    // $ANTLR start "T__52"
    public final void mT__52() throws RecognitionException {
        try {
            int _type = T__52;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jens/development/take/workspace2/take/grammar/Take.g:53:7: ( 'char' )
            // /home/jens/development/take/workspace2/take/grammar/Take.g:53:9: 'char'
            {
            match("char"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__52"

    // $ANTLR start "T__53"
    public final void mT__53() throws RecognitionException {
        try {
            int _type = T__53;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jens/development/take/workspace2/take/grammar/Take.g:54:7: ( 'byte' )
            // /home/jens/development/take/workspace2/take/grammar/Take.g:54:9: 'byte'
            {
            match("byte"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__53"

    // $ANTLR start "T__54"
    public final void mT__54() throws RecognitionException {
        try {
            int _type = T__54;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jens/development/take/workspace2/take/grammar/Take.g:55:7: ( 'short' )
            // /home/jens/development/take/workspace2/take/grammar/Take.g:55:9: 'short'
            {
            match("short"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__54"

    // $ANTLR start "T__55"
    public final void mT__55() throws RecognitionException {
        try {
            int _type = T__55;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jens/development/take/workspace2/take/grammar/Take.g:56:7: ( 'int' )
            // /home/jens/development/take/workspace2/take/grammar/Take.g:56:9: 'int'
            {
            match("int"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__55"

    // $ANTLR start "T__56"
    public final void mT__56() throws RecognitionException {
        try {
            int _type = T__56;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jens/development/take/workspace2/take/grammar/Take.g:57:7: ( 'long' )
            // /home/jens/development/take/workspace2/take/grammar/Take.g:57:9: 'long'
            {
            match("long"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__56"

    // $ANTLR start "T__57"
    public final void mT__57() throws RecognitionException {
        try {
            int _type = T__57;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jens/development/take/workspace2/take/grammar/Take.g:58:7: ( 'float' )
            // /home/jens/development/take/workspace2/take/grammar/Take.g:58:9: 'float'
            {
            match("float"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__57"

    // $ANTLR start "T__58"
    public final void mT__58() throws RecognitionException {
        try {
            int _type = T__58;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jens/development/take/workspace2/take/grammar/Take.g:59:7: ( 'double' )
            // /home/jens/development/take/workspace2/take/grammar/Take.g:59:9: 'double'
            {
            match("double"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__58"

    // $ANTLR start "HexLiteral"
    public final void mHexLiteral() throws RecognitionException {
        try {
            int _type = HexLiteral;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jens/development/take/workspace2/take/grammar/Take.g:531:12: ( '0' ( 'x' | 'X' ) ( HexDigit )+ ( IntegerTypeSuffix )? )
            // /home/jens/development/take/workspace2/take/grammar/Take.g:531:14: '0' ( 'x' | 'X' ) ( HexDigit )+ ( IntegerTypeSuffix )?
            {
            match('0'); 
            if ( input.LA(1)=='X'||input.LA(1)=='x' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // /home/jens/development/take/workspace2/take/grammar/Take.g:531:28: ( HexDigit )+
            int cnt1=0;
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( ((LA1_0>='0' && LA1_0<='9')||(LA1_0>='A' && LA1_0<='F')||(LA1_0>='a' && LA1_0<='f')) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // /home/jens/development/take/workspace2/take/grammar/Take.g:531:28: HexDigit
            	    {
            	    mHexDigit(); 

            	    }
            	    break;

            	default :
            	    if ( cnt1 >= 1 ) break loop1;
                        EarlyExitException eee =
                            new EarlyExitException(1, input);
                        throw eee;
                }
                cnt1++;
            } while (true);

            // /home/jens/development/take/workspace2/take/grammar/Take.g:531:38: ( IntegerTypeSuffix )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0=='L'||LA2_0=='l') ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // /home/jens/development/take/workspace2/take/grammar/Take.g:531:38: IntegerTypeSuffix
                    {
                    mIntegerTypeSuffix(); 

                    }
                    break;

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "HexLiteral"

    // $ANTLR start "DecimalLiteral"
    public final void mDecimalLiteral() throws RecognitionException {
        try {
            int _type = DecimalLiteral;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jens/development/take/workspace2/take/grammar/Take.g:533:16: ( ( '0' | '1' .. '9' ( '0' .. '9' )* ) ( IntegerTypeSuffix )? )
            // /home/jens/development/take/workspace2/take/grammar/Take.g:533:18: ( '0' | '1' .. '9' ( '0' .. '9' )* ) ( IntegerTypeSuffix )?
            {
            // /home/jens/development/take/workspace2/take/grammar/Take.g:533:18: ( '0' | '1' .. '9' ( '0' .. '9' )* )
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0=='0') ) {
                alt4=1;
            }
            else if ( ((LA4_0>='1' && LA4_0<='9')) ) {
                alt4=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;
            }
            switch (alt4) {
                case 1 :
                    // /home/jens/development/take/workspace2/take/grammar/Take.g:533:19: '0'
                    {
                    match('0'); 

                    }
                    break;
                case 2 :
                    // /home/jens/development/take/workspace2/take/grammar/Take.g:533:25: '1' .. '9' ( '0' .. '9' )*
                    {
                    matchRange('1','9'); 
                    // /home/jens/development/take/workspace2/take/grammar/Take.g:533:34: ( '0' .. '9' )*
                    loop3:
                    do {
                        int alt3=2;
                        int LA3_0 = input.LA(1);

                        if ( ((LA3_0>='0' && LA3_0<='9')) ) {
                            alt3=1;
                        }


                        switch (alt3) {
                    	case 1 :
                    	    // /home/jens/development/take/workspace2/take/grammar/Take.g:533:34: '0' .. '9'
                    	    {
                    	    matchRange('0','9'); 

                    	    }
                    	    break;

                    	default :
                    	    break loop3;
                        }
                    } while (true);


                    }
                    break;

            }

            // /home/jens/development/take/workspace2/take/grammar/Take.g:533:45: ( IntegerTypeSuffix )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0=='L'||LA5_0=='l') ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // /home/jens/development/take/workspace2/take/grammar/Take.g:533:45: IntegerTypeSuffix
                    {
                    mIntegerTypeSuffix(); 

                    }
                    break;

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DecimalLiteral"

    // $ANTLR start "OctalLiteral"
    public final void mOctalLiteral() throws RecognitionException {
        try {
            int _type = OctalLiteral;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jens/development/take/workspace2/take/grammar/Take.g:535:14: ( '0' ( '0' .. '7' )+ ( IntegerTypeSuffix )? )
            // /home/jens/development/take/workspace2/take/grammar/Take.g:535:16: '0' ( '0' .. '7' )+ ( IntegerTypeSuffix )?
            {
            match('0'); 
            // /home/jens/development/take/workspace2/take/grammar/Take.g:535:20: ( '0' .. '7' )+
            int cnt6=0;
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( ((LA6_0>='0' && LA6_0<='7')) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // /home/jens/development/take/workspace2/take/grammar/Take.g:535:21: '0' .. '7'
            	    {
            	    matchRange('0','7'); 

            	    }
            	    break;

            	default :
            	    if ( cnt6 >= 1 ) break loop6;
                        EarlyExitException eee =
                            new EarlyExitException(6, input);
                        throw eee;
                }
                cnt6++;
            } while (true);

            // /home/jens/development/take/workspace2/take/grammar/Take.g:535:32: ( IntegerTypeSuffix )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0=='L'||LA7_0=='l') ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // /home/jens/development/take/workspace2/take/grammar/Take.g:535:32: IntegerTypeSuffix
                    {
                    mIntegerTypeSuffix(); 

                    }
                    break;

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "OctalLiteral"

    // $ANTLR start "HexDigit"
    public final void mHexDigit() throws RecognitionException {
        try {
            // /home/jens/development/take/workspace2/take/grammar/Take.g:538:10: ( ( '0' .. '9' | 'a' .. 'f' | 'A' .. 'F' ) )
            // /home/jens/development/take/workspace2/take/grammar/Take.g:538:12: ( '0' .. '9' | 'a' .. 'f' | 'A' .. 'F' )
            {
            if ( (input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='F')||(input.LA(1)>='a' && input.LA(1)<='f') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "HexDigit"

    // $ANTLR start "IntegerTypeSuffix"
    public final void mIntegerTypeSuffix() throws RecognitionException {
        try {
            // /home/jens/development/take/workspace2/take/grammar/Take.g:541:19: ( ( 'l' | 'L' ) )
            // /home/jens/development/take/workspace2/take/grammar/Take.g:541:21: ( 'l' | 'L' )
            {
            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "IntegerTypeSuffix"

    // $ANTLR start "FloatingPointLiteral"
    public final void mFloatingPointLiteral() throws RecognitionException {
        try {
            int _type = FloatingPointLiteral;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jens/development/take/workspace2/take/grammar/Take.g:544:5: ( ( '0' .. '9' )+ '.' ( '0' .. '9' )* ( Exponent )? ( FloatTypeSuffix )? | '.' ( '0' .. '9' )+ ( Exponent )? ( FloatTypeSuffix )? | ( '0' .. '9' )+ Exponent ( FloatTypeSuffix )? | ( '0' .. '9' )+ FloatTypeSuffix )
            int alt18=4;
            alt18 = dfa18.predict(input);
            switch (alt18) {
                case 1 :
                    // /home/jens/development/take/workspace2/take/grammar/Take.g:544:9: ( '0' .. '9' )+ '.' ( '0' .. '9' )* ( Exponent )? ( FloatTypeSuffix )?
                    {
                    // /home/jens/development/take/workspace2/take/grammar/Take.g:544:9: ( '0' .. '9' )+
                    int cnt8=0;
                    loop8:
                    do {
                        int alt8=2;
                        int LA8_0 = input.LA(1);

                        if ( ((LA8_0>='0' && LA8_0<='9')) ) {
                            alt8=1;
                        }


                        switch (alt8) {
                    	case 1 :
                    	    // /home/jens/development/take/workspace2/take/grammar/Take.g:544:10: '0' .. '9'
                    	    {
                    	    matchRange('0','9'); 

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt8 >= 1 ) break loop8;
                                EarlyExitException eee =
                                    new EarlyExitException(8, input);
                                throw eee;
                        }
                        cnt8++;
                    } while (true);

                    match('.'); 
                    // /home/jens/development/take/workspace2/take/grammar/Take.g:544:25: ( '0' .. '9' )*
                    loop9:
                    do {
                        int alt9=2;
                        int LA9_0 = input.LA(1);

                        if ( ((LA9_0>='0' && LA9_0<='9')) ) {
                            alt9=1;
                        }


                        switch (alt9) {
                    	case 1 :
                    	    // /home/jens/development/take/workspace2/take/grammar/Take.g:544:26: '0' .. '9'
                    	    {
                    	    matchRange('0','9'); 

                    	    }
                    	    break;

                    	default :
                    	    break loop9;
                        }
                    } while (true);

                    // /home/jens/development/take/workspace2/take/grammar/Take.g:544:37: ( Exponent )?
                    int alt10=2;
                    int LA10_0 = input.LA(1);

                    if ( (LA10_0=='E'||LA10_0=='e') ) {
                        alt10=1;
                    }
                    switch (alt10) {
                        case 1 :
                            // /home/jens/development/take/workspace2/take/grammar/Take.g:544:37: Exponent
                            {
                            mExponent(); 

                            }
                            break;

                    }

                    // /home/jens/development/take/workspace2/take/grammar/Take.g:544:47: ( FloatTypeSuffix )?
                    int alt11=2;
                    int LA11_0 = input.LA(1);

                    if ( (LA11_0=='D'||LA11_0=='F'||LA11_0=='d'||LA11_0=='f') ) {
                        alt11=1;
                    }
                    switch (alt11) {
                        case 1 :
                            // /home/jens/development/take/workspace2/take/grammar/Take.g:544:47: FloatTypeSuffix
                            {
                            mFloatTypeSuffix(); 

                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // /home/jens/development/take/workspace2/take/grammar/Take.g:545:9: '.' ( '0' .. '9' )+ ( Exponent )? ( FloatTypeSuffix )?
                    {
                    match('.'); 
                    // /home/jens/development/take/workspace2/take/grammar/Take.g:545:13: ( '0' .. '9' )+
                    int cnt12=0;
                    loop12:
                    do {
                        int alt12=2;
                        int LA12_0 = input.LA(1);

                        if ( ((LA12_0>='0' && LA12_0<='9')) ) {
                            alt12=1;
                        }


                        switch (alt12) {
                    	case 1 :
                    	    // /home/jens/development/take/workspace2/take/grammar/Take.g:545:14: '0' .. '9'
                    	    {
                    	    matchRange('0','9'); 

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt12 >= 1 ) break loop12;
                                EarlyExitException eee =
                                    new EarlyExitException(12, input);
                                throw eee;
                        }
                        cnt12++;
                    } while (true);

                    // /home/jens/development/take/workspace2/take/grammar/Take.g:545:25: ( Exponent )?
                    int alt13=2;
                    int LA13_0 = input.LA(1);

                    if ( (LA13_0=='E'||LA13_0=='e') ) {
                        alt13=1;
                    }
                    switch (alt13) {
                        case 1 :
                            // /home/jens/development/take/workspace2/take/grammar/Take.g:545:25: Exponent
                            {
                            mExponent(); 

                            }
                            break;

                    }

                    // /home/jens/development/take/workspace2/take/grammar/Take.g:545:35: ( FloatTypeSuffix )?
                    int alt14=2;
                    int LA14_0 = input.LA(1);

                    if ( (LA14_0=='D'||LA14_0=='F'||LA14_0=='d'||LA14_0=='f') ) {
                        alt14=1;
                    }
                    switch (alt14) {
                        case 1 :
                            // /home/jens/development/take/workspace2/take/grammar/Take.g:545:35: FloatTypeSuffix
                            {
                            mFloatTypeSuffix(); 

                            }
                            break;

                    }


                    }
                    break;
                case 3 :
                    // /home/jens/development/take/workspace2/take/grammar/Take.g:546:9: ( '0' .. '9' )+ Exponent ( FloatTypeSuffix )?
                    {
                    // /home/jens/development/take/workspace2/take/grammar/Take.g:546:9: ( '0' .. '9' )+
                    int cnt15=0;
                    loop15:
                    do {
                        int alt15=2;
                        int LA15_0 = input.LA(1);

                        if ( ((LA15_0>='0' && LA15_0<='9')) ) {
                            alt15=1;
                        }


                        switch (alt15) {
                    	case 1 :
                    	    // /home/jens/development/take/workspace2/take/grammar/Take.g:546:10: '0' .. '9'
                    	    {
                    	    matchRange('0','9'); 

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt15 >= 1 ) break loop15;
                                EarlyExitException eee =
                                    new EarlyExitException(15, input);
                                throw eee;
                        }
                        cnt15++;
                    } while (true);

                    mExponent(); 
                    // /home/jens/development/take/workspace2/take/grammar/Take.g:546:30: ( FloatTypeSuffix )?
                    int alt16=2;
                    int LA16_0 = input.LA(1);

                    if ( (LA16_0=='D'||LA16_0=='F'||LA16_0=='d'||LA16_0=='f') ) {
                        alt16=1;
                    }
                    switch (alt16) {
                        case 1 :
                            // /home/jens/development/take/workspace2/take/grammar/Take.g:546:30: FloatTypeSuffix
                            {
                            mFloatTypeSuffix(); 

                            }
                            break;

                    }


                    }
                    break;
                case 4 :
                    // /home/jens/development/take/workspace2/take/grammar/Take.g:547:9: ( '0' .. '9' )+ FloatTypeSuffix
                    {
                    // /home/jens/development/take/workspace2/take/grammar/Take.g:547:9: ( '0' .. '9' )+
                    int cnt17=0;
                    loop17:
                    do {
                        int alt17=2;
                        int LA17_0 = input.LA(1);

                        if ( ((LA17_0>='0' && LA17_0<='9')) ) {
                            alt17=1;
                        }


                        switch (alt17) {
                    	case 1 :
                    	    // /home/jens/development/take/workspace2/take/grammar/Take.g:547:10: '0' .. '9'
                    	    {
                    	    matchRange('0','9'); 

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt17 >= 1 ) break loop17;
                                EarlyExitException eee =
                                    new EarlyExitException(17, input);
                                throw eee;
                        }
                        cnt17++;
                    } while (true);

                    mFloatTypeSuffix(); 

                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "FloatingPointLiteral"

    // $ANTLR start "Exponent"
    public final void mExponent() throws RecognitionException {
        try {
            // /home/jens/development/take/workspace2/take/grammar/Take.g:551:10: ( ( 'e' | 'E' ) ( '+' | '-' )? ( '0' .. '9' )+ )
            // /home/jens/development/take/workspace2/take/grammar/Take.g:551:12: ( 'e' | 'E' ) ( '+' | '-' )? ( '0' .. '9' )+
            {
            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // /home/jens/development/take/workspace2/take/grammar/Take.g:551:22: ( '+' | '-' )?
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0=='+'||LA19_0=='-') ) {
                alt19=1;
            }
            switch (alt19) {
                case 1 :
                    // /home/jens/development/take/workspace2/take/grammar/Take.g:
                    {
                    if ( input.LA(1)=='+'||input.LA(1)=='-' ) {
                        input.consume();

                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;}


                    }
                    break;

            }

            // /home/jens/development/take/workspace2/take/grammar/Take.g:551:33: ( '0' .. '9' )+
            int cnt20=0;
            loop20:
            do {
                int alt20=2;
                int LA20_0 = input.LA(1);

                if ( ((LA20_0>='0' && LA20_0<='9')) ) {
                    alt20=1;
                }


                switch (alt20) {
            	case 1 :
            	    // /home/jens/development/take/workspace2/take/grammar/Take.g:551:34: '0' .. '9'
            	    {
            	    matchRange('0','9'); 

            	    }
            	    break;

            	default :
            	    if ( cnt20 >= 1 ) break loop20;
                        EarlyExitException eee =
                            new EarlyExitException(20, input);
                        throw eee;
                }
                cnt20++;
            } while (true);


            }

        }
        finally {
        }
    }
    // $ANTLR end "Exponent"

    // $ANTLR start "FloatTypeSuffix"
    public final void mFloatTypeSuffix() throws RecognitionException {
        try {
            // /home/jens/development/take/workspace2/take/grammar/Take.g:554:17: ( ( 'f' | 'F' | 'd' | 'D' ) )
            // /home/jens/development/take/workspace2/take/grammar/Take.g:554:19: ( 'f' | 'F' | 'd' | 'D' )
            {
            if ( input.LA(1)=='D'||input.LA(1)=='F'||input.LA(1)=='d'||input.LA(1)=='f' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "FloatTypeSuffix"

    // $ANTLR start "StringLiteral"
    public final void mStringLiteral() throws RecognitionException {
        try {
            int _type = StringLiteral;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jens/development/take/workspace2/take/grammar/Take.g:557:5: ( '\"' ( EscapeSequence | ~ ( '\\\\' | '\"' ) )* '\"' )
            // /home/jens/development/take/workspace2/take/grammar/Take.g:557:8: '\"' ( EscapeSequence | ~ ( '\\\\' | '\"' ) )* '\"'
            {
            match('\"'); 
            // /home/jens/development/take/workspace2/take/grammar/Take.g:557:12: ( EscapeSequence | ~ ( '\\\\' | '\"' ) )*
            loop21:
            do {
                int alt21=3;
                int LA21_0 = input.LA(1);

                if ( (LA21_0=='\\') ) {
                    alt21=1;
                }
                else if ( ((LA21_0>='\u0000' && LA21_0<='!')||(LA21_0>='#' && LA21_0<='[')||(LA21_0>=']' && LA21_0<='\uFFFF')) ) {
                    alt21=2;
                }


                switch (alt21) {
            	case 1 :
            	    // /home/jens/development/take/workspace2/take/grammar/Take.g:557:14: EscapeSequence
            	    {
            	    mEscapeSequence(); 

            	    }
            	    break;
            	case 2 :
            	    // /home/jens/development/take/workspace2/take/grammar/Take.g:557:31: ~ ( '\\\\' | '\"' )
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='!')||(input.LA(1)>='#' && input.LA(1)<='[')||(input.LA(1)>=']' && input.LA(1)<='\uFFFF') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop21;
                }
            } while (true);

            match('\"'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "StringLiteral"

    // $ANTLR start "EscapeSequence"
    public final void mEscapeSequence() throws RecognitionException {
        try {
            // /home/jens/development/take/workspace2/take/grammar/Take.g:562:5: ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\\\"' | '\\'' | '\\\\' | '=' ) | UnicodeEscape | OctalEscape )
            int alt22=3;
            int LA22_0 = input.LA(1);

            if ( (LA22_0=='\\') ) {
                switch ( input.LA(2) ) {
                case '\"':
                case '\'':
                case '=':
                case '\\':
                case 'b':
                case 'f':
                case 'n':
                case 'r':
                case 't':
                    {
                    alt22=1;
                    }
                    break;
                case 'u':
                    {
                    alt22=2;
                    }
                    break;
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                    {
                    alt22=3;
                    }
                    break;
                default:
                    NoViableAltException nvae =
                        new NoViableAltException("", 22, 1, input);

                    throw nvae;
                }

            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 22, 0, input);

                throw nvae;
            }
            switch (alt22) {
                case 1 :
                    // /home/jens/development/take/workspace2/take/grammar/Take.g:562:9: '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\\\"' | '\\'' | '\\\\' | '=' )
                    {
                    match('\\'); 
                    if ( input.LA(1)=='\"'||input.LA(1)=='\''||input.LA(1)=='='||input.LA(1)=='\\'||input.LA(1)=='b'||input.LA(1)=='f'||input.LA(1)=='n'||input.LA(1)=='r'||input.LA(1)=='t' ) {
                        input.consume();

                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;}


                    }
                    break;
                case 2 :
                    // /home/jens/development/take/workspace2/take/grammar/Take.g:563:9: UnicodeEscape
                    {
                    mUnicodeEscape(); 

                    }
                    break;
                case 3 :
                    // /home/jens/development/take/workspace2/take/grammar/Take.g:564:9: OctalEscape
                    {
                    mOctalEscape(); 

                    }
                    break;

            }
        }
        finally {
        }
    }
    // $ANTLR end "EscapeSequence"

    // $ANTLR start "OctalEscape"
    public final void mOctalEscape() throws RecognitionException {
        try {
            // /home/jens/development/take/workspace2/take/grammar/Take.g:569:5: ( '\\\\' ( '0' .. '3' ) ( '0' .. '7' ) ( '0' .. '7' ) | '\\\\' ( '0' .. '7' ) ( '0' .. '7' ) | '\\\\' ( '0' .. '7' ) )
            int alt23=3;
            int LA23_0 = input.LA(1);

            if ( (LA23_0=='\\') ) {
                int LA23_1 = input.LA(2);

                if ( ((LA23_1>='0' && LA23_1<='3')) ) {
                    int LA23_2 = input.LA(3);

                    if ( ((LA23_2>='0' && LA23_2<='7')) ) {
                        int LA23_5 = input.LA(4);

                        if ( ((LA23_5>='0' && LA23_5<='7')) ) {
                            alt23=1;
                        }
                        else {
                            alt23=2;}
                    }
                    else {
                        alt23=3;}
                }
                else if ( ((LA23_1>='4' && LA23_1<='7')) ) {
                    int LA23_3 = input.LA(3);

                    if ( ((LA23_3>='0' && LA23_3<='7')) ) {
                        alt23=2;
                    }
                    else {
                        alt23=3;}
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 23, 1, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 23, 0, input);

                throw nvae;
            }
            switch (alt23) {
                case 1 :
                    // /home/jens/development/take/workspace2/take/grammar/Take.g:569:9: '\\\\' ( '0' .. '3' ) ( '0' .. '7' ) ( '0' .. '7' )
                    {
                    match('\\'); 
                    // /home/jens/development/take/workspace2/take/grammar/Take.g:569:14: ( '0' .. '3' )
                    // /home/jens/development/take/workspace2/take/grammar/Take.g:569:15: '0' .. '3'
                    {
                    matchRange('0','3'); 

                    }

                    // /home/jens/development/take/workspace2/take/grammar/Take.g:569:25: ( '0' .. '7' )
                    // /home/jens/development/take/workspace2/take/grammar/Take.g:569:26: '0' .. '7'
                    {
                    matchRange('0','7'); 

                    }

                    // /home/jens/development/take/workspace2/take/grammar/Take.g:569:36: ( '0' .. '7' )
                    // /home/jens/development/take/workspace2/take/grammar/Take.g:569:37: '0' .. '7'
                    {
                    matchRange('0','7'); 

                    }


                    }
                    break;
                case 2 :
                    // /home/jens/development/take/workspace2/take/grammar/Take.g:570:9: '\\\\' ( '0' .. '7' ) ( '0' .. '7' )
                    {
                    match('\\'); 
                    // /home/jens/development/take/workspace2/take/grammar/Take.g:570:14: ( '0' .. '7' )
                    // /home/jens/development/take/workspace2/take/grammar/Take.g:570:15: '0' .. '7'
                    {
                    matchRange('0','7'); 

                    }

                    // /home/jens/development/take/workspace2/take/grammar/Take.g:570:25: ( '0' .. '7' )
                    // /home/jens/development/take/workspace2/take/grammar/Take.g:570:26: '0' .. '7'
                    {
                    matchRange('0','7'); 

                    }


                    }
                    break;
                case 3 :
                    // /home/jens/development/take/workspace2/take/grammar/Take.g:571:9: '\\\\' ( '0' .. '7' )
                    {
                    match('\\'); 
                    // /home/jens/development/take/workspace2/take/grammar/Take.g:571:14: ( '0' .. '7' )
                    // /home/jens/development/take/workspace2/take/grammar/Take.g:571:15: '0' .. '7'
                    {
                    matchRange('0','7'); 

                    }


                    }
                    break;

            }
        }
        finally {
        }
    }
    // $ANTLR end "OctalEscape"

    // $ANTLR start "UnicodeEscape"
    public final void mUnicodeEscape() throws RecognitionException {
        try {
            // /home/jens/development/take/workspace2/take/grammar/Take.g:576:5: ( '\\\\' 'u' HexDigit HexDigit HexDigit HexDigit )
            // /home/jens/development/take/workspace2/take/grammar/Take.g:576:9: '\\\\' 'u' HexDigit HexDigit HexDigit HexDigit
            {
            match('\\'); 
            match('u'); 
            mHexDigit(); 
            mHexDigit(); 
            mHexDigit(); 
            mHexDigit(); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "UnicodeEscape"

    // $ANTLR start "GlobalAnnotation"
    public final void mGlobalAnnotation() throws RecognitionException {
        try {
            int _type = GlobalAnnotation;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            Token key=null;
            Token value=null;

            // /home/jens/development/take/workspace2/take/grammar/Take.g:580:5: ( '@@' key= AnnotationIdentifier '=' value= AnnotationValue )
            // /home/jens/development/take/workspace2/take/grammar/Take.g:580:9: '@@' key= AnnotationIdentifier '=' value= AnnotationValue
            {
            match("@@"); 

            int keyStart734 = getCharIndex();
            mAnnotationIdentifier(); 
            key = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, keyStart734, getCharIndex()-1);
            match('='); 
            int valueStart740 = getCharIndex();
            mAnnotationValue(); 
            value = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, valueStart740, getCharIndex()-1);

                        emit(new CommonToken(GlobalAnnotationKey, (key!=null?key.getText():null)));
                        emit(new CommonToken(AnnotationValue, lastAnnotationValue));
                    

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "GlobalAnnotation"

    // $ANTLR start "LocalAnnotation"
    public final void mLocalAnnotation() throws RecognitionException {
        try {
            int _type = LocalAnnotation;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            Token key=null;
            Token value=null;

            // /home/jens/development/take/workspace2/take/grammar/Take.g:588:5: ( '@' key= AnnotationIdentifier '=' value= AnnotationValue )
            // /home/jens/development/take/workspace2/take/grammar/Take.g:588:9: '@' key= AnnotationIdentifier '=' value= AnnotationValue
            {
            match('@'); 
            int keyStart774 = getCharIndex();
            mAnnotationIdentifier(); 
            key = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, keyStart774, getCharIndex()-1);
            match('='); 
            int valueStart780 = getCharIndex();
            mAnnotationValue(); 
            value = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, valueStart780, getCharIndex()-1);

                        emit(new CommonToken(LocalAnnotationKey, (key!=null?key.getText():null)));
                        emit(new CommonToken(AnnotationValue, lastAnnotationValue));
                    

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LocalAnnotation"

    // $ANTLR start "AnnotationValue"
    public final void mAnnotationValue() throws RecognitionException {
        try {
            Token Whitespace1=null;
            Token Newline2=null;

            // /home/jens/development/take/workspace2/take/grammar/Take.g:597:5: ( ( options {greedy=false; } : . )+ Whitespace Newline )
            // /home/jens/development/take/workspace2/take/grammar/Take.g:597:9: ( options {greedy=false; } : . )+ Whitespace Newline
            {
             int startIndex = getCharIndex(); 
            // /home/jens/development/take/workspace2/take/grammar/Take.g:598:9: ( options {greedy=false; } : . )+
            int cnt24=0;
            loop24:
            do {
                int alt24=2;
                int LA24_0 = input.LA(1);

                if ( (LA24_0=='\t'||LA24_0==' ') ) {
                    alt24=2;
                }
                else if ( ((LA24_0>='\u0000' && LA24_0<='\b')||(LA24_0>='\n' && LA24_0<='\u001F')||(LA24_0>='!' && LA24_0<='\uFFFF')) ) {
                    alt24=1;
                }


                switch (alt24) {
            	case 1 :
            	    // /home/jens/development/take/workspace2/take/grammar/Take.g:598:39: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    if ( cnt24 >= 1 ) break loop24;
                        EarlyExitException eee =
                            new EarlyExitException(24, input);
                        throw eee;
                }
                cnt24++;
            } while (true);

            int Whitespace1Start840 = getCharIndex();
            mWhitespace(); 
            Whitespace1 = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, Whitespace1Start840, getCharIndex()-1);
            int Newline2Start842 = getCharIndex();
            mNewline(); 
            Newline2 = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, Newline2Start842, getCharIndex()-1);

                        int endIndex = getCharIndex() - 1;
                        int whitespace = (Whitespace1!=null?Whitespace1.getText():null).length() + (Newline2!=null?Newline2.getText():null).length();
                        
                        // Workaround untill getText(), and setText() work as exprected within lexer fragments
                        lastAnnotationValue = input.substring(startIndex, endIndex - whitespace);
                    

            }

        }
        finally {
        }
    }
    // $ANTLR end "AnnotationValue"

    // $ANTLR start "Expression"
    public final void mExpression() throws RecognitionException {
        try {
            int _type = Expression;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jens/development/take/workspace2/take/grammar/Take.g:609:5: ( '\\'' ( EscapeSequence | ~ ( '\\\\' | '\\'' ) )+ '\\'' )
            // /home/jens/development/take/workspace2/take/grammar/Take.g:609:9: '\\'' ( EscapeSequence | ~ ( '\\\\' | '\\'' ) )+ '\\''
            {
            match('\''); 
            // /home/jens/development/take/workspace2/take/grammar/Take.g:609:14: ( EscapeSequence | ~ ( '\\\\' | '\\'' ) )+
            int cnt25=0;
            loop25:
            do {
                int alt25=3;
                int LA25_0 = input.LA(1);

                if ( (LA25_0=='\\') ) {
                    alt25=1;
                }
                else if ( ((LA25_0>='\u0000' && LA25_0<='&')||(LA25_0>='(' && LA25_0<='[')||(LA25_0>=']' && LA25_0<='\uFFFF')) ) {
                    alt25=2;
                }


                switch (alt25) {
            	case 1 :
            	    // /home/jens/development/take/workspace2/take/grammar/Take.g:609:16: EscapeSequence
            	    {
            	    mEscapeSequence(); 

            	    }
            	    break;
            	case 2 :
            	    // /home/jens/development/take/workspace2/take/grammar/Take.g:609:33: ~ ( '\\\\' | '\\'' )
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='&')||(input.LA(1)>='(' && input.LA(1)<='[')||(input.LA(1)>=']' && input.LA(1)<='\uFFFF') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    if ( cnt25 >= 1 ) break loop25;
                        EarlyExitException eee =
                            new EarlyExitException(25, input);
                        throw eee;
                }
                cnt25++;
            } while (true);

            match('\''); 

                        String expression = getText().substring(1, getText().length() - 1); // strip braces
                        setText(expression.replaceAll("\\'", "'"));
                    

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "Expression"

    // $ANTLR start "Identifier"
    public final void mIdentifier() throws RecognitionException {
        try {
            int _type = Identifier;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jens/development/take/workspace2/take/grammar/Take.g:617:5: ( Letter ( Letter | IDDigit )* )
            // /home/jens/development/take/workspace2/take/grammar/Take.g:617:9: Letter ( Letter | IDDigit )*
            {
            mLetter(); 
            // /home/jens/development/take/workspace2/take/grammar/Take.g:617:16: ( Letter | IDDigit )*
            loop26:
            do {
                int alt26=2;
                int LA26_0 = input.LA(1);

                if ( ((LA26_0>='0' && LA26_0<='9')||(LA26_0>='A' && LA26_0<='Z')||LA26_0=='_'||(LA26_0>='a' && LA26_0<='z')) ) {
                    alt26=1;
                }


                switch (alt26) {
            	case 1 :
            	    // /home/jens/development/take/workspace2/take/grammar/Take.g:
            	    {
            	    if ( (input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop26;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "Identifier"

    // $ANTLR start "AnnotationIdentifier"
    public final void mAnnotationIdentifier() throws RecognitionException {
        try {
            int _type = AnnotationIdentifier;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jens/development/take/workspace2/take/grammar/Take.g:621:5: ( Letter ( Letter | IDDigit | '.' )* )
            // /home/jens/development/take/workspace2/take/grammar/Take.g:621:9: Letter ( Letter | IDDigit | '.' )*
            {
            mLetter(); 
            // /home/jens/development/take/workspace2/take/grammar/Take.g:621:16: ( Letter | IDDigit | '.' )*
            loop27:
            do {
                int alt27=2;
                int LA27_0 = input.LA(1);

                if ( (LA27_0=='.'||(LA27_0>='0' && LA27_0<='9')||(LA27_0>='A' && LA27_0<='Z')||LA27_0=='_'||(LA27_0>='a' && LA27_0<='z')) ) {
                    alt27=1;
                }


                switch (alt27) {
            	case 1 :
            	    // /home/jens/development/take/workspace2/take/grammar/Take.g:
            	    {
            	    if ( input.LA(1)=='.'||(input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop27;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "AnnotationIdentifier"

    // $ANTLR start "Letter"
    public final void mLetter() throws RecognitionException {
        try {
            // /home/jens/development/take/workspace2/take/grammar/Take.g:626:5: ( 'a' .. 'z' | 'A' .. 'Z' | '_' )
            // /home/jens/development/take/workspace2/take/grammar/Take.g:
            {
            if ( (input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "Letter"

    // $ANTLR start "IDDigit"
    public final void mIDDigit() throws RecognitionException {
        try {
            // /home/jens/development/take/workspace2/take/grammar/Take.g:633:5: ( '0' .. '9' )
            // /home/jens/development/take/workspace2/take/grammar/Take.g:633:9: '0' .. '9'
            {
            matchRange('0','9'); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "IDDigit"

    // $ANTLR start "Newline"
    public final void mNewline() throws RecognitionException {
        try {
            int _type = Newline;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jens/development/take/workspace2/take/grammar/Take.g:637:5: ( ( '\\r' )? '\\n' )
            // /home/jens/development/take/workspace2/take/grammar/Take.g:637:9: ( '\\r' )? '\\n'
            {
            // /home/jens/development/take/workspace2/take/grammar/Take.g:637:9: ( '\\r' )?
            int alt28=2;
            int LA28_0 = input.LA(1);

            if ( (LA28_0=='\r') ) {
                alt28=1;
            }
            switch (alt28) {
                case 1 :
                    // /home/jens/development/take/workspace2/take/grammar/Take.g:637:9: '\\r'
                    {
                    match('\r'); 

                    }
                    break;

            }

            match('\n'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "Newline"

    // $ANTLR start "Whitespace"
    public final void mWhitespace() throws RecognitionException {
        try {
            int _type = Whitespace;
            int _channel = DEFAULT_TOKEN_CHANNEL;
             _channel=HIDDEN; 
            // /home/jens/development/take/workspace2/take/grammar/Take.g:642:5: ( ( ' ' | '\\t' )+ )
            // /home/jens/development/take/workspace2/take/grammar/Take.g:642:9: ( ' ' | '\\t' )+
            {
            // /home/jens/development/take/workspace2/take/grammar/Take.g:642:9: ( ' ' | '\\t' )+
            int cnt29=0;
            loop29:
            do {
                int alt29=2;
                int LA29_0 = input.LA(1);

                if ( (LA29_0=='\t'||LA29_0==' ') ) {
                    alt29=1;
                }


                switch (alt29) {
            	case 1 :
            	    // /home/jens/development/take/workspace2/take/grammar/Take.g:
            	    {
            	    if ( input.LA(1)=='\t'||input.LA(1)==' ' ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    if ( cnt29 >= 1 ) break loop29;
                        EarlyExitException eee =
                            new EarlyExitException(29, input);
                        throw eee;
                }
                cnt29++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "Whitespace"

    // $ANTLR start "BlockComment"
    public final void mBlockComment() throws RecognitionException {
        try {
            int _type = BlockComment;
            int _channel = DEFAULT_TOKEN_CHANNEL;
             _channel=HIDDEN; 
            // /home/jens/development/take/workspace2/take/grammar/Take.g:647:5: ( '/*' ( options {greedy=false; } : . )* '*/' )
            // /home/jens/development/take/workspace2/take/grammar/Take.g:647:9: '/*' ( options {greedy=false; } : . )* '*/'
            {
            match("/*"); 

            // /home/jens/development/take/workspace2/take/grammar/Take.g:647:14: ( options {greedy=false; } : . )*
            loop30:
            do {
                int alt30=2;
                int LA30_0 = input.LA(1);

                if ( (LA30_0=='*') ) {
                    int LA30_1 = input.LA(2);

                    if ( (LA30_1=='/') ) {
                        alt30=2;
                    }
                    else if ( ((LA30_1>='\u0000' && LA30_1<='.')||(LA30_1>='0' && LA30_1<='\uFFFF')) ) {
                        alt30=1;
                    }


                }
                else if ( ((LA30_0>='\u0000' && LA30_0<=')')||(LA30_0>='+' && LA30_0<='\uFFFF')) ) {
                    alt30=1;
                }


                switch (alt30) {
            	case 1 :
            	    // /home/jens/development/take/workspace2/take/grammar/Take.g:647:44: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop30;
                }
            } while (true);

            match("*/"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "BlockComment"

    // $ANTLR start "LineComment"
    public final void mLineComment() throws RecognitionException {
        try {
            int _type = LineComment;
            int _channel = DEFAULT_TOKEN_CHANNEL;
             _channel=HIDDEN; 
            // /home/jens/development/take/workspace2/take/grammar/Take.g:652:5: ( '//' ( options {greedy=false; } : . )* Newline )
            // /home/jens/development/take/workspace2/take/grammar/Take.g:652:9: '//' ( options {greedy=false; } : . )* Newline
            {
            match("//"); 

            // /home/jens/development/take/workspace2/take/grammar/Take.g:652:14: ( options {greedy=false; } : . )*
            loop31:
            do {
                int alt31=2;
                int LA31_0 = input.LA(1);

                if ( (LA31_0=='\r') ) {
                    alt31=2;
                }
                else if ( (LA31_0=='\n') ) {
                    alt31=2;
                }
                else if ( ((LA31_0>='\u0000' && LA31_0<='\t')||(LA31_0>='\u000B' && LA31_0<='\f')||(LA31_0>='\u000E' && LA31_0<='\uFFFF')) ) {
                    alt31=1;
                }


                switch (alt31) {
            	case 1 :
            	    // /home/jens/development/take/workspace2/take/grammar/Take.g:652:44: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop31;
                }
            } while (true);

            mNewline(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LineComment"

    public void mTokens() throws RecognitionException {
        // /home/jens/development/take/workspace2/take/grammar/Take.g:1:8: ( T__30 | T__31 | T__32 | T__33 | T__34 | T__35 | T__36 | T__37 | T__38 | T__39 | T__40 | T__41 | T__42 | T__43 | T__44 | T__45 | T__46 | T__47 | T__48 | T__49 | T__50 | T__51 | T__52 | T__53 | T__54 | T__55 | T__56 | T__57 | T__58 | HexLiteral | DecimalLiteral | OctalLiteral | FloatingPointLiteral | StringLiteral | GlobalAnnotation | LocalAnnotation | Expression | Identifier | AnnotationIdentifier | Newline | Whitespace | BlockComment | LineComment )
        int alt32=43;
        alt32 = dfa32.predict(input);
        switch (alt32) {
            case 1 :
                // /home/jens/development/take/workspace2/take/grammar/Take.g:1:10: T__30
                {
                mT__30(); 

                }
                break;
            case 2 :
                // /home/jens/development/take/workspace2/take/grammar/Take.g:1:16: T__31
                {
                mT__31(); 

                }
                break;
            case 3 :
                // /home/jens/development/take/workspace2/take/grammar/Take.g:1:22: T__32
                {
                mT__32(); 

                }
                break;
            case 4 :
                // /home/jens/development/take/workspace2/take/grammar/Take.g:1:28: T__33
                {
                mT__33(); 

                }
                break;
            case 5 :
                // /home/jens/development/take/workspace2/take/grammar/Take.g:1:34: T__34
                {
                mT__34(); 

                }
                break;
            case 6 :
                // /home/jens/development/take/workspace2/take/grammar/Take.g:1:40: T__35
                {
                mT__35(); 

                }
                break;
            case 7 :
                // /home/jens/development/take/workspace2/take/grammar/Take.g:1:46: T__36
                {
                mT__36(); 

                }
                break;
            case 8 :
                // /home/jens/development/take/workspace2/take/grammar/Take.g:1:52: T__37
                {
                mT__37(); 

                }
                break;
            case 9 :
                // /home/jens/development/take/workspace2/take/grammar/Take.g:1:58: T__38
                {
                mT__38(); 

                }
                break;
            case 10 :
                // /home/jens/development/take/workspace2/take/grammar/Take.g:1:64: T__39
                {
                mT__39(); 

                }
                break;
            case 11 :
                // /home/jens/development/take/workspace2/take/grammar/Take.g:1:70: T__40
                {
                mT__40(); 

                }
                break;
            case 12 :
                // /home/jens/development/take/workspace2/take/grammar/Take.g:1:76: T__41
                {
                mT__41(); 

                }
                break;
            case 13 :
                // /home/jens/development/take/workspace2/take/grammar/Take.g:1:82: T__42
                {
                mT__42(); 

                }
                break;
            case 14 :
                // /home/jens/development/take/workspace2/take/grammar/Take.g:1:88: T__43
                {
                mT__43(); 

                }
                break;
            case 15 :
                // /home/jens/development/take/workspace2/take/grammar/Take.g:1:94: T__44
                {
                mT__44(); 

                }
                break;
            case 16 :
                // /home/jens/development/take/workspace2/take/grammar/Take.g:1:100: T__45
                {
                mT__45(); 

                }
                break;
            case 17 :
                // /home/jens/development/take/workspace2/take/grammar/Take.g:1:106: T__46
                {
                mT__46(); 

                }
                break;
            case 18 :
                // /home/jens/development/take/workspace2/take/grammar/Take.g:1:112: T__47
                {
                mT__47(); 

                }
                break;
            case 19 :
                // /home/jens/development/take/workspace2/take/grammar/Take.g:1:118: T__48
                {
                mT__48(); 

                }
                break;
            case 20 :
                // /home/jens/development/take/workspace2/take/grammar/Take.g:1:124: T__49
                {
                mT__49(); 

                }
                break;
            case 21 :
                // /home/jens/development/take/workspace2/take/grammar/Take.g:1:130: T__50
                {
                mT__50(); 

                }
                break;
            case 22 :
                // /home/jens/development/take/workspace2/take/grammar/Take.g:1:136: T__51
                {
                mT__51(); 

                }
                break;
            case 23 :
                // /home/jens/development/take/workspace2/take/grammar/Take.g:1:142: T__52
                {
                mT__52(); 

                }
                break;
            case 24 :
                // /home/jens/development/take/workspace2/take/grammar/Take.g:1:148: T__53
                {
                mT__53(); 

                }
                break;
            case 25 :
                // /home/jens/development/take/workspace2/take/grammar/Take.g:1:154: T__54
                {
                mT__54(); 

                }
                break;
            case 26 :
                // /home/jens/development/take/workspace2/take/grammar/Take.g:1:160: T__55
                {
                mT__55(); 

                }
                break;
            case 27 :
                // /home/jens/development/take/workspace2/take/grammar/Take.g:1:166: T__56
                {
                mT__56(); 

                }
                break;
            case 28 :
                // /home/jens/development/take/workspace2/take/grammar/Take.g:1:172: T__57
                {
                mT__57(); 

                }
                break;
            case 29 :
                // /home/jens/development/take/workspace2/take/grammar/Take.g:1:178: T__58
                {
                mT__58(); 

                }
                break;
            case 30 :
                // /home/jens/development/take/workspace2/take/grammar/Take.g:1:184: HexLiteral
                {
                mHexLiteral(); 

                }
                break;
            case 31 :
                // /home/jens/development/take/workspace2/take/grammar/Take.g:1:195: DecimalLiteral
                {
                mDecimalLiteral(); 

                }
                break;
            case 32 :
                // /home/jens/development/take/workspace2/take/grammar/Take.g:1:210: OctalLiteral
                {
                mOctalLiteral(); 

                }
                break;
            case 33 :
                // /home/jens/development/take/workspace2/take/grammar/Take.g:1:223: FloatingPointLiteral
                {
                mFloatingPointLiteral(); 

                }
                break;
            case 34 :
                // /home/jens/development/take/workspace2/take/grammar/Take.g:1:244: StringLiteral
                {
                mStringLiteral(); 

                }
                break;
            case 35 :
                // /home/jens/development/take/workspace2/take/grammar/Take.g:1:258: GlobalAnnotation
                {
                mGlobalAnnotation(); 

                }
                break;
            case 36 :
                // /home/jens/development/take/workspace2/take/grammar/Take.g:1:275: LocalAnnotation
                {
                mLocalAnnotation(); 

                }
                break;
            case 37 :
                // /home/jens/development/take/workspace2/take/grammar/Take.g:1:291: Expression
                {
                mExpression(); 

                }
                break;
            case 38 :
                // /home/jens/development/take/workspace2/take/grammar/Take.g:1:302: Identifier
                {
                mIdentifier(); 

                }
                break;
            case 39 :
                // /home/jens/development/take/workspace2/take/grammar/Take.g:1:313: AnnotationIdentifier
                {
                mAnnotationIdentifier(); 

                }
                break;
            case 40 :
                // /home/jens/development/take/workspace2/take/grammar/Take.g:1:334: Newline
                {
                mNewline(); 

                }
                break;
            case 41 :
                // /home/jens/development/take/workspace2/take/grammar/Take.g:1:342: Whitespace
                {
                mWhitespace(); 

                }
                break;
            case 42 :
                // /home/jens/development/take/workspace2/take/grammar/Take.g:1:353: BlockComment
                {
                mBlockComment(); 

                }
                break;
            case 43 :
                // /home/jens/development/take/workspace2/take/grammar/Take.g:1:366: LineComment
                {
                mLineComment(); 

                }
                break;

        }

    }


    protected DFA18 dfa18 = new DFA18(this);
    protected DFA32 dfa32 = new DFA32(this);
    static final String DFA18_eotS =
        "\6\uffff";
    static final String DFA18_eofS =
        "\6\uffff";
    static final String DFA18_minS =
        "\2\56\4\uffff";
    static final String DFA18_maxS =
        "\1\71\1\146\4\uffff";
    static final String DFA18_acceptS =
        "\2\uffff\1\2\1\3\1\4\1\1";
    static final String DFA18_specialS =
        "\6\uffff}>";
    static final String[] DFA18_transitionS = {
            "\1\2\1\uffff\12\1",
            "\1\5\1\uffff\12\1\12\uffff\1\4\1\3\1\4\35\uffff\1\4\1\3\1\4",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA18_eot = DFA.unpackEncodedString(DFA18_eotS);
    static final short[] DFA18_eof = DFA.unpackEncodedString(DFA18_eofS);
    static final char[] DFA18_min = DFA.unpackEncodedStringToUnsignedChars(DFA18_minS);
    static final char[] DFA18_max = DFA.unpackEncodedStringToUnsignedChars(DFA18_maxS);
    static final short[] DFA18_accept = DFA.unpackEncodedString(DFA18_acceptS);
    static final short[] DFA18_special = DFA.unpackEncodedString(DFA18_specialS);
    static final short[][] DFA18_transition;

    static {
        int numStates = DFA18_transitionS.length;
        DFA18_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA18_transition[i] = DFA.unpackEncodedString(DFA18_transitionS[i]);
        }
    }

    class DFA18 extends DFA {

        public DFA18(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 18;
            this.eot = DFA18_eot;
            this.eof = DFA18_eof;
            this.min = DFA18_min;
            this.max = DFA18_max;
            this.accept = DFA18_accept;
            this.special = DFA18_special;
            this.transition = DFA18_transition;
        }
        public String getDescription() {
            return "543:1: FloatingPointLiteral : ( ( '0' .. '9' )+ '.' ( '0' .. '9' )* ( Exponent )? ( FloatTypeSuffix )? | '.' ( '0' .. '9' )+ ( Exponent )? ( FloatTypeSuffix )? | ( '0' .. '9' )+ Exponent ( FloatTypeSuffix )? | ( '0' .. '9' )+ FloatTypeSuffix );";
        }
    }
    static final String DFA32_eotS =
        "\1\uffff\1\40\1\uffff\2\40\1\uffff\4\40\1\uffff\3\40\1\uffff\2\40"+
        "\1\65\4\40\2\75\3\uffff\1\40\3\uffff\1\40\1\uffff\1\40\1\uffff\13"+
        "\40\1\120\1\121\4\40\2\uffff\5\40\1\uffff\1\133\1\uffff\1\75\4\uffff"+
        "\1\134\1\135\1\40\1\137\1\140\1\141\1\142\1\143\4\40\1\150\2\uffff"+
        "\1\151\2\40\1\154\5\40\3\uffff\1\40\5\uffff\2\40\1\165\1\40\2\uffff"+
        "\1\40\1\170\1\uffff\1\40\1\172\1\173\3\40\1\177\1\u0080\1\uffff"+
        "\1\u0081\1\40\1\uffff\1\40\2\uffff\1\u0084\2\40\3\uffff\2\40\1\uffff"+
        "\1\u0089\2\40\1\u008c\1\uffff\1\40\1\u008e\1\uffff\1\40\1\uffff"+
        "\1\40\1\u0091\1\uffff";
    static final String DFA32_eofS =
        "\u0092\uffff";
    static final String DFA32_minS =
        "\1\11\1\56\1\uffff\2\56\1\uffff\4\56\1\uffff\3\56\1\uffff\2\56\1"+
        "\60\6\56\1\uffff\1\100\1\uffff\1\56\2\uffff\1\52\1\56\1\uffff\1"+
        "\56\1\uffff\21\56\2\uffff\5\56\1\uffff\1\56\1\uffff\1\56\4\uffff"+
        "\15\56\2\uffff\11\56\3\uffff\1\56\5\uffff\4\56\2\uffff\2\56\1\uffff"+
        "\10\56\1\uffff\2\56\1\uffff\1\56\2\uffff\3\56\3\uffff\2\56\1\uffff"+
        "\4\56\1\uffff\2\56\1\uffff\1\56\1\uffff\2\56\1\uffff";
    static final String DFA32_maxS =
        "\1\174\1\172\1\uffff\2\172\1\uffff\4\172\1\uffff\3\172\1\uffff\2"+
        "\172\1\71\4\172\1\170\1\146\1\uffff\1\172\1\uffff\1\172\2\uffff"+
        "\1\57\1\172\1\uffff\1\172\1\uffff\21\172\2\uffff\5\172\1\uffff\1"+
        "\146\1\uffff\1\146\4\uffff\15\172\2\uffff\11\172\3\uffff\1\172\5"+
        "\uffff\4\172\2\uffff\2\172\1\uffff\10\172\1\uffff\2\172\1\uffff"+
        "\1\172\2\uffff\3\172\3\uffff\2\172\1\uffff\4\172\1\uffff\2\172\1"+
        "\uffff\1\172\1\uffff\2\172\1\uffff";
    static final String DFA32_acceptS =
        "\2\uffff\1\2\2\uffff\1\5\4\uffff\1\14\3\uffff\1\20\11\uffff\1\42"+
        "\1\uffff\1\45\1\uffff\1\50\1\51\2\uffff\1\46\1\uffff\1\47\21\uffff"+
        "\1\41\1\25\5\uffff\1\36\1\uffff\1\37\1\uffff\1\43\1\44\1\52\1\53"+
        "\15\uffff\1\15\1\21\11\uffff\1\40\1\1\1\3\1\uffff\1\6\1\23\1\7\1"+
        "\10\1\11\4\uffff\1\32\1\16\2\uffff\1\24\10\uffff\1\27\2\uffff\1"+
        "\22\1\uffff\1\30\1\33\3\uffff\1\31\1\12\1\13\2\uffff\1\34\4\uffff"+
        "\1\35\2\uffff\1\26\1\uffff\1\17\2\uffff\1\4";
    static final String DFA32_specialS =
        "\u0092\uffff}>";
    static final String[] DFA32_transitionS = {
            "\1\35\1\34\2\uffff\1\34\22\uffff\1\35\1\uffff\1\30\4\uffff\1"+
            "\32\4\uffff\1\2\1\uffff\1\21\1\36\1\26\11\27\1\16\2\uffff\1"+
            "\5\2\uffff\1\31\32\33\4\uffff\1\33\1\uffff\1\4\1\22\1\10\1\25"+
            "\1\15\1\24\2\33\1\13\2\33\1\23\1\6\1\20\1\14\1\33\1\11\1\3\1"+
            "\7\1\17\1\33\1\1\4\33\1\uffff\1\12",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\1\37"+
            "\31\41",
            "",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\4\41"+
            "\1\43\25\41",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\6\41"+
            "\1\44\6\41\1\46\7\41\1\45\4\41",
            "",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\1\50"+
            "\7\41\1\47\21\41",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\7\41"+
            "\1\52\14\41\1\51\5\41",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\7\41"+
            "\1\54\6\41\1\53\13\41",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\24\41"+
            "\1\55\5\41",
            "",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\5\41"+
            "\1\57\7\41\1\56\14\41",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\24\41"+
            "\1\60\5\41",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\27\41"+
            "\1\61\2\41",
            "",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\7\41"+
            "\1\62\22\41",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\16\41"+
            "\1\63\13\41",
            "\12\64",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\16\41"+
            "\1\66\11\41\1\67\1\41",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\16\41"+
            "\1\70\13\41",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\13\41"+
            "\1\71\16\41",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\16\41"+
            "\1\72\13\41",
            "\1\64\1\uffff\10\74\2\64\12\uffff\3\64\21\uffff\1\73\13\uffff"+
            "\3\64\21\uffff\1\73",
            "\1\64\1\uffff\12\76\12\uffff\3\64\35\uffff\3\64",
            "",
            "\1\77\32\100\4\uffff\1\100\1\uffff\32\100",
            "",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\32\41",
            "",
            "",
            "\1\101\4\uffff\1\102",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\21\41"+
            "\1\103\10\41",
            "",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\32\41",
            "",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\5\41"+
            "\1\104\24\41",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\6\41"+
            "\1\105\23\41",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\6\41"+
            "\1\106\23\41",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\3\41"+
            "\1\107\26\41",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\15\41"+
            "\1\110\14\41",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\27\41"+
            "\1\111\2\41",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\14\41"+
            "\1\112\15\41",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\16\41"+
            "\1\113\13\41",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\24\41"+
            "\1\114\5\41",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\1\115"+
            "\31\41",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\4\41"+
            "\1\116\25\41",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\23\41"+
            "\1\117\6\41",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\32\41",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\23\41"+
            "\1\122\6\41",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\23\41"+
            "\1\123\6\41",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\4\41"+
            "\1\124\25\41",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\23\41"+
            "\1\125\6\41",
            "",
            "",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\16\41"+
            "\1\126\13\41",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\23\41"+
            "\1\127\6\41",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\15\41"+
            "\1\130\14\41",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\16\41"+
            "\1\131\13\41",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\24\41"+
            "\1\132\5\41",
            "",
            "\1\64\1\uffff\10\74\2\64\12\uffff\3\64\35\uffff\3\64",
            "",
            "\1\64\1\uffff\12\76\12\uffff\3\64\35\uffff\3\64",
            "",
            "",
            "",
            "",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\32\41",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\32\41",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\21\41"+
            "\1\136\10\41",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\32\41",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\32\41",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\32\41",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\32\41",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\32\41",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\21\41"+
            "\1\144\10\41",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\15\41"+
            "\1\145\14\41",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\21\41"+
            "\1\146\10\41",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\21\41"+
            "\1\147\10\41",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\32\41",
            "",
            "",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\32\41",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\4\41"+
            "\1\152\25\41",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\15\41"+
            "\1\153\14\41",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\32\41",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\13\41"+
            "\1\155\16\41",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\4\41"+
            "\1\156\25\41",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\6\41"+
            "\1\157\23\41",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\1\160"+
            "\31\41",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\1\41"+
            "\1\161\30\41",
            "",
            "",
            "",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\4\41"+
            "\1\162\25\41",
            "",
            "",
            "",
            "",
            "",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\23\41"+
            "\1\163\6\41",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\23\41"+
            "\1\164\6\41",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\32\41",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\30\41"+
            "\1\166\1\41",
            "",
            "",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\21\41"+
            "\1\167\10\41",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\32\41",
            "",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\4\41"+
            "\1\171\25\41",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\32\41",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\32\41",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\23\41"+
            "\1\174\6\41",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\13\41"+
            "\1\175\16\41",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\6\41"+
            "\1\176\23\41",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\32\41",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\32\41",
            "",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\32\41",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\15\41"+
            "\1\u0082\14\41",
            "",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\1\u0083"+
            "\31\41",
            "",
            "",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\32\41",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\4\41"+
            "\1\u0085\25\41",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\1\u0086"+
            "\31\41",
            "",
            "",
            "",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\1\u0087"+
            "\31\41",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\15\41"+
            "\1\u0088\14\41",
            "",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\32\41",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\23\41"+
            "\1\u008a\6\41",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\13\41"+
            "\1\u008b\16\41",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\32\41",
            "",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\10\41"+
            "\1\u008d\21\41",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\32\41",
            "",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\16\41"+
            "\1\u008f\13\41",
            "",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\15\41"+
            "\1\u0090\14\41",
            "\1\42\1\uffff\12\41\7\uffff\32\41\4\uffff\1\41\1\uffff\32\41",
            ""
    };

    static final short[] DFA32_eot = DFA.unpackEncodedString(DFA32_eotS);
    static final short[] DFA32_eof = DFA.unpackEncodedString(DFA32_eofS);
    static final char[] DFA32_min = DFA.unpackEncodedStringToUnsignedChars(DFA32_minS);
    static final char[] DFA32_max = DFA.unpackEncodedStringToUnsignedChars(DFA32_maxS);
    static final short[] DFA32_accept = DFA.unpackEncodedString(DFA32_acceptS);
    static final short[] DFA32_special = DFA.unpackEncodedString(DFA32_specialS);
    static final short[][] DFA32_transition;

    static {
        int numStates = DFA32_transitionS.length;
        DFA32_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA32_transition[i] = DFA.unpackEncodedString(DFA32_transitionS[i]);
        }
    }

    class DFA32 extends DFA {

        public DFA32(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 32;
            this.eot = DFA32_eot;
            this.eof = DFA32_eof;
            this.min = DFA32_min;
            this.max = DFA32_max;
            this.accept = DFA32_accept;
            this.special = DFA32_special;
            this.transition = DFA32_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( T__30 | T__31 | T__32 | T__33 | T__34 | T__35 | T__36 | T__37 | T__38 | T__39 | T__40 | T__41 | T__42 | T__43 | T__44 | T__45 | T__46 | T__47 | T__48 | T__49 | T__50 | T__51 | T__52 | T__53 | T__54 | T__55 | T__56 | T__57 | T__58 | HexLiteral | DecimalLiteral | OctalLiteral | FloatingPointLiteral | StringLiteral | GlobalAnnotation | LocalAnnotation | Expression | Identifier | AnnotationIdentifier | Newline | Whitespace | BlockComment | LineComment );";
        }
    }
 

}