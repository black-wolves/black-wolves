package com.blackwolves.subscriber;

import java.io.FileWriter;
import java.io.IOException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
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
		String[] seed = mySeed.split(",");
		Seed dbSeed = seedService.getSeedFromDb(seed);

		logger.info("Creating the driver");
		DesiredCapabilities caps = new DesiredCapabilities();
		caps.setCapability("binary", "/usr/bin/wires-0.3.0-linux64");
		caps.setCapability("resolution", "1280x800");
		WebDriver driver = new FirefoxDriver(caps);
		try {
			subscribeToSkimm(dbSeed, driver);
			subscribeToMatterMark(dbSeed, driver);
			subscribeFashionMagazine(dbSeed, driver);
			subscribeToDigg(dbSeed, driver);
			subscribeToTheWeek(dbSeed, driver);

			driver.close();
		}

		catch (NoSuchElementException e) {
			logger.error(e.getMessage(), e);
			driver.close();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			driver.close();
		}
	}
	
	// Works! :)
	private void subscribeToSkimm(Seed dbSeed, WebDriver driver) throws InterruptedException {
		String url = "http://www.theskimm.com/";
		if (isSubscribed(dbSeed, url)) {
			logger.info("This seed is already subscribe to: " + url);
			return;
		}
		logger.info("Subscribing to The Skimm");
		driver.get(url);
		driver.findElement(By.name("email")).clear();
		driver.findElement(By.name("email")).sendKeys(dbSeed.getEmail());
		driver.findElement(By.name("email")).submit();
		Subscription subscription = new Subscription("The Skimm", url);
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
		driver.findElement(By.name("email")).clear();
		driver.findElement(By.name("email")).sendKeys(dbSeed.getEmail());
		driver.findElement(By.name("email")).submit();
		Subscription subscription = new Subscription("The Week", url);
		dbSeed.getSubscriptions().add(subscription);
		try {
			seedService.saveOrUpdate(dbSeed);
			logger.info("Successfully subscribed to: " + url);
		} catch (ServiceException e) {
			logger.error(e.getMessage(), e);
		}
		Thread.sleep(3000);
	}

	/**
	 * 
	 * @param dbSeed
	 * @param url
	 * @return
	 */
	private boolean isSubscribed(Seed dbSeed, String url) {
		for (Subscription s : dbSeed.getSubscriptions()) {
			if(s.equalsByUrl(url)){
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
