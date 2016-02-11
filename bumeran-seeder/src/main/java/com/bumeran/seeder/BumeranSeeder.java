package com.bumeran.seeder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
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

import com.bumeran.behavior.AverageHuman;
import com.bumeran.behavior.DumbHuman;
import com.bumeran.behavior.FastHuman;
import com.bumeran.behavior.Human;
import com.bumeran.util.Constant;
import com.gargoylesoftware.htmlunit.ElementNotFoundException;

/**
 * 
 * @author gastondapice
 *
 */
public class BumeranSeeder implements Runnable {

	private Logger logger;

	private Human human;

	public BumeranSeeder() {
	}

	/**
	 * 
	 * @param logger
	 * @param values
	 */
	public BumeranSeeder(Logger logger) {
		this.logger = logger;
	}

	/**
	 * 
	 */
	public void run() {
		try {
			logger.info("Entering first do while");
	
			WebDriver driver = createWebDriver();
			logger.info("Firefox Created");
	
			human = generateRandomHumanUser();
			
			logger.info("Processing post");
			bumeranLogin(driver);
			
			logger.info("Getting to la busqueda url: " + Constant.BUSQUEDA_URL);
			driver.get(Constant.BUSQUEDA_URL);
		
			Thread.sleep(randInt(3000, 4000));
			driver.findElement(By.id("36026580")).click();
			Thread.sleep(randInt(3000, 4000));
			while(true){
				getEmails(driver);
				Thread.sleep(5000);
			}
		} catch (InterruptedException e) {
			logger.error("InterruptedException");
		}
		
		
//		logger.info("Finished!!");
//		driver.close();
//		driver.quit();
	}


	/**
	 * 
	 * @param driver
	 */
	private void getEmails(WebDriver driver) {
		WebElement resumenDatosPersonales = driver.findElement(By.className("resumenDatosPersonales"));
		resumenDatosPersonales.findElement(By.partialLinkText("@"));
		
		String email = resumenDatosPersonales.findElement(By.partialLinkText("@")).getText();
		logger.info("Email: " + email + "Url: " + driver.getCurrentUrl());
		if(email!=null&&!email.isEmpty()){
			writeSeedToFile(email.toLowerCase());
		}
		
		WebElement proximoPostulante = driver.findElement(By.className("postulanteProximo"));
		proximoPostulante.click();
	}
	
	/**
	 * 
	 * @param seed
	 * @param outputFileName
	 */
	private void writeSeedToFile(String email) {
		PrintWriter pw = null;
		try {
			List<String> usedSeeds = readSeedsFromFile();
			pw = new PrintWriter(new FileWriter(Constant.ROUTE + Constant.OUTPUT_FILE));
			for (String usedSeed : usedSeeds) {
				pw.write(usedSeed);
				pw.write("\n");
			}
			pw.write(email);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally{
			if(pw!=null){
				pw.close();
			}
		}
	}
	
	/**
	 * 
	 * @return
	 */
	private List<String> readSeedsFromFile() {
		List<String> list = null;
		try {
			File file = new File(Constant.ROUTE + Constant.OUTPUT_FILE);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			list = new ArrayList<String>();
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				list.add(line);
			}
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return list;
	}

	/**
	 * 
	 * @param bumeranUrl
	 * @param seed
	 * @param driver
	 */
	private void bumeranLogin(WebDriver driver) {
		logger.info("Trying to login in....");
		try {

			Thread.sleep(randInt(2000, 3000));
			logger.info("Getting to the url: " + Constant.BUMERAN_URL);
			driver.get(Constant.BUMERAN_URL);

			logger.info("Introducing username: " + Constant.USERNAME);
			WebElement accountInput = driver.findElement(By.id("input-username"));
			human.type(accountInput, Constant.USERNAME);

			logger.info("Introducing password: " + Constant.PASSWORD);
			WebElement passwordInput = driver.findElement(By.id("input-password"));
			human.type(passwordInput, Constant.PASSWORD);

			logger.info("Clicking login button");
			if (driver.findElements(By.className("primary")).size() > 0) {
				driver.findElement(By.className("primary")).click();
			} else {
				logger.info("Already logged in..Moving forward!");
			}
		} catch (NoSuchElementException e) {
			logger.error("NoSuchelementException");
		} catch (StaleElementReferenceException e) {
			logger.error("StaleElementReferenceException");
		} catch (ElementNotVisibleException e) {
			logger.error("ElementNotVisibleException");
		} catch (ElementNotFoundException e) {
			logger.error("ElementNotFoundException");
		} catch (InterruptedException e) {
			logger.error("InterruptedException");
		}
	}
	
	/**
	 * 
	 * @return
	 */
	private Human generateRandomHumanUser() {
		logger.info("Random Human generation started");
		int number = randInt(0, 10);
		if (number <= 3) {
			return new DumbHuman();
		} else if (number >= 4 && number <= 8) {
			return new AverageHuman();
		}
		return new FastHuman();
	}
	
	/**
	 * 
	 * @param min
	 * @param max
	 * @return
	 */
	public static int randInt(int min, int max) {
		Random rand = new Random();
		// nextInt is normally exclusive of the top value, so add 1 to make it inclusive
		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}
	
	/**
	 * Throw dices to get random results
	 * @return boolean
	 */
	public static boolean throwDice() {
		int dice = randInt(1, 6);
		return dice == 3;
	}

	/**
	 * 
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
	 * 
	 * @param driver
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
	
	/**
	 * 
	 * @param driver
	 * @param name
	 */
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

	/**
	 * 
	 * @param route
	 * @param file
	 * @return
	 */
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

	/**
	 * 
	 */
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
	 * 
	 * @return
	 */
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
		return uas[randInt(0, 8)];

	}
}