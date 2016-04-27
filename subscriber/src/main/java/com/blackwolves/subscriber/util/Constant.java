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
		public interface SiteUrl{
			final String cooking = "https://regilite.nytimes.com/regilite?product=CK&theme=Transparent&landing=true&app=newsletter&interface=sign_up_page&sourceApp=nyt-v5&title=Cooking";
			final String dealBook = "https://regilite.nytimes.com/regilite?product=DK&theme=Transparent&landing=true&app=newsletter&interface=sign_up_page&sourceApp=nyt-v5&title=DealBook";
			final String bits = "https://regilite.nytimes.com/regilite?product=TU&theme=Transparent&landing=true&app=newsletter&interface=sign_up_page&sourceApp=nyt-v5&title=Bits";
			final String firstDraft = "https://regilite.nytimes.com/regilite?product=CN&theme=Transparent&landing=true&app=newsletter&interface=sign_up_page&sourceApp=nyt-v5&title=First+Draft";
			final String opinionToday = "https://regilite.nytimes.com/regilite?product=TY&theme=Transparent&landing=true&app=newsletter&interface=sign_up_page&sourceApp=nyt-v5&title=Opinion+Today";
			final String afterNoonUpdate = "https://regilite.nytimes.com/regilite?product=AU&theme=Transparent&landing=true&app=newsletter&interface=sign_up_page&sourceApp=nyt-v5&title=Afternoon+Update";
			final String theUpshot = "https://regilite.nytimes.com/regilite?product=UP&theme=Transparent&landing=true&app=newsletter&interface=sign_up_page&sourceApp=nyt-v5&title=The+Upshot";
			final String nytNowMorning = "https://regilite.nytimes.com/regilite?product=NN&theme=Transparent&landing=true&app=newsletter&interface=sign_up_page&sourceApp=nyt-v5&title=The+NYT+Now+Morning+Briefing";
			final String nytNowEvening = "https://regilite.nytimes.com/regilite?product=NE&theme=Transparent&landing=true&app=newsletter&interface=sign_up_page&sourceApp=nyt-v5&title=The+NYT+Now+Evening+Briefing";
			final String asian = "https://regilite.nytimes.com/regilite?product=AE&theme=Transparent&landing=true&app=newsletter&interface=sign_up_page&sourceApp=nyt-v5&title=Today%E2%80%99s+Headlines+Asian+Morning";
			final String european = "https://regilite.nytimes.com/regilite?product=EE&theme=Transparent&landing=true&app=newsletter&interface=sign_up_page&sourceApp=nyt-v5&title=Today%E2%80%99s+Headlines+European+Morning";
			final String nyToday = "https://regilite.nytimes.com/regilite?product=UR&theme=Transparent&landing=true&app=newsletter&interface=sign_up_page&sourceApp=nyt-v5&title=New+York+Today";
			final String todayHeadlines = "https://regilite.nytimes.com/regilite?product=TH&theme=Transparent&landing=true&app=newsletter&interface=sign_up_page&sourceApp=nyt-v5&title=Today%E2%80%99s+Headlines";
		}
		
		public interface SiteName{
			final String cooking = "NYTimes-Cooking,";
			final String dealBook = "NYTimes-Dealbook,";
			final String bits = "NYTimes-Bits,";
			final String firstDraft = "NYTimes-FirstDraft,";
			final String opinionToday = "NYTimes-OpinionToday,";
			final String afterNoonUpdate = "NYTimes-AfternoonUpdate,";
			final String theUpshot = "NYTimes-TheUpshot,";
			final String nytNowMorning = "NYTimes-NytMorning,";
			final String nytNowEvening = "NYTimes-NytEvening,";
			final String asian = "NYTimes-Asian,";
			final String european = "NYTimes-European,";
			final String nyToday = "NYTimes-NyToday,";
			final String todayHeadlines = "NYTimes-TodayHeadlines,";
		}
	}
}
