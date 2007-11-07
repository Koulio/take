/**
 * Copyright 2007 Jens Dietrich Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
 */
 

package example.nz.org.take.compiler.userv.domainmodel;

/**
 * Bean class that is part of the example domain model.
 * http://www.businessrulesforum.com/2005_Product_Derby.pdf 
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class Driver {
	private String id = "424242";
	private boolean isMale = true;
	private int age = 18;
	private boolean hasDriversTrainingFromSchool = false;
	private boolean  hasDriversTrainingFromLicensedDriverTrainingCompany = false;
	private boolean hasTakenASeniorCitizenDriversRefresherCourse = false;
	private int numberOfAccidentsInvolvedIn = 0;
	private int numberOfMovingViolationsInLastTwoYears = 0;
	private int numberOfYearsWithUServ = 0;
	private boolean isPreferred = false;
	private boolean isElite = false;
	private String location = "unknown"; // the state, such as CA or NY
	private boolean isMarried = false;
	
	public boolean isMarried() {
		return isMarried;
	}
	public void setMarried(boolean isMarried) {
		this.isMarried = isMarried;
	}
	public boolean isMale() {
		return isMale;
	}
	public void setMale(boolean isMale) {
		this.isMale = isMale;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}

	public boolean hasDriversTrainingFromSchool() {
		return hasDriversTrainingFromSchool;
	}
	public void setHasDriversTrainingFromSchool(boolean hasDriversTrainingFromSchool) {
		this.hasDriversTrainingFromSchool = hasDriversTrainingFromSchool;
	}
	public boolean hasDriversTrainingFromLicensedDriverTrainingCompany() {
		return hasDriversTrainingFromLicensedDriverTrainingCompany;
	}
	public void setHasDriversTrainingFromLicensedDriverTrainingCompany(
			boolean hasDriversTrainingFromLicensedDriverTrainingCompany) {
		this.hasDriversTrainingFromLicensedDriverTrainingCompany = hasDriversTrainingFromLicensedDriverTrainingCompany;
	}
	public boolean hasTakenASeniorCitizenDriversRefresherCourse() {
		return hasTakenASeniorCitizenDriversRefresherCourse;
	}
	public void setHasTakenASeniorCitizenDriversRefresherCourse(
			boolean hasTakenASeniorCitizenDriversRefresherCourse) {
		this.hasTakenASeniorCitizenDriversRefresherCourse = hasTakenASeniorCitizenDriversRefresherCourse;
	}
	public int getNumberOfAccidentsInvolvedIn() {
		return numberOfAccidentsInvolvedIn;
	}
	public void setNumberOfAccidentsInvolvedIn(int numberOfAccidentsInvolvedIn) {
		this.numberOfAccidentsInvolvedIn = numberOfAccidentsInvolvedIn;
	}
	public int getNumberOfMovingViolationsInLastTwoYears() {
		return numberOfMovingViolationsInLastTwoYears;
	}
	public void setNumberOfMovingViolationsInLastTwoYears(
			int numberOfMovingViolationsInLastTwoYears) {
		this.numberOfMovingViolationsInLastTwoYears = numberOfMovingViolationsInLastTwoYears;
	}
	public int getNumberOfYearsWithUServ() {
		return numberOfYearsWithUServ;
	}
	public void setNumberOfYearsWithUServ(int numberOfYearsWithUServ) {
		this.numberOfYearsWithUServ = numberOfYearsWithUServ;
	}
	public boolean isPreferred() {
		return isPreferred;
	}
	public void setPreferred(boolean isPreferred) {
		this.isPreferred = isPreferred;
	}
	public boolean isElite() {
		return isElite;
	}
	public void setElite(boolean isElite) {
		this.isElite = isElite;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	

}
