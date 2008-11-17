package test.nz.org.take.compiler.issue16;

public class Person extends Bean {

	private Company company;

	public Person(String name) {
		super(name);
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
}
