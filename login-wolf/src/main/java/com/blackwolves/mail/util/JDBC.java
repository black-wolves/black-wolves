/**
 * 
 */
package com.blackwolves.mail.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blackwolves.mail.Seed;

/**
 * @author gastondapice
 *
 */
public class JDBC {

	private static final Logger logger = LoggerFactory.getLogger(JDBC.class);
	
	private static BufferedReader reader;
	
	public static void main(String[] args) throws ParseException {

	}
	
	/**
	 * 
	 * @return
	 */
	public static List<Seed> getNonRegisteredFacebookSeeds() {
		Connection dbConnection = null;
		Statement statement = null;
		
		SimpleDateFormat formatter = new SimpleDateFormat(Constant.JDBC.SDF);
		TimeZone tz = TimeZone.getTimeZone(Constant.JDBC.GMT_3);
		formatter.setTimeZone(tz);
		
		List<Seed> seeds = new ArrayList<Seed>();
		try {

			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
			
			String selectSQL = "SELECT * FROM FEEDER WHERE FB_REGISTERED = 0 ORDER BY RAND() LIMIT 1";
			
			logger.info(selectSQL);
			
			ResultSet rs = statement.executeQuery(selectSQL);
			while(rs.next()){
				String user = rs.getString(Constant.Feeder.SEED);
				String password = rs.getString(Constant.Feeder.PASSWORD);
				String fullSeed = rs.getString(Constant.Feeder.FULL_SEED);
				Timestamp feederUpdatedDate = rs.getTimestamp(Constant.Feeder.FEEDER_UPDATED_DATE);
				Timestamp seederUpdatedDate = rs.getTimestamp(Constant.Feeder.SEEDER_UPDATED_DATE);
				String subscriptions = rs.getString(Constant.Feeder.SUBSCRIPTION);
				Seed seed = new Seed(user, password, fullSeed, feederUpdatedDate, seederUpdatedDate, subscriptions);
				seeds.add(seed);
			}

			logger.info("Seed selected from FEEDER table!");
			

		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		} finally {
			closeStatementAndConnection(dbConnection, statement);
		}
		return seeds;
	}
	
	/**
	 * 
	 * @param seed
	 */
	public static void updateSeedPersonalInfo(Seed seed) {
		Connection dbConnection = null;
		Statement statement = null;
		
		String updateSQL = "UPDATE FEEDER"
				+ " SET VALIDATED = 1"
				+ firstName(seed)
				+ lastName(seed)
				+ gender(seed)
				+ birthDate(seed)
				+ fbRegistered(seed)
				+ fbConfirmed(seed)
				+ " WHERE SEED = '" + seed.getUser() + "'";

		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();

			logger.info(updateSQL);

			statement.execute(updateSQL);

			logger.info("Seed updated to FEEDER table!");

		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		} finally {
			closeStatementAndConnection(dbConnection, statement);
		}
	}
	
	/**
	 * 
	 * @param seed
	 * @return
	 */
	private static String firstName(Seed seed) {
		if(seed.getFirstName()!=null){
			return " , FIRST_NAME = '" + seed.getFirstName() + "'";
		}
		return Constant.EMPTY_STRING;
	}
	
	/**
	 * 
	 * @param seed
	 * @return
	 */
	private static String lastName(Seed seed) {
		if(seed.getLastName()!=null){
			return " , LAST_NAME = '" + seed.getLastName() + "'";
		}
		return Constant.EMPTY_STRING;
	}
	
	/**
	 * 
	 * @param seed
	 * @return
	 */
	private static String gender(Seed seed) {
		if(seed.getGender()!=null){
			return " , GENDER = '" + seed.getGender() + "'";
		}
		return Constant.EMPTY_STRING;
	}
	
	/**
	 * 
	 * @param seed
	 * @return
	 */
	private static String birthDate(Seed seed) {
		if(seed.getBirthDate()!=null){
			return " , BIRTH_DATE = '" + seed.getBirthDate() + "'";
		}
		return Constant.EMPTY_STRING;
	}
	
	/**
	 * 
	 * @param seed
	 * @return
	 */
	private static String fbRegistered(Seed seed) {
		if(seed.isFbRegistered()){
			return " , FB_REGISTERED = 1";
		}
		return Constant.EMPTY_STRING;
	}
	
	/**
	 * 
	 * @param seed
	 * @return
	 */
	private static String fbConfirmed(Seed seed) {
		if(seed.isFbConfirmed()){
			return " , FB_CONFIRMED = 1";
		}
		return Constant.EMPTY_STRING;
	}
	
	/**
	 * 
	 * @param seed
	 * @throws SQLException
	 */
	public static void updateSeed(Seed seed, boolean mailChanged) {
		Connection dbConnection = null;
		Statement statement = null;
		
		String updateSQL = "UPDATE FEEDER"
				+ " SET VALIDATED = 1"
				+ mailChangeSQL(seed, mailChanged)
				+ " WHERE SEED = '" + seed.getUser() + "'";

		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();

			logger.info(updateSQL);

			statement.execute(updateSQL);

			logger.info("Seed updated to FEEDER table!");

		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		} finally {
			closeStatementAndConnection(dbConnection, statement);
		}
	}
	
	/**
	 * 
	 * @param seed
	 * @param mailChanged
	 * @return
	 */
	private static String mailChangeSQL(Seed seed, boolean mailChanged) {
		if(mailChanged){
			return " , SEED = '" + seed.getNewUser() + "' , FULL_SEED = '" + seed.getFullSeed() + "'";
		}
		return Constant.EMPTY_STRING;
	}
	
	/**
	 * 
	 * @return
	 */
	public static List<Seed> get50NonValidatedSeeds() {
		Connection dbConnection = null;
		Statement statement = null;
		
		SimpleDateFormat formatter = new SimpleDateFormat(Constant.JDBC.SDF);
		TimeZone tz = TimeZone.getTimeZone(Constant.JDBC.GMT_3);
		formatter.setTimeZone(tz);
		
		List<Seed> seeds = new ArrayList<Seed>();
		try {

			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
			
			String selectSQL = "SELECT * FROM FEEDER WHERE VALIDATED = 0 ORDER BY RAND() LIMIT 50";
			
//			String selectSQL =  "SELECT * FROM FEEDER  where SEED='voxackkhzzc@yahoo.com'";
			
			logger.info(selectSQL);
			
			ResultSet rs = statement.executeQuery(selectSQL);
			while(rs.next()){
				String user = rs.getString(Constant.Feeder.SEED);
				String password = rs.getString(Constant.Feeder.PASSWORD);
				String fullSeed = rs.getString(Constant.Feeder.FULL_SEED);
				Timestamp feederUpdatedDate = rs.getTimestamp(Constant.Feeder.FEEDER_UPDATED_DATE);
				Timestamp seederUpdatedDate = rs.getTimestamp(Constant.Feeder.SEEDER_UPDATED_DATE);
				String subscriptions = rs.getString(Constant.Feeder.SUBSCRIPTION);
				Seed seed = new Seed(user, password, fullSeed, feederUpdatedDate, seederUpdatedDate, subscriptions);
				seeds.add(seed);
			}

			logger.info("Seed selected from FEEDER table!");
			

		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		} finally {
			closeStatementAndConnection(dbConnection, statement);
		}
		return seeds;
	}
	
	/**
	 * Creates the connection to the database
	 * @return
	 */
	private static Connection getDBConnection() {
		Connection dbConnection = null;
		String connection = null;
		String user = null;
		String password = null;
		String db = getDbSetup();
		switch (db) {
		case Constant.JDBC.RO_DB:
			connection = Constant.JDBC.RO_DB_CONNECTION;
			user = Constant.JDBC.RO_DB_USER;
			password = Constant.JDBC.RO_DB_PASSWORD;
			break;
		case Constant.JDBC.US_DB:
			connection = Constant.JDBC.US_DB_CONNECTION;
			user = Constant.JDBC.US_DB_USER;
			password = Constant.JDBC.US_DB_PASSWORD;
			break;
		case Constant.JDBC.CAB_DB:
			connection = Constant.JDBC.CAB_DB_CONNECTION;
			user = Constant.JDBC.CAB_DB_USER;
			password = Constant.JDBC.CAB_DB_PASSWORD;
			break;
		default:
			break;
		}
		try {
			Class.forName(Constant.JDBC.DB_DRIVER);
		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
		}
		try {
			dbConnection = DriverManager.getConnection(connection, user,password);
			return dbConnection;
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
		return dbConnection;

	}
	
	private static String getDbSetup() {
		String db = null;
		try {
			reader = new BufferedReader(new FileReader(new File(Constant.JDBC.WOLF_CONFIG_ROUTE)));
			for(String line = reader.readLine(); line != null; line = reader.readLine()){
				if(line.contains(Constant.JDBC.SEEDER_DB)){
					String[] s = line.split("=");
					db = s[1];
					break;
				}
			}
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return db;
	}

	/**
	 * @param dbConnection
	 * @param statement
	 */
	private static void closeStatementAndConnection(Connection dbConnection,
			Statement statement) {
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				logger.error(e.getMessage(), e);
			}
		}
		if (dbConnection != null) {
			try {
				dbConnection.close();
			} catch (SQLException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

}