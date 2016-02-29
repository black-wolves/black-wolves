/**
 * 
 */
package com.blackwolves.subscriber;

import java.io.FileWriter;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blackwolves.persistence.entity.Seed;

/**
 * @author gaston.dapice
 *
 */
public class Subscriber implements Runnable {

	private Seed seed;

	public Subscriber(Seed seed) {
		this.seed = seed;
	}

	private static final Logger logger = LoggerFactory.getLogger(Subscriber.class);

	@Override
	public void run() {
		logger.info("Creating the driver");
		DesiredCapabilities caps = new DesiredCapabilities();
		caps.setCapability("binary", "/usr/bin/wires-0.3.0-linux64");
		caps.setCapability("resolution", "1280x800");
		WebDriver driver = new FirefoxDriver(caps);
		try {
			// subscribeToSkimm(seed, driver);
			// subscribeToMatterMark(seed, driver);
			// subscribeFashionMagazine(seed, driver);
			// subscribeToGolfSmith(seed, driver);
			// subscribeToFetch(seed, driver);
			// subscribeToReDef(seed,driver);
		}

		catch (NoSuchElementException e) {
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			driver.close();
			driver.quit();
		}
	}

	

	// Works! :)
	private void subscribeToLongreads(Seed seed, WebDriver driver) throws InterruptedException {
		String url = "http://longreads.com/subscribe/";
		logger.info("Subscribing to LongReads");
		driver.get(url);
		driver.findElement(By.name("email")).clear();
		driver.findElement(By.name("email")).sendKeys(seed.getEmail());
		driver.findElement(By.xpath("//div[@id='button']/input")).click();
		;
		Thread.sleep(3000);
	}
	// Works! :)
	private void subscribeToReDef(Seed seed, WebDriver driver) throws InterruptedException {
		String url = "http://link.mediaredefined.com/join/353/media-redefweb";
		logger.info("Subscribing to ReDef");
		driver.get(url);
		driver.findElement(By.name("email")).clear();
		driver.findElement(By.name("email")).sendKeys(seed.getEmail());
		driver.findElement(By.xpath("//div[@id='button']/input")).click();
		Thread.sleep(3000);
	}

	// Works! :)
	private void subscribeToFetch(Seed seed, WebDriver driver) throws InterruptedException {
		String url = "http://thefetch.com/";
		logger.info("Subscribing to The Fetch");
		driver.get(url);
		driver.findElement(By.name("email")).clear();
		driver.findElement(By.name("email")).sendKeys(seed.getEmail());
		driver.findElement(By.name("email")).submit();
		Thread.sleep(3000);
	}

	// Works! :)
	private void subscribeToSkimm(Seed dbSeed, WebDriver driver) throws InterruptedException {
		String url = "http://www.theskimm.com/";
		logger.info("Subscribing to The Skimm");
		driver.get(url);
		driver.findElement(By.name("email")).clear();
		driver.findElement(By.name("email")).sendKeys(dbSeed.getEmail());
		driver.findElement(By.name("email")).submit();
		Thread.sleep(3000);
	}

	// Works :)
	private void subscribeToMatterMark(Seed dbSeed, WebDriver driver) throws InterruptedException {
		String url = "http://mattermark.com/app/Newsletter";
		logger.info("Subscribing to Mattermark");
		driver.get(url);
		driver.findElement(By.id("free_email")).sendKeys(dbSeed.getEmail());
		driver.findElement(By.id("free_email")).submit();
		Thread.sleep(3000);

	}

	// Works :)
	private void subscribeFashionMagazine(Seed dbSeed, WebDriver driver) throws InterruptedException {
		String url = "http://www.fashionmagazine.com/";
		logger.info("Subscribing to Fashion Magazine");
		driver.get(url);
		driver.findElement(By.name("input_1")).clear();
		driver.findElement(By.name("input_1")).sendKeys(dbSeed.getEmail());
		driver.findElement(By.name("input_1")).submit();
		if (driver.getPageSource().contains("Thanks")) {
			logger.info("Fashion Magazine Subscription succesful.");
		}
		Thread.sleep(3000);
	}

	// Works! :)
	private void subscribeToDigg(Seed dbSeed, WebDriver driver) throws InterruptedException {
		String url = "http://digg.com/";
		logger.info("Subscribing to Digg");
		driver.get(url);
		driver.findElement(By.name("email")).sendKeys(dbSeed.getEmail());
		driver.findElement(By.id("daily-digg-email-submit-btn")).click();
		if (driver.getPageSource().contains("Thanks for subscribing!")) {
			logger.info("Digg Subscription succesful.");
		}
		Thread.sleep(3000);
	}

	// Works! :)
	private void subscribeToGolfSmith(Seed dbSeed, WebDriver driver) throws InterruptedException {
		String url = "http://www.golfsmith.com/";
		logger.info("Subscribing to GolfSmith");
		driver.get(url);
		driver.findElement(By.name("email")).clear();
		driver.findElement(By.name("email")).sendKeys(dbSeed.getEmail());
		driver.findElement(By.id("submitAddress_footer")).submit();
		Thread.sleep(3000);
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
