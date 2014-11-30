package ecshospital.people;

public class LimbSurgeon extends Surgeon
{

	public LimbSurgeon(char gender, int age, Health health)
	{
		super(gender, age, health);
		setSpecialism(3);
	}

	public boolean operate(Patient assignedPatient, boolean patientTreated)
	{
		int illnessID = assignedPatient.getHealth().getIllness().getIdNumber();
		if (illnessID > -1)
		{
			assignedPatient.getHealth().setHealthState(2); //Sets patient's health state to recovering
			assignedPatient.getHealth().setRecoveryTime(assignedPatient.getHealth().generateRecoveryTime());	//Assigns a recovery time based on the min and max recovery time of their illness
			this.assignedPatient = null;
			patientTreated = true;
		}
		return patientTreated;
	}
}
