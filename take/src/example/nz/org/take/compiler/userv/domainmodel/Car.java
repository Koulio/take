package example.nz.org.take.compiler.userv.domainmodel;

public class Car {
	private boolean isConvertible = false;
	private int price = 30000;
	private boolean  hasDriversAirbag = false;
	private boolean  hasFrontPassengerAirbag = false;
	private boolean  hasSidePanelAirbags = false;
	private boolean  hasRollBar = true;
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
}
