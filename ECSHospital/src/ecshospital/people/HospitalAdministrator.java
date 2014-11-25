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
	ArrayList<Doctor> doctors = new ArrayList<Doctor>();
	ArrayList<Patient> waitingPatients = new ArrayList<Patient>();
	Parser parser = new Parser();

	public boolean initSimulation()
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
					healthState = 0;
				}
				else
				{
					tempIllness = hospital.getIllnessArray().get((Integer.parseInt(line.get(3))) - 1);
					healthState = 1;
				}
				Health tempHealth = new Health(healthState, -1, tempIllness);
				Patient tempPatient = new Patient(line.get(1).charAt(0), Integer.parseInt(line.get(2)), tempHealth);
				waitingPatients.add(tempPatient);
			}
			else if (line.get(0).equals("doctor"))
			{
				Health tempHealth = new Health(0, -1, null);
				Doctor tempDoctor = new Doctor(line.get(1).charAt(0), Integer.parseInt(line.get(2)), tempHealth);
				doctors.add(tempDoctor);
			}
			else if (line.get(0).equals("limbSurgeon"))
			{
				Health tempHealth = new Health(0, -1, null);
				Doctor tempDoctor = new LimbSurgeon(line.get(1).charAt(0), Integer.parseInt(line.get(2)), tempHealth);
				doctors.add(tempDoctor);
			}
			else if (line.get(0).equals("organSurgeon"))
			{
				Health tempHealth = new Health(0, -1, null);
				Doctor tempDoctor = new OrganSurgeon(line.get(1).charAt(0), Integer.parseInt(line.get(2)), tempHealth);
				doctors.add(tempDoctor);
			}
			else if (line.get(0).equals("hospital"))
			{
				hospital.initBeds(Integer.parseInt(line.get(1)));
				hospital.initTheatres(Integer.parseInt(line.get(2)));
			}
		}
		return false;
	}

	public void assignDoctors()
	{
		HashSet<Patient> assignedPatients = new HashSet<Patient>();
		for (Doctor d : doctors)
		{
			int tempSpec = d.getSpecialism();
			boolean patientFound = false;

			if (d.getSpecialism() == 4)
			{
				for (int i = 0; i < hospital.size(); i++)
				{
					Patient tempPatient = hospital.getBeds().get(i).getPatient();
					int illnessID = tempPatient.getHealth().getIllness().getIdNumber();
					if ((illnessID == 5 || illnessID == 6) && (!assignedPatients.contains(tempPatient)))
					{
						patientFound = true;
						boolean theatreFound = false;
						i = 0;
						while ((!theatreFound) && (i < hospital.getMaxTheatres()))
						{
							if (hospital.isTheatreFree(i))
							{
								theatreFound = true;
								hospital.prepForTheatre(i, tempPatient);
							}
						}
					}
				}
				if (!patientFound)
					d.setSpecialism(2);
			}

			if (d.getSpecialism() == 3)
			{
				for (int i = 0; i < hospital.size(); i++)
				{
					Patient tempPatient = hospital.getBeds().get(i).getPatient();
					int illnessID = tempPatient.getHealth().getIllness().getIdNumber();
					if ((illnessID == 7 || illnessID == 8) && (!assignedPatients.contains(tempPatient)))
					{
						patientFound = true;
						boolean theatreFound = false;
						i = 0;
						while ((!theatreFound) && (i < hospital.getMaxTheatres()))
						{
							if (hospital.isTheatreFree(i))
							{
								theatreFound = true;
								hospital.prepForTheatre(i, tempPatient);
							}
						}
					}
				}
				if (!patientFound)
					d.setSpecialism(2);
			}

			if (d.getSpecialism() == 2)
			{
				for (int i = 0; i < hospital.size(); i++)
				{
					Patient tempPatient = hospital.getBeds().get(i).getPatient();
					int illnessID = tempPatient.getHealth().getIllness().getIdNumber();
					if ((illnessID == 4) && (!assignedPatients.contains(tempPatient)))
					{
						patientFound = true;
						boolean theatreFound = false;
						i = 0;
						while ((!theatreFound) && (i < hospital.getMaxTheatres()))
						{
							if (hospital.isTheatreFree(i))
							{
								theatreFound = true;
								hospital.prepForTheatre(i, tempPatient);
							}
						}
					}
				}
				if (!patientFound)
					d.setSpecialism(1);
			}

			if (d.getSpecialism() == 1)
			{
				for (int i = 0; i < hospital.size(); i++)
				{
					Patient tempPatient = hospital.getBeds().get(i).getPatient();
					int illnessID = tempPatient.getHealth().getIllness().getIdNumber();
					if ((illnessID < 4) && (!assignedPatients.contains(tempPatient)))
					{
						patientFound = true;
						boolean theatreFound = false;
						i = 0;
						while ((!theatreFound) && (i < hospital.getMaxTheatres()))
						{
							if (hospital.isTheatreFree(i))
							{
								theatreFound = true;
								hospital.prepForTheatre(i, tempPatient);
							}
						}
					}
				}
			}

			d.setSpecialism(tempSpec);
		}
	}

	public boolean aDayPasses()
	{
		for (Patient p : waitingPatients)
		{
			hospital.admitPatient(p);
		}
		this.assignDoctors();
		for (Doctor d : doctors)
		{
			d.aDayPasses();
		}
		int i = 0;
		for (Theatre t : hospital.getTheatres())
		{
			if (t.isOccupied())
			{
				hospital.takeForRecovery(i);
				i++;
			}
		}
		i = 0;
		for (Bed b : hospital.getBeds())
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
