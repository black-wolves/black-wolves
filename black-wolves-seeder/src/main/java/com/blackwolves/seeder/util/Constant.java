package com.blackwolves.seeder.util;

public interface Constant {

	public static final String YAHOO_MAIL_RO_URL = "https://login.yahoo.com/?.src=ym&.intl=us&.lang=en-US&.done=https%3a//mail.yahoo.com";
	public static final String IMAGES_PATH = "/var/www/screenshots/";
	public static final String ROUTE = "/var/www/";
	public static final String EMPTY_STRING = "";
	public static final String PDF_FILE = "files/ionescu-iuliana-not-over-you.pdf";
	public static final int MIN_SUBJECT_WORDS = 15;
	public static final int MAX_SUBJECT_WORDS = 30;
	public static final int MIN_BODY_WORDS = 150;
	public static final int MAX_BODY_WORDS = 300;
	public static final String ALL = "All";
	public static final String HOME_BLACKWOLVES_LOGS = "/home/blackwolves/logs/";
	
	public static final String SPECIFIC = "specific";
	public static final String MULTIPLE = "multiple";
	public static final String ONE = "one";
	public static final String DESTROYER = "destroyer";
	public static final String SENDER = "sender";
	public static final String CONTINUE = "Continue";
	public static final String Next = "Next";
	
	public interface JDBC{
		public static final String SEEDER_DB = "SEEDER_DB";
		public static final String RO_DB = "ro";
		public static final String US_DB = "us";
		public static final String USER_HOME = "user.home";
		public static final String PROFILE = ".profile";
		public static final String BASHRC = ".bashrc";
		
		public static final String DB_DRIVER = "com.mysql.jdbc.Driver";
		
		public static final String RO_DB_CONNECTION = "jdbc:mysql://190.228.29.59:3306/mailinglocaweb";
		public static final String RO_DB_USER = "mailinglocaweb";
		public static final String RO_DB_PASSWORD = "3H8osZA3";
		
		public static final String US_DB_CONNECTION = "jdbc:mysql://38.95.111.2:3306/obcabril_usa_seeds";
		public static final String US_DB_USER = "obcabril_root";
		public static final String US_DB_PASSWORD = "Daniel123";
		
		public static final String SDF = "yyyy-M-dd HH:mm:ss";
		public static final String GMT_3 = "GMT-3";
	}
	
	public interface FactSlides{
		public static final String TCI_IMAGES = "/Users/gastondapice/Dropbox/Black Wolves/sites/TheCoolInfo/images/";
		public static final String TCI_DOWNLOAD = "/Users/gastondapice/Downloads/images/";
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
		
	}
	
	public interface FROM{
		public static final String ENTREPRENEUR = "Entrepreneur";
		public static final String SPAM = "Golfsmith";
		public static final String JERRY = "Jerry Seinfeld";
		public static final String NYTIMES = "NYTimes. com";
		public static final String YAHOO_MAIL = "Yahoo Mail";

		
	}
}
