package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
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
	
	public static String getIp() throws Exception {
        URL whatismyip = new URL("http://checkip.amazonaws.com");
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
            String ip = in.readLine();
            return ip;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
	
	public static void main(String [] args) throws Exception
	{
		System.out.println(getRandomNumber(0, 1));
	}
}
