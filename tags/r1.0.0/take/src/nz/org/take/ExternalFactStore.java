/*
 * Copyright (C) 2007 <A href="http://www-ist.massey.ac.nz/JBDietrich" target="_top">Jens Dietrich</a>
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package nz.org.take;

/**
 * Represents external fact stores.
 * The compiler will generate a resourec iterator factory interface
 * for these fact stores that can then be implemented.
 * Typically, an implementation is based on a database or web service.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class ExternalFactStore extends AbstractAnnotatable implements KnowledgeElement {

	private String id = null;
	private SimplePredicate predicate = null;

	public void accept(KnowledgeBaseVisitor visitor) {
		visitor.visit(this);
		visitor.endVisit(this);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public SimplePredicate getPredicate() {
		return predicate;
	}

	public void setPredicate(SimplePredicate predicate) {
		this.predicate = predicate;
	}

}
