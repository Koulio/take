package nz.ac.massey.take.takeep.editor;


import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.text.StyledEditorKit.BoldAction;

import nz.ac.massey.take.takeep.editor.tokens.TakePartitionScanner;
import nz.ac.massey.take.takeep.editor.tokens.WhitespaceDetector;
import nz.ac.massey.take.takeep.editor.tokens.TakePartitionScanner.TAKE_PARTITIONS;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextDoubleClickStrategy;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.IPresentationRepairer;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.BufferedRuleBasedScanner;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.rules.EndOfLineRule;
import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.IWordDetector;
import org.eclipse.jface.text.rules.RuleBasedPartitionScanner;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WhitespaceRule;
import org.eclipse.jface.text.rules.WordRule;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;



public class TakeSourceViewerConfiguration extends SourceViewerConfiguration {


	private DesignManager designManager;

	public enum TAKE_TOKENS
	{
		TAKE_KEYWORD,
		TAKE_STRING_LITERAL
		
	}
	
	public TakeSourceViewerConfiguration(DesignManager colorManager) {
		this.designManager = colorManager;
	}


	public String[] getConfiguredContentTypes(ISourceViewer sourceViewer) {
		ArrayList<String> strings = new ArrayList<String>();
		strings.add(IDocument.DEFAULT_CONTENT_TYPE);
		
		for(TAKE_PARTITIONS tp : TAKE_PARTITIONS.values())
		{
			strings.add(tp.name());
		}
		
		for(TAKE_TOKENS tp : TAKE_TOKENS.values())
		{
			strings.add(tp.name());
		}
		
		return strings.toArray(new String[strings.size()]);
	}




	class TokenHighlighter extends RuleBasedScanner {

		public TokenHighlighter(Color c, int style) {
			setDefaultReturnToken(buildTextAttributeToken(c, style));
		}



	}

	public Token buildTextAttributeToken(Color c, int style) {
		return new Token(
				new TextAttribute(c,null,style));
	}
	
	
	class BodyScanner extends RuleBasedScanner {
		private String[] keyWords = {"var", "not" , "ref", "and","if","then","query","external","in","out","aggregation","sum","max","min","avg","count"};
		public BodyScanner() {
			WordRule rule = new WordRule(new IWordDetector() {
				public boolean isWordStart(char c) { 
					return Character.isJavaIdentifierStart(c); 
				}
				public boolean isWordPart(char c) {   
					return Character.isJavaIdentifierPart(c); 
				}
				
				
			}){

				@Override
				public IToken evaluate(ICharacterScanner scanner) {
					scanner.unread();
					int c = scanner.read();
					if(Character.isLetterOrDigit(c))
					{
						return Token.UNDEFINED;
					}
					return super.evaluate(scanner);
				}
				
			};
			

			Token keyword = buildTextAttributeToken(designManager.getColor(TAKE_TOKENS.TAKE_KEYWORD.name()), designManager.getStyle(TAKE_TOKENS.TAKE_KEYWORD.name()));

			for(String s : keyWords)
			{
				rule.addWord(s, keyword);
			}
			
			Token stringLiteral = buildTextAttributeToken(designManager.getColor(TAKE_TOKENS.TAKE_STRING_LITERAL.name()), designManager.getStyle(TAKE_TOKENS.TAKE_STRING_LITERAL.name()));
			SingleLineRule stringLiteralRule = new SingleLineRule("\"","\"",stringLiteral,(char) 0);
			

			
			setRules(new IRule[] {
					rule,stringLiteralRule
			});
		}
	}


	public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) {
		PresentationReconciler reconciler = new PresentationReconciler();



		setUpDamageReconciler(reconciler, TAKE_PARTITIONS.TAKE_LOCAL_ANNOTATION.name());
		setUpDamageReconciler(reconciler,  TAKE_PARTITIONS.TAKE_GLOBAL_ANNOTATION.name());
		setUpDamageReconciler(reconciler, TAKE_PARTITIONS.TAKE_COMMENT.name());
		
		setUpDamageReconciler(reconciler, TAKE_TOKENS.TAKE_STRING_LITERAL.name());

		DefaultDamagerRepairer bodyDR = new DefaultDamagerRepairer(new BodyScanner());
		
		setKeywordHighlightingPartition(reconciler, bodyDR, IDocument.DEFAULT_CONTENT_TYPE);
		setKeywordHighlightingPartition(reconciler, bodyDR, TAKE_PARTITIONS.TAKE_EXTERNAL.name());
		setKeywordHighlightingPartition(reconciler, bodyDR, TAKE_PARTITIONS.TAKE_QUERY.name());
		setKeywordHighlightingPartition(reconciler, bodyDR, TAKE_PARTITIONS.TAKE_RULE_OR_FACT.name());
		setKeywordHighlightingPartition(reconciler, bodyDR, TAKE_PARTITIONS.TAKE_REF.name());
		setKeywordHighlightingPartition(reconciler, bodyDR, TAKE_PARTITIONS.TAKE_VAR.name());
		setKeywordHighlightingPartition(reconciler, bodyDR, TAKE_PARTITIONS.TAKE_AGGREGATION.name());
		
		return reconciler;
	}


	private void setKeywordHighlightingPartition(
			PresentationReconciler reconciler, DefaultDamagerRepairer dr,
			String type) {
		reconciler.setDamager(dr, type);
		reconciler.setRepairer(dr, type);
	}


	private void setUpDamageReconciler(PresentationReconciler reconciler, String tokenValue) {
		DefaultDamagerRepairer dr = new DefaultDamagerRepairer(buildTokenDesign(tokenValue));
		setKeywordHighlightingPartition(reconciler, dr, tokenValue);
	}

	private TokenHighlighter buildTokenDesign(String tokenValue)
	{
		Integer style = designManager.getStyle(tokenValue);
		Color color = designManager.getColor(tokenValue);

		return new TokenHighlighter(color,style);

	}
}
