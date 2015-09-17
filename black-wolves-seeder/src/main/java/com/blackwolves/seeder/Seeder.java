package com.blackwolves.seeder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;

import au.com.bytecode.opencsv.CSVReader;

import com.blackwolves.persistence.entity.Seed;
import com.blackwolves.persistence.util.Constant;
import com.gargoylesoftware.htmlunit.ElementNotFoundException;

/**
 * @author gaston.dapice
 *
 */
//@Component
public class Seeder implements Runnable {

	private Logger logger;

	private YahooRunnable handler;

	private Human human;
	
	private Seed dbSeed ;
	
	private String[] seed;
	
	public Seeder() {
	}
	
	public Seeder(String[] seed, Logger logger) {
		this.seed = seed;
		this.logger = logger;
	}

	public void run() {
		checkMail();
	}

	/**
	 */
	private void checkMail() {

		do {
			dbSeed = new Seed(seed[0], seed[1]);
			dbSeed.setPid(getPidFromFile(seed[0]));
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				logger.error(e.getMessage(), e);
			}
		} while (dbSeed.getPid() == 0);
		
		WebDriver driver = createWebDriver();

		logger.info("Firefox Created");

		human = generateRandomHumanUser();

		yahooLogin(Constant.YAHOO_MAIL_RO_URL, seed, driver);

		handler = validateYahooVersion(driver, seed[0]+","+seed[1]);

		if (handler != null) {

			addToAddressBook(driver);

			createNewFolder(driver);

			handler.runProcess();
			
			dbSeed.setWakeUp(DateUtils.addMinutes(new Date(), 3));
			
			while(true){
				
				int diff = calculateDifferenceBetweenDatesInMinutes(dbSeed.getWakeUp(), new Date());
				if (diff >= 0) {
					logger.info("Running the process");
					handler.runProcess();
					dbSeed.setWakeUp(DateUtils.addMinutes(new Date(), 3));
				}else{
					logger.info("Waiting for the Date to reactivate. Time to wait : "+diff+ " minutes");
					try {
						Thread.sleep(60000);
					} catch (InterruptedException e) {
						logger.error(e.getMessage(), e);
					}
				}
			}
			

		} else {
			logger.info("New Interface detected.Exiting");
		}
	}

	private int getPidFromFile(String myEmail) {
		List<String[]> pids = new ArrayList<String[]>();
		try {
			CSVReader pidsReader = new CSVReader(new FileReader(Constant.ROUTE + "pids.txt"));
			pids = pidsReader.readAll();
			pidsReader.close();
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		for (String[] s : pids) {
			if(s[0].equals(myEmail)){
				return Integer.valueOf(s[1]);
			}
		}
		return 0;
	}

	/**
	 * 
	 */
	private void testPurposes() {
		WebDriver driver = createWebDriver();
		logger.info("Firefox Created");
		driver.get("http://www.useragentstring.com/");
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(scrFile, new File("/var/www/test_driver.jpg"));
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * @return
	 */
	private WebDriver createWebDriver() {
		logger.info("Creating the web driver");

		FirefoxProfile profile = new FirefoxProfile();
		File modifyHeaders = new File("/var/www/modify_headers.xpi");
		profile.setEnableNativeEvents(false);
		try {
			profile.addExtension(modifyHeaders);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		profile.setPreference("modifyheaders.headers.count", 1);
		profile.setPreference("modifyheaders.headers.action0", "Add");
		profile.setPreference("modifyheaders.headers.name0", "sox");
		profile.setPreference("modifyheaders.headers.value0", "305471");
		profile.setPreference("modifyheaders.headers.enabled0", true);
		profile.setPreference("modifyheaders.config.active", true);
		profile.setPreference("modifyheaders.config.alwaysOn", true);
		profile.setPreference("general.useragent.override",
				"Mozilla/5.0 (Macintosh; Intel Mac OS X 10.10; rv:40.0) Gecko/20100101 Firefox/40.0");

		DesiredCapabilities capabilities = new DesiredCapabilities();
		// capabilities.setBrowserName("Graneeeeeeekk");
		capabilities.setPlatform(org.openqa.selenium.Platform.ANY);
		capabilities.setCapability(FirefoxDriver.PROFILE, profile);

		// caps.setCapability("applicationCacheEnabled", false);
		// String PROXY = "192.168.1.111:8888";
		// org.openqa.selenium.Proxy proxy = new org.openqa.selenium.Proxy();
		// proxy.setHttpProxy(PROXY)
		// .setFtpProxy(PROXY)
		// .setSslProxy(PROXY);
		// caps.setCapability(CapabilityType.PROXY, proxy);
		WebDriver driver = new FirefoxDriver(capabilities);

		driver.manage().window().maximize();
		return driver;
	}

	/**
	 * Calculates the hours of difference between the two given dates
	 * 
	 * @param from
	 * @param to
	 * @return int
	 */
	private int calculateDifferenceBetweenDates(Date from, Date to) {
		long diff = to.getTime() - from.getTime();
		int diffHours = (int) (diff / (60 * 60 * 1000));
		// int diffDays = (int) (diff / (24 * 60 * 60 * 1000));
		// int diffMin = (int) (diff / (60 * 1000));
		// int diffSec = (int) (diff / (1000));
		return diffHours;
	}

	private int calculateDifferenceBetweenDatesInMinutes(Date from, Date to) {
		long diff = to.getTime() - from.getTime();
		//int diffHours = (int) (diff / (60 * 60 * 1000));
		// int diffDays = (int) (diff / (24 * 60 * 60 * 1000));
		 int diffMin = (int) (diff / (60 * 1000));
		// int diffSec = (int) (diff / (1000));
		return diffMin;
	}
	private Human generateRandomHumanUser() {
		logger.info("Random Human generation started");
		int number = YahooRunnable.randInt(0, 10);
		if (number <= 2) {
			return new DumbHuman();
		} else if (number >= 4 && number <= 8) {
			return new AverageHuman();
		}
		return new FastHuman();
	}

	/**
	 * @param yahooUrl
	 * @param seed
	 * @param driver
	 * @param session
	 * @throws IOException
	 * @throws InterruptedException
	 */
	private void yahooLogin(String yahooUrl, String[] seed, WebDriver driver) {
		logger.info("Trying to login in....");
		try {

			Thread.sleep(YahooRunnable.randInt(2500, 3500));
			logger.info("Getting to the url: " + yahooUrl);
			driver.get(yahooUrl);

			logger.info("SCREENSHOT");
			getScreenShot(driver, "quepasa");

			logger.info("Introducing username: " + seed[0]);
			WebElement accountInput = driver.findElement(By.id("login-username"));
			human.type(accountInput, seed[0]);

			logger.info("Introducing password: " + seed[1]);
			WebElement passwordInput = driver.findElement(By.id("login-passwd"));
			human.type(passwordInput, seed[1]);

			logger.info("Clicking login button");
			if (driver.findElements(By.id("login-signin")).size() > 0) {
				driver.findElement(By.id("login-signin")).click();
			} else {
				logger.info("Already logged in..Moving forward!");
			}
		} catch (InterruptedException e) {
			logger.error(e.getMessage(), e);
		} catch (NoSuchElementException e) {
			logger.error("NoSuchelementException");
		} catch (StaleElementReferenceException e) {
			logger.error("StaleElementReferenceException");
		} catch (ElementNotVisibleException e) {
			logger.error("ElementNotVisibleException");
		} catch (ElementNotFoundException e) {
			logger.error("ElementNotFoundException");
		}
	}

	/**
	 * 
	 * @param driver
	 * @param seed
	 */
	private YahooRunnable validateYahooVersion(WebDriver driver, String seed) {
		try {
			Thread.sleep(10000);
			if (driver.findElements(By.className("uh-srch-btn")).size() > 0) {
				logger.info("**********   Old yahoo version   **********");
				handler = new OldYahooRunnable(driver, seed, human, logger);
			} else if (driver.findElements(By.id("UHSearchProperty")).size() > 0) {
				logger.info("**********   New yahoo 2 version   **********");
				handler = new ModernYahooRunnable(driver, seed, human, logger);
			} else if (driver.findElements(By.id("mail-search-btn")).size() > 0) {
				logger.info("**********   New yahoo version   **********");
			} else {
				logger.info("**********   There is a new yahoo version in town  **********");
			}
		} catch (InterruptedException e) {
			logger.error(e.getMessage(), e);
		} catch (NoSuchElementException e) {
			logger.error("NoSuchelementException");
		} catch (StaleElementReferenceException e) {
			logger.error("StaleElementReferenceException");
		} catch (ElementNotVisibleException e) {
			logger.error("ElementNotVisibleException");
		} catch (ElementNotFoundException e) {
			logger.error("ElementNotFoundException");
		}
		return handler;
	}

	/**
	 * 
	 */
	public void addToAddressBook(WebDriver driver) {
		List<String[]> domains = YahooRunnable.generateDomainsList();
		WebElement element;
		for (String[] d : domains) {
			try {
				if(driver.findElements(By.className("list-view-item-container")).size() > 0){
					logger.info("Adding domains to address book");
					element = driver.findElement(By.className("list-view-item-container")).findElement(By.className("first"));
					rightClick(driver, element);
					Thread.sleep(YahooRunnable.randInt(2500, 3500));
					WebElement menu = driver.findElement(By.id("menu-msglist"));
	
					List<WebElement> li = menu.findElements(By.className("onemsg"));
					WebElement addContact = li.get(3);
					addContact.click();
					Thread.sleep(YahooRunnable.randInt(2500, 3500));
	
					WebElement modal = driver.findElement(By.id("modal-kiosk-addcontact"));
	
					WebElement givenName = modal.findElement(By.id("givenName"));
					givenName.clear();
					human.type(givenName, d[0]);
					givenName.sendKeys(Keys.TAB);
					WebElement middleName = modal.findElement(By.id("middleName"));
					middleName.clear();
					WebElement familyName = modal.findElement(By.id("familyName"));
					familyName.clear();
					WebElement email = modal.findElement(By.className("field-lg"));
					email.clear();
					human.type(email, "newsletter@" + d[0]);
					email.sendKeys(Keys.TAB);
					Thread.sleep(YahooRunnable.randInt(2500, 3500));
					WebElement save = modal.findElement(By.id("saveModalOverlay"));
					save.click();
					Thread.sleep(YahooRunnable.randInt(2500, 3500));
					if (driver.findElements(By.className("error")).size() > 0) {
						WebElement cancel = driver.findElement(By.id("cancelModalOverlay"));
						cancel.click();
						logger.info("Contact was not added: " + d[0]);
						Thread.sleep(YahooRunnable.randInt(2500, 3500));
					} else {
						WebElement done = driver.findElement(By.id("doneModalOverlay"));
						done.click();
						logger.info("Contact added: " + d[0]);
						Thread.sleep(YahooRunnable.randInt(2500, 3500));
					}
				}else{
					logger.info("No emails in inbox, we can't add the domains to the address book");
				}
			} catch (InterruptedException e) {
				logger.error(e.getMessage(), e);
			} catch (NoSuchElementException e) {
				logger.error("NoSuchelementException");
			} catch (StaleElementReferenceException e) {
				logger.error("StaleElementReferenceException");
			} catch (ElementNotVisibleException e) {
				logger.error("ElementNotVisibleException");
			} catch (ElementNotFoundException e) {
				logger.error("ElementNotFoundException");
			}
		}
	}

	private void createNewFolder(WebDriver driver) {
		try {
			if(validateIfFolderExists(driver)){
				logger.info("Folder already exists");
			}else{
				logger.info("Creating new folder");
				WebElement newFolder = driver.findElement(By.id("btn-newfolder"));
				newFolder.click();
				Thread.sleep(YahooRunnable.randInt(1500, 2500));
				WebElement newFolderInput = driver.findElement(By.id("newFolder"));
				human.type(newFolderInput, Constant.ALL);
				WebElement ok = driver.findElement(By.id("okayModalOverlay"));
				ok.click();
				Thread.sleep(YahooRunnable.randInt(2500, 3500));
				if (driver.findElements(By.id("newFolderErr")).size() > 0) {
					logger.info("Folder already exists");
					WebElement cancel = driver.findElement(By.id("cancelModalOverlay"));
					cancel.click();
					Thread.sleep(YahooRunnable.randInt(2500, 3500));
				} else {
					logger.info("Folder created");
					Thread.sleep(YahooRunnable.randInt(2500, 3500));
				}
			}
		} catch (InterruptedException e) {
			logger.error(e.getMessage(), e);
		} catch (NoSuchElementException e) {
			logger.error("NoSuchelementException");
		} catch (StaleElementReferenceException e) {
			logger.error("StaleElementReferenceException");
		} catch (ElementNotVisibleException e) {
			logger.error("ElementNotVisibleException");
		} catch (ElementNotFoundException e) {
			logger.error("ElementNotFoundException");
		}
	}

	/**
	 * @param driver
	 * @return
	 */
	private boolean validateIfFolderExists(WebDriver driver) {
		if(driver.findElements(By.id("Folders")).size() > 0){
			if(driver.findElement(By.id("Folders")).findElements(By.className("foldername")).size() > 0){
				return driver.findElement(By.id("Folders")).findElement(By.className("foldername")).getText().equals(Constant.ALL);
			}
			
		}
		return false;
	}

	/**
	 * 
	 * @param element
	 */
	public void rightClick(WebDriver driver, WebElement element) {
		try {
			Actions action = new Actions(driver).contextClick(element);
			action.build().perform();
			logger.info("Sucessfully Right clicked on the element");
		} catch (StaleElementReferenceException e) {
			logger.error("Element is not attached to the page document " + e.getStackTrace());
		} catch (NoSuchElementException e) {
			logger.error("Element " + element + " was not found in DOM " + e.getStackTrace());
		} catch (Exception e) {
			logger.error("Element " + element + " was not clickable " + e.getStackTrace());
		}
	}

	public void getScreenShot(WebDriver driver, String name) {
		logger.info("****************TAKING SCREENSHOT!*****************");
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		// Now you can do whatever you need to do with it, for example copy
		// somewhere
		try {
			FileUtils.copyFile(scrFile, new File("/var/www/" + name + ".jpg"));
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}

	}

	public void getFingerPrint() {
		DesiredCapabilities caps = new DesiredCapabilities();
		caps.setCapability("binary", "/usr/bin/wires-0.3.0-linux64");
		logger.info("Creating new driver");
		WebDriver driver = new FirefoxDriver(caps);
		String url = "https://panopticlick.eff.org/";
		driver.get(url);
		WebElement clickMe = driver.findElement(By.id("clicklink"));
		clickMe.click();
	}

	/**
	 * @return the seed
	 */
	public String[] getSeed() {
		return seed;
	}

	/**
	 * @param seed the seed to set
	 */
	public void setSeed(String[] seed) {
		this.seed = seed;
	}
}
