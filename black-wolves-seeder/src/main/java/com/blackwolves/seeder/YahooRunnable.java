package com.blackwolves.seeder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;

import au.com.bytecode.opencsv.CSVReader;

import com.blackwolves.seeder.util.Constant;
import com.blackwolves.seeder.util.JDBC;
import com.gargoylesoftware.htmlunit.ElementNotFoundException;

/**
 * 
 * @author gastondapice
 *
 */
public abstract class YahooRunnable {

	protected static Logger logger;

	protected static double PERCENTAGE = generateDoubleRandom(0.1, 0.25);

	protected Seed seed;

	protected Human human;

	protected WebDriver driver;

	protected Actions mouse;

	protected JavascriptExecutor jse;

	/**
	 * Constructor
	 * 
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
	 * 
	 * @param logger
	 */
	public void runProcess() {
		
		if(seed.getBirthDate() == null){
			
			clickAllNotSapm(driver);
			
			gatherPersonalInfo(driver, seed);
			
			
		}
		
		processInbox(seed);
		if (Math.random() <= 0.1) {
			processSpam(seed);
		}
	}

	/**
	 * Throw dices to get random results
	 * 
	 * @return boolean
	 */
	public static boolean throwDice() {
		int dice = randInt(1, 6);
		return dice == 3;
	}

	public static int randInt(int min, int max) {
		Random rand = new Random();
		// nextInt is normally exclusive of the top value, so add 1 to make it
		// inclusive
		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}

	/**
	 * 
	 * @param msgs
	 * @return int
	 */
	protected int obtainRandomMsgsPosition(List<WebElement> msgs) {
		int randomPosition = randInt(0, msgs.size() >= 10 ? 10 : msgs.size() == 0 ? 0 : msgs.size() - 1);
		return randomPosition;
	}

	/**
	 * 
	 * @param className
	 * @throws InterruptedException
	 */
	protected void clickShowImages(String className) {
		try {
			if (validateInboxShowImagesButton(className)) {
				if (driver.findElements(By.className("show-text")).size() > 0) {
					List<WebElement> divs = driver.findElements(By.className("show-text"));
					WebElement showImage = divs.get(0);

					WebElement a = showImage.findElement(By.tagName("a"));
					mouse.moveToElement(a);
					logger.info("clicking!");
					if (a != null && a.isDisplayed()) {
						a.click();
						logger.info(
								"**********  Wohooo! Showing Images. Waiting a little bit to display them **********");
						Thread.sleep(randInt(3000, 5000));
					} else {
						logger.info("Show images not displayed");
					}
					ModernYahooRunnable.scrollToBottom(driver);
				} else {
					logger.info("No Images to click");
				}
			} else {
				logger.info("**********   No show images button found or there is none   **********");
			}
		} catch (InterruptedException e) {
			logger.error("InterruptedException for seed: " + seed.getUser() + " with password: " + seed.getPassword()
					+ " " + e.getMessage() + " ", e);
		} catch (NoSuchElementException e) {
			logger.error("NoSuchElementException for seed: " + seed.getUser() + " with password: " + seed.getPassword()
					+ " " + e.getMessage() + " ", e);
		} catch (StaleElementReferenceException e) {
			logger.error("StaleElementReferenceException for seed: " + seed.getUser() + " with password: "
					+ seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (ElementNotVisibleException e) {
			logger.error("ElementNotVisibleException for seed: " + seed.getUser() + " with password: "
					+ seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (ElementNotFoundException e) {
			logger.error("ElementNotFoundException for seed: " + seed.getUser() + " with password: "
					+ seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (UnhandledAlertException e) {
			logger.error("UnhandledAlertException for seed: " + seed.getUser() + " with password: " + seed.getPassword()
					+ " " + e.getMessage() + " ", e);
		} catch (WebDriverException e) {
			logger.error("WebDriverException for seed: " + seed.getUser() + " with password: " + seed.getPassword()
					+ " " + e.getMessage() + " ", e);
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
	 * 
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

	public abstract boolean clickRandomLink();

	// public abstract void replyToEmail();

	// public abstract void forwardEmail();

	// public abstract void sendEmail();

	// public abstract void moveMessageToAllFolder();

	// public abstract void replyToEmailFromSubList();

	// public abstract void forwardEmailFromSubList();

	/**
	 * Executes a script on an element
	 * 
	 * @note Really should only be used when the web driver is sucking at
	 *       exposing functionality natively
	 * @param script
	 *            The script to execute
	 * @param element
	 *            The target of the script, referenced as arguments[0]
	 */
	public void trigger(String script, WebElement element) {
		((JavascriptExecutor) driver).executeScript(script, element);
	}

	/**
	 * Executes a script
	 * 
	 * @note Really should only be used when the web driver is sucking at
	 *       exposing functionality natively
	 * @param script
	 *            The script to execute
	 */
	public Object trigger(String script) {
		return ((JavascriptExecutor) driver).executeScript(script);
	}

	/**
	 * Opens a new tab for the given URL
	 * 
	 * @param url
	 *            The URL to
	 * @throws JavaScriptException
	 *             If unable to open tab
	 */
	public void openTab(String url) {
		logger.info("Opening link in new window");
		String script = "var d=document,a=d.createElement('a');a.target='_blank';a.href='%s';a.innerHTML='.';d.body.appendChild(a);return a";
		Object element = trigger(String.format(script, url));
		if (element instanceof WebElement) {
			WebElement anchor = (WebElement) element;
			anchor.click();
			trigger("var a=arguments[0];a.parentNode.removeChild(a);", anchor);
		} else {
			throw new JavaScriptException(element, "Unable to open tab", 1);
		}
	}

	/**
	 * Switches to the non-current window
	 * 
	 * @throws InterruptedException
	 */
	public void switchToNewWindow() {
		try {
			Set<String> handles = driver.getWindowHandles();
			String current = driver.getWindowHandle();
			handles.remove(current);
			String newTab = handles.iterator().next();
			logger.info("Switching to new window");
			driver.switchTo().window(newTab);
			Thread.sleep(randInt(5000, 10000));
		} catch (InterruptedException e) {
			logger.error("InterruptedException for seed: " + seed.getUser() + " with password: " + seed.getPassword()
					+ " " + e.getMessage() + " ", e);
		} catch (NoSuchElementException e) {
			logger.error("NoSuchElementException for seed: " + seed.getUser() + " with password: " + seed.getPassword()
					+ " " + e.getMessage() + " ", e);
		} catch (StaleElementReferenceException e) {
			logger.error("StaleElementReferenceException for seed: " + seed.getUser() + " with password: "
					+ seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (ElementNotVisibleException e) {
			logger.error("ElementNotVisibleException for seed: " + seed.getUser() + " with password: "
					+ seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (ElementNotFoundException e) {
			logger.error("ElementNotFoundException for seed: " + seed.getUser() + " with password: "
					+ seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (UnhandledAlertException e) {
			logger.error("UnhandledAlertException for seed: " + seed.getUser() + " with password: " + seed.getPassword()
					+ " " + e.getMessage() + " ", e);
		} catch (WebDriverException e) {
			logger.error("WebDriverException for seed: " + seed.getUser() + " with password: " + seed.getPassword()
					+ " " + e.getMessage() + " ", e);
		}
	}

	/**
	 * Switches to the non-current window
	 * 
	 * @throws InterruptedException
	 */
	public void switchToPreviousWindow() {
		try {
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
			logger.error("InterruptedException for seed: " + seed.getUser() + " with password: " + seed.getPassword()
					+ " " + e.getMessage() + " ", e);
		} catch (NoSuchElementException e) {
			logger.error("NoSuchElementException for seed: " + seed.getUser() + " with password: " + seed.getPassword()
					+ " " + e.getMessage() + " ", e);
		} catch (StaleElementReferenceException e) {
			logger.error("StaleElementReferenceException for seed: " + seed.getUser() + " with password: "
					+ seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (ElementNotVisibleException e) {
			logger.error("ElementNotVisibleException for seed: " + seed.getUser() + " with password: "
					+ seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (ElementNotFoundException e) {
			logger.error("ElementNotFoundException for seed: " + seed.getUser() + " with password: "
					+ seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (UnhandledAlertException e) {
			logger.error("UnhandledAlertException for seed: " + seed.getUser() + " with password: " + seed.getPassword()
					+ " " + e.getMessage() + " ", e);
		} catch (WebDriverException e) {
			logger.error("WebDriverException for seed: " + seed.getUser() + " with password: " + seed.getPassword()
					+ " " + e.getMessage() + " ", e);
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

	public static boolean isClickable(WebDriver driver, WebElement webElement) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 15);
			wait.until(ExpectedConditions.elementToBeClickable(webElement));
			return true;
		} catch (WebDriverException e) {
			return false;
		}
	}
	
	/**
	 * 
	 * @param driver
	 * @param seed
	 */
	private void gatherPersonalInfo(WebDriver driver, Seed seed) {
		try {
			logger.info("Gathering personal information");
			driver.get(Constant.PERSONAL_INFO_PAGE);
			
			WebElement fullname = driver.findElement(By.id("fullname"));
			String[] fullNameElement = fullname.getText().trim().split(Constant.LINE_BREAK);
			String[] fullName = fullNameElement[0].split(Constant.BLANK_SPACE);
			String firstName = Character.toUpperCase(fullName[0].charAt(0)) + fullName[0].substring(1);
			String lastName = Character.toUpperCase(fullName[1].charAt(0)) + fullName[1].substring(1);
			seed.setFirstName(firstName);
			seed.setLastName(lastName);
			
			WebElement genderElement = driver.findElement(By.id("gender"));
			String[] gender = genderElement.getText().trim().split(Constant.LINE_BREAK);
			seed.setGender(gender[0]);
			
			WebElement birthday = driver.findElement(By.id("birthday"));
			String[] birthDate = birthday.getText().trim().split(Constant.LINE_BREAK);
			DateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy");
			Date date = dateFormat.parse(birthDate[0]);
			DateFormat df = new SimpleDateFormat("YYYY-MM-dd");
			seed.setBirthDate(df.format(date));
			
			JDBC.updateSeedPersonalInfo(seed);
			logger.info("Personal information saved in the database");
			
			driver.get(Constant.YAHOO_MAIL_RO_URL);
			
			if (driver.findElements(By.id("login-passwd")).size() > 0) {
				WebElement passwordInput = driver.findElement(By.id("login-passwd"));
				human.type(passwordInput, seed.getPassword());
			}
			if (driver.findElements(By.id("login-signin")).size() > 0) {
				driver.findElement(By.id("login-signin")).click();
				Thread.sleep(5000);
			}
			
		} catch (NoSuchElementException | ElementNotVisibleException | ElementNotFoundException | StaleElementReferenceException | UnhandledAlertException | InterruptedException e) {
			logger.error("Error with seed: " + seed.getUser() + " with password: " + seed.getPassword() + " message: " + e.getMessage());
		} catch (WebDriverException e) {
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 
	 * @param driver
	 */
	private void clickAllNotSapm(WebDriver driver) {
		try {
			logger.info("Checking spam folder");
			if (driver.findElements(By.id("spam-label")).size() > 0) {
				WebElement spam = driver.findElement(By.id("spam-label"));
				logger.info("Clicking spam folder");
				spam.click();
				Thread.sleep(5000);
				if (driver.findElements(By.className("list-view-item")).size() > 0) {
					logger.info("There are emails in the spam folder");
					if(driver.findElements(By.xpath("//span[@id='btn-ml-cbox']/label/input")).size() > 0){
						WebElement checkbox = driver.findElement(By.xpath("//span[@id='btn-ml-cbox']/label/input"));
						if (!checkbox.isSelected()) {
							logger.info("Checking not spam checkbox");
							checkbox.click();
							Thread.sleep(2500);
						}
						if(driver.findElements(By.id("btn-not-spam")).size() > 0){
							WebElement notSpam = driver.findElement(By.id("btn-not-spam"));
							logger.info("Clicking NOT SPAM button");
							notSpam.click();
							Thread.sleep(5000);
						}
					}
				}else{
					logger.info("There are NO emails in the spam folder");
				}
			}
		} catch (NoSuchElementException | ElementNotVisibleException | ElementNotFoundException | StaleElementReferenceException | UnhandledAlertException | InterruptedException e) {
			logger.error("Error with seed: " + seed.getUser() + " with password: " + seed.getPassword() + " message: " + e.getMessage());
		} catch (WebDriverException e) {
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

}
