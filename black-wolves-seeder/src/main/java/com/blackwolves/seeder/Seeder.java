/**
 * 
 */
package com.blackwolves.seeder;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
//github.com/black-wolves/black-wolves.git
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.blackwolves.persistence.entity.Seed;
import com.blackwolves.persistence.entity.Session;
import com.blackwolves.service.ISeedService;

/**
 * @author gaston.dapice
 *
 */
@Component
public class Seeder {

	private static final Logger logger = LogManager.getLogger(Seeder.class.getName());

	private static final String YAHOO_MAIL_RO_URL = "https://login.yahoo.com/?.src=ym&.intl=ro&.lang=ro-RO&.done=https%3a//mail.yahoo.com";
	private static String IMAGES_PATH = "/var/www/screenshots/";

	@Autowired
	private ISeedService seedService;

	private static YahooRunnable handler;

	private static Human human;
	private static ApplicationContext context;

	public static void main(String[] args) {
		context = new ClassPathXmlApplicationContext("classpath:application-context.xml");
		Seeder seeder = context.getBean(Seeder.class);
		seeder.checkMail(args[0], args[1]);
		logger.info("Finished checking mails");
		return;
	}

	/**
	 * 
	 * @param myIp
	 * @param mySeed
	 */
	private void checkMail(String myIp, String mySeed) {

		WebDriver driver = createWebDriver();

		logger.info("Firefox Created");

		human = generateRandomHumanUser();

		driver.get("http://www.useragentstring.com/");
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		// The below method will save the screen shot in d drive with name
		// "screenshot.png"
		try {
			FileUtils.copyFile(scrFile, new File("/home/blackwolves/screenshot_1.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// String[] seed = mySeed.split(",");
		//
		// Seed dbSeed = seedService.getSeedFromDb(seed);
		//
		// Session session = validateLastSession(myIp, dbSeed);
		//
		// if(session==null){
		// logger.info("This seed can't continue the process");
		// return;
		// }else{
		// WebDriver driver = createWebDriver();
		//
		// logger.info("Firefox Created");
		//
		// human = generateRandomHumanUser();
		//
		// yahooLogin(YAHOO_MAIL_RO_URL, seed, driver, session);
		//
		// handler = validateYahooVersion(driver, mySeed);
		//
		// if (handler != null) {
		// handler.runProcess();
		// try {
		// dbSeed.getSessions().add(session);
		// logger.info("Saving seed session in the database");
		// seedService.saveOrUpdate(dbSeed);
		// } catch (ServiceException e) {
		// logger.error(e.getMessage(), e);
		// } finally{
		// driver.quit();
		// logger.info("Thread should end now.");
		// }
		// } else{
		// logger.info("New Interface detected.Exiting");
		// driver.quit();
		// return;
		// }
		// }
	}

	/**
	 * @return
	 */
	private WebDriver createWebDriver() {
		logger.info("Creating the web driver");
		
		
		 FirefoxProfile profile = new FirefoxProfile();
		 File modifyHeaders = new File(ClassLoader.getSystemResource("modify_headers.xpi").getFile()); 
		 profile.setEnableNativeEvents(false); 
		  try {
		    profile.addExtension(modifyHeaders); 
		  } catch (IOException e) {
		    e.printStackTrace(); 
		  }
		  profile.setPreference("modifyheaders.headers.count", 1);
		   profile.setPreference("modifyheaders.headers.action0", "Add");
		   profile.setPreference("modifyheaders.headers.name0", "sox");
		   profile.setPreference("modifyheaders.headers.value0", "305471");
		   profile.setPreference("modifyheaders.headers.enabled0", true);
		   profile.setPreference("modifyheaders.config.active", true);
		   profile.setPreference("modifyheaders.config.alwaysOn", true);
		   profile.setPreference("general.useragent.override", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.10; rv:40.0) Gecko/20100101 Firefox/40.0");

		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setPlatform(org.openqa.selenium.Platform.ANY);
		capabilities.setCapability(FirefoxDriver.PROFILE, profile);

		// caps.setCapability("applicationCacheEnabled", false);
		// String PROXY = "192.168.1.111:8888";
		// org.openqa.selenium.Proxy proxy = new org.openqa.selenium.Proxy();
		// proxy.setHttpProxy(PROXY)
		// .setFtpProxy(PROXY)
		// .setSslProxy(PROXY);
		// caps.setCapability(CapabilityType.PROXY, proxy);
		WebDriver driver = new FirefoxDriver(capabilities);

		driver.manage().window().maximize();
		return driver;
	}

	/**
	 * Validate if the seed has any session created. If it doesn't have any it
	 * continues the process. If it has at least one it validates the last date
	 * of the session to see if the seed can continue the process
	 * 
	 * @param myIp
	 * @param dbSeed
	 * @return {@link Session}
	 */
	private Session validateLastSession(String myIp, Seed dbSeed) {
		if (dbSeed.getSessions().isEmpty()) {
			logger.info("Creating new session with IP: " + myIp);
			return new Session(myIp);
		}
		Session session = dbSeed.getSessions().iterator().next();
		if (calculateDifferenceBetweenDates(session.getLastDate(), new Date()) <= dbSeed.getProfile()
				.getHoursNextLogin()) {
			logger.info("Last time the seed was logged it was less than " + dbSeed.getProfile().getHoursNextLogin()
					+ " hours");
			return null;
		}
		logger.info("Creating new session with IP: " + myIp);
		return new Session(myIp);
	}

	/**
	 * Calculates the hours of difference between the two given dates
	 * 
	 * @param from
	 * @param to
	 * @return int
	 */
	private int calculateDifferenceBetweenDates(Date from, Date to) {
		long diff = to.getTime() - from.getTime();
		int diffHours = (int) (diff / (60 * 60 * 1000));
		// int diffDays = (int) (diff / (24 * 60 * 60 * 1000));
		// int diffMin = (int) (diff / (60 * 1000));
		// int diffSec = (int) (diff / (1000));
		return diffHours;
	}

	private static Human generateRandomHumanUser() {
		logger.info("Random Human generation started");
		int number = YahooRunnable.randInt(0, 10);
		if (number <= 3) {
			return new DumbHuman();
		} else if (number >= 4 && number <= 8) {
			return new AverageHuman();
		}
		return new FastHuman();
	}

	/**
	 * @param yahooUrl
	 * @param seed
	 * @param driver
	 * @param session
	 * @throws IOException
	 * @throws InterruptedException
	 */
	private static void yahooLogin(String yahooUrl, String[] seed, WebDriver driver, Session session) {
		logger.info("Trying to login in....");
		try {
			logger.info("Getting to the url: " + yahooUrl);
			driver.get(yahooUrl);

			logger.info("Introducing username: " + seed[0]);
			WebElement accountInput = driver.findElement(By.id("login-username"));
			human.type(accountInput, seed[0]);

			logger.info("Introducing password: " + seed[1]);
			WebElement passwordInput = driver.findElement(By.id("login-passwd"));
			human.type(passwordInput, seed[1]);

			logger.info("Clicking login button");
			if (driver.findElements(By.id("login-signin")).size() > 0) {
				driver.findElement(By.id("login-signin")).click();
			} else {
				logger.info("Already logged in..Moving forward!");
			}
		} catch (NoSuchElementException e) {
			logger.error("Enter was already pressed...Moving forward!");
			// logger.error(e.getMessage(), e);
		} catch (Exception e) {
			logger.error("Something went wrong at login");
			logger.error(e.getMessage(), e);
		}
		YahooRunnable.addActionToSession(session, "login");
	}

	/**
	 * 
	 * @param driver
	 * @param seed
	 */
	private static YahooRunnable validateYahooVersion(WebDriver driver, String seed) {

		try {
			Thread.sleep(10000);
			if (driver.findElements(By.className("uh-srch-btn")).size() > 0) {
				logger.info("**********   Old yahoo version   **********");
				handler = new OldYahooRunnable(driver, seed, human);

			} else if (driver.findElements(By.id("UHSearchProperty")).size() > 0) {
				logger.info("**********   New yahoo 2 version   **********");
				handler = new ModernYahooRunnable(driver, seed, human);
				// SubscriberRunnable.writeToFile("new_yahoo_2_version.html",
				// driver.getPageSource());
			} else if (driver.findElements(By.id("mail-search-btn")).size() > 0) {
				logger.info("**********   New yahoo version   **********");
				// SubscriberRunnable.writeToFile("new_yahoo_version.html",
				// driver.getPageSource());
			} else {
				logger.info("**********   There is a new yahoo version in town  **********");
				// SubscriberRunnable.writeToFile("new_version_in_town.html",
				// driver.getPageSource());
			}
		} catch (InterruptedException e) {
			logger.error(e.getMessage(), e);
		}
		return handler;
	}
	//
	// public static void getScreenShot(WebDriver driver, String name) {
	// File scrFile = ((TakesScreenshot)
	// driver).getScreenshotAs(OutputType.FILE);
	// // Now you can do whatever you need to do with it, for example copy
	// // somewhere
	// try {
	// FileUtils.copyFile(scrFile, new File(IMAGES_PATH + name + ".jpg"));
	// } catch (IOException e) {
	// logger.error(e.getMessage(), e);
	// }
	//
	// }

	public void getFingerPrint() {
		DesiredCapabilities caps = new DesiredCapabilities();
		caps.setCapability("binary", "/usr/bin/wires-0.3.0-linux64");
		logger.info("Creating new driver");
		WebDriver driver = new FirefoxDriver(caps);
		String url = "https://panopticlick.eff.org/";
		driver.get(url);
		WebElement clickMe = driver.findElement(By.id("clicklink"));
		clickMe.click();
	}
}
