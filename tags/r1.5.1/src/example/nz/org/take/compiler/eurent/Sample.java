/*
 * Copyright 2007 Jens Dietrich 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
 */

package example.nz.org.take.compiler.eurent;

import java.util.List;
import java.util.Map;

import nz.org.take.rt.DerivationLogEntry;
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
		KB kb = new KB();
		ResultSet<IsAvailable> rs = kb.isAvailable10(c);
		IsAvailable result = rs.next();
		List<DerivationLogEntry> log = rs.getDerivationLog();
		System.out.println(result.branch.getName());
		for (DerivationLogEntry e:log) {
			Map<String,String> annotations = kb.getAnnotations(e.getName());
			if (annotations!=null){
				System.out.println(annotations.get("take.auto.date"));
				System.out.println(annotations.get("take.auto.creator"));
				System.out.println(annotations.get("take.auto.tostring"));
			}
		}
                                                                                                     
		                                                                                                            
	}

}
