package ecshospital.people;


public class Patient extends Person
{

	public Patient(char gender, int age, Health health)
	{
		super(gender, age, health);
	}
	
	public void printDetails()
	{
		super.printDetails();
	}

	public boolean aDayPasses()
	{
		return false;
	}

}
