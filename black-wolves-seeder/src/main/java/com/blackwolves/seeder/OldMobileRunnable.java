package com.blackwolves.seeder;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;

public class OldMobileRunnable extends YahooRunnable {

	public OldMobileRunnable(WebDriver driver, String seed, Human human, Logger logger) {
		super(driver, seed, human, logger);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void processInbox(String[] seed) {
		logger.info("Processing mobile inbox");
		if (validateInboxFolder()) {
			List<WebElement> msgs = driver.findElements(By.className("item"));
			if (msgs.size() > 0) {
				int msgsToProcess = YahooRunnable.randInt(1, msgs.size() <= 1 ? 1 : msgs.size() - 1);
				logger.info("Msgs to process :" + msgsToProcess);
				for (int i = 0; i < msgsToProcess; i++) {
					logger.info("Msg number :" + i);

					msgs = driver.findElements(By.className("item"));
					WebElement msg = (WebElement) msgs.get(YahooRunnable.randInt(0, msgs.size() - 1));

					logger.info("Subject : " + msg.findElement(By.xpath("//*[@class='subtext']/div")).getText());
					waitForIt(1000, 3000);
					msg.findElement(By.tagName("a")).click();
					viewHtml();
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
	}

	@Override
	public void processSpam(String[] seed) {
		logger.info("Processing mobile spam ");
		goToSpamFolder();
		List<WebElement> msgs = driver.findElements(By.className("item"));
		if (msgs.size() > 0) {
			int msgsToProcess = YahooRunnable.randInt(1, msgs.size() <= 1 ? 1 : msgs.size() - 1);
			logger.info("Msgs to process :" + msgsToProcess);

			for (int i = 0; i < msgsToProcess; i++) {
				logger.info("Msg number :" + i);

				msgs = driver.findElements(By.className("item"));
				WebElement msg = (WebElement) msgs.get(YahooRunnable.randInt(0, msgs.size() - 1));

				logger.info("Subject : " + msg.findElement(By.xpath("//*[@class='subtext']/div")).getText());
				waitForIt(1000, 3000);
				msg.findElement(By.tagName("a")).click();
				waitForIt(1000, 3000);

				viewHtml();
				scrollDownSlow(driver);
				waitForIt(1000, 3000);

				scrollUpSlow(driver);
				// Clicking in not spam
				// driver.findElements(By.xpath("//*[@class='uic']/a")).get(3).click();
				driver.findElement(By.xpath("//a[@accesskey='6']")).click();

				waitForIt(2000, 5000);
				goToSpamFolder();

			}
		}
		goToInboxFolder();
	}

	private void goToInboxFolder() {
		goToMenu();
		waitForIt(1000, 3000);
		driver.findElement(By.xpath("//*[@accesskey='0']")).click();
	}

	private void deleteEmailAfterRead() {
		logger.info("deleteEmailAfterRead()");

	}

	private void backToInboxArrow() {

		logger.info("backToInboxArrow()");
		driver.findElement(By.xpath("//*[@class='backButton']/a")).click();

	}

	private void viewHtml() {
		logger.info("viewHtml()");
		WebElement html = driver.findElement(By.xpath("//*[@class='uip']/a"));
		if (html != null) {
			logger.info("clicking html()");

			html.click();
			waitForIt(2000, 4000);
			logger.info("going back");

			driver.navigate().back();

		}
	}

	private boolean validateInboxFolder() {
		try {
			if (driver.findElements(By.className("item")).size() > 0) {
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

	public void goToSpamFolder() {
		goToMenu();
		waitForIt(1000, 3000);
		driver.findElement(By.xpath("//*[@class='uip'][5]/a")).click();
		;

	}

	private void goToMenu() {
		driver.findElement(By.xpath("//*[@class='imgL']/a")).click();
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
		// TODO Auto-generated method stub

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
