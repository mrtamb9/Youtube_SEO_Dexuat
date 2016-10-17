package utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class Utils {
	static public ArrayList<Integer> getListRandomNumbers(int size, int max_number)
	{
		ArrayList<Integer> listRandomNumbers = new ArrayList<Integer>();
		Random random = new Random();
		HashSet<Integer> setCheckDuplicate = new HashSet<Integer>();
		while(listRandomNumbers.size()<size)
		{
			int number = random.nextInt(max_number);
			if(!setCheckDuplicate.contains(number))
			{
				setCheckDuplicate.add(number);
				listRandomNumbers.add(number);
			}
		}
		
		return listRandomNumbers;
	}
	
	static public int getRandomNumber(int min_value, int max_value)
	{
		if(min_value > max_value)
		{
			return -1;
		}
		
		Random random = new Random();
		int result = random.nextInt(max_value - min_value + 1) + min_value;
		return result;
	}
	
	public static void main(String [] args)
	{
		System.out.println(getListRandomNumbers(2, 6));
	}
}
