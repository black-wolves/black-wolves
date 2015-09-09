package com.blackwolves.seeder;

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
			driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
			wait.until(ExpectedConditions.elementToBeClickable(By.className("list-view-item-container")));

			if (driver.findElements(By.className("list-view-item-container")).size() > 0) {
				
				logger.info("list-view-item-container found");
				List<WebElement> inboxMsgs = driver.findElements(By.className("list-view-item-container"));
				
				logger.info("Percentage is " + PERCENTAGE);
				int percentage = (int) (inboxMsgs.size() * PERCENTAGE);
				
				for (int j = 0; j < percentage; j++) {
					
					logger.info((percentage - j) + " emails to go ");
					wait.until(ExpectedConditions.elementToBeClickable(By.className("list-view-item-container")));
					
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
							
							if(isWarmupDomain(currentMsg)){
								
								logger.info("Clicking in Msg : " + currentMsg.getText());
								currentMsg.findElement(By.className("subj")).click();
								
								driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
								
								clickShowImages("show-text");
								
//								if (throwDice()) {
//									replyToEmail();
//								}else if (throwDice()){
//									replyToEmailFromSubList(wait);
//								}else if (throwDice()){
//									forwardEmail(wait);
//								}else if (throwDice()){
//									forwardEmailFromSubList(wait);
//								}
//								
//								if (throwDice()) {
//									clickRandomLink();
//								}
//								
//								if (throwDice()) {
//									sendEmail();
//								}

								logger.info("Going back to inbox");
//								mouse.moveToElement(driver.findElement(By.className("inbox-label"))).build().perform();
								driver.findElement(By.className("inbox-label")).click();
								
								driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
								
//								checkForInboxReloadError();
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
		if(driver.findElements(By.id("loadingpane")).size() > 0){
			if(driver.findElement(By.id("loadingpane")).findElements(By.className("default")).size() > 0){
				driver.navigate().refresh();
			}
		}
	}

	/**
	 * @param driver
	 * @param inboxFolder
	 * @return
	 */
	private WebElement validateInboxFolder(WebElement inboxFolder) {
		if(driver.findElements(By.className("inbox-label")).size() > 0){
			driver.findElement(By.className("inbox-label")).click();
			if(driver.findElements(By.className("empty-folder")).size() > 0){
				inboxFolder = driver.findElement(By.className("empty-folder"));
			}
		}
		return inboxFolder;
	}

	private void checkWelcomeDialog() {
		int retries = 2;
		for (int i = 0; i < retries; i++) {
			if(driver.findElements(By.className("ob-contactimport-btn-skip")).size() > 0){
				List<WebElement> dialogs = driver.findElements(By.className("ob-contactimport-btn-skip"));
				logger.info("Welcome Dialog found. Closing it.");
				WebElement welcomeDialog = dialogs.get(0);
				welcomeDialog.click();
			}else {
				logger.info("No Welcome Dialog was found.");
				driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
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
		if (driver.findElements(By.id("spam-label")).size() > 0) {
			WebDriverWait wait = new WebDriverWait(driver, 20);
			WebElement spamFolder = null;
			try {
				logger.info("Getting the Bulk Url");
				driver.findElement(By.id("spam-label")).click();
				driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
				spamFolder = driver.findElement(By.className("empty-folder"));
			} catch (Exception e) {
				logger.info("There are msgs in the spam folder, go get them Tiger!");
			}

			// Check if the spam folder is empty
			if (spamFolder != null && spamFolder.isDisplayed()) {
				logger.info("Spam Folder is empty! UOHOOO!");
			} else {
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
//						if(increment){
						if(true){
							j++;
						}
					}
				} else {
					logger.info("**********   No mlink found or no messages available   **********");
				}
			}
		}
	}

	private boolean dragAndDropNotSpam(WebDriverWait wait) {
		List<WebElement> spamMsgs = driver.findElements(By.className("list-view-item-container"));
		int randomPosition = obtainRandomMsgsPosition(spamMsgs);
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		logger.info("Selecting spam message");
		WebElement msg = spamMsgs.get(randomPosition);
		if(isWarmupDomain(msg)){
			WebElement inboxFolder = driver.findElement(By.className("inbox-label"));
			logger.info("******** Dragging Message to inbox ***********");
			(new Actions(driver)).dragAndDrop(msg, inboxFolder).perform();
			driver.navigate().refresh();
			return true;
		}
		return false;
	}

	private boolean isWarmupDomain(WebElement msg) {
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
		logger.info("Is not a warmup domain!!!");
		return false;
	}

	private boolean normalNotSpam(WebDriverWait wait) {
		try {
			List<WebElement> spamMsgs = driver.findElements(By.className("list-view-item-container"));
			logger.info("Obtaining a random message position so it can be open");
			int randomPosition = obtainRandomMsgsPosition(spamMsgs);
			driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
			
			WebElement msg = spamMsgs.get(randomPosition);
			
			if(isWarmupDomain(msg)){
				logger.info("Opening the spam message");
				msg.findElement(By.className("subj")).click();
				driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
				
				clickShowImages("show-text");
				driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	
				if (throwDice()) {
					logger.info("******** Clicking the not spam LIST button ***********");
					notSpamFromSubList();
				} else {
					logger.info("******** Clicking the not spam MAIN button ***********");
					driver.findElement(By.id("main-btn-spam")).click();
					wait.until(ExpectedConditions.elementToBeClickable(By.className("list-view-item-container")));
				}
				return true;
			}else{
//				logger.info("Getting the Bulk Url");
//				driver.findElement(By.id("spam-label")).click();
//				Thread.sleep(randInt(1000, 3000));
				return false;
			}
		} catch (Exception e) {
			logger.info("Way too fast Usain Bolt...Let's go to spam folder and keep going");
			logger.info("This is the error " + e.getMessage());
			driver.findElement(By.id("spam-label")).click();
			return false;
		}
	}

	private void notSpamFromSubList() {
		List<WebElement> elements = driver.findElements(By.className("card-actions-menu"));
		logger.info("Clicking not spam from submenu");
		for (WebElement ahref : elements) {
			if (ahref.isDisplayed()) {
				logger.info("sublist is visible");
				jse.executeScript("arguments[0].scrollIntoView(true);", ahref);
				mouse.moveToElement(ahref);
				ahref.findElement(By.tagName("a")).click();
				List<WebElement> myList = driver.findElements(By.className("spamactions"));
				List<WebElement> submenuList = myList.get(0).findElements(By.tagName("li"));
				driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
				WebElement notSpamMultare = submenuList.get(1);
				logger.info("Clicking Not Spam!");
				notSpamMultare.click();
				driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
			}
			else {
				logger.info("sublist is not visible. Going back to bulk");
				driver.findElement(By.id("spam-label")).click();
				driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
			}
		}
	}

	public void clickRandomLink() {
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
					openInNewWindow(linksToGo.get(randomLinkNo));
//					openTab(aUrl);
//					switchToNewWindow();
//					switchToPreviousWindow();
				}
			}
		} else {
			logger.info("**********   No links found or none available  **********");
		}
	}
	
	@Override
	public void replyToEmail() {
		logger.info("Clicking the reply button");
//		String body = human.generateRandomBody(driver, wait);
//		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		
		WebElement reply = driver.findElement(By.id("btn-reply-sender"));
		reply.click();
		
		WebElement quickReply = driver.findElement(By.className("quickReply"));
		
		WebElement bodyMail = quickReply.findElement(By.id("rtetext"));
		bodyMail.click();
//		human.type(bodyMail, body);
		
		logger.info("Replying the email");
		WebElement send = quickReply.findElement(By.className("bottomToolbar")).findElement(By.className("default")).findElement(By.tagName("a"));
		send.click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public void replyToEmailFromSubList(WebDriverWait wait) {
		logger.info("Clicking the reply button from sublist");
		if(driver.findElements(By.className("addconvtitle")).size() > 0){
			List<WebElement> elements = driver.findElement(By.className("addconvtitle")).findElements(By.tagName("a"));
			validateIfElementIsVisible(elements.get(0));
			logger.info("Waiting for button to be clickable");
			driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
			logger.info("Replying the email from sublist");
			validateIfElementIsVisible(driver.findElement(By.className("bottomToolbar")).findElement(By.className("default")));
			driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		}
		
	}
	
	@Override
	public void forwardEmail(WebDriverWait wait) {
		logger.info("Clicking the forward button");
//		String body = human.generateRandomBody(driver, wait);
//		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		
		WebElement forward = driver.findElement(By.id("btn-forward"));
		forward.click();
		
		List<String[]> seeds = YahooRunnable.generateSeedsList();
		String[] seed = seeds.get(randInt(0, seeds.size()));
		String to = seed[0];
		
		WebElement quickReply = driver.findElement(By.className("quickReply"));
		WebElement toInput = quickReply.findElement(By.id("to-field"));
		logger.info("Filling to field");
		human.type(toInput,to);
		
		WebElement bodyMail = quickReply.findElement(By.id("rtetext"));
		bodyMail.click();
//		human.type(bodyMail, body);
		
		logger.info("Forwarding the email");
		WebElement send = quickReply.findElement(By.className("bottomToolbar")).findElement(By.className("default")).findElement(By.tagName("a"));
		send.click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	@Override
	public void forwardEmailFromSubList(WebDriverWait wait) {
		logger.info("Forwarding an email");
		if(driver.findElements(By.className("addconvtitle")).size() > 0){
			List<WebElement> elements = driver.findElement(By.className("addconvtitle")).findElements(By.tagName("a"));
			validateIfElementIsVisible(elements.get(2));
			logger.info("Waiting for button to be clickable");
			driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
			List<String[]> seeds = YahooRunnable.generateSeedsList();
			String[] seed = seeds.get(randInt(0, seeds.size()));
			String to = seed[0];
			WebElement toInput = driver.findElement(By.id("to-field"));
			logger.info("Filling to field");
			human.type(toInput,to);
			logger.info("Sending email forward");
			validateIfElementIsVisible(driver.findElement(By.className("bottomToolbar")).findElement(By.className("default")));
			driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		}
	}
	
	@Override
	public void sendEmail() {
		List<String[]> seeds = YahooRunnable.generateSeedsList();
		String[] seed = seeds.get(randInt(0, seeds.size()));
		String to = seed[0];
		WebElement compose = driver.findElement(By.className("btn-compose"));
		logger.info("Clicking compose button");
		compose.click();
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		WebElement toInput = driver.findElement(By.id("to-field"));
		logger.info("Filling to field");
		human.type(toInput,to);
		WebElement subjectInput = driver.findElement(By.id("subject-field"));
		logger.info("Filling subject field");
		human.type(subjectInput,"Need random subject");
		WebElement bodyInput = driver.findElement(By.id("rtetext"));
		logger.info("Filling body field");
		human.type(bodyInput,"Need random body");
		WebElement sendButton = driver.findElement(By.className("bottomToolbar")).findElement(By.tagName("span")).findElement(By.tagName("a"));
		logger.info("Clicking send button");
		sendButton.click();
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	}
	
	@Override
	public void clickSpam() {
		List<WebElement> elements = driver.findElements(By.className("card-actions-menu"));
		for (WebElement ahref : elements) {
			if (ahref.isDisplayed()) {
				logger.info("Clicking more from submenu");
				jse.executeScript("arguments[0].scrollIntoView(true);", ahref);
				mouse.moveToElement(ahref);
				ahref.findElement(By.tagName("a")).click();
				List<WebElement> myList = driver.findElements(By.className("spamactions"));
				List<WebElement> submenuList = myList.get(0).findElements(By.tagName("li"));
				driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
				WebElement spam = submenuList.get(0);
				logger.info("Clicking spam!");
				spam.click();
				driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
			}
		}
		
	}

	/**
	 * @param element
	 */
	private void validateIfElementIsVisible(WebElement element) {
		logger.info("Validating element visibility");
		if(element.isDisplayed()){
			logger.info("Element is visible");
			element.click();
		}else{
			logger.info("Element is not visible, not doing anything, please review it");
//			mouse.moveToElement(element).build().perform();
//			String keysPressed =  Keys.chord(Keys.CONTROL, Keys.RETURN);
//			element.sendKeys(keysPressed) ;
//			((JavascriptExecutor)driver).executeScript("arguments[0].checked = true;", element);
//			element.click();
		}
	}

	
	@Override
	public void addToAddressBook() {
//		WebDriverWait wait = new WebDriverWait(driver, 30);
//		logger.info("Going to contacts section");
//		driver.findElement(By.className("nav-item-contacts")).click();
//		
//		
////		logger.info(driver.findElements(By.className("listnav-label primary-property-btn")).size());
//		logger.info(driver.findElements(By.className("listnav-label")).size());
//		logger.info(driver.findElements(By.className("primary-property-btn")).size());
//		
////		logger.info(driver.findElements(By.className("icon icon-add-contact")).size());
//		logger.info(driver.findElements(By.className("icon")).size());
//		logger.info(driver.findElements(By.className("icon-add-contact")).size());
//		
//		logger.info(driver.findElements(By.className("add-contact-text")).size());
//		//wait.until(ExpectedConditions.elementT(By.id("legend")));
//		//driver.navigate().refresh();
//		Thread.sleep(randInt(1500, 2500));
//	//	SubscriberRunnable.writeToFile("lalala.html", driver.getPageSource());
////		driver.findElement(By.partialLinkText("Contact nou"));
//		Thread.sleep(randInt(1500, 2500));
//		List <WebElement> inputs = driver.findElements(By.tagName("input"));
//		for (WebElement webElement : inputs) {
//			if (webElement.isDisplayed() && webElement.isEnabled()) {
//				webElement.sendKeys("asdasdasd");
//			}
//		}
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
