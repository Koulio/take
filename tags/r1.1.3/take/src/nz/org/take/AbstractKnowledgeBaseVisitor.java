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
 * Useful abstract kb visitor. The visit method return all true, i.e., the children will be visited.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class AbstractKnowledgeBaseVisitor implements KnowledgeBaseVisitor {

	public void endVisit(KnowledgeBase kb) {
		

	}

	public void endVisit(DerivationRule r) {
		

	}

	public void endVisit(ExternalFactStore k) {
		

	}

	public void endVisit(Fact f) {
		

	}

	public void endVisit(ComplexTerm t) {
		

	}

	public void endVisit(Constant t) {
		

	}

	public void endVisit(Variable t) {
		

	}

	public void endVisit(Query q) {
		

	}

	public boolean visit(KnowledgeBase kb) {
		
		return true;
	}

	public boolean visit(DerivationRule r) {
		
		return true;
	}

	public boolean visit(ExternalFactStore k) {
		
		return true;
	}

	public boolean visit(Fact f) {
		
		return true;
	}

	public boolean visit(ComplexTerm t) {
		
		return true;
	}

	public boolean visit(Constant t) {
		
		return true;
	}

	public boolean visit(Variable t) {
		
		return true;
	}

	public boolean visit(Query q) {
		
		return true;
	}

}
