/**
 * Copyright 2007 Jens Dietrich Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
 */

package test.nz.org.take.compiler.scenario9;
/**
 * Bean class referenced in tests. 
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */
public class Loan {
	private int clientRisk = 0;
	private int currencyRisk = 0;
	private int countryRisk = 0;
	public int getClientRisk() {
		return clientRisk;
	}
	public void setClientRisk(int clientRisk) {
		this.clientRisk = clientRisk;
	}
	public int getCurrencyRisk() {
		return currencyRisk;
	}
	public void setCurrencyRisk(int currencyRisk) {
		this.currencyRisk = currencyRisk;
	}
	public int getCountryRisk() {
		return countryRisk;
	}
	public void setCountryRisk(int countryRisk) {
		this.countryRisk = countryRisk;
	}

}
