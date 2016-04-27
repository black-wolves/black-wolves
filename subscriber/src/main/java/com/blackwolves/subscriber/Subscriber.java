package com.blackwolves.subscriber;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;

import com.blackwolves.subscriber.util.Constant;
import com.blackwolves.subscriber.util.JDBC;
import com.gargoylesoftware.htmlunit.ElementNotFoundException;

/**
 * @author gaston.dapice
 *
 */
public class Subscriber implements Runnable {
	
	private Logger logger;

	private Seed seed;

	public Subscriber(Seed seed, Logger logger) {
		this.seed = seed;
		this.logger = logger;
	}

	@Override
	public void run() {
		logger.info("Creating the driver");
		WebDriver driver = createWebDriver();
		try {
			
			subscribeToNyTimes(driver);
			
			if (Math.random() <= 1 && !seed.getSubscription().contains(Constant.Golfsmith.siteName)) {
				subscribeToGolfSmith(seed, Constant.Golfsmith.siteUrl, Constant.Golfsmith.siteName, driver);
			}
			
			if (Math.random() <= 1 && !seed.getSubscription().contains(Constant.TheHerald.siteName)) {
				subscribeToTheHerald(seed, Constant.TheHerald.siteUrl, Constant.TheHerald.siteName, driver);
			}
			
			if (Math.random() <= 1 && !seed.getSubscription().contains(Constant.NBCNews.siteName)) {
				subscribeToNBCNews(seed, Constant.NBCNews.siteUrl, Constant.NBCNews.siteName, driver);
			}
			
			if (Math.random() <= 1 && !seed.getSubscription().contains(Constant.DailyNews.siteName)) {
				subscribeToDailyNews(seed, Constant.DailyNews.siteUrl, Constant.DailyNews.siteName, driver);
			}
			
			if (Math.random() <= 1 && !seed.getSubscription().contains(Constant.Forbes.siteName)) {
				subscribeToForbes(seed, Constant.Forbes.siteUrl, Constant.Forbes.siteName, driver);
			}
			
			if (Math.random() <= 1 && !seed.getSubscription().contains(Constant.Mattermark.siteName)) {
				subscribeToMatterMark(seed, Constant.Mattermark.siteUrl, Constant.Mattermark.siteName, driver);
			}
			
			if (Math.random() <= 1 && !seed.getSubscription().contains(Constant.FashionMagazine.siteName)) {
				subscribeFashionMagazine(seed, Constant.FashionMagazine.siteUrl, Constant.FashionMagazine.siteName, driver);
			}
			
			if (Math.random() <= 1 && !seed.getSubscription().contains(Constant.TheFetch.siteName)) {
				subscribeToFetch(seed, Constant.TheFetch.siteUrl, Constant.TheFetch.siteName, driver);
			}
			
			if (Math.random() <= 1 && !seed.getSubscription().contains(Constant.TheWashingtonPost.siteName)) {
				subscribeToTheWashingtonPost(seed, Constant.TheWashingtonPost.siteUrl, Constant.TheWashingtonPost.siteName, driver);
			}
			
			
			
			
			
			
			
//			if (Math.random() < 0.5) {
//				subscribeToHoustonCron(seed, driver);
//			}
//			if (Math.random() < 0.5) {
//				subscribeToNYDailyNews(seed, driver);
//			}
//			if (Math.random() < 0.2) {
//				subscribeToNBCSanDiego(seed, driver);
//			}
//			if (Math.random() < 0.5) {
//				subscribeToTheGolfChannel(seed, driver);
//			}
//			if (Math.random() < 0.5) {
//				subscribeToDetroitDailyNews(seed, driver);
//			}
//			if (Math.random() < 0.5) {
//				subscribeToSanAntonioNews(seed, driver);
//			}
//			if (Math.random() < 0.5) {
//				subscribeToReDef(seed, driver);
//			}
//			if (Math.random() < 0.5) {
//				subscribeToSkimm(seed, driver);
//			}
			
			JDBC.updateSubscription(seed);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			logger.info("Closing driver");
			driver.close();
			driver.quit();
		}
	}
	
	/**
	 * Subscribes to different newsletters from the NY Times
	 * @param driver
	 */
	private void subscribeToNyTimes(WebDriver driver) {
		if (Math.random() <= 1 && !seed.getSubscription().contains(Constant.NyTimes.SiteName.cooking)) {
		    subscribeToNyTimesRandom(seed, Constant.NyTimes.SiteUrl.cooking, Constant.NyTimes.SiteName.cooking, driver);
		}
		if (Math.random() <= 1 && !seed.getSubscription().contains(Constant.NyTimes.SiteName.dealBook)) {
		    subscribeToNyTimesRandom(seed, Constant.NyTimes.SiteUrl.dealBook, Constant.NyTimes.SiteName.dealBook, driver);
		}
		if (Math.random() <= 1 && !seed.getSubscription().contains(Constant.NyTimes.SiteName.bits)) {
		    subscribeToNyTimesRandom(seed, Constant.NyTimes.SiteUrl.bits, Constant.NyTimes.SiteName.bits, driver);
		}
		if (Math.random() <= 1 && !seed.getSubscription().contains(Constant.NyTimes.SiteName.firstDraft)) {
		    subscribeToNyTimesRandom(seed, Constant.NyTimes.SiteUrl.firstDraft, Constant.NyTimes.SiteName.firstDraft, driver);
		}
		if (Math.random() <= 1 && !seed.getSubscription().contains(Constant.NyTimes.SiteName.opinionToday)) {
		    subscribeToNyTimesRandom(seed, Constant.NyTimes.SiteUrl.opinionToday, Constant.NyTimes.SiteName.opinionToday, driver);
		}
		if (Math.random() <= 1 && !seed.getSubscription().contains(Constant.NyTimes.SiteName.afterNoonUpdate)) {
		    subscribeToNyTimesRandom(seed, Constant.NyTimes.SiteUrl.afterNoonUpdate, Constant.NyTimes.SiteName.afterNoonUpdate, driver);
		}
		if (Math.random() <= 1 && !seed.getSubscription().contains(Constant.NyTimes.SiteName.theUpshot)) {
		    subscribeToNyTimesRandom(seed, Constant.NyTimes.SiteUrl.theUpshot, Constant.NyTimes.SiteName.theUpshot, driver);
		}
		if (Math.random() <= 1 && !seed.getSubscription().contains(Constant.NyTimes.SiteName.nytNowMorning)) {
		    subscribeToNyTimesRandom(seed, Constant.NyTimes.SiteUrl.nytNowMorning, Constant.NyTimes.SiteName.nytNowMorning, driver);
		}
		if (Math.random() <= 1 && !seed.getSubscription().contains(Constant.NyTimes.SiteName.nytNowEvening)) {
		    subscribeToNyTimesRandom(seed, Constant.NyTimes.SiteUrl.nytNowEvening, Constant.NyTimes.SiteName.nytNowEvening, driver);
		}
		if (Math.random() <= 1 && !seed.getSubscription().contains(Constant.NyTimes.SiteName.asian)) {
		    subscribeToNyTimesRandom(seed, Constant.NyTimes.SiteUrl.asian, Constant.NyTimes.SiteName.asian, driver);
		}
		if (Math.random() <= 1 && !seed.getSubscription().contains(Constant.NyTimes.SiteName.european)) {
		    subscribeToNyTimesRandom(seed, Constant.NyTimes.SiteUrl.european, Constant.NyTimes.SiteName.european, driver);
		}
		if (Math.random() <= 1 && !seed.getSubscription().contains(Constant.NyTimes.SiteName.nyToday)) {
		    subscribeToNyTimesRandom(seed, Constant.NyTimes.SiteUrl.nyToday, Constant.NyTimes.SiteName.nyToday, driver);
		}
		if (Math.random() <= 1 && !seed.getSubscription().contains(Constant.NyTimes.SiteName.todayHeadlines)) {
		    subscribeToNyTimesRandom(seed, Constant.NyTimes.SiteUrl.todayHeadlines, Constant.NyTimes.SiteName.todayHeadlines, driver);
		}
		
	}
	
	/**
	 * Subscribes to the NY Times newsletter url given by param
	 * @param seed
	 * @param url
	 * @param driver
	 */
	private void subscribeToNyTimesRandom(Seed seed, String url, String site, WebDriver driver) {
		try {
			logger.info("Subscribing " + seed.getUser() + " to " + site);
			driver.get(url);
			
			WebElement input = driver.findElement(By.className("text"));
			input.clear();
			input.sendKeys(seed.getUser());
			
			WebElement button = driver.findElement(By.className("applicationButton"));
			button.click();
			
			seed.setSubscription(seed.getSubscription().concat(site + Constant.COMMA));
			
		} catch (NoSuchElementException | ElementNotVisibleException | ElementNotFoundException e) {
			logger.error("Error with Seed: " + seed.getUser() + " in " + url);
		} catch (WebDriverException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 
	 * @param seed
	 * @param url
	 * @param site
	 * @param driver
	 */
	private void subscribeToGolfSmith(Seed seed, String url, String site, WebDriver driver) {
		try {
			logger.info("Subscribing " + seed.getUser() + " to " + site);
			driver.get(url);
			
			WebElement input = driver.findElement(By.name("email"));
			input.clear();
			input.sendKeys(seed.getUser());
			
			WebElement button = driver.findElement(By.id("submitAddress_footer"));
			button.submit();
			
			seed.setSubscription(seed.getSubscription().concat(site + Constant.COMMA));
			
		} catch (NoSuchElementException | ElementNotVisibleException | ElementNotFoundException e) {
			logger.error("Error with Seed: " + seed.getUser() + " in " + url);
		} catch (WebDriverException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 
	 * @param seed
	 * @param url
	 * @param site
	 * @param driver
	 */
	private void subscribeToTheHerald(Seed seed, String url, String site, WebDriver driver) {
		try {
			logger.info("Subscribing " + seed.getUser() + " to " + site);
			driver.get(url);
			
			WebElement email = driver.findElement(By.id("txtEmailAddress"));
			email.clear();
			email.sendKeys(seed.getUser());
			
			WebElement submit = driver.findElement(By.id("btnSave"));
			submit.click();
			
			seed.setSubscription(seed.getSubscription().concat(site + Constant.COMMA));
			
		} catch (NoSuchElementException | ElementNotVisibleException | ElementNotFoundException e) {
			logger.error("Error with Seed: " + seed.getUser() + " in " + url);
		} catch (WebDriverException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	private void subscribeToNBCNews(Seed seed, String url, String site, WebDriver driver) {
		try {
			logger.info("Subscribing " + seed.getUser() + " to " + site);
			driver.get(url);
			
			WebElement email = driver.findElement(By.className("j-email"));
			email.clear();
			email.sendKeys(seed.getUser());
			
			WebElement submit = driver.findElement(By.className("j-submit"));
			submit.click();
			Thread.sleep(5000); //We wait 5 seconds until the email is saved on the page
			
			seed.setSubscription(seed.getSubscription().concat(site + Constant.COMMA));
			
		} catch (NoSuchElementException | ElementNotVisibleException | ElementNotFoundException | InterruptedException e) {
			logger.error("Error with Seed: " + seed.getUser() + " in " + url);
		} catch (WebDriverException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	private void subscribeToDailyNews(Seed seed, String url, String site, WebDriver driver) {
		try {
			logger.info("Subscribing " + seed.getUser() + " to " + site);
			driver.get(url);
			
			WebElement input = driver.findElement(By.name("email"));
			input.clear();
			input.sendKeys(seed.getUser());

			WebElement zipcode = driver.findElement(By.name("zipcode"));
			zipcode.clear();
			CharSequence code = new String(Integer.toString(randInt(60000, 799999)));
			zipcode.sendKeys(code);

			List<WebElement> radioButtons = driver.findElements(By.name("print_subscriber"));
			radioButtons.get(1).click();
			
			List<WebElement> checkboxes = driver.findElements(By.xpath("//label[@class='nw_label']/input"));
			for (int i = 0; i < checkboxes.size(); i++) {
				checkboxes.get(i).click();
			}
			
			WebElement button = driver.findElement(By.xpath("//div[@class='nw_submit_contain']/input"));
			button.click();
			
			if(driver.findElements(By.id("update_pref")).size() > 0){
				WebElement update = driver.findElement(By.id("update_pref"));
				update.click();
				
				checkboxes = driver.findElements(By.xpath("//label[@class='nw_label']/input"));
				for (int i = 0; i < checkboxes.size(); i++) {
					if(!checkboxes.get(i).isSelected()){
						checkboxes.get(i).click();
					}
				}
				button = driver.findElement(By.xpath("//div[@class='nw_submit_contain']/input"));
				button.click();
			}
			
			seed.setSubscription(seed.getSubscription().concat(site + Constant.COMMA));
			
		} catch (NoSuchElementException | ElementNotVisibleException | ElementNotFoundException e) {
			logger.error("Error with Seed: " + seed.getUser() + " in " + url);
		} catch (WebDriverException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	private void subscribeToForbes(Seed seed, String url, String site, WebDriver driver) {
		try {
			logger.info("Subscribing " + seed.getUser() + " to " + site);
			driver.get(url);
			
			WebElement continueBtn = driver.findElement(By.className("continue-button"));
			continueBtn.click();
			
			WebElement input = driver.findElement(By.id("Email"));
			input.clear();
			input.sendKeys(seed.getUser());
			input.sendKeys(Keys.RETURN);
			
			seed.setSubscription(seed.getSubscription().concat(site + Constant.COMMA));
			
		} catch (NoSuchElementException | ElementNotVisibleException | ElementNotFoundException e) {
			logger.error("Error with Seed: " + seed.getUser() + " in " + url);
		} catch (WebDriverException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 
	 * @param seed
	 * @param url
	 * @param site
	 * @param driver
	 */
	private void subscribeToMatterMark(Seed seed, String url, String site, WebDriver driver) {
		try {
			logger.info("Subscribing " + seed.getUser() + " to " + site);
			driver.get(url);
			
			WebElement input = driver.findElement(By.id("free_email"));
			input.clear();
			input.sendKeys(seed.getUser());
			input.submit();
			
			seed.setSubscription(seed.getSubscription().concat(site + Constant.COMMA));
			
		} catch (NoSuchElementException | ElementNotVisibleException | ElementNotFoundException e) {
			logger.error("Error with Seed: " + seed.getUser() + " in " + url);
		} catch (WebDriverException e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 
	 * @param seed
	 * @param url
	 * @param site
	 * @param driver
	 */
	private void subscribeFashionMagazine(Seed seed, String url, String site, WebDriver driver) {
		try {
			logger.info("Subscribing " + seed.getUser() + " to " + site);
			driver.get(url);
			
			WebElement input = driver.findElement(By.name("input_1"));
			input.clear();
			input.sendKeys(seed.getUser());
			input.submit();
			
			seed.setSubscription(seed.getSubscription().concat(site + Constant.COMMA));
			
		} catch (NoSuchElementException | ElementNotVisibleException | ElementNotFoundException e) {
			logger.error("Error with Seed: " + seed.getUser() + " in " + url);
		} catch (WebDriverException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 
	 * @param seed
	 * @param url
	 * @param site
	 * @param driver
	 */
	private void subscribeToFetch(Seed seed, String url, String site, WebDriver driver) {
		try {
			logger.info("Subscribing " + seed.getUser() + " to " + site);
			driver.get(url);
			
			WebElement input = driver.findElement(By.name("email"));
			input.clear();
			input.sendKeys(seed.getUser());
			input.submit();
			
			seed.setSubscription(seed.getSubscription().concat(site + Constant.COMMA));
			
		} catch (NoSuchElementException | ElementNotVisibleException | ElementNotFoundException e) {
			logger.error("Error with Seed: " + seed.getUser() + " in " + url);
		} catch (WebDriverException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 
	 * @param seed
	 * @param url
	 * @param site
	 * @param driver
	 */
	private void subscribeToTheWashingtonPost(Seed seed, String url, String site, WebDriver driver) {
		try {
			logger.info("Subscribing " + seed.getUser() + " to " + site);
			driver.get(url);
			
			List<WebElement> signUpButtons = driver.findElements(By.className("action"));
			List<WebElement> buttons = driver.findElements(By.xpath("//span[@class='input-group-btn']/button"));
			int i = 0;
			int j = 0;
			int x = 0;
			boolean searchInput = true;
			do{
				if(j == 3){
					j=0;
					x++;
					scrollDown(450*x, driver);
				}
				signUpButtons.get(i).click();
				Thread.sleep(1000);
				if(searchInput){
					WebElement input = driver.findElement(By.name("email"));
					input.clear();
					input.sendKeys(seed.getUser());
				}
				buttons.get(i).click();
				Thread.sleep(3500);
				i++;
				j++;
				searchInput = false;
			}while(i < signUpButtons.size());
			
			seed.setSubscription(seed.getSubscription().concat(site + Constant.COMMA));
			
		} catch (NoSuchElementException | ElementNotVisibleException | ElementNotFoundException | InterruptedException e) {
			logger.error("Error with Seed: " + seed.getUser() + " in " + url);
		} catch (WebDriverException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	private void scrollDown(int x, WebDriver driver){
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("scroll(0, " + x + ");");
	}
	
	
	
	private void subscribeToHoustonCron(Seed seed, WebDriver driver) {
		String url = "http://www.chron.com/newsletters/";
		String site = "HoustonCron,";
		if (seed.getSubscription().isEmpty() || !seed.getSubscription().contains(site)) {
			try {
				logger.info("Subscribing " + seed.getUser() + " to " + site);
				driver.get(url);
				WebElement email = driver.findElement(By.id("email"));
				email.clear();
				email.sendKeys(seed.getUser());
				WebElement confirmEmail = driver.findElement(By.id("custom_confirm_email"));
				confirmEmail.clear();
				confirmEmail.sendKeys(seed.getUser());

				WebElement first = driver.findElement(By.id("first"));
				first.clear();
				first.sendKeys(seed.getUser());

				WebElement last = driver.findElement(By.id("last"));
				last.clear();
				last.sendKeys(seed.getUser());

				for (int i = 1; i <= 32; i++) {
					try {
						if (Math.random() < 0.8) {
							WebElement checkbox = driver.findElement(By.name("slid_" + i));
							checkbox.click();
						}

					} catch (WebDriverException e) {
						logger.info("ElementNotClickable in Houston. Trying another one");
					}

				}
				email.sendKeys(Keys.RETURN);

				Thread.sleep(1000);
				seed.setSubscription(seed.getSubscription().concat(site));
				Thread.sleep(1000);
			} catch (InterruptedException | NoSuchElementException e) {
				logger.info("Error with Seed: " + seed.getUser() + " in " + url);
			} catch (WebDriverException e) {
				logger.info("Element in " + url + "is not clickable. Please review");
			}
		} else {
			logger.info("Seed " + seed.getUser() + " is already subscripted to " + site);
		}
	}

	private void subscribeToNBCSanDiego(Seed seed, WebDriver driver) {
		String url = "http://www.nbcsandiego.com/newsletters/";
		String site = "NBCSanDiego,";
		if (seed.getSubscription().isEmpty() | !seed.getSubscription().contains(site)) {
			try {
				logger.info("Subscribing " + seed.getUser() + " to " + site);
				driver.get(url);
				WebElement name = driver.findElement(By.name("fname"));
				name.clear();
				name.sendKeys(seed.getUser());
				WebElement lname = driver.findElement(By.name("lname"));
				lname.clear();
				lname.sendKeys(seed.getUser());

				WebElement checkbox1 = driver.findElements(By.name("newsletterSelection")).get(0);
				checkbox1.click();
				// if (Math.random() < 0.5) {
				// WebElement checkbox2 =
				// driver.findElement(By.name("subscriptions.interestCategories[1].selected"));
				// checkbox2.click();
				// }
				// WebElement submit =
				// driver.findElement(By.className("btn-primary"));
				Thread.sleep(1000);
				// submit.click();
				seed.setSubscription(seed.getSubscription().concat(site));

			} catch (InterruptedException | NoSuchElementException e) {
				logger.info("Error with Seed: " + seed.getUser() + " in " + url);
			} catch (WebDriverException e) {
				logger.info("Element in " + url + "is not clickable. Please review");
			}
		} else {
			logger.info("Seed " + seed.getUser() + " is already subscripted to " + site);
		}
	}

	private void subscribeToTheGolfChannel(Seed seed, WebDriver driver) {
		String url = "http://email.thegolfchannel.com/golfchan3/golfchan_reg.action";
		String site = "TheGolfChannel,";
		if (seed.getSubscription().isEmpty() | !seed.getSubscription().contains(site)) {

			try {
				logger.info("Subscribing " + seed.getUser() + " to " + site);
				driver.get(url);
				WebElement email = driver.findElement(By.id("news-form_email"));
				email.clear();
				email.sendKeys(seed.getUser());
				List<WebElement> checkboxes = driver.findElements(By.className("customCheckbox"));
				for (int i = 0; i < checkboxes.size(); i++) {
					checkboxes.get(i).click();
				}
				WebElement submit = driver.findElement(By.className("submit-btn"));
				Thread.sleep(1000);
				submit.click();
				seed.setSubscription(seed.getSubscription().concat(site));
				Thread.sleep(1000);
			} catch (InterruptedException | NoSuchElementException e) {
				logger.info("Error with Seed: " + seed.getUser() + " in " + url);
				getScreenShot(driver, seed.getUser() + "such");
				logger.info("Saving screenshot as /var/www/errors/" + seed.getUser() + "such");

			} catch (WebDriverException e) {
				logger.info("Element in " + url + "is not clickable. Saving screenshot as /var/www/errors/"
						+ seed.getUser());
				getScreenShot(driver, seed.getUser());

			}
		} else {
			logger.info("Seed " + seed.getUser() + " is already subscripted to " + site);
		}

	}

	private void subscribeToDetroitDailyNews(Seed seed, WebDriver driver) {
		String url = "https://account.detroitnews.com/newsletters/";
		String site = "DetroitDailyNews,";
		if (seed.getSubscription() != null && !seed.getSubscription().contains(site)) {

			try {
				logger.info("Subscribing " + seed.getUser() + " to " + site);
				driver.get(url);
				WebElement email = driver.findElement(By.id("email"));
				email.clear();
				email.sendKeys(seed.getUser());
				WebElement confirmEmail = driver.findElement(By.id("email-confirm"));
				confirmEmail.clear();
				confirmEmail.sendKeys(seed.getUser());

				List<WebElement> checkboxes = driver.findElements(By.xpath("//input[@name='pubLists']"));
				for (int i = 0; i < checkboxes.size(); i++) {
					if (Math.random() < 0.4) {
						checkboxes.get(i).click();
					}
				}
				List<WebElement> checkboxesOffers = driver.findElements(By.xpath("//input[@name='categories']"));
				for (int i = 0; i < checkboxesOffers.size(); i++) {
					if (Math.random() < 0.3) {
						checkboxesOffers.get(i).click();
					}
				}
				WebElement submit = driver.findElement(By.xpath("//button[@name='action']"));
				Thread.sleep(1000);
				submit.click();
				seed.setSubscription(seed.getSubscription().concat(site));
				Thread.sleep(1000);
			} catch (InterruptedException | NoSuchElementException e) {
				logger.info("Error with Seed: " + seed.getUser() + " in " + url);
			} catch (WebDriverException e) {
				logger.info("Element in " + url + "is not clickable. Please review");
			}
		} else {
			logger.info("Seed " + seed.getUser() + " is already subscripted to " + site);
		}

	}

	private void subscribeToNYDailyNews(Seed seed, WebDriver driver) {
		String url = "http://link.nydailynews.com/join/4xm/newslettersignup-desktop";
		String site = "NYDailyNews,";
		if (seed.getSubscription().isEmpty() | !seed.getSubscription().contains(site)) {

			try {
				logger.info("Subscribing " + seed.getUser() + " to " + site);
				driver.get(url);
				WebElement email = driver.findElement(By.name("email"));
				email.clear();
				email.sendKeys(seed.getUser());
				WebElement checkbox = driver.findElement(By.id("nydn-offer"));
				checkbox.click();
				WebElement submit = driver.findElement(By.className("nydn-submit"));
				Thread.sleep(1000);
				submit.click();
				seed.setSubscription(seed.getSubscription().concat(site));

				Thread.sleep(1000);
			} catch (InterruptedException | NoSuchElementException e) {
				logger.info("Error with Seed: " + seed.getUser() + " in " + url);
			} catch (WebDriverException e) {
				logger.info("Element in " + url + "is not clickable. Please review");
			}
		} else {
			logger.info("Seed " + seed.getUser() + " is already subscripted to " + site);
		}
	}

	private void subscribeToSanAntonioNews(Seed seed, WebDriver driver) {
		String url = "http://www.mysanantonio.com/news/local/";
		String site = "SanAntonioNews,";
		if (seed.getSubscription().isEmpty() | !seed.getSubscription().contains(site)) {

			try {
				logger.info("Subscribing " + seed.getUser() + " to " + site);
				driver.get(url);
				WebElement email = driver.findElement(By.name("email"));
				email.clear();
				email.sendKeys(seed.getUser());
				WebElement spursNation = driver.findElement(By.id("slid2"));
				spursNation.click();
				WebElement breakingNews = driver.findElement(By.id("slid3"));
				breakingNews.click();
				WebElement submit = driver.findElement(By.name("submit"));
				Thread.sleep(1000);
				submit.click();
				seed.setSubscription(seed.getSubscription().concat(site));
				Thread.sleep(2000);
			} catch (InterruptedException | NoSuchElementException e) {
				logger.info("Error with Seed: " + seed.getUser() + " in " + url);
			} catch (WebDriverException e) {
				logger.info("Element in " + url + "is not clickable. Please review");
			}
		} else {
			logger.info("Seed " + seed.getUser() + " is already subscripted to " + site);
		}
	}

	// Works! :)
	private void subscribeToReDef(Seed seed, WebDriver driver) {
		String url = "http://link.mediaredefined.com/join/353/media-redefweb";
		String site = "ReDef,";

		try {
			logger.info("Subscribing " + seed.getUser() + " to " + site);
			driver.get(url);
			driver.findElement(By.name("email")).clear();
			driver.findElement(By.name("email")).sendKeys(seed.getUser());
			driver.findElement(By.xpath("//div[@id='button']/input")).click();
			seed.setSubscription(seed.getSubscription().concat(site));
			Thread.sleep(3000);
		} catch (NoSuchElementException | InterruptedException e) {
			logger.info("Error with Seed: " + seed.getUser() + " in " + url);
		}
	}

	// Works! :)
	private void subscribeToSkimm(Seed seed, WebDriver driver) {
		String url = "http://www.theskimm.com/";
		String site = "TheSkimm,";

		try {
			logger.info("Subscribing " + seed.getUser() + " to " + site);
			driver.get(url);
			driver.findElement(By.name("email")).clear();
			driver.findElement(By.name("email")).sendKeys(seed.getUser());
			driver.findElement(By.name("email")).submit();
			seed.setSubscription(seed.getSubscription().concat(site));
			Thread.sleep(3000);
		} catch (InterruptedException | NoSuchElementException e) {
			logger.info("Error with Seed: " + seed.getUser() + " in " + url);
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

	/**
	 * 
	 * @param fileName
	 * @param fileContent
	 */
	public void writeToFile(String fileName, String fileContent) {
		try {
			logger.info("Writing page to: " + fileName);
			FileWriter writer = new FileWriter(fileName, false);
			writer.write(fileContent);

			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static int randInt(int min, int max) {
		Random rand = new Random();
		// nextInt is normally exclusive of the top value, so add 1 to make it
		// inclusive
		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}
	
	public void getScreenShot(WebDriver driver, String name) {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		// Now you can do whatever you need to do with it, for example copy
		// somewhere
		try {
			FileUtils.copyFile(scrFile, new File("/var/www/errors/" + name + ".jpg"));
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}

	}

}