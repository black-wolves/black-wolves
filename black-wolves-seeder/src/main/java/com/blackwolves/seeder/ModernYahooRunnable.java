package com.blackwolves.seeder;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;

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
	public void processInbox(String[] seed) {
		logger.info("Processing inbox");
		
		checkWelcomeDialog();
		validateOkayModal();
		
		WebDriverWait wait = new WebDriverWait(driver, 30);
		
		WebElement inboxFolder = null;
		inboxFolder = validateInboxFolder(inboxFolder);
		//Close validate Modal
		
		// Check if inbox is empty
		if (inboxFolder != null && inboxFolder.isDisplayed()) {
			logger.info("Inbox Folder is empty.");
		}
		
		// If not empty, proceed
		else {
			
			wait.until(ExpectedConditions.elementToBeClickable(By.className("list-view-item-container")));

			if (driver.findElements(By.className("list-view-item-container")).size() > 0) {
				
				logger.info("list-view-item-container found");
				List<WebElement> inboxMsgs = driver.findElements(By.className("list-view-item-container"));
				
				logger.info("Percentage is " + PERCENTAGE);
				int percentage = (int) (inboxMsgs.size() * PERCENTAGE);
				
				for (int j = 0; j < percentage; j++) {
					
					if (throwDice()) {
						sendEmail();
					}
					
					logger.info((percentage - j) + " emails to go ");
					driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
					
					if (driver.findElements(By.className("list-view-item-container")).size() > 0) {
						try {
							mouse.moveByOffset(200 + randInt(0, 300), 300 + randInt(0, 400));
							
							logger.info("list-view-item-container found");
							inboxMsgs = driver.findElements(By.className("list-view-item-container"));
							
							logger.info("Obtaining a random message position so it can be open");
							int randomPosition = obtainRandomMsgsPosition(inboxMsgs);
							
							driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
							
							logger.info("Getting the random message");
							WebElement currentMsg = inboxMsgs.get(randomPosition);
							
							if(isWarmupDomain(true, currentMsg)){
								
								logger.info("Clicking in Msg : " + currentMsg.getText());
								currentMsg.findElement(By.className("subj")).click();
								
								clickShowImages("show-text");
								
								if (throwDice()) {
									replyToEmail();
								}else if (throwDice()){
									forwardEmail();
								}else if(throwDice()){
									clickRandomLink();
								}
								
								moveMessageToAllFolder();
								
								driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
								logger.info("Going back to inbox");
								driver.findElement(By.className("inbox-label")).click();
								
								checkForInboxReloadError();
							}else{
								if (YahooRunnable.randInt(0,1) == 1) {
									
									logger.info("Clicking in Msg : " + currentMsg.getText());
									currentMsg.findElement(By.className("subj")).click();
									
									driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
									
									clickSpam();
								}
							}
						}catch (Exception e) {
							logger.error(e.getMessage(), e);
							logger.info("Need to sync the thread...Going to inbox to keep going ");
							driver.findElement(By.className("inbox-label")).click();
							checkForInboxReloadError();
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

	@Override
	public void replyToEmail() {
		try{
			logger.info("Clicking the reply button");
			Thread.sleep(randInt(1500, 2500));
			WebElement reply = driver.findElement(By.id("btn-reply-sender"));
			reply.click();
			
			Thread.sleep(randInt(1500, 2500));
			WebElement quickReply = driver.findElement(By.className("quickReply"));
			
			Thread.sleep(randInt(1500, 2500));
			JavascriptExecutor jse = (JavascriptExecutor)driver;
			String body = human.generateRandomBody();
			jse.executeScript("document.getElementById('rtetext').getElementsByTagName('p')[0].outerHTML = \" " + body + " \";");
			logger.info("Filling body field");
			Thread.sleep(randInt(1500, 2500));
			
			logger.info("Replying the email");
			Thread.sleep(randInt(1500, 2500));
			WebElement send = quickReply.findElement(By.className("bottomToolbar")).findElement(By.className("default")).findElement(By.tagName("a"));
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
	
	@Override
	public void forwardEmail() {
		try{
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
			human.type(toInput,to);
			Thread.sleep(randInt(2000, 3000));
			toInput.sendKeys(Keys.TAB);
			
			logger.info("Forwarding the email");
			Thread.sleep(randInt(2000, 3000));
			WebElement send = quickReply.findElement(By.className("bottomToolbar")).findElement(By.className("default")).findElement(By.tagName("a"));
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
	
	@Override
	public void sendEmail() {
		try{
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
			human.type(toInput,to);
			Thread.sleep(randInt(1500, 2500));
			toInput.sendKeys(Keys.TAB);
			
			Thread.sleep(randInt(1500, 2500));
			WebElement subjectInput = fullCompose.findElement(By.id("subject-field"));
			logger.info("Filling subject field");
			Thread.sleep(randInt(1500, 2500));
			human.type(subjectInput, human.generateRandomSubject());
			
			Thread.sleep(randInt(1500, 2500));
			JavascriptExecutor jse = (JavascriptExecutor)driver;
			String body = human.generateRandomBody();
			jse.executeScript("document.getElementById('rtetext').getElementsByTagName('p')[0].outerHTML = \" " + body + " \";");
			logger.info("Filling body field");
			Thread.sleep(randInt(1500, 2500));
			
			logger.info("Sending the email");
			Thread.sleep(randInt(1500, 2500));
			WebElement send = fullCompose.findElement(By.className("bottomToolbar")).findElement(By.className("default")).findElement(By.tagName("a"));
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

	@Override
	public void clickRandomLink() {
		try{
			logger.info("Getting the content of the message");
			Thread.sleep(YahooRunnable.randInt(2500, 3500));
			WebElement div = driver.findElement(By.className("thread-body"));
			logger.info("Looking for links inside the message");
			Thread.sleep(YahooRunnable.randInt(1500, 2500));
			if (div.findElements(By.tagName("a")).size() > 0) {
				logger.info("Links found");
				List<WebElement> linksToGo = div.findElements(By.tagName("a"));
				int randomLinkNo = randInt(0, linksToGo.size()-1);
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
			logger.error(e.getMessage(), e);
		} catch (StaleElementReferenceException e) {
			logger.error(e.getMessage(), e);
		} catch (ElementNotVisibleException e) {
			logger.error(e.getMessage(), e);
		} catch (ElementNotFoundException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	@Override
	public void moveMessageToAllFolder() {
		try{
			logger.info("Clicking move button");
			Thread.sleep(randInt(1000, 2000));
			driver.findElement(By.id("btn-move")).click();
			logger.info("Moving message to All folder");
			Thread.sleep(randInt(1500, 2500));
			driver.findElement(By.id("menu-move")).findElement(By.id("menu-move-folder")).findElement(By.tagName("li")).click();
			logger.info("Message moved!!");
			Thread.sleep(randInt(2000, 3000));
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
	
	@Override
	public void clickSpam() {
		try{
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
			logger.error(e.getMessage(), e);
		} catch (StaleElementReferenceException e) {
			logger.error(e.getMessage(), e);
		} catch (ElementNotVisibleException e) {
			logger.error(e.getMessage(), e);
		} catch (ElementNotFoundException e) {
			logger.error(e.getMessage(), e);
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
			if(domain.equals(d[0])){
				logger.info("Is a warmup domain, we move forward :D");
				return true;
			}
		}
		if(inbox && (domain.endsWith("yahoo.com") || domain.endsWith(".ro"))){
			logger.info("Is a yahoo or a .ro domain, we move forward :D");
			return true;
		}
		logger.info("Is not a warmup domain!!!");
		return false;
	}

	private void validateOkayModal() {
		List <WebElement> okayModals = driver.findElements(By.id("okayModalOverlay"));
		if(okayModals.size()>0)
		{
			logger.info("OkayModal Found. Closing it");
			okayModals.get(0).findElement(By.tagName("a")).click();
			
		}
	}

	/**
	 * @param driver
	 */
	private void checkForInboxReloadError() {
		try{
			Thread.sleep(randInt(2000, 3000));
			if(driver.findElements(By.id("loadingpane")).size() > 0){
				logger.info("loadingpane found!");
				Thread.sleep(randInt(2000, 3000));
				if(driver.findElement(By.id("loadingpane")).findElements(By.className("default")).size() > 0){
					logger.info("refreshing the page");
					driver.navigate().refresh();
				}
			}
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

	/**
	 * @param driver
	 * @param inboxFolder
	 * @return
	 */
	private WebElement validateInboxFolder(WebElement inboxFolder) {
		if(driver.findElements(By.className("inbox-label")).size() > 0){
			try{
				WebElement inbox = driver.findElement(By.className("inbox-label"));
				inbox.click();
				Thread.sleep(randInt(2000, 3000));
				if(inbox.findElements(By.className("empty-folder")).size() > 0){
					inboxFolder = driver.findElement(By.className("empty-folder"));
				}
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
		return inboxFolder;
	}

	private void checkWelcomeDialog() {
		int retries = 2;
		for (int i = 0; i < retries; i++) {
			try{
				if(driver.findElements(By.className("ob-contactimport-btn-skip")).size() > 0){
					List<WebElement> dialogs = driver.findElements(By.className("ob-contactimport-btn-skip"));
					logger.info("Welcome Dialog found. Closing it.");
					WebElement welcomeDialog = dialogs.get(0);
					welcomeDialog.click();
				}else {
					logger.info("No Welcome Dialog was found.");
					Thread.sleep(randInt(2000, 3000));
				}
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.blackwolves.selenium.seeder.YahooHandler#spamHandler(org.openqa.
	 * selenium.WebDriver, java.lang.String[])
	 */
	@Override
	public void processSpam(String[] seed) {
		logger.info("Processing Spam....");
		try{
			if (driver.findElements(By.id("spam-label")).size() > 0) {
				WebElement spam = driver.findElement(By.id("spam-label"));
				spam.click();
				Thread.sleep(randInt(2000, 3000));
				if(spam.findElements(By.className("empty-folder")).size() > 0){
					logger.info("Spam Folder is empty! UOHOOO!");
				}else{
					logger.info("There are msgs in the spam folder, go get them Tiger!");
					
					WebDriverWait wait = new WebDriverWait(driver, 20);
					wait.until(ExpectedConditions.elementToBeClickable(By.className("list-view-item-container")));
					if (driver.findElements(By.className("list-view-item-container")).size() > 0) {
						logger.info("list-view-item-container found");
						wait.until(ExpectedConditions.elementToBeClickable(By.className("list-view-item-container")));
						List<WebElement> spamMsgs = driver.findElements(By.className("list-view-item-container"));
						logger.info("Percentage is " + PERCENTAGE);
						int percentage = (int) (spamMsgs.size() * PERCENTAGE);
						for (int j = 0; j < percentage; ) {
							driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
							logger.info(j + " emails not spammed " + (percentage - j) + " emails to go");
							int chances =  randInt(0, 10);
							boolean increment;
							if (chances <= 7 ) {
								increment = normalNotSpam(wait);
							} else {
								increment = dragAndDropNotSpam(wait);
							}
//							if(increment){
							if(true){
								j++;
							}
						}
					} else {
						logger.info("**********   No list-view-item-container found or no messages available   **********");
					}
				}
			}else{
				logger.info("**********   spam-label is not available   **********");
			}
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

	private boolean dragAndDropNotSpam(WebDriverWait wait) {
		try{
			List<WebElement> spamMsgs = driver.findElements(By.className("list-view-item-container"));
			int randomPosition = obtainRandomMsgsPosition(spamMsgs);
			Thread.sleep(randInt(2000, 3000));
			logger.info("Selecting spam message");
			WebElement msg = spamMsgs.get(randomPosition);
			if(isWarmupDomain(false, msg)){
				WebElement inboxFolder = driver.findElement(By.className("inbox-label"));
				logger.info("******** Dragging Message to inbox ***********");
				(new Actions(driver)).dragAndDrop(msg, inboxFolder).perform();
				driver.navigate().refresh();
				return true;
			}
			return false;
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
		return false;
	}

	private boolean normalNotSpam(WebDriverWait wait) {
		try {
			List<WebElement> spamMsgs = driver.findElements(By.className("list-view-item-container"));
			logger.info("Obtaining a random message position so it can be open");
			int randomPosition = obtainRandomMsgsPosition(spamMsgs);
			Thread.sleep(randInt(2000, 3000));
			
			WebElement msg = spamMsgs.get(randomPosition);
			
			if(isWarmupDomain(false, msg)){
				logger.info("Opening the spam message");
				msg.findElement(By.className("subj")).click();
				Thread.sleep(randInt(2000, 3000));
				
				clickShowImages("show-text");
				Thread.sleep(randInt(2000, 3000));
				//REMOVED CLICK FROM LIST SINCE IT IS BREAKING IN THE SERVER
				if (false) {
					logger.info("******** Clicking the not spam LIST button ***********");
					notSpamFromSubList();
				} else {
					logger.info("******** Clicking the not spam MAIN button ***********");
					driver.findElement(By.id("main-btn-spam")).click();
					wait.until(ExpectedConditions.elementToBeClickable(By.className("list-view-item-container")));
				}
				return true;
			}else{
				return false;
			}
		}  catch (InterruptedException e) {
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
		driver.findElement(By.id("spam-label")).click();
		return false;
	}
	
	private void notSpamFromSubList() {
		List<WebElement> elements = driver.findElements(By.className("card-actions-menu"));
		logger.info("Clicking not spam from submenu");
		for (WebElement ahref : elements) {
			try{
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
				}
				else {
					logger.info("sublist is not visible. Going back to bulk");
					driver.findElement(By.id("spam-label")).click();
					Thread.sleep(randInt(2000, 3000));
				}
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

//	NOT WORKING
	@Override
	public void replyToEmailFromSubList() {
		logger.info("Clicking the reply button from sublist");
//		String body = human.generateRandomBody(driver, wait);
//		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		List<WebElement> elements = driver.findElement(By.className("addconvtitle")).findElements(By.tagName("a"));
		WebElement reply = elements.get(2);
		reply.click();
		
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		WebElement quickReply = driver.findElement(By.className("quickReply"));
		
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		WebElement bodyMail = quickReply.findElement(By.id("rtetext"));
		bodyMail.click();
//		human.type(bodyMail, body);
		
		logger.info("Replying the email from sublist");
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		WebElement send = quickReply.findElement(By.className("bottomToolbar")).findElement(By.className("default")).findElement(By.tagName("a"));
		send.click();
	}
	
//	NOT WORKING
	@Override
	public void forwardEmailFromSubList() {
		logger.info("Clicking the forward button from sublist");
//		String body = human.generateRandomBody(driver, wait);
//		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		List<WebElement> elements = driver.findElement(By.className("addconvtitle")).findElements(By.tagName("a"));
		WebElement forward = elements.get(2);
		forward.click();
		
		String to = human.generateRandomTo(seed);
		
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		WebElement quickReply = driver.findElement(By.className("quickReply"));
		
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		WebElement toInput = quickReply.findElement(By.id("to-field"));
		
		logger.info("Filling to field");
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		human.type(toInput,to);
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		toInput.sendKeys(Keys.TAB);
		
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		WebElement bodyMail = quickReply.findElement(By.id("rtetext"));
		bodyMail.click();
//		human.type(bodyMail, body);
		
		logger.info("Forwarding the email from sublist");
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		WebElement send = quickReply.findElement(By.className("bottomToolbar")).findElement(By.className("default")).findElement(By.tagName("a"));
		send.click();
	}

//	private void humanizeMe() {
//		logger.info("Adding Human Behaviour");
//
//		int goToX = randInt(0, 100);
//		int goToY = randInt(0, 100);
//		logger.info("Starting to move mouse randomly. ");
//		for (int i = randInt(10, 150); i > 0; i--) {
//			mouse.moveByOffset(goToX + i, goToY + i);
//			driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
//		}
//		logger.info("Ended mouse simulation. ");
//
//		// WebElement searchBar = driver.findElement(By.id("UHSearchProperty"));
//		// mouse.moveToElement(searchBar).build().perform();
//
//		if (true) {
//			jse.executeScript("window.scrollBy(0,250)", "");
//			driver.manage().timeouts().implicitlyWait(randInt(2, 5), TimeUnit.SECONDS);
//		}
//
//		if (throwDice()) {
//			jse.executeScript("scroll(0, -250);");
//			// Reading email
//			driver.manage().timeouts().implicitlyWait(randInt(2, 5), TimeUnit.SECONDS);
//		}
//
//	}

}
