package com.billdesk.CustomerApp;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

//import oracle.jdbc.pool.OracleDataSource;


public class DBConnection {
	
	//TO CREATE DATABASE CONNECTION		--RETURNS CONNECTION OBJECT
    public Connection getOracleConnection() throws IOException {
		String hname = null;
		String port = null;
		String user = null;
		String pass = null;
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException classNotFoundException) {
			classNotFoundException.printStackTrace();
			return null;
		}

		// Properties file path.
		String filePath = "application.properties";
		Properties prop = new Properties();

		try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath)) {

			// Loading the properties.
			prop.load(inputStream);

			// Getting properties
			hname = prop.getProperty("hostname");
			port = prop.getProperty("port");
			user = prop.getProperty("username");
			pass = prop.getProperty("password");
		}

		
		catch (IOException ex) {
			System.out.println("Problem occurs when reading file !");
			ex.printStackTrace();
		}

		try {

			Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@" + hname + ":" + port + ": bduat",
					user, pass);
			return connection;
		} catch (SQLException exception) {
			exception.printStackTrace();
			return null;
		}
	}
	
}
