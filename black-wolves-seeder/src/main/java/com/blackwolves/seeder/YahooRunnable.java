package com.blackwolves.seeder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import net.sourceforge.htmlunit.corejs.javascript.JavaScriptException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.slf4j.Logger;

import au.com.bytecode.opencsv.CSVReader;

import com.blackwolves.seeder.util.Constant;
import com.gargoylesoftware.htmlunit.ElementNotFoundException;

/**
 * 
 * @author gastondapice
 *
 */
public abstract class YahooRunnable {

	protected static Logger logger;

	protected static double PERCENTAGE = generateDoubleRandom(0.2, 0.4);
	
	protected Seed seed;
	
	protected Human human;

	protected WebDriver driver;

	protected Actions mouse;

	protected JavascriptExecutor jse;

	/**
	 * Constructor
	 * @param driver
	 * @param seed
	 * @param human
	 * @param logger2 
	 */
	public YahooRunnable(WebDriver driver, Seed seed, Human human, Logger logger) {
		this.driver = driver;
		this.seed = seed;
		this.mouse = new Actions(driver);
		this.jse = (JavascriptExecutor) driver;
		this.human = human;
		YahooRunnable.logger = logger;
	}

	/**
	 * Starts the wolf seeder process
	 * @param logger 
	 */
	public void runProcess() {
		
		logger.info("Processing inbox");
		processInbox(seed);
		
//		logger.info("Processing spam....");
//		processSpam(seed);
			
	}

	/**
	 * Throw dices to get random results
	 * @return boolean
	 */
	public static boolean throwDice() {
		int dice = randInt(1, 6);
		return dice == 3;
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
		logger.info("The msgs.size() is: " + msgs.size());
		int randomPosition = randInt(0, msgs.size()>= 50?49:msgs.size()==0?0:msgs.size()-1);		
		logger.info("The random position is: " + randomPosition);
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
					logger.info("clicking!");

					a.click();
					logger.info("**********  Wohooo! Showing Images. Waiting a little bit to display them **********");
					Thread.sleep(randInt(3000, 5000));
					ModernYahooRunnable.scrollToBottom(driver);
				}else{
					logger.info("No Images to click");
				}
			} else {
				logger.info("**********   No show images button found or there is none   **********");
			}
		} catch (InterruptedException e) {
			logger.error("InterruptedException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " " , e);
		} catch (NoSuchElementException e) {
			logger.error("NoSuchElementException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " " , e);
		} catch (StaleElementReferenceException e) {
			logger.error("StaleElementReferenceException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " ", e); 
		} catch (ElementNotVisibleException e) {
			logger.error("ElementNotVisibleException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " ", e); 
		} catch (ElementNotFoundException e) {
			logger.error("ElementNotFoundException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " ", e); 
		} catch (UnhandledAlertException e) {
			logger.error("UnhandledAlertException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " " , e);
					
		} catch (WebDriverException e) {
			logger.error("WebDriverException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " " , e);
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
	
	public abstract void processInbox(Seed seed);

	public abstract void processSpam(Seed seed);

	public abstract void clickSpam();
	
	public abstract void clickRandomLink();
	
//	public abstract void replyToEmail();
	
//	public abstract void forwardEmail();
	
//	public abstract void sendEmail();
	
//	public abstract void moveMessageToAllFolder();
	
//	public abstract void replyToEmailFromSubList();
	
//	public abstract void forwardEmailFromSubList();
	
	/**
	 * Executes a script on an element
	 * @note Really should only be used when the web driver is sucking at exposing
	 * functionality natively
	 * @param script The script to execute
	 * @param element The target of the script, referenced as arguments[0]
	 */
	public void trigger(String script, WebElement element) {
	    ((JavascriptExecutor)driver).executeScript(script, element);
	}

	/** Executes a script
	 * @note Really should only be used when the web driver is sucking at exposing
	 * functionality natively
	 * @param script The script to execute
	 */
	public Object trigger(String script) {
	    return ((JavascriptExecutor)driver).executeScript(script);
	}

	/**
	 * Opens a new tab for the given URL
	 * @param url The URL to 
	 * @throws JavaScriptException If unable to open tab
	 */
	public void openTab(String url) {
		logger.info("Opening link in new window");
	    String script = "var d=document,a=d.createElement('a');a.target='_blank';a.href='%s';a.innerHTML='.';d.body.appendChild(a);return a";
	    Object element = trigger(String.format(script, url));
	    if (element instanceof WebElement) {
	        WebElement anchor = (WebElement) element;
	        logger.info("Text: " + anchor.getText());
	        logger.info("TageName: " + anchor.getTagName());
	        logger.info("Location: " + anchor.getLocation());
	        logger.info("Size: " + anchor.getSize());
	        logger.info("Rect: " + anchor.getRect());
	        logger.info("Displayed: " + anchor.isDisplayed());
	        logger.info("Enabled: " + anchor.isEnabled());
	        logger.info("Selected: " + anchor.isSelected());
	        anchor.click();
	        trigger("var a=arguments[0];a.parentNode.removeChild(a);", anchor);
	    } else {
	        throw new JavaScriptException(element, "Unable to open tab", 1);
	    }       
	}
	
	/**
	 * Switches to the non-current window
	 * @throws InterruptedException 
	 */
	public void switchToNewWindow() {
	    try{
			Set<String> handles = driver.getWindowHandles();
		    String current = driver.getWindowHandle();
		    handles.remove(current);
		    String newTab = handles.iterator().next();
		    logger.info("Switching to new window");
		    driver.switchTo().window(newTab);
		    Thread.sleep(randInt(5000, 10000));
	    } catch (InterruptedException e) {
			logger.error("InterruptedException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " " , e);
		} catch (NoSuchElementException e) {
			logger.error("NoSuchElementException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " " , e);
		} catch (StaleElementReferenceException e) {
			logger.error("StaleElementReferenceException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " ", e); 
		} catch (ElementNotVisibleException e) {
			logger.error("ElementNotVisibleException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " ", e); 
		} catch (ElementNotFoundException e) {
			logger.error("ElementNotFoundException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " ", e); 
		} catch (UnhandledAlertException e) {
			logger.error("UnhandledAlertException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " " , e);
					
		} catch (WebDriverException e) {
			logger.error("WebDriverException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " " , e);
		}
	}
	
	/**
	 * Switches to the non-current window
	 * @throws InterruptedException 
	 */
	public void switchToPreviousWindow() {
		try{	
			Set<String> handles = driver.getWindowHandles();
		    String current = driver.getWindowHandle();
		    handles.remove(current);
		    String newTab = handles.iterator().next();
		    logger.info("Closing new window");
		    driver.close();
		    logger.info("Switching back to yahoo");
		    driver.switchTo().window(newTab);
		    Thread.sleep(randInt(5000, 10000));
		} catch (InterruptedException e) {
			logger.error("InterruptedException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " " , e);
		} catch (NoSuchElementException e) {
			logger.error("NoSuchElementException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " " , e);
		} catch (StaleElementReferenceException e) {
			logger.error("StaleElementReferenceException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " ", e); 
		} catch (ElementNotVisibleException e) {
			logger.error("ElementNotVisibleException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " ", e); 
		} catch (ElementNotFoundException e) {
			logger.error("ElementNotFoundException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " ", e); 
		} catch (UnhandledAlertException e) {
			logger.error("UnhandledAlertException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " " , e);
					
		} catch (WebDriverException e) {
			logger.error("WebDriverException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " " , e);
		}
	}
	
	/**
	 * @return
	 */
	public static List<String[]> generateSeedsList(String fileName) {
		List<String[]> seeds = new ArrayList<String[]>();
		try {
			CSVReader seedsReader = new CSVReader(new FileReader(Constant.ROUTE + fileName));
			seeds = seedsReader.readAll();
			seedsReader.close();
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return seeds;
	}
	
	public static void getScreenShot(WebDriver driver, String name) {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		// Now you can do whatever you need to do with it, for example copy
		// somewhere
		try {
			FileUtils.copyFile(scrFile, new File("/var/www/errors/" + name + ".jpg"));
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}

	}
	
}
