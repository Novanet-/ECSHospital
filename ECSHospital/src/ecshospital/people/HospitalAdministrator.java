package ecshospital.people;

import java.util.ArrayList;
import java.util.HashSet;

import ecshospital.attributes.Illness;
import ecshospital.containers.Bed;
import ecshospital.containers.Hospital;
import ecshospital.containers.Theatre;
import ecshospital.people.Doctor;
import ecshospital.people.Health;
import ecshospital.people.LimbSurgeon;
import ecshospital.people.OrganSurgeon;
import ecshospital.people.Patient;
import ecshospital.util.Parser;
import ecshospital.util.TestHarness;

public class HospitalAdministrator
{
	Hospital hospital = new Hospital();
	HospitalAdministrator admin;
	private ArrayList<Doctor> doctors = new ArrayList<Doctor>();
	private ArrayList<Patient> waitingPatients = new ArrayList<Patient>();
	private Parser parser = new Parser();

	/**
	 * Reads the config file and parses the data within, using it to populate
	 * the hospital with patients, doctors, beds and operating theatres.
	 * 
	 */
	public void initSimulation()
	{
		ArrayList<String> fileArray = parser.readConfigFile();
		for (int i = 0; i < fileArray.size(); i++)
		{
			ArrayList<String> line = parser.parseConfigFile(fileArray.get(i));
			if (line.get(0).equals("patient"))
			{
				Illness tempIllness = null;
				int healthState = 0;
				if (line.get(3).equals("0"))
				{
					if (Integer.parseInt(line.get(4)) > -1)
					{
						healthState = 2;
					} else
					{
						healthState = 0;
					}
				} else
				{
					tempIllness = hospital.getIllnessArray().get((Integer.parseInt(line.get(3))) - 1);
					healthState = 1;
				}
				Health tempHealth = new Health(healthState, -1, tempIllness);
				Patient tempPatient = new Patient(line.get(1).charAt(0), Integer.parseInt(line.get(2)), tempHealth);
				waitingPatients.add(tempPatient);
			} else if (line.get(0).equals("doctor"))
			{
				Health tempHealth = new Health(0, -1, null);
				Doctor tempDoctor = new Doctor(line.get(1).charAt(0), Integer.parseInt(line.get(2)), tempHealth);
				doctors.add(tempDoctor);
			} else if (line.get(0).equals("limbSurgeon"))
			{
				Health tempHealth = new Health(0, -1, null);
				Doctor tempDoctor = new LimbSurgeon(line.get(1).charAt(0), Integer.parseInt(line.get(2)), tempHealth);
				doctors.add(tempDoctor);
			} else if (line.get(0).equals("organSurgeon"))
			{
				Health tempHealth = new Health(0, -1, null);
				Doctor tempDoctor = new OrganSurgeon(line.get(1).charAt(0), Integer.parseInt(line.get(2)), tempHealth);
				doctors.add(tempDoctor);
			} else if (line.get(0).equals("hospital"))
			{
				hospital.setMaxBeds(Integer.parseInt(line.get(1)) - 1);
				hospital.setMaxTheatres(Integer.parseInt(line.get(2)) - 1);
				hospital.initBeds(hospital.getMaxBeds());
				hospital.initTheatres(hospital.getMaxTheatres());
			}
		}
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
	 */

	public void assignDoctors()
	{
		HashSet<Patient> assignedPatients = new HashSet<Patient>(); //HashSet containing assigned patients
		for (Doctor d : doctors) //Iterate through doctors
		{
			int tempSpec = d.getSpecialism();
			boolean patientFound = false;
			int i = 0;

			if (d.getSpecialism() == 4) //If LimbSurgeon
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
							boolean theatreFound = false;
							i = 0;
							while ((!theatreFound) && (i < hospital.getMaxTheatres())) //Iterates through the operating theatres until a free one found
							{
								if (hospital.isTheatreFree(i))
								{
									theatreFound = true;
									d.assignPatient(tempPatient); 	//Assigns patient to doctor
									assignedPatients.add(tempPatient); 	//Marks patient as being already assigned
									hospital.prepForTheatre(i, tempPatient);	 //Assigns patient to operating theatre
								}
								i++;
							}
						}
					}
					i++;
				}
				if ((!patientFound) && (!(i < hospital.size())))
					d.setSpecialism(2);
			}

			i = 0;

			if (d.getSpecialism() == 3) //If OrganSurgeon
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
							boolean theatreFound = false;
							i = 0;
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
						}
					}
					i++;
				}
				if ((!patientFound) && (!(i < hospital.size())))
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
							boolean theatreFound = false;
							i = 0;
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
						}
					}
					i++;
				}
				if ((!patientFound) && (!(i < hospital.size())))
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
							d.assignPatient(tempPatient); 	//Assigns patient to doctor
							assignedPatients.add(tempPatient);	//Marks patient as being already assigned
						}
					}
					i++;
				}
			}

			d.setSpecialism(tempSpec);
		}
	}

	public boolean aDayPasses()
	{
		for (Patient p : waitingPatients)		//Admits all waiting patients
		{
			hospital.admitPatient(p);
		}
		this.assignDoctors();		///Assigns patients to doctors
		for (Doctor d : doctors)	//Doctors treat their patients
		{
			d.aDayPasses();
		}
		int i = 0;
		for (Theatre t : hospital.getTheatres())		//Remove patients from operating theatres
		{
			if (t.isOccupied())
			{
				hospital.takeForRecovery(i);
				i++;
			}
		}
		i = 0;
		for (Bed b : hospital.getBeds())		//Discharge any healthy patients and lets patients recover
		{
			if (b.isOccupied())
			{
				b.getPatient().aDayPasses();
				if (b.getPatient().getHealth().getHealthState() == 0)
				{
					hospital.dischargePatient(i);
				}
			}
			i++;
		}
		return true;
	}
	
	public void printDayResults(HospitalAdministrator admin)
	{
		System.out.println("Patients : ");
		for (int i = 0; i < admin.getHospital().size(); i++)
		{
			if (admin.getHospital().getBeds().get(i).getPatient() != null)
			{
				admin.getHospital().getBeds().get(i).getPatient().printGenderAge();
				admin.getHospital().getBeds().get(i).getPatient().printHealth();;
				System.out.println();
			}
		}
		System.out.println("Doctors : ");
		for (int i = 0; i < admin.getDoctors().size(); i++)
		{
			admin.getDoctors().get(i).printDetails();
			System.out.println();
		}
		System.out.println();
	}
	
	public void startSimulation()
	{
		HospitalAdministrator admin = new HospitalAdministrator();
		admin.initSimulation();
		System.out.println("Max Beds = " + hospital.getMaxBeds());
		System.out.println("Max Theatres = " + hospital.getMaxTheatres());
		System.out.println("");
		for(int i = 0; i < 7; i++)
		{
			System.out.println("Day " + (i+1));
			admin.aDayPasses();
			admin.printDayResults(admin);
		}
	}
	
	public static void main(String args[])
	{
		HospitalAdministrator admin = new HospitalAdministrator();
		admin.startSimulation();
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
