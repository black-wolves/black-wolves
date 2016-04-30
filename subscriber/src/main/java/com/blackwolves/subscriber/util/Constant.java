package com.blackwolves.subscriber.util;

public interface Constant {

	public static final String HOME_BLACKWOLVES_LOGS = "/home/blackwolves/logs/";
	public static final String IMAGES_PATH = "/var/www/screenshots/";
	public static final String ROUTE_LOGS_ERROR = "/var/www/logs/errors/";
	public static final String ROUTE = "/var/www/";
	public static final String EMPTY_STRING = "";
	public static final String COMMA = ",";
	public static final String DASH = "-";
	public static final String BLANK_SPACE = " ";
	public static final String LINE_BREAK = "\n";
	
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
			final String cooking = "NYTimes-Cooking";
			final String dealBook = "NYTimes-Dealbook";
			final String bits = "NYTimes-Bits";
			final String firstDraft = "NYTimes-FirstDraft";
			final String opinionToday = "NYTimes-OpinionToday";
			final String afterNoonUpdate = "NYTimes-AfternoonUpdate";
			final String theUpshot = "NYTimes-TheUpshot";
			final String nytNowMorning = "NYTimes-NytMorning";
			final String nytNowEvening = "NYTimes-NytEvening";
			final String asian = "NYTimes-Asian";
			final String european = "NYTimes-European";
			final String nyToday = "NYTimes-NyToday";
			final String todayHeadlines = "NYTimes-TodayHeadlines";
		}
	}
	
	public interface Golfsmith{
		final String siteUrl = "http://www.golfsmith.com/";
		final String siteName = "GolfSmith";
	}
	
	public interface TheHerald{
		final String siteUrl = "https://affiliates.eblastengine.com/Widgets/EmailSignup.aspx?wcguid=3E8B8709-AF46-4F2C-AFBA-2D662DFCC337";
		final String siteName = "TheHerald";
	}
	
	public interface NBCNews{
		final String siteUrl = "http://www.nbcnews.com/";
		final String siteName = "NBCNews";
	}
	
	public interface DailyNews{
		final String siteUrl = "http://www.dailynews.com/email_signup";
		final String siteName = "DailyNews";
	}
	
	public interface Forbes{
		final String siteUrl = "http://www.forbes.com/";
		final String siteName = "Forbes";
	}
	
	public interface Mattermark{
		final String siteUrl = "http://mattermark.com/app/Newsletter";
		final String siteName = "Mattermark";
	}
	
	public interface FashionMagazine{
		final String siteUrl = "http://www.fashionmagazine.com/";
		final String siteName = "FashionMagazine";
	}
	
	public interface TheFetch{
		final String siteUrl = "http://thefetch.com/";
		final String siteName = "TheFetch";
	}
	
	public interface TheWashingtonPost{
		final String siteUrl = "https://subscribe.washingtonpost.com/newsletters/#/newsletters";
		final String siteName = "TheWashingtonPost";
	}
	
	public interface TheGolfChannel{
		final String siteUrl = "http://email.thegolfchannel.com/golfchan3/golfchan_reg.action";
		final String siteName = "TheGolfChannel";
	}
	
	public interface NYDailyNews{
		final String siteUrl = "http://link.nydailynews.com/join/4xm/newslettersignup-desktop";
		final String siteName = "NYDailyNews";
	}
	
	public interface TheSkimm{
		final String siteUrl = "http://www.theskimm.com/";
		final String siteName = "TheSkimm";
	}
	
	public interface ReDef{
		final String siteUrl = "https://redef.com/new-subscription";
		final String siteName = "ReDef";
	}
	
	public interface DetroitDailyNews{
		final String siteUrl = "https://account.detroitnews.com/newsletters/";
		final String siteName = "DetroitDailyNews";
	}
	
	public interface SanAntonioNews{
		final String siteUrl = "http://www.mysanantonio.com/news/local/";
		final String siteName = "SanAntonioNews";
	}
	
	public interface HoustonCron{
		final String siteUrl = "http://www.chron.com/newsletters/";
		final String siteName = "HoustonCron";
	}
	
	public interface NBCSanDiego{
		final String siteUrl = "http://www.nbcsandiego.com/newsletters/";
		final String siteName = "NBCSanDiego";
	}
	
	public interface AltoPalermo{
		final String siteUrl = "http://www.altopalermo.com.ar/newsletter-formulario.php";
		final String siteName = "AltoPalermo";
	}
	
	public interface AirBerlin{
		final String siteUrl = "https://www.airberlin.com/en-RO/newsletter/index/email//preselection/profileSubscribe";
		final String siteName = "AirBerlin";
	}
	
	public interface Virgin{
		final String siteUrl = "https://www.virgin.com/newsletter";
		final String siteName = "Virgin";
	}
	
	public interface TomPeters{
		final String siteUrl = "http://tompeters.com/contact/subscribe/";
		final String siteName = "TomPeters";
	}
	
	public interface DanielPink{
		final String siteUrl = "http://www.danpink.com/contact/";
		final String siteName = "DanielPink";
	}
	
}