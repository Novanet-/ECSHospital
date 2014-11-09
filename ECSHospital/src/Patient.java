
public class Patient extends Person {
	String name;
	

	public Patient(String name) {
		super();
		this.name = name;
	}

	public boolean aDayPasses() {
		return false;
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
