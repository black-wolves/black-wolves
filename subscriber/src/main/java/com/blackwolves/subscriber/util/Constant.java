package com.blackwolves.subscriber.util;

public interface Constant {

	public static final String IMAGES_PATH = "/var/www/screenshots/";
	public static final String ROUTE = "/var/www/";
	public static final String EMPTY_STRING = "";
	public static final String HOME_BLACKWOLVES_LOGS = "/home/blackwolves/logs/";
	
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
	
	public interface FEEDER{
		final String SEED = "SEED";
		final String PASSWORD = "PASSWORD";
		final String FULL_SEED = "FULL_SEED";
		final String SUBSCRIPTION = "SUBSCRIPTION";
	}
	
	public interface NyTimes{
		final String cooking = "http://www.nytimes.com/newsletters/cooking?pgtype=subscriptionspage&version=lifestyle&contentId=CK&eventName=signup&module=newsletter-sign-up&region=button";
		final String dealBook = "http://www.nytimes.com/newsletters/dealbook?pgtype=subscriptionspage&version=business&contentId=DK&eventName=signup&module=newsletter-sign-up&region=button";
		final String bits = "http://www.nytimes.com/newsletters/bits?pgtype=subscriptionspage&version=business&contentId=TU&eventName=signup&module=newsletter-sign-up&region=button";
		final String firstDraft = "http://www.nytimes.com/newsletters/politics?pgtype=subscriptionspage&version=news&contentId=CN&eventName=signup&module=newsletter-sign-up&region=button";
		final String opinionToday = "http://www.nytimes.com/newsletters/opiniontoday?pgtype=subscriptionspage&version=news&contentId=TY&eventName=signup&module=newsletter-sign-up&region=button";
		final String afterNoonUpdate = "http://www.nytimes.com/newsletters/afternoonupdate?pgtype=subscriptionspage&version=news&contentId=AU&eventName=signup&module=newsletter-sign-up&region=button";
		final String theUpshot = "http://www.nytimes.com/newsletters/upshot?pgtype=subscriptionspage&version=news&contentId=UP&eventName=signup&module=newsletter-sign-up&region=button";
		final String nytNowMorning = "http://www.nytimes.com/newsletters/nyt-now-morning-briefing?pgtype=subscriptionspage&version=news&contentId=NN&eventName=signup&module=newsletter-sign-up&region=button";
		final String nytNowEvening = "http://www.nytimes.com/newsletters/nyt-now-evening-briefing?pgtype=subscriptionspage&version=new&contentId=NE&eventName=signup&module=newsletter-sign-up&region=button";
		final String asian = "http://www.nytimes.com/newsletters/asianheadlines?pgtype=subscriptionspage&version=news&contentId=AE&eventName=signup&module=newsletter-sign-up&region=button";
		final String european = "http://www.nytimes.com/newsletters/europeanheadlines?pgtype=subscriptionspage&version=news&contentId=EE&eventName=signup&module=newsletter-sign-up&region=button";
		final String nyToday = "http://www.nytimes.com/newsletters/newyorktoday?pgtype=subscriptionspage&version=news&contentId=UR&eventName=signup&module=newsletter-sign-up&region=button";
		final String todayHeadlines = "http://www.nytimes.com/newsletters/todaysheadlines?pgtype=subscriptionspage&version=news&contentId=TH&eventName=signup&module=newsletter-sign-up&region=button";
	}
}
