package com.blackwolves.selenium.seeder;

import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Random;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * @author daniel.grane
 *
 */
public class SuscriberRunnable implements Runnable {

	private static final Logger logger = LogManager.getLogger(SuscriberRunnable.class.getName());
	

	private final List<String[]> seeds;
	private final List<String[]> ips;
	private final Random ipRandomizer;


	


	public SuscriberRunnable(List<String[]> seeds, List<String[]> ips, Random ipRandomizer) {
		super();
		this.seeds = seeds;
		this.ips = ips;
		this.ipRandomizer = ipRandomizer;
	}


	@Override
	public void run() {
		WebDriver driver = null;
		System.setProperty("webdriver.chrome.driver", "/Users/danigrane/Downloads/Software/chromedriver");

	   
		for (int i = 0; i < seeds.size(); i++) {

			try{

				logger.info("Counter i is now: " + i);
				//				String seed = seeds.get(seedRandomizer.nextInt(seeds.size()));
				String[] seed = seeds.get(i);

				String[] ip = ips.get(ipRandomizer.nextInt(ips.size()));

				logger.info("Creating the proxy capability");

				DesiredCapabilities capability = SeederRunnable.addProxyCapabilities(ip[0]);

				logger.info("Creating the driver");
				//				WebDriver driver = new HtmlUnitDriver(capability);
				driver = new ChromeDriver();
				suscribeToSkimm(seed, driver);
				suscribeToMatterMark(seed, driver);
				suscribeFashionMagazine(seed, driver);
				suscribeToDigg(seed, driver);
			    suscribeToTheWeek(seed, driver);
			   
			    driver.close();
				
			}

			catch(NoSuchElementException nse){
				logger.error(nse.getMessage(), nse);
				 driver.close();
				continue;
			}catch(Exception e){
				logger.error(e.getMessage(), e);
				 driver.close();
				continue;
			}	
		}

	}

    //works :)
	private void suscribeFashionMagazine(String[] seed, WebDriver driver) throws InterruptedException {
		driver.get("http://www.fashionmagazine.com/");
		driver.findElement(By.name("input_1")).clear();;
		driver.findElement(By.name("input_1")).sendKeys(seed[0]);
		driver.findElement(By.name("input_1")).submit();
		if(driver.getPageSource().contains("Thanks"))
			{
				logger.info("Fashion Magazine Suscription succesful.");
			}



	}

	//Works :)
	private void suscribeToMatterMark(String[] seed, WebDriver driver) throws InterruptedException {
		
		driver.get("http://mattermark.com/app/Newsletter");
		driver.findElement(By.id("free_email")).sendKeys(seed[0]);
		driver.findElement(By.id("free_email")).submit();
        Thread.sleep(1000);

	}
	
	//Works! :)
	private void suscribeToSkimm(String[] seed, WebDriver driver) throws InterruptedException {
		driver.get("http://www.theskimm.com/");
		writeToFile("theskimm.html",driver.getPageSource());
		driver.findElement(By.name("email")).clear();;
		driver.findElement(By.name("email")).sendKeys(seed[0]);
		driver.findElement(By.name("email")).submit();
		Thread.sleep(1000);
	}
	
	
	
	private void suscribeToDigg(String[] seed, WebDriver driver) {
		
		
		driver.get("http://digg.com/");

		driver.findElement(By.name("email")).sendKeys(seed[0]);
		
		driver.findElement(By.id("daily-digg-email-submit-btn")).click();
		if(driver.getPageSource().contains("Thanks for subscribing!"))
		{
			logger.info("Suscribed "+ seed[0]+ " to Digg!");
		}
		
	}
	
	
	private void  suscribeToTheWeek(String[] seed, WebDriver driver) throws InterruptedException
	{
		driver.get("http://theweek.com/");
		driver.findElement(By.name("email")).clear();;
		driver.findElement(By.name("email")).sendKeys(seed[0]);
		driver.findElement(By.name("email")).submit();
		Thread.sleep(1000);
		
	}
	
	 public static void writeToFile(String fileName,String fileContent) {
	        try {
	            FileWriter writer = new FileWriter("/Users/danigrane/Documents/workspace/"+fileName, true);
	            writer.write(fileContent);
	           
	            writer.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	 
	    }
}
