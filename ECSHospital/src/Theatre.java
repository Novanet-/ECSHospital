
public class Theatre {
	Patient patient;
	boolean occupied;
	
	
	public Patient getPatient() {
		return this.patient;
	}
	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	public boolean isOccupied() {
		return this.occupied;
	}
	public void setOccupied(boolean occupied) {
		this.occupied = occupied;
	}
	
	public void setTheatre(Theatre theatre){
		this.patient = theatre.patient;
		this.occupied = theatre.occupied;
	}
	
}
