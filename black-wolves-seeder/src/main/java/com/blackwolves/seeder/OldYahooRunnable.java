/**
 * 
 */
package com.blackwolves.seeder;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;

/**
 * @author gaston.dapice
 *
 */
public class OldYahooRunnable extends YahooRunnable{

	
	public OldYahooRunnable(WebDriver driver, Seed seed, Human human, Logger logger) {
		super(driver, seed, human, logger);
	}

	/**
	 * @param driver
	 * @param seed
	 * @throws InterruptedException
	 */
	
	@Override
	public void processInbox(Seed seed) {
		if (driver.findElements(By.id("inbox")).size() > 0) {

			logger.info("Getting the Inbox Url");
			driver.get(driver.findElement(By.id("inbox")).findElement(By.tagName("a")).getAttribute("href"));
			if (driver.findElements(By.className("mlink")).size() > 0) {

				logger.info("mlink found");
				List<WebElement> inboxMsgs = driver.findElements(By.className("mlink"));

				int percentage = (int) (inboxMsgs.size() * PERCENTAGE);
				for (int j = 0; j < percentage; j++) {
					if (driver.findElements(By.className("mlink")).size() > 0) {
						inboxMsgs = driver.findElements(By.className("mlink"));
						logger.info("Obtaining a random message position so it can be open");
						int randomPosition = obtainRandomMsgsPosition(inboxMsgs);
						String href = inboxMsgs.get(randomPosition).getAttribute("href");
						logger.info("Opening this inbox message: " + href);
						driver.get(href);
						clickShowImages("show-text");

						scrollDownAndUp();

						clickRandomLink();

						logger.info("Going back to inbox");
						driver.get(
								driver.findElement(By.id("inbox")).findElement(By.tagName("a")).getAttribute("href"));
					} else {
						logger.info("**********   No mlink found or no messages available   **********");
					}
				}
			} else {
				logger.info("**********   No mlink found or no messages available   **********");
			}
		} else {
			logger.info("**********   No inbox Url found   **********");
			// SubscriberRunnable.writeToFile("no_inbox_url.html",
			// driver.getPageSource());
		}
	}

	
	/**
	 * @param driver
	 * @param seed
	 * @throws InterruptedException
	 */
	
	@Override
	public void processSpam(Seed seed) {
		if (driver.findElements(By.id("bulk")).size() > 0) {

			logger.info("Getting the Bulk Url");
			driver.get(driver.findElement(By.id("bulk")).findElement(By.tagName("a")).getAttribute("href"));

			if (driver.findElements(By.className("mlink")).size() > 0) {

				logger.info("mlink found");
				List<WebElement> spamMsgs = driver.findElements(By.className("mlink"));

				int percentage = (int) (spamMsgs.size() * PERCENTAGE);
				logger.info("Clicking " + percentage + " emails out of spam");
				for (int j = 0; j < percentage; j++) {
					logger.info(j + " emails not spammed " + (percentage - j) + " emails to go");
					if (driver.findElements(By.className("mlink")).size() > 0) {
						spamMsgs = driver.findElements(By.className("mlink"));

						logger.info("Obtaining a random message position so it can be open");
						int randomPosition = obtainRandomMsgsPosition(spamMsgs);
						spamMsgs.get(randomPosition).click();
						String href = spamMsgs.get(randomPosition).getAttribute("href");

						logger.info("Opening this spam message: " + href);
						driver.get(href);

						clickShowImages("spamwarning");

						Select notSpam = new Select(driver.findElement(By.name("top_action_select")));
						notSpam.selectByValue("msg.ham");
						logger.info("Clicking the not spam option");
						driver.findElement(By.name("self_action_msg_topaction")).click();
					} else {
						logger.info("**********   No mlink found or no messages available   **********");
					}
				}
			} else {
				logger.info("**********   No mlink found or no messages available   **********");
			}
		} else {
			logger.info("**********   No bulk Url found   **********");
//			SubscriberRunnable.writeToFile("now_bulk_url.html", driver.getPageSource());
		}
	}

	/**
	 * 
	 */
	public void clickRandomLink() {
		logger.info("Getting the content of the message");
		WebElement div = driver.findElement(By.className("mailContent"));
		logger.info("Looking for links inside the message");
		if (div.findElements(By.tagName("a")).size() != 0) {
			logger.info("Links found");
			List<WebElement> linksToGo = div.findElements(By.tagName("a"));
			Random rand = new Random();
			int randomLinkNo = rand.nextInt(linksToGo.size());
			String aUrl = linksToGo.get(randomLinkNo).getAttribute("href");
			if (aUrl != null) {
				if (aUrl.contains("unsub") || aUrl.contains("yahoo")) {
					logger.info("It is an Unsubscribe link!! - we are not clicking it");
					logger.info(aUrl);
				} else {
					openTab(aUrl);
					switchToNewWindow();
					switchToPreviousWindow();
				}
			}
		} else {
			logger.info("**********   No links found or none available  **********");
		}
	}

	/**
	 * @param driver
	 */
	private void scrollDownAndUp() {
		// scroll down
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		// jse.executeScript("window.scrollBy(0,250)", "");
		// OR
		jse.executeScript("scroll(0, 250);");
		// scroll up
		// jse.executeScript("window.scrollBy(0,-250)", "");
		// OR
		jse.executeScript("scroll(0, -250);");
	}

//	/**
//	 * @param driver
//	 */
//	private void clickNotSpamForAllMessages(WebDriver driver) {
//		// Select all spams and move them to inbox
//		if (driver.findElements(By.id("select_all")).size() > 0) {
//
//			logger.info("Clicking Select all checkbox");
//			driver.findElement(By.id("select_all")).click();
//
//			if (driver.findElements(By.id("top_ham")).size() > 0) {
//
//				logger.info("Clicking Not spam button");
//				driver.findElement(By.id("top_ham")).click();
//			}
//		}
//	}

	

	/**
	 * 
	 * @param ip
	 * @return
	 */
	public DesiredCapabilities addProxyCapabilities(String ip) {

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
		// proxy.setProxyType(ProxyType.MANUAL);
		proxy.setHttpProxy(httpProxy);
		proxy.setSslProxy(sslProxy);
		proxy.setFtpProxy(ftpProxy);

		capability.setCapability(CapabilityType.PROXY, proxy);
		capability.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);

		return capability;
	}

	@Override
	public void clickSpam() {
		
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

		Set<Cookie> allCookies = driver.manage().getCookies();

		for (Cookie cookie : allCookies) {
			logger.info("***************** Cookies? :" + cookie.getName());

		}
	}
	
	/**
	 * Calculates the hours of difference between the two given dates
	 * 
	 * @param from
	 * @param to
	 * @return int
	 */
	public int calculateDifferenceBetweenDates(Date from, Date to) {
		long diff = to.getTime() - from.getTime();
		int diffHours = (int) (diff / (60 * 60 * 1000));
		// int diffDays = (int) (diff / (24 * 60 * 60 * 1000));
		// int diffMin = (int) (diff / (60 * 1000));
		// int diffSec = (int) (diff / (1000));
		return diffHours;
	}

	public int calculateDifferenceBetweenDatesInMinutes(Date from, Date to) {
		long diff = to.getTime() - from.getTime();
		// int diffHours = (int) (diff / (60 * 60 * 1000));
		// int diffDays = (int) (diff / (24 * 60 * 60 * 1000));
		int diffMin = (int) (diff / (60 * 1000));
		// int diffSec = (int) (diff / (1000));
		return diffMin;
	}
}
