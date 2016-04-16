package com.blackwolves.mail;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blackwolves.mail.util.Constant;
import com.gargoylesoftware.htmlunit.ElementNotFoundException;

/**
 * @author gaston.dapice
 *
 */
public class Login implements Runnable {

	private Logger logger = LoggerFactory.getLogger(Login.class);

	private Human human;
	
	private Seed seed;
	
	private String invalidMessage;

	public Login() {
		
	}

	public Login(Seed seed, Logger logger) {
		this.seed = seed;
		this.logger = logger;
	}

	/**
	 * 
	 */
	public void run() {
//		seed.setUser("aaanrwb@yahoo.com");
//		seed.setPassword("@Ra!Y%UcS@j4477");

		WebDriver driver = createWebDriver();

		human = new Human();

		yahooLogin(Constant.YAHOO_MAIL_RO_URL, seed, driver);

		if (validateYahooVersion(driver, seed)) {
			removeConversationMailView(driver);
			writeSeedToFile(seed, true);
			logger.info("Finished!!");

		} else {
			writeSeedToFile(seed, false);
			logger.info("Invalid seed detected");
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
			Thread.sleep(randInt(2500, 3500));

			WebElement accountInput = driver.findElement(By.id("login-username"));
			human.type(accountInput, seed.getUser());

			if(driver.findElements(By.id("login-signin")).size() > 0 && (Constant.CONTINUE.equals(driver.findElement(By.id("login-signin")).getText()) || Constant.Next.equals(driver.findElement(By.id("login-signin")).getText()))) {
				driver.findElement(By.id("login-signin")).click();
				Thread.sleep(randInt(2500, 3500));
				if(driver.findElements(By.id("mbr-login-error")).size() > 0){
					return;
				}
				WebElement passwordInput = driver.findElement(By.id("login-passwd"));
				human.type(passwordInput, seed.getPassword());
				Thread.sleep(randInt(2500, 3500));
				
			}else if (driver.findElements(By.id("login-passwd")).size() > 0) {
				WebElement passwordInput = driver.findElement(By.id("login-passwd"));
				human.type(passwordInput, seed.getPassword());
				Thread.sleep(randInt(2500, 3500));
				
			}
			if (driver.findElements(By.id("login-signin")).size() > 0) {
				driver.findElement(By.id("login-signin")).click();
				Thread.sleep(randInt(2500, 3500));
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
	
	private void removeConversationMailView(WebDriver driver) {

		try {
			Actions myMouse = new Actions(driver);
			WebElement settings = driver.findElement(By.id("yucs-help"));
			myMouse.moveToElement(settings).build().perform();
			Thread.sleep(randInt(1000, 2000));
			settings = driver.findElement(By.id("yucs-help"));
			myMouse.moveToElement(settings).build().perform();
			Thread.sleep(randInt(1000, 2000));
			logger.info("Moving to configuration wheel");
			Thread.sleep(randInt(1000, 2000));
			if (driver.findElements(By.xpath("//div[@id='yucs-help_inner']")).size() > 0) {
				settings = driver.findElement(By.id("yucs-help"));
				myMouse.moveToElement(settings).build().perform();
				Thread.sleep(randInt(1000, 2000));
				if(driver.findElements((By.xpath("//div[@id='yucs-help_inner']/ul/li[2]/a"))).size() > 0){
					driver.findElement(By.xpath("//div[@id='yucs-help_inner']/ul/li[2]/a")).click();
					Thread.sleep(randInt(1000, 2000));
					if (driver.findElement(By.xpath("//input[@id='options-enableConv']")).isSelected()) {
						logger.info("Conversation mode is on. Turning off.");
						driver.findElement(By.xpath("//input[@id='options-enableConv']")).click();
						Thread.sleep(randInt(1000, 2000));
						if(driver.findElements(By.className("selectable")).size() > 0){
							driver.findElement(By.xpath("//ul[@class='selectable']/li[6]/a")).click();
							Thread.sleep(randInt(1000, 2000));
							driver.findElement(By.xpath("//ul[@class='options-settings-pane']/li/div[2]/div/select/option[2]")).click();
							Thread.sleep(randInt(1000, 2000));
						}
					}else if(driver.findElements(By.className("selectable")).size() > 0){
						driver.findElement(By.xpath("//ul[@class='selectable']/li[6]/a")).click();
						Thread.sleep(randInt(1000, 2000));
						driver.findElement(By.xpath("//ul[@class='options-settings-pane']/li/div[2]/div/select/option[2]")).click();
						Thread.sleep(randInt(1000, 2000));
					}
					driver.findElement(By.xpath("//button[@class='left right default btn']")).click();
					Thread.sleep(randInt(1000, 2000));
				}
			}
		} catch (InterruptedException e) {
			logger.error("InterruptedException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (NoSuchElementException e) {
			logger.error("NoSuchElementException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (StaleElementReferenceException e) {
			logger.error("StaleElementReferenceException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (ElementNotVisibleException e) {
			logger.error("ElementNotVisibleException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (ElementNotFoundException e) {
			logger.error("ElementNotFoundException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (UnhandledAlertException e) {
			logger.error("UnhandledAlertException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (WebDriverException e) {
			logger.error("WebDriverException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " ", e);
		}
	}

	/**
	 * 
	 * @param driver
	 * @param seed
	 */
	private boolean validateYahooVersion(WebDriver driver, Seed seed) {
		try {
			Thread.sleep(5000);
			if (driver.findElements(By.className("uh-srch-btn")).size() > 0) {
				logger.info("----------   Old yahoo version   ----------");
			} else if (driver.findElements(By.id("UHSearchProperty")).size() > 0) {
				logger.info("LOGGED IN!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				checkMultipleAccountsPanel(driver);
				return true;
			} else if (driver.findElements(By.id("mail-search-btn")).size() > 0) {
				logger.info("##########   New yahoo version   ##########");
			} else if (driver.findElements(By.id("comm-channel-module")).size() > 0) {
				logger.info("LOGGED IN and phone validation found!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				driver.get("http://mail.yahoo.com");
				return true;
			} else if (driver.findElements(By.id("skipbtn")).size() > 0) {
				logger.info("LOGGED IN and Dont get locked out of your account, clicking skip button!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				driver.findElement(By.id("skipbtn")).click();
				return true;
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
					getScreenShot(driver, randInt(1, 100) + "newVersion" + UUID.randomUUID());
				}
			} else{
				invalidMessage = "THERE IS A NEW YAHOO VERSION IN TOWN";
				 logger.info("==========   "+ invalidMessage +"   ==========");
				 getScreenShot(driver, randInt(1, 100) + "newVersion" + UUID.randomUUID());
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
		return false;
	}

	private void checkMultipleAccountsPanel(WebDriver driver) {
		if (driver.findElements(By.id("imapInOnboardDlg")).size() > 0) {
			logger.info("Multiple accounts Panel Found");
			if (driver.findElement(By.xpath("//div[@id='imapInOnboardDlg']/a")).isDisplayed()) {
				driver.findElement(By.xpath("//div[@id='imapInOnboardDlg']/a")).click();
			}
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

	private String getRandomUA() {
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
		return uas[randInt(0, 8)];

	}
	
	public static int randInt(int min, int max) {
		Random rand = new Random();
		// nextInt is normally exclusive of the top value, so add 1 to make it
		// inclusive
		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}
	
	private void getScreenShot(WebDriver driver, String name) {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		// Now you can do whatever you need to do with it, for example copy
		// somewhere
		try {
			FileUtils.copyFile(scrFile, new File("/var/www/errors/" + name + ".jpg"));
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}

	}
	
	private void writeSeedToFile(Seed seed, boolean valid) {
		String s = null;
		try {
			if(valid){
				s = "\n" + seed.getFullSeed();
				Files.write(Paths.get(Constant.ROUTE + "wolf_valid_seeds.txt"), s.getBytes(), StandardOpenOption.APPEND);
			}else{
				s = "\n" + seed.getFullSeed() + " error: " + invalidMessage;
				Files.write(Paths.get(Constant.ROUTE + "wolf_invalid_seeds.txt"), s.getBytes(), StandardOpenOption.APPEND);
			}
		}catch (IOException e) {
		    //exception handling left as an exercise for the reader
		}
	}
}
