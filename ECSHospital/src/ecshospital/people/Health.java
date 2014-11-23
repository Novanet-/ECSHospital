package ecshospital.people;

import ecshospital.attributes.Illness;
import ecshospital.util.RandomNumber;

public class Health
{
	int healthState;	//0 = healthy, 1 = sick, 2 = recovering
	int recoveryTime;
	Illness illness;
	
	
	public Health(int healthState, int recoveryTime, Illness illness)
	{
		this.healthState = healthState;
		this.recoveryTime = recoveryTime;
		this.illness = illness;
	}
	
	public int generateRecoveryTime()
	{
		RandomNumber randomNo = new RandomNumber();
		int rand = randomNo.generate(illness.getMaxRecoveryTime() - illness.getMinRecoveryTime());  //Uses a random number generator, to find a value for recovery time
		return illness.getMinRecoveryTime() + rand;													//Between the min and max recovery time for the illness
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
