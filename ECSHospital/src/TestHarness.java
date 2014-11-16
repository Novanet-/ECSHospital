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
			if (i % 2 == 0)
			{
				gender = 'M';
			}
			else
			{
				gender = 'F';
			}
			
			Illness tempIllness = hospital.getIllnessArray().get(i);
			int tempRecoverTime;
			
			RandomNumber randomNo = new RandomNumber();
			if (randomNo.generate() == 0)
			{
				tempRecoverTime = tempIllness.getMaxRecoveryTime();
			}
			else
			{
				tempRecoverTime = tempIllness.getMinRecoveryTime();
			}
			
			Health tempHealth = new Health(0, tempRecoverTime, tempIllness);
			hospital.admitPatient(new Patient(gender, (20+ i), tempHealth, i + " Smith"));
		}
		System.out.println("Beds in hospital = " + hospital.getBeds().size());
		System.out.println("Beds occupied = " + hospital.size());
		System.out.println("");
		System.out.println("First occupied bed patient = ");
		hospital.getPatient(0).printDetails();
		System.out.println("");
		System.out.println("Last  occupied bed patient = ");
		hospital.getPatient(hospital.size() - 1).printDetails();
		System.out.println("");
		System.out.println("********");
		for (int i = 0; i < hospital.getMAX_THEATRES(); i++)
		{
			if (i % 2 == 0 || i == 3)
				hospital.prepForTheatre(i, hospital.getPatient(i));
		}

		System.out.println("Theatres in hospital = "
				+ hospital.getTheatres().size());
		System.out.println("Theatres occupied = " + hospital.theatresFree());
		System.out.println("First theatre patient = "
				+ hospital.getPatient(0).getName());
		System.out.println("Last theatre patient = "
				+ hospital.getPatient(hospital.getTheatres().size() - 1)
						.getName());
		System.out.println("********");

		for (int i = 0; i < hospital.getMAX_THEATRES(); i++)
		{
			if (!hospital.isTheatreFree(i))
				hospital.takeForRecovery(i);
		}

		System.out.println("Theatres in hospital = "
				+ hospital.getTheatres().size());
		System.out.println("Theatres occupied = "
				+ (hospital.getMAX_THEATRES() - (hospital.theatresFree())));
		System.out.println("First theatre patient = "
				+ hospital.getTheatres().get(0).isOccupied());
		System.out.println("Last theatre patient = "
				+ hospital.getTheatres().get(hospital.getMAX_THEATRES() - 1)
						.isOccupied());
		System.out.println("********");
	}
}
