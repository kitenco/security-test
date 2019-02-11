package de.kitenco.security;

import java.util.Properties;

import javax.naming.Context;

public class Password {

	private String SECRET_PASSWORD = "letMeIn!";

	public Password() {
	
		Properties props = new Properties();
		props.put(Context.SECURITY_CREDENTIALS, "p@ssw0rd");
		props.put(Context.SECURITY_CREDENTIALS, SECRET_PASSWORD);

	}

}
