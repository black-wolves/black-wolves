/**
 * 
 */
package com.blackwolves.seeder;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.blackwolves.persistence.entity.Seed;
import com.blackwolves.persistence.entity.Session;
import com.blackwolves.service.ISeedService;
import com.blackwolves.service.exception.ServiceException;
import com.google.common.collect.Lists;

/**
 * @author gaston.dapice
 *
 */
@Component
public class Seeder {

	private static final String YAHOO_MAIL_RO_URL = "https://login.yahoo.com/?.src=ym&.intl=ro&.lang=ro-RO&.done=https%3a//mail.yahoo.com";
	private static final Logger logger = LogManager.getLogger(Seeder.class.getName());
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
	}

	/**
	 * 
	 * @param myIp
	 * @param mySeed
	 */
	private void checkMail(String myIp, String mySeed) {
		String[] seed = mySeed.split(",");
		
		Seed dbSeed = getSeedFromDb(seed);
		
		Session session = validateLastSession(myIp, dbSeed);
		
		if(session==null){
			logger.info("This seed can't continue the process");
			return;
		}
		
		WebDriver driver = createWebDriver();
		
		logger.info("Firefox Created");
		
		human = generateRandomHumanUser();
		
		logger.info("Human Generated");
		Seeder.getScreenShot(driver, "Before.html");
		
		yahooLogin(YAHOO_MAIL_RO_URL, seed, driver, session);
		
		handler = validateYahooVersion(driver, mySeed);

		if (handler != null) {
			handler.runProcess();
		} else{
			logger.info("New Interface detected.Exiting");
			return;
		}
		try {
			dbSeed.getSessions().add(session);
			seedService.saveOrUpdate(dbSeed);
		} catch (ServiceException e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * @return
	 */
	private WebDriver createWebDriver() {
		logger.info("Creating the web driver");
		DesiredCapabilities caps = new DesiredCapabilities();
		caps.setCapability("binary", "/usr/bin/wires-0.3.0-linux64");
		WebDriver driver = new FirefoxDriver(caps);
		driver.manage().window().maximize();
		return driver;
	}

	/**
	 * Validate if the seed has any session created.
	 * If it doesn't have any it continues the process.
	 * If it has at least one it validates the last date of the session
	 * to see if the seed can continue the process
	 * @param myIp 
	 * @param dbSeed
	 * @return {@link Session}
	 */
	private Session validateLastSession(String myIp, Seed dbSeed) {
		if(dbSeed.getSessions().isEmpty()){
			return new Session(myIp);
		}
		Session session = dbSeed.getSessions().iterator().next();
		if(calculateDifferenceBetweenDates(session.getLastDate(), new Date()) <= dbSeed.getProfile().getHoursNextLogin()){
			logger.info("Last time the seed was logged it was less than " + dbSeed.getProfile().getHoursNextLogin() + " hours");
			return null;
		}
		return new Session(myIp);
	}
	
	/**
	 * Calculates the hours of difference between the two given dates
	 * @param from
	 * @param to
	 * @return int
	 */
	private int calculateDifferenceBetweenDates(Date from, Date to){
		long diff = to.getTime() - from.getTime();
		int diffHours = (int) (diff / (60 * 60 * 1000));
//		int diffDays = (int) (diff / (24 * 60 * 60 * 1000));
//		int diffMin = (int) (diff / (60 * 1000));
//		int diffSec = (int) (diff / (1000));
		return diffHours;
	}

	/**
	 * @param seed
	 * @return
	 */
	private Seed getSeedFromDb(String[] seed) {
		Seed dbSeed = null;
		try {
			dbSeed = seedService.findByEmail(seed[0]);
		} catch (ServiceException e) {
			logger.error(e.getMessage(), e);
		}
		
		if(dbSeed==null){
			try {
				dbSeed = seedService.insertSeedInDB(seed);
			} catch (ServiceException e) {
				logger.error(e.getMessage(), e);
			}
		}
		return dbSeed;
	}

	private static Human generateRandomHumanUser() {
		logger.info("Random Human generation started");
		int number =  YahooRunnable.randInt(0, 10);
		if(number <= 3)
		{
			return new DumbHuman();
		}
		else if (number >=4  && number <= 8) {
			return new AverageHuman();
		}
		return new FastHuman();
	}

	public void suscribeToNewsletters() {
		final int THREADS = 1;

		List<String[]> seeds = YahooRunnable.generateSeedsList();
		List<String[]> ips = YahooRunnable.generateIpsList();

		List<List<String[]>> partitions = Lists.partition(seeds, 10);

		final Random ipRandomizer = new Random();

		ExecutorService executor = Executors.newFixedThreadPool(THREADS);
		for (final List<String[]> partition : partitions) {

			Runnable suscriber = new SuscriberRunnable(partition, ips, ipRandomizer);
			executor.execute(suscriber);
		}

		executor.shutdownNow();
		// Wait until all threads are finish
		while (!executor.isTerminated()) {

		}
		logger.info("\nFinished all threads");

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
			human.type(accountInput,seed[0]);
			
			logger.info("Introducing password: " + seed[1]);
			WebElement passwordInput = driver.findElement(By.id("login-passwd"));
			human.type(passwordInput, seed[1]);
			
			logger.info("Clicking login button");
			if(driver.findElements(By.id("login-signin")).size() > 0){
				driver.findElement(By.id("login-signin")).click();
			} else {
				logger.info("Already logged in..Moving forward!");
			}
		}catch (InterruptedException e) {
			logger.error("Thread was interrupted at login");
			logger.error(e.getMessage(), e);
		}catch (Exception e) {
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
			getScreenShot(driver, "AfterLogin");
			Thread.sleep(10000);
			if (driver.findElements(By.className("uh-srch-btn")).size() > 0) {
				logger.info("**********   Old yahoo version   **********");
				handler = new OldYahooRunnable(driver, seed, human);

			} else if (driver.findElements(By.id("UHSearchProperty")).size() > 0) {
				logger.info("**********   New yahoo 2 version   **********");
				handler = new ModernYahooRunnable(driver, seed, human);
				// SuscriberRunnable.writeToFile("new_yahoo_2_version.html",
				// driver.getPageSource());
			} else if (driver.findElements(By.id("mail-search-btn")).size() > 0) {
				logger.info("**********   New yahoo version   **********");
				// SuscriberRunnable.writeToFile("new_yahoo_version.html",
				// driver.getPageSource());
			} else {
				logger.info("**********   There is a new yahoo version in town  **********");
				// SuscriberRunnable.writeToFile("new_version_in_town.html",
				// driver.getPageSource());
			}
		} catch (InterruptedException e) {
			logger.error(e.getMessage(), e);
		}
		return handler;
	}

	public static void getScreenShot(WebDriver driver, String name) {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		// Now you can do whatever you need to do with it, for example copy
		// somewhere
		try {
			FileUtils.copyFile(scrFile, new File(IMAGES_PATH + name + ".jpg"));
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}

	}
	
	public void getFingerPrint() {
		DesiredCapabilities caps = new DesiredCapabilities();
		caps.setCapability("binary", "/usr/bin/wires-0.3.0-linux64");
		logger.info("Creating new driver");
		WebDriver driver = new FirefoxDriver(caps);
		String url = "https://panopticlick.eff.org/";
		driver.get(url);
		WebElement clickMe =  driver.findElement(By.id("clicklink"));
		clickMe.click();
	}
}