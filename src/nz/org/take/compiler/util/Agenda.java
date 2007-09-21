package nz.org.take.compiler.util;

import nz.org.take.Query;

/**
 * This class is responsible for managing the agenda of querries that
 * havent been compiled yet.
 * @author Bastian Schenke (bastian.schenke@googlemail.com)
 */
public interface Agenda {
	
	/**
	 * Indicates whether the agenda is empty.
	 * 
	 * @return Returns a boolean.
	 */
	public boolean isAgendaEmpty();

	/**
	 * Returns the next query.
	 * 
	 * @return a query
	 */
	public Query getNextQuery();

	/**
	 * Remove the current query.
	 * 
	 * @return a query
	 */
	public void removeLastFromAgenda();

	/**
	 * Add a query to the agenda.
	 * 
	 * @param q a query
	 */
	public void addToAgenda(Query query);

}
