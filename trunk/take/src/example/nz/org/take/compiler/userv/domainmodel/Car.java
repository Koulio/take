/*
 * Copyright (C) 2007 <A href="http://www-ist.massey.ac.nz/JBDietrich" target="_top">Jens Dietrich</a>
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package example.nz.org.take.compiler.userv.domainmodel;

/**
 * Bean class that is part of the example domain model.
 * http://www.businessrulesforum.com/2005_Product_Derby.pdf 
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class Car {
	private boolean isConvertible = false;
	private int price = 30000;
	private boolean  hasDriversAirbag = false;
	private boolean  hasFrontPassengerAirbag = false;
	private boolean  hasSidePanelAirbags = false;
	private boolean  hasRollBar = true;
	private boolean isCompact = true;
	private boolean isSedan = false;
	private boolean isLuxury = false;
	private int age = 0;
	private int modelYear = new java.util.Date().getYear()+1900;
	
	private String  type = "unknown";
	
	public boolean hasDriversAirbag() {
		return hasDriversAirbag;
	}
	public void setHasDriversAirbag(boolean hasDriversAirbag) {
		this.hasDriversAirbag = hasDriversAirbag;
	}
	public boolean hasFrontPassengerAirbag() {
		return hasFrontPassengerAirbag;
	}
	public void setHasFrontPassengerAirbag(boolean hasFrontPassengerAirbag) {
		this.hasFrontPassengerAirbag = hasFrontPassengerAirbag;
	}
	public boolean hasRollBar() {
		return hasRollBar;
	}
	public void setHasRollBar(boolean hasRollBar) {
		this.hasRollBar = hasRollBar;
	}
	public boolean hasSidePanelAirbags() {
		return hasSidePanelAirbags;
	}
	public boolean hasAirbags() {
		return this.hasDriversAirbag || this.hasFrontPassengerAirbag || this.hasSidePanelAirbags;
	}
	public void setHasSidePanelAirbags(boolean hasSidePanelAirbags) {
		this.hasSidePanelAirbags = hasSidePanelAirbags;
	}
	public boolean isConvertible() {
		return isConvertible;
	}
	public void setConvertible(boolean isConvertible) {
		this.isConvertible = isConvertible;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public boolean isCompact() {
		return isCompact;
	}
	public void setCompact(boolean isCompactCar) {
		this.isCompact = isCompactCar;
		if (isCompact) {
			this.isLuxury = false;
			this.isSedan = false;
		}
	}
	public boolean isSedan() {
		return isSedan;
	}
	public void setSedan(boolean isSedan) {
		this.isSedan = isSedan;
		if (isSedan) {
			this.isCompact = false;
			this.isLuxury = false;
		}
	}
	public boolean isLuxury() {
		return isLuxury;
	}
	public void setLuxury(boolean isLuxuryCar) {
		this.isLuxury = isLuxuryCar;
		if (isLuxury) {
			this.isCompact = false;
			this.isSedan = false;
		}
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public int getModelYear() {
		return modelYear;
	}
	public void setModelYear(int modelYear) {
		this.modelYear = modelYear;
	}
}
