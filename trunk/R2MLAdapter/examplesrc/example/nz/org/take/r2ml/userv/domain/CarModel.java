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
}
