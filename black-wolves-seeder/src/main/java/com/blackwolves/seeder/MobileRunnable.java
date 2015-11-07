package com.blackwolves.seeder;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;

public class MobileRunnable extends YahooRunnable {

	public MobileRunnable(WebDriver driver, String seed, Human human, Logger logger) {
		super(driver, seed, human, logger);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void processInbox(String[] seed) {
		logger.info("Processing mobile inbox");
		if (validateInboxFolder()) {
			List<WebElement> msgs = driver.findElements(By.className("mlink"));
			int msgsToProcess = YahooRunnable.randInt(1, msgs.size() - 1);
			logger.info("Msgs to process :" + msgsToProcess);
			for (int i = 0; i < msgsToProcess; i++) {
				logger.info("Msg number :" + i);

			//	msgs = driver.findElements(By.className("mlink"));
				WebElement msg = (WebElement) msgs.get(YahooRunnable.randInt(0, msgs.size() - 1));
				logger.info("Subject : "+msg.findElement(By.xpath("//*[@class='subject']")).getText());
				waitForIt(1000, 3000);
				msg.click();
				viewHtml();
				waitForIt(1000, 10000);
				scrollDownSlow(driver);
				scrollUpSlow(driver);
				clickRandomLink();
				if (throwDice()) {

					backToInboxArrow();
				} else
					deleteEmailAfterRead();

			}
		}

	}

	private void deleteEmailAfterRead() {
		logger.info("deleteEmailAfterRead()");
		driver.findElement(By.xpath("//*[@id='bottom_delete']")).click();
	}

	private void viewHtml() {
		logger.info("viewHtml()");
		WebElement html = driver.findElement(By.xpath("//*[@class='viewhtml']/a"));
		if (html != null) {
			logger.info("clicking html()");

			html.click();
		}
	}

	private void backToInboxArrow() {
		logger.info("backToInboxArrow()");
		driver.findElement(By.xpath("//*[@class='left']/a")).click();

	}

	@Override
	public void processSpam(String[] seed) {
		logger.info("Processing mobile spam");
		goToSpamFolder();
		List<WebElement> msgs = driver.findElements(By.className("mlink"));
		logger.info("Msgs to process :" + msgs.size());
		int msgsToProcess = YahooRunnable.randInt(1, msgs.size() - 1);
		for (int i = 0; i < msgsToProcess; i++) {
			logger.info("Msg number :" + i);
//			msgs = driver.findElements(By.className("mlink"));
			WebElement msg = (WebElement) msgs.get(YahooRunnable.randInt(1, msgs.size() - 1));
			logger.info("Subject : "+msg.findElement(By.xpath("//*[@class='subject']")).getText());

			msg.click();
			viewHtml();
			scrollDownSlow(driver);
			// Clicking in not spam
			driver.findElement(By.xpath("//*[@id='bottom_ham']")).click();
		}
		goToInboxFolder();
	}

	public void goToInboxFolder() {
		goToMenu();
		waitForIt(1000, 3000);
		driver.findElement(By.xpath("//*[@id='inbox']/a")).click();

	}

	public void goToSpamFolder() {
		goToMenu();
		waitForIt(1000, 3000);
		driver.findElement(By.xpath("//*[@id='bulk']/a")).click();

	}

	private void goToMenu() {
		List<WebElement> menu = driver.findElements(By.className("uh-bread"));
		WebElement span = (WebElement) menu.get(0);
		WebElement a = span.findElement(By.tagName("a"));
		a.click();
	}

	private boolean validateInboxFolder() {
		try {
			if (driver.findElements(By.className("msgListItem")).size() > 0) {
				return true;
			}
		} catch (NoSuchElementException e) {
			logger.error("NoSuchelementException");
		} catch (StaleElementReferenceException e) {
			logger.error("StaleElementReferenceException");
		} catch (ElementNotVisibleException e) {
			logger.error("ElementNotVisibleException");
		} catch (ElementNotFoundException e) {
			logger.error("ElementNotFoundException");
		}
		return false;
	}

	@Override
	public void replyToEmail() {
		// TODO Auto-generated method stub

	}

	@Override
	public void forwardEmail() {
		// TODO Auto-generated method stub

	}

	@Override
	public void sendEmail() {
		// TODO Auto-generated method stub

	}

	@Override
	public void moveMessageToAllFolder() {
		// TODO Auto-generated method stub

	}

	@Override
	public void clickSpam() {
		// TODO Auto-generated method stub

	}

	@Override
	public void clickRandomLink() {
		try {
			Thread.sleep(YahooRunnable.randInt(2500, 3500));
			WebElement div = driver.findElement(By.className("messagebody"));
			logger.info("Looking for links inside the message");
			Thread.sleep(YahooRunnable.randInt(1500, 2500));
			if (div.findElements(By.tagName("a")).size() > 0) {
				logger.info("Links found");
				List<WebElement> linksToGo = div.findElements(By.tagName("a"));
				int randomLinkNo = randInt(0, linksToGo.size() - 1);
				String aUrl = linksToGo.get(randomLinkNo).getAttribute("href");
				if (aUrl != null) {
					if (aUrl.contains("unsub") || aUrl.contains("yahoo")) {
						logger.info("It is an Unsubscribe link!! - we are not clicking it" + aUrl);
					} else {
						logger.info("It's a good link, click it!! " + aUrl);
						openTab(aUrl);
						switchToNewWindow();
						switchToPreviousWindow();

					}
				}
			} else {
				logger.info("**********   No links found or none available  **********");
			}
		} catch (InterruptedException e) {
			logger.error(e.getMessage(), e);
		} catch (NoSuchElementException e) {
			logger.error("NoSuchelementException");
		} catch (StaleElementReferenceException e) {
			logger.error("StaleElementReferenceException");
		} catch (ElementNotVisibleException e) {
			logger.error("ElementNotVisibleException");
		} catch (ElementNotFoundException e) {
			logger.error("ElementNotFoundException");
		}
	}

	@Override
	public void replyToEmailFromSubList() {
		// TODO Auto-generated method stub

	}

	@Override
	public void forwardEmailFromSubList() {
		// TODO Auto-generated method stub

	}

}
