package example.nz.org.take.r2ml.userv.domain;

import java.util.ArrayList;
import java.util.List;

public class Policy {

	Car covers = null;

	Double premium = 0.0;

	Integer vehiclePolicyEligibilityScore = 0;

	public Car getCovers() {
		return covers;
	}

	public void setCovers(Car covers) {
		this.covers = covers;
	}

	public Double getPremium() {
		return premium;
	}

	public void setPremium(Double premium) {
		this.premium = premium;
	}

	public Integer getVehiclePolicyEligibilityScore() {
		return vehiclePolicyEligibilityScore;
	}

	public void setVehiclePolicyEligibilityScore(
			Integer vehiclePolicyEligibilityScore) {
		this.vehiclePolicyEligibilityScore = vehiclePolicyEligibilityScore;
	}

}
