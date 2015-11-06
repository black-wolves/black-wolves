package com.blackwolves.seeder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Dimension;
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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;

import com.blackwolves.persistence.entity.Seed;
import com.blackwolves.persistence.util.Constant;
import com.gargoylesoftware.htmlunit.ElementNotFoundException;

import au.com.bytecode.opencsv.CSVReader;

/**
 * @author gaston.dapice
 *
 */
public class Seeder implements Runnable {

	private Logger logger;

	private YahooRunnable handler;

	private Human human;

	private Seed dbSeed;

	private String[] seed;

	public Seeder() {
	}

	public Seeder(String[] seed, Logger logger) {
		logger.info("Seeder constructor");
		this.seed = seed;
		this.logger = logger;
	}

	public void run() {
		// addPermittedSender();
		checkMail();
	}

	/**
	 */
	private void checkMail() {
		logger.info("Entering first do while");
		dbSeed = new Seed(seed[0], seed[1]);

		WebDriver driver = createWebDriver();
		logger.info("Firefox Created");

		if (ModernYahooRunnable.randInt(0, 9) == 9) {
			checkUserAgent(driver);
		}

		// visitSomewhereBefore(driver);

		human = generateRandomHumanUser();

		yahooLogin(Constant.YAHOO_MAIL_RO_URL, seed, driver);

		handler = validateYahooVersion(driver, seed[0] + "," + seed[1]);

		if (handler != null) {
			handler.runProcess();
			while (!YahooRunnable.throwDice()) {
				logger.info("Entering Process");
				handler.runProcess();
				handler.waitForIt(60000, 120000);

			}
			driver.quit();

		} else {
			logger.info("New Interface detected.Exiting");
		}
	}

	private void newAddToAddressBook(WebDriver driver) {
		try {
			WebElement contacts = driver.findElement(By.xpath("//*[@id='nav']/ul/li[2]/a"));
			contacts.click();
			WebElement div = driver.findElement(By.xpath("//*[@id='paneshell']/div"));
			Actions myMouse = new Actions(driver);
			myMouse.moveToElement(div).build().perform();
			myMouse.click();
			myMouse.click().build().perform();
			myMouse.moveByOffset(86, 266);
			div.click();

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

	private void visitSomewhereBefore(WebDriver driver) {
		String[] sites = new String[10];
		sites[0] = "http://lanacion.com";
		sites[1] = "http://ole.com.ar";
		sites[2] = "http://marca.com";
		sites[3] = "http://dig.com";
		sites[4] = "http://yahoo.com";
		sites[5] = "http://google.com";
		sites[6] = "http://clarin.com";
		sites[7] = "http://amazon.com";
		sites[8] = "http://ebay.com";
		sites[9] = "http://mcdonalds.com";
		int random = ModernYahooRunnable.randInt(0, 9);
		logger.info("***************** Visiting :" + sites[random]);
		driver.get(sites[random]);

		Set<Cookie> allCookies = driver.manage().getCookies();

		for (Cookie cookie : allCookies) {
			logger.info("***************** Cookies? :" + cookie.getName());

		}
	}

	private void addPermittedSender() {

		List<String[]> seeds = generateList("/Users/danigrane/Downloads/Madrivo/seeds/", "seeds2.csv");
		for (int i = 1; i < seeds.size(); i++) {
			try {
				seed = seeds.get(i);
				dbSeed = new Seed(seed[0], seed[1]);

				WebDriver driver = createWebDriver();

				human = generateRandomHumanUser();

				yahooLogin(Constant.YAHOO_MAIL_RO_URL, seed, driver);
				Thread.sleep(5000);

				driver.get("https://edit.yahoo.com/commchannel/manage");

				driver.findElement(By.id("addLink")).click();
				Thread.sleep(3000);

				driver.findElement(By.id("addCommStr")).clear();
				driver.findElement(By.id("addCommStr")).sendKeys("postmaster@betoacostadalefuncionanamelamily.ro");
				driver.findElement(By.id("saveLink")).click();
				Thread.sleep(3000);
				driver.findElement(By.id("yui-gen1-button")).click();
				Thread.sleep(2000);
				logger.debug(" Suscription successful: " + seed[0]);
				driver.quit();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchElementException e) {
				System.out.println("The seed " + seed[0] + " failed to suscribed");
			}

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
			if (s[0].equals(myEmail)) {
				return Integer.valueOf(s[1]);
			}
		}
		return 0;
	}

	/**
	 * 
	 */
	private void checkUserAgent(WebDriver driver) {
		// WebDriver driver = createWebDriver();
		logger.info("checking user agent");
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
				"Mozilla/5.0 (iPhone; CPU iPhone OS 7_0 like Mac OS X; en-us) AppleWebKit/537.51.1 (KHTML, like Gecko) Version/7.0 Mobile/11A465 Safari/9537.53");
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setPlatform(org.openqa.selenium.Platform.ANY);
		capabilities.setCapability(FirefoxDriver.PROFILE, profile);

		WebDriver driver = new FirefoxDriver(capabilities);
		driver.manage().window().setSize(new Dimension(320, 568));

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
		// int diffHours = (int) (diff / (60 * 60 * 1000));
		// int diffDays = (int) (diff / (24 * 60 * 60 * 1000));
		int diffMin = (int) (diff / (60 * 1000));
		// int diffSec = (int) (diff / (1000));
		return diffMin;
	}

	private Human generateRandomHumanUser() {
		logger.info("Random Human generation started");
		int number = YahooRunnable.randInt(0, 10);
		if (number <= 3) {
			return new DumbHuman();
		} else if (number >= 4 && number <= 8) {
			return new AverageHuman();
		}
		return new AverageHuman();
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

			logger.info("Introducing username: " + seed[0]);
			WebElement accountInput = driver.findElement(By.id("login-username"));
			human.type(accountInput, seed[0]);

			logger.info("Introducing password: " + seed[1]);
			WebElement passwordInput = driver.findElement(By.id("login-passwd"));
			human.type(passwordInput, seed[1]);

			if (driver.findElements(By.id("login-signin")).size() > 0) {
				logger.info("Clicking login button");

				Thread.sleep(YahooRunnable.randInt(1000, 25000));
				driver.findElement(By.id("login-signin")).click();
				
			} else {
				logger.info("Already logged in..Moving forward!");
			}
			
			getScreenShot(driver, "After_click_login");
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

			WebDriverWait wait = new WebDriverWait(driver, 1000);

			checkJustOneTapPage(driver, seed);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("msgListItem")));

			if (driver.findElements(By.className("msgListItem")).size() > 0) {
				logger.info("************   Mobile version   **********");
				handler = new MobileRunnable(driver, seed, human, logger);
			} else if (driver.findElements(By.className("uh-srch-btn")).size() > 0) {
				logger.info("**********   Old yahoo version   **********");
				handler = new OldYahooRunnable(driver, seed, human, logger);
			} else if (driver.findElements(By.id("UHSearchProperty")).size() > 0) {
				logger.info("**********   New yahoo 2 version   **********");
				handler = new ModernYahooRunnable(driver, seed, human, logger);
			} else if (driver.findElements(By.id("mail-search-btn")).size() > 0) {
				logger.info("**********   New yahoo version   **********");
			} else if (driver.findElements(By.id("comm-channel-module")).size() > 0) {
				logger.info("*************  Phone validation. Going to URL  **************");
				driver.get("http://mail.yahoo.com");
				handler = new ModernYahooRunnable(driver, seed, human, logger);
			} else {
				getScreenShot(driver, YahooRunnable.randInt(1, 100) + "newVersion");
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

	private void checkJustOneTapPage(WebDriver driver, String seed) {
		try {
			WebElement skipLink = driver.findElement(By.xpath("//*[@id='mobile_site_link']"));
			if (skipLink != null) {
				logger.info("************   Just One Tap Away Mobile Page   **********");
				skipLink.click();
				handler = new MobileRunnable(driver, seed, human, logger);
			}

		} catch (NoSuchElementException e) {
			logger.info("Just One Tap Page not shown");
		}

	}

	/**
	 * 
	 */
	public void addToAddressBook(WebDriver driver) {
		List<String[]> domains = YahooRunnable.generateDomainsList();
		WebElement element;
		for (String[] d : domains) {
			try {
				if (driver.findElements(By.className("list-view-item-container")).size() > 0) {
					logger.info("Adding domains to address book");
					element = driver.findElement(By.className("list-view-item-container"))
							.findElement(By.className("first"));
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
				} else {
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
			if (validateIfFolderExists(driver)) {
				logger.info("Folder already exists");
			} else {
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
		if (driver.findElements(By.id("Folders")).size() > 0) {
			if (driver.findElement(By.id("Folders")).findElements(By.className("foldername")).size() > 0) {
				return driver.findElement(By.id("Folders")).findElement(By.className("foldername")).getText()
						.equals(Constant.ALL);
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

			FileUtils.copyFile(scrFile, new File("/var/www/errors/" + name + ".jpg"));
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}

	}

	public static List<String[]> generateList(String route, String file) {
		List<String[]> list = new ArrayList<String[]>();
		try {
			CSVReader reader = new CSVReader(new FileReader(route + file));
			list = reader.readAll();
		} catch (FileNotFoundException e) {
			// logger.error(e.getMessage(), e);
		} catch (IOException e) {
			// logger.error(e.getMessage(), e);
		}
		return list;
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

	public String getRandomUA() {
		String[] uas = new String[10];
		uas[0] = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.10; rv:40.0) Gecko/20100101 Firefox/40.0";
		uas[1] = "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2224.3 Safari/537.36";
		uas[3] = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.12 Safari/535.11";
		uas[4] = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36";
		uas[5] = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2227.1 Safari/537.36";
		uas[6] = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2227.0 Safari/537.36";
		uas[7] = "Mozilla/5.0 (Windows NT 10.0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/40.0.2214.93 Safari/537.36";
		uas[8] = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/36.0.1944.0 Safari/537.36";
		uas[9] = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:40.0) Gecko/20100101 Firefox/40.1";
		return uas[ModernYahooRunnable.randInt(0, 9)];

	}

	/**
	 * @return the seed
	 */
	public String[] getSeed() {
		return seed;
	}

	/**
	 * @param seed
	 *            the seed to set
	 */
	public void setSeed(String[] seed) {
		this.seed = seed;
	}
}
