package main;

import java.util.Random;

public class RandomNumber {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Random randomGenerator = new Random();
	    for (int idx = 1; idx <= 10; ++idx){
	      int randomInt = randomGenerator.nextInt(100);
	      System.out.println("Generated : " + randomInt);
	    }	
	}

}
