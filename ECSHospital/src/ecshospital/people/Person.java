package ecshospital.people;

public abstract class Person
{
	private char gender;
	private int age;
	private Health health;

	public Person(char gender, int age, Health health)
	{
		this.gender = gender;
		this.age = age;
		this.health = health;
	}

	public abstract boolean aDayPasses();

	public void printDetails()
	{
		System.out.println(this.gender);
		System.out.println(this.age);
		System.out.println(this.health.getHealthState());
		System.out.println(this.health.getRecoveryTime());
		System.out.println(this.health.getIllness().getIllnessName());
	}

	public void printGenderAge()
	{
		System.out.print(this.gender + " , ");
		System.out.print(this.age + " , ");
	}

	public void printHealth()
	{
		System.out.print(this.getHealth().getHealthState() + " , ");
		if (this.getHealth().getHealthState() == 1)
			System.out.print(this.getHealth().getIllness().getIdNumber() + " , ");
		else
			System.out.print(-1 + " , ");
		System.out.print(this.getHealth().getRecoveryTime());
	}

	public char getGender()
	{
		return gender;
	}

	public int getAge()
	{
		return age;
	}

	public Health getHealth()
	{
		return health;
	}

	public void setAge(int age)
	{
		this.age = age;
	}

}