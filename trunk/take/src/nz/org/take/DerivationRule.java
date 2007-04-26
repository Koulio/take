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

import java.util.ArrayList;
import java.util.List;

/**
 * Derivation rules, i.e., if .. then ..  constructs.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class DerivationRule extends AbstractAnnotatable implements Clause {
	private Fact head = null;
	private String id = null;
	private List<Prerequisite> body = new ArrayList<Prerequisite>();
	

	public Predicate getPredicate() {
		return head==null?null:head.getPredicate();
	}


	public Fact getHead() {
		return head;
	}


	public void setHead(Fact head) {
		this.head = head;
	}


	public List<Prerequisite> getBody() {
		return body;
	}


	public void setBody(List<Prerequisite> body) {
		this.body = body;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}

}
