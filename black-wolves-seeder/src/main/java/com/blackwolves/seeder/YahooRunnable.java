package com.blackwolves.seeder;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import au.com.bytecode.opencsv.CSVReader;

import com.blackwolves.persistence.entity.Action;
import com.blackwolves.persistence.entity.Session;
import com.blackwolves.persistence.util.Constant;
import com.gargoylesoftware.htmlunit.ElementNotFoundException;

/**
 * @author danigrane
 *
 */

public abstract class YahooRunnable {

	private static final Logger logger = LogManager.getLogger(YahooRunnable.class.getName());

	protected static final double PERCENTAGE = generateDoubleRandom(0.1, 0.2);
	
	private String seed = "";
	
	protected Human human;

	protected WebDriver driver;

	protected Actions mouse;

	protected JavascriptExecutor jse;

	/**
	 * Constructor
	 * @param driver
	 * @param seed
	 * @param human
	 */
	public YahooRunnable(WebDriver driver, String seed, Human human) {
		this.driver = driver;
		this.seed = seed;
		this.mouse = new Actions(driver);
		this.jse = (JavascriptExecutor) driver;
		this.human = human;
	}

	/**
	 * Starts the wolf seeder process
	 */
	public void runProcess() {
		String[] seed = this.seed.split(",");
		try {
			processSpam(seed);
			processInbox(seed);
			logger.info("Finished!!");
		} catch (NoSuchElementException e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * Throw dices to get random results
	 * @return boolean
	 */
	public static boolean throwDice() {
		int dice = randInt(1, 6);
		return dice == 6;
	}
	
	public static int randInt(int min, int max) {
		Random rand = new Random();
		// nextInt is normally exclusive of the top value, so add 1 to make it inclusive
		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}

	/**
	 * 
	 * @param msgs
	 * @return int
	 */
	protected int obtainRandomMsgsPosition(List<WebElement> msgs) {
		Random randomNo = new Random();
		int randomPosition = randomNo.nextInt(msgs.size() >= 50 ? 50 : msgs.size());
		return randomPosition;
	}

	/**
	 * 
	 * @param className
	 * @throws InterruptedException
	 */
	protected void clickShowImages(String className) {
		try{
			if (validateInboxShowImagesButton(className)) {
				if(driver.findElements(By.className("show-text")).size() > 0){
					logger.info("Getting the show images div");
					List<WebElement> divs = driver.findElements(By.className("show-text"));
					WebElement showImage = divs.get(0);
					WebElement a = showImage.findElement(By.tagName("a"));
					mouse.moveToElement(a);
					logger.info("Clicking the show images button");
					a.click();
					logger.info("**********  Wohooo! Showing Images. Waiting a little bit to display them **********");
					Thread.sleep(randInt(3000, 5000));
				}else{
					logger.info("No Images to click");
				}
			} else {
				logger.info("**********   No show images button found or there is none   **********");
			}
		} catch (InterruptedException e) {
			logger.error(e.getMessage(), e);
		} catch (NoSuchElementException e) {
			logger.error(e.getMessage(), e);
		} catch (StaleElementReferenceException e) {
			logger.error(e.getMessage(), e);
		} catch (ElementNotVisibleException e) {
			logger.error(e.getMessage(), e);
		} catch (ElementNotFoundException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 
	 * @param className
	 * @return
	 * @throws InterruptedException
	 */
	private boolean validateInboxShowImagesButton(String className) {
		try {
			Thread.sleep(randInt(3000, 5000));
		} catch (InterruptedException e) {
			logger.error(e.getMessage(), e);
		}
		return driver.findElements(By.className(className)).size() > 0;
	}

	/**
	 * 
	 * @param a
	 * @throws InterruptedException
	 */
	protected void openInNewWindow(WebElement a) {
		try{
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
			Thread.sleep(2500);
			for (String winHandle : driver.getWindowHandles()) {
				driver.switchTo().window(winHandle);
			}
		} catch (InterruptedException e) {
			logger.error(e.getMessage(), e);
		} catch (NoSuchElementException e) {
			logger.error(e.getMessage(), e);
		} catch (StaleElementReferenceException e) {
			logger.error(e.getMessage(), e);
		} catch (ElementNotVisibleException e) {
			logger.error(e.getMessage(), e);
		} catch (ElementNotFoundException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	/**
	 * @return
	 */
	public static List<String[]> generateIpsList() {
		List<String[]> ips = new ArrayList<String[]>();
		try {
			CSVReader ipsReader = new CSVReader(new FileReader(Constant.ROUTE + "ip_curl.txt"));
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
	public static List<String[]> generateSeedsList() {
		List<String[]> seeds = new ArrayList<String[]>();
		try {
			CSVReader seedsReader = new CSVReader(new FileReader(Constant.ROUTE + "seeds.csv"));
			seeds = seedsReader.readAll();
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return seeds;
	}
	
	/**
	 * @return
	 */
	public static List<String[]> generateDomainsList() {
		List<String[]> domains = new ArrayList<String[]>();
		try {
			CSVReader domainsReader = new CSVReader(new FileReader(Constant.ROUTE + "domains.txt"));
			domains = domainsReader.readAll();
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return domains;
	}
	
	/**
	 * Adds the performed action to the session
	 * @param session
	 * @param actionName 
	 */
	public static void addActionToSession(Session session, String actionName) {
		Action action = new Action(actionName);
		session.getActions().add(action);
	}
	
	/**
	 * Generates a random value of type doulbe
	 * @param max
	 * @param min
	 * @return double
	 */
	public static double generateDoubleRandom(double max, double min) {
		double r = Math.random();
		if (r < 0.5) {
			return ((1 - Math.random()) * (max - min) + min);
		}
		return (Math.random() * (max - min) + min);
	}
	
	public abstract void processInbox(String[] seed);

	public abstract void processSpam(String[] seed);

	public abstract void replyToEmail();
	
	public abstract void forwardEmail();
	
	public abstract void sendEmail();
	
	public abstract void moveMessageToAllFolder();
	
	public abstract void clickSpam();
	
	public abstract void clickRandomLink();
	
	public abstract void replyToEmailFromSubList();
	
	public abstract void forwardEmailFromSubList();
	
//	protected void openLinkInNewWindow(WebElement a) throws InterruptedException{
//		Actions newTab = new Actions(driver);
//		newTab.keyDown(Keys.SHIFT).click(a).keyUp(Keys.SHIFT).build().perform();
//		Thread.sleep(5000);
//		 
//		//handle windows change
//		String base = driver.getWindowHandle();
//		Set<String> set = driver.getWindowHandles();
//		 
//		set.remove(base);
//		assert set.size() == 1;
//		driver.switchTo().window((String) set.toArray()[0]);
//		 
//		//close the window
//		driver.close();
//		driver.switchTo().window(base);
//		 
//		// handle windows change and switch back to the main window
//		Thread.sleep(2500);
//		for (String winHandle : driver.getWindowHandles()) {
//			driver.switchTo().window(winHandle);
//		}
//	}
//	
//	protected void openLinkInNewTab(WebElement a) throws InterruptedException{
//		Actions newTab = new Actions(driver);
//		newTab.keyDown(Keys.CONTROL).keyDown(Keys.SHIFT).click(a).keyUp(Keys.CONTROL).keyUp(Keys.SHIFT).build().perform();
//		Thread.sleep(5000);
//		 
//		//handle windows change
//		String base = driver.getWindowHandle();
//		Set<String> set = driver.getWindowHandles();
//		 
//		set.remove(base);
//		assert set.size() == 1;
//		driver.switchTo().window((String) set.toArray()[0]);
//		 
//		//close the window and switch back to the base tab
//		driver.close();
//		driver.switchTo().window(base);
//	}
	
//	/**
//	 * Executes a script on an element
//	 * @note Really should only be used when the web driver is sucking at exposing
//	 * functionality natively
//	 * @param script The script to execute
//	 * @param element The target of the script, referenced as arguments[0]
//	 */
//	public void trigger(String script, WebElement element) {
//	    ((JavascriptExecutor)driver).executeScript(script, element);
//	}
//
//	/** Executes a script
//	 * @note Really should only be used when the web driver is sucking at exposing
//	 * functionality natively
//	 * @param script The script to execute
//	 */
//	public Object trigger(String script) {
//	    return ((JavascriptExecutor)driver).executeScript(script);
//	}
//
//	/**
//	 * Opens a new tab for the given URL
//	 * @param url The URL to 
//	 * @throws JavaScriptException If unable to open tab
//	 */
//	public void openTab(String url) {
//	    String script = "var d=document,a=d.createElement('a');a.target='_blank';a.href='%s';a.innerHTML='.';d.body.appendChild(a);return a";
//	    Object element = trigger(String.format(script, url));
//	    if (element instanceof WebElement) {
//	        WebElement anchor = (WebElement) element; anchor.click();
//	        trigger("var a=arguments[0];a.parentNode.removeChild(a);", anchor);
//	    } else {
//	        throw new JavaScriptException(element, "Unable to open tab", 1);
//	    }       
//	}
//	
//	/**
//	 * Switches to the non-current window
//	 * @throws InterruptedException 
//	 */
//	public void switchToNewWindow() throws NoSuchWindowException, NoSuchWindowException, InterruptedException {
//	    Set<String> handles = driver.getWindowHandles();
//	    String current = driver.getWindowHandle();
//	    handles.remove(current);
//	    String newTab = handles.iterator().next();
//	    driver.switchTo().window(newTab);
//	    Thread.sleep(randInt(5000, 10000));
//	}
//	
//	/**
//	 * Switches to the non-current window
//	 * @throws InterruptedException 
//	 */
//	public void switchToPreviousWindow() throws NoSuchWindowException, NoSuchWindowException, InterruptedException {
//		Set<String> handles = driver.getWindowHandles();
//	    String current = driver.getWindowHandle();
//	    handles.remove(current);
//	    String newTab = handles.iterator().next();
//	    driver.switchTo().window(newTab);
//	    Thread.sleep(randInt(5000, 10000));
//	}

}
