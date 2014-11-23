package ecshospital.people;

public class OrganSurgeon extends Surgeon
{

	public OrganSurgeon(char gender, int age, Health health)
	{
		super(gender, age, health);
		setSpecialism(4);
	}

	public boolean operate(Patient assignedPatient, boolean patientTreated)
	{
		int illnessID = assignedPatient.getHealth().getIllness().getIdNumber();
		if (illnessID == 7 || illnessID == 8)
		{
			assignedPatient.getHealth().setHealthState(2); //Sets patient's health state to recovering
			assignedPatient.getHealth().setRecoveryTime(assignedPatient.getHealth().generateRecoveryTime());	//Assigns a recovery time based on the min and max recovery time of their illness
			patientTreated = true;
		}
		return patientTreated;
	}
}
