/**
 * 
 */
package com.blackwolves.selenium.seeder;

import java.util.List;
import java.util.Random;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.Proxy.ProxyType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * @author gaston.dapice
 *
 */
public class SeederRunnable implements Runnable {
	
	private static final Logger logger = LogManager.getLogger(SeederRunnable.class.getName());
	
	private final List<String[]> seeds;
	private final List<String[]> ips;
	private final Random ipRandomizer;

	public SeederRunnable(List<String[]> partition, List<String[]> ips, Random ipRandomizer) {
		this.seeds = partition;
		this.ips = ips;
		this.ipRandomizer = ipRandomizer;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		String yahooUrl = "https://login.yahoo.com/?.src=ym&.intl=us&.lang=en-US&.done=https%3a//mail.yahoo.com";
//		for (int i = 0; i < 10; i++) {
		for (int i = 0; i < seeds.size(); i++) {
			
			try{
//				logger.debug("Waiting 5 seconds");
//				TimeUnit.SECONDS.sleep(5);
				
				logger.debug("Counter i is now: " + i);
//				String seed = seeds.get(seedRandomizer.nextInt(seeds.size()));
				String[] seed = seeds.get(i);
				
				String[] ip = ips.get(ipRandomizer.nextInt(ips.size()));
				
				logger.debug("Creating the proxy capability");
				
				DesiredCapabilities capability = addProxyCapabilities(ip[0]);
				
				logger.debug("Creating the driver");
//				WebDriver driver = new HtmlUnitDriver(capability);
				WebDriver driver = new HtmlUnitDriver();
				
				logger.debug("Getting to the url: " + yahooUrl);
		        driver.get(yahooUrl);
		        
		        logger.debug("Introducing username: " + seed[0]);
		        driver.findElement(By.id("login-username")).clear();
		        driver.findElement(By.id("login-username")).sendKeys(seed[0]);
		        
		        logger.debug("Introducing password: " + seed[1]);
		        driver.findElement(By.id("login-passwd")).clear();
		        driver.findElement(By.id("login-passwd")).sendKeys(seed[1]);
		        
		        logger.debug("Clicking login button");
		        driver.findElement(By.id("login-signin")).click();
		        
//		        logger.debug(driver.getPageSource());
		        
		        if(driver.findElements(By.id("bulk")).size() > 0){
		        
			        logger.debug("Getting the Bulk Url");
			        driver.get(driver.findElement(By.id("bulk")).findElement(By.tagName("a")).getAttribute("href"));
			        
			        if(driver.findElements(By.id("select_all")).size() > 0){
			        	
			        	logger.debug("Clicking Select all checkbox");
				        driver.findElement(By.id("select_all")).click();
				        
				        if(driver.findElements(By.id("top_ham")).size() > 0){
				        	
					        logger.debug("Clicking Not spam button");
					        driver.findElement(By.id("top_ham")).click();
				        }
			        }
		        }
		        
//		        logger.debug(driver.getPageSource());
		        
		        logger.debug("Finished!!");
		        
			}catch(NoSuchElementException nse){
				logger.error(nse.getMessage(), nse);
				continue;
			}catch(Exception e){
				logger.error(e.getMessage(), e);
				continue;
			}
			
		}

	}
	
	public static DesiredCapabilities addProxyCapabilities(String ip) {
		
		logger.debug("Using ip: " + ip);
		
		System.setProperty("http.proxyHost", ip);
        System.setProperty("http.proxyPort", "80");
        System.setProperty("https.proxyHost", ip);
        System.setProperty("https.proxyPort", "443");
		
		String httpProxy = ip + ":8080";
        String sslProxy = ip + ":8080";
        String ftpProxy = ip + ":8080";
 
        DesiredCapabilities capability = new DesiredCapabilities();
 
        Proxy proxy = new Proxy();
        proxy.setProxyType(ProxyType.MANUAL);
        proxy.setHttpProxy(httpProxy);
        proxy.setSslProxy(sslProxy);
        proxy.setFtpProxy(ftpProxy);
 
        capability.setCapability(CapabilityType.PROXY, proxy);
        capability.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        
        return capability;
    }

}
