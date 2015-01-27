package ecshospital.people;

import java.util.ArrayList;
import java.util.HashSet;

import ecshospital.attributes.Illness;
import ecshospital.containers.Bed;
import ecshospital.containers.Hospital;
import ecshospital.containers.Theatre;
import ecshospital.util.Parser;

public class HospitalAdministrator
{
	Hospital hospital = new Hospital();
	private ArrayList<Doctor> doctors = new ArrayList<Doctor>();
	private ArrayList<Patient> waitingPatients = new ArrayList<Patient>();
	private ArrayList<Patient> assignedPatients = new ArrayList<Patient>();
	private ArrayList<Integer> dischargedBeds = new ArrayList<Integer>();
	private Parser parser = new Parser();

	/**
	 * Reads the config file and parses the data within, using it to populate
	 * the hospital with patients, doctors, beds, operating theatres, and
	 * illnesses.
	 * 
	 * @throws Exception
	 * 
	 */
	public void initSimulation() throws Exception
	{
		ArrayList<String> fileArray = parser.readConfigFile();	//Reads the hospital's config file into an ArrayList
		boolean illnessesFound = false;
		for (int i = 0; i < fileArray.size(); i++)	//For each line of the file
		{
			ArrayList<String> line = parser.parseConfigFile(fileArray.get(i));	//Parses each element of the line into separate array elements
			if (line.get(0).equals("patient"))	//If line defines a patient then create a patient with the information supplied
			{
				if (!illnessesFound)
				{
					hospital.getIllnessArray().add(new Illness("Djkstra’s syndrome", 1, 5, 5, "Any Doctor", false));
					hospital.getIllnessArray().add(new Illness("Java Flu ", 2, 3, 3, "Any Doctor", false));
					hospital.getIllnessArray().add(new Illness("Deadline Panic Attacks", 3, 1, 1, "Any Doctor", false));
					hospital.getIllnessArray().add(new Illness("Polymorphic Cist ", 4, 2, 4, "Any Doctor", true));
					hospital.getIllnessArray().add(new Illness("Semicolon Missing ", 5, 5, 8, "Organ Surgeon", true));
					hospital.getIllnessArray().add(new Illness("Trapped Exception ", 6, 6, 8, "Organ Surgeon", true));
					hospital.getIllnessArray().add(new Illness("Tim Berners Knee ", 7, 4, 6, "Limb Surgeon", true));
					hospital.getIllnessArray().add(new Illness("Coder’s Elbow ", 8, 2, 3, "Limb Surgeon", true));
					illnessesFound = true;
				}
				Illness tempIllness = null;
				int healthState = 0;
				int recoveryTime = -1;
				if (line.get(3).equals("0"))
				{
					if (Integer.parseInt(line.get(4)) > -1)
					{
						healthState = 2;
						recoveryTime = Integer.parseInt(line.get(4));
					}
					else
					{
						healthState = 0;
					}
				}
				else
				{
					tempIllness = hospital.getIllnessArray().get((Integer.parseInt(line.get(3))) - 1);
					healthState = 1;
				}
				Health tempHealth = new Health(healthState, recoveryTime, tempIllness);
				Patient tempPatient = new Patient(line.get(1).charAt(0), Integer.parseInt(line.get(2)), tempHealth);
				waitingPatients.add(tempPatient);
			}
			else if (line.get(0).equals("doctor"))	//If line defines a doctor then create a doctor with the information supplied
			{
				Health tempHealth = new Health(0, -1, null);
				Doctor tempDoctor = new Doctor(line.get(1).charAt(0), Integer.parseInt(line.get(2)), tempHealth);
				doctors.add(tempDoctor);
			}
			else if (line.get(0).equals("surgeon"))	//If line defines a surgeon then create a surgeon with the information supplied
			{
				Health tempHealth = new Health(0, -1, null);
				Doctor tempDoctor = new Surgeon(line.get(1).charAt(0), Integer.parseInt(line.get(2)), tempHealth);
				doctors.add(tempDoctor);
			}
			else if (line.get(0).equals("limbSurgeon"))	//If line defines a limb surgeon then create a limb surgeon with the information supplied
			{
				Health tempHealth = new Health(0, -1, null);
				Doctor tempDoctor = new LimbSurgeon(line.get(1).charAt(0), Integer.parseInt(line.get(2)), tempHealth);
				doctors.add(tempDoctor);
			}
			else if (line.get(0).equals("organSurgeon"))	//If line defines a organ surgeon then create a organ surgeon with the information supplied
			{
				Health tempHealth = new Health(0, -1, null);
				Doctor tempDoctor = new OrganSurgeon(line.get(1).charAt(0), Integer.parseInt(line.get(2)), tempHealth);
				doctors.add(tempDoctor);
			}
			else if (line.get(0).equals("hospital"))	//If line defines a hospital then create beds and theatres with the information supplied
			{
				hospital.setMaxBeds(Integer.parseInt(line.get(1)));
				hospital.setMaxTheatres(Integer.parseInt(line.get(2)));
				hospital.initBeds(hospital.getMaxBeds());
				hospital.initTheatres(hospital.getMaxTheatres());
			}
			else if (line.get(0).equals("illness"))	//If line defines a illness then create a illness with the information supplied
			{
				Illness tempIllness = new Illness(null, 0, 0, 0, null, false);
				tempIllness.setIllnessName(line.get(1));
				tempIllness.setIdNumber(Integer.parseInt(line.get(2)));
				tempIllness.setMinRecoveryTime(Integer.parseInt(line.get(3)));
				tempIllness.setMaxRecoveryTime(Integer.parseInt(line.get(4)));
				tempIllness.setCanBeTreatedBy(line.get(5));
				tempIllness.setRequiresTheatres(Boolean.parseBoolean(line.get(6)));
				hospital.getIllnessArray().add(tempIllness);
			}
			else
				throw new Exception("Invalid config file");
		}

		System.out.println("Day 0");							//Displays the state of the doctors and patients before a day has passed
		System.out.println("Patients : ");
		for (int i = 0; i < this.getHospital().size(); i++)
		{
			if (this.getHospital().getBeds().get(i).getPatient() != null)
			{
				this.getHospital().getBeds().get(i).getPatient().printGenderAge();
				this.getHospital().getBeds().get(i).getPatient().printHealth();
				System.out.println();
			}
		}
		System.out.println("Doctors : ");
		for (int i = 0; i < this.getDoctors().size(); i++)
		{
			this.getDoctors().get(i).printDetails();
			System.out.println();
		}
		System.out.println();

	}

	/**
	 * Assigns patients to doctors using a cascading mechanism. If no patients
	 * are found that need a high level specialism, the doctor is temporarily
	 * dropped to a lower speciality and a patient is attempted to be found for
	 * that specialism.
	 * 
	 * If a relevant patient is found that hasn't already been assigned to a
	 * doctor, try to find a free theatre if the treatment is a surgery, and if
	 * that is successful then assign the patient to the doctor, if non-surgical
	 * treatment then just assign the patient to the doctor
	 * 
	 * @throws Exception
	 * 
	 */

	public void assignDoctors()
	{
		HashSet<Patient> assignedPatients = new HashSet<Patient>(); //HashSet containing assigned patients
		for (Doctor d : doctors) //Iterate through doctors
		{
			int tempSpec = d.getSpecialism();
			boolean patientFound = false;
			int i = 0;

			if (d.getSpecialism() == 4) //If OrganSurgeon
			{
				while ((i < hospital.size()) && (!patientFound)) //Iterates through occupied beds until patient is found
				{
					Patient tempPatient = hospital.getBeds().get(i).getPatient();
					if (tempPatient.getHealth().getHealthState() == 1)
					{
						int illnessID = tempPatient.getHealth().getIllness().getIdNumber();
						if ((illnessID == 5 || illnessID == 6) && (!assignedPatients.contains(tempPatient))) //If a LimbSurgeon illness and patient not already been assigned
						{
							patientFound = true;
							try
							{
								findFreeTheatre(tempPatient, d);
							}
							catch (Exception e)
							{
								System.out.println(e.getMessage());
								e.printStackTrace();
								patientFound = false;
								d.setSpecialism(1);		//If no free theatres, temporarily make the doctor only try to treat non-surgical illness
							}
						}
					}
					i++;
				}
				if ((!patientFound) && (!(i < hospital.size())))	//If patient not found, but unchecked patients left, temporarily reduce specialism of doctor to help find a match
					d.setSpecialism(2);
			}

			i = 0;

			if (d.getSpecialism() == 3) //If LimbSurgeon
			{
				while ((i < hospital.size()) && (!patientFound)) //Iterates through occupied beds until patient is found
				{
					Patient tempPatient = hospital.getBeds().get(i).getPatient();
					if (tempPatient.getHealth().getHealthState() == 1)
					{
						int illnessID = tempPatient.getHealth().getIllness().getIdNumber();
						if ((illnessID == 7 || illnessID == 8) && (!assignedPatients.contains(tempPatient))) //If an OrganSurgeon illness and patient not already been assigned
						{
							patientFound = true;
							try
							{
								findFreeTheatre(tempPatient, d);
							}
							catch (Exception e)
							{
								System.out.println(e.getMessage());
								e.printStackTrace();
								patientFound = false;
								d.setSpecialism(1);	//If no free theatres, temporarily make the doctor only try to treat non-surgical illness
							}
						}
					}
					i++;
				}
				if ((!patientFound) && (!(i < hospital.size())))	//If patient not found, but unchecked patients left, temporarily reduce specialism of doctor to help find a match
					d.setSpecialism(2);
			}

			i = 0;

			if (d.getSpecialism() == 2) //If basic Surgeon
			{
				while ((i < hospital.size()) && (!patientFound)) //Iterates through occupied beds until patient is found
				{
					Patient tempPatient = hospital.getBeds().get(i).getPatient();
					if (tempPatient.getHealth().getHealthState() == 1)
					{
						int illnessID = tempPatient.getHealth().getIllness().getIdNumber();
						if ((illnessID == 4) && (!assignedPatients.contains(tempPatient))) //If a basic Surgeon illness and patient not already been assigned
						{
							patientFound = true;
							try
							{
								findFreeTheatre(tempPatient, d);
							}
							catch (Exception e)
							{
								System.out.println(e.getMessage());
								e.printStackTrace();
								patientFound = false;
								d.setSpecialism(1);	//If no free theatres, temporarily make the doctor only try to treat non-surgical illness
							}
						}
					}
					i++;
				}
				if ((!patientFound) && (!(i < hospital.size())))	//If patient not found, but unchecked patients left, temporarily reduce specialism of doctor to help find a match
					d.setSpecialism(1);
			}

			i = 0;

			if (d.getSpecialism() == 1) //If basic Doctor
			{
				while ((i < hospital.size()) && (!patientFound)) //Iterates through occupied beds until patient is found
				{
					Patient tempPatient = hospital.getBeds().get(i).getPatient();
					if (tempPatient.getHealth().getHealthState() == 1)
					{
						int illnessID = tempPatient.getHealth().getIllness().getIdNumber();
						if ((illnessID < 4) && (!assignedPatients.contains(tempPatient))) //If a basic Doctor illness and patient not already been assigned
						{
							patientFound = true;
							d.assignPatient(tempPatient); //Assigns patient to doctor
							assignedPatients.add(tempPatient); //Marks patient as being already assigned
						}
					}
					i++;
				}
			}

			d.setSpecialism(tempSpec);	//Reverts doctor back to their original specialism
		}
	}

	/**
	 * Attempts to find a free operating theatre so that the specified doctor
	 * can operate on the specified patient
	 * 
	 * @throws Exception
	 * 
	 */
	public boolean findFreeTheatre(Patient tempPatient, Doctor d) throws Exception
	{
		int i = 0;
		boolean theatreFound = false;
		while ((!theatreFound) && (i < hospital.getMaxTheatres())) //Iterates through the operating theatres until a free one found
		{
			if (hospital.isTheatreFree(i))
			{
				theatreFound = true;
				d.assignPatient(tempPatient); 	//Assigns patient to doctor
				assignedPatients.add(tempPatient);	//Marks patient as being already assigned
				hospital.prepForTheatre(i, tempPatient);	//Assigns patient to operating theatre
			}
			i++;
		}
		if (theatreFound)
			return theatreFound;
		else
			throw new Exception("No free theatre");

	}

	/**
	 * The main body of the simulation, causes all the elements such as doctors
	 * and patients to perform their daily tasks, such as treating patients or
	 * being discharged.
	 * 
	 * 
	 * @return Whether the method completed succesfully
	 * 
	 */
	public boolean aDayPasses()
	{
		for (Patient p : waitingPatients) //Admits all waiting patients
		{
			int bedFound = -1;
			if (p != null)	//If patient is valid
				try
				{
					bedFound = hospital.admitPatient(p);	//Try and admit patient to a bed
				}
				catch (Exception e)
				{
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
			if (bedFound != -1)	//If bed was found, add the patient to an admitted patients list
				assignedPatients.add(p);

		}
		for (Patient p : assignedPatients) //Removes assigned patients from waiting patients list
		{
			if (waitingPatients.contains(p))
				waitingPatients.remove(waitingPatients.indexOf(p));
		}
		assignedPatients.clear();
		this.assignDoctors(); ///Assigns patients to doctors
		for (Doctor d : doctors) //Doctors treat their patients
		{
			d.aDayPasses();
		}
		assignedPatients.clear();
		int i = 0;
		for (Theatre t : hospital.getTheatres()) //Remove patients from operating theatres
		{
			if (t.isOccupied())
			{
				hospital.takeForRecovery(i);
				i++;
			}
		}
		i = 0;
		for (Bed b : hospital.getBeds()) //Discharge any healthy patients and lets patients recover
		{
			if (!(i < hospital.size()))	//Stop iterating when the number off occuipied beds has been reached
			{
				break;
			}
			if (b.isOccupied())
			{
				b.getPatient().aDayPasses();
				if (b.getPatient().getHealth().getHealthState() == 0)	//iF patient is healthy
				{
					dischargedBeds.add(i);	//Add them to a "waiting to be discharged" list
				}
			}
			i++;
		}
		for (Integer iB : dischargedBeds)
		{
			hospital.dischargePatient(iB);	//Discharges all patients that are waiting to be discharged
		}
		dischargedBeds.clear();
		return true;
	}

	/**
	 * Prints out the status of the hospital at the current day.
	 * 
	 * @param admin
	 *            The HospitalAdminsitrator obejct managing the simulation
	 * 
	 * 
	 */
	public void printDayResults(HospitalAdministrator admin)
	{
		System.out.println("Patients : ");						//Prints out the details of the current patients in the hospital
		for (int i = 0; i < admin.getHospital().size(); i++)
		{
			if (admin.getHospital().getBeds().get(i).getPatient() != null)
			{
				admin.getHospital().getBeds().get(i).getPatient().printGenderAge();
				admin.getHospital().getBeds().get(i).getPatient().printHealth();
				System.out.println();
			}
		}
		System.out.println("Doctors : ");						//Prints out the details of the current doctors in the hospital
		for (int i = 0; i < admin.getDoctors().size(); i++)
		{
			admin.getDoctors().get(i).printDetails();
			System.out.println();
		}
		System.out.println();
	}

	/**
	 * Starts the simulation, initialises the hospital and then runs the
	 * simulation for 7 days
	 * 
	 */
	public void startSimulation()
	{
		HospitalAdministrator admin = new HospitalAdministrator();
		try
		{
			admin.initSimulation();		//Initialises the simulation by populating it with various objects
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
			return;
		}
		System.out.println("Max Beds = " + admin.hospital.getMaxBeds());
		System.out.println("Max Theatres = " + admin.hospital.getMaxTheatres());
		System.out.println("");
		for (int i = 0; i < 7; i++)
		{
			System.out.println("Day " + (i + 1));
			admin.aDayPasses();
			admin.printDayResults(admin);
			try
			{
				Thread.sleep(1000);
			}
			catch (InterruptedException e)
			{
				System.out.println("Sleep interrupted");
				e.printStackTrace();
			}

		}
	}

	/**
	 * Accepts any arguments the program has been started with, then starts the
	 * simulation.
	 * 
	 * @param args
	 *            Contains any arguments added to the program's launch command
	 * 
	 */
	public static void main(String args[])
	{
		HospitalAdministrator admin = new HospitalAdministrator();
		if (!(args == null))			//If there was an argument when program was run
		{
			for (String s : args)
				admin.parser.setConfigPath(s);	//Set the config file path as the argument
		}
		admin.startSimulation();	//Start the simulation
	}

	/**
	 * @return the hospital
	 */
	public Hospital getHospital()
	{
		return hospital;
	}

	/**
	 * @return the doctors
	 */
	public ArrayList<Doctor> getDoctors()
	{
		return doctors;
	}

	/**
	 * @return the parser
	 */
	public Parser getParser()
	{
		return parser;
	}

	/**
	 * @param hospital
	 *            the hospital to set
	 */
	public void setHospital(Hospital hospital)
	{
		this.hospital = hospital;
	}

	/**
	 * @param doctors
	 *            the doctors to set
	 */
	public void setDoctors(ArrayList<Doctor> doctors)
	{
		this.doctors = doctors;
	}

	/**
	 * @param parser
	 *            the parser to set
	 */
	public void setParser(Parser parser)
	{
		this.parser = parser;
	}

}
