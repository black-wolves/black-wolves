/**
 * 
 */
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
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;

/**
 * @author gaston.dapice
 *
 */
public class SeederRunnable implements Runnable {
	
	private static final double PERCENTAGE = 0.3;

	private static final Logger logger = LogManager.getLogger(SeederRunnable.class.getName());
	
	private final List<String[]> seeds;
	private final List<String[]> ips;
	private final Random ipRandomizer;
	private final String seed;
	private final String ip;

	public SeederRunnable(List<String[]> partition, List<String[]> ips, Random ipRandomizer, String ip, String seed) {
		this.seeds = partition;
		this.ips = ips;
		this.ipRandomizer = ipRandomizer;
		this.ip = ip;
		this.seed = seed;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
//		String[] seed = seeds.get(randInt(0, seeds.size()-1));
//		logger.info("Current Seed is : "+seed[0]);
		String[] seed = this.seed.split(",");
		String yahooUrl = "https://login.yahoo.com/?.src=ym&.intl=us&.lang=en-US&.done=https%3a//mail.yahoo.com";
			try{
				logger.info("Creating new driver");
//				WebDriver driver = new HtmlUnitDriver(capability);
//				WebDriver driver = new HtmlUnitDriver(true);
				WebDriver driver = new HtmlUnitDriver();
//				WebDriver driver = new FirefoxDriver();
//				WebDriver driver = new ChromeDriver();
//				WebDriver driver = new InternetExplorerDriver();
				
				yahooLogin(yahooUrl, seed, driver);
				
				validateYahooVersion(driver, seed);
		        
		        processBulk(driver, seed);
		        
		        processInbox(driver, seed);
		        
		        logger.info("Finished!!");

//		        logger.info(driver.getPageSource());
		        
//		        clickNotSpamForAllMessages(driver);
		        
			}catch(NoSuchElementException nse){
				logger.error(nse.getMessage(), nse);
			}catch(Exception e){
				logger.error(e.getMessage(), e);
			}
		
	}

	/**
	 * @param yahooUrl
	 * @param seed
	 * @param driver
	 */
	private void yahooLogin(String yahooUrl, String[] seed, WebDriver driver) {
		logger.info("Getting to the url: " + yahooUrl);
		driver.get(yahooUrl);
		
		logger.info("Introducing username: " + seed[0]);
		driver.findElement(By.id("login-username")).clear();
		driver.findElement(By.id("login-username")).sendKeys(seed[0]);
		
		logger.info("Introducing password: " + seed[1]);
		driver.findElement(By.id("login-passwd")).clear();
		driver.findElement(By.id("login-passwd")).sendKeys(seed[1]);
		
		logger.info("Clicking login button");
		driver.findElement(By.id("login-signin")).click();
	}
	
	/**
	 * 
	 * @param driver
	 * @param seed 
	 */
	private void validateYahooVersion(WebDriver driver, String[] seed) {
		if(driver.findElements(By.className("uh-srch-btn")).size() > 0){
			logger.info("**********   Old yahoo version   **********");
		}else if(driver.findElements(By.id("UHSearchProperty")).size() > 0){
			logger.info("**********   New yahoo 2 version   **********");
			SuscriberRunnable.writeToFile(seed[0] +"new_yahoo_2_version.html", driver.getPageSource());
		}else if(driver.findElements(By.id("mail-search-btn")).size() > 0){
			logger.info("**********   New yahoo version   **********");
			SuscriberRunnable.writeToFile(seed[0] + "new_yahoo_version.html", driver.getPageSource());
		}else{
			logger.info("**********   There is a new yahoo version in town  **********");
			SuscriberRunnable.writeToFile(seed[0] + "new_version_in_town.html", driver.getPageSource());
		}
		
	}
	
	/**
	 * @param driver
	 * @param seed 
	 */
	private void processBulk(WebDriver driver, String[] seed) {
		if(driver.findElements(By.id("bulk")).size() > 0){
		
		    logger.info("Getting the Bulk Url");
		    driver.get(driver.findElement(By.id("bulk")).findElement(By.tagName("a")).getAttribute("href"));
		    
		    if(driver.findElements(By.className("mlink")).size() > 0){
		    	
		    	logger.info("mlink found");
	    		List<WebElement> spamMsgs = driver.findElements(By.className("mlink"));
		    	
		    	int percentage = (int) (spamMsgs.size() * PERCENTAGE);
		    	for(int j = 0 ; j < percentage; j++){
		    		if(driver.findElements(By.className("mlink")).size() > 0){
		    			spamMsgs = driver.findElements(By.className("mlink"));
		    			
		    			logger.info("Obtaining a random message position so it can be open");
			    		int randomPosition = obtainRandomMsgsPosition(spamMsgs);
		        		String href = spamMsgs.get(randomPosition).getAttribute("href");
						
		        		logger.info("Opening this spam message: " + href);
						driver.get(href);
						
						clickShowImages(driver, "spamwarning");
						
						Select notSpam = new Select(driver.findElement(By.name("top_action_select")));
						notSpam.selectByValue("msg.ham");
						logger.info("Clicking the not spam option");
						driver.findElement(By.name("self_action_msg_topaction")).click();
		    		}else{
				    	logger.info("**********   No mlink found or no messages available   **********");
				    }
		    	}
		    }else{
		    	logger.info("**********   No mlink found or no messages available   **********");
		    }
		}else{
			logger.info("**********   No bulk Url found   **********");
			SuscriberRunnable.writeToFile(seed[0] + "now_bulk_url.html", driver.getPageSource());
		}
	}

	/**
	 * @param driver
	 * @param seed 
	 * @throws InterruptedException
	 */
	private void processInbox(WebDriver driver, String[] seed) throws InterruptedException {
		if(driver.findElements(By.id("inbox")).size() > 0){
			
			logger.info("Getting the Inbox Url");
			driver.get(driver.findElement(By.id("inbox")).findElement(By.tagName("a")).getAttribute("href"));
			
			if(driver.findElements(By.className("mlink")).size() > 0){
		    	
				logger.info("mlink found");
		    	List<WebElement> inboxMsgs = driver.findElements(By.className("mlink"));
		    	
		    	int percentage = (int) (inboxMsgs.size() * PERCENTAGE);
		    	for(int j = 0 ; j < percentage; j++){
		    		if(driver.findElements(By.className("mlink")).size() >  0){
		    			inboxMsgs = driver.findElements(By.className("mlink"));
		    			logger.info("Obtaining a random message position so it can be open");
			    		int randomPosition = obtainRandomMsgsPosition(inboxMsgs);
			        	String href = inboxMsgs.get(randomPosition).getAttribute("href");
			        	logger.info("Opening this inbox message: " + href);
						driver.get(href);
						clickShowImages(driver, "show-text");
						
//						scrollDownAndUp(driver);
						
						clickRandomLink(driver);
						
						logger.info("Going back to inbox");
						driver.get(driver.findElement(By.id("inbox")).findElement(By.tagName("a")).getAttribute("href"));
		    		}else{
				    	logger.info("**********   No mlink found or no messages available   **********");
				    }
		    	}
		    }else{
		    	logger.info("**********   No mlink found or no messages available   **********");
		    }
		}else{
			logger.info("**********   No inbox Url found   **********");
			SuscriberRunnable.writeToFile(seed[0] + "no_inbox_url.html", driver.getPageSource());
		}
	}
	
	/**
	 * @param spamMsgs
	 * @return
	 */
	private int obtainRandomMsgsPosition(List<WebElement> spamMsgs) {
		Random randomNo = new Random();
		int randomPosition = randomNo.nextInt(spamMsgs.size()>=50?50:spamMsgs.size());
		return randomPosition;
	}

	/**
	 * @param driver
	 * @throws InterruptedException
	 */
	private void clickRandomLink(WebDriver driver) throws InterruptedException {
		logger.info("Getting the content of the message");
		WebElement div = driver.findElement(By.className("mailContent"));
		
		logger.info("Looking for links inside the message");
		if(div.findElements(By.tagName("a")).size()!=0){
			logger.info("Links found");
			List<WebElement> linksToGo = div.findElements(By.tagName("a"));
			Random rand = new Random();
			int randomLinkNo = rand.nextInt(linksToGo.size());
			String aUrl = linksToGo.get(randomLinkNo).getAttribute("href");
			if(aUrl!=null){
				if(aUrl.contains("unsub") || aUrl.contains("yahoo")){
					logger.info("It is an Unsubscribe link!! - we are not clicking it");
					logger.info(aUrl);
				}else{
					openInNewWindow(driver, linksToGo.get(randomLinkNo));
				}
			}
		}else{
			logger.info("**********   No links found or none available  **********");
		}
	}

	/**
	 * 
	 * @param driver
	 * @param className
	 */
	private void clickShowImages(WebDriver driver, String className) {
		if(validateInboxShowImagesButton(driver, className)){
			logger.info("Clicking the show images button");
			driver.findElement(By.className(className)).findElement(By.tagName("a")).click();
		}else{
			logger.info("**********   No show images button found or there is none   **********");
		}
		
	}

	/**
	 * 
	 * @param driver
	 * @param className
	 * @return
	 */
	private boolean validateInboxShowImagesButton(WebDriver driver, String className) {
		return driver.findElements(By.className(className)).size() > 0;
	}

	/**
	 * 
	 * @param driver
	 * @param a
	 * @throws InterruptedException
	 */
	private void openInNewWindow(WebDriver driver, WebElement a) throws InterruptedException {
		
		logger.info("Cicking this link: " + a.getAttribute("href"));
		Actions newTab = new Actions(driver);
		newTab.keyDown(Keys.SHIFT).click(a).keyUp(Keys.SHIFT).build().perform();
		Thread.sleep(5000);
		 
		//handle windows change
		String base = driver.getWindowHandle();
		Set<String> set = driver.getWindowHandles();
		 
		set.remove(base);
		assert set.size() == 1;
		driver.switchTo().window((String) set.toArray()[0]);
		 
		//close the window
		driver.close();
		driver.switchTo().window(base);
		 
		// handle windows change and switch back to the main window
		Thread.sleep(2500);
		for (String winHandle : driver.getWindowHandles()) {
			driver.switchTo().window(winHandle);
		}
	}
	
	private static int randInt(int min, int max) {

	    // Usually this can be a field rather than a method variable
	    Random rand = new Random();

	    // nextInt is normally exclusive of the top value,
	    // so add 1 to make it inclusive
	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
	
	/**
	 * @param driver
	 */
	private void scrollDownAndUp(WebDriver driver) {
//		scroll down
		JavascriptExecutor jse = (JavascriptExecutor)driver;
//		jse.executeScript("window.scrollBy(0,250)", "");
//		OR
		jse.executeScript("scroll(0, 250);");
//		scroll up
//		jse.executeScript("window.scrollBy(0,-250)", "");
//		OR
		jse.executeScript("scroll(0, -250);");
	}
	
	/**
	 * @param driver
	 */
	private void clickNotSpamForAllMessages(WebDriver driver) {
		//		        Select all spams and move them to inbox
		if(driver.findElements(By.id("select_all")).size() > 0){
			
			logger.info("Clicking Select all checkbox");
		    driver.findElement(By.id("select_all")).click();
		    
		    if(driver.findElements(By.id("top_ham")).size() > 0){
		    	
		        logger.info("Clicking Not spam button");
		        driver.findElement(By.id("top_ham")).click();
		    }
		}
	}
	
	/**
	 * 
	 * @param ip
	 * @return
	 */
	public static DesiredCapabilities addProxyCapabilities(String ip) {
		
		logger.info("Using ip: " + ip);
		
		System.setProperty("http.proxyHost", ip);
        System.setProperty("http.proxyPort", "80");
        System.setProperty("https.proxyHost", ip);
        System.setProperty("https.proxyPort", "443");
		
		String httpProxy = ip + ":8080";
        String sslProxy = ip + ":8080";
        String ftpProxy = ip + ":8080";
 
        DesiredCapabilities capability = new DesiredCapabilities();
 
        Proxy proxy = new Proxy();
//        proxy.setProxyType(ProxyType.MANUAL);
        proxy.setHttpProxy(httpProxy);
        proxy.setSslProxy(sslProxy);
        proxy.setFtpProxy(ftpProxy);
 
        capability.setCapability(CapabilityType.PROXY, proxy);
        capability.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        
        return capability;
    }

}
