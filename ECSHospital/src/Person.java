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