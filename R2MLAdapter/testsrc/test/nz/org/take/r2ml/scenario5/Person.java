package test.nz.org.take.r2ml.scenario5;

public class Person {

	private String name = "";
	private Job job = null;
	
	public Person(String name, Job job) {
		this.name = name;
		this.job = job;
	}

	/**
	 * @return the job
	 */
	public Job getJob() {
		return job;
	}

	/**
	 * @param job the job to set
	 */
	public void setJob(Job job) {
		this.job = job;
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
	
	

}
