package com.blackwolves.seeder;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.slf4j.Logger;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;

/**
 * @author danigrane
 *
 */
public class ModernYahooRunnable extends YahooRunnable {

	public ModernYahooRunnable(WebDriver driver, String seed, Human human, Logger logger) {
		super(driver, seed, human, logger);
	}
	
	@Override
	public void processInbox(String[] seed) {
		logger.info("Processing inbox");

		checkWelcomeDialog();
		validateOkayModal();

		if (validateInboxFolder()) {

			logger.info("There are msgs in the inbox folder, start working!!");
			List<WebElement> inboxMsgs = driver.findElements(By.className("list-view-item"));

			logger.info("Percentage is " + PERCENTAGE);
			int percentage = (int) (inboxMsgs.size() * PERCENTAGE);

			for (int j = 0; j < percentage; j++) {

				try {
//					if (throwDice()) {
//						sendEmail();
//					}

					logger.info((percentage - j) + " emails to go ");
					Thread.sleep(randInt(2500, 3500));

					if (driver.findElements(By.className("list-view-item")).size() > 0) {

						logger.info("list-view-item found");
						inboxMsgs = driver.findElements(By.className("list-view-item"));

						logger.info("Obtaining a random message position so it can be open");
						int randomPosition = obtainRandomMsgsPosition(inboxMsgs);

						Thread.sleep(randInt(2500, 3500));

						logger.info("Getting the random message");
						WebElement currentMsg = inboxMsgs.get(randomPosition);

//						if (isWarmupDomain(true, currentMsg)) {

							logger.info("Clicking in Msg : " + currentMsg.getText());
							currentMsg.findElement(By.className("subj")).click();

							clickShowImages("show-text");

//							if (throwDice()) {
//								replyToEmail();
//							} else if (throwDice()) {
//								forwardEmail();
//							} else if (throwDice()) {
//								clickRandomLink();
//							}

//							moveMessageToAllFolder();

							Thread.sleep(randInt(2500, 3500));
							logger.info("Going back to inbox");
							driver.findElement(By.className("inbox-label")).click();

							checkForInboxReloadError();
//						} else {
//							if (YahooRunnable.randInt(0, 1) == 1) {
//
//								logger.info("Clicking in Msg : " + currentMsg.getText());
//								currentMsg.findElement(By.className("subj")).click();
//
//								Thread.sleep(randInt(2500, 3500));
//
//								clickSpam();
//							}
//						}
					} else {
						logger.info("**********   No mlink found or no messages available   **********");
					}
				} catch (InterruptedException e) {
					logger.error("InterruptedException");
					driver.findElement(By.className("inbox-label")).click();
					checkForInboxReloadError();
				} catch (NoSuchElementException e) {
					logger.error("NoSuchElementException");
					driver.findElement(By.className("inbox-label")).click();
					checkForInboxReloadError();
				} catch (StaleElementReferenceException e) {
					logger.error("StaleElementReferenceException");
					driver.findElement(By.className("inbox-label")).click();
					checkForInboxReloadError();
				} catch (ElementNotVisibleException e) {
					logger.error("ElementNotVisibleException");
					driver.findElement(By.className("inbox-label")).click();
					checkForInboxReloadError();
				} catch (ElementNotFoundException e) {
					logger.error("ElementNotFoundException");
					driver.findElement(By.className("inbox-label")).click();
					checkForInboxReloadError();
				}
			}
		} else {
			logger.info("Inbox Folder is empty.");
		}

	}

	@Override
	public void replyToEmail() {
		try {
			logger.info("Clicking the reply button");
			Thread.sleep(randInt(1500, 2500));
			WebElement reply = driver.findElement(By.id("btn-reply-sender"));
			reply.click();

			Thread.sleep(randInt(1500, 2500));
			WebElement quickReply = driver.findElement(By.className("quickReply"));

			Thread.sleep(randInt(1500, 2500));
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			String body = human.generateRandomBody();
			jse.executeScript(
					"document.getElementById('rtetext').getElementsByTagName('p')[0].outerHTML = \" " + body + " \";");
			logger.info("Filling body field");
			Thread.sleep(randInt(1500, 2500));

			logger.info("Replying the email");
			Thread.sleep(randInt(1500, 2500));
			WebElement send = quickReply.findElement(By.className("bottomToolbar")).findElement(By.className("default"))
					.findElement(By.tagName("a"));
			send.click();
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
	public void forwardEmail() {
		try {
			logger.info("Clicking the forward button");
			Thread.sleep(randInt(2000, 3000));
			WebElement forward = driver.findElement(By.id("btn-forward"));
			forward.click();

			String to = human.generateRandomTo(seed);

			Thread.sleep(randInt(2000, 3000));
			WebElement quickReply = driver.findElement(By.className("quickReply"));

			Thread.sleep(randInt(2000, 3000));
			WebElement toInput = quickReply.findElement(By.id("to-field"));

			logger.info("Filling to field");
			Thread.sleep(randInt(2000, 3000));
			human.type(toInput, to);
			Thread.sleep(randInt(2000, 3000));
			toInput.sendKeys(Keys.TAB);

			logger.info("Forwarding the email");
			Thread.sleep(randInt(2000, 3000));
			WebElement send = quickReply.findElement(By.className("bottomToolbar")).findElement(By.className("default"))
					.findElement(By.tagName("a"));
			send.click();
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
	public void sendEmail() {
		try {
			logger.info("Clicking compose button");
			Thread.sleep(randInt(2000, 3000));
			WebElement compose = driver.findElement(By.className("btn-compose"));
			compose.click();

			String to = human.generateRandomTo(seed);

			Thread.sleep(randInt(2000, 3000));
			WebElement fullCompose = driver.findElement(By.className("full-compose"));

			Thread.sleep(randInt(1500, 2500));
			WebElement toInput = fullCompose.findElement(By.id("to-field"));
			logger.info("Filling to field");
			Thread.sleep(randInt(1500, 2500));
			human.type(toInput, to);
			Thread.sleep(randInt(1500, 2500));
			toInput.sendKeys(Keys.TAB);

			Thread.sleep(randInt(1500, 2500));
			WebElement subjectInput = fullCompose.findElement(By.id("subject-field"));
			logger.info("Filling subject field");
			Thread.sleep(randInt(1500, 2500));
			human.type(subjectInput, human.generateRandomSubject());
			Thread.sleep(randInt(1500, 2500));
			subjectInput.sendKeys(Keys.TAB);

			Thread.sleep(randInt(1500, 2500));
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			String body = human.generateRandomBody();
			jse.executeScript(
					"document.getElementById('rtetext').getElementsByTagName('p')[0].outerHTML = \" " + body + " \";");
			logger.info("Filling body field");
			Thread.sleep(randInt(1500, 2500));

			logger.info("Sending the email");
			Thread.sleep(randInt(1500, 2500));
			WebElement send = fullCompose.findElement(By.className("bottomToolbar"))
					.findElement(By.className("default")).findElement(By.tagName("a"));
			send.click();
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
	public void clickRandomLink() {
		try {
			logger.info("Getting the content of the message");
			Thread.sleep(YahooRunnable.randInt(2500, 3500));
			WebElement div = driver.findElement(By.className("thread-body"));
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
	public void moveMessageToAllFolder() {
		try {
			logger.info("Clicking move button");
			Thread.sleep(randInt(1000, 2000));
			driver.findElement(By.id("btn-move")).click();
			logger.info("Moving message to All folder");
			Thread.sleep(randInt(1500, 2500));
			driver.findElement(By.id("menu-move")).findElement(By.id("menu-move-folder")).findElement(By.tagName("li"))
					.click();
			logger.info("Message moved!!");
			Thread.sleep(randInt(2000, 3000));
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
	public void clickSpam() {
		try {
			List<WebElement> elements = driver.findElements(By.className("card-actions-menu"));
			for (WebElement ahref : elements) {
				if (ahref.isDisplayed()) {
					logger.info("Clicking more from submenu");
					jse.executeScript("arguments[0].scrollIntoView(true);", ahref);
					mouse.moveToElement(ahref);
					ahref.findElement(By.tagName("a")).click();
					List<WebElement> myList = driver.findElements(By.className("spamactions"));
					List<WebElement> submenuList = myList.get(0).findElements(By.tagName("li"));
					Thread.sleep(randInt(2000, 3000));
					WebElement spam = submenuList.get(0);
					logger.info("Clicking spam!");
					spam.click();
					Thread.sleep(randInt(2000, 3000));
				}
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

	private boolean isWarmupDomain(boolean inbox, WebElement msg) {
		String address = msg.findElement(By.className("flex")).findElement(By.className("from")).getAttribute("title");
		logger.info("Address from message is: " + address);
		String[] s = address.split("@");
		String domain = s.length > 1 ? s[1] : s[0];
		logger.info("Domain to validate: " + domain);
		List<String[]> domains = generateDomainsList();
		for (String[] d : domains) {
			if (domain.equals(d[0])) {
				logger.info("Is a warmup domain, we move forward :D");
				return true;
			}
		}
		if (inbox && (domain.endsWith("yahoo.com") || domain.endsWith(".ro"))) {
			logger.info("Is a yahoo or a .ro domain, we move forward :D");
			return true;
		}
		logger.info("Is not a warmup domain!!!");
		return false;
	}

	private void validateOkayModal() {
		List<WebElement> okayModals = driver.findElements(By.id("okayModalOverlay"));
		if (okayModals.size() > 0) {
			logger.info("OkayModal Found. Closing it");
			okayModals.get(0).findElement(By.tagName("a")).click();

		}
	}

	/**
	 * @param driver
	 */
	private void checkForInboxReloadError() {
		try {
			Thread.sleep(randInt(2000, 3000));
			if (driver.findElements(By.id("loadingpane")).size() > 0) {
				logger.info("loadingpane found!");
				Thread.sleep(randInt(2000, 3000));
				if (driver.findElement(By.id("loadingpane")).findElements(By.className("default")).size() > 0) {
					logger.info("refreshing the page");
					driver.navigate().refresh();
				}
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

	/**
	 * @param driver
	 * @param inboxFolder
	 * @return
	 */
	private boolean validateInboxFolder() {
		try {
			if (driver.findElements(By.className("inbox-label")).size() > 0) {
				WebElement inbox = driver.findElement(By.className("inbox-label"));
				inbox.click();
				Thread.sleep(randInt(2000, 3000));
				if (driver.findElements(By.className("list-view-item")).size() > 0) {
					return true;
				}
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
		return false;
	}

	private void checkWelcomeDialog() {
		int retries = 2;
		for (int i = 0; i < retries; i++) {
			try {
				if (driver.findElements(By.className("ob-contactimport-btn-skip")).size() > 0) {
					List<WebElement> dialogs = driver.findElements(By.className("ob-contactimport-btn-skip"));
					logger.info("Welcome Dialog found. Closing it.");
					WebElement welcomeDialog = dialogs.get(0);
					welcomeDialog.click();
				} else {
					logger.info("No Welcome Dialog was found.");
					Thread.sleep(randInt(2000, 3000));
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
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.blackwolves.selenium.seeder.YahooHandler#spamHandler(org.openqa.
	 * selenium.WebDriver, java.lang.String[])
	 */
	@Override
	public void processSpam(String[] seed) {
		logger.info("Processing Spam....");

		if (validateSpamFolder()) {
			logger.info("There are msgs in the spam folder, go get them Tiger!");

			List<WebElement> spamMsgs = driver.findElements(By.className("list-view-item"));

			logger.info("Percentage is " + PERCENTAGE);
			int percentage = (int) (spamMsgs.size() * PERCENTAGE);

			for (int j = 0; j < percentage;) {

				try {
					logger.info(j + " emails not spammed " + (percentage - j) + " emails to go");
					int chances = randInt(0, 10);
					boolean increment;
					Thread.sleep(randInt(2000, 3000));
					if (chances <= 7) {
						increment = normalNotSpam();
					} else {
						increment = dragAndDropNotSpam();
					}
					// if(increment){
					if (true) {
						j++;
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
		} else {
			logger.info("Spam Folder is empty! UOHOOO!");
		}
	}

	/**
	 * @param driver
	 * @param spamFolder
	 * @return
	 */
	private boolean validateSpamFolder() {
		try {
			if (driver.findElements(By.id("spam-label")).size() > 0) {
				WebElement spam = driver.findElement(By.id("spam-label"));
				spam.click();
				Thread.sleep(randInt(2000, 3000));
				if (driver.findElements(By.className("list-view-item")).size() > 0) {
					return true;
				}
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
		return false;
	}

	private boolean dragAndDropNotSpam() {
		try {
			List<WebElement> spamMsgs = driver.findElements(By.className("list-view-item"));
			int randomPosition = obtainRandomMsgsPosition(spamMsgs);
			Thread.sleep(randInt(2000, 3000));
			logger.info("Selecting spam message");
			WebElement msg = null;
			try {
				 msg = spamMsgs.get(randomPosition);
				 
			} catch (ArrayIndexOutOfBoundsException e) {
				logger.error("dragAndDropNotSpam: ArrayIndexOutOfBoundsException");
				return false;
			}
			
			if (isWarmupDomain(false, msg)) {
				WebElement inboxFolder = driver.findElement(By.className("inbox-label"));
				logger.info("******** Dragging Message to inbox ***********");
				(new Actions(driver)).dragAndDrop(msg, inboxFolder).perform();
		//		driver.navigate().refresh();
				return true;
			}
			return false;
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
		
		return false;
	}

	private boolean normalNotSpam() {
		try {
			List<WebElement> spamMsgs = driver.findElements(By.className("list-view-item"));
			int randomPosition = obtainRandomMsgsPosition(spamMsgs);
			logger.info("Obtaining a random message position so it can be open " + randomPosition);

			Thread.sleep(randInt(2000, 3000));

			WebElement currentMsg = spamMsgs.get(randomPosition);

			if (isWarmupDomain(false, currentMsg)) {
				logger.info("Opening the spam message");
				currentMsg.findElement(By.className("subj")).click();
				Thread.sleep(randInt(2000, 3000));

				clickShowImages("show-text");
				Thread.sleep(randInt(2000, 3000));
				// REMOVED CLICK FROM LIST SINCE IT IS BREAKING IN THE SERVER
				if (false) {
					logger.info("******** Clicking the not spam LIST button ***********");
					notSpamFromSubList();
				} else {
					logger.info("******** Clicking the not spam MAIN button ***********");
					driver.findElement(By.id("main-btn-spam")).click();
					Thread.sleep(randInt(2500, 3500));
				}
				return true;
			} else {
				return false;
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
		} catch (IndexOutOfBoundsException e) {
			logger.error(e.getMessage(), e);
		}
		driver.findElement(By.id("spam-label")).click();
		return false;
	}

	private void notSpamFromSubList() {
		List<WebElement> elements = driver.findElements(By.className("card-actions-menu"));
		logger.info("Clicking not spam from submenu");
		for (WebElement ahref : elements) {
			try {
				if (ahref.isDisplayed()) {
					logger.info("sublist is visible");
					jse.executeScript("arguments[0].scrollIntoView(true);", ahref);
					mouse.moveToElement(ahref);
					ahref.findElement(By.tagName("a")).click();
					List<WebElement> myList = driver.findElements(By.className("spamactions"));
					List<WebElement> submenuList = myList.get(0).findElements(By.tagName("li"));
					Thread.sleep(randInt(2000, 3000));
					WebElement notSpamMultare = submenuList.get(1);
					logger.info("Clicking Not Spam!");
					notSpamMultare.click();
					Thread.sleep(randInt(2000, 3000));
				} else {
					logger.info("sublist is not visible. Going back to bulk");
					driver.findElement(By.id("spam-label")).click();
					Thread.sleep(randInt(2000, 3000));
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
	}

	// NOT WORKING
	@Override
	public void replyToEmailFromSubList() {
		logger.info("Clicking the reply button from sublist");
		try {
			// String body = human.generateRandomBody(driver, wait);
			// Thread.sleep(randInt(2000, 3000));

			Thread.sleep(randInt(2000, 3000));
			List<WebElement> elements = driver.findElement(By.className("addconvtitle")).findElements(By.tagName("a"));
			WebElement reply = elements.get(2);
			reply.click();

			Thread.sleep(randInt(2000, 3000));
			WebElement quickReply = driver.findElement(By.className("quickReply"));

			Thread.sleep(randInt(2000, 3000));
			WebElement bodyMail = quickReply.findElement(By.id("rtetext"));
			bodyMail.click();
			// human.type(bodyMail, body);

			logger.info("Replying the email from sublist");
			Thread.sleep(randInt(2000, 3000));
			WebElement send = quickReply.findElement(By.className("bottomToolbar")).findElement(By.className("default"))
					.findElement(By.tagName("a"));
			send.click();
		} catch (InterruptedException e) {
			logger.error(e.getMessage(), e);
		} catch (NoSuchElementException e) {
			logger.error(e.getMessage(), e);
		} catch (StaleElementReferenceException e) {
			logger.error(e.getMessage(), e);
		} catch (ElementNotVisibleException e) {
			logger.error(e.getMessage(), e);
		} catch (ElementNotFoundException e) {
			logger.error(e.getMessage(), e);
		}
	}

	// NOT WORKING
	@Override
	public void forwardEmailFromSubList() {
		logger.info("Clicking the forward button from sublist");
		try {
			// String body = human.generateRandomBody(driver);
			// Thread.sleep(randInt(2000, 3000));

			Thread.sleep(randInt(2000, 3000));
			List<WebElement> elements = driver.findElement(By.className("addconvtitle")).findElements(By.tagName("a"));
			WebElement forward = elements.get(2);
			forward.click();

			String to = human.generateRandomTo(seed);

			Thread.sleep(randInt(2000, 3000));
			WebElement quickReply = driver.findElement(By.className("quickReply"));

			Thread.sleep(randInt(2000, 3000));
			WebElement toInput = quickReply.findElement(By.id("to-field"));

			logger.info("Filling to field");
			Thread.sleep(randInt(2000, 3000));
			human.type(toInput, to);
			Thread.sleep(randInt(2000, 3000));
			toInput.sendKeys(Keys.TAB);

			Thread.sleep(randInt(2000, 3000));
			WebElement bodyMail = quickReply.findElement(By.id("rtetext"));
			bodyMail.click();
			// human.type(bodyMail, body);

			logger.info("Forwarding the email from sublist");
			Thread.sleep(randInt(2000, 3000));
			WebElement send = quickReply.findElement(By.className("bottomToolbar")).findElement(By.className("default"))
					.findElement(By.tagName("a"));
			send.click();
		} catch (InterruptedException e) {
			logger.error(e.getMessage(), e);
		} catch (NoSuchElementException e) {
			logger.error(e.getMessage(), e);
		} catch (StaleElementReferenceException e) {
			logger.error(e.getMessage(), e);
		} catch (ElementNotVisibleException e) {
			logger.error(e.getMessage(), e);
		} catch (ElementNotFoundException e) {
			logger.error(e.getMessage(), e);
		}
	}

}
