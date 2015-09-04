/**
 * 
 */
package com.blackwolves.up;

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
public class Up {
	
	private static final Logger logger = LogManager.getLogger(Up.class.getName());
	
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
		
		logger.info("Creating the driver");
		WebDriver driver = new HtmlUnitDriver();
		
		logger.info("Getting to the url: " + upUrl);
        driver.get(upUrl);
        
        logger.info("Introducing username: " + upUser);
        driver.findElement(By.name("username")).sendKeys(upUser);
        
        logger.info("Introducing password: xxxxx");
        driver.findElement(By.name("password")).sendKeys(upPassword);
        
        logger.info("Clicking login button");
        driver.findElement(By.className("btn-primary")).click();
        
        logger.info("Getting to the job url: " + jobUrl + jobId);
        driver.get(jobUrl + jobId);
        
        if(checkIndex(driver)){
        	
        	logger.info("Clearing the feed input");
            driver.findElement(By.name("feed")).clear();
            
            logger.info("Entering feed value: " + feed);
            driver.findElement(By.name("feed")).sendKeys(feed);
	    
        	logger.info("Clicking the Send button");
	        driver.findElement(By.name("submit")).click();
	
	        try {
	        	logger.info("Waiting " + upWaitTime + " minutes");
				TimeUnit.MINUTES.sleep(upWaitTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	        
	        logger.info("Clicking the Delete Queue button");
	        driver.get(deleteUrl  + jobId);
        }else{
        	logger.info("Sending email");
        }
        
        logger.info("Exiting the driver");
        driver.quit();
	}
	
	/**
	 * Checks the index of the list to see if we have more contacts left to send
	 * @param driver 
	 * @return
	 */
	private boolean checkIndex(WebDriver driver) {
		logger.info("Getting the index value");
		String[] emText = driver.findElement(By.tagName("em")).getText().split(" ");
		logger.info("Replacing");
        String index = emText[0].replaceAll("\\(","");
        logger.info("Creating the variable");
        Long indexValue = Long.valueOf(index);
        logger.info("Comparing values");
		return checkRemainingFeedAmount(indexValue);
	}

	/**
	 * @param indexValue
	 * @return
	 */
	private boolean checkRemainingFeedAmount(Long indexValue) {
		Long feedValue = Long.valueOf(feed);
		logger.info("Comparing indexValue > jobFinalContacts");
		if(indexValue >= jobFinalContacts){
			return false;
		}
		logger.info("Comparing (indexValue + feedValue) > jobFinalContacts");
		if((indexValue + feedValue) >= jobFinalContacts){
			Long newFeedValue = jobFinalContacts - indexValue;
			feed = newFeedValue.toString();
			return true;
		}
		logger.info("Comparing nothing :) ");
		return true;
	}

}
