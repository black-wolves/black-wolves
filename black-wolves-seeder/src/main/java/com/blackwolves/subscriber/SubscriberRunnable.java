package com.blackwolves.subscriber;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blackwolves.persistence.entity.Seed;
import com.blackwolves.persistence.entity.Subscription;
import com.blackwolves.service.ISeedService;
import com.blackwolves.service.exception.ServiceException;

/**
 * @author daniel.grane
 *
 */
@Component
public class SubscriberRunnable {

	private static final String HTML_LOCATION = "/root/htmls/";

	private static final Logger logger = LogManager.getLogger(SubscriberRunnable.class.getName());

	@Autowired
	private ISeedService seedService;

	public SubscriberRunnable() {

	}

	/**
	 * 
	 * @param mySeed
	 */
	public void runProcess(String mySeed) {
		System.out.println(mySeed);
		String[] seed = mySeed.split(",");
		Seed dbSeed = new Seed(seed[0], seed[1]); // seedService.getSeedFromDb(seed);
		System.setProperty("webdriver.chrome.driver","/Applications/chromedriver");
		logger.info("Creating the driver");
		WebDriver driver=new ChromeDriver();

		try {
			// subscribeToSkimm(dbSeed, driver);
			// subscribeToMatterMark(dbSeed, driver);
			// subscribeFashionMagazine(dbSeed, driver);
			// subscribeToDigg(dbSeed, driver);

			subscribeToTheWeek(dbSeed, driver);
			subscribeToRentalCars(dbSeed, driver);
			subscribeToHollister(dbSeed, driver);
		}

		catch (NoSuchElementException e) {
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			driver.quit();
		}
	}

	// Works! :)
	private void subscribeToSkimm(Seed dbSeed, WebDriver driver) throws InterruptedException {
		String url = "https://www.avantrip.com/";
		if (isSubscribed(dbSeed, url)) {
			logger.info("This seed is already subscribe to: " + url);
			return;
		}
		logger.info("Subscribing to The Skimm");
		driver.get(url);

		List<WebElement> listOfElements = driver.findElements(By.xpath("//input"));
		// driver.findElement(By.className("styled__StyledInput-s1dmtr08-0
		// buKiPa")).clear();
		// driver.findElement(By.name("email")).sendKeys(dbSeed.getEmail());
		// driver.findElement(By.name("email")).submit();
		// Subscription subscription = new Subscription("The Skimm", url);
		// dbSeed.getSubscriptions().add(subscription);
		// try {
		// seedService.saveOrUpdate(dbSeed);
		// logger.info("Successfully subscribed to: " + url);
		// } catch (ServiceException e) {
		// logger.error(e.getMessage(), e);
		// }
		Thread.sleep(3000);
	}

	// Works :)
	private void subscribeToMatterMark(Seed dbSeed, WebDriver driver) throws InterruptedException {
		String url = "http://mattermark.com/app/Newsletter";
		if (isSubscribed(dbSeed, url)) {
			logger.info("This seed is already subscribe to: " + url);
			return;
		}
		logger.info("Subscribing to Mattermark");
		driver.get(url);
		driver.findElement(By.id("free_email")).sendKeys(dbSeed.getEmail());
		driver.findElement(By.id("free_email")).submit();
		Subscription subscription = new Subscription("Mattermark", url);
		dbSeed.getSubscriptions().add(subscription);
		try {
			seedService.saveOrUpdate(dbSeed);
			logger.info("Successfully subscribed to: " + url);
		} catch (ServiceException e) {
			logger.error(e.getMessage(), e);
		}
		Thread.sleep(3000);

	}

	// Works :)
	private void subscribeFashionMagazine(Seed dbSeed, WebDriver driver) throws InterruptedException {
		String url = "http://www.fashionmagazine.com/";
		if (isSubscribed(dbSeed, url)) {
			logger.info("This seed is already subscribe to: " + url);
			return;
		}
		logger.info("Subscribing to Fashion Magazine");
		driver.get(url);
		driver.findElement(By.name("input_1")).clear();
		driver.findElement(By.name("input_1")).sendKeys(dbSeed.getEmail());
		driver.findElement(By.name("input_1")).submit();
		if (driver.getPageSource().contains("Thanks")) {
			logger.info("Fashion Magazine Subscription succesful.");
		}
		Subscription subscription = new Subscription("Fashion Magazine", url);
		dbSeed.getSubscriptions().add(subscription);
		try {
			seedService.saveOrUpdate(dbSeed);
			logger.info("Successfully subscribed to: " + url);
		} catch (ServiceException e) {
			logger.error(e.getMessage(), e);
		}
		Thread.sleep(3000);
	}

	// Works! :)
	private void subscribeToDigg(Seed dbSeed, WebDriver driver) throws InterruptedException {
		String url = "http://digg.com/";
		if (isSubscribed(dbSeed, url)) {
			logger.info("This seed is already subscribe to: " + url);
			return;
		}
		logger.info("Subscribing to Digg");
		driver.get(url);
		driver.findElement(By.name("email")).sendKeys(dbSeed.getEmail());
		driver.findElement(By.id("daily-digg-email-submit-btn")).click();
		if (driver.getPageSource().contains("Thanks for subscribing!")) {
			logger.info("Digg Subscription succesful.");
		}
		Subscription subscription = new Subscription("Digg", url);
		dbSeed.getSubscriptions().add(subscription);
		try {
			seedService.saveOrUpdate(dbSeed);
			logger.info("Successfully subscribed to: " + url);
		} catch (ServiceException e) {
			logger.error(e.getMessage(), e);
		}
		Thread.sleep(3000);
	}

	// Works! :)
	private void subscribeToTheWeek(Seed dbSeed, WebDriver driver) throws InterruptedException {
		String url = "http://theweek.com/";
		if (isSubscribed(dbSeed, url)) {
			logger.info("This seed is already subscribe to: " + url);
			return;
		}
		logger.info("Subscribing to The Week");
		driver.get(url);
		driver.manage().window().maximize();
		Thread.sleep(5000);

		// driver.findElement(By.name("email")).clear();
		driver.findElement(By.name("email")).sendKeys(dbSeed.getEmail());
		driver.findElement(By.name("email")).submit();
		Subscription subscription = new Subscription("The Week", url);
		dbSeed.getSubscriptions().add(subscription);
//		try {
//			seedService.saveOrUpdate(dbSeed);
//			logger.info("Successfully subscribed to: " + url);
//		} catch (ServiceException e) {
//			logger.error(e.getMessage(), e);
//		}
//		Thread.sleep(3000);
	}

	// Works! :)
	private void subscribeToRentalCars(Seed dbSeed, WebDriver driver) throws InterruptedException {
		String url = "https://www.rentalcars.com";
		if (isSubscribed(dbSeed, url)) {
			logger.info("This seed is already subscribe to: " + url);
			return;
		}
		logger.info("Subscribing to RentalCars");
		driver.get(url);
		driver.manage().window().maximize();
		Thread.sleep(5000);

		driver.findElement(By.id("emailAddress")).clear();
		driver.findElement(By.id("emailAddress")).sendKeys(dbSeed.getEmail());
		driver.findElement(By.className("cb-c-email-signup__submit")).submit();

	}

	// Works! :)
	private void subscribeToHollister(Seed dbSeed, WebDriver driver) throws InterruptedException {
		String url = "https://hollisterco.com";
		if (isSubscribed(dbSeed, url)) {
			logger.info("This seed is already subscribe to: " + url);
			return;
		}
		logger.info("Subscribing to Hollister");
		driver.get(url);
		driver.manage().window().maximize();
		Thread.sleep(5000);

		driver.findElement(By.id("banner-email-field")).clear();
		driver.findElement(By.id("banner-email-field")).sendKeys(dbSeed.getEmail());
		driver.findElement(By.className("footer-email-subscribe")).submit();

	}

	/**
	 * 
	 * @param dbSeed
	 * @param url
	 * @return
	 */
	private boolean isSubscribed(Seed dbSeed, String url) {
		for (Subscription s : dbSeed.getSubscriptions()) {
			if (s.equalsByUrl(url)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * @param fileName
	 * @param fileContent
	 */
	public static void writeToFile(String fileName, String fileContent) {
		try {
			logger.info("Writing page to: " + fileName);
			FileWriter writer = new FileWriter(fileName, false);
			writer.write(fileContent);

			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
