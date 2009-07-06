// $ANTLR 3.1.1 /home/jens/development/take/workspace/take/grammar/Take.g 2008-12-12 12:53:20

package nz.org.take.script.antlr;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class TakeLexer extends Lexer {
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
    public static final int T__19=19;
    public static final int T__30=30;
    public static final int T__16=16;
    public static final int T__15=15;
    public static final int T__18=18;
    public static final int NEWLINE=6;
    public static final int T__17=17;
    public static final int T__12=12;
    public static final int T__11=11;
    public static final int T__14=14;
    public static final int T__13=13;
    public static final int T__10=10;

    // delegates
    // delegators

    public TakeLexer() {;} 
    public TakeLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public TakeLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "/home/jens/development/take/workspace/take/grammar/Take.g"; }

    // $ANTLR start "T__9"
    public final void mT__9() throws RecognitionException {
        try {
            int _type = T__9;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jens/development/take/workspace/take/grammar/Take.g:7:6: ( '@@' )
            // /home/jens/development/take/workspace/take/grammar/Take.g:7:8: '@@'
            {
            match("@@"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__9"

    // $ANTLR start "T__10"
    public final void mT__10() throws RecognitionException {
        try {
            int _type = T__10;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jens/development/take/workspace/take/grammar/Take.g:8:7: ( '=' )
            // /home/jens/development/take/workspace/take/grammar/Take.g:8:9: '='
            {
            match('='); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__10"

    // $ANTLR start "T__11"
    public final void mT__11() throws RecognitionException {
        try {
            int _type = T__11;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jens/development/take/workspace/take/grammar/Take.g:9:7: ( '@' )
            // /home/jens/development/take/workspace/take/grammar/Take.g:9:9: '@'
            {
            match('@'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__11"

    // $ANTLR start "T__12"
    public final void mT__12() throws RecognitionException {
        try {
            int _type = T__12;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jens/development/take/workspace/take/grammar/Take.g:10:7: ( 'var' )
            // /home/jens/development/take/workspace/take/grammar/Take.g:10:9: 'var'
            {
            match("var"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__12"

    // $ANTLR start "T__13"
    public final void mT__13() throws RecognitionException {
        try {
            int _type = T__13;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jens/development/take/workspace/take/grammar/Take.g:11:7: ( ',' )
            // /home/jens/development/take/workspace/take/grammar/Take.g:11:9: ','
            {
            match(','); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__13"

    // $ANTLR start "T__14"
    public final void mT__14() throws RecognitionException {
        try {
            int _type = T__14;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jens/development/take/workspace/take/grammar/Take.g:12:7: ( 'ref' )
            // /home/jens/development/take/workspace/take/grammar/Take.g:12:9: 'ref'
            {
            match("ref"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__14"

    // $ANTLR start "T__15"
    public final void mT__15() throws RecognitionException {
        try {
            int _type = T__15;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jens/development/take/workspace/take/grammar/Take.g:13:7: ( 'aggregation' )
            // /home/jens/development/take/workspace/take/grammar/Take.g:13:9: 'aggregation'
            {
            match("aggregation"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__15"

    // $ANTLR start "T__16"
    public final void mT__16() throws RecognitionException {
        try {
            int _type = T__16;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jens/development/take/workspace/take/grammar/Take.g:14:7: ( 'avg' )
            // /home/jens/development/take/workspace/take/grammar/Take.g:14:9: 'avg'
            {
            match("avg"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__16"

    // $ANTLR start "T__17"
    public final void mT__17() throws RecognitionException {
        try {
            int _type = T__17;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jens/development/take/workspace/take/grammar/Take.g:15:7: ( 'min' )
            // /home/jens/development/take/workspace/take/grammar/Take.g:15:9: 'min'
            {
            match("min"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__17"

    // $ANTLR start "T__18"
    public final void mT__18() throws RecognitionException {
        try {
            int _type = T__18;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jens/development/take/workspace/take/grammar/Take.g:16:7: ( 'max' )
            // /home/jens/development/take/workspace/take/grammar/Take.g:16:9: 'max'
            {
            match("max"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__18"

    // $ANTLR start "T__19"
    public final void mT__19() throws RecognitionException {
        try {
            int _type = T__19;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jens/development/take/workspace/take/grammar/Take.g:17:7: ( 'sum' )
            // /home/jens/development/take/workspace/take/grammar/Take.g:17:9: 'sum'
            {
            match("sum"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__19"

    // $ANTLR start "T__20"
    public final void mT__20() throws RecognitionException {
        try {
            int _type = T__20;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jens/development/take/workspace/take/grammar/Take.g:18:7: ( 'count' )
            // /home/jens/development/take/workspace/take/grammar/Take.g:18:9: 'count'
            {
            match("count"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__20"

    // $ANTLR start "T__21"
    public final void mT__21() throws RecognitionException {
        try {
            int _type = T__21;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jens/development/take/workspace/take/grammar/Take.g:19:7: ( 'external' )
            // /home/jens/development/take/workspace/take/grammar/Take.g:19:9: 'external'
            {
            match("external"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__21"

    // $ANTLR start "T__22"
    public final void mT__22() throws RecognitionException {
        try {
            int _type = T__22;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jens/development/take/workspace/take/grammar/Take.g:20:7: ( '.' )
            // /home/jens/development/take/workspace/take/grammar/Take.g:20:9: '.'
            {
            match('.'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__22"

    // $ANTLR start "T__23"
    public final void mT__23() throws RecognitionException {
        try {
            int _type = T__23;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jens/development/take/workspace/take/grammar/Take.g:21:7: ( 'boolean' )
            // /home/jens/development/take/workspace/take/grammar/Take.g:21:9: 'boolean'
            {
            match("boolean"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__23"

    // $ANTLR start "T__24"
    public final void mT__24() throws RecognitionException {
        try {
            int _type = T__24;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jens/development/take/workspace/take/grammar/Take.g:22:7: ( 'char' )
            // /home/jens/development/take/workspace/take/grammar/Take.g:22:9: 'char'
            {
            match("char"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__24"

    // $ANTLR start "T__25"
    public final void mT__25() throws RecognitionException {
        try {
            int _type = T__25;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jens/development/take/workspace/take/grammar/Take.g:23:7: ( 'byte' )
            // /home/jens/development/take/workspace/take/grammar/Take.g:23:9: 'byte'
            {
            match("byte"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__25"

    // $ANTLR start "T__26"
    public final void mT__26() throws RecognitionException {
        try {
            int _type = T__26;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jens/development/take/workspace/take/grammar/Take.g:24:7: ( 'short' )
            // /home/jens/development/take/workspace/take/grammar/Take.g:24:9: 'short'
            {
            match("short"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__26"

    // $ANTLR start "T__27"
    public final void mT__27() throws RecognitionException {
        try {
            int _type = T__27;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jens/development/take/workspace/take/grammar/Take.g:25:7: ( 'int' )
            // /home/jens/development/take/workspace/take/grammar/Take.g:25:9: 'int'
            {
            match("int"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__27"

    // $ANTLR start "T__28"
    public final void mT__28() throws RecognitionException {
        try {
            int _type = T__28;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jens/development/take/workspace/take/grammar/Take.g:26:7: ( 'long' )
            // /home/jens/development/take/workspace/take/grammar/Take.g:26:9: 'long'
            {
            match("long"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__28"

    // $ANTLR start "T__29"
    public final void mT__29() throws RecognitionException {
        try {
            int _type = T__29;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jens/development/take/workspace/take/grammar/Take.g:27:7: ( 'float' )
            // /home/jens/development/take/workspace/take/grammar/Take.g:27:9: 'float'
            {
            match("float"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__29"

    // $ANTLR start "T__30"
    public final void mT__30() throws RecognitionException {
        try {
            int _type = T__30;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jens/development/take/workspace/take/grammar/Take.g:28:7: ( 'double' )
            // /home/jens/development/take/workspace/take/grammar/Take.g:28:9: 'double'
            {
            match("double"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__30"

    // $ANTLR start "LINE_COMMENT"
    public final void mLINE_COMMENT() throws RecognitionException {
        try {
            int _type = LINE_COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jens/development/take/workspace/take/grammar/Take.g:160:5: ( '//' LINE_TEXT )
            // /home/jens/development/take/workspace/take/grammar/Take.g:160:7: '//' LINE_TEXT
            {
            match("//"); 

            mLINE_TEXT(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LINE_COMMENT"

    // $ANTLR start "LINE_TEXT"
    public final void mLINE_TEXT() throws RecognitionException {
        try {
            // /home/jens/development/take/workspace/take/grammar/Take.g:165:2: ( ( options {greedy=false; } : . )* NEWLINE )
            // /home/jens/development/take/workspace/take/grammar/Take.g:165:4: ( options {greedy=false; } : . )* NEWLINE
            {
            // /home/jens/development/take/workspace/take/grammar/Take.g:165:4: ( options {greedy=false; } : . )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0=='\r') ) {
                    alt1=2;
                }
                else if ( (LA1_0=='\n') ) {
                    alt1=2;
                }
                else if ( ((LA1_0>='\u0000' && LA1_0<='\t')||(LA1_0>='\u000B' && LA1_0<='\f')||(LA1_0>='\u000E' && LA1_0<='\uFFFF')) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // /home/jens/development/take/workspace/take/grammar/Take.g:165:34: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);

            mNEWLINE(); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "LINE_TEXT"

    // $ANTLR start "NEWLINE"
    public final void mNEWLINE() throws RecognitionException {
        try {
            int _type = NEWLINE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jens/development/take/workspace/take/grammar/Take.g:169:2: ( ( '\\r' )? '\\n' )
            // /home/jens/development/take/workspace/take/grammar/Take.g:169:4: ( '\\r' )? '\\n'
            {
            // /home/jens/development/take/workspace/take/grammar/Take.g:169:4: ( '\\r' )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0=='\r') ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // /home/jens/development/take/workspace/take/grammar/Take.g:169:4: '\\r'
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
    // $ANTLR end "NEWLINE"

    // $ANTLR start "ID"
    public final void mID() throws RecognitionException {
        try {
            int _type = ID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jens/development/take/workspace/take/grammar/Take.g:172:4: ( ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )* )
            // /home/jens/development/take/workspace/take/grammar/Take.g:172:6: ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )*
            {
            if ( (input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // /home/jens/development/take/workspace/take/grammar/Take.g:172:29: ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( ((LA3_0>='0' && LA3_0<='9')||(LA3_0>='A' && LA3_0<='Z')||LA3_0=='_'||(LA3_0>='a' && LA3_0<='z')) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // /home/jens/development/take/workspace/take/grammar/Take.g:
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
            	    break loop3;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ID"

    // $ANTLR start "WHITESPACE"
    public final void mWHITESPACE() throws RecognitionException {
        try {
            int _type = WHITESPACE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/jens/development/take/workspace/take/grammar/Take.g:175:2: ( ( ' ' | '\\t' )+ )
            // /home/jens/development/take/workspace/take/grammar/Take.g:175:4: ( ' ' | '\\t' )+
            {
            // /home/jens/development/take/workspace/take/grammar/Take.g:175:4: ( ' ' | '\\t' )+
            int cnt4=0;
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( (LA4_0=='\t'||LA4_0==' ') ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // /home/jens/development/take/workspace/take/grammar/Take.g:
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
            	    if ( cnt4 >= 1 ) break loop4;
                        EarlyExitException eee =
                            new EarlyExitException(4, input);
                        throw eee;
                }
                cnt4++;
            } while (true);

             _channel=HIDDEN; 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "WHITESPACE"

    public void mTokens() throws RecognitionException {
        // /home/jens/development/take/workspace/take/grammar/Take.g:1:8: ( T__9 | T__10 | T__11 | T__12 | T__13 | T__14 | T__15 | T__16 | T__17 | T__18 | T__19 | T__20 | T__21 | T__22 | T__23 | T__24 | T__25 | T__26 | T__27 | T__28 | T__29 | T__30 | LINE_COMMENT | NEWLINE | ID | WHITESPACE )
        int alt5=26;
        alt5 = dfa5.predict(input);
        switch (alt5) {
            case 1 :
                // /home/jens/development/take/workspace/take/grammar/Take.g:1:10: T__9
                {
                mT__9(); 

                }
                break;
            case 2 :
                // /home/jens/development/take/workspace/take/grammar/Take.g:1:15: T__10
                {
                mT__10(); 

                }
                break;
            case 3 :
                // /home/jens/development/take/workspace/take/grammar/Take.g:1:21: T__11
                {
                mT__11(); 

                }
                break;
            case 4 :
                // /home/jens/development/take/workspace/take/grammar/Take.g:1:27: T__12
                {
                mT__12(); 

                }
                break;
            case 5 :
                // /home/jens/development/take/workspace/take/grammar/Take.g:1:33: T__13
                {
                mT__13(); 

                }
                break;
            case 6 :
                // /home/jens/development/take/workspace/take/grammar/Take.g:1:39: T__14
                {
                mT__14(); 

                }
                break;
            case 7 :
                // /home/jens/development/take/workspace/take/grammar/Take.g:1:45: T__15
                {
                mT__15(); 

                }
                break;
            case 8 :
                // /home/jens/development/take/workspace/take/grammar/Take.g:1:51: T__16
                {
                mT__16(); 

                }
                break;
            case 9 :
                // /home/jens/development/take/workspace/take/grammar/Take.g:1:57: T__17
                {
                mT__17(); 

                }
                break;
            case 10 :
                // /home/jens/development/take/workspace/take/grammar/Take.g:1:63: T__18
                {
                mT__18(); 

                }
                break;
            case 11 :
                // /home/jens/development/take/workspace/take/grammar/Take.g:1:69: T__19
                {
                mT__19(); 

                }
                break;
            case 12 :
                // /home/jens/development/take/workspace/take/grammar/Take.g:1:75: T__20
                {
                mT__20(); 

                }
                break;
            case 13 :
                // /home/jens/development/take/workspace/take/grammar/Take.g:1:81: T__21
                {
                mT__21(); 

                }
                break;
            case 14 :
                // /home/jens/development/take/workspace/take/grammar/Take.g:1:87: T__22
                {
                mT__22(); 

                }
                break;
            case 15 :
                // /home/jens/development/take/workspace/take/grammar/Take.g:1:93: T__23
                {
                mT__23(); 

                }
                break;
            case 16 :
                // /home/jens/development/take/workspace/take/grammar/Take.g:1:99: T__24
                {
                mT__24(); 

                }
                break;
            case 17 :
                // /home/jens/development/take/workspace/take/grammar/Take.g:1:105: T__25
                {
                mT__25(); 

                }
                break;
            case 18 :
                // /home/jens/development/take/workspace/take/grammar/Take.g:1:111: T__26
                {
                mT__26(); 

                }
                break;
            case 19 :
                // /home/jens/development/take/workspace/take/grammar/Take.g:1:117: T__27
                {
                mT__27(); 

                }
                break;
            case 20 :
                // /home/jens/development/take/workspace/take/grammar/Take.g:1:123: T__28
                {
                mT__28(); 

                }
                break;
            case 21 :
                // /home/jens/development/take/workspace/take/grammar/Take.g:1:129: T__29
                {
                mT__29(); 

                }
                break;
            case 22 :
                // /home/jens/development/take/workspace/take/grammar/Take.g:1:135: T__30
                {
                mT__30(); 

                }
                break;
            case 23 :
                // /home/jens/development/take/workspace/take/grammar/Take.g:1:141: LINE_COMMENT
                {
                mLINE_COMMENT(); 

                }
                break;
            case 24 :
                // /home/jens/development/take/workspace/take/grammar/Take.g:1:154: NEWLINE
                {
                mNEWLINE(); 

                }
                break;
            case 25 :
                // /home/jens/development/take/workspace/take/grammar/Take.g:1:162: ID
                {
                mID(); 

                }
                break;
            case 26 :
                // /home/jens/development/take/workspace/take/grammar/Take.g:1:165: WHITESPACE
                {
                mWHITESPACE(); 

                }
                break;

        }

    }


    protected DFA5 dfa5 = new DFA5(this);
    static final String DFA5_eotS =
        "\1\uffff\1\26\1\uffff\1\23\1\uffff\6\23\1\uffff\5\23\6\uffff\21"+
        "\23\1\71\1\72\1\23\1\74\1\75\1\76\1\77\6\23\1\106\3\23\2\uffff\1"+
        "\23\4\uffff\2\23\1\115\2\23\1\120\1\uffff\1\121\3\23\1\125\1\126"+
        "\1\uffff\2\23\2\uffff\1\131\2\23\2\uffff\2\23\1\uffff\1\136\2\23"+
        "\1\141\1\uffff\1\23\1\143\1\uffff\1\23\1\uffff\1\23\1\146\1\uffff";
    static final String DFA5_eofS =
        "\147\uffff";
    static final String DFA5_minS =
        "\1\11\1\100\1\uffff\1\141\1\uffff\1\145\1\147\1\141\2\150\1\170"+
        "\1\uffff\1\157\1\156\1\157\1\154\1\157\6\uffff\1\162\1\146\2\147"+
        "\1\156\1\170\1\155\1\157\1\165\1\141\1\164\1\157\2\164\1\156\1\157"+
        "\1\165\2\60\1\162\4\60\1\162\1\156\1\162\1\145\1\154\1\145\1\60"+
        "\1\147\1\141\1\142\2\uffff\1\145\4\uffff\2\164\1\60\1\162\1\145"+
        "\1\60\1\uffff\1\60\1\164\1\154\1\147\2\60\1\uffff\1\156\1\141\2"+
        "\uffff\1\60\1\145\1\141\2\uffff\1\141\1\156\1\uffff\1\60\1\164\1"+
        "\154\1\60\1\uffff\1\151\1\60\1\uffff\1\157\1\uffff\1\156\1\60\1"+
        "\uffff";
    static final String DFA5_maxS =
        "\1\172\1\100\1\uffff\1\141\1\uffff\1\145\1\166\1\151\1\165\1\157"+
        "\1\170\1\uffff\1\171\1\156\1\157\1\154\1\157\6\uffff\1\162\1\146"+
        "\2\147\1\156\1\170\1\155\1\157\1\165\1\141\1\164\1\157\2\164\1\156"+
        "\1\157\1\165\2\172\1\162\4\172\1\162\1\156\1\162\1\145\1\154\1\145"+
        "\1\172\1\147\1\141\1\142\2\uffff\1\145\4\uffff\2\164\1\172\1\162"+
        "\1\145\1\172\1\uffff\1\172\1\164\1\154\1\147\2\172\1\uffff\1\156"+
        "\1\141\2\uffff\1\172\1\145\1\141\2\uffff\1\141\1\156\1\uffff\1\172"+
        "\1\164\1\154\1\172\1\uffff\1\151\1\172\1\uffff\1\157\1\uffff\1\156"+
        "\1\172\1\uffff";
    static final String DFA5_acceptS =
        "\2\uffff\1\2\1\uffff\1\5\6\uffff\1\16\5\uffff\1\27\1\30\1\31\1\32"+
        "\1\1\1\3\42\uffff\1\4\1\6\1\uffff\1\10\1\11\1\12\1\13\6\uffff\1"+
        "\23\6\uffff\1\20\2\uffff\1\21\1\24\3\uffff\1\22\1\14\2\uffff\1\25"+
        "\4\uffff\1\26\2\uffff\1\17\1\uffff\1\15\2\uffff\1\7";
    static final String DFA5_specialS =
        "\147\uffff}>";
    static final String[] DFA5_transitionS = {
            "\1\24\1\22\2\uffff\1\22\22\uffff\1\24\13\uffff\1\4\1\uffff\1"+
            "\13\1\21\15\uffff\1\2\2\uffff\1\1\32\23\4\uffff\1\23\1\uffff"+
            "\1\6\1\14\1\11\1\20\1\12\1\17\2\23\1\15\2\23\1\16\1\7\4\23\1"+
            "\5\1\10\2\23\1\3\4\23",
            "\1\25",
            "",
            "\1\27",
            "",
            "\1\30",
            "\1\31\16\uffff\1\32",
            "\1\34\7\uffff\1\33",
            "\1\36\14\uffff\1\35",
            "\1\40\6\uffff\1\37",
            "\1\41",
            "",
            "\1\42\11\uffff\1\43",
            "\1\44",
            "\1\45",
            "\1\46",
            "\1\47",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\50",
            "\1\51",
            "\1\52",
            "\1\53",
            "\1\54",
            "\1\55",
            "\1\56",
            "\1\57",
            "\1\60",
            "\1\61",
            "\1\62",
            "\1\63",
            "\1\64",
            "\1\65",
            "\1\66",
            "\1\67",
            "\1\70",
            "\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32\23",
            "\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32\23",
            "\1\73",
            "\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32\23",
            "\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32\23",
            "\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32\23",
            "\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32\23",
            "\1\100",
            "\1\101",
            "\1\102",
            "\1\103",
            "\1\104",
            "\1\105",
            "\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32\23",
            "\1\107",
            "\1\110",
            "\1\111",
            "",
            "",
            "\1\112",
            "",
            "",
            "",
            "",
            "\1\113",
            "\1\114",
            "\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32\23",
            "\1\116",
            "\1\117",
            "\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32\23",
            "",
            "\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32\23",
            "\1\122",
            "\1\123",
            "\1\124",
            "\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32\23",
            "\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32\23",
            "",
            "\1\127",
            "\1\130",
            "",
            "",
            "\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32\23",
            "\1\132",
            "\1\133",
            "",
            "",
            "\1\134",
            "\1\135",
            "",
            "\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32\23",
            "\1\137",
            "\1\140",
            "\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32\23",
            "",
            "\1\142",
            "\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32\23",
            "",
            "\1\144",
            "",
            "\1\145",
            "\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32\23",
            ""
    };

    static final short[] DFA5_eot = DFA.unpackEncodedString(DFA5_eotS);
    static final short[] DFA5_eof = DFA.unpackEncodedString(DFA5_eofS);
    static final char[] DFA5_min = DFA.unpackEncodedStringToUnsignedChars(DFA5_minS);
    static final char[] DFA5_max = DFA.unpackEncodedStringToUnsignedChars(DFA5_maxS);
    static final short[] DFA5_accept = DFA.unpackEncodedString(DFA5_acceptS);
    static final short[] DFA5_special = DFA.unpackEncodedString(DFA5_specialS);
    static final short[][] DFA5_transition;

    static {
        int numStates = DFA5_transitionS.length;
        DFA5_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA5_transition[i] = DFA.unpackEncodedString(DFA5_transitionS[i]);
        }
    }

    class DFA5 extends DFA {

        public DFA5(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 5;
            this.eot = DFA5_eot;
            this.eof = DFA5_eof;
            this.min = DFA5_min;
            this.max = DFA5_max;
            this.accept = DFA5_accept;
            this.special = DFA5_special;
            this.transition = DFA5_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( T__9 | T__10 | T__11 | T__12 | T__13 | T__14 | T__15 | T__16 | T__17 | T__18 | T__19 | T__20 | T__21 | T__22 | T__23 | T__24 | T__25 | T__26 | T__27 | T__28 | T__29 | T__30 | LINE_COMMENT | NEWLINE | ID | WHITESPACE );";
        }
    }
 

}