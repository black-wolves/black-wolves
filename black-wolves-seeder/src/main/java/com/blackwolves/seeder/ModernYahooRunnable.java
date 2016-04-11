package com.blackwolves.seeder;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.slf4j.Logger;

import com.blackwolves.seeder.util.Constant;
import com.blackwolves.seeder.util.JDBC;
import com.gargoylesoftware.htmlunit.ElementNotFoundException;

/**
 * 
 * @author gastondapice
 *
 */
public class ModernYahooRunnable extends YahooRunnable {

	boolean exception = false;

	public ModernYahooRunnable(WebDriver driver, Seed seed, Human human, Logger logger) {
		super(driver, seed, human, logger);
	}

	@Override
	public void processInbox(Seed seed) {
		logger.info("Processing inbox");
//		checkWelcomeDialog();
		removeConversationMailView();
		validateOkayModal();
		moveMouse();

		if (validateInboxFolder()) {

			logger.info("There are msgs in the inbox folder, start working!!");
			List<WebElement> inboxMsgs = driver.findElements(By.className("list-view-item"));

			logger.info("Percentage is " + PERCENTAGE);
			int percentage = (int) (inboxMsgs.size() * PERCENTAGE);
			boolean foundMyMsg = false;

			// This case is for mailboxes with less than 4 emails.
			if (inboxMsgs.size() > 0 && inboxMsgs.size() <= 4) {
				logger.info("Less than 4 emails in inbox.");
				percentage = inboxMsgs.size();
			}

			for (int j = 0; j < percentage; j++) {
				boolean opened = false;
				boolean clicked = false;
				boolean spam = false;
				WebElement currentMsg = null;
				try {
					if (exception) {
						exception = false;
						logger.info("There was an exception, we are going to inbox");
						driver.findElement(By.className("inbox-label")).click();
					}

					checkForInboxReloadError();

					if (driver.findElements(By.className("onboarding-notif-close-btn")).size() > 0) {
						List<WebElement> notifications = driver.findElements(By.className("onboarding-notif-close-btn"));
						WebElement dialog = (WebElement) notifications.get(0);
						dialog.click();
					}
					logger.info((percentage - j) + " emails to go ");
					Thread.sleep(randInt(2500, 3500));

					if (driver.findElements(By.className("list-view-item")).size() > 0) {
						logger.info("list-view-item found");
						inboxMsgs = driver.findElements(By.className("list-view-item"));
						currentMsg = null;
						// Looking for MyMessage
						if (findMyMessage() && !foundMyMsg) {
							currentMsg = findMessage(inboxMsgs, Constant.FROM.YAHOO_MAIL);
							if (currentMsg != null) {
								logger.info(" #########  MAIL "+Constant.FROM.YAHOO_MAIL +" FOUND AT INBOX ##############");
								foundMyMsg = true;
							}

						}
						// Or a SpamMsg to give the seed reputation. 0.02
						// chances
						else if (findSpamMessage()) {
							currentMsg = findMessage(inboxMsgs, Constant.FROM.SPAM);
							spam = true;
						}
						// Or just any Msg
						else {
							int randomPosition = obtainRandomMsgsPosition(inboxMsgs);

							logger.info("Getting the random message");
							currentMsg = inboxMsgs.get(randomPosition);
						}

						if (currentMsg != null) {
							WebElement from = currentMsg.findElement(By.className("from"));
							final String fromText = from.getText();
							logger.info("$$$$$$$$$$ Opening Message from: " + fromText);
							if (isClickable(driver, currentMsg)) {
								logger.info("Will click at  X: " + currentMsg.getLocation().getX() + " and Y:" + currentMsg.getLocation().getY());
								currentMsg.click();
								if (fromText.contains(Constant.FROM.YAHOO_MAIL)) {
									opened = true;
									if (Math.random() <= 0.6) {
										clickShowImages("show-text");
										clicked = clickRandomLink();
									}
								} else if (spam) {
									opened = true;
									spam = true;
									logger.info("Marking Msg as Spam And refreshing page");
									clickSpam();
									driver.navigate().refresh();
								}

								else if (!opened && Math.random() <= 0.2) {
									clickShowImages("show-text");
									clickRandomLink();
								}
								// scrollToBottom(driver);
								Thread.sleep(randInt(2500, 3500));

								if (opened) {
									JDBC.updateSeed(seed.getUser(), 1, clicked ? 1 : 0, spam ? 1 : 0, 0, true);
								}

								archiveMsg();

								logger.info("Going back to inbox");
								driver.findElement(By.className("inbox-label")).click();
							} else {
								logger.info("Msg is not clickable. Refreshing Page");
							}
						}
					} else {
						logger.info("**********   Element is not clickable, Refreshing page.   **********");
					}
				} catch (InterruptedException e) {
					logger.error("InterruptedException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " ", e);
					exception = true;
					continue;
				} catch (NoSuchElementException e) {
					logger.error("NoSuchElementException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " ", e);
					exception = true;
					continue;
				} catch (StaleElementReferenceException e) {
					logger.error("StaleElementReferenceException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " ", e);
					exception = true;
					continue;
				} catch (ElementNotVisibleException e) {
					logger.error("ElementNotVisibleException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " ", e);
					exception = true;
					continue;
				} catch (ElementNotFoundException e) {
					logger.error("ElementNotFoundException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " ", e);
					exception = true;
					continue;
				} catch (UnhandledAlertException e) {
					logger.error("UnhandledAlertException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " ", e);
					exception = true;
					continue;
				} catch (WebDriverException e) {
					logger.error("WebDriverException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " ", e);
					exception = true;
					continue;
				}
			}
		} else {
			logger.info("Inbox Folder is empty.");
		}

	}

	public void processSpam(Seed seed) {
		logger.info("Processing spam....");
		try {
			logger.info("Process Spam");
			if (validateSpamFolder()) {
				if (normalNotSpam()) {
					logger.info("Msg found adding to NOT_SPAM");
					JDBC.updateSeed(seed.getUser(), 1, 0, 0, 1, false);
				} else {
					logger.info("Msg not found in SPAM.");
				}
			}
		}catch (NoSuchElementException e) {
			logger.error("NoSuchElementException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (StaleElementReferenceException e) {
			logger.error("StaleElementReferenceException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (ElementNotVisibleException e) {
			logger.error("ElementNotVisibleException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (ElementNotFoundException e) {
			logger.error("ElementNotFoundException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (UnhandledAlertException e) {
			logger.error("UnhandledAlertException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (WebDriverException e) {
			logger.error("WebDriverException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " ", e);
		}
	}

	/**
	 * @param driver
	 * @param spamFolder
	 * @return
	 */
	public boolean validateSpamFolder() {
		logger.info("Entering validateSpamFolder ");
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
			logger.error("InterruptedException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (NoSuchElementException e) {
			logger.error("NoSuchElementException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (StaleElementReferenceException e) {
			logger.error("StaleElementReferenceException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (ElementNotVisibleException e) {
			logger.error("ElementNotVisibleException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (ElementNotFoundException e) {
			logger.error("ElementNotFoundException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (UnhandledAlertException e) {
			logger.error("UnhandledAlertException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (WebDriverException e) {
			logger.error("WebDriverException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " ", e);
		}
		return false;
	}

	public boolean normalNotSpam() {
		try {
			List<WebElement> spamMsgs = driver.findElements(By.className("list-view-item"));

			Thread.sleep(randInt(2000, 3000));

			WebElement currentMsg = findMessage(spamMsgs, Constant.FROM.YAHOO_MAIL);
			if (currentMsg != null) {

				logger.info("Opening the spam message");
				currentMsg.findElement(By.className("subj")).click();

				Thread.sleep(randInt(1000, 2000));

				clickShowImages("show-text");
				Thread.sleep(randInt(1000, 2000));
				scrollToBottom(driver);
				// REMOVED CLICK FROM LIST SINCE IT IS BREAKING IN THE SERVER
				logger.info("******** Clicking the not spam MAIN button ***********");
				driver.findElement(By.id("main-btn-spam")).click();
				Thread.sleep(randInt(2500, 3500));
				return true;
			}
		} catch (InterruptedException e) {
			logger.error("InterruptedException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (NoSuchElementException e) {
			logger.error("NoSuchElementException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (StaleElementReferenceException e) {
			logger.error("StaleElementReferenceException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (ElementNotVisibleException e) {
			logger.error("ElementNotVisibleException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (ElementNotFoundException e) {
			logger.error("ElementNotFoundException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (UnhandledAlertException e) {
			logger.error("UnhandledAlertException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (WebDriverException e) {
			logger.error("WebDriverException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " ", e);
		}
		driver.findElement(By.id("spam-label")).click();
		return false;
	}

	private boolean findMyMessage() {
		if (Math.random() <= 0.3) {
			return true;
		}
		return false;
	}

	private boolean findSpamMessage() {
		if (Math.random() <= 0.1) {
			return true;
		}
		return false;
	}

	private void archiveMsg() {
		if (Math.random() <= 0.5) {
			WebElement archive = driver.findElement(By.id("btn-archive"));
			if (archive != null && archive.isDisplayed()) {
				logger.info("Archiving Msg");
				archive.click();
			}
		}
	}

	@Override
	public boolean clickRandomLink() {
		boolean clicked = false;
		try {
			Thread.sleep(YahooRunnable.randInt(2500, 3500));
			WebElement div = driver.findElement(By.className("email-wrapped"));
			logger.info("Looking for links inside the message");
			Thread.sleep(YahooRunnable.randInt(1500, 2500));
			if (div.findElements(By.tagName("a")).size() > 0) {
				logger.info("Links found");
				List<WebElement> linksToGo = div.findElements(By.tagName("a"));
				if (!linksToGo.isEmpty()) {
					boolean keepGoing = false;
					int count = 0;
					do {
						WebElement link = linksToGo.get(count);
						if (link != null && link.isDisplayed()) {
							String url = link.getAttribute("href");
							if (url.contains("unsub") || url.contains("subsc")) {
								logger.info("It is an Unsubscribe link!! - we are not clicking it" + url);
							} else {
								logger.info("It's a good link, click it!! " + url);
								link.click();
								switchToNewWindow();
								switchToPreviousWindow();
								clicked = true;
								keepGoing = false;
							}
						} else {
							if (count < linksToGo.size() - 1) {
								keepGoing = true;
								count++;
							} else {
								keepGoing = false;
							}
						}
					} while (keepGoing);
				}
			} else {
				logger.info("**********   No links found or none available  **********");
			}
		} catch (InterruptedException e) {
			logger.error("InterruptedException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (NoSuchElementException e) {
			logger.error("NoSuchElementException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (StaleElementReferenceException e) {
			logger.error("StaleElementReferenceException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (ElementNotVisibleException e) {
			logger.error("ElementNotVisibleException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (ElementNotFoundException e) {
			logger.error("ElementNotFoundException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (UnhandledAlertException e) {
			logger.error("UnhandledAlertException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (WebDriverException e) {
			logger.error("WebDriverException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " ", e);
		}
		return clicked;
	}

	@Override
	public void clickSpam() {
		try {
			List<WebElement> elements = driver.findElements(By.className("card-actions-menu"));
			for (WebElement ahref : elements) {
				if (ahref.isDisplayed()) {
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
			logger.error("InterruptedException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (NoSuchElementException e) {
			logger.error("NoSuchElementException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (StaleElementReferenceException e) {
			logger.error("StaleElementReferenceException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (ElementNotVisibleException e) {
			logger.error("ElementNotVisibleException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (ElementNotFoundException e) {
			logger.error("ElementNotFoundException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (UnhandledAlertException e) {
			logger.error("UnhandledAlertException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (WebDriverException e) {
			logger.error("WebDriverException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " ", e);
		}
	}

	private void validateOkayModal() {
		List<WebElement> okayModals = driver.findElements(By.id("okayModalOverlay"));
		if (okayModals.size() > 0) {
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
				Thread.sleep(randInt(2000, 3000));
				if (driver.findElement(By.id("loadingpane")).findElements(By.className("default")).size() > 0) {
					driver.navigate().refresh();
				}
			}
		} catch (InterruptedException e) {
			logger.error("InterruptedException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (NoSuchElementException e) {
			logger.error("NoSuchElementException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (StaleElementReferenceException e) {
			logger.error("StaleElementReferenceException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (ElementNotVisibleException e) {
			logger.error("ElementNotVisibleException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (ElementNotFoundException e) {
			logger.error("ElementNotFoundException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (UnhandledAlertException e) {
			logger.error("UnhandledAlertException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (WebDriverException e) {
			logger.error("WebDriverException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " ", e);
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
			logger.error("InterruptedException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (NoSuchElementException e) {
			logger.error("NoSuchElementException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (StaleElementReferenceException e) {
			logger.error("StaleElementReferenceException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (ElementNotVisibleException e) {
			logger.error("ElementNotVisibleException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (ElementNotFoundException e) {
			logger.error("ElementNotFoundException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (UnhandledAlertException e) {
			logger.error("UnhandledAlertException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (WebDriverException e) {
			logger.error("WebDriverException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " ", e);
		}
		return false;
	}

//	private void checkWelcomeDialog() {
//		int retries = 2;
//		for (int i = 0; i < retries; i++) {
//			try {
//				if (driver.findElements(By.className("ob-contactimport-btn-skip")).size() > 0) {
//					List<WebElement> dialogs = driver.findElements(By.className("ob-contactimport-btn-skip"));
//					WebElement welcomeDialog = dialogs.get(0);
//					welcomeDialog.click();
//				}
//			} catch (NoSuchElementException e) {
//				logger.error("NoSuchElementException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " ", e);
//			} catch (StaleElementReferenceException e) {
//				logger.error("StaleElementReferenceException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " ", e);
//			} catch (ElementNotVisibleException e) {
//				logger.error("ElementNotVisibleException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " ", e);
//			} catch (ElementNotFoundException e) {
//				logger.error("ElementNotFoundException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " ", e);
//			} catch (UnhandledAlertException e) {
//				logger.error("UnhandledAlertException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " ", e);
//			} catch (WebDriverException e) {
//				logger.error("WebDriverException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " ", e);
//			}
//		}
//	}
	
	private void removeConversationMailView() {

		try {
			Actions myMouse = new Actions(driver);
			WebElement settings = driver.findElement(By.id("yucs-help"));
			myMouse.moveToElement(settings).build().perform();
			Thread.sleep(randInt(1000, 2000));
			settings = driver.findElement(By.id("yucs-help"));
			myMouse.moveToElement(settings).build().perform();
			Thread.sleep(randInt(1000, 2000));
			logger.info("Moving to configuration wheel");
			Thread.sleep(randInt(1000, 2000));
			if (driver.findElements(By.xpath("//div[@id='yucs-help_inner']")).size() > 0) {
				settings = driver.findElement(By.id("yucs-help"));
				myMouse.moveToElement(settings).build().perform();
				Thread.sleep(randInt(1000, 2000));
				driver.findElement(By.xpath("//div[@id='yucs-help_inner']/ul/li[2]/a")).click();
				Thread.sleep(randInt(1000, 2000));
				if (driver.findElement(By.xpath("//input[@id='options-enableConv']")).isSelected()) {
					logger.info("Conversation mode is on. Turning off.");
					driver.findElement(By.xpath("//input[@id='options-enableConv']")).click();
					Thread.sleep(randInt(1000, 2000));
					if(driver.findElements(By.className("selectable")).size() > 0){
						driver.findElement(By.xpath("//ul[@class='selectable']/li[6]/a")).click();
						Thread.sleep(randInt(1000, 2000));
						driver.findElement(By.xpath("//ul[@class='options-settings-pane']/li/div[2]/div/select/option[2]")).click();
						Thread.sleep(randInt(1000, 2000));
					}
				}else if(driver.findElements(By.className("selectable")).size() > 0){
					driver.findElement(By.xpath("//ul[@class='selectable']/li[6]/a")).click();
					Thread.sleep(randInt(1000, 2000));
					driver.findElement(By.xpath("//ul[@class='options-settings-pane']/li/div[2]/div/select/option[2]")).click();
					Thread.sleep(randInt(1000, 2000));
				}
				driver.findElement(By.xpath("//button[@class='left right default btn']")).click();
				Thread.sleep(randInt(1000, 2000));
			}
		} catch (InterruptedException e) {
			logger.error("InterruptedException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (NoSuchElementException e) {
			logger.error("NoSuchElementException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (StaleElementReferenceException e) {
			logger.error("StaleElementReferenceException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (ElementNotVisibleException e) {
			logger.error("ElementNotVisibleException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (ElementNotFoundException e) {
			logger.error("ElementNotFoundException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (UnhandledAlertException e) {
			logger.error("UnhandledAlertException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (WebDriverException e) {
			logger.error("WebDriverException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " ", e);
		}
	}


	public void moveMouse() {
		try {
			logger.info("Enters to move mouse");
			// Build webElement object to contain menu link xpath for all the
			// mainmenu links
			// WebElement HomeLink =
			// driver.findElement(By.xpath("//*[@id='globalNav']/ul/li[1]/a"));
			// WebElement ShowsLink =
			// driver.findElement(By.xpath("//*[@id='globalNav']/ul/li[2]/a"));
			// WebElement MusicLink =
			// driver.findElement(By.xpath("//*[@id='globalNav']/ul/li[3]/a"));
			// WebElement EventsLink =
			// driver.findElement(By.xpath("//*[@id='globalNav']/ul/li[6]/a"));

			// Create an action object called myMouse
			Actions myMouse = new Actions(driver);

			// there is a slight delay before each mouse movement hence the
			// "Thread.sleep" statement
			if (throwDice()) {
				WebElement yucsHelp = driver.findElement(By.id("yucs-help"));
				myMouse.moveToElement(yucsHelp).build().perform();
				logger.info("Moving to configuration wheel");
				Thread.sleep(randInt(500, 1500));
			}
			if (throwDice()) {
				WebElement inboxLabel = driver.findElement(By.className("inbox-label"));
				myMouse.moveToElement(inboxLabel).build().perform();
				logger.info("Moving to inbox");
				Thread.sleep(randInt(500, 1500));
			}
			if (throwDice()) {
				WebElement multitask = driver.findElement(By.id("multitask"));
				myMouse.moveToElement(multitask).build().perform();
				logger.info("Moving to multitask");
				Thread.sleep(randInt(500, 1500));
			}
			if (throwDice()) {
				WebElement spamLabel = driver.findElement(By.id("spam-label"));
				myMouse.moveToElement(spamLabel).build().perform();
				logger.info("Moving to spam");
				Thread.sleep(randInt(500, 1500));
			}
			if (throwDice()) {
				WebElement yucsHomeLink = driver.findElement(By.id("yucs-home_link"));
				myMouse.moveToElement(yucsHomeLink).build().perform();
				logger.info("Moving to Home");
				Thread.sleep(randInt(500, 1500));
			}
			if (throwDice()) {
				WebElement typeaheadInput = driver.findElement(By.className("typeahead-input"));
				myMouse.moveToElement(typeaheadInput).build().perform();
				logger.info("Moving to search box");
				Thread.sleep(randInt(500, 1500));
			}
			if (throwDice()) {
				WebElement uhWrapper = driver.findElement(By.xpath("//*[@id='uhWrapper']/table/tbody/tr/td/a"));
				myMouse.moveToElement(uhWrapper).build().perform();
				logger.info("Moving to yahoo icon");
				Thread.sleep(randInt(500, 1500));
			}
			if (throwDice()) {
				WebElement yucsProfile = driver.findElement(By.id("yucs-profile"));
				myMouse.moveToElement(yucsProfile).build().perform();
				logger.info("Moving to Profile");
				Thread.sleep(randInt(500, 1500));
			}
			if (throwDice()) {
				WebElement smartviews = driver.findElement(By.id("smartviews"));
				myMouse.moveToElement(smartviews).build().perform();
				logger.info("Moving to smartviews");
				Thread.sleep(randInt(500, 1500));
			}
			if (throwDice()) {
				WebElement trash = driver.findElement(By.className("trash"));
				myMouse.moveToElement(trash).build().perform();
				logger.info("Moving to trash");
				Thread.sleep(randInt(500, 1500));
			}
			if (throwDice()) {
				WebElement sent = driver.findElement(By.className("sent"));
				myMouse.moveToElement(sent).build().perform();
				logger.info("Moving to sent");
				Thread.sleep(randInt(500, 1500));
			}
			if (throwDice()) {
				WebElement drafts = driver.findElement(By.className("drafts"));
				myMouse.moveToElement(drafts).build().perform();
				logger.info("Moving to drafts");
				Thread.sleep(randInt(500, 1500));
			}
			if (throwDice()) {
				WebElement compose = driver.findElement(By.id("Compose"));
				myMouse.moveToElement(compose).build().perform();
				logger.info("Moving to compose");
				Thread.sleep(randInt(500, 1500));
			}
			if (throwDice()) {
				WebElement mailSearchBtn = driver.findElement(By.id("mail-search-btn"));
				myMouse.moveToElement(mailSearchBtn).build().perform();
				logger.info("Moving to mailSearchBtn");
				Thread.sleep(randInt(500, 1500));
			}
			if (throwDice()) {
				WebElement webSearchBtn = driver.findElement(By.id("web-search-btn"));
				myMouse.moveToElement(webSearchBtn).build().perform();
				logger.info("Moving to webSearchBtn");
				Thread.sleep(randInt(500, 1500));
			}
		} catch (InterruptedException e) {
			logger.error("InterruptedException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (NoSuchElementException e) {
			logger.error("NoSuchElementException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (StaleElementReferenceException e) {
			logger.error("StaleElementReferenceException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (ElementNotVisibleException e) {
			logger.error("ElementNotVisibleException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (ElementNotFoundException e) {
			logger.error("ElementNotFoundException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (UnhandledAlertException e) {
			logger.error("UnhandledAlertException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (WebDriverException e) {
			logger.error("WebDriverException for seed: " + seed.getUser() + " with password: " + seed.getPassword() + " " + e.getMessage() + " ", e);
		}
	}

	public static void scrollToBottom(WebDriver driver) {
		((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}

	private WebElement findMessage(List<WebElement> inboxMsgs, String msgfrom) {
		logger.info("Finding message " + msgfrom);
		for (WebElement webElement : inboxMsgs) {
			WebElement from = webElement.findElement(By.className("from"));
			String fromText = from.getText();
			if (fromText.contains(msgfrom)) {
				return webElement;
			}
		}
		return null;
	}

}
