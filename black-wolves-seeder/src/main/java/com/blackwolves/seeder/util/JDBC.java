/**
 * 
 */
package com.blackwolves.seeder.util;

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
	
	private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_CONNECTION = "jdbc:mysql://190.228.29.59:3306/mailinglocaweb";
	private static final String DB_USER = "mailinglocaweb";
	private static final String DB_PASSWORD = "3H8osZA3";
	
	private static final String SDF = "yyyy-M-dd HH:mm:ss";
	private static final String GMT_3 = "GMT-3";
	
	public static void main(String[] args) throws ParseException {
//		updateSeed("lhnxoj@yahoo.com", 0, 0, 0);
//		getStats();
//		getLastUpdatedSeeds();
//		getSeedsWithNoSubscriptions(10000);
	}
	
	public static Map<String, Object> getStats() {
		Connection dbConnection = null;
		Statement statement = null;
		
		Timestamp now = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
		Timestamp oneHourAgo = new Timestamp(System.currentTimeMillis() - (60 * 60 * 1000));
		
		SimpleDateFormat formatter = new SimpleDateFormat(SDF);
		TimeZone tz = TimeZone.getTimeZone(GMT_3);
		formatter.setTimeZone(tz);

		String selectSQL = "SELECT SUM(MAIL_COUNT) AS MAIL_COUNT, SUM(OPENED) AS OPENED, SUM(CLICKED) AS CLICKED, SUM(SPAMMED) AS SPAMMED FROM FEEDER WHERE FEEDER_UPDATED_DATE BETWEEN '" + formatter.format(oneHourAgo) + "' AND '" + formatter.format(now) + "'";
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
		
		String updateSQL = "UPDATE mailinglocaweb.FEEDER"
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
	public static void updateSeed(String user, int openCount, int clickCount, int spamCount){

		Connection dbConnection = null;
		Statement statement = null;
		
		Timestamp now = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
		
		SimpleDateFormat formatter = new SimpleDateFormat(SDF);
		TimeZone tz = TimeZone.getTimeZone(GMT_3);
		formatter.setTimeZone(tz);

		String updateSQL = "UPDATE mailinglocaweb.FEEDER"
				+ " SET SEEDER_UPDATED_DATE = '" + formatter.format(now) + "'"
				+ openSql(openCount)
				+ clickSql(clickCount)
				+ spamSql(spamCount)
				+ " WHERE SEED = '" + user + "'";

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
	 * Creates the connection to the database
	 * @return
	 */
	private static Connection getDBConnection() {
		Connection dbConnection = null;
		try {
			Class.forName(DB_DRIVER);
		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
		}
		try {
			dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER,DB_PASSWORD);
			return dbConnection;
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
		return dbConnection;

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

	public static List<Seed> getLastUpdatedSeeds() {
		Connection dbConnection = null;
		Statement statement = null;
		
		Timestamp now = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
		Timestamp oneHourAgo = new Timestamp(System.currentTimeMillis() - (60 * 60 * 1000));
		
		SimpleDateFormat formatter = new SimpleDateFormat(SDF);
		TimeZone tz = TimeZone.getTimeZone(GMT_3);
		formatter.setTimeZone(tz);
		
		String selectSQL = "SELECT * from mailinglocaweb.FEEDER WHERE FEEDER_UPDATED_DATE BETWEEN '" + formatter.format(oneHourAgo) + "' AND '" + formatter.format(now) + "' ORDER BY FEEDER_UPDATED_DATE DESC";
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
				Timestamp feederUpdatedDate = rs.getTimestamp(Constant.FEEDER.FEEDER_UPDATED_DATE);
				Timestamp seederUpdatedDate = rs.getTimestamp(Constant.FEEDER.SEEDER_UPDATED_DATE);
				String subscriptions = rs.getString(Constant.FEEDER.SUBSCRIPTION);
				Seed seed = new Seed(user, password, mailCount, opened, clicked, spammed, feederUpdatedDate, seederUpdatedDate,subscriptions);
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
	
	public static List<Seed> getSeedsWithNoSubscriptions(int index) {
		Connection dbConnection = null;
		Statement statement = null;
		
		SimpleDateFormat formatter = new SimpleDateFormat(SDF);
		TimeZone tz = TimeZone.getTimeZone(GMT_3);
		formatter.setTimeZone(tz);
		
		String selectSQL = "SELECT * from mailinglocaweb.FEEDER WHERE FEEDER.ID >= "+index+"  AND FEEDER.SUBSCRIPTION is NULL ORDER BY FEEDER.ID ASC LIMIT 1  ";
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
				String subscriptions = rs.getString(Constant.FEEDER.SUBSCRIPTION);
				Timestamp feederUpdatedDate = rs.getTimestamp(Constant.FEEDER.FEEDER_UPDATED_DATE);
				Timestamp seederUpdatedDate = rs.getTimestamp(Constant.FEEDER.SEEDER_UPDATED_DATE);
				Seed seed = new Seed(user, password, mailCount, opened, clicked, spammed, feederUpdatedDate, seederUpdatedDate,subscriptions);
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


}
