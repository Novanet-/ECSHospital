import java.util.ArrayList;


public class Hospital {
	ArrayList<Bed> beds = new ArrayList<Bed>();
	ArrayList<Theatre> theatres = new ArrayList<Theatre>();
	final int MAX_BEDS = 50;
	final int MAX_THEATRES = 4;
	
	public int admitPatient(Patient patient){
		Bed tempBed = new Bed();
		boolean bedFound = false;
		int index = 0;
		tempBed.setPatient(patient);
		tempBed.setOccupied(true);
		while ((beds.size() < (MAX_BEDS - 1)) || (!bedFound)) {			
			if (!beds.get(index).isOccupied()){
				beds.get(index).setBed(tempBed);
				bedFound = true;
			}
			index++;
		}
		if (bedFound)
			return index;
		else
			return -1;		
	}
	
	public Patient getPatient(int bedIndex){
		return (beds.get(bedIndex).getPatient());		
	}
	
	public int size(){
		int bedsOccupied = 0;
		for (int i = 0; i < MAX_BEDS; i++){
			if (beds.get(i).isOccupied() == true)
				bedsOccupied++;
		}
		return bedsOccupied;		
	}
	
	public int theatresFree(){
		int theatresFree = 0;
		for (int i = 0; i < MAX_THEATRES; i++){
			if (isTheatreFree(i) == true){
				theatresFree++;
			}
		}
		return theatresFree;
	}
	
	public void dischargePatient(int bedIndex){
		
	}
	
	public boolean isTheatreFree(int theatreIndex){
		return (theatres.get(theatreIndex).isOccupied());		
	}
	
	public void prepForTheatre(int theatreIndex, Patient patient){
		Theatre tempTheatre = new Theatre();
		tempTheatre.setPatient(patient);
		tempTheatre.setOccupied(true);
		theatres.add(theatreIndex, tempTheatre);
	}
	
	public void takeForRecovery(int theatreIndex){
		theatres.get(theatreIndex).setOccupied(false);
		theatres.get(theatreIndex).setPatient(null);
	}
	
	public void aDayPasses(){
		
	}
	
	public void initBeds(){
		for (int i = 0; i < MAX_BEDS; i++){
			Bed tempBed =  new Bed();
			admitPatient(null);
			tempBed.setPatient(null);
			tempBed.setOccupied(false);
			beds.add(tempBed);
		}
	}
	
	public void initTheatres(){
		for (int i = 0; i < MAX_THEATRES; i++){
			Theatre tempTheatre = new Theatre();
			tempTheatre.setPatient(null);
			tempTheatre.setOccupied(false);
			theatres.add(tempTheatre);
		}
	}
	
	public static void main(String args[]){
		TestHarness test = new TestHarness();
		test.start();
	}
	
}
