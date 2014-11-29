package ecshospital.people;

import ecshospital.people.Health;
import ecshospital.people.Patient;
import ecshospital.people.Person;

public class Doctor extends Person
{
	private int specialism;
	private Patient assignedPatient;

	public Doctor(char gender, int age, Health health)
	{
		super(gender, age, health);
		setSpecialism(1);
		assignedPatient = null;
	}

	public void printDetails()
	{
		super.printGenderAge();
		System.out.print(this.getSpecialism());
	}

	public boolean treatPatient()
	{
		int illnessID = assignedPatient.getHealth().getIllness().getIdNumber();
		if (illnessID == 1 || illnessID == 2 || illnessID == 3)  //If illness id is in range 1-3
		{
			assignedPatient.getHealth().setHealthState(2); //Sets patient's health state to recovering
			assignedPatient.getHealth().setRecoveryTime(assignedPatient.getHealth().generateRecoveryTime());	//Assigns a recovery time based on the min and max recovery time of their illness
			return true;
		}
		else
			return false;
	}

	public boolean aDayPasses()
	{
		return treatPatient();
	}

	public int getSpecialism()
	{
		return specialism;
	}

	public Patient getAssignedPatient()
	{
		return assignedPatient;
	}

	/**
	 * @param specialism
	 *            the specialism to set
	 */
	public void setSpecialism(int specialism)
	{
		this.specialism = specialism;
	}

	public void assignPatient(Patient assignedPatient)
	{
		this.assignedPatient = assignedPatient;
	}
}
