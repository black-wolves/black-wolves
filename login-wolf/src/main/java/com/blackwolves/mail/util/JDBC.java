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
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;

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
	 * @param seed
	 * @throws SQLException
	 */
	public static void updateSeed(Seed seed) {
		Connection dbConnection = null;
		Statement statement = null;
		
		String updateSQL = "UPDATE FEEDER"
				+ " SET SEED = '" + seed.getNewUser() + "'"
				+ " SET FULL_SEED = '" + seed.getFullSeed() + "'"
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
