package ecshospital.util;

import ecshospital.attributes.Illness;
import ecshospital.containers.Hospital;
import ecshospital.people.Health;
import ecshospital.people.Patient;

public class TestHarness
{
	public void start()
	{
		Hospital hospital = new Hospital();
		hospital.initBeds();
		hospital.initTheatres();
		hospital.initIllnessArray();

		char gender;
		for (int i = 0; i < 8; i++)
		{
			if (i % 2 == 0)												//Alternates the the gender of each patient being added to the array list
			{
				gender = 'M';
			} else
			{
				gender = 'F';
			}

			Illness tempIllness = hospital.getIllnessArray().get(i);
			Health tempHealth = new Health(0, 0, tempIllness);
			hospital.admitPatient(new Patient(gender, (20 + i), tempHealth, i + " Smith"));
		}
		System.out.println("Beds in hospital = " + hospital.getBeds().size());		//Total number of beds in the hospital
		System.out.println("Beds occupied = " + hospital.size());					//Beds occupied
		System.out.println("");
		System.out.println("First occupied bed patient = ");						//The details of the patient in the first occupied bed
		hospital.getPatient(0).printDetails();
		System.out.println("");
		System.out.println("Last  occupied bed patient = ");						//The details of the patient in the last occupied bed
		hospital.getPatient(hospital.size() - 1).printDetails();
		System.out.println("");
		System.out.println("********");
		for (int i = 0; i < hospital.getMAX_THEATRES(); i++)
		{
			if (i % 2 == 0 || i == 3)
				hospital.prepForTheatre(i, hospital.getPatient(i));
		}

		System.out.println("Theatres in hospital = " + hospital.getTheatres().size());		//Total number of theatres in the hospital
		System.out.println("Theatres occupied = " + hospital.theatresFree());				//Theatres occupied
		System.out.println("First theatre patient = " + hospital.getPatient(0).getName());	//The details of the patient in the first occupied theatre
		System.out.println("Last theatre patient = "
				+ hospital.getPatient(hospital.getTheatres().size() - 1).getName());		//The details of the patient in the last occupied theatre
		System.out.println("********");

		for (int i = 0; i < hospital.getMAX_THEATRES(); i++)								//Removes patients from theatres
		{
			if (!hospital.isTheatreFree(i))
				hospital.takeForRecovery(i);
		}

		System.out.println("Theatres in hospital = " + hospital.getTheatres().size());				//Total number of theatres in the hospital
		System.out.println("Theatres occupied = "
				+ (hospital.getMAX_THEATRES() - (hospital.theatresFree() ) ) );						//Theatres occupied
		System.out.println("First theatre patient = " + hospital.getTheatres().get(0).isOccupied());//The details of the patient in the first occupied theatre
		System.out.println("Last theatre patient = "
				+ hospital.getTheatres().get(hospital.getMAX_THEATRES() - 1).isOccupied());			//The details of the patient in the last occupied theatre
		System.out.println("********");
	}
}
