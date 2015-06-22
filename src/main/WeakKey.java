package main;

import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

public class WeakKey {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		KeyPairGenerator keyGen;
		try {
			keyGen = KeyPairGenerator.getInstance("RSA");
			keyGen.initialize(512);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
	}

}
