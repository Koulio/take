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

public class CarModel {
	private boolean highTheftProbability = false;
	private double basePremium = 0.0;
	private String modelType = "";
	
	/**
	 * @param highTheftProbability
	 * @param basePremium
	 * @param modelType
	 */
	public CarModel(boolean highTheftProbability, double basePremium, String modelType) {
		super();
		this.highTheftProbability = highTheftProbability;
		this.basePremium = basePremium;
		this.modelType = modelType;
	}
	public CarModel() {
		super();
	}
	public boolean isCarModel () {
		return true;
	}
	/**
	 * @return the basePremium
	 */
	public double getBasePremium() {
		return basePremium;
	}
	/**
	 * @param basePremium the basePremium to set
	 */
	public void setBasePremium(double basePremium) {
		this.basePremium = basePremium;
	}
	/**
	 * @return the highTheftProbability
	 */
	public boolean isHighTheftProbability() {
		return highTheftProbability;
	}
	/**
	 * @param highTheftProbability the highTheftProbability to set
	 */
	public void setHighTheftProbability(boolean highTheftProbability) {
		this.highTheftProbability = highTheftProbability;
	}
	/**
	 * @return the modelType
	 */
	public String getModelType() {
		return modelType;
	}
	/**
	 * @param modelType the modelType to set
	 */
	public void setModelType(String modelType) {
		this.modelType = modelType;
	}

	@Override
	public String toString() {
		return "CarModel[modelType=" + modelType + "]";
	}
}
