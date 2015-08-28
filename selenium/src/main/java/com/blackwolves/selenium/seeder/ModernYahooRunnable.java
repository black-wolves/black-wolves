/**
 * 
 */
package com.blackwolves.selenium.seeder;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * @author danigrane
 *
 */
public class ModernYahooRunnable extends YahooRunnable {

	
	public ModernYahooRunnable(WebDriver driver, String seed) {
		super(driver, seed);
		// TODO Auto-generated constructor stub
	}

	private static final Logger logger = LogManager.getLogger(ModernYahooRunnable.class.getName());
	
	@Override
	public void processInbox(WebDriver driver, String[] seed) throws InterruptedException {
		checkWelcomeDialog(driver);
		WebDriverWait wait = new WebDriverWait(driver, 30);
		WebElement inboxFolder = null;
		try {
			Seeder.getScreenShot(driver, "processNewYahoo2Inbox");
			driver.findElement(By.className("inbox-label")).click();
			inboxFolder = driver.findElement(By.className("empty-folder"));
		} catch (Exception e) {
			logger.info("Inbox Folder is not empty");
		}
		
		
		// Check if inbox is empty
		if (inboxFolder != null && inboxFolder.isDisplayed()) {
			logger.info("Inbox Folder is empty.");
		} 
		//If not empty, proceed
		else {
			Thread.sleep(1000 + randInt(0, 2000));
			wait.until(ExpectedConditions.elementToBeClickable(By.className("subj")));
		   
			if (driver.findElements(By.className("subj")).size() > 0) {
				logger.info("subj found");
				List<WebElement> inboxMsgs = driver.findElements(By.className("subj"));
				logger.info("Percentage is " +PERCENTAGE);
				int percentage = (int) (inboxMsgs.size() * PERCENTAGE);
				for (int j = 0; j < percentage; j++) {
					logger.info((percentage - j) + " emails to go ");

					if (driver.findElements(By.className("subj")).size() > 0) {
						try {
					    	mouse.moveByOffset(200+randInt(0, 300), 300+randInt(0, 400));
							logger.info("Obtaining a random message position so it can be open");
							int randomPosition = obtainRandomMsgsPosition(inboxMsgs);
							WebElement currentMsg = inboxMsgs.get(randomPosition);
							logger.info("Clicking in Msg : " + currentMsg.getText());
							currentMsg.click();
							humanizeMe();
							Thread.sleep(1000 + randInt(1000, 5000));
							humanizeMe();
							clickShowImages(driver, "show-text");

							// The first time works fine. Then it tries to go to
							// the same link every time and breaks.
							// clickRandomLinkForNewYahoo2(driver);

							logger.info("Going back to inbox");
						
							
						//     mouse.moveToElement(driver.findElement(By.className("inbox-label"))).build().perform();
							driver.findElement(By.className("inbox-label")).click();

						}

						catch (Exception exception) {
							logger.info("Need to sync the thread...Going to inbox to keep going ");
							driver.findElement(By.className("inbox-label")).click();
						}
					} else {
						logger.info("**********   No mlink found or no messages available   **********");
					}
				}
			} else {
				logger.info("**********   No mlink found or no messages available   **********");
			}
		}
	}

	private void humanizeMe() {
		logger.info("Adding Human Behaviour");
		
		int goToX = randInt(0, 100);
		int goToY = randInt(0, 100);
		logger.info("Starting to move mouse randomly. ");
		for (int i = randInt(10, 150); i > 0; i--) {
			mouse.moveByOffset(goToX+i, goToY+i);
			driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
		}
		logger.info("Ended mouse simulation. ");


	//	WebElement searchBar = driver.findElement(By.id("UHSearchProperty"));
	//	mouse.moveToElement(searchBar).build().perform();
		
		if(true){  
			jse.executeScript("window.scrollBy(0,250)", "");
			driver.manage().timeouts().implicitlyWait(randInt(2, 5), TimeUnit.SECONDS);
		}
		
		if(throwDice()){  
			jse.executeScript("scroll(0, -250);");
			//Reading email
			driver.manage().timeouts().implicitlyWait(randInt(2, 5), TimeUnit.SECONDS);
		}
		
		
	}

	private void checkWelcomeDialog(WebDriver driver) {
		int retries = 2;
		for (int i = 0; i <retries ; i++) {
			try {
				List<?> dialogs = driver.findElements(By.className("ob-contactimport-btn-skip"));
				if (!dialogs.isEmpty()) {
					logger.info("WelcomeDialog found. Closing it.");
					WebElement welcomeDialog = (WebElement) dialogs.get(0);
					welcomeDialog.click();
					break;
				}
			} catch (Exception e) {
				driver.manage().timeouts().implicitlyWait(3000+randInt(500, 2000), TimeUnit.SECONDS);
			}
			
		}
	
	}

		
		
		
		
		
		
		

	/* (non-Javadoc)
	 * @see com.blackwolves.selenium.seeder.YahooHandler#spamHandler(org.openqa.selenium.WebDriver, java.lang.String[])
	 */
	@Override
	public void processSpam(WebDriver driver, String[] seed) throws InterruptedException {
		if (driver.findElements(By.id("spam-label")).size() > 0) {
			WebDriverWait wait = new WebDriverWait(driver, 20);
			WebElement spamFolder = null;
			try {
				logger.info("Getting the Bulk Url");
				driver.findElement(By.id("spam-label")).click();
				Thread.sleep(2000 + randInt(0, 2000));
				spamFolder = driver.findElement(By.className("empty-folder"));
			} catch (Exception e) {
				logger.info("There are msgs in the spam folder, go get them Tiger!");
			}

			// Check if the spam folder is empty
			if (spamFolder != null && spamFolder.isDisplayed()) {
				logger.info("Spam Folder is empty! UOHOOO!");
			} else {
				wait.until(ExpectedConditions.elementToBeClickable(By.className("subj")));
				Seeder.getScreenShot(driver, "processNewYahoo2Bulk");
				if (driver.findElements(By.className("subj")).size() > 0) {

					logger.info("subj found");
					wait.until(ExpectedConditions.elementToBeClickable(By.className("subj")));

					List<WebElement> spamMsgs = driver.findElements(By.className("subj"));
					logger.info("Percentage is " +PERCENTAGE);
					int percentage = (int) (spamMsgs.size() * PERCENTAGE);
					for (int j = 0; j < percentage; j++) {
						logger.info(j + " emails not spammed " + (percentage - j) + " emails to go");

						if (driver.findElements(By.className("subj")).size() > 0) {

							try {
								spamMsgs = driver.findElements(By.className("subj"));
								logger.info("Obtaining a random message position so it can be open");
								int randomPosition = obtainRandomMsgsPosition(spamMsgs);
								Thread.sleep(1000 + randInt(1000, 2000));
								logger.info("Opening the spam message");
								spamMsgs.get(randomPosition).click();
								Thread.sleep(1000 + randInt(1000, 2000));
							//	humanizeMe();
								clickShowImages(driver, "show-text");
								logger.info("Clicking the not spam option");
								wait.until(ExpectedConditions.elementToBeClickable(By.id("main-btn-spam")));

									List<WebElement> elements = driver.findElements(By.className("card-actions-menu"));
									for (WebElement ahref : elements) {
									if (ahref.isDisplayed()) {
										jse.executeScript("arguments[0].scrollIntoView(true);",ahref);
										mouse.moveToElement(ahref);
										ahref.findElement(By.tagName("a")).click();
										List <WebElement> myList =  driver.findElements(By.className("spamactions"));
										List <WebElement> submenuList =  myList.get(0).findElements(By.tagName("li"));
										Thread.sleep(randInt(1500, 2500));
										WebElement notSpamMultare = submenuList.get(1);
										notSpamMultare.click();
										logger.info("Clicking not spam from submenu");
										Thread.sleep(randInt(1000, 2000));
									}
								}
									
										
							} catch (Exception e) {
								logger.info("Way too fast Usain Bolt...Let's go to spam folder and keep going");
								logger.info("This is the error "+e.getMessage());
								driver.findElement(By.id("spam-label")).click();
							}

							// wait.until(ExpectedConditions.elementToBeClickable(By.className("list-view-item-container
							// ml-bg tcLabel-y")));
						//	Thread.sleep(randInt(1000, 3000));

						} else {
							logger.info("**********   No mlink found or no messages available   **********");
						}
					}
				} else {
					logger.info("**********   No mlink found or no messages available   **********");
				}
			}
		}
	}
	
	public void clickRandomLink(WebDriver driver) throws InterruptedException {
		logger.info("Getting the content of the message");
		WebElement div = driver.findElement(By.className("thread-body"));
		logger.info("Looking for links inside the message");
		if (div.findElements(By.tagName("a")).size() != 0) {
			logger.info("Links found");
			List<WebElement> linksToGo = div.findElements(By.tagName("a"));
			int randomLinkNo = randInt(0, linksToGo.size());
			String aUrl = linksToGo.get(randomLinkNo).getAttribute("href");
			if (aUrl != null) {
				if (aUrl.contains("unsub") || aUrl.contains("yahoo")) {
					logger.info("It is an Unsubscribe link!! - we are not clicking it");
					logger.info(aUrl);
				} else {
					openInNewWindow(driver, linksToGo.get(randomLinkNo));
				}
			}
		} else {
			logger.info("**********   No links found or none available  **********");
		}
	}


}
