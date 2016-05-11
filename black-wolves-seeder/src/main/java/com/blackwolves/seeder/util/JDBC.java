/**
 * 
 */
package com.blackwolves.seeder.util;

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
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blackwolves.seeder.Seed;

/**
 * @author gastondapice
 *
 */
public class JDBC {

	private static final Logger logger = LoggerFactory.getLogger(JDBC.class);
	
	private static BufferedReader reader;
	
	public static void main(String[] args) throws ParseException {
//		updateSeed("lhnxoj@yahoo.com", 0, 0, 0);
//		getStats();
		getLastUpdatedSeeds();
//		getSeedsForSubscriptions(9994,10000);
	}
	
	public static List<Seed> getLastUpdatedSeeds() {
		Connection dbConnection = null;
		Statement statement = null;
		
		SimpleDateFormat formatter = new SimpleDateFormat(Constant.JDBC.SDF);
		TimeZone tz = TimeZone.getTimeZone(Constant.JDBC.GMT_3);
		formatter.setTimeZone(tz);
		
		List<Seed> seeds = new ArrayList<Seed>();
		try {

			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
			
			String selectSQL = "SELECT * FROM "
					+ "(SELECT * FROM FEEDER WHERE FEEDER_UPDATED_DATE IS NOT NULL AND LOGGED_IN = 0 order by FEEDER_UPDATED_DATE DESC) x "
					+ "ORDER BY RAND() LIMIT 40";
			
//			String selectSQL =  "SELECT * FROM FEEDER  where SEED='voxackkhzzc@yahoo.com'";
			
			logger.info(selectSQL);
			
			ResultSet rs = statement.executeQuery(selectSQL);
			while(rs.next()){
				String user = rs.getString(Constant.FEEDER.SEED);
				String password = rs.getString(Constant.FEEDER.PASSWORD);
				int mailCount = rs.getInt(Constant.FEEDER.MAIL_COUNT);
				int opened = rs.getInt(Constant.FEEDER.OPENED);
				int clicked = rs.getInt(Constant.FEEDER.CLICKED);
				int spammed = rs.getInt(Constant.FEEDER.SPAMMED);
				int notSpam = rs.getInt(Constant.FEEDER.NOT_SPAM);

				Timestamp feederUpdatedDate = rs.getTimestamp(Constant.FEEDER.FEEDER_UPDATED_DATE);
				Timestamp seederUpdatedDate = rs.getTimestamp(Constant.FEEDER.SEEDER_UPDATED_DATE);
				String subscriptions = rs.getString(Constant.FEEDER.SUBSCRIPTION);
				Seed seed = new Seed(user, password, mailCount, opened, clicked, spammed, notSpam, feederUpdatedDate, seederUpdatedDate,subscriptions);
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
	
	public static Map<String, Object> getStats() {
		Connection dbConnection = null;
		Statement statement = null;
		
//		Timestamp now = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
//		Timestamp oneHourAgo = new Timestamp(System.currentTimeMillis() - (60 * 60 * 1000));
		
		SimpleDateFormat formatter = new SimpleDateFormat(Constant.JDBC.SDF);
		TimeZone tz = TimeZone.getTimeZone(Constant.JDBC.GMT_3);
		formatter.setTimeZone(tz);

		String selectSQL = "SELECT SUM(MAIL_COUNT) AS MAIL_COUNT, SUM(OPENED) AS OPENED, SUM(CLICKED) AS CLICKED, SUM(SPAMMED) AS SPAMMED FROM FEEDER";// WHERE FEEDER_UPDATED_DATE BETWEEN '" + formatter.format(oneHourAgo) + "' AND '" + formatter.format(now) + "'";
		Map<String, Object> map = null;
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();

			logger.info(selectSQL);

			ResultSet rs = statement.executeQuery(selectSQL);
			int mailCount = 0;
			int opened = 0;
			int clicked = 0;
			int spammed = 0;
			if(rs.next()){
				mailCount = rs.getInt(Constant.FEEDER.MAIL_COUNT);
				opened = rs.getInt(Constant.FEEDER.OPENED);
				clicked = rs.getInt(Constant.FEEDER.CLICKED);
				spammed = rs.getInt(Constant.FEEDER.SPAMMED);
			}
			map = new HashMap<String, Object>();
			map.put(Constant.FEEDER.MAIL_COUNT, mailCount);
			map.put(Constant.FEEDER.OPENED, opened);
			map.put(Constant.FEEDER.CLICKED, clicked);
			map.put(Constant.FEEDER.SPAMMED, spammed);

			logger.info("Seed selected from FEEDER table!");

		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		} finally {
			closeStatementAndConnection(dbConnection, statement);
		}
		return map;
	}
	
	/**
	 * 
	 * @param seed
	 * @throws SQLException
	 */
	public static void updateSubscription(Seed seed) {
		Connection dbConnection = null;
		Statement statement = null;
		
		String updateSQL = "UPDATE FEEDER"
				+ " SET SUBSCRIPTION = '" + seed.getSubscription() + "'"
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
	 * @param user
	 * @param open
	 * @param click
	 * @throws SQLException
	 */
	public static void updateSeed(Seed seed, int openCount, int clickCount, int spamCount, int notSpamCount ,boolean inUse, boolean mailChanged){

		Connection dbConnection = null;
		Statement statement = null;
		
		Timestamp now = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
		
		SimpleDateFormat formatter = new SimpleDateFormat(Constant.JDBC.SDF);
		TimeZone tz = TimeZone.getTimeZone(Constant.JDBC.GMT_3);
		formatter.setTimeZone(tz);

		String updateSQL = Constant.EMPTY_STRING;
		
		if(mailChanged){
			updateSQL = "UPDATE FEEDER"
					+ " SET VALIDATED = 1"
					+ mailChangeSQL(seed, mailChanged)
					+ " WHERE SEED = '" + seed.getUser() + "'";
		}else{
			updateSQL = "UPDATE FEEDER"
					+ " SET SEEDER_UPDATED_DATE = '" + formatter.format(now) + "'"
					+ " , IN_USE = "+ inUse
					+ openSql(openCount)
					+ clickSql(clickCount)
					+ spamSql(spamCount)
					+ notSpamSql(notSpamCount)
					+ " WHERE SEED = '" + seed.getUser() + "'";
		}
		

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
	
	/**
	 * Checks the count number if it is > 0 it will add the sql 
	 * sentence to update the NOT_SPAM column
	 * @param notSpamCount
	 * @return
	 */
	private static String notSpamSql(int notSpamCount) {
		if(notSpamCount > 0){
			return ", NOT_SPAM = NOT_SPAM + " + notSpamCount;
		}
		return Constant.EMPTY_STRING;
	}

	/**
	 * Checks the count number if it is > 0 it will add the sql 
	 * sentence to update the SPAMMED column
	 * @param spamCount
	 * @return
	 */
	private static String spamSql(int spamCount) {
		if(spamCount > 0){
			return ", SPAMMED = SPAMMED + " + spamCount;
		}
		return Constant.EMPTY_STRING;
	}

	/**
	 * Checks the count number if it is > 0 it will add the sql 
	 * sentence to update the CLICKED column
	 * @param clickCount
	 * @return
	 */
	private static String clickSql(int clickCount) {
		if(clickCount > 0){
			return ", CLICKED = CLICKED + " + clickCount;
		}
		return Constant.EMPTY_STRING;
	}

	/**
	 * Checks the count number if it is > 0 it will add the sql 
	 * sentence to update the OPENED column 
	 * @param openCount
	 * @return
	 */
	private static String openSql(int openCount) {
		if(openCount > 0){
			return ", OPENED = OPENED + " + openCount;
		}
		return Constant.EMPTY_STRING;
	}


	public static List<Seed> getSeedsForSubscriptions(int index, int top) {
		Connection dbConnection = null;
		Statement statement = null;
		
		SimpleDateFormat formatter = new SimpleDateFormat(Constant.JDBC.SDF);
		TimeZone tz = TimeZone.getTimeZone(Constant.JDBC.GMT_3);
		formatter.setTimeZone(tz);
		
		String selectSQL = "SELECT * FROM FEEDER WHERE  FEEDER.ID >= "+index+" AND FEEDER.ID < "+top+" AND SUBSCRIPTION is NULL ORDER BY FEEDER.ID ASC LIMIT 15";
		List<Seed> seeds = new ArrayList<Seed>();
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();

			logger.info(selectSQL);

			ResultSet rs = statement.executeQuery(selectSQL);
			while(rs.next()){
				String user = rs.getString(Constant.FEEDER.SEED);
				String password = rs.getString(Constant.FEEDER.PASSWORD);
				int mailCount = rs.getInt(Constant.FEEDER.MAIL_COUNT);
				int opened = rs.getInt(Constant.FEEDER.OPENED);
				int clicked = rs.getInt(Constant.FEEDER.CLICKED);
				int spammed = rs.getInt(Constant.FEEDER.SPAMMED);
				int notSpam = rs.getInt(Constant.FEEDER.NOT_SPAM);
				String subscriptions = rs.getString(Constant.FEEDER.SUBSCRIPTION);
				Timestamp feederUpdatedDate = rs.getTimestamp(Constant.FEEDER.FEEDER_UPDATED_DATE);
				Timestamp seederUpdatedDate = rs.getTimestamp(Constant.FEEDER.SEEDER_UPDATED_DATE);
				Seed seed = new Seed(user, password, mailCount, opened, clicked, spammed, notSpam, feederUpdatedDate, seederUpdatedDate,subscriptions);
				seeds.add(seed);
			}

			logger.info( seeds.size() + " seeds selected from FEEDER table!");

		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		} finally {
			closeStatementAndConnection(dbConnection, statement);
		}
		return seeds;
	}
	
	public static int getLessOpenedCount() {
		Connection dbConnection = null;
		Statement statement = null;
		
		String selectSQL = "SELECT MIN(OPENED) AS OPENED FROM FEEDER";
		
		int opened = 0;
		
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();

			logger.info(selectSQL);

			ResultSet rs = statement.executeQuery(selectSQL);
			if(rs.next()){
				opened = rs.getInt(Constant.FEEDER.OPENED);
			}

			logger.info("Less opened count is: " + opened);

		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		} finally {
			closeStatementAndConnection(dbConnection, statement);
		}
		return opened;
	}

	public static void updateSeeds(List<Seed> seeds, boolean loggedIn, boolean inUse) {
		Connection dbConnection = null;
		Statement statement = null;
		
		Timestamp now = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
		
		SimpleDateFormat formatter = new SimpleDateFormat(Constant.JDBC.SDF);
		TimeZone tz = TimeZone.getTimeZone(Constant.JDBC.GMT_3);
		formatter.setTimeZone(tz);


		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
			
			for (Seed seed : seeds) {
				String updateSQL = "UPDATE FEEDER"
						+ " SET SEEDER_UPDATED_DATE = '" + formatter.format(now) + "'"
						+ " , IN_USE = "+ inUse
						+ loggedInSql(loggedIn)
						+ " WHERE SEED = '" + seed.getUser() + "'";

				logger.info(updateSQL);

				statement.execute(updateSQL);
			}

			logger.info("Seeds updated to FEEDER table!");

		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		} finally {
			closeStatementAndConnection(dbConnection, statement);
		}
		
	}

	private static String loggedInSql(boolean loggedIn) {
		if(loggedIn){
			return ", LOGGED_IN = LOGGED_IN + 1";
		}
		return Constant.EMPTY_STRING;
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
}
