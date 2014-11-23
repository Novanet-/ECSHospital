package ecshospital.attributes;


public class Illness
{
	String illnessName;
	int idNumber;
	int minRecoveryTime;
	int maxRecoveryTime;
	String canBeTreatedBy;
	boolean requiresTheatres;

	
	public Illness(String illnessName, int idNumber, int minRecoveryTime, int maxRecoveryTime,
			String canBeTreatedBy, boolean requiresTheatres)
	{
		this.illnessName = illnessName;
		this.idNumber = idNumber;
		this.minRecoveryTime = minRecoveryTime;
		this.maxRecoveryTime = maxRecoveryTime;
		this.canBeTreatedBy = canBeTreatedBy;
		this.requiresTheatres = requiresTheatres;
	}


	public String getIllnessName()
	{
		return illnessName;
	}

	public int getIdNumber()
	{
		return idNumber;
	}

	public int getMinRecoveryTime()
	{
		return minRecoveryTime;
	}

	public int getMaxRecoveryTime()
	{
		return maxRecoveryTime;
	}

	public String getCanBeTreatedBy()
	{
		return canBeTreatedBy;
	}

	public boolean isRequiresTheatres()
	{
		return requiresTheatres;
	}

	public void setIllnessName(String illnessName)
	{
		this.illnessName = illnessName;
	}

	public void setIdNumber(int idNumber)
	{
		this.idNumber = idNumber;
	}



	public void setMinRecoveryTime(int minRecoveryTime)
	{
		this.minRecoveryTime = minRecoveryTime;
	}


	public void setMaxRecoveryTime(int maxRecoveryTime)
	{
		this.maxRecoveryTime = maxRecoveryTime;
	}


	public void setCanBeTreatedBy(String canBeTreatedBy)
	{
		this.canBeTreatedBy = canBeTreatedBy;
	}

	public void setRequiresTheatres(boolean requiresTheatres)
	{
		this.requiresTheatres = requiresTheatres;
	}

}
