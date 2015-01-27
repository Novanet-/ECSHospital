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
		return super.operate(assignedPatient, patientTreated);
	}
}
