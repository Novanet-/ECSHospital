
public abstract class Person {
	private char gender;
	private int age;
	private String health;
	
	public Person(){
		this.gender = 'F';
		this.age = 20;
		this.health = "healthly";
	}
	
	public abstract boolean aDayPasses();
	
	public char getGender() {
		return gender;
	}

	public int getAge() {
		return age;
	}

	public String getHealth() {
		return health;
	}

	public void setAge(int age) {
		this.age = age;
	}
}