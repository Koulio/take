/*
 * Copyright 2007 Bastian Schenke Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
 */
package nz.org.take.r2ml;

/**
 * @author Bastian Schenke (bastian.schenke@googlemail.com)
 *
 */
public class DataValueHandler implements XmlTypeHandler {

	/**
	 * Map a DataValue to a Fact.
	 * 
	 * The "inner" lists represent disjuncts of the original condition and there
	 * elements are suppossed to be conjuncted Prerequisites. This is neccessary
	 * hence take doesnt support Disjunctions in rule bodies. Each disjunct is
	 * represented by a single take rule with the same head.
	 * 
	 * @param obj
	 *            a Condition
	 * @return a List of Lists that contain Prerequisites
	 * 
	 * @see nz.org.take.r2ml.XmlTypeHandler#importObject(java.lang.Object,
	 *      nz.org.take.r2ml.R2MLDriver)
	 */
	public Object importObject(Object obj) throws R2MLException {

		//DataValue
		return null;
	}

}
