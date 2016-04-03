package com.blackwolves.seeder;

import java.util.ArrayList;
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

		checkWelcomeDialog();
		validateOkayModal();
		moveMouse();

		if (validateInboxFolder()) {

			logger.info("There are msgs in the inbox folder, start working!!");
			List<WebElement> inboxMsgs = driver.findElements(By.className("list-view-item"));

			logger.info("Percentage is " + PERCENTAGE);
			int percentage = (int) (inboxMsgs.size() * PERCENTAGE);
			boolean foundMyMsg = false;
			for (int j = 0; j < percentage; j++) {
				boolean opened = false;
				boolean clicked = false;
				boolean spam = false;


				try {
					if (exception) {
						exception = false;
						logger.info("There was an exception, we are going to inbox");
						driver.findElement(By.className("inbox-label")).click();
					}

					checkForInboxReloadError();

					if (driver.findElements(By.className("onboarding-notif-close-btn")).size() > 0) {
						List<WebElement> notifications = driver
								.findElements(By.className("onboarding-notif-close-btn"));
						WebElement dialog = (WebElement) notifications.get(0);
						dialog.click();
					}
					logger.info((percentage - j) + " emails to go ");
					Thread.sleep(randInt(2500, 3500));

					if (driver.findElements(By.className("list-view-item")).size() > 0) {

						logger.info("list-view-item found");
						inboxMsgs = driver.findElements(By.className("list-view-item"));
						WebElement currentMsg = null;
						//Looking for MyMessage
						if (findMyMessage() && !foundMyMsg) {
							currentMsg = findMessage(inboxMsgs,Constant.FROM.ENTREPRENEUR);
							foundMyMsg = true;
						} 
						//Or a SpamMsg to give the seed reputation. 0.02 chances
						else if(findSpamMessage()){
							currentMsg =  findMessage(inboxMsgs, Constant.FROM.SPAM);
							spam =true;
						}
						//Or just any Msg
						else {
							logger.info("Obtaining a random message position so it can be open");
							int randomPosition = obtainRandomMsgsPosition(inboxMsgs);

							logger.info("Getting the random message");
							currentMsg = inboxMsgs.get(randomPosition);
						}
						if (currentMsg != null) {

							WebElement from = currentMsg.findElement(By.className("from"));
							WebElement subject = currentMsg.findElement(By.className("subj"));
							String fromText = from.getText();
							String subjectText = subject.getText();
							logger.info("$$$$$$$$$$ Opening Message from: " + fromText + " Subject: " + subjectText);
							currentMsg.click();

							if (Constant.FROM.ENTREPRENEUR.equals(fromText)) {
								opened = true;
								if (Math.random() <= 0.6) {
									clickShowImages("show-text");
									clicked = clickRandomLink();
								}
							} else if (spam) {
								opened = true;
								spam = true;
								logger.info("Marking Msg as Spam");
								clickSpam();
							} 
							
							else if (!opened && Math.random() <= 0.25) {
								clickShowImages("show-text");
								clickRandomLink();
							}
							scrollToBottom(driver);
							Thread.sleep(randInt(2500, 3500));

							if (opened) {
								logger.info("Saving message stats into database");
								JDBC.updateSeed(seed.getUser(), 1, clicked ? 1 : 0, spam?1:0);
							}

							archiveMsg();

							logger.info("Going back to inbox");
							driver.findElement(By.className("inbox-label")).click();
						}
					} else {
						logger.info("**********   No mlink found or no messages available   **********");
					}

				} catch (InterruptedException e) {
					logger.error("InterruptedException for seed: " + seed.getUser() + " with password: "
							+ seed.getPassword() + " " + e.getMessage() + " ", e);
					exception = true;
					continue;
				} catch (NoSuchElementException e) {
					logger.error("NoSuchElementException for seed: " + seed.getUser() + " with password: "
							+ seed.getPassword() + " " + e.getMessage() + " ", e);
					exception = true;
					continue;
				} catch (StaleElementReferenceException e) {
					logger.error("StaleElementReferenceException for seed: " + seed.getUser() + " with password: "
							+ seed.getPassword() + " " + e.getMessage() + " ", e);
					exception = true;
					continue;
				} catch (ElementNotVisibleException e) {
					logger.error("ElementNotVisibleException for seed: " + seed.getUser() + " with password: "
							+ seed.getPassword() + " " + e.getMessage() + " ", e);
					exception = true;
					continue;
				} catch (ElementNotFoundException e) {
					logger.error("ElementNotFoundException for seed: " + seed.getUser() + " with password: "
							+ seed.getPassword() + " " + e.getMessage() + " ", e);
					exception = true;
					continue;
				} catch (UnhandledAlertException e) {
					logger.error("UnhandledAlertException for seed: " + seed.getUser() + " with password: "
							+ seed.getPassword() + " " + e.getMessage() + " ", e);
					exception = true;
					continue;
				} catch (WebDriverException e) {
					logger.error("WebDriverException for seed: " + seed.getUser() + " with password: "
							+ seed.getPassword() + " " + e.getMessage() + " ", e);
					exception = true;
					continue;
				}
			}
		} else {
			logger.info("Inbox Folder is empty.");
		}

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
			} else {
				logger.info("Archiving is not displayed");
			}
		}
	}

	@Override
	public boolean clickRandomLink() {
		boolean clicked = false;
		try {
			logger.info("Getting the content of the message");
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
//						logger.info("Link Displayed: " + link.isDisplayed());
//						logger.info("Link Enabled: " + link.isEnabled());
//						logger.info("Link Selected: " + link.isSelected());
						if (link != null && link.isDisplayed()) {
							String url = link.getAttribute("href");
							if (url.contains("unsub") || url.contains("yahoo") || url.contains("subsc")) {
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
							if (count < linksToGo.size()-1) {
								keepGoing = true;
								count++;
							}
							else {
								keepGoing =  false;
							}
						}
					} while (keepGoing);
				}
			} else {
				logger.info("**********   No links found or none available  **********");
			}
		} catch (InterruptedException e) {
			logger.error("InterruptedException for seed: " + seed.getUser() + " with password: " + seed.getPassword()
					+ " " + e.getMessage() + " ", e);
		} catch (NoSuchElementException e) {
			logger.error("NoSuchElementException for seed: " + seed.getUser() + " with password: " + seed.getPassword()
					+ " " + e.getMessage() + " ", e);
		} catch (StaleElementReferenceException e) {
			logger.error("StaleElementReferenceException for seed: " + seed.getUser() + " with password: "
					+ seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (ElementNotVisibleException e) {
			logger.error("ElementNotVisibleException for seed: " + seed.getUser() + " with password: "
					+ seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (ElementNotFoundException e) {
			logger.error("ElementNotFoundException for seed: " + seed.getUser() + " with password: "
					+ seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (UnhandledAlertException e) {
			logger.error("UnhandledAlertException for seed: " + seed.getUser() + " with password: " + seed.getPassword()
					+ " " + e.getMessage() + " ", e);

		} catch (WebDriverException e) {
			logger.error("WebDriverException for seed: " + seed.getUser() + " with password: " + seed.getPassword()
					+ " " + e.getMessage() + " ", e);
		}
		return clicked;
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
			logger.error("InterruptedException for seed: " + seed.getUser() + " with password: " + seed.getPassword()
					+ " " + e.getMessage() + " ", e);
		} catch (NoSuchElementException e) {
			logger.error("NoSuchElementException for seed: " + seed.getUser() + " with password: " + seed.getPassword()
					+ " " + e.getMessage() + " ", e);
		} catch (StaleElementReferenceException e) {
			logger.error("StaleElementReferenceException for seed: " + seed.getUser() + " with password: "
					+ seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (ElementNotVisibleException e) {
			logger.error("ElementNotVisibleException for seed: " + seed.getUser() + " with password: "
					+ seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (ElementNotFoundException e) {
			logger.error("ElementNotFoundException for seed: " + seed.getUser() + " with password: "
					+ seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (UnhandledAlertException e) {
			logger.error("UnhandledAlertException for seed: " + seed.getUser() + " with password: " + seed.getPassword()
					+ " " + e.getMessage() + " ", e);

		} catch (WebDriverException e) {
			logger.error("WebDriverException for seed: " + seed.getUser() + " with password: " + seed.getPassword()
					+ " " + e.getMessage() + " ", e);
		}
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
			logger.error("InterruptedException for seed: " + seed.getUser() + " with password: " + seed.getPassword()
					+ " " + e.getMessage() + " ", e);
		} catch (NoSuchElementException e) {
			logger.error("NoSuchElementException for seed: " + seed.getUser() + " with password: " + seed.getPassword()
					+ " " + e.getMessage() + " ", e);
		} catch (StaleElementReferenceException e) {
			logger.error("StaleElementReferenceException for seed: " + seed.getUser() + " with password: "
					+ seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (ElementNotVisibleException e) {
			logger.error("ElementNotVisibleException for seed: " + seed.getUser() + " with password: "
					+ seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (ElementNotFoundException e) {
			logger.error("ElementNotFoundException for seed: " + seed.getUser() + " with password: "
					+ seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (UnhandledAlertException e) {
			logger.error("UnhandledAlertException for seed: " + seed.getUser() + " with password: " + seed.getPassword()
					+ " " + e.getMessage() + " ", e);

		} catch (WebDriverException e) {
			logger.error("WebDriverException for seed: " + seed.getUser() + " with password: " + seed.getPassword()
					+ " " + e.getMessage() + " ", e);
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
			logger.error("InterruptedException for seed: " + seed.getUser() + " with password: " + seed.getPassword()
					+ " " + e.getMessage() + " ", e);
		} catch (NoSuchElementException e) {
			logger.error("NoSuchElementException for seed: " + seed.getUser() + " with password: " + seed.getPassword()
					+ " " + e.getMessage() + " ", e);
		} catch (StaleElementReferenceException e) {
			logger.error("StaleElementReferenceException for seed: " + seed.getUser() + " with password: "
					+ seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (ElementNotVisibleException e) {
			logger.error("ElementNotVisibleException for seed: " + seed.getUser() + " with password: "
					+ seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (ElementNotFoundException e) {
			logger.error("ElementNotFoundException for seed: " + seed.getUser() + " with password: "
					+ seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (UnhandledAlertException e) {
			logger.error("UnhandledAlertException for seed: " + seed.getUser() + " with password: " + seed.getPassword()
					+ " " + e.getMessage() + " ", e);

		} catch (WebDriverException e) {
			logger.error("WebDriverException for seed: " + seed.getUser() + " with password: " + seed.getPassword()
					+ " " + e.getMessage() + " ", e);
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
				logger.error("InterruptedException for seed: " + seed.getUser() + " with password: "
						+ seed.getPassword() + " " + e.getMessage() + " ", e);
			} catch (NoSuchElementException e) {
				logger.error("NoSuchElementException for seed: " + seed.getUser() + " with password: "
						+ seed.getPassword() + " " + e.getMessage() + " ", e);
			} catch (StaleElementReferenceException e) {
				logger.error("StaleElementReferenceException for seed: " + seed.getUser() + " with password: "
						+ seed.getPassword() + " " + e.getMessage() + " ", e);
			} catch (ElementNotVisibleException e) {
				logger.error("ElementNotVisibleException for seed: " + seed.getUser() + " with password: "
						+ seed.getPassword() + " " + e.getMessage() + " ", e);
			} catch (ElementNotFoundException e) {
				logger.error("ElementNotFoundException for seed: " + seed.getUser() + " with password: "
						+ seed.getPassword() + " " + e.getMessage() + " ", e);
			} catch (UnhandledAlertException e) {
				logger.error("UnhandledAlertException for seed: " + seed.getUser() + " with password: "
						+ seed.getPassword() + " " + e.getMessage() + " ", e);

			} catch (WebDriverException e) {
				logger.error("WebDriverException for seed: " + seed.getUser() + " with password: " + seed.getPassword()
						+ " " + e.getMessage() + " ", e);
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
	public void processSpam(Seed seed) {

		// try {
		// logger.info("Process Spam");
		//
		// if (Constant.SPECIFIC.equals(Seeder.type) ||
		// Constant.DESTROYER.equals(Seeder.type)) {
		//
		// logger.info("RemoveConversationMailView");
		// removeConversationMailView();
		// }
		//
		// } catch (MoveTargetOutOfBoundsException e) {
		// logger.info("Process Spam MoveTargetOutOfBoundsException ");
		// }
		//
		// if (validateSpamFolder()) {
		// logger.info("There are msgs in the spam folder, go get them Tiger!");
		// logger.info("Seeder type: " + Seeder.type);
		// if (Constant.SPECIFIC.equals(Seeder.type) ||
		// Constant.DESTROYER.equals(Seeder.type)) {
		//
		// logger.info("Checking all NOT SPAM");
		// WebElement checkbox =
		// driver.findElement(By.xpath("//span[@id='btn-ml-cbox']/label/input"));
		// if (!checkbox.isSelected()) {
		// checkbox.click();
		// }
		//
		// driver.findElement(By.xpath("//*[@id='btn-not-spam']")).click();
		//
		// }else if (Constant.MULTIPLE.equals(Seeder.type)) {
		// List<WebElement> spamMsgs =
		// driver.findElements(By.className("list-view-item"));
		//
		// logger.info("Percentage is " + PERCENTAGE);
		// int percentage = (int) (spamMsgs.size() * PERCENTAGE);
		//
		// if (percentage > 10) {
		// percentage = 3;
		// }
		//
		// for (int j = 0; j < percentage; j++) {
		//
		// try {
		// logger.info(j + " emails not spammed " + (percentage - j) + " emails
		// to go");
		// int chances = randInt(0, 10);
		// Thread.sleep(randInt(2000, 3000));
		// if (chances <= 8) {
		// normalNotSpam();
		// } else {
		// dragAndDropNotSpam();
		// }
		//
		// } catch (InterruptedException e) {
		// logger.error("InterruptedException for seed: " + seed.getUser() + "
		// with password: " + seed.getPassword() + " " + e.getMessage() + " " ,
		// e);
		// } catch (NoSuchElementException e) {
		// logger.error("NoSuchElementException for seed: " + seed.getUser() + "
		// with password: " + seed.getPassword() + " " + e.getMessage() + " " ,
		// e);
		// } catch (StaleElementReferenceException e) {
		// logger.error("StaleElementReferenceException for seed: " +
		// seed.getUser() + " with password: " + seed.getPassword() + " " +
		// e.getMessage() + " ", e);
		// } catch (ElementNotVisibleException e) {
		// logger.error("ElementNotVisibleException for seed: " + seed.getUser()
		// + " with password: " + seed.getPassword() + " " + e.getMessage() + "
		// ", e);
		// } catch (ElementNotFoundException e) {
		// logger.error("ElementNotFoundException for seed: " + seed.getUser() +
		// " with password: " + seed.getPassword() + " " + e.getMessage() + " ",
		// e);
		// } catch (UnhandledAlertException e) {
		// logger.error("UnhandledAlertException for seed: " + seed.getUser() +
		// " with password: " + seed.getPassword() + " " + e.getMessage() + " "
		// , e);
		//
		// } catch (WebDriverException e) {
		// logger.error("WebDriverException for seed: " + seed.getUser() + "
		// with password: " + seed.getPassword() + " " + e.getMessage() + " " ,
		// e);
		// }
		// }
		// }
		// } else {
		// logger.info("Spam Folder is empty! UOHOOO!");
		// }
	}

	private void removeConversationMailView() {

		try {
			Actions myMouse = new Actions(driver);

			WebElement settings = driver.findElement(By.id("yucs-help"));
			myMouse.moveToElement(settings).build().perform();
			logger.info("Moving to configuration wheel");

			Thread.sleep(randInt(1000, 2000));

			if (driver.findElement(By.xpath("//div[@id='yucs-help_inner']")).getText().isEmpty()) {
				driver.findElement(By.xpath("//div[@id='yucs-help_inner']/ul/li[2]/a")).click();
				Thread.sleep(randInt(1000, 2000));
				if (driver.findElement(By.xpath("//input[@id='options-enableConv']")).isSelected()) {
					logger.info("Conversation mode is on. Turning off. Aprox 15 seconds");

					driver.findElement(By.xpath("//input[@id='options-enableConv']")).click();
					Thread.sleep(randInt(500, 2000));

					driver.findElement(By.xpath("//button[@class='left right default btn']")).click();
					Thread.sleep(randInt(3000, 7000));
				}
			}
		} catch (InterruptedException e) {
			logger.error("InterruptedException for seed: " + seed.getUser() + " with password: " + seed.getPassword()
					+ " " + e.getMessage() + " ", e);
		} catch (NoSuchElementException e) {
			logger.error("NoSuchElementException for seed: " + seed.getUser() + " with password: " + seed.getPassword()
					+ " " + e.getMessage() + " ", e);
		} catch (StaleElementReferenceException e) {
			logger.error("StaleElementReferenceException for seed: " + seed.getUser() + " with password: "
					+ seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (ElementNotVisibleException e) {
			logger.error("ElementNotVisibleException for seed: " + seed.getUser() + " with password: "
					+ seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (ElementNotFoundException e) {
			logger.error("ElementNotFoundException for seed: " + seed.getUser() + " with password: "
					+ seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (UnhandledAlertException e) {
			logger.error("UnhandledAlertException for seed: " + seed.getUser() + " with password: " + seed.getPassword()
					+ " " + e.getMessage() + " ", e);

		} catch (WebDriverException e) {
			logger.error("WebDriverException for seed: " + seed.getUser() + " with password: " + seed.getPassword()
					+ " " + e.getMessage() + " ", e);
		}
	}

	/**
	 * @param driver
	 * @param spamFolder
	 * @return
	 */
	private boolean validateSpamFolder() {
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
			logger.error("InterruptedException for seed: " + seed.getUser() + " with password: " + seed.getPassword()
					+ " " + e.getMessage() + " ", e);
		} catch (NoSuchElementException e) {
			logger.error("NoSuchElementException for seed: " + seed.getUser() + " with password: " + seed.getPassword()
					+ " " + e.getMessage() + " ", e);
		} catch (StaleElementReferenceException e) {
			logger.error("StaleElementReferenceException for seed: " + seed.getUser() + " with password: "
					+ seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (ElementNotVisibleException e) {
			logger.error("ElementNotVisibleException for seed: " + seed.getUser() + " with password: "
					+ seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (ElementNotFoundException e) {
			logger.error("ElementNotFoundException for seed: " + seed.getUser() + " with password: "
					+ seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (UnhandledAlertException e) {
			logger.error("UnhandledAlertException for seed: " + seed.getUser() + " with password: " + seed.getPassword()
					+ " " + e.getMessage() + " ", e);

		} catch (WebDriverException e) {
			logger.error("WebDriverException for seed: " + seed.getUser() + " with password: " + seed.getPassword()
					+ " " + e.getMessage() + " ", e);
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

			// if (isWarmupDomain(false, msg)) {
			WebElement inboxFolder = driver.findElement(By.className("inbox-label"));
			logger.info("******** Dragging Message to inbox ***********");
			(new Actions(driver)).dragAndDrop(msg, inboxFolder).perform();

			return true;
		} catch (InterruptedException e) {
			logger.error("InterruptedException for seed: " + seed.getUser() + " with password: " + seed.getPassword()
					+ " " + e.getMessage() + " ", e);
		} catch (NoSuchElementException e) {
			logger.error("NoSuchElementException for seed: " + seed.getUser() + " with password: " + seed.getPassword()
					+ " " + e.getMessage() + " ", e);
		} catch (StaleElementReferenceException e) {
			logger.error("StaleElementReferenceException for seed: " + seed.getUser() + " with password: "
					+ seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (ElementNotVisibleException e) {
			logger.error("ElementNotVisibleException for seed: " + seed.getUser() + " with password: "
					+ seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (ElementNotFoundException e) {
			logger.error("ElementNotFoundException for seed: " + seed.getUser() + " with password: "
					+ seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (UnhandledAlertException e) {
			logger.error("UnhandledAlertException for seed: " + seed.getUser() + " with password: " + seed.getPassword()
					+ " " + e.getMessage() + " ", e);

		} catch (WebDriverException e) {
			logger.error("WebDriverException for seed: " + seed.getUser() + " with password: " + seed.getPassword()
					+ " " + e.getMessage() + " ", e);
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

			// if (isWarmupDomain(false, currentMsg)) {
			logger.info("Opening the spam message");
			currentMsg.findElement(By.className("subj")).click();

			Thread.sleep(randInt(1000, 2000));

			clickShowImages("show-text");
			Thread.sleep(randInt(1000, 2000));
			scrollToBottom(driver);
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
			// } else {
			// return false;
			// }
		} catch (InterruptedException e) {
			logger.error("InterruptedException for seed: " + seed.getUser() + " with password: " + seed.getPassword()
					+ " " + e.getMessage() + " ", e);
		} catch (NoSuchElementException e) {
			logger.error("NoSuchElementException for seed: " + seed.getUser() + " with password: " + seed.getPassword()
					+ " " + e.getMessage() + " ", e);
		} catch (StaleElementReferenceException e) {
			logger.error("StaleElementReferenceException for seed: " + seed.getUser() + " with password: "
					+ seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (ElementNotVisibleException e) {
			logger.error("ElementNotVisibleException for seed: " + seed.getUser() + " with password: "
					+ seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (ElementNotFoundException e) {
			logger.error("ElementNotFoundException for seed: " + seed.getUser() + " with password: "
					+ seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (UnhandledAlertException e) {
			logger.error("UnhandledAlertException for seed: " + seed.getUser() + " with password: " + seed.getPassword()
					+ " " + e.getMessage() + " ", e);

		} catch (WebDriverException e) {
			logger.error("WebDriverException for seed: " + seed.getUser() + " with password: " + seed.getPassword()
					+ " " + e.getMessage() + " ", e);
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
				logger.error("InterruptedException for seed: " + seed.getUser() + " with password: "
						+ seed.getPassword() + " " + e.getMessage() + " ", e);
			} catch (NoSuchElementException e) {
				logger.error("NoSuchElementException for seed: " + seed.getUser() + " with password: "
						+ seed.getPassword() + " " + e.getMessage() + " ", e);
			} catch (StaleElementReferenceException e) {
				logger.error("StaleElementReferenceException for seed: " + seed.getUser() + " with password: "
						+ seed.getPassword() + " " + e.getMessage() + " ", e);
			} catch (ElementNotVisibleException e) {
				logger.error("ElementNotVisibleException for seed: " + seed.getUser() + " with password: "
						+ seed.getPassword() + " " + e.getMessage() + " ", e);
			} catch (ElementNotFoundException e) {
				logger.error("ElementNotFoundException for seed: " + seed.getUser() + " with password: "
						+ seed.getPassword() + " " + e.getMessage() + " ", e);
			} catch (UnhandledAlertException e) {
				logger.error("UnhandledAlertException for seed: " + seed.getUser() + " with password: "
						+ seed.getPassword() + " " + e.getMessage() + " ", e);

			} catch (WebDriverException e) {
				logger.error("WebDriverException for seed: " + seed.getUser() + " with password: " + seed.getPassword()
						+ " " + e.getMessage() + " ", e);
			}
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
				Thread.sleep(randInt(1500, 3500));
			}
			if (throwDice()) {
				WebElement inboxLabel = driver.findElement(By.className("inbox-label"));
				myMouse.moveToElement(inboxLabel).build().perform();
				logger.info("Moving to inbox");
				Thread.sleep(randInt(1500, 3500));
			}
			if (throwDice()) {
				WebElement multitask = driver.findElement(By.id("multitask"));
				myMouse.moveToElement(multitask).build().perform();
				logger.info("Moving to multitask");
				Thread.sleep(randInt(1500, 3500));
			}
			if (throwDice()) {
				WebElement spamLabel = driver.findElement(By.id("spam-label"));
				myMouse.moveToElement(spamLabel).build().perform();
				logger.info("Moving to spam");
				Thread.sleep(randInt(1500, 3500));
			}
			if (throwDice()) {
				WebElement yucsHomeLink = driver.findElement(By.id("yucs-home_link"));
				myMouse.moveToElement(yucsHomeLink).build().perform();
				logger.info("Moving to Home");
				Thread.sleep(randInt(1500, 3500));
			}
			if (throwDice()) {
				WebElement typeaheadInput = driver.findElement(By.className("typeahead-input"));
				myMouse.moveToElement(typeaheadInput).build().perform();
				logger.info("Moving to search box");
				Thread.sleep(randInt(1500, 3500));
			}
			if (throwDice()) {
				WebElement uhWrapper = driver.findElement(By.xpath("//*[@id='uhWrapper']/table/tbody/tr/td/a"));
				myMouse.moveToElement(uhWrapper).build().perform();
				logger.info("Moving to yahoo icon");
				Thread.sleep(randInt(1500, 3500));
			}
			if (throwDice()) {
				WebElement yucsProfile = driver.findElement(By.id("yucs-profile"));
				myMouse.moveToElement(yucsProfile).build().perform();
				logger.info("Moving to Profile");
				Thread.sleep(randInt(1500, 3500));
			}
			if (throwDice()) {
				WebElement smartviews = driver.findElement(By.id("smartviews"));
				myMouse.moveToElement(smartviews).build().perform();
				logger.info("Moving to smartviews");
				Thread.sleep(randInt(1500, 3500));
			}
			if (throwDice()) {
				WebElement trash = driver.findElement(By.className("trash"));
				myMouse.moveToElement(trash).build().perform();
				logger.info("Moving to trash");
				Thread.sleep(randInt(1500, 3500));
			}
			if (throwDice()) {
				WebElement sent = driver.findElement(By.className("sent"));
				myMouse.moveToElement(sent).build().perform();
				logger.info("Moving to sent");
				Thread.sleep(randInt(1500, 3500));
			}
			if (throwDice()) {
				WebElement drafts = driver.findElement(By.className("drafts"));
				myMouse.moveToElement(drafts).build().perform();
				logger.info("Moving to drafts");
				Thread.sleep(randInt(1500, 3500));
			}
			if (throwDice()) {
				WebElement compose = driver.findElement(By.id("Compose"));
				myMouse.moveToElement(compose).build().perform();
				logger.info("Moving to compose");
				Thread.sleep(randInt(1500, 3500));
			}
			if (throwDice()) {
				WebElement mailSearchBtn = driver.findElement(By.id("mail-search-btn"));
				myMouse.moveToElement(mailSearchBtn).build().perform();
				logger.info("Moving to mailSearchBtn");
				Thread.sleep(randInt(1500, 3500));
			}
			if (throwDice()) {
				WebElement webSearchBtn = driver.findElement(By.id("web-search-btn"));
				myMouse.moveToElement(webSearchBtn).build().perform();
				logger.info("Moving to webSearchBtn");
				Thread.sleep(randInt(1500, 3500));
			}
		} catch (InterruptedException e) {
			logger.error("InterruptedException for seed: " + seed.getUser() + " with password: " + seed.getPassword()
					+ " " + e.getMessage() + " ", e);
		} catch (NoSuchElementException e) {
			logger.error("NoSuchElementException for seed: " + seed.getUser() + " with password: " + seed.getPassword()
					+ " " + e.getMessage() + " ", e);
		} catch (StaleElementReferenceException e) {
			logger.error("StaleElementReferenceException for seed: " + seed.getUser() + " with password: "
					+ seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (ElementNotVisibleException e) {
			logger.error("ElementNotVisibleException for seed: " + seed.getUser() + " with password: "
					+ seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (ElementNotFoundException e) {
			logger.error("ElementNotFoundException for seed: " + seed.getUser() + " with password: "
					+ seed.getPassword() + " " + e.getMessage() + " ", e);
		} catch (UnhandledAlertException e) {
			logger.error("UnhandledAlertException for seed: " + seed.getUser() + " with password: " + seed.getPassword()
					+ " " + e.getMessage() + " ", e);

		} catch (WebDriverException e) {
			logger.error("WebDriverException for seed: " + seed.getUser() + " with password: " + seed.getPassword()
					+ " " + e.getMessage() + " ", e);
		}
	}

	public static void scrollToBottom(WebDriver driver) {
		((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}

	private WebElement findMyMsgBySearchBox() {
		WebElement searchBox = driver.findElement(By.className("typeahead-input-usertext"));
		String searchString = "From: Entrepreneur is: unread";
		human.type(searchBox, searchString);
		WebElement searchButton = driver.findElement(By.id("mail-search-btn"));
		searchButton.click();
		List<WebElement> myMessages = new ArrayList<WebElement>();
		if (driver.findElements(By.className("message-list-group")).size() > 0) {
			myMessages = driver.findElements(By.className("message-list-group"));
		}
		if (myMessages.isEmpty()) {
			return null;
		}
		int randomPosition = obtainRandomMsgsPosition(myMessages);

		logger.info("Getting my message randomly");
		return myMessages.get(randomPosition);
	}

	private WebElement findMessage(List<WebElement> inboxMsgs,String msgfrom) {
		logger.info("Finding message "+msgfrom);
		List<WebElement> myMessages = new ArrayList<WebElement>();
		for (WebElement webElement : inboxMsgs) {
			WebElement from = webElement.findElement(By.className("from"));
			String fromText = from.getText();
			if (msgfrom.equals(fromText)) {
				myMessages.add(webElement);
				break;
			}
		}
		if (myMessages.isEmpty()) {
			return null;
		}
		int randomPosition = obtainRandomMsgsPosition(myMessages);

		logger.info("Getting my message randomly");
		return myMessages.get(randomPosition);
	}

}
