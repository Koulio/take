package nz.org.take.script;

import java.util.ArrayList;
import java.util.List;

import org.antlr.runtime.Token;

import nz.org.take.AbstractAnnotatable;
import nz.org.take.NamedElement;

public class QueryDeclaration extends AbstractAnnotatable implements NamedElement {
	private String name;
	private List<IOState> parameterStates;
	
	private int lineNumber;
	private int columnNumber;
	
	public QueryDeclaration(Token predicate, List<IOState> parameterStates) {
		this.name = predicate.getText();
		this.parameterStates = new ArrayList<IOState>(parameterStates);
		
		this.lineNumber = predicate.getLine();
		this.columnNumber = predicate.getCharPositionInLine();
	}
	
	public String getName() {
		return name;
	}
	
	public List<IOState> getParameterStates() {
		return new ArrayList<IOState>(parameterStates);
	}
	
	public int getLineNumber() {
		return lineNumber;
	}
	
	public int getColumnNumber() {
		return columnNumber;
	}
}
