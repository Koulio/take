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
 * Visitor for knowledge bases.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */
public interface KnowledgeBaseVisitor {
	public boolean visit(KnowledgeBase kb);
	public void endVisit(KnowledgeBase kb);
	public boolean visit(DerivationRule r);
	public void endVisit(DerivationRule r);
	public boolean visit(ExternalKnowledge k);
	public void endVisit(ExternalKnowledge k);
	public boolean visit(Fact f);
	public void endVisit(Fact f);
	public boolean visit(ComplexTerm t);
	public void endVisit(ComplexTerm t);
	public boolean visit(Constant t);
	public void endVisit(Constant t);
	public boolean visit(Variable t);
	public void endVisit(Variable t);
	public boolean visit(Query q);
	public void endVisit(Query q);
	
}
