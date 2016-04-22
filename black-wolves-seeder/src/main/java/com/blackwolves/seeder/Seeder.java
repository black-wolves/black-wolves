package com.blackwolves.seeder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
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
	
	private String invalidMessage;

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
		
//		seed.setUser("roderigocuffarouwr@yahoo.com");
//		seed.setPassword("mqA9dhmzQZ");

		WebDriver driver = createWebDriver();

		human = generateRandomHumanUser();

		visitSomewhereBefore(driver);
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

			driver.get(yahooUrl);
			Thread.sleep(YahooRunnable.randInt(2500, 3500));

			WebElement accountInput = driver.findElement(By.id("login-username"));
			human.type(accountInput, seed.getUser());

			if(driver.findElements(By.id("login-signin")).size() > 0 && (Constant.CONTINUE.equals(driver.findElement(By.id("login-signin")).getText()) || Constant.Next.equals(driver.findElement(By.id("login-signin")).getText()))) {
				driver.findElement(By.id("login-signin")).click();
				Thread.sleep(YahooRunnable.randInt(2500, 3500));
				
				WebElement passwordInput = driver.findElement(By.id("login-passwd"));
				human.type(passwordInput, seed.getPassword());
				Thread.sleep(YahooRunnable.randInt(2500, 3500));
				
			}else if (driver.findElements(By.id("login-passwd")).size() > 0) {
				WebElement passwordInput = driver.findElement(By.id("login-passwd"));
				human.type(passwordInput, seed.getPassword());
				Thread.sleep(YahooRunnable.randInt(2500, 3500));
				
			}
			if (driver.findElements(By.id("login-signin")).size() > 0) {
				driver.findElement(By.id("login-signin")).click();
				Thread.sleep(YahooRunnable.randInt(2500, 3500));
			} else {
				logger.info("Already logged in..Moving forward!");
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
		logger.info("Firefox Created");
		return driver;
	}

	private Human generateRandomHumanUser() {
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
			Thread.sleep(5000);
			if (driver.findElements(By.className("uh-srch-btn")).size() > 0) {
				logger.info("----------   Old yahoo version   ----------");
			} else if (driver.findElements(By.id("UHSearchProperty")).size() > 0) {
				logger.info("LOGGED IN!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				checkMultipleAccountsPanel(driver);
				handler = new ModernYahooRunnable(driver, seed, human, logger);
			} else if (driver.findElements(By.id("mail-search-btn")).size() > 0) {
				logger.info("##########   New yahoo version   ##########");
			} else if (driver.findElements(By.id("comm-channel-module")).size() > 0) {
				logger.info("LOGGED IN and phone validation found!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				driver.get("http://mail.yahoo.com");
				handler = new ModernYahooRunnable(driver, seed, human, logger);
			} else if (driver.findElements(By.id("skipbtn")).size() > 0) {
				logger.info("LOGGED IN and Dont get locked out of your account, clicking skip button!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				driver.findElement(By.id("skipbtn")).click();
				handler = new ModernYahooRunnable(driver, seed, human, logger);
			} else if(driver.findElements(By.id("mbr-bd")).size() > 0){
				invalidMessage = "Not able to login, validate accout by phone";
				logger.info("==========   "+ invalidMessage +"   ==========");
			} else if(driver.findElements(By.id("mbr-login-error")).size() > 0){
				String error = driver.findElement(By.id("mbr-login-error")).getText();
				if(error.contains(Constant.ERROR_TEXT_1)){
					invalidMessage = "Not able to login, The email and password you entered dont match.";
					logger.info("==========   "+ invalidMessage +"   ==========");
				} else if(error.contains(Constant.ERROR_TEXT_2)){
					invalidMessage = "Sorry, we dont recognize this email.";
				} else{
					invalidMessage = "mbr-login-error not found";
					 logger.info("==========   "+ invalidMessage +"   ==========");
					 YahooRunnable.getScreenShot(driver, YahooRunnable.randInt(1, 100) + "newVersion" + UUID.randomUUID());
				}
			} else{
				invalidMessage = "THERE IS A NEW YAHOO VERSION IN TOWN";
				 logger.info("==========   "+ invalidMessage +"   ==========");
				 YahooRunnable.getScreenShot(driver, YahooRunnable.randInt(1, 100) + "newVersion" + UUID.randomUUID());
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
		return handler;
	}

	private void checkMultipleAccountsPanel(WebDriver driver) {
		if (driver.findElements(By.id("imapInOnboardDlg")).size() > 0) {
			logger.info("Multiple accounts Panel Found");
			if (driver.findElement(By.xpath("//div[@id='imapInOnboardDlg']/a")).isDisplayed()) {
				driver.findElement(By.xpath("//div[@id='imapInOnboardDlg']/a")).click();
			}
		}
	}

	public List<String[]> generateList(String route, String file) {
		List<String[]> list = new ArrayList<String[]>();
		try {
			@SuppressWarnings("resource")
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
	
	public void visitSomewhereBefore(WebDriver driver) {
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
}

	public void logAttempts(String log) {
		File file = new File("/var/www/total.txt");
		FileWriter fw;
		String newline = System.getProperty("line.separator");

		try {
			fw = new FileWriter(file, true);
			fw.write(log);
			fw.write(newline);

			fw.close();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}

	}
}
