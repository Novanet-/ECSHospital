
public abstract class Person {
	char gender;
	int age;
	String health;
	
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