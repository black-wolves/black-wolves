/**
 * 
 */
package com.blackwolves.seeder.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	
//	public static void main(String[] args) {
//		updateSeed("gastondapice@yaoo.com", 20, 2, 1);
//	}
	
	/**
	 * 
	 * @param seed
	 * @param open
	 * @param click
	 * @throws SQLException
	 */
	public static void updateSeed(String seed, int openCount, int clickCount, int spamCount){

		Connection dbConnection = null;
		Statement statement = null;
		
		Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());

		String updateTableSQL = "UPDATE mailinglocaweb.FEEDER"
				+ " SET SEEDER_UPDATED_DATE = '" + currentTimestamp + "'"
				+ openSql(openCount)
				+ clickSql(clickCount)
				+ clickSpam(spamCount)
				+ " WHERE SEED = '" + seed + "'";

		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();

			logger.info(updateTableSQL);

			statement.execute(updateTableSQL);

			logger.info("Seedis updated to DBUSER table!");

		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		} finally {
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

	/**
	 * Checks the count number if it is > 0 it will add the sql 
	 * sentence to update the SPAMMED column
	 * @param spamCount
	 * @return
	 */
	private static String clickSpam(int spamCount) {
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
}
