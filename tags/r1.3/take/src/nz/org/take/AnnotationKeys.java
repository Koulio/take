/**
 * Copyright 2007 Jens Dietrich Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
 */

package nz.org.take;
/**
 * Constants representing annotation keys.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public interface AnnotationKeys {
	// annotation on queries used to define the method
	public static final String TAKE_GENERATE_SLOTS = "take.compilerhint.slots";
	// annotation on queries used to define the method
	public static final String TAKE_GENERATE_METHOD = "take.compilerhint.method";
	// annotation on queries used to define the local class name
	public static final String TAKE_GENERATE_CLASS = "take.compilerhint.class";
	// auto annotation for the current date
	public static final String TAKE_AUTO_DATE = "take.auto.date";
	// auto annotation for the string representation of a rule
	public static final String TAKE_AUTO_TOSTRING= "take.auto.name";
	// auto annotation for the author of a rule
	public static final String TAKE_AUTO_CREATOR= "take.auto.creator";
}
