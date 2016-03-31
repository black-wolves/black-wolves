package com.blackwolves.seeder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
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

import com.blackwolves.seeder.util.Constant;
import com.gargoylesoftware.htmlunit.ElementNotFoundException;

/**
 * @author gaston.dapice
 *
 */
public class Seeder implements Runnable {

	private Logger logger;

	private YahooRunnable handler;

	private Human human;
	
	private Seed seed;

	public Seeder() {
		
	}

	public Seeder(Seed seed, Logger logger) {
		this.seed = seed;
		this.logger = logger;
	}

	/**
	 * 
	 */
	public void run() {
		checkMail();
	}

	/**
	 */
	private void checkMail() {
		logger.info("Entering first do while");

		WebDriver driver = createWebDriver();
		logger.info("Firefox Created");

		human = generateRandomHumanUser();

		yahooLogin(Constant.YAHOO_MAIL_RO_URL, seed, driver);

		handler = validateYahooVersion(driver, seed);

		if (handler != null) {
			handler.runProcess();
		
			logger.info("Finished!!");

		} else {
			logAttempts(seed.getUser() + "," + seed.getPassword() + "  was not able to connect");
			logger.info("New Interface detected.Exiting");
		}
		driver.close();
		driver.quit();
	}
	
	/**
	 * @param yahooUrl
	 * @param seed
	 * @param driver
	 * @param session
	 * @throws IOException
	 * @throws InterruptedException
	 */
	private void yahooLogin(String yahooUrl, Seed seed, WebDriver driver) {
		logger.info("Trying to login in....");
		try {

			Thread.sleep(YahooRunnable.randInt(2500, 3500));
			logger.info("Getting to the URL: " + yahooUrl);
			driver.get(yahooUrl);

			logger.info("Introducing username: " + seed.getUser());
			WebElement accountInput = driver.findElement(By.id("login-username"));
			human.type(accountInput, seed.getUser());

			if(driver.findElements(By.id("login-signin")).size() > 0 && (Constant.CONTINUE.equals(driver.findElement(By.id("login-signin")).getText()) || Constant.Next.equals(driver.findElement(By.id("login-signin")).getText()))) {
				logger.info("Clicking CONTINUE button");
				driver.findElement(By.id("login-signin")).click();
				
				Thread.sleep(YahooRunnable.randInt(1500, 2500));
				
				logger.info("Introducing password: " + seed.getPassword());
				WebElement passwordInput = driver.findElement(By.id("login-passwd"));
				human.type(passwordInput, seed.getPassword());
				
			}else if (driver.findElements(By.id("login-passwd")).size() > 0) {
				logger.info("Introducing password: " + seed.getPassword());
				WebElement passwordInput = driver.findElement(By.id("login-passwd"));
				human.type(passwordInput, seed.getPassword());
				
			}
			logger.info("Clicking LOGIN button");
			if (driver.findElements(By.id("login-signin")).size() > 0) {
				driver.findElement(By.id("login-signin")).click();
				Thread.sleep(YahooRunnable.randInt(1000, 2000));
				logger.info("LOGGED IN!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
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

	/**
	 * @return
	 */
	private WebDriver createWebDriver() {
		logger.info("Creating the web driver");

		FirefoxProfile profile = new FirefoxProfile();
		File modifyHeaders = new File("/var/www/modify_headers.xpi");
		File canvasBlocker = new File("/var/www/canvasblocker-0.2.1-Release-fx+an.xpi");
		profile.setEnableNativeEvents(false);
		try {
			profile.addExtension(modifyHeaders);
			profile.addExtension(canvasBlocker);
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
		profile.setPreference("general.useragent.override", getRandomUA());

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
		return new FastHuman();
	}

	/**
	 * 
	 * @param driver
	 * @param seed
	 */
	private YahooRunnable validateYahooVersion(WebDriver driver, Seed seed) {
		try {
			logger.info("Validating yahoo version");
			Thread.sleep(5000);
			if (driver.findElements(By.className("uh-srch-btn")).size() > 0) {
				logger.info("----------   Old yahoo version   ----------");
				handler = new OldYahooRunnable(driver, seed, human, logger);
			} else if (driver.findElements(By.id("UHSearchProperty")).size() > 0) {
				logger.info("**********   New yahoo 2 version   **********");
				checkMultipleAccountsPanel(driver);
				handler = new ModernYahooRunnable(driver, seed, human, logger);
			} else if (driver.findElements(By.id("mail-search-btn")).size() > 0) {
				logger.info("##########   New yahoo version   ##########");
			} else if (driver.findElements(By.id("comm-channel-module")).size() > 0) {
				logger.info("**********   Phone validation. Going to UR   **********");
				driver.get("http://mail.yahoo.com");
				handler = new ModernYahooRunnable(driver, seed, human, logger);
			}else {
				 getScreenShot(driver, YahooRunnable.randInt(1, 100) + "newVersion");
				logger.info("==========   There is a new yahoo version in town   ==========");
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

	private void checkMultipleAccountsPanel(WebDriver driver) {
		logger.info("Checking multiple accounts Panel Found");
		if (driver.findElements(By.id("imapInOnboardDlg")).size() > 0) {
			logger.info("Multiple accounts Panel Found");
			if (driver.findElement(By.xpath("//div[@id='imapInOnboardDlg']/a")).isDisplayed()) {
				driver.findElement(By.xpath("//div[@id='imapInOnboardDlg']/a")).click();
			}
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

	public List<String[]> generateList(String route, String file) {
		List<String[]> list = new ArrayList<String[]>();
		try {
			CSVReader reader = new CSVReader(new FileReader(route + file));
			list = reader.readAll();
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
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
		String[] uas = new String[9];
		uas[0] = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.10; rv:40.0) Gecko/20100101 Firefox/40.0";
		uas[1] = "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2224.3 Safari/537.36";
		uas[2] = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:40.0) Gecko/20100101 Firefox/40.1";
		uas[3] = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.12 Safari/535.11";
		uas[4] = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36";
		uas[5] = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2227.1 Safari/537.36";
		uas[6] = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2227.0 Safari/537.36";
		uas[7] = "Mozilla/5.0 (Windows NT 10.0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/40.0.2214.93 Safari/537.36";
		uas[8] = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/36.0.1944.0 Safari/537.36";
		return uas[ModernYahooRunnable.randInt(0, 8)];

	}

	public static void logAttempts(String log) {
		File file = new File("/var/www/total.txt");
		FileWriter fw;
		String newline = System.getProperty("line.separator");

		try {
			fw = new FileWriter(file, true);
			fw.write(log);
			fw.write(newline);

			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
