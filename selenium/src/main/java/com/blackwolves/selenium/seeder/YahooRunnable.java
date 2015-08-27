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

/**
 * @author danigrane
 *
 */

public abstract class YahooRunnable implements Runnable {

	private static final Logger logger = LogManager.getLogger(YahooRunnable.class.getName());

	protected static final double PERCENTAGE = 0.36;

	private String seed = "";

	protected WebDriver driver;

	protected Actions mouse;

	protected JavascriptExecutor jse;

	public YahooRunnable(WebDriver driver, String seed) {
		this.driver = driver;
		this.seed = seed;
		mouse = new Actions(driver);
		jse = (JavascriptExecutor) driver;
	}

	public abstract void processInbox(WebDriver driver, String[] seed) throws InterruptedException;

	public abstract void processSpam(WebDriver driver, String[] seed) throws InterruptedException;

	public abstract void clickRandomLink(WebDriver driver) throws InterruptedException;

	@Override
	public void run() {
		String[] seed = this.seed.split(",");
		try {
			processInbox(driver, seed);
			processSpam(driver, seed);
			logger.info("Finished!!");
			driver.close();

		} catch (NoSuchElementException nse) {
			logger.error(nse.getMessage(), nse);
			driver.close();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			driver.close();
		}

	}

	protected static int randInt(int min, int max) {

		// Usually this can be a field rather than a method variable
		Random rand = new Random();

		// nextInt is normally exclusive of the top value,
		// so add 1 to make it inclusive
		int randomNum = rand.nextInt((max - min) + 1) + min;

		return randomNum;
	}

	// Throw dices to get random results
	public static boolean throwDice() {
		int dice = randInt(1, 6);
		return dice != 6;
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

}
