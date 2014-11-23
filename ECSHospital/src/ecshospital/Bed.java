package ecshospital;
public class Bed
{
	private Patient patient;
	private boolean occupied;

	public Patient getPatient()
	{
		return this.patient;
	}

	public boolean isOccupied()
	{
		return occupied;
	}

	public void setPatient(Patient patient)
	{
		this.patient = patient;
	}

	public void setOccupied(boolean occupied)
	{
		this.occupied = occupied;
	}

	public void setBed(Bed bed)
	{
		this.setPatient(bed.patient);
		this.setOccupied(bed.occupied);
	}

}
