/*
 * Copyright 2007 Bastian Schenke Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
 */
package nz.org.take.r2ml.util;

import nz.org.take.PropertyPredicate;

/**
 * 
 * 
 * @author Bastian Schenke (bastian.schenke(at)googlemail.com)
 *
 */
public class R2MLPropertyPredicate extends PropertyPredicate {

	/**
	 * 
	 */
	public R2MLPropertyPredicate() {
		super();
	}
	
	

//	/* (non-Javadoc)
//	 * @see nz.org.take.PropertyPredicate#getSlotTypes()
//	 */
//	@Override
//	public Class[] getSlotTypes() {
//		// TODO Auto-generated method stub
//		return super.getSlotTypes();
//	}
//
//
//
	@Override
	public String getName() {
		return "p_" + super.getName();
	}
	
	

}
