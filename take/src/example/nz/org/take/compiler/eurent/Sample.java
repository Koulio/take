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

package example.nz.org.take.compiler.eurent;

import nz.org.take.rt.ResultSet;
import example.nz.org.take.compiler.eurent.generated.IsAvailable;
import example.nz.org.take.compiler.eurent.generated.KB;

/**
 * Sample script used to query the generated classes.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class Sample {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		RentalCar c = new RentalCar();
		c.setPlateNumber("No42");
		Branch b = new Branch();
		b.setName("Palmerston North");
		Rental r = new Rental();
		r.setId("rental1");
		c.setScheduledForService(false);
		c.setStoredAt(b);
		c.setAssignedTo(null);
		ResultSet<IsAvailable> rs = new KB().isAvailable(c);
		IsAvailable result = rs.next();
		System.out.println(result.branch.getName());
		
		//rule1: if storedAt[car,branch] and not scheduledForService[car] and not hasAssignedTo[car] then availableAt[car,branch]
		                                                                                                            
		                                                                                                            
	}

}
