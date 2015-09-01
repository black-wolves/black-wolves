package com.blackwolves.selenium.seeder;

import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * @author danigrane
 *
 */

public abstract class YahooRunnable {

	private static final Logger logger = LogManager.getLogger(YahooRunnable.class.getName());

	protected static final double PERCENTAGE = generateDoubleRandom(0.1, 0.2) ;

	private String seed = "";
	
	private Human human;

	protected WebDriver driver;

	protected Actions mouse;

	protected JavascriptExecutor jse;

	public YahooRunnable(WebDriver driver, String seed, Human human) {
		this.driver = driver;
		this.seed = seed;
		this.mouse = new Actions(driver);
		this.jse = (JavascriptExecutor) driver;
		this.human = human;
	}

	public abstract void processInbox(WebDriver driver, String[] seed, Human human) throws InterruptedException;

	public abstract void processSpam(WebDriver driver, String[] seed) throws InterruptedException;

	public abstract void clickRandomLink(WebDriver driver) throws InterruptedException;
	
	public abstract void addToAddressBook(WebDriver driver) throws InterruptedException;
	
	public abstract void replyToEmail(WebDriver driver, WebDriverWait wait, Human human) throws InterruptedException;
	
	public void runProcess() {
		String[] seed = this.seed.split(",");
		try {
			processInbox(driver, seed, human);
//			if (!throwDice()) {
				processSpam(driver, seed);
//			}
			logger.info("Finished!!");
		} catch (NoSuchElementException e) {
			logger.error(e.getMessage(), e);
		} catch (InterruptedException e) {
			logger.error(e.getMessage(), e);
		} finally{
			driver.close();
			logger.info("Thread should end now.");
		}
	}

	// Throw dices to get random results
		public static boolean throwDice() {
			int dice = randInt(1, 6);
			return dice == 6;
		}
	
	public static int randInt(int min, int max) {

		// Usually this can be a field rather than a method variable
		Random rand = new Random();

		// nextInt is normally exclusive of the top value,
		// so add 1 to make it inclusive
		int randomNum = rand.nextInt((max - min) + 1) + min;

		return randomNum;
	}

	/**
	 * @param msgs
	 * @return
	 */
	protected int obtainRandomMsgsPosition(List<WebElement> msgs) {
		Random randomNo = new Random();
		int randomPosition = randomNo.nextInt(msgs.size() >= 50 ? 50 : msgs.size());
		return randomPosition;
	}

	/**
	 * 
	 * @param driver
	 * @param className
	 * @throws InterruptedException
	 */
	protected void clickShowImages(WebDriver driver, String className) throws InterruptedException {
		if (validateInboxShowImagesButton(driver, className)) {
			try {

				logger.info("Clicking the show images button");
				List<WebElement> divs = driver.findElements(By.className("show-text"));
				WebElement showImage = divs.get(0);
				WebElement a1 = showImage.findElement(By.tagName("a"));
				mouse.moveToElement(a1);
				a1.click();
				logger.info("**********  Wohooo! Showing Images. Waiting a little bit to display them **********");
				Thread.sleep(3000 + randInt(1000, 4000));
			} catch (Exception exception) {
				logger.info("No Images to click");
			}

		} else {
			logger.info("**********   No show images button found or there is none   **********");
		}
	}

	/**
	 * 
	 * @param driver
	 * @param a
	 * @throws InterruptedException
	 */
	protected void openInNewWindow(WebDriver driver, WebElement a) throws InterruptedException {

		logger.info("Cicking this link: " + a.getAttribute("href"));
		Actions newTab = new Actions(driver);
		newTab.keyDown(Keys.SHIFT).click(a).keyUp(Keys.SHIFT).build().perform();
		Thread.sleep(5000);

		// handle windows change
		String base = driver.getWindowHandle();
		Set<String> set = driver.getWindowHandles();

		set.remove(base);
		assert set.size() == 1;
		driver.switchTo().window((String) set.toArray()[0]);

		// close the window
		driver.close();
		driver.switchTo().window(base);

		// handle windows change and switch back to the main window
		Thread.sleep(1500);
		for (String winHandle : driver.getWindowHandles()) {
			driver.switchTo().window(winHandle);
		}
	}

	/**
	 * 
	 * @param driver
	 * @param className
	 * @return
	 * @throws InterruptedException
	 */
	private boolean validateInboxShowImagesButton(WebDriver driver, String className) throws InterruptedException {
		Thread.sleep(3000 + randInt(1000, 4000));
		return driver.findElements(By.className(className)).size() > 0;
	}
	
	
	public static double generateDoubleRandom(double max, double min) {
		double r = Math.random();
		if (r < 0.5) {
			return ((1 - Math.random()) * (max - min) + min);
		}
		return (Math.random() * (max - min) + min);
	}

}
