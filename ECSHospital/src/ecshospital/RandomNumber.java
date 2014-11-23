package ecshospital;
import java.util.Random;

public class RandomNumber
{

	Random randomNum = new Random();

	public int generate(int range)
	{
		int result = randomNum.nextInt(range + 1);
		return result;
	}

}
