package ecshospital.people;

public class Doctor extends Person
{
	public Doctor(char gender, int age, Health health)
	{
		super(gender, age, health);
	}

	private int specialism;
	private Patient assignedPatient;
	
	public int getSpecialism()
	{
		return specialism;
	}
	
	
	public Patient getAssignedPatient()
	{
		return assignedPatient;
	}
	
	/**
	 * @param specialism the specialism to set
	 */
	public void setSpecialism(int specialism)
	{
		this.specialism = specialism;
	}

	public void assignPatient(Patient assignedPatient)
	{
		this.assignedPatient = assignedPatient;
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
		return false;
	}
}
