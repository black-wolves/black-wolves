/**
 * 
 */
package com.blackwolves.seeder.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.slf4j.Logger;

import com.blackwolves.seeder.Human;
import com.blackwolves.seeder.Seed;
import com.blackwolves.seeder.YahooRunnable;
import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

/**
 * @author gastondapice
 *
 */
public class OldMethods {
	
	protected static Logger logger;
	protected WebDriver driver;
	protected Human human;
	protected Seed seed;
	
	public static int differenceBetweenTimestamps(Timestamp now, Timestamp now2) {
//		Timestamp now = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
//		Timestamp oneHourAgo = new Timestamp(System.currentTimeMillis() - (60 * 60 * 1000));
		long diff = now2.getTime() - now.getTime();
		// int diffHours = (int) (diff / (60 * 60 * 1000));
		// int diffDays = (int) (diff / (24 * 60 * 60 * 1000));
		int diffMin = (int) (diff / (60 * 1000));
		// int diffSec = (int) (diff / (1000));
		return diffMin;
	}

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
			String body = this.generateRandomBody();
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
			logger.error("InterruptedException");
		} catch (NoSuchElementException e) {
			logger.error("NoSuchElementException");
		} catch (StaleElementReferenceException e) {
			logger.error("StaleElementReferenceException");
		} catch (ElementNotVisibleException e) {
			logger.error("ElementNotVisibleException");
		} catch (ElementNotFoundException e) {
			logger.error("ElementNotFoundException");
		} catch (UnhandledAlertException e) {
			logger.error("UnhandledAlertException");
		} catch (WebDriverException e) {
			logger.error("WebDriverException");
		}
	}

	public void forwardEmail() {
		try {
			logger.info("Clicking the forward button");
			Thread.sleep(randInt(2000, 3000));
			WebElement forward = driver.findElement(By.id("btn-forward"));
			forward.click();

			String to = this.generateRandomTo(seed);

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
			logger.error("InterruptedException");
		} catch (NoSuchElementException e) {
			logger.error("NoSuchElementException");
		} catch (StaleElementReferenceException e) {
			logger.error("StaleElementReferenceException");
		} catch (ElementNotVisibleException e) {
			logger.error("ElementNotVisibleException");
		} catch (ElementNotFoundException e) {
			logger.error("ElementNotFoundException");
		} catch (UnhandledAlertException e) {
			logger.error("UnhandledAlertException");
		} catch (WebDriverException e) {
			logger.error("WebDriverException");
		}

	}

	public void sendEmail() {
		try {
			logger.info("Clicking compose button");
			Thread.sleep(randInt(2000, 3000));
			WebElement compose = driver.findElement(By.className("btn-compose"));
			compose.click();

			String to = this.generateRandomTo(seed);

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
			human.type(subjectInput, this.generateRandomSubject());
			Thread.sleep(randInt(1500, 2500));
			subjectInput.sendKeys(Keys.TAB);

			Thread.sleep(randInt(1500, 2500));
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			String body = this.generateRandomBody();
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
			logger.error("InterruptedException");
		} catch (NoSuchElementException e) {
			logger.error("NoSuchElementException");
		} catch (StaleElementReferenceException e) {
			logger.error("StaleElementReferenceException");
		} catch (ElementNotVisibleException e) {
			logger.error("ElementNotVisibleException");
		} catch (ElementNotFoundException e) {
			logger.error("ElementNotFoundException");
		} catch (UnhandledAlertException e) {
			logger.error("UnhandledAlertException");
		} catch (WebDriverException e) {
			logger.error("WebDriverException");
		}
	}
	
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
			logger.error("InterruptedException");
		} catch (NoSuchElementException e) {
			logger.error("NoSuchElementException");
		} catch (StaleElementReferenceException e) {
			logger.error("StaleElementReferenceException");
		} catch (ElementNotVisibleException e) {
			logger.error("ElementNotVisibleException");
		} catch (ElementNotFoundException e) {
			logger.error("ElementNotFoundException");
		} catch (UnhandledAlertException e) {
			logger.error("UnhandledAlertException");
		} catch (WebDriverException e) {
			logger.error("WebDriverException");
		}
	}
	
	// NOT WORKING
	public void replyToEmailFromSubList() {
		logger.info("Clicking the reply button from sublist");
		try {
			// String body = this.generateRandomBody(driver, wait);
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
			logger.error("InterruptedException");
		} catch (NoSuchElementException e) {
			logger.error("NoSuchElementException");
		} catch (StaleElementReferenceException e) {
			logger.error("StaleElementReferenceException");
		} catch (ElementNotVisibleException e) {
			logger.error("ElementNotVisibleException");
		} catch (ElementNotFoundException e) {
			logger.error("ElementNotFoundException");
		} catch (UnhandledAlertException e) {
			logger.error("UnhandledAlertException");
		} catch (WebDriverException e) {
			logger.error("WebDriverException");
		}
	}

	// NOT WORKING
	public void forwardEmailFromSubList() {
		logger.info("Clicking the forward button from sublist");
		try {
			// String body = this.generateRandomBody(driver);
			// Thread.sleep(randInt(2000, 3000));

			Thread.sleep(randInt(2000, 3000));
			List<WebElement> elements = driver.findElement(By.className("addconvtitle")).findElements(By.tagName("a"));
			WebElement forward = elements.get(2);
			forward.click();

			String to = this.generateRandomTo(seed);

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
			logger.error("InterruptedException");
		} catch (NoSuchElementException e) {
			logger.error("NoSuchElementException");
		} catch (StaleElementReferenceException e) {
			logger.error("StaleElementReferenceException");
		} catch (ElementNotVisibleException e) {
			logger.error("ElementNotVisibleException");
		} catch (ElementNotFoundException e) {
			logger.error("ElementNotFoundException");
		} catch (UnhandledAlertException e) {
			logger.error("UnhandledAlertException");
		} catch (WebDriverException e) {
			logger.error("WebDriverException");
		}
	}
	
	public boolean isWarmupDomain(boolean inbox, WebElement msg) {
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
	
	public static int randInt(int min, int max) {
		Random rand = new Random();
		// nextInt is normally exclusive of the top value, so add 1 to make it inclusive
		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}
	
	
	/**
	 * @return
	 */
	public static List<String[]> generateIpsList() {
		List<String[]> ips = new ArrayList<String[]>();
//		try {
//			CSVReader ipsReader = new CSVReader(new FileReader(Constant.ROUTE + "ip_curl.txt"));
//			ips = ipsReader.readAll();
//		} catch (FileNotFoundException e) {
//			logger.error(e.getMessage(), e);
//		} catch (IOException e) {
//			logger.error(e.getMessage(), e);
//		}
		return ips;
	}

	/**
	 * @return
	 */
	public static List<String[]> generateDomainsList() {
		List<String[]> domains = new ArrayList<String[]>();
//		try {
//			CSVReader domainsReader = new CSVReader(new FileReader(Constant.ROUTE + "domains.txt"));
//			domains = domainsReader.readAll();
//		} catch (FileNotFoundException e) {
//			logger.error(e.getMessage(), e);
//		} catch (IOException e) {
//			logger.error(e.getMessage(), e);
//		}
		return domains;
	}
	
	/**
	 * 
	 * @param mySeed
	 * @return
	 */
	public String generateRandomTo(Seed mySeed) {
		String myMail = mySeed.getUser();
		List<String[]> seeds = YahooRunnable.generateSeedsList("seeds.csv");
		String to = Constant.EMPTY_STRING;
		do {
			String[] seed = seeds.get(YahooRunnable.randInt(0, seeds.size()-1));
			to = seed[0];
		} while (myMail.equals(to));
		return to;
	}
	
	/**
	 * 
	 * @return
	 */
	public String generateRandomSubject() {
		logger.info("Generating a random subject");
		String content = generateRandomContent();
		String subject = Constant.EMPTY_STRING;
		int numberOfWords = Integer.MAX_VALUE;
		int min = content.length()<=Constant.MIN_SUBJECT_WORDS?content.length():Constant.MIN_SUBJECT_WORDS;
		int max = content.length()<=Constant.MAX_SUBJECT_WORDS?content.length():Constant.MAX_SUBJECT_WORDS;
		while (numberOfWords >= max || numberOfWords <= min) {
			subject = content.substring(YahooRunnable.randInt(1, content.length()));
			numberOfWords = subject.length();
		}
		return subject;
	}

	/**
	 * 
	 * @return
	 */
	public String generateRandomBody() {
		logger.info("Generating a random body");
		String content = generateRandomContent();
		String body = Constant.EMPTY_STRING;
		int numberOfWords = Integer.MAX_VALUE;
		int min = content.length()<=Constant.MIN_BODY_WORDS?content.length():Constant.MIN_BODY_WORDS;
		int max = content.length()<=Constant.MAX_BODY_WORDS?content.length():Constant.MAX_BODY_WORDS;
		while (numberOfWords >= max || numberOfWords <= min) {
			body = content.substring(YahooRunnable.randInt(1, content.length()));
			numberOfWords = body.length();
		}
		return body;
	}
	
	/**
	 * 
	 * @return
	 */
	public String generateRandomContent() {
		logger.info("Getting content from the PDF");
		String page = Constant.EMPTY_STRING;
		PdfReader reader = null;
		try {
			reader = new PdfReader(Constant.PDF_FILE);
			int randomPage = YahooRunnable.randInt(3, reader.getNumberOfPages());
			page = PdfTextExtractor.getTextFromPage(reader, randomPage);
			reader.close();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}finally{
			if(reader!=null){
				reader.close();
			}
		}
		return page.replaceAll("(\\r|\\n|\\r\\n)+", "");
	}
	
	/**
	 * 
	 */
	public void addToAddressBook(WebDriver driver) {
//		List<String[]> domains = this.generateDomainsList();
//		WebElement element;
//		for (String[] d : domains) {
//			try {
//				if (driver.findElements(By.className("list-view-item-container")).size() > 0) {
//					logger.info("Adding domains to address book");
//					element = driver.findElement(By.className("list-view-item-container"))
//							.findElement(By.className("first"));
//					rightClick(driver, element);
//					Thread.sleep(YahooRunnable.randInt(2500, 3500));
//					WebElement menu = driver.findElement(By.id("menu-msglist"));
//
//					List<WebElement> li = menu.findElements(By.className("onemsg"));
//					WebElement addContact = li.get(3);
//					addContact.click();
//					Thread.sleep(YahooRunnable.randInt(2500, 3500));
//
//					WebElement modal = driver.findElement(By.id("modal-kiosk-addcontact"));
//
//					WebElement givenName = modal.findElement(By.id("givenName"));
//					givenName.clear();
//					human.type(givenName, d[0]);
//					givenName.sendKeys(Keys.TAB);
//					WebElement middleName = modal.findElement(By.id("middleName"));
//					middleName.clear();
//					WebElement familyName = modal.findElement(By.id("familyName"));
//					familyName.clear();
//					WebElement email = modal.findElement(By.className("field-lg"));
//					email.clear();
//					human.type(email, "newsletter@" + d[0]);
//					email.sendKeys(Keys.TAB);
//					Thread.sleep(YahooRunnable.randInt(2500, 3500));
//					WebElement save = modal.findElement(By.id("saveModalOverlay"));
//					save.click();
//					Thread.sleep(YahooRunnable.randInt(2500, 3500));
//					if (driver.findElements(By.className("error")).size() > 0) {
//						WebElement cancel = driver.findElement(By.id("cancelModalOverlay"));
//						cancel.click();
//						logger.info("Contact was not added: " + d[0]);
//						Thread.sleep(YahooRunnable.randInt(2500, 3500));
//					} else {
//						WebElement done = driver.findElement(By.id("doneModalOverlay"));
//						done.click();
//						logger.info("Contact added: " + d[0]);
//						Thread.sleep(YahooRunnable.randInt(2500, 3500));
//					}
//				} else {
//					logger.info("No emails in inbox, we can't add the domains to the address book");
//				}
//			} catch (InterruptedException e) {
//				logger.error(e.getMessage(), e);
//			} catch (NoSuchElementException e) {
//				logger.error("NoSuchelementException");
//			} catch (StaleElementReferenceException e) {
//				logger.error("StaleElementReferenceException");
//			} catch (ElementNotVisibleException e) {
//				logger.error("ElementNotVisibleException");
//			} catch (ElementNotFoundException e) {
//				logger.error("ElementNotFoundException");
//			}
//		}
	}

	public void createNewFolder(WebDriver driver) {
		try {
			if (validateIfFolderExists(driver)) {
				logger.info("Folder already exists");
			} else {
				logger.info("Creating new folder");
				WebElement newFolder = driver.findElement(By.id("btn-newfolder"));
				newFolder.click();
				Thread.sleep(YahooRunnable.randInt(1500, 2500));
				WebElement newFolderInput = driver.findElement(By.id("newFolder"));
				human.type(newFolderInput, Constant.ALL);
				WebElement ok = driver.findElement(By.id("okayModalOverlay"));
				ok.click();
				Thread.sleep(YahooRunnable.randInt(2500, 3500));
				if (driver.findElements(By.id("newFolderErr")).size() > 0) {
					logger.info("Folder already exists");
					WebElement cancel = driver.findElement(By.id("cancelModalOverlay"));
					cancel.click();
					Thread.sleep(YahooRunnable.randInt(2500, 3500));
				} else {
					logger.info("Folder created");
					Thread.sleep(YahooRunnable.randInt(2500, 3500));
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
	 * @return
	 */
	public boolean validateIfFolderExists(WebDriver driver) {
		if (driver.findElements(By.id("Folders")).size() > 0) {
			if (driver.findElement(By.id("Folders")).findElements(By.className("foldername")).size() > 0) {
				return driver.findElement(By.id("Folders")).findElement(By.className("foldername")).getText()
						.equals(Constant.ALL);
			}

		}
		return false;
	}
	
	/**
	 * 
	 * @param element
	 */
	public void rightClick(WebDriver driver, WebElement element) {
		try {
			Actions action = new Actions(driver).contextClick(element);
			action.build().perform();
			logger.info("Sucessfully Right clicked on the element");
		} catch (StaleElementReferenceException e) {
			logger.error("Element is not attached to the page document " + e.getStackTrace());
		} catch (NoSuchElementException e) {
			logger.error("Element " + element + " was not found in DOM " + e.getStackTrace());
		} catch (Exception e) {
			logger.error("Element " + element + " was not clickable " + e.getStackTrace());
		}
	}
	
	public static void writeSeedToFile(String seed) {
		PrintWriter pw = null;
		try {
			List<String> usedSeeds = readSeedsFromFile();
			pw = new PrintWriter(new FileWriter(Constant.ROUTE + "in-use-seeds.txt"));
			for (String usedSeed : usedSeeds) {
				pw.write(usedSeed);
				pw.write("\n");
			}
			pw.write(seed);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally{
			if(pw!=null){
				pw.close();
			}
		}
	}

	public static List<String> readSeedsFromFile() {
		List<String> list = null;
		try {
			File file = new File(Constant.ROUTE + "in-use-seeds.txt");
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			list = new ArrayList<String>();
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				list.add(line);
			}
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public static void deleteSeedFromFile(String seed) {
		PrintWriter pw = null;
		try {
			List<String> usedSeeds = readSeedsFromFile();
			pw = new PrintWriter(new FileWriter(Constant.ROUTE + "in-use-seeds.txt"));
			for (String usedSeed : usedSeeds) {
				if(!seed.equals(usedSeed)){
					pw.write(usedSeed);
					pw.write("\n");
				}
			}
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally{
			if(pw!=null){
				pw.close();
			}
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.blackwolves.selenium.seeder.YahooHandler#spamHandler(org.openqa.
	 * selenium.WebDriver, java.lang.String[])
	 */
	public void processSpam(Seed seed) {

//		try {
//			logger.info("Process Spam");
//
//			if (Constant.SPECIFIC.equals(Seeder.type)
//					|| Constant.DESTROYER.equals(Seeder.type)) {
//
//				logger.info("RemoveConversationMailView");
//				removeConversationMailView();
//			}
//
//		} catch (MoveTargetOutOfBoundsException e) {
//			logger.info("Process Spam MoveTargetOutOfBoundsException ");
//		}
//
//		if (validateSpamFolder()) {
//			logger.info("There are msgs in the spam folder, go get them Tiger!");
//			logger.info("Seeder type: " + Seeder.type);
//			if (Constant.SPECIFIC.equals(Seeder.type)
//					|| Constant.DESTROYER.equals(Seeder.type)) {
//
//				logger.info("Checking all NOT SPAM");
//				WebElement checkbox = driver.findElement(By
//						.xpath("//span[@id='btn-ml-cbox']/label/input"));
//				if (!checkbox.isSelected()) {
//					checkbox.click();
//				}
//
//				driver.findElement(By.xpath("//*[@id='btn-not-spam']")).click();
//
//			} else if (Constant.MULTIPLE.equals(Seeder.type)) {
//				List<WebElement> spamMsgs = driver.findElements(By
//						.className("list-view-item"));
//
//				logger.info("Percentage is " + PERCENTAGE);
//				int percentage = (int) (spamMsgs.size() * PERCENTAGE);
//
//				if (percentage > 10) {
//					percentage = 3;
//				}
//
//				for (int j = 0; j < percentage; j++) {
//
//					try {
//						logger.info(j + " emails not spammed "
//								+ (percentage - j) + " emails to go");
//						int chances = randInt(0, 10);
//						Thread.sleep(randInt(2000, 3000));
//						if (chances <= 8) {
//							normalNotSpam();
//						} else {
//							dragAndDropNotSpam();
//						}
//
//					} catch (InterruptedException e) {
//						logger.error(
//								"InterruptedException for seed: "
//										+ seed.getUser() + " with password: "
//										+ seed.getPassword() + " "
//										+ e.getMessage() + " ", e);
//					} catch (NoSuchElementException e) {
//						logger.error(
//								"NoSuchElementException for seed: "
//										+ seed.getUser() + " with password: "
//										+ seed.getPassword() + " "
//										+ e.getMessage() + " ", e);
//					} catch (StaleElementReferenceException e) {
//						logger.error(
//								"StaleElementReferenceException for seed: "
//										+ seed.getUser() + " with password: "
//										+ seed.getPassword() + " "
//										+ e.getMessage() + " ", e);
//					} catch (ElementNotVisibleException e) {
//						logger.error(
//								"ElementNotVisibleException for seed: "
//										+ seed.getUser() + " with password: "
//										+ seed.getPassword() + " "
//										+ e.getMessage() + " ", e);
//					} catch (ElementNotFoundException e) {
//						logger.error(
//								"ElementNotFoundException for seed: "
//										+ seed.getUser() + " with password: "
//										+ seed.getPassword() + " "
//										+ e.getMessage() + " ", e);
//					} catch (UnhandledAlertException e) {
//						logger.error(
//								"UnhandledAlertException for seed: "
//										+ seed.getUser() + " with password: "
//										+ seed.getPassword() + " "
//										+ e.getMessage() + " ", e);
//
//					} catch (WebDriverException e) {
//						logger.error(
//								"WebDriverException for seed: "
//										+ seed.getUser() + " with password: "
//										+ seed.getPassword() + " "
//										+ e.getMessage() + " ", e);
//					}
//				}
//			}
//		} else {
//			logger.info("Spam Folder is empty! UOHOOO!");
//		}
	}

	/**
	 * @param driver
	 * @param spamFolder
	 * @return
	 */
//	public boolean validateSpamFolder() {
//		logger.info("Entering validateSpamFolder ");
//		try {
//			if (driver.findElements(By.id("spam-label")).size() > 0) {
//				WebElement spam = driver.findElement(By.id("spam-label"));
//				spam.click();
//				Thread.sleep(randInt(2000, 3000));
//				if (driver.findElements(By.className("list-view-item")).size() > 0) {
//					return true;
//				}
//			}
//		} catch (InterruptedException e) {
//			logger.error("InterruptedException for seed: " + seed.getUser() + " with password: " + seed.getPassword()
//					+ " " + e.getMessage() + " ", e);
//		} catch (NoSuchElementException e) {
//			logger.error("NoSuchElementException for seed: " + seed.getUser() + " with password: " + seed.getPassword()
//					+ " " + e.getMessage() + " ", e);
//		} catch (StaleElementReferenceException e) {
//			logger.error("StaleElementReferenceException for seed: " + seed.getUser() + " with password: "
//					+ seed.getPassword() + " " + e.getMessage() + " ", e);
//		} catch (ElementNotVisibleException e) {
//			logger.error("ElementNotVisibleException for seed: " + seed.getUser() + " with password: "
//					+ seed.getPassword() + " " + e.getMessage() + " ", e);
//		} catch (ElementNotFoundException e) {
//			logger.error("ElementNotFoundException for seed: " + seed.getUser() + " with password: "
//					+ seed.getPassword() + " " + e.getMessage() + " ", e);
//		} catch (UnhandledAlertException e) {
//			logger.error("UnhandledAlertException for seed: " + seed.getUser() + " with password: " + seed.getPassword()
//					+ " " + e.getMessage() + " ", e);
//
//		} catch (WebDriverException e) {
//			logger.error("WebDriverException for seed: " + seed.getUser() + " with password: " + seed.getPassword()
//					+ " " + e.getMessage() + " ", e);
//		}
//		return false;
//	}

//	public boolean dragAndDropNotSpam() {
//		try {
//			List<WebElement> spamMsgs = driver.findElements(By.className("list-view-item"));
//			int randomPosition = obtainRandomMsgsPosition(spamMsgs);
//			Thread.sleep(randInt(2000, 3000));
//			logger.info("Selecting spam message");
//			WebElement msg = null;
//			try {
//				msg = spamMsgs.get(randomPosition);
//
//			} catch (ArrayIndexOutOfBoundsException e) {
//				logger.error("dragAndDropNotSpam: ArrayIndexOutOfBoundsException");
//				return false;
//			}
//
//			// if (isWarmupDomain(false, msg)) {
//			WebElement inboxFolder = driver.findElement(By.className("inbox-label"));
//			logger.info("******** Dragging Message to inbox ***********");
//			(new Actions(driver)).dragAndDrop(msg, inboxFolder).perform();
//
//			return true;
//		} catch (InterruptedException e) {
//			logger.error("InterruptedException for seed: " + seed.getUser() + " with password: " + seed.getPassword()
//					+ " " + e.getMessage() + " ", e);
//		} catch (NoSuchElementException e) {
//			logger.error("NoSuchElementException for seed: " + seed.getUser() + " with password: " + seed.getPassword()
//					+ " " + e.getMessage() + " ", e);
//		} catch (StaleElementReferenceException e) {
//			logger.error("StaleElementReferenceException for seed: " + seed.getUser() + " with password: "
//					+ seed.getPassword() + " " + e.getMessage() + " ", e);
//		} catch (ElementNotVisibleException e) {
//			logger.error("ElementNotVisibleException for seed: " + seed.getUser() + " with password: "
//					+ seed.getPassword() + " " + e.getMessage() + " ", e);
//		} catch (ElementNotFoundException e) {
//			logger.error("ElementNotFoundException for seed: " + seed.getUser() + " with password: "
//					+ seed.getPassword() + " " + e.getMessage() + " ", e);
//		} catch (UnhandledAlertException e) {
//			logger.error("UnhandledAlertException for seed: " + seed.getUser() + " with password: " + seed.getPassword()
//					+ " " + e.getMessage() + " ", e);
//
//		} catch (WebDriverException e) {
//			logger.error("WebDriverException for seed: " + seed.getUser() + " with password: " + seed.getPassword()
//					+ " " + e.getMessage() + " ", e);
//		}
//		return false;
//	}

//	public boolean normalNotSpam() {
//		try {
//			List<WebElement> spamMsgs = driver.findElements(By.className("list-view-item"));
//			int randomPosition = obtainRandomMsgsPosition(spamMsgs);
//			logger.info("Obtaining a random message position so it can be open " + randomPosition);
//
//			Thread.sleep(randInt(2000, 3000));
//
//			WebElement currentMsg = spamMsgs.get(randomPosition);
//
//			// if (isWarmupDomain(false, currentMsg)) {
//			logger.info("Opening the spam message");
//			currentMsg.findElement(By.className("subj")).click();
//
//			Thread.sleep(randInt(1000, 2000));
//
//			clickShowImages("show-text");
//			Thread.sleep(randInt(1000, 2000));
//			scrollToBottom(driver);
//			// REMOVED CLICK FROM LIST SINCE IT IS BREAKING IN THE SERVER
//			if (false) {
//				logger.info("******** Clicking the not spam LIST button ***********");
//				notSpamFromSubList();
//			} else {
//				logger.info("******** Clicking the not spam MAIN button ***********");
//				driver.findElement(By.id("main-btn-spam")).click();
//				Thread.sleep(randInt(2500, 3500));
//			}
//			return true;
//			// } else {
//			// return false;
//			// }
//		} catch (InterruptedException e) {
//			logger.error("InterruptedException for seed: " + seed.getUser() + " with password: " + seed.getPassword()
//					+ " " + e.getMessage() + " ", e);
//		} catch (NoSuchElementException e) {
//			logger.error("NoSuchElementException for seed: " + seed.getUser() + " with password: " + seed.getPassword()
//					+ " " + e.getMessage() + " ", e);
//		} catch (StaleElementReferenceException e) {
//			logger.error("StaleElementReferenceException for seed: " + seed.getUser() + " with password: "
//					+ seed.getPassword() + " " + e.getMessage() + " ", e);
//		} catch (ElementNotVisibleException e) {
//			logger.error("ElementNotVisibleException for seed: " + seed.getUser() + " with password: "
//					+ seed.getPassword() + " " + e.getMessage() + " ", e);
//		} catch (ElementNotFoundException e) {
//			logger.error("ElementNotFoundException for seed: " + seed.getUser() + " with password: "
//					+ seed.getPassword() + " " + e.getMessage() + " ", e);
//		} catch (UnhandledAlertException e) {
//			logger.error("UnhandledAlertException for seed: " + seed.getUser() + " with password: " + seed.getPassword()
//					+ " " + e.getMessage() + " ", e);
//
//		} catch (WebDriverException e) {
//			logger.error("WebDriverException for seed: " + seed.getUser() + " with password: " + seed.getPassword()
//					+ " " + e.getMessage() + " ", e);
//		}
//		driver.findElement(By.id("spam-label")).click();
//		return false;
//	}

//	public void notSpamFromSubList() {
//		List<WebElement> elements = driver.findElements(By.className("card-actions-menu"));
//		logger.info("Clicking not spam from submenu");
//		for (WebElement ahref : elements) {
//			try {
//				if (ahref.isDisplayed()) {
//					logger.info("sublist is visible");
//					jse.executeScript("arguments[0].scrollIntoView(true);", ahref);
//					mouse.moveToElement(ahref);
//					ahref.findElement(By.tagName("a")).click();
//					List<WebElement> myList = driver.findElements(By.className("spamactions"));
//					List<WebElement> submenuList = myList.get(0).findElements(By.tagName("li"));
//					Thread.sleep(randInt(2000, 3000));
//					WebElement notSpamMultare = submenuList.get(1);
//					logger.info("Clicking Not Spam!");
//					notSpamMultare.click();
//					Thread.sleep(randInt(2000, 3000));
//				} else {
//					logger.info("sublist is not visible. Going back to bulk");
//					driver.findElement(By.id("spam-label")).click();
//					Thread.sleep(randInt(2000, 3000));
//				}
//			} catch (InterruptedException e) {
//				logger.error("InterruptedException for seed: " + seed.getUser() + " with password: "
//						+ seed.getPassword() + " " + e.getMessage() + " ", e);
//			} catch (NoSuchElementException e) {
//				logger.error("NoSuchElementException for seed: " + seed.getUser() + " with password: "
//						+ seed.getPassword() + " " + e.getMessage() + " ", e);
//			} catch (StaleElementReferenceException e) {
//				logger.error("StaleElementReferenceException for seed: " + seed.getUser() + " with password: "
//						+ seed.getPassword() + " " + e.getMessage() + " ", e);
//			} catch (ElementNotVisibleException e) {
//				logger.error("ElementNotVisibleException for seed: " + seed.getUser() + " with password: "
//						+ seed.getPassword() + " " + e.getMessage() + " ", e);
//			} catch (ElementNotFoundException e) {
//				logger.error("ElementNotFoundException for seed: " + seed.getUser() + " with password: "
//						+ seed.getPassword() + " " + e.getMessage() + " ", e);
//			} catch (UnhandledAlertException e) {
//				logger.error("UnhandledAlertException for seed: " + seed.getUser() + " with password: "
//						+ seed.getPassword() + " " + e.getMessage() + " ", e);
//
//			} catch (WebDriverException e) {
//				logger.error("WebDriverException for seed: " + seed.getUser() + " with password: " + seed.getPassword()
//						+ " " + e.getMessage() + " ", e);
//			}
//		}
//	}
	
//	/**
//	 * 
//	 * @param driver
//	 * @param from
//	 * @return
//	 */
//	private WebElement findMyMsgBySearchBox(WebDriver driver, String from) {
//		WebElement message = null;
//		try{
//			if(driver.findElements(By.className("typeahead-input-usertext")).size() > 0){
//				WebElement searchBox = driver.findElement(By.className("typeahead-input-usertext"));
//				String searchString = "From: " + from + " is: unread";
//				human.type(searchBox, searchString);
//				searchBox.sendKeys(Keys.ENTER);
//				Thread.sleep(10000);
//				List<WebElement> myMessages = new ArrayList<WebElement>();
//				if (driver.findElements(By.className("message-list-item")).size() > 0) {
//					myMessages = driver.findElements(By.className("message-list-item"));
//				}
//				if (myMessages.isEmpty()) {
//					return null;
//				}
//				logger.info("Getting first facebook message");
//				message = myMessages.get(0);
//			}
//		} catch (NoSuchElementException | ElementNotVisibleException | ElementNotFoundException | StaleElementReferenceException | UnhandledAlertException | InterruptedException e) {
//			logger.error("Error with Seed: " + seed.getUser() + " message: " + e.getMessage());
//		} catch (WebDriverException e) {
//			logger.error(e.getMessage(), e);
//		} catch (Exception e) {
//			logger.error(e.getMessage(), e);
//		}
//		return message;
//	}

}
