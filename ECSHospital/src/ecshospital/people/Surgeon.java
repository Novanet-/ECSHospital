package ecshospital.people;

public class Surgeon extends Doctor
{

	public Surgeon(char gender, int age, Health health)
	{
		super(gender, age, health);
		setSpecialism(2);
	}

	public boolean treatPatient()
	{
		boolean patientTreated = super.treatPatient();
		Patient assignedPatient = super.getAssignedPatient();
		if (!patientTreated && (assignedPatient != null))
		{
			operate(assignedPatient, patientTreated);
		}
		return patientTreated;
	}

	public boolean operate(Patient assignedPatient, boolean patientTreated)	//Doesn't need check to see if patient is in an operating theatre
																			//As if patient had illness that need an operation
																			//They must have been assigned a theatre because of the way I built aDayPasses in HospitalAdministrator
	{
		int illnessID = assignedPatient.getHealth().getIllness().getIdNumber();
		if (illnessID > -1)		//Doesn't need to see if patient is in theatre as this is handled by HospitalAdministratior
		{
			assignedPatient.getHealth().setHealthState(2); //Sets patient's health state to recovering
			assignedPatient.getHealth().setRecoveryTime(assignedPatient.getHealth().generateRecoveryTime());	//Assigns a recovery time based on the min and max recovery time of their illness
			patientTreated = true;
		}
		return patientTreated;
	}

}
