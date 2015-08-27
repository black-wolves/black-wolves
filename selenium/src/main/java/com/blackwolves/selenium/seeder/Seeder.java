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

import org.apache.commons.io.FileUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import au.com.bytecode.opencsv.CSVReader;

import com.gargoylesoftware.htmlunit.protocol.about.Handler;
import com.google.common.collect.Lists;

/**
 * @author gaston.dapice
 *
 */
@Component
public class Seeder {

	private static final Logger logger = LogManager.getLogger(Seeder.class.getName());
	private static final String ROUTE = "/var/www/";
	private static String IMAGES_PATH = "/var/www/screenshots/";

	private static YahooRunnable handler;

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
		logger.info("Creating new driver");
		WebDriver driver = new FirefoxDriver(caps);
		String yahooUrl = "https://login.yahoo.com/?.src=ym&.intl=ro&.lang=ro-RO&.done=https%3a//mail.yahoo.com";
		try {
			// Maximize Window
			// driver.manage().window().maximize();
			logger.info("Trying to login in....");
			yahooLogin(yahooUrl, seed, driver);
			handler = validateYahooVersion(driver, mySeed);
		} catch (Exception e) {
			logger.info("Something went wrong at login");
		}

		if (handler != null) {
			ExecutorService executor = Executors.newFixedThreadPool(THREADS);
			executor.execute(handler);
			executor.shutdown();

			// Wait until all threads are finish
			while (!executor.isTerminated()) {

			}
			executor.shutdownNow();
			driver.close();;
			logger.info("Shutting down browser...");

		}
		else
			logger.info("New Interface detected.Exiting");
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
		getScreenShot(driver, "START");
		logger.info("Getting to the url: " + yahooUrl);
		driver.get(yahooUrl);
		getScreenShot(driver, "AT_LOGIN_PAGE");
		logger.info("Introducing username: " + seed[0]);
		driver.findElement(By.id("login-username")).clear();
		driver.findElement(By.id("login-username")).sendKeys(seed[0]);
		getScreenShot(driver, "AFTER_LOGIN_NAME");

		logger.info("Introducing password: " + seed[1]);
		driver.findElement(By.id("login-passwd")).clear();
		driver.findElement(By.id("login-passwd")).sendKeys(seed[1]);
		getScreenShot(driver, "AFTER_SETTING_PASS");

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
				//SuscriberRunnable.writeToFile("new_version_in_town.html", driver.getPageSource());
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
