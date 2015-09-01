/**
 * 
 */
package com.blackwolves.selenium.seeder;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * @author danigrane
 *
 */
public class ModernYahooRunnable extends YahooRunnable {

	private static final Logger logger = LogManager.getLogger(ModernYahooRunnable.class.getName());
	
	public ModernYahooRunnable(WebDriver driver, String seed, Human human) {
		super(driver, seed, human);
	}

	@Override
	public void processInbox(WebDriver driver, String[] seed, Human human) throws InterruptedException {
		checkWelcomeDialog(driver);
		WebDriverWait wait = new WebDriverWait(driver, 30);
		WebElement inboxFolder = null;
		try {
			Seeder.getScreenShot(driver, "processNewYahoo2Inbox");
			driver.findElement(By.className("inbox-label")).click();
			inboxFolder = driver.findElement(By.className("empty-folder"));
		} catch (Exception e) {
			logger.info("Inbox Folder is not empty");
			logger.error(e.getMessage(), e);
		}

		// Check if inbox is empty
		if (inboxFolder != null && inboxFolder.isDisplayed()) {
			logger.info("Inbox Folder is empty.");
		}
		// If not empty, proceed
		else {
			Thread.sleep(1000 + randInt(0, 2000));
			wait.until(ExpectedConditions.elementToBeClickable(By.className("subj")));

			if (driver.findElements(By.className("subj")).size() > 0) {
				
				logger.info("subj found");
				List<WebElement> inboxMsgs = driver.findElements(By.className("subj"));
				
				logger.info("Percentage is " + PERCENTAGE);
				int percentage = (int) (inboxMsgs.size() * PERCENTAGE);
				
				for (int j = 0; j < percentage; j++) {
					
					logger.info((percentage - j) + " emails to go ");
					wait.until(ExpectedConditions.elementToBeClickable(By.className("subj")));
					
					if (driver.findElements(By.className("subj")).size() > 0) {
						try {
							mouse.moveByOffset(200 + randInt(0, 300), 300 + randInt(0, 400));
							
							logger.info("subj found");
							inboxMsgs = driver.findElements(By.className("subj"));
							
							logger.info("Obtaining a random message position so it can be open");
							int randomPosition = obtainRandomMsgsPosition(inboxMsgs);
							WebElement currentMsg = inboxMsgs.get(randomPosition);
							
							logger.info("Clicking in Msg : " + currentMsg.getText());
							currentMsg.click();
							
							humanizeMe();
							
							Thread.sleep(1000 + randInt(1000, 5000));
							
							humanizeMe();
							
							clickShowImages(driver, "show-text");
							
							replyToEmail(driver, wait, human);
							
							humanizeMe();
							
							clickRandomLink(driver);

							logger.info("Going back to inbox");
							mouse.moveToElement(driver.findElement(By.className("inbox-label"))).build().perform();
							driver.findElement(By.className("inbox-label")).click();
							
							Thread.sleep(randInt(1500, 2500));
							
							driver.navigate().refresh();
							
							Thread.sleep(randInt(1500, 2500));

						}

						catch (Exception exception) {
							logger.info("Need to sync the thread...Going to inbox to keep going ");
							driver.findElement(By.className("inbox-label")).click();
							driver.navigate().refresh();
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
			mouse.moveByOffset(goToX + i, goToY + i);
			driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
		}
		logger.info("Ended mouse simulation. ");

		// WebElement searchBar = driver.findElement(By.id("UHSearchProperty"));
		// mouse.moveToElement(searchBar).build().perform();

		if (true) {
			jse.executeScript("window.scrollBy(0,250)", "");
			driver.manage().timeouts().implicitlyWait(randInt(2, 5), TimeUnit.SECONDS);
		}

		if (throwDice()) {
			jse.executeScript("scroll(0, -250);");
			// Reading email
			driver.manage().timeouts().implicitlyWait(randInt(2, 5), TimeUnit.SECONDS);
		}

	}

	private void checkWelcomeDialog(WebDriver driver) {
		int retries = 2;
		for (int i = 0; i < retries; i++) {
			try {
				List<?> dialogs = driver.findElements(By.className("ob-contactimport-btn-skip"));
				if (!dialogs.isEmpty()) {
					logger.info("WelcomeDialog found. Closing it.");
					WebElement welcomeDialog = (WebElement) dialogs.get(0);
					welcomeDialog.click();
					break;
				}
			} catch (Exception e) {
				driver.manage().timeouts().implicitlyWait(3000 + randInt(500, 2000), TimeUnit.SECONDS);
			}

		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.blackwolves.selenium.seeder.YahooHandler#spamHandler(org.openqa.
	 * selenium.WebDriver, java.lang.String[])
	 */
	@Override
	public void processSpam(WebDriver driver, String[] seed) throws InterruptedException {
		if (driver.findElements(By.id("spam-label")).size() > 0) {
			WebDriverWait wait = new WebDriverWait(driver, 20);
			WebElement spamFolder = null;
			try {
				logger.info("Getting the Bulk Url");
				driver.findElement(By.id("spam-label")).click();
				Thread.sleep(randInt(1000, 3000));
				spamFolder = driver.findElement(By.className("empty-folder"));
			} catch (Exception e) {
				logger.info("There are msgs in the spam folder, go get them Tiger!");
			}

			// Check if the spam folder is empty
			if (spamFolder != null && spamFolder.isDisplayed()) {
				logger.info("Spam Folder is empty! UOHOOO!");
			} else {
				wait.until(ExpectedConditions.elementToBeClickable(By.className("subj")));
				if (driver.findElements(By.className("subj")).size() > 0) {
					logger.info("subj found");
					wait.until(ExpectedConditions.elementToBeClickable(By.className("subj")));
					List<WebElement> spamMsgs = driver.findElements(By.className("subj"));
					logger.info("Percentage is " + PERCENTAGE);
					int percentage = (int) (spamMsgs.size() * PERCENTAGE);
					for (int j = 0; j < percentage; j++) {
						Thread.sleep(randInt(1000, 3000));
						logger.info(j + " emails not spammed " + (percentage - j) + " emails to go");
						int chances =  randInt(0, 10);
						if (chances <=6 ) {
							normalNotSpam(driver, wait);
						} else {
							dragAndDropNotSpam(driver, wait);
						}
					}
				} else {
					logger.info("**********   No mlink found or no messages available   **********");
				}
			}
		}
	}

	private void dragAndDropNotSpam(WebDriver driver, WebDriverWait wait) throws InterruptedException {
		List<WebElement> spamMsgs = driver.findElements(By.className("subj"));
		int randomPosition = obtainRandomMsgsPosition(spamMsgs);
		Thread.sleep(randInt(1000, 2000));
		logger.info("Selecting spam message");
		WebElement msg = spamMsgs.get(randomPosition);
		WebElement inboxFolder = driver.findElement(By.className("inbox-label"));
		logger.info("******** Dragging Message to inbox ***********");
		(new Actions(driver)).dragAndDrop(msg, inboxFolder).perform();
	}

	private void normalNotSpam(WebDriver driver, WebDriverWait wait) {
		List<WebElement> spamMsgs;
		try {
			spamMsgs = driver.findElements(By.className("subj"));
			logger.info("Obtaining a random message position so it can be open");
			int randomPosition = obtainRandomMsgsPosition(spamMsgs);
			Thread.sleep(1000 + randInt(1000, 2000));
			logger.info("Opening the spam message");
			spamMsgs.get(randomPosition).click();
			Thread.sleep(1000 + randInt(1000, 2000));
			// humanizeMe();
			clickShowImages(driver, "show-text");
			wait.until(ExpectedConditions.elementToBeClickable(By.id("main-btn-spam")));

			if (!throwDice()) {
				logger.info("******** Clicking the not spam LIST button ***********");
				notSpamFromSubList(driver);
			} else {
				logger.info("******** Clicking the not spam MAIN button ***********");
				driver.findElement(By.id("main-btn-spam")).click();
				wait.until(ExpectedConditions.elementToBeClickable(By.className("subj")));

			}

		} catch (Exception e) {
			logger.info("Way too fast Usain Bolt...Let's go to spam folder and keep going");
			logger.info("This is the error " + e.getMessage());
			driver.findElement(By.id("spam-label")).click();
		}
	}

	private void notSpamFromSubList(WebDriver driver) throws InterruptedException {
		List<WebElement> elements = driver.findElements(By.className("card-actions-menu"));
		for (WebElement ahref : elements) {
			if (ahref.isDisplayed()) {
				logger.info("Clicking not spam from submenu");
				jse.executeScript("arguments[0].scrollIntoView(true);", ahref);
				mouse.moveToElement(ahref);
				ahref.findElement(By.tagName("a")).click();
				List<WebElement> myList = driver.findElements(By.className("spamactions"));
				List<WebElement> submenuList = myList.get(0).findElements(By.tagName("li"));
				Thread.sleep(randInt(1500, 2500));
				WebElement notSpamMultare = submenuList.get(1);
				notSpamMultare.click();

				Thread.sleep(randInt(1000, 2000));
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
			int randomLinkNo = randInt(0, linksToGo.size()-1);
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

	@Override
	public void replyToEmail(WebDriver driver, WebDriverWait wait, Human human) throws InterruptedException {
//		String body = human.generateRandomBody(driver, wait);
		driver.findElement(By.className("icon-reply")).click();
//		Thread.sleep(randInt(2500, 3500));
//		WebElement bodyMail = driver.findElement(By.id("rtetext"));
		Thread.sleep(randInt(2500, 3500));
//		bodyMail.clear();
//		bodyMail.click();
//		human.type(bodyMail, body);
		driver.findElement(By.className("bottomToolbar")).findElement(By.className("default")).click();
		Thread.sleep(randInt(2500, 3500));
	}

	
	@Override
	public void addToAddressBook(WebDriver driver) throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		logger.info("Going to contacts section");
		driver.findElement(By.className("nav-item-contacts")).click();
		
		
//		logger.info(driver.findElements(By.className("listnav-label primary-property-btn")).size());
		logger.info(driver.findElements(By.className("listnav-label")).size());
		logger.info(driver.findElements(By.className("primary-property-btn")).size());
		
//		logger.info(driver.findElements(By.className("icon icon-add-contact")).size());
		logger.info(driver.findElements(By.className("icon")).size());
		logger.info(driver.findElements(By.className("icon-add-contact")).size());
		
		logger.info(driver.findElements(By.className("add-contact-text")).size());
		//wait.until(ExpectedConditions.elementT(By.id("legend")));
		//driver.navigate().refresh();
		Thread.sleep(randInt(1500, 2500));
	//	SuscriberRunnable.writeToFile("lalala.html", driver.getPageSource());
//		driver.findElement(By.partialLinkText("Contact nou"));
		Thread.sleep(randInt(1500, 2500));
		List <WebElement> inputs = driver.findElements(By.tagName("input"));
		for (WebElement webElement : inputs) {
			if (webElement.isDisplayed() && webElement.isEnabled()) {
				webElement.sendKeys("asdasdasd");
				
			}
		}
		
	}
}
