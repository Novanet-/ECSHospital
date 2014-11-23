package ecshospital.containers;
import java.util.ArrayList;

import ecshospital.attributes.Illness;
import ecshospital.people.Patient;
import ecshospital.util.TestHarness;


public class Hospital
{
	private ArrayList<Bed> beds = new ArrayList<Bed>();
	private ArrayList<Theatre> theatres = new ArrayList<Theatre>();
	private ArrayList<Illness> illnessArray = new ArrayList<Illness>();

	private final int MAX_BEDS = 50;
	private final int MAX_THEATRES = 4;

	/**
	 * Admits a patient to the hospital, by finding an unoccupied bed and
	 * setting it's patient variable to the Patient parameter, it then changes
	 * the bed.occupied variable to true.
	 * 
	 * @param Patient
	 *            The Patient to be admitted to a free bed
	 * 
	 * @return The index of the bed the patient has been placed in, if no bed
	 *         found then return -1
	 */
	public int admitPatient(Patient patient)
	{
		Bed tempBed = new Bed();
		boolean bedFound = false;
		int index = 0;
		tempBed.setPatient(patient);
		tempBed.setOccupied(true);
		while ((beds.size() < (MAX_BEDS - 1)) || (!bedFound))
		{
			if (!beds.get(index).isOccupied())
			{
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

	/**
	 * Returns the Patient at the specified bedIndex.
	 * 
	 * @param bedIndex
	 *            The position in the beds ArrayList in which to find the
	 *            Patient for
	 * 
	 * @return The patient found at the specified bedIndex
	 */
	public Patient getPatient(int bedIndex)
	{
		return (beds.get(bedIndex).getPatient());
	}

	/**
	 * Returns the number of occupied beds in the hospital.
	 * 
	 * @return The number of occupied beds in the hospital
	 */
	public int size()
	{
		int bedsOccupied = 0;
		for (int i = 0; i < MAX_BEDS; i++)
		{
			if (beds.get(i).isOccupied() == true)
				bedsOccupied++;
		}
		return bedsOccupied;
	}

	/**
	 * Returns the number of operating theatres in the hospital that are
	 * unoccupied.
	 * 
	 * @return The number of operating theatres that are free
	 */
	public int theatresFree()
	{
		int theatresFree = 0;
		for (int i = 0; i < MAX_THEATRES; i++)
		{
			if (isTheatreFree(i) == true)
			{
				theatresFree++;
			}
		}
		return theatresFree;
	}

	/**
	 * Discharges a patient from the hospital, setting their bed's occupied
	 * status to false and the Patient at that bed to null.
	 * 
	 * @param The
	 *            bed index of the patient to be discharged
	 * 
	 */
	public void dischargePatient(int bedIndex)
	{
		Bed tempBed = new Bed();
		tempBed.setOccupied(false);
		tempBed.setPatient(null);
		beds.get(bedIndex).setBed(tempBed);
	}

	/**
	 * Returns a boolean based on whether the operating theatre specified by the
	 * index parameter is occupied or not.
	 * 
	 * @param Patient
	 *            The Patient to be admitted to a free bed
	 * 
	 * @return True if theatre occupied, false is it is free
	 */
	public boolean isTheatreFree(int theatreIndex)
	{
		return !(theatres.get(theatreIndex).isOccupied());
	}

	/**
	 * Assigns a patient to a specified operating theatre.
	 * 
	 * @param theatreIndex
	 *            the theatre the patient will be asssigned to
	 * @param Patient
	 *            the patient to be assigned to an operating theatre
	 * 
	 */
	public void prepForTheatre(int theatreIndex, Patient patient)
	{
		Theatre tempTheatre = new Theatre();
		tempTheatre.setPatient(patient);
		tempTheatre.setOccupied(true);
		theatres.get(theatreIndex).setTheatre(tempTheatre);
	}

	/**
	 * Removes a patient from the specified operating theatre, setting the
	 * theatres occupied status to false and it's contained patient to null.
	 * 
	 * @param theatreIndex
	 *            The index of the operating theatre that should have it's
	 *            patient removed
	 * 
	 */
	public void takeForRecovery(int theatreIndex)
	{
		Theatre tempTheatre = new Theatre();
		tempTheatre.setOccupied(false);
		tempTheatre.setPatient(null);
		theatres.get(theatreIndex).setTheatre(tempTheatre);
	}

	public void aDayPasses()
	{

	}

	/**
	 * Fills the Beds ArrayList with the number of beds specified by the
	 * MAX_BEDS constant (default 50).
	 * 
	 */
	public void initBeds()
	{
		for (int i = 0; i < MAX_BEDS; i++)
		{
			Bed tempBed = new Bed();
			tempBed.setPatient(null);
			tempBed.setOccupied(false);
			beds.add(tempBed);
		}
	}

	/**
	 * Fills the Theatres ArrayList with the number of theatres specified by the
	 * MAX_THEATRES constant (default 4).
	 * 
	 */
	public void initTheatres()
	{
		for (int i = 0; i < MAX_THEATRES; i++)
		{
			Theatre tempTheatre = new Theatre();
			tempTheatre.setPatient(null);
			tempTheatre.setOccupied(false);
			theatres.add(tempTheatre);
		}
	}

	public void initIllnessArray()
	{
		illnessArray.add(new Illness("Djkstra’s syndrome",1, 5,5, "Any Doctor", false));
		illnessArray.add(new Illness("Java Flu ",2, 3,3, "Any Doctor", false));
		illnessArray.add(new Illness("Deadline Panic Attacks",3,1,1, "Any Doctor", false));
		illnessArray.add(new Illness("Polymorphic Cist ",4, 2,4, "Any Doctor", true));
		illnessArray.add(new Illness("Semicolon Missing ",5, 5,8, "Organ Surgeon", true));
		illnessArray.add(new Illness("Trapped Exception ",6, 6,8, "Organ Surgeon", true));
		illnessArray.add(new Illness("Tim Berners Knee ",7, 4,6, "Limb Surgeon", true));
		illnessArray.add(new Illness("Coder’s Elbow ",8, 2,3, "Limb Surgeon", true));
	}

	public static void main(String args[])
	{
		TestHarness test = new TestHarness();
		test.start();
	}

	public ArrayList<Bed> getBeds()
	{
		return beds;
	}

	public ArrayList<Theatre> getTheatres()
	{
		return theatres;
	}

	public ArrayList<Illness> getIllnessArray()
	{
		return illnessArray;
	}

	public int getMAX_BEDS()
	{
		return MAX_BEDS;
	}

	public int getMAX_THEATRES()
	{
		return MAX_THEATRES;
	}

}
