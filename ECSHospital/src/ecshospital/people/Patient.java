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
		if (this.getHealth().getHealthState() == 2)
		{
			this.getHealth().setRecoveryTime(this.getHealth().getRecoveryTime() - 1);
		}
		if (this.getHealth().getRecoveryTime() == 0)
		{
			this.getHealth().setHealthState(0);
		}
		return false;
	}
}
