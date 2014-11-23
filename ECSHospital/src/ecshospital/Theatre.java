package ecshospital;
public class Theatre
{
	private Patient patient;
	private boolean occupied;

	public Patient getPatient()
	{
		return this.patient;
	}

	public void setPatient(Patient patient)
	{
		this.patient = patient;
	}

	public boolean isOccupied()
	{
		return this.occupied;
	}

	public void setOccupied(boolean occupied)
	{
		this.occupied = occupied;
	}

	public void setTheatre(Theatre theatre)
	{
		this.setPatient(theatre.patient);
		this.setOccupied(theatre.occupied);
	}
}
