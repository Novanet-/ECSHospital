
public class TestHarness {
	public void start(){
		Hospital hospital = new Hospital();
		hospital.initBeds();
		hospital.initTheatres();
		for(int i = 0; i < hospital.MAX_BEDS; i++){
			if (i % 2 == 0 || i == 49)
				hospital.admitPatient(new Patient(i + " Smith"));
		}
		System.out.println("Beds in hospital = "+hospital.beds.size());
		System.out.println("Beds occupied = "+hospital.size());
		System.out.println("First bed patient = "+hospital.getPatient(0).getName());
		System.out.println("Last  bed patient = "+hospital.getPatient(hospital.beds.size()-1).getName());
		System.out.println("********");
		for(int i = 0; i < hospital.MAX_THEATRES; i++){
			if (i % 2 == 0 || i == 3)
				hospital.prepForTheatre(i, hospital.getPatient(i));
		}
		
		System.out.println("Theatres in hospital = "+hospital.theatres.size());
		System.out.println("Theatres occupied = "+hospital.theatresFree());
		System.out.println("First theatre patient = "+hospital.getPatient(0).getName());
		System.out.println("Last theatre patient = "+hospital.getPatient(hospital.theatres.size()-1).getName());
	}
}
