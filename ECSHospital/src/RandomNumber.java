import java.util.Random;

public class RandomNumber
{

	Random randomNum = new Random();

	public int generate()
	{
		int result = randomNum.nextInt(2);
		return result;
	}

}
