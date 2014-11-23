package ecshospital.people;

import ecshospital.attributes.Illness;

public class Health
{
	int healthState;
	int recoveryTime;
	Illness illness;
	
	
	public Health(int healthState, int recoveryTime, Illness illness)
	{
		this.healthState = healthState;
		this.recoveryTime = recoveryTime;
		this.illness = illness;
	}
	
	public int getHealthState()
	{
		return healthState;
	}
	public int getRecoveryTime()
	{
		return recoveryTime;
	}
	public Illness getIllness()
	{
		return illness;
	}
	public void setHealthState(int healthState)
	{
		this.healthState = healthState;
	}
	public void setRecoveryTime(int recoveryTime)
	{
		this.recoveryTime = recoveryTime;
	}
	public void setIllness(Illness illness)
	{
		this.illness = illness;
	}
	
}
