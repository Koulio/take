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
		TAKE_EXTERNAL, 
		TAKE_REF, 
		TAKE_AGGREGATION
	}





	class CompleteLine extends EndOfLineRule
	{

		public CompleteLine(String startSequence, IToken token) {
			super(startSequence, token);
			//this.setColumnConstraint(0);
		}



		protected boolean sequenceDetected(ICharacterScanner scanner, char[] sequence, boolean eofAllowed) {

			if(sequence == fStartSequence)
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

	class StatementRule extends EndOfLineRule
	{
		public StatementRule(String startSequence, IToken token) {
			super(startSequence, token);
			// TODO Auto-generated constructor stub
		}

		private char escapeChar = ' ';
		private char detectedChar = ':';
		


		@Override
		public IToken evaluate(ICharacterScanner scanner, boolean resume) {
			// TODO Auto-generated method stub
			return doEvaluate(scanner, resume);
		}


	


		@Override
		protected IToken doEvaluate(ICharacterScanner scanner, boolean resume) {
			if (resume) {

				if (endSequenceDetected(scanner))
					return fToken;

			} else {


				
					if (sequenceDetected(scanner, fStartSequence, false)) {
						if (endSequenceDetected(scanner))
							return fToken;
					}
				
			}
			
			
			return Token.UNDEFINED;
		}


		protected boolean sequenceDetected(ICharacterScanner scanner, char[] sequence, boolean eofAllowed) {

			if(sequence == fStartSequence)
			{
				
				scanner.unread();
				scanner.unread();
				int nlc = scanner.read();
				scanner.read();
				if(nlc != '\n' && nlc != '\r')
				{
					return false;
				}
				int c = scanner.read();
				
				boolean breakLoop = (c == '\n' || c == '\r') || c == escapeChar || c == ICharacterScanner.EOF ;

				int j = 1;
				while(!breakLoop) {
					
					if(c == detectedChar)
					{
						return true;
					}
					
					j++;
					c = scanner.read();
					breakLoop = (c == '\n' || c == '\r') || c == escapeChar || c == ICharacterScanner.EOF ;
				}
				
				for (int i= 1; i < j; i++)
					scanner.unread();
				return false;

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
		IToken ref = new Token(TAKE_PARTITIONS.TAKE_REF.name());
		IToken aggregation = new Token(TAKE_PARTITIONS.TAKE_AGGREGATION.name());

		IToken statement = new Token(TAKE_PARTITIONS.TAKE_RULE_OR_FACT.name());

		LinkedList<IPredicateRule> rules = new LinkedList<IPredicateRule>();

		rules.add(new EndOfLineRule("//",comment));

		rules.add(new CompleteLine("@@",gAnotation));
		//rules.add(new EndOfLineRule("@@",gAnotation));
		rules.add(new CompleteLine("@",lAnotation));
		//rules.add(new EndOfLineRule("@",lAnotation));
		rules.add(new CompleteLine("query ",query));
		rules.add(new CompleteLine("external ",external));
		rules.add(new CompleteLine("var ",var));
		rules.add(new CompleteLine("ref ",ref));
		rules.add(new CompleteLine("aggregation ", aggregation));

		rules.add(new StatementRule("bih", statement));
		setPredicateRules(rules.toArray(new IPredicateRule[rules.size()]));


	}


}
