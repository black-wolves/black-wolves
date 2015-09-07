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

	public void runProcess(String mySeed) {
		String[] seed = mySeed.split(",");
		Seed dbSeed = seedService.getSeedFromDb(seed);

		logger.info("Creating the driver");
		DesiredCapabilities caps = new DesiredCapabilities();
		caps.setCapability("binary", "/usr/bin/wires-0.3.0-linux64");
		caps.setCapability("resolution", "1280x800");
		WebDriver driver = new FirefoxDriver(caps);
		try {
			suscribeToSkimm(dbSeed, driver);
//			suscribeToMatterMark(seed, driver);
//			suscribeFashionMagazine(seed, driver);
//			suscribeToDigg(seed, driver);
			// suscribeToTheWeek(seed, driver);

			driver.close();
		}

		catch (NoSuchElementException nse)
		{
			logger.error(nse.getMessage(), nse);
			driver.close();
		} catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			driver.close();
		}

	}

	// works :)
	private void suscribeFashionMagazine(String[] seed, WebDriver driver) throws InterruptedException {
		driver.get("http://www.fashionmagazine.com/");
		driver.findElement(By.name("input_1")).clear();
		;
		driver.findElement(By.name("input_1")).sendKeys(seed[0]);
		driver.findElement(By.name("input_1")).submit();
		if (driver.getPageSource().contains("Thanks")) {
			logger.info("Fashion Magazine Suscription succesful.");
		}

	}

	// Works :)
	private void suscribeToMatterMark(String[] seed, WebDriver driver) throws InterruptedException {

		driver.get("http://mattermark.com/app/Newsletter");
		driver.findElement(By.id("free_email")).sendKeys(seed[0]);
		driver.findElement(By.id("free_email")).submit();
		Thread.sleep(1000);

	}

	// Works! :)
	private void suscribeToSkimm(Seed dbSeed, WebDriver driver) throws InterruptedException {
		String url = "http://www.theskimm.com/";
		if(isSubscribed(dbSeed, url)){
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
		Thread.sleep(1000);
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

	private void suscribeToDigg(String[] seed, WebDriver driver) {

		driver.get("http://digg.com/");

		driver.findElement(By.name("email")).sendKeys(seed[0]);

		driver.findElement(By.id("daily-digg-email-submit-btn")).click();
		if (driver.getPageSource().contains("Thanks for subscribing!")) {
			logger.info("Suscribed " + seed[0] + " to Digg!");
		}

	}

	private void suscribeToTheWeek(String[] seed, WebDriver driver) throws InterruptedException {
		driver.get("http://theweek.com/");
		driver.findElement(By.name("email")).clear();
		;
		driver.findElement(By.name("email")).sendKeys(seed[0]);
		driver.findElement(By.name("email")).submit();
		Thread.sleep(1000);

	}

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
