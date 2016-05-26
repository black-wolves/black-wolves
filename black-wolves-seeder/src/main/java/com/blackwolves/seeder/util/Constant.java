package com.blackwolves.seeder.util;

public interface Constant {

	final String HOME_BLACKWOLVES_LOGS = "/home/blackwolves/logs/";
	final String IMAGES_PATH = "/var/www/screenshots/";
	final String ROUTE_LOGS_ERROR = "/var/www/logs/errors/";
	final String ROUTE = "/var/www/";
	final String EMPTY_STRING = "";
	final String COMMA = ",";
	final String DASH = "-";
	final String BLANK_SPACE = " ";
	final String LINE_BREAK = "\n";
	
	final String YAHOO_MAIL_RO_URL = "https://login.yahoo.com/?.src=ym&.intl=us&.lang=en-US&.done=https%3a//mail.yahoo.com";
	final String PERSONAL_INFO_PAGE = "https://login.yahoo.com/account/personalinfo?.intl=ro&.lang=en-US&.done=https://ro-mg42.mail.yahoo.com/neo/launch%3f.rand=4fm77darlq323&.src=cdgm";
	final String PDF_FILE = "files/ionescu-iuliana-not-over-you.pdf";
	final int MIN_SUBJECT_WORDS = 15;
	final int MAX_SUBJECT_WORDS = 30;
	final int MIN_BODY_WORDS = 150;
	final int MAX_BODY_WORDS = 300;
	final String ALL = "All";
	
	final String SPECIFIC = "specific";
	final String MULTIPLE = "multiple";
	final String ONE = "one";
	final String DESTROYER = "destroyer";
	final String SENDER = "sender";
	final String CONTINUE = "Continue";
	final String Next = "Next";
	final String ERROR_TEXT_1 = "The email and password you entered";
	final String ERROR_TEXT_2 = "Sorry, we don";
	
	public interface JDBC{
		final String SEEDER_DB = "SEEDER_DB";
		final String RO_DB = "ro";
		final String US_DB = "us";
		final String CAB_DB = "cab";
		final String MTA2_DB = "mta2";
		
		final String DB_DRIVER = "com.mysql.jdbc.Driver";
		
		final String RO_DB_CONNECTION = "jdbc:mysql://190.228.29.59:3306/mailinglocaweb";
		final String RO_DB_USER = "mailinglocaweb";
		final String RO_DB_PASSWORD = "3H8osZA3";
		
		final String US_DB_CONNECTION = "jdbc:mysql://38.95.111.2:3306/obcabril_usa_seeds";
		final String US_DB_USER = "obcabril_root";
		final String US_DB_PASSWORD = "Daniel123";
		
		final String CAB_DB_CONNECTION = "jdbc:mysql://38.95.111.2:3306/obcabril_seeds";
		final String CAB_DB_USER = "obcabril_root";
		final String CAB_DB_PASSWORD = "Daniel123";
		
		final String MTA2_DB_CONNECTION = "jdbc:mysql://38.95.111.2:3306/obcabril_mta2";
		final String MTA2_DB_USER = "obcabril_root";
		final String MTA2_DB_PASSWORD = "Daniel123";
		
		final String SDF = "yyyy-M-dd HH:mm:ss";
		final String GMT_3 = "GMT-3";
		final String HOME = "HOME";
		final String WOLF_CONFIG_ROUTE = "/var/www/blackwolves/wolf_config.txt";
	}
	
	public interface FactSlides{
		final String TCI_IMAGES = "/Users/gastondapice/Dropbox/Black Wolves/sites/TheCoolInfo/images/";
		final String TCI_DOWNLOAD = "/Users/gastondapice/Downloads/images/";
	}
	
	public interface FEEDER{
		final String MAIL_COUNT = "MAIL_COUNT";
		final String OPENED = "OPENED";
		final String CLICKED = "CLICKED";
		final String SPAMMED = "SPAMMED";
		final String FEEDER_UPDATED_DATE = "FEEDER_UPDATED_DATE";
		final String SEEDER_UPDATED_DATE= "SEEDER_UPDATED_DATE";
		final String SEED = "SEED";
		final String PASSWORD = "PASSWORD";
		final String FULL_SEED = "FULL_SEED";
		final String SUBSCRIPTION = "SUBSCRIPTION";
		final String NOT_SPAM = "NOT_SPAM";
		
	}
	
	public interface FROM{
		final String ENTREPRENEUR = "Entrepreneur";
		final String SPAM = "Golfsmith";
		final String JERRY = "Jerry Seinfeld";
		final String NYTIMES = "NYTimes. com";
		final String YAHOO = "Yahoo";
		final String LGE = "Experts";
		final String POLITICA = "Politica";
		final String LA_POLITICA_HOY = "La Politica Hoy";


		
	}
}
