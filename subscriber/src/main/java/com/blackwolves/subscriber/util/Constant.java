package com.blackwolves.subscriber.util;

public interface Constant {

	public static final String IMAGES_PATH = "/var/www/screenshots/";
	public static final String ROUTE = "/var/www/";
	public static final String EMPTY_STRING = "";
	public static final String PDF_FILE = "files/ionescu-iuliana-not-over-you.pdf";
	public static final String HOME_BLACKWOLVES_LOGS = "/home/blackwolves/logs/";
	
	public interface JDBC{
		public static final String SEEDER_DB = "SEEDER_DB";
		public static final String RO_DB = "ro";
		public static final String US_DB = "us";
		public static final String CAB_DB = "cab";
		
		public static final String DB_DRIVER = "com.mysql.jdbc.Driver";
		
		public static final String RO_DB_CONNECTION = "jdbc:mysql://190.228.29.59:3306/mailinglocaweb";
		public static final String RO_DB_USER = "mailinglocaweb";
		public static final String RO_DB_PASSWORD = "3H8osZA3";
		
		public static final String US_DB_CONNECTION = "jdbc:mysql://38.95.111.2:3306/obcabril_usa_seeds";
		public static final String US_DB_USER = "obcabril_root";
		public static final String US_DB_PASSWORD = "Daniel123";
		
		public static final String CAB_DB_CONNECTION = "jdbc:mysql://38.95.111.2:3306/obcabril_seeds";
		public static final String CAB_DB_USER = "obcabril_root";
		public static final String CAB_DB_PASSWORD = "Daniel123";
		
		public static final String SDF = "yyyy-M-dd HH:mm:ss";
		public static final String GMT_3 = "GMT-3";
		public static final String HOME = "HOME";
		public static final String WOLF_CONFIG_ROUTE = "/var/www/blackwolves/wolf_config.txt";
	}
	
	public interface FEEDER{
		public static final String SEED = "SEED";
		public static final String PASSWORD = "PASSWORD";
		public static final String FULL_SEED = "FULL_SEED";
		public static final String SUBSCRIPTION = "SUBSCRIPTION";
	}
}
