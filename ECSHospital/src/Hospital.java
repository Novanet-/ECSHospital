import java.util.ArrayList;


public class Hospital {
	ArrayList<Bed> beds = new ArrayList<Bed>();
	int MAX_BEDS = 50;
	
	public int admitPatient(Patient patient){
		Bed tempBed = new Bed();
		tempBed.setPatient(patient);
		tempBed.setOccupied(true);
		if (beds.size() < MAX_BEDS){
			beds.add(tempBed);
			return beds.indexOf(tempBed);
		}
		else
			return -1;		
	}
	
	public Patient getPatient(int bedIndex){
		return null;
		
	}
	
	public int size(){
		return 0;
		
	}
	
	public void dischargePatient(int bedIndex){
		
	}
	
	public boolean isTheatreFree(int theatreIndex){
		return false;
		
	}
	
	public void prepForTheatre(int theatreIndex, Patient patient){
		
	}
	
	public void takeForRecovery(int theatreIndex){
		
	}
	
	public void aDayPasses(){
		
	}
	
}
