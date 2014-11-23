package ecshospital.people;

public class Surgeon extends Doctor
{

	public Surgeon(char gender, int age, Health health)
	{
		super(gender, age, health);
	}


	public boolean treatPatient()
	{
		boolean patientTreated = super.treatPatient();
		Patient assignedPatient = super.getAssignedPatient();
		if (!patientTreated)
		{
			operate(assignedPatient, patientTreated);
		}
		return patientTreated;
	}
	
	public boolean operate(Patient assignedPatient, boolean patientTreated)
	{
		int illnessID = assignedPatient.getHealth().getIllness().getIdNumber();
		if (illnessID == 4)
		{
			assignedPatient.getHealth().setHealthState(2); //Sets patient's health state to recovering
			assignedPatient.getHealth().setRecoveryTime(assignedPatient.getHealth().generateRecoveryTime());	//Assigns a recovery time based on the min and max recovery time of their illness
			patientTreated = true;
		}
		return patientTreated;
	}
	
	

}
