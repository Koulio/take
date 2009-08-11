package nz.org.take.script;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import nz.org.take.Predicate;
import nz.org.take.Query;

public class QueryBuilder {
	private NamedElementTable<? extends Predicate> predicateTable;
	
	public QueryBuilder(NamedElementTable<? extends Predicate> predicateTable) {
		this.predicateTable = predicateTable;
	}
	
	public Iterable<Query> buildQueries(Iterable<QueryDeclaration> queryDeclarations) throws NoSuchPredicateException {
		Collection<Query> queries = new ArrayList<Query>();
		
		for (QueryDeclaration declaration : queryDeclarations)
			queries.add(buildQuery(declaration));
		
		return queries;
		
	}
	
	public Query buildQuery(QueryDeclaration queryDeclaration) throws NoSuchPredicateException {
		Query query = new Query();
		query.setPredicate(getPredicate(queryDeclaration));
		query.setInputParams(buildInputParams(queryDeclaration));
		query.addAnnotations(queryDeclaration.getAnnotations());
		
		return query;
	}
	
	private Predicate getPredicate(QueryDeclaration queryDeclaration) throws NoSuchPredicateException {
		Predicate predicate = predicateTable.get(queryDeclaration.getName());
		if (predicate == null)
			throw new NoSuchPredicateException(queryDeclaration.getName());
		else
			return predicate;
	}
	
	private boolean[] buildInputParams(QueryDeclaration queryDeclaration) {
		List<IOState> inputStates = queryDeclaration.getParameterStates();
		boolean[] inputParams = new boolean[inputStates.size()];
		
		for (int i = 0; i < inputParams.length; ++i)
			inputParams[i] = (inputStates.get(i) == IOState.IN ? true : false);
		
		return inputParams;
	}
}
