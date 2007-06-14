package test.nz.org.take.r2ml.c;

public class Person {
	
	String firstname;
	String lastname;
	
	Person(String fName, String lName) {
		firstname = fName;
		lastname = lName;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
}
