package nz.org.take.script;

import java.util.HashMap;
import java.util.Map;

public class QueryDeclarationTable {
	
	private Map<String, QueryDeclaration> queryDeclarationMap = new HashMap<String, QueryDeclaration>();

	public boolean containsName(String signature) {
		return queryDeclarationMap.containsKey(signature);
	}

	public QueryDeclaration get(String signature) {
		return queryDeclarationMap.get(signature);
	}

	public void put(QueryDeclaration queryDeclaration) throws DuplicateQueryException {
		if (this.containsName(queryDeclaration.getQuerySignature()))
			throw new DuplicateQueryException(queryDeclaration);
		
		queryDeclarationMap.put(queryDeclaration.getQuerySignature(), queryDeclaration);
	}
	
	public Iterable<QueryDeclaration> getValues() {
		return queryDeclarationMap.values();
	}

}
