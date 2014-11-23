package ecshospital.people;

import java.util.ArrayList;

import ecshospital.attributes.Illness;
import ecshospital.containers.Hospital;
import ecshospital.util.Parser;

public class HospitalAdministrator
{
	Hospital hospital = new Hospital();
	ArrayList<Doctor> doctors = new ArrayList<Doctor>();
	Parser parser = new Parser();
	
	public boolean aDayPasses()
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
					tempIllness = hospital.getIllnessArray().get((Integer.parseInt(line.get(3)))-1);
					healthState = 1;
				}
				Health tempHealth = new Health(healthState, -1, tempIllness);
				Patient tempPatient = new Patient(line.get(1).charAt(0), Integer.parseInt(line.get(2)), tempHealth);
				hospital.admitPatient(tempPatient);
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
	 * @param hospital the hospital to set
	 */
	public void setHospital(Hospital hospital)
	{
		this.hospital = hospital;
	}

	/**
	 * @param doctors the doctors to set
	 */
	public void setDoctors(ArrayList<Doctor> doctors)
	{
		this.doctors = doctors;
	}

	/**
	 * @param parser the parser to set
	 */
	public void setParser(Parser parser)
	{
		this.parser = parser;
	}
	
	
}
