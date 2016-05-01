package com.blackwolves.mail;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blackwolves.behavior.AverageHuman;
import com.blackwolves.behavior.DumbHuman;
import com.blackwolves.behavior.FastHuman;
import com.blackwolves.behavior.Human;
import com.blackwolves.mail.util.Constant;
import com.blackwolves.mail.util.JDBC;
import com.gargoylesoftware.htmlunit.ElementNotFoundException;

/**
 * @author gaston.dapice
 *
 */
public class Login implements Runnable {

	private Logger logger = LoggerFactory.getLogger(Login.class);

	private Human human;
	
	private Seed seed;
	
	private String invalidMessage;
	
	private boolean mailChanged;
	
	private String action;

	public Login() {
		
	}

	/**
	 * 
	 * @param seed
	 * @param logger
	 * @param action
	 */
	public Login(Seed seed, Logger logger, String action) {
		this.seed = seed;
		this.logger = logger;
		this.action = action;
	}

	/**
	 * 
	 */
	public void run() {
		
		WebDriver driver = createWebDriver();
		
		human = generateRandomHumanUser();
		
		yahooLogin(Constant.Yahoo.YAHOO_MAIL_RO_URL, seed, driver);
		
		if (validateYahooVersion(driver, seed)) {
			
			if(mailChanged){
				JDBC.updateSeed(seed, mailChanged);
			}
			
			if(!Constant.Facebook.FACEBOOK_NAME.equals(action)){
			
				writeSeedToFile(seed, true);
				
				removeConversationMailView(driver);
				
			}
			
			if(seed.getBirthDate() == null){
				
				clickAllNotSapm(driver);
				
				gatherPersonalInfo(driver, seed);
				
				
			}
			
			searchAndConfirmAirBerlin(driver);
			
			searchAndConfirmDanielPink(driver);

			
			if(Constant.Facebook.FACEBOOK_NAME.equals(action)){
				
				boolean created = createNewAccount(driver);
				
				if(created){
					confirmFacebookAccount(driver);
				}
			}

			logger.info("Finished!!");
			
		} else {
			if(!Constant.Facebook.FACEBOOK_NAME.equals(action)){
				writeSeedToFile(seed, false);
				logger.info("Invalid seed detected");
			}
		}
		
		driver.close();
		driver.quit();
	}
	
	/**
	 * 
	 * @param yahooUrl
	 * @param seed
	 * @param driver
	 */
	private void yahooLogin(String yahooUrl, Seed seed, WebDriver driver) {
		logger.info("Trying to login in....");
		try {

			driver.get(yahooUrl);
			Thread.sleep(randInt(2000, 3000));

			WebElement accountInput = driver.findElement(By.id("login-username"));
			human.type(accountInput, seed.getUser());

			if(driver.findElements(By.id("login-signin")).size() > 0 && (Constant.Yahoo.CONTINUE.equals(driver.findElement(By.id("login-signin")).getText()) || Constant.Yahoo.Next.equals(driver.findElement(By.id("login-signin")).getText()))) {
				driver.findElement(By.id("login-signin")).click();
				Thread.sleep(randInt(2000, 3000));
				if(driver.findElements(By.id("mbr-login-error")).size() > 0){
					if(!driver.findElement(By.id("mbr-login-error")).getText().isEmpty()){
						return;
					}
				}
				WebElement passwordInput = driver.findElement(By.id("login-passwd"));
				human.type(passwordInput, seed.getPassword());
				Thread.sleep(randInt(2000, 3000));
				
			}else if (driver.findElements(By.id("login-passwd")).size() > 0) {
				WebElement passwordInput = driver.findElement(By.id("login-passwd"));
				human.type(passwordInput, seed.getPassword());
				Thread.sleep(randInt(2000, 3000));
			}
			if (driver.findElements(By.id("login-signin")).size() > 0) {
				driver.findElement(By.id("login-signin")).click();
				Thread.sleep(randInt(2000, 3000));
				if(driver.findElements(By.id("mbr-login-error")).size() > 0){
					if(!driver.findElement(By.id("mbr-login-error")).getText().isEmpty()){
						return;
					}
				}
				if (driver.findElements(By.id("login-signin")).size() > 0) {
					WebElement passwordInput = driver.findElement(By.id("login-passwd"));
					human.type(passwordInput, seed.getPassword());
					Thread.sleep(randInt(2000, 3000));
				}
			}
			if (driver.findElements(By.id("login-signin")).size() > 0) {
				driver.findElement(By.id("login-signin")).click();
				Thread.sleep(randInt(2000, 3000));
			} else {
				logger.info("Already logged in..Moving forward!");
			}
			if (driver.findElements(By.id("IAgreeBtnNew")).size() > 0) {
				mailChanged = true;
				String newUser = seed.getUser();
				if (driver.findElements(By.id("IAgreeBtnNew")).size() > 0) {
					newUser = driver.findElement(By.id("ymemformfield")).getText();
				}
				seed.setNewUser(newUser);
				String newFullSeed = seed.getNewUser() + "," + seed.getPassword();
				seed.setFullSeed(newFullSeed);
				driver.findElement(By.id("IAgreeBtnNew")).click();
				Thread.sleep(randInt(2000, 3000));
			}
		} catch (NoSuchElementException | ElementNotVisibleException | ElementNotFoundException | StaleElementReferenceException | UnhandledAlertException | InterruptedException e) {
			logger.error("Error with seed: " + seed.getUser() + " with password: " + seed.getPassword() + " message: " + e.getMessage());
		} catch (WebDriverException e) {
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 
	 * @param driver
	 * @param seed
	 */
	private boolean validateYahooVersion(WebDriver driver, Seed seed) {
		try {
			Thread.sleep(5000);
			if (driver.findElements(By.className("uh-srch-btn")).size() > 0) {
				logger.info("----------   Old yahoo version   ----------");
			} else if (driver.findElements(By.id("UHSearchProperty")).size() > 0) {
				logger.info("LOGGED IN!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				checkMultipleAccountsPanel(driver);
				return true;
			} else if (driver.findElements(By.id("mail-search-btn")).size() > 0) {
				logger.info("##########   New yahoo version   ##########");
			} else if (driver.findElements(By.id("comm-channel-module")).size() > 0) {
				logger.info("LOGGED IN and phone validation found!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				driver.get("http://mail.yahoo.com");
				return true;
			} else if (driver.findElements(By.id("skipbtn")).size() > 0) {
				logger.info("LOGGED IN and Dont get locked out of your account, clicking skip button!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				driver.findElement(By.id("skipbtn")).click();
				return true;
			} else if(driver.findElements(By.id("mbr-bd")).size() > 0){
				invalidMessage = "Not able to login, validate accout by phone";
				logger.info("==========   "+ invalidMessage +"   ==========");
			} else if(driver.findElements(By.id("mbr-login-error")).size() > 0){
				String error = driver.findElement(By.id("mbr-login-error")).getText();
				if(error.contains(Constant.Yahoo.ERROR_TEXT_1)){
					invalidMessage = "Not able to login, The email and password you entered dont match.";
					logger.info("==========   "+ invalidMessage +"   ==========");
				} else if(error.contains(Constant.Yahoo.ERROR_TEXT_2)){
					invalidMessage = "Sorry, we dont recognize this email.";
				} else{
					invalidMessage = "mbr-login-error not found";
					 logger.info("==========   "+ invalidMessage +"   ==========");
					getScreenShot(driver, randInt(1, 100) + "newVersion" + UUID.randomUUID());
				}
			} else{
				invalidMessage = "THERE IS A NEW YAHOO VERSION IN TOWN";
				 logger.info("==========   "+ invalidMessage +"   ==========");
				 getScreenShot(driver, randInt(1, 100) + "newVersion" + UUID.randomUUID());
			}
		} catch (NoSuchElementException | ElementNotVisibleException | ElementNotFoundException | StaleElementReferenceException | UnhandledAlertException | InterruptedException e) {
			logger.error("Error with seed: " + seed.getUser() + " with password: " + seed.getPassword() + " message: " + e.getMessage());
		} catch (WebDriverException e) {
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return false;
	}
	
	/**
	 * 
	 * @param driver
	 */
	private void removeConversationMailView(WebDriver driver) {

		try {
			Actions myMouse = new Actions(driver);
			WebElement settings = driver.findElement(By.id("yucs-help"));
			myMouse.moveToElement(settings).build().perform();
			Thread.sleep(randInt(2000, 3000));
			settings = driver.findElement(By.id("yucs-help"));
			myMouse.moveToElement(settings).build().perform();
			Thread.sleep(randInt(2000, 3000));
			logger.info("Moving to configuration wheel");
			Thread.sleep(randInt(2000, 3000));
			if (driver.findElements(By.xpath("//div[@id='yucs-help_inner']")).size() > 0) {
				settings = driver.findElement(By.id("yucs-help"));
				myMouse.moveToElement(settings).build().perform();
				Thread.sleep(randInt(2000, 3000));
				if(driver.findElements((By.xpath("//div[@id='yucs-help_inner']/ul/li[2]/a"))).size() > 0){
					driver.findElement(By.xpath("//div[@id='yucs-help_inner']/ul/li[2]/a")).click();
					Thread.sleep(randInt(2000, 3000));
					if(driver.findElements(By.xpath("//input[@id='options-enableConv']")).size() > 0){
						if (driver.findElement(By.xpath("//input[@id='options-enableConv']")).isSelected()) {
							logger.info("Conversation mode is on. Turning off.");
							driver.findElement(By.xpath("//input[@id='options-enableConv']")).click();
							Thread.sleep(randInt(2000, 3000));
							if(driver.findElements(By.className("selectable")).size() > 0){
								driver.findElement(By.xpath("//ul[@class='selectable']/li[6]/a")).click();
								Thread.sleep(randInt(2000, 3000));
								driver.findElement(By.xpath("//ul[@class='options-settings-pane']/li/div[2]/div/select/option[2]")).click();
								Thread.sleep(randInt(2000, 3000));
							}
						}
					}
					if(driver.findElements(By.className("selectable")).size() > 0){
						if(driver.findElements(By.xpath("//ul[@class='selectable']/li[6]/a")).size() > 0){
							driver.findElement(By.xpath("//ul[@class='selectable']/li[6]/a")).click();
							Thread.sleep(randInt(2000, 3000));
							if(driver.findElements(By.xpath("//ul[@class='options-settings-pane']/li/div[2]/div/select/option[2]")).size() > 0){
								driver.findElement(By.xpath("//ul[@class='options-settings-pane']/li/div[2]/div/select/option[2]")).click();
								Thread.sleep(randInt(2000, 3000));
							}
						}
					}
					driver.findElement(By.xpath("//button[@class='left right default btn']")).click();
					Thread.sleep(randInt(2000, 3000));
				}
			}
		} catch (NoSuchElementException | ElementNotVisibleException | ElementNotFoundException | StaleElementReferenceException | UnhandledAlertException | InterruptedException e) {
			logger.error("Error with seed: " + seed.getUser() + " with password: " + seed.getPassword() + " message: " + e.getMessage());
		} catch (WebDriverException e) {
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 
	 * @param driver
	 */
	private void checkMultipleAccountsPanel(WebDriver driver) {
		if (driver.findElements(By.id("imapInOnboardDlg")).size() > 0) {
			logger.info("Multiple accounts Panel Found");
			if (driver.findElement(By.xpath("//div[@id='imapInOnboardDlg']/a")).isDisplayed()) {
				driver.findElement(By.xpath("//div[@id='imapInOnboardDlg']/a")).click();
			}
		}
	}
	
	/**
	 * 
	 * @param driver
	 * @param seed
	 */
	private void gatherPersonalInfo(WebDriver driver, Seed seed) {
		try {
			logger.info("Gathering personal information");
			driver.get(Constant.Facebook.PERSONAL_INFO_PAGE);
			
			WebElement fullname = driver.findElement(By.id("fullname"));
			String[] fullNameElement = fullname.getText().trim().split(Constant.LINE_BREAK);
			String[] fullName = fullNameElement[0].split(Constant.BLANK_SPACE);
			String firstName = Character.toUpperCase(fullName[0].charAt(0)) + fullName[0].substring(1);
			String lastName = Character.toUpperCase(fullName[1].charAt(0)) + fullName[1].substring(1);
			seed.setFirstName(firstName);
			seed.setLastName(lastName);
			
			WebElement genderElement = driver.findElement(By.id("gender"));
			String[] gender = genderElement.getText().trim().split(Constant.LINE_BREAK);
			seed.setGender(gender[0]);
			
			WebElement birthday = driver.findElement(By.id("birthday"));
			String[] birthDate = birthday.getText().trim().split(Constant.LINE_BREAK);
			DateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy");
			Date date = dateFormat.parse(birthDate[0]);
			DateFormat df = new SimpleDateFormat("YYYY-MM-dd");
			seed.setBirthDate(df.format(date));
			
			JDBC.updateSeedPersonalInfo(seed);
			logger.info("Personal information saved in the database");
			
			driver.get(Constant.Yahoo.YAHOO_MAIL_RO_URL);
			
			if (driver.findElements(By.id("login-passwd")).size() > 0) {
				WebElement passwordInput = driver.findElement(By.id("login-passwd"));
				human.type(passwordInput, seed.getPassword());
			}
			if (driver.findElements(By.id("login-signin")).size() > 0) {
				driver.findElement(By.id("login-signin")).click();
				Thread.sleep(5000);
			}
			
		} catch (NoSuchElementException | ElementNotVisibleException | ElementNotFoundException | StaleElementReferenceException | UnhandledAlertException | InterruptedException e) {
			logger.error("Error with seed: " + seed.getUser() + " with password: " + seed.getPassword() + " message: " + e.getMessage());
		} catch (WebDriverException e) {
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 
	 */
	private boolean createNewAccount(WebDriver driver) {
		boolean created = false;
		try {
			String page = Constant.Facebook.FACEBOOK_URL;
			logger.info("Getting to the url: " + page);
			driver.get(page);
			
			Thread.sleep(10000);
			
			WebElement firstName = driver.findElement(By.name("firstname"));
			WebElement lastName = driver.findElement(By.name("lastname"));
			WebElement email = driver.findElement(By.name("reg_email__"));
			WebElement emailConfirmation = driver.findElement(By.name("reg_email_confirmation__"));
			WebElement password = driver.findElement(By.name("reg_passwd__"));
			Select day = new Select(driver.findElement(By.name("birthday_day")));
			Select month = new Select(driver.findElement(By.name("birthday_month")));
			Select year = new Select(driver.findElement(By.name("birthday_year")));
			List<WebElement> sex = driver.findElements(By.name("sex"));
			WebElement gender = seed.getGender().equals(Constant.Facebook.MALE)?sex.get(1):sex.get(0);
			WebElement button = driver.findElement(By.name("websubmit"));
			
			human.type(firstName, seed.getFirstName());
			human.type(lastName, seed.getLastName());
			human.type(email, seed.getUser());
			human.type(emailConfirmation, seed.getUser());
			human.type(password, seed.getPassword());
			
			String dayString = seed.getDayOfBirth();
			String monthString = seed.getMonthOfBirth();
			String yearString = seed.getYearOfBirth();
			
			day.selectByVisibleText(dayString.startsWith("0") ? dayString.substring(1) : dayString);
			month.selectByValue(monthString.startsWith("0") ? monthString.substring(1) : monthString);
			year.selectByVisibleText(yearString);
			
			gender.click();
			
			Thread.sleep(2500);
			
			logger.info("Clicking sin up in Facebook");
			button.click();
			
			Thread.sleep(7500);
			
			if(driver.findElements(By.id("u_8_4")).size() > 0){
				String securityText = driver.findElement(By.id("u_8_4")).getText();
				if(Constant.Facebook.SECURITY_TEXT.equals(securityText)){
					logger.error("Error with Seed: " + seed.getUser() + " when trying to create a Facebook account, message: " + securityText);
				}
			}else{
			
				created = true;
				
				seed.setFbRegistered(true);
				
				JDBC.updateSeedPersonalInfo(seed);
				logger.info("Facebook registration saved in the database");
				
			}
			
		} catch (NoSuchElementException | ElementNotVisibleException | ElementNotFoundException | StaleElementReferenceException | UnhandledAlertException | InterruptedException e) {
			logger.error("Error with seed: " + seed.getUser() + " with password: " + seed.getPassword() + " message: " + e.getMessage());
		} catch (WebDriverException e) {
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return created;
	}
	
	/**
	 * 
	 * @param driver
	 */
	private void confirmFacebookAccount(WebDriver driver) {
		try{
			logger.info("Confirming facebook registration");
			boolean clicked = false;
			
			if(driver.findElements(By.tagName("button")).size() > 0) {
				List<WebElement> buttons = driver.findElements(By.tagName("button"));
				for (WebElement button : buttons) {
					if(Constant.Facebook.FIND_FRIENDS.equals(button.getText())){
						button.click();
						break;
					}
				}
				
				Thread.sleep(7500);
				
				switchToNewWindow(driver, 1);
				
				if(driver.findElements(By.id("agree")).size() > 0){
					driver.findElement(By.id("agree")).click();
					clicked = true;
				}
				
				Thread.sleep(7500);
				
				switchToNewWindow(driver, -1);
				
				if(driver.findElements(By.name("skip_step")).size() > 0){
					driver.findElement(By.name("skip_step")).click();
				}
				
				if(clicked){
					seed.setFbConfirmed(true);
					
					JDBC.updateSeedPersonalInfo(seed);
					logger.info("Facebook confirmation saved in the database");
				}
			}
			
		} catch (NoSuchElementException | ElementNotVisibleException | ElementNotFoundException | StaleElementReferenceException | UnhandledAlertException | InterruptedException e) {
			logger.error("Error with seed: " + seed.getUser() + " with password: " + seed.getPassword() + " message: " + e.getMessage());
		} catch (WebDriverException e) {
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	/**
	 * @param driver
	 */
	private void searchAndConfirmDanielPink(WebDriver driver) {
		try{
			if(seed.getSubscription().contains(Constant.Subscriber.DanielPink)){
				logger.info("Looking for Daniel Pink confirmation email");
				WebElement myMessage = findMyMsgBySearchBox(driver, Constant.Subscriber.DanielPink_Search);
				if(myMessage!=null){
					myMessage.click();
					Thread.sleep(5000);
					clickLink(driver, Constant.Confirmation.DanielPink);
					switchToNewWindow(driver, 1);
					driver.close();
					switchToNewWindow(driver, -1);
				}
				
				if(driver.findElements(By.className("inbox-label")).size() > 0){
					logger.info("Going back to inbox");
					driver.findElement(By.className("inbox-label")).click();
				}
			}
		} catch (NoSuchElementException | ElementNotVisibleException | ElementNotFoundException | StaleElementReferenceException | UnhandledAlertException | InterruptedException e) {
			logger.error("Error with seed: " + seed.getUser() + " with password: " + seed.getPassword() + " message: " + e.getMessage());
		} catch (WebDriverException e) {
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * @param driver
	 */
	private void searchAndConfirmAirBerlin(WebDriver driver) {
		try{
			if(seed.getSubscription().contains(Constant.Subscriber.AirBerlin)){
				logger.info("Looking for airberlin confirmation email");
				WebElement myMessage = findMyMsgBySearchBox(driver, Constant.Subscriber.AirBerlin_Search);
				if(myMessage!=null){
					myMessage.click();
					Thread.sleep(5000);
					clickLink(driver, Constant.Confirmation.AirBerlin);
					switchToNewWindow(driver, 1);
					List<WebElement> interests = driver.findElements(By.id("interests"));
					for (WebElement interest : interests) {
						interest.click();
						Thread.sleep(500);
					}
					List<WebElement> flaggeIds = driver.findElements(By.id("flaggeIDs"));
					for (WebElement flagId : flaggeIds) {
						if(Constant.Subscriber.AirBerlin_FlagValue.equals(flagId.getAttribute("value"))){
							flagId.click();
							Thread.sleep(500);
							break;
						}
					}
					if(driver.findElements(By.id("submitSubscribe")).size() > 0){
						WebElement saveChanges = driver.findElement(By.id("submitSubscribe"));
						saveChanges.click();
						Thread.sleep(10000);
					}
					driver.close();
					switchToNewWindow(driver, -1);
				}
				
				if(driver.findElements(By.className("inbox-label")).size() > 0){
					logger.info("Going back to inbox");
					driver.findElement(By.className("inbox-label")).click();
				}
			}
	} catch (NoSuchElementException | ElementNotVisibleException | ElementNotFoundException | StaleElementReferenceException | UnhandledAlertException | InterruptedException e) {
		logger.error("Error with seed: " + seed.getUser() + " with password: " + seed.getPassword() + " message: " + e.getMessage());
	} catch (WebDriverException e) {
		logger.error(e.getMessage(), e);
	} catch (Exception e) {
		logger.error(e.getMessage(), e);
	}
	}
	
	/**
	 * 
	 * @param driver
	 * @param from
	 * @return
	 */
	private WebElement findMyMsgBySearchBox(WebDriver driver, String from) {
		WebElement message = null;
		try{
			Thread.sleep(3000);
			if(driver.findElements(By.className("typeahead-input-usertext")).size() > 0){
				WebElement searchBox = driver.findElement(By.className("typeahead-input-usertext"));
				String searchString = "From: " + from + " is: unread";
				human.type(searchBox, searchString);
				searchBox.sendKeys(Keys.ENTER);
				Thread.sleep(10000);
				List<WebElement> myMessages = new ArrayList<WebElement>();
				if (driver.findElements(By.className("message-list-item")).size() > 0) {
					myMessages = driver.findElements(By.className("message-list-item"));
				}
				if (myMessages.isEmpty()) {
					return null;
				}
				logger.info("Getting first " + from + " message");
				message = myMessages.get(0);
			}
		} catch (NoSuchElementException | ElementNotVisibleException | ElementNotFoundException | StaleElementReferenceException | UnhandledAlertException | InterruptedException e) {
			logger.error("Error with seed: " + seed.getUser() + " with password: " + seed.getPassword() + " message: " + e.getMessage());
		} catch (WebDriverException e) {
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return message;
	}
	
	/**
	 * 
	 * @param driver
	 * @param containing
	 * @return
	 */
	private boolean clickLink(WebDriver driver, String containing) {
		boolean clicked = false;
		try {
			WebElement div = driver.findElement(By.className("email-wrapped"));
			logger.info("Looking for links inside the message");
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
							if (url.contains(containing)) {
								logger.info("Containing " + containing + " link, click it!! " + url);
								link.click();
								Thread.sleep(5000);
								clicked = true;
								keepGoing = false;
							} else {
								logger.info("Not the link we want!! - we are not clicking it" + url);
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
				logger.info("**********   No Facebook link found or none available  **********");
			}
		} catch (NoSuchElementException | ElementNotVisibleException | ElementNotFoundException | StaleElementReferenceException | UnhandledAlertException | InterruptedException e) {
			logger.error("Error with seed: " + seed.getUser() + " with password: " + seed.getPassword() + " message: " + e.getMessage());
		} catch (WebDriverException e) {
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return clicked;
	}
	
	/**
	 * 
	 * @param driver
	 */
	private void clickAllNotSapm(WebDriver driver) {
		try {
			logger.info("Checking spam folder");
			if (driver.findElements(By.id("spam-label")).size() > 0) {
				WebElement spam = driver.findElement(By.id("spam-label"));
				logger.info("Clicking spam folder");
				spam.click();
				Thread.sleep(5000);
				if (driver.findElements(By.className("list-view-item")).size() > 0) {
					logger.info("There are emails in the spam folder");
					if(driver.findElements(By.xpath("//span[@id='btn-ml-cbox']/label/input")).size() > 0){
						WebElement checkbox = driver.findElement(By.xpath("//span[@id='btn-ml-cbox']/label/input"));
						if (!checkbox.isSelected()) {
							logger.info("Checking not spam checkbox");
							checkbox.click();
							Thread.sleep(2500);
						}
						if(driver.findElements(By.id("btn-not-spam")).size() > 0){
							WebElement notSpam = driver.findElement(By.id("btn-not-spam"));
							logger.info("Clicking NOT SPAM button");
							notSpam.click();
							Thread.sleep(5000);
						}
					}
				}else{
					logger.info("There are NO emails in the spam folder");
				}
			}
		} catch (NoSuchElementException | ElementNotVisibleException | ElementNotFoundException | StaleElementReferenceException | UnhandledAlertException | InterruptedException e) {
			logger.error("Error with seed: " + seed.getUser() + " with password: " + seed.getPassword() + " message: " + e.getMessage());
		} catch (WebDriverException e) {
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	/**
	 * Switches to the non-current window
	 * 
	 * @throws InterruptedException
	 */
	private void switchToNewWindow(WebDriver driver, int i) {
		try {
			Set<String> handles = driver.getWindowHandles();
			if(i < 0){
				i = 1;
			}else{
				String current = driver.getWindowHandle();
				handles.remove(current);
			}
			String newTab = null;
			for (int j = 0; j < i; j++) {
				newTab = handles.iterator().next();
			}
			logger.info("Switching to new window");
			driver.switchTo().window(newTab);
			Thread.sleep(5000);
		} catch (NoSuchElementException | ElementNotVisibleException | ElementNotFoundException | StaleElementReferenceException | UnhandledAlertException | InterruptedException e) {
			logger.error("Error with seed: " + seed.getUser() + " with password: " + seed.getPassword() + " message: " + e.getMessage());
		} catch (WebDriverException e) {
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	/**
	 * @return
	 */
	private WebDriver createWebDriver() {
		logger.info("Creating the web driver");

		FirefoxProfile profile = new FirefoxProfile();
		File modifyHeaders = new File("/var/www/modify_headers.xpi");
		File canvasBlocker = new File("/var/www/canvasblocker-0.2.1-Release-fx+an.xpi");
		profile.setEnableNativeEvents(false);
		try {
			profile.addExtension(modifyHeaders);
			profile.addExtension(canvasBlocker);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		profile.setPreference("modifyheaders.headers.count", 1);
		profile.setPreference("modifyheaders.headers.action0", "Add");
		profile.setPreference("modifyheaders.headers.name0", "sox");
		profile.setPreference("modifyheaders.headers.value0", "305471");
		profile.setPreference("modifyheaders.headers.enabled0", true);
		profile.setPreference("modifyheaders.config.active", true);
		profile.setPreference("modifyheaders.config.alwaysOn", true);
		profile.setPreference("general.useragent.override", getRandomUA());

		DesiredCapabilities capabilities = new DesiredCapabilities();
		// capabilities.setBrowserName("Graneeeeeeekk");
		capabilities.setPlatform(org.openqa.selenium.Platform.ANY);
		capabilities.setCapability(FirefoxDriver.PROFILE, profile);

		// caps.setCapability("applicationCacheEnabled", false);
		// String PROXY = "192.168.1.111:8888";
		// org.openqa.selenium.Proxy proxy = new org.openqa.selenium.Proxy();
		// proxy.setHttpProxy(PROXY)
		// .setFtpProxy(PROXY)
		// .setSslProxy(PROXY);
		// caps.setCapability(CapabilityType.PROXY, proxy);
		WebDriver driver = new FirefoxDriver(capabilities);

		driver.manage().window().maximize();
		logger.info("Firefox Created");
		return driver;
	}

	private String getRandomUA() {
		String[] uas = new String[9];
		uas[0] = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.10; rv:40.0) Gecko/20100101 Firefox/40.0";
		uas[1] = "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2224.3 Safari/537.36";
		uas[2] = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:40.0) Gecko/20100101 Firefox/40.1";
		uas[3] = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.12 Safari/535.11";
		uas[4] = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36";
		uas[5] = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2227.1 Safari/537.36";
		uas[6] = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2227.0 Safari/537.36";
		uas[7] = "Mozilla/5.0 (Windows NT 10.0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/40.0.2214.93 Safari/537.36";
		uas[8] = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/36.0.1944.0 Safari/537.36";
		return uas[randInt(0, 8)];

	}
	
	/**
	 * 
	 * @param min
	 * @param max
	 * @return
	 */
	public static int randInt(int min, int max) {
		Random rand = new Random();
		// nextInt is normally exclusive of the top value, so add 1 to make it inclusive
		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}
	
	/**
	 * Throw dices to get random results
	 * 
	 * @return boolean
	 */
	public static boolean throwDice() {
		int dice = randInt(1, 6);
		return dice == 3;
	}
	
	/**
	 * 
	 * @param driver
	 * @param name
	 */
	private void getScreenShot(WebDriver driver, String name) {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		// Now you can do whatever you need to do with it, for example copy
		// somewhere
		try {
			FileUtils.copyFile(scrFile, new File("/var/www/errors/" + name + ".jpg"));
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}

	}
	
	/**
	 * 
	 * @param seed
	 * @param valid
	 */
	private void writeSeedToFile(Seed seed, boolean valid) {
		String s = null;
		try {
			if(valid){
				s = "\n" + seed.getFullSeed();
				Files.write(Paths.get(Constant.ROUTE + "wolf_valid_seeds.txt"), s.getBytes(), StandardOpenOption.APPEND);
			}else{
				s = "\n" + seed.getFullSeed() + " error: " + invalidMessage;
				Files.write(Paths.get(Constant.ROUTE + "wolf_invalid_seeds.txt"), s.getBytes(), StandardOpenOption.APPEND);
			}
		}catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 
	 * @return
	 */
	private Human generateRandomHumanUser() {
		logger.info("Random Human generation started");
		int number = randInt(0, 10);
		if (number <= 3) {
			return new DumbHuman();
		} else if (number >= 4 && number <= 8) {
			return new AverageHuman();
		}
		return new FastHuman();
	}
	
//	
//	/**
//	 * 
//	 * @param facebookUrl
//	 * @param email
//	 * @param password
//	 * @param driver
//	 */
//	private void facebookLogin(String facebookUrl, String email, String password, WebDriver driver) {
//		logger.info("Trying to login in....");
//		try {
//
//			logger.info("Getting to the url: " + facebookUrl);
//			driver.get(facebookUrl);
//
//			logger.info("Introducing username: " + email);
//			WebElement accountInput = driver.findElement(By.id("email"));
//			human.type(accountInput, email);
//
//			logger.info("Introducing password: " + password);
//			WebElement passwordInput = driver.findElement(By.id("pass"));
//			human.type(passwordInput, password);
//
//			logger.info("Clicking login button");
//			if (driver.findElements(By.id("loginbutton")).size() > 0) {
//				driver.findElement(By.id("loginbutton")).click();
//				Thread.sleep(5000);
//			} else {
//				logger.info("Already logged in..Moving forward!");
//			}
//		} catch (NoSuchElementException | ElementNotVisibleException | ElementNotFoundException | StaleElementReferenceException | UnhandledAlertException | InterruptedException e) {
//			logger.error("Error with seed: " + seed.getUser() + " with password: " + seed.getPassword() + " message: " + e.getMessage());
//		} catch (WebDriverException e) {
//			logger.error(e.getMessage(), e);
//		} catch (Exception e) {
//			logger.error(e.getMessage(), e);
//		}
//	}
	
}
