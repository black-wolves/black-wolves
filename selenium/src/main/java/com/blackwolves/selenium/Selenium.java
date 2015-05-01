/**
 * 
 */
package com.blackwolves.selenium;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author gaston.dapice
 *
 */
@Component
public class Selenium {
	
	private static final Logger logger = LogManager.getLogger(Selenium.class.getName());
	
	@Value("${up.url}")
	private String upUrl;
	
	@Value("${up.user}")
	private String upUser;
	
	@Value("${up.password}")
	private String upPassword;
	
	@Value("${job.url}")
	private String jobUrl;
	
	@Value("${feed}")
	private String feed;
	
	@Value("${up.wait.time}")
	private int upWaitTime;
	
	@Value("${delete.url}")
	private String deleteUrl;
	
	@Value("${job.id}")
	private String jobId;
	
	@Value("${job.final.contacts}")
	private Long jobFinalContacts;

	/**
	 * Sends the campaign for the configured values
	 */
	public void sendCampaign(){
		
		logger.debug("Creating the driver");
		WebDriver driver = new HtmlUnitDriver();
		
		logger.debug("Getting to the url: " + upUrl);
        driver.get(upUrl);
        
        logger.debug("Introducing username: " + upUser);
        driver.findElement(By.name("username")).sendKeys(upUser);
        
        logger.debug("Introducing password: xxxxx");
        driver.findElement(By.name("password")).sendKeys(upPassword);
        
        logger.debug("Clicking login button");
        driver.findElement(By.className("btn-primary")).click();
        
        logger.debug("Getting to the job url: " + jobUrl + jobId);
        driver.get(jobUrl + jobId);
        
        if(checkIndex(driver)){
        	
        	logger.debug("Clearing the feed input");
            driver.findElement(By.name("feed")).clear();
            
            logger.debug("Entering feed value: " + feed);
            driver.findElement(By.name("feed")).sendKeys(feed);
	    
        	logger.debug("Clicking the Send button");
	        driver.findElement(By.name("submit")).click();
	
	        try {
	        	logger.debug("Waiting " + upWaitTime + " minutes");
				TimeUnit.MINUTES.sleep(upWaitTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	        
	        logger.debug("Clicking the Delete Queue button");
	        driver.get(deleteUrl  + jobId);
        }else{
        	logger.debug("Sending email");
        }
        
        logger.debug("Exiting the driver");
        driver.quit();
	}
	
	@Value("${interspire.url}")
	private String interspireUrl;
	
	@Value("${interspire.user}")
	private String interspireUser;
	
	@Value("${interspire.password}")
	private String interspirePassword;
	
	@Value("${interspire.fbl.url}")
	private String interspireFblUrl;
	
	@Value("${interspire.wait.time}")
	private int intesrpisreWaitTime;
	
	/**
	 * Runs the FBL
	 */
	public void runFbl(){
		
		logger.debug("Creating the driver");
		WebDriver driver = new HtmlUnitDriver();
		
		logger.debug("Getting to the url: " + interspireUrl);
        driver.get(interspireUrl);
        
        logger.debug("Introducing username: " + interspireUser);
        driver.findElement(By.name("ss_username")).sendKeys(interspireUser);
        
        logger.debug("Introducing password: xxxxx");
        driver.findElement(By.name("ss_password")).sendKeys(interspirePassword);
        
        logger.debug("Clicking login button");
        driver.findElement(By.name("SubmitButton")).click();
        
        try {
        	logger.debug("Waiting " + intesrpisreWaitTime + " minutes");
			TimeUnit.MINUTES.sleep(intesrpisreWaitTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        
        logger.debug("Clicking the FBL button");
        driver.get(interspireFblUrl);
	
        try {
        	logger.debug("Waiting " + intesrpisreWaitTime + " minutes");
			TimeUnit.MINUTES.sleep(intesrpisreWaitTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        
        logger.debug(driver.findElement(By.className("FlashSuccess")).getText());
        
        logger.debug("Exiting the driver");
        driver.quit();
	}

	/**
	 * Checks the index of the list to see if we have more contacts left to send
	 * @param driver 
	 * @return
	 */
	private boolean checkIndex(WebDriver driver) {
		logger.debug("Getting the index value");
		String[] emText = driver.findElement(By.tagName("em")).getText().split(" ");
		logger.debug("Replacing");
        String index = emText[0].replaceAll("\\(","");
        logger.debug("Creating the variable");
        Long indexValue = Long.valueOf(index);
        logger.debug("Comparing values");
		return checkRemainingFeedAmount(indexValue);
	}

	/**
	 * @param indexValue
	 * @return
	 */
	private boolean checkRemainingFeedAmount(Long indexValue) {
		Long feedValue = Long.valueOf(feed);
		logger.debug("Comparing indexValue > jobFinalContacts");
		if(indexValue >= jobFinalContacts){
			return false;
		}
		logger.debug("Comparing (indexValue + feedValue) > jobFinalContacts");
		if((indexValue + feedValue) >= jobFinalContacts){
			Long newFeedValue = jobFinalContacts - indexValue;
			feed = newFeedValue.toString();
			return true;
		}
		logger.debug("Comparing nothing :) ");
		return true;
	}
	
}
