/**
 * 
 */
package com.blackwolves.selenium.seeder;

import java.util.List;

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
			logger.info("Inbox link not visible or empty folder. Sleeping just in case");
			Thread.sleep(5000);
			checkWelcomeDialog(driver);
			//driver.findElement(By.className("inbox-label")).click();
		}
		// Check if inbox is empty
		if (inboxFolder != null && inboxFolder.isDisplayed()) {
			logger.info("Inbox Folder is empty.");
		} else {
			Thread.sleep(1000 + randInt(0, 2000));
			wait.until(ExpectedConditions.elementToBeClickable(By.className("subj")));

			if (driver.findElements(By.className("subj")).size() > 0) {
				logger.info("subj found");
				List<WebElement> inboxMsgs = driver.findElements(By.className("subj"));
				int percentage = (int) (inboxMsgs.size() * PERCENTAGE);
				for (int j = 0; j < percentage; j++) {
					logger.info((percentage - j) + " emails to go ");

					if (driver.findElements(By.className("subj")).size() > 0) {
						try {
							logger.info("Obtaining a random message position so it can be open");
							int randomPosition = obtainRandomMsgsPosition(inboxMsgs);
							WebElement currentMsg = inboxMsgs.get(randomPosition);
							logger.info("Clicking in Msg : " + currentMsg.getText());
							currentMsg.click();

							Thread.sleep(1000 + randInt(1000, 5000));
							clickShowImages(driver, "show-text");

							// scrollDownAndUp(driver);

							// The first time works fine. Then it tries to go to
							// the same link every time and breaks.
							// clickRandomLinkForNewYahoo2(driver);

							logger.info("Going back to inbox");
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

	private void checkWelcomeDialog(WebDriver driver) {
		List<?> dialogs = driver.findElements(By.className("ob-contactimport-btn-skip"));
		if (!dialogs.isEmpty()) {
			WebElement welcomeDialog = (WebElement) dialogs.get(0);
			welcomeDialog.click();
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

					int percentage = (int) (spamMsgs.size() * PERCENTAGE);
					for (int j = 0; j < percentage; j++) {
						logger.info(j + " emails not spammed " + (percentage - j) + " emails to go");

						if (driver.findElements(By.className("subj")).size() > 0) {

							try {
								spamMsgs = driver.findElements(By.className("subj"));
								logger.info("Obtaining a random message position so it can be open");
								int randomPosition = obtainRandomMsgsPosition(spamMsgs);
								logger.info("Opening the spam message");
								spamMsgs.get(randomPosition).click();
								Thread.sleep(1000 + randInt(1000, 5000));
								clickShowImages(driver, "blocked-image");
								logger.info("Clicking the not spam option");
								wait.until(ExpectedConditions.elementToBeClickable(By.id("main-btn-spam")));

								// Clicking not spam in 83% of the cases
								if (throwDice()) {
									driver.findElement(By.id("main-btn-spam")).click();
								} else {
									driver.findElement(By.id("spam-label")).click();
									wait.until(ExpectedConditions.elementToBeClickable(By.className("subj")));
								}
							} catch (Exception e) {
								logger.info("Way too fast Usain Bolt...Let's go to spam folder and keep going");
								driver.findElement(By.id("spam-label")).click();
							}

							// wait.until(ExpectedConditions.elementToBeClickable(By.className("list-view-item-container
							// ml-bg tcLabel-y")));
							Thread.sleep(3000);

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


}
