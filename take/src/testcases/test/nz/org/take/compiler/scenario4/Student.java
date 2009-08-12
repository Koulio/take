/**
 * Copyright 2007 Jens Dietrich Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
 */

package test.nz.org.take.compiler.scenario4;
/**
 * Bean class referenced in tests. 
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */


public class Student extends Bean {
	private java.util.List<Course> courses = new java.util.ArrayList<Course>();

	public Student(String name) {
		super(name);
	}
	public Student() {
		super();
	}

	public java.util.List<Course> getCourses() {
		return courses;
	}

	public void setCourse(java.util.List<Course> courses) {
		this.courses = courses;
	}
}
