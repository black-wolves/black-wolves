package com.blackwolves.mail.util;

public interface Constant {

	public static final String ROUTE = "/var/www/";
	public static final String ROUTE_LOGS_ERROR = "/var/www/logs/errors/";
	public static final String EMPTY_STRING = "";
	public static final String TRUE = "true";
	public static final String FALSE = "false";
	public static final String BLANK_SPACE = " ";
	public static final int MAX_SPAM = 20;
	public static final int DEFAULT_THREADS = 25;
	public static final String VALIDATE = "validate";

	public interface Yahoo{
		public static final String IMAP_YAHOO = "imap.mail.yahoo.com";
		public static final int IMAP_PORT = 993;
		public static final String PICKUP_ROUTE = "/ramcache/pmta/pickup/";
		public static final String BLACKWOLVES_ROUTE = "/root/blackwolves/bodies/";
		public static final String INBOX = "Inbox";
		public static final String SPAM = "Bulk Mail";
		public static final String BODIES = "Bodies";

		public static final String HOST = "smtp.mail.yahoo.com";
		public static final String PORT = "587";
		public static final String CONTENT_TYPE = "text/html; charset=utf-8";
		public static final String CONTENT_TRANSFER_ENCODING = "7bit";
	}
	
	public static final String YAHOO_MAIL_RO_URL = "https://login.yahoo.com/?.src=ym&.intl=us&.lang=en-US&.done=https%3a//mail.yahoo.com";
	public static final String CONTINUE = "Continue";
	public static final String Next = "Next";
	public static final String ERROR_TEXT_1 = "The email and password you entered";
	public static final String ERROR_TEXT_2 = "Sorry, we don";
	
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
		public static final String MAIL_COUNT = "MAIL_COUNT";
		public static final String OPENED = "OPENED";
		public static final String CLICKED = "CLICKED";
		public static final String SPAMMED = "SPAMMED";
		public static final String FEEDER_UPDATED_DATE = "FEEDER_UPDATED_DATE";
		public static final String SEEDER_UPDATED_DATE= "SEEDER_UPDATED_DATE";
		public static final String SEED = "SEED";
		public static final String PASSWORD = "PASSWORD";
		public static final String FULL_SEED = "FULL_SEED";
		public static final String SUBSCRIPTION = "SUBSCRIPTION";
		public static final String NOT_SPAM = "NOT_SPAM";
		public static final String FILE = "file";
	}
	
}
