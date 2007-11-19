/**
 * Copyright 2007 Jens Dietrich Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
 */

package nz.org.take.mandarax_migration_kit;

import java.io.File;
import java.util.Map;
import org.apache.log4j.Category;

/**
 * Constant handlers are used to deal with the constants found in the
 * mandarax kb. These objects are represented by strings in the script
 * generated. 
 * @author Jens Dietrich
 */
public interface ConstantHandler {
	public void generateScript(File folder,Map<String,Object> constants,Category logger) throws Exception ;
	
}
