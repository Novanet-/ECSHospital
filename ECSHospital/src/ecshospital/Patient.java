package ecshospital;
public class Patient extends Person
{
	private String name;

	public Patient(char gender, int age, Health health, String name)
	{
		super(gender, age, health);
		this.name = name;
	}
	
	public void printDetails()
	{
		super.printDetails();
		System.out.println(this.name);
	}

	public boolean aDayPasses()
	{
		return false;
	}

	public String getName()
	{
		return this.name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

}
