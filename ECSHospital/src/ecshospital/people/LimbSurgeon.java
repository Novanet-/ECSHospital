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
		return super.operate(assignedPatient, patientTreated);
	}
}
