/*
 * Copyright 2007 Bastian Schenke Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
 */
package example.nz.org.take.r2ml.userv.domain;

public class Driver {
	
	public static final String GENDER_MALE = "male";
	public static final String GENDER_FEMALE = "female";
	public static final String TRAINING_CERTIFICATE_CATEGORY_SCHOOL = "school certificate";
	public static final String TRAINING_CERTIFICATE_CATEGORY_LICENSE = "licensed training company";
	public static final String TRAINING_CERTIFICATE_CATEGORY_SENIOR = "senior citizen refresher course";
	
	private long age = 0;
	private long birthDate = 0;
	private String gender = GENDER_MALE;
	private String name = "";
	private long numberOfMovingViolations = 0;
	private long numberOfAccidents = 0;
	private boolean dui = false;
	private String trainingCertificateCategory = "";
	
	public Driver(String name, String gender, int age) {
		this.name = name;
		this.gender = gender;
		this.age = age;
	}
	public boolean isPerson () {
		return true;
	}
	public boolean isBusinessParty () {
		return true;
	}
	public boolean isServiceParticipant () {
		return true;
	}
	public boolean isDriver () {
		return true;
	}
	/**
	 * @return the age
	 */
	public long getAge() {
		return age;
	}
	/**
	 * @param age the age to set
	 */
	public void setAge(long age) {
		this.age = age;
	}
	/**
	 * @return the birthDate
	 */
	public long getBirthDate() {
		return birthDate;
	}
	/**
	 * @param birthDate the birthDate to set
	 */
	public void setBirthDate(long birthDate) {
		this.birthDate = birthDate;
	}
	/**
	 * @return the dui
	 */
	public boolean isDui() {
		return dui;
	}
	/**
	 * @param dui the dui to set
	 */
	public void setDui(boolean dui) {
		this.dui = dui;
	}
	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}
	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the numberOfAccidents
	 */
	public long getNumberOfAccidents() {
		return numberOfAccidents;
	}
	/**
	 * @param numberOfAccidents the numberOfAccidents to set
	 */
	public void setNumberOfAccidents(long numberOfAccidents) {
		this.numberOfAccidents = numberOfAccidents;
	}
	/**
	 * @return the numberOfMovingViolations
	 */
	public long getNumberOfMovingViolations() {
		return numberOfMovingViolations;
	}
	/**
	 * @param numberOfMovingViolations the numberOfMovingViolations to set
	 */
	public void setNumberOfMovingViolations(long numberOfMovingViolations) {
		this.numberOfMovingViolations = numberOfMovingViolations;
	}
	/**
	 * @return the trainingCertificateCategory
	 */
	public String getTrainingCertificateCategory() {
		return trainingCertificateCategory;
	}
	/**
	 * @param trainingCertificateCategory the trainingCertificateCategory to set
	 */
	public void setTrainingCertificateCategory(String trainingCertificateCategory) {
		this.trainingCertificateCategory = trainingCertificateCategory;
	}
}
