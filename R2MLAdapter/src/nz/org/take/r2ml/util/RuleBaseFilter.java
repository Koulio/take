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

import nz.org.take.r2ml.R2MLException;
import de.tu_cottbus.r2ml.RuleBase;

/**
 * RepairFilters are responsible to repair errors in R2ML rule bases. This
 * interface is neccessary hence strelka is a very academic project with lots
 * of bugs and errors.
 * 
 * @author schenke
 *
 */
public interface RuleBaseFilter {
	
	public String getName();

	public void repair(RuleBase ruleBase) throws R2MLException;
	
}
