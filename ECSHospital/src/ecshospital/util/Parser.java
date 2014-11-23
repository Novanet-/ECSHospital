package ecshospital.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Parser
{
	public ArrayList<String> readConfigFile()
	{
	    Scanner scanner = null;
	    ArrayList<String> fileArray = new ArrayList<String>();
		try
		{
			scanner = new Scanner(new FileInputStream("H:/Computer Science/COMP1202 (Programming I)/Git/ECSHospital/ECSHospital/cfg/myHospital.txt"));
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	    try {
	      while (scanner.hasNextLine()){
	         fileArray.add(scanner.nextLine());
	      }
	    }
	    finally{
	      scanner.close();
	    }
		return fileArray;
	}
	
	public ArrayList<String> parseConfigFile(String line)
	{
		ArrayList<String> splitLine = new ArrayList<String>();
		String[] tempParse = line.split(":");
		splitLine.add(tempParse[0]);
		tempParse = tempParse[1].split(",");
		for (int i =0; i < tempParse.length; i++)
		{
			splitLine.add(tempParse[i]);
		}
		return splitLine;
	}
}
