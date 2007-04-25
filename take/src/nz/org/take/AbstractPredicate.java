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
 * Abstract predicate. By default, slot names are generated.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public abstract class AbstractPredicate implements Predicate {

	protected String[] slotNames;

	public String[] getSlotNames() {
		if (slotNames==null && this.getSlotTypes()!=null) {
			slotNames = new String[this.getSlotTypes().length];
			for (int i=1;i<slotNames.length+1;i++) {
				slotNames[i-1]="slot"+i;
			}
		}
		return slotNames;
	}
	public void setSlotNames(String[] slotNames) {
		this.slotNames = slotNames;
	}

}
