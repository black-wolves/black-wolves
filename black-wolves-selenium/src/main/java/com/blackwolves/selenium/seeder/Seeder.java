/**
 * 
 */
package com.blackwolves.selenium.seeder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

import au.com.bytecode.opencsv.CSVReader;

/**
 * @author gaston.dapice
 *
 */
@Component
public class Seeder {

	private static final String YAHOO_MAIL_RO_URL = "https://login.yahoo.com/?.src=ym&.intl=ro&.lang=ro-RO&.done=https%3a//mail.yahoo.com";
	private static final Logger logger = LogManager.getLogger(Seeder.class.getName());
	private static final String ROUTE = "/var/www/";
	private static String IMAGES_PATH = "/var/www/screenshots/";

	private static YahooRunnable handler;
	
	private static Human human;

	public static void main(String[] args) {
		checkMail(args[0], args[1]);
	}

	/**
	 * 
	 * @param myIp
	 * @param mySeed
	 */
	private static void checkMail(String myIp, String mySeed) {
		final int THREADS = 1;
		String[] seed = mySeed.split(",");
		DesiredCapabilities caps = new DesiredCapabilities();
		caps.setCapability("binary", "/usr/bin/wires-0.3.0-linux64");
		
		logger.info("Random Human generation started");
		human = generateRandomHumanUser();
		
		logger.info("Creating new driver");
		WebDriver driver = new FirefoxDriver(caps);
		String yahooUrl = YAHOO_MAIL_RO_URL;
		try {
			// Maximize Window
			driver.manage().window().maximize();
			logger.info("Trying to login in....");
			yahooLogin(yahooUrl, seed, driver);
			handler = validateYahooVersion(driver, mySeed);
		} catch (Exception e) {
			logger.error("Something went wrong at login");
		}

		if (handler != null) {
			ExecutorService pool = Executors.newFixedThreadPool(THREADS);
			pool.execute(handler);

			pool.shutdown(); // Disable new tasks from being submitted
			try {
				// Wait a while for existing tasks to terminate
				if (!pool.awaitTermination(4, TimeUnit.MINUTES)) {
					pool.shutdownNow(); // Cancel currently executing tasks
					// Wait a while for tasks to respond to being cancelled
					if (!pool.awaitTermination(60, TimeUnit.SECONDS))
						logger.info("Pool did not terminate");
				}
			} catch (InterruptedException ie) {
				// (Re-)Cancel if current thread also interrupted
				pool.shutdownNow();
				// Preserve interrupt status
				Thread.currentThread().interrupt();
			}

		} else
			logger.info("New Interface detected.Exiting");
			System.exit(0);
	}

	private static Human generateRandomHumanUser() {
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

		List<String[]> seeds = generateSeedsList();
		List<String[]> ips = generateIpsList();

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
	 * @throws IOException
	 * @throws InterruptedException
	 */
	private static void yahooLogin(String yahooUrl, String[] seed, WebDriver driver)
			throws IOException, InterruptedException {
		logger.info("Getting to the url: " + yahooUrl);
		driver.get(yahooUrl);
		//getScreenShot(driver, "AT_LOGIN_PAGE");
		logger.info("Introducing username: " + seed[0]);
		WebElement accountInput = driver.findElement(By.id("login-username"));
		human.type(accountInput,seed[0], driver);
		
		getScreenShot(driver, "AFTER_LOGIN_NAME");

		logger.info("Introducing password: " + seed[1]);
		WebElement passwordInput = driver.findElement(By.id("login-passwd"));
		human.type(passwordInput, seed[1], driver);
		
		logger.info("Clicking login button");
		getScreenShot(driver, "before-click");
		SuscriberRunnable.writeToFile(IMAGES_PATH + "before-click.html", driver.getPageSource());

		try {
			driver.findElement(By.id("login-signin")).click();
		} catch (Exception e) {
			logger.info("Already logged in..Moving forward!");
			// Thread.sleep(5000);
			// driver.findElement(By.id("login-signin")).click();
		}

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
				handler = new OldYahooRunnable(driver, seed);

			} else if (driver.findElements(By.id("UHSearchProperty")).size() > 0) {
				logger.info("**********   New yahoo 2 version   **********");
				handler = new ModernYahooRunnable(driver, seed);
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void getFingerPrint()
	{
		DesiredCapabilities caps = new DesiredCapabilities();
		caps.setCapability("binary", "/usr/bin/wires-0.3.0-linux64");
		logger.info("Creating new driver");
		WebDriver driver = new FirefoxDriver(caps);
		String url = "https://panopticlick.eff.org/";
		driver.get(url);
		WebElement clickMe =  driver.findElement(By.id("clicklink"));
		clickMe.click();
		
	}

	/**
	 * @return
	 */
	private static List<String[]> generateIpsList() {
		List<String[]> ips = new ArrayList<String[]>();
		try {
			CSVReader ipsReader = new CSVReader(new FileReader(ROUTE + "ip_curl.txt"));
			ips = ipsReader.readAll();
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return ips;
	}

	/**
	 * @return
	 */
	private static List<String[]> generateSeedsList() {
		List<String[]> seeds = new ArrayList<String[]>();
		try {
			CSVReader seedsReader = new CSVReader(new FileReader(ROUTE + "seeds.csv"));
			seeds = seedsReader.readAll();
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return seeds;
	}
}