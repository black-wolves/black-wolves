package com.blackwolves.mail.util;

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
	final String TRUE = "true";
	final String FALSE = "false";
	final int MAX_SPAM = 20;
	final int DEFAULT_THREADS = 25;
	final String VALIDATE = "validate";

	public interface Yahoo{
		final String IMAP_YAHOO = "imap.mail.yahoo.com";
		final int IMAP_PORT = 993;
		final String PICKUP_ROUTE = "/ramcache/pmta/pickup/";
		final String BLACKWOLVES_ROUTE = "/root/blackwolves/bodies/";
		final String INBOX = "Inbox";
		final String SPAM = "Bulk Mail";
		final String BODIES = "Bodies";

		final String HOST = "smtp.mail.yahoo.com";
		final String PORT = "587";
		final String CONTENT_TYPE = "text/html; charset=utf-8";
		final String CONTENT_TRANSFER_ENCODING = "7bit";
	
		final String YAHOO_MAIL_RO_URL = "https://login.yahoo.com/?.src=ym&.intl=us&.lang=en-US&.done=https%3a//mail.yahoo.com";
		final String PERSONAL_INFO_PAGE = "https://login.yahoo.com/account/personalinfo?.intl=ro&.lang=en-US&.done=https://ro-mg42.mail.yahoo.com/neo/launch%3f.rand=4fm77darlq323&.src=cdgm";
		final String CONTINUE = "Continue";
		final String Next = "Next";
		final String ERROR_TEXT_1 = "The email and password you entered";
		final String ERROR_TEXT_2 = "Sorry, we don";
	}
	
	
	
	public interface JDBC{
		final String SEEDER_DB = "SEEDER_DB";
		final String RO_DB = "ro";
		final String US_DB = "us";
		final String CAB_DB = "cab";
		
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
		
		final String SDF = "yyyy-M-dd HH:mm:ss";
		final String GMT_3 = "GMT-3";
		final String HOME = "HOME";
		final String WOLF_CONFIG_ROUTE = "/var/www/blackwolves/wolf_config.txt";
	}
	
	public interface Feeder{
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
		final String FILE = "file";
	}
	
	public interface Facebook{
		final String FACEBOOK_URL = "https://www.facebook.com/";
		final String POST = "post";
		final String CREATE = "create";
		final String MALE = "male";
		final String FACEBOOK_NAME = "facebook";
		final String FIND_FRIENDS = "Find Friends";
		final String SECURITY_TEXT = "Please Complete a Security Check";
	}
	
	public interface Subscriber{
		final String DanielPink = "DanielPink";
		final String DanielPink_Search = "Daniel Pink";
		final String AirBerlin = "AirBerlin";
		final String AirBerlin_Search = "airberlin";
		final String AirBerlin_FlagValue = "en-US";
	}
	
	public interface Confirmation{
		final String Facebook = "confirmemail";
		final String DanielPink = "confirm";
		final String AirBerlin = "Confirm & edit profile";
	}
	
}
