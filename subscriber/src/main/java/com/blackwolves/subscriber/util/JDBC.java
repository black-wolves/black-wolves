/**
 * 
 */
package com.blackwolves.subscriber.util;

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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blackwolves.subscriber.Seed;

/**
 * @author gastondapice
 *
 */
public class JDBC {

	private static final Logger logger = LoggerFactory.getLogger(JDBC.class);
	
	private static BufferedReader reader;
	
	public static void main(String[] args) throws ParseException {
		
	}
	
	public static List<Seed> getSeedsForSubscriptions() {
		Connection dbConnection = null;
		Statement statement = null;
		
		SimpleDateFormat formatter = new SimpleDateFormat(Constant.JDBC.SDF);
		TimeZone tz = TimeZone.getTimeZone(Constant.JDBC.GMT_3);
		formatter.setTimeZone(tz);
		
//		String selectSQL = "SELECT * FROM FEEDER WHERE SUBSCRIPTION is null ORDER BY RAND() LIMIT 50";
		String selectSQL = "SELECT * FROM FEEDER ORDER BY RAND() LIMIT 50";
		
		List<Seed> seeds = new ArrayList<Seed>();
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();

			logger.info(selectSQL);

			ResultSet rs = statement.executeQuery(selectSQL);
			while(rs.next()){
				String user = rs.getString(Constant.FEEDER.SEED);
				String password = rs.getString(Constant.FEEDER.PASSWORD);
				String fullSeed = rs.getString(Constant.FEEDER.FULL_SEED);
				String subscriptions = rs.getString(Constant.FEEDER.SUBSCRIPTION);
				Seed seed = new Seed(user, password, fullSeed, subscriptions);
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
		case Constant.JDBC.MTA2_DB:
			connection = Constant.JDBC.MTA2_DB_CONNECTION;
			user = Constant.JDBC.MTA2_DB_USER;
			password = Constant.JDBC.MTA2_DB_PASSWORD;
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