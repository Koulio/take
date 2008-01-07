package nz.ac.massey.take.takeep.editor.tokens;

import java.util.Arrays;
import java.util.LinkedList;

import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.RuleBasedPartitionScanner;
import org.eclipse.jface.text.rules.Token;

import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IWordDetector;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.EndOfLineRule;
import org.eclipse.jface.text.rules.NumberRule;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.PatternRule;
import org.eclipse.jface.text.rules.WhitespaceRule;
import org.eclipse.jface.text.rules.WordPatternRule;
import org.eclipse.jface.text.rules.WordRule;

import com.sun.org.apache.xalan.internal.xsltc.dom.SingletonIterator;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.RegularExpression;


public class TakePartitionScanner extends RuleBasedPartitionScanner{

	public enum TAKE_PARTITIONS
	{
		TAKE_COMMENT,
		TAKE_GLOBAL_ANNOTATION,
		TAKE_LOCAL_ANNOTATION,
		TAKE_RULE_OR_FACT,
		TAKE_VAR,
		TAKE_QUERY,
		TAKE_EXTERNAL
	}
	
	
	public enum TAKE_TOKENS
	{
		TAKE_KEYWORD,
		TAKE_STRING_LITERAL
		
	}

	
	class CompleteLine extends EndOfLineRule
	{

		public CompleteLine(String startSequence, IToken token) {
			super(startSequence, token);
			// TODO Auto-generated constructor stub
		}
		private boolean newLineStart = true;
		protected boolean sequenceDetected(ICharacterScanner scanner, char[] sequence, boolean eofAllowed) {
			
			if(newLineStart && sequence == fStartSequence)
			{

				scanner.unread();
				scanner.unread();
				int c = scanner.read();
				scanner.read();
				if(c != '\n' && c != '\r')
				{
					return false;
				}
			}
			
				return super.sequenceDetected(scanner, sequence, eofAllowed);
			
		}

	}
	
	
	public TakePartitionScanner()
	{
		super();
		IToken comment = new Token(TAKE_PARTITIONS.TAKE_COMMENT.name());
		IToken gAnotation = new Token(TAKE_PARTITIONS.TAKE_GLOBAL_ANNOTATION.name());
		IToken lAnotation = new Token(TAKE_PARTITIONS.TAKE_LOCAL_ANNOTATION.name());
		IToken query = new Token(TAKE_PARTITIONS.TAKE_QUERY.name());
		IToken external = new Token(TAKE_PARTITIONS.TAKE_EXTERNAL.name());
		IToken var = new Token(TAKE_PARTITIONS.TAKE_VAR.name());
		
		LinkedList<IPredicateRule> rules = new LinkedList<IPredicateRule>();

		rules.add(new EndOfLineRule("//",comment));

		//rules.add(new SingleLineRule("\"","\"",stringLiteral,(char) 0));
		
		rules.add(new CompleteLine("@@",gAnotation));
		//rules.add(new EndOfLineRule("@@",gAnotation));
		rules.add(new CompleteLine("@",lAnotation));
		//rules.add(new EndOfLineRule("@",lAnotation));
		rules.add(new CompleteLine("query ",query));
		rules.add(new CompleteLine("external ",external));
		rules.add(new CompleteLine("var ",var));
		
		
		setPredicateRules(rules.toArray(new IPredicateRule[rules.size()]));


	}


}
