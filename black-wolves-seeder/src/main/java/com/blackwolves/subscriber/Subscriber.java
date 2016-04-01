/**
 * 
 */
package com.blackwolves.subscriber;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blackwolves.seeder.Seed;
import com.blackwolves.seeder.Seeder;
import com.blackwolves.seeder.YahooRunnable;
import com.blackwolves.seeder.util.JDBC;

/**
 * @author gaston.dapice
 *
 */
public class Subscriber implements Runnable {

	private Seed seed;

	final String liveStyle = "https://regilite.nytimes.com/regilite?product=LI&theme=Transparent&landing=true&app=newsletter&interface=sign_up_page&sourceApp=nyt-v5&title=NYT+Living";
	final String bits = "https://regilite.nytimes.com/regilite?product=TU&theme=Transparent&landing=true&app=newsletter&interface=sign_up_page&sourceApp=nyt-v5&title=Bits";
	final String cooking = "https://regilite.nytimes.com/regilite?product=CK&theme=Transparent&landing=true&app=newsletter&interface=sign_up_page&sourceApp=nyt-v5&title=Cooking";
	final String news = "https://regilite.nytimes.com/regilite?product=NN&theme=Transparent&landing=true&app=newsletter&interface=sign_up_page&sourceApp=nyt-v5&title=The+NYT+Now+Morning+Briefing";
	final String headlines = "https://regilite.nytimes.com/regilite?product=TH&theme=Transparent&landing=true&app=newsletter&interface=sign_up_page&sourceApp=nyt-v5&title=Today%E2%80%99s+Headlines";
	final String afterNoon = "https://regilite.nytimes.com/regilite?product=AU&theme=Transparent&landing=true&app=newsletter&interface=sign_up_page&sourceApp=nyt-v5&title=Afternoon+Update";
	final String today = "https://regilite.nytimes.com/regilite?product=UR&theme=Transparent&landing=true&app=newsletter&interface=sign_up_page&sourceApp=nyt-v5&title=New+York+Today";
	final String firstDraft = "https://regilite.nytimes.com/regilite?product=CN&theme=Transparent&landing=true&app=newsletter&interface=sign_up_page&sourceApp=nyt-v5&title=First+Draft";
	final String dealBook = "https://regilite.nytimes.com/regilite?product=DK&theme=Transparent&landing=true&app=newsletter&interface=sign_up_page&sourceApp=nyt-v5&title=DealBook";
	final String opinion = "https://regilite.nytimes.com/regilite?product=TY&theme=Transparent&landing=true&app=newsletter&interface=sign_up_page&sourceApp=nyt-v5&title=Opinion+Today";
	final String europe = "https://regilite.nytimes.com/regilite?product=EE&theme=Transparent&landing=true&app=newsletter&interface=sign_up_page&sourceApp=nyt-v5&title=Today%E2%80%99s+Headlines+European+Morning";
	final String asia = "https://regilite.nytimes.com/regilite?product=AE&theme=Transparent&landing=true&app=newsletter&interface=sign_up_page&sourceApp=nyt-v5&title=Today%E2%80%99s+Headlines+Asian+Morning";

	public Subscriber(Seed seed) {
		this.seed = seed;
	}

	private static final Logger logger = LoggerFactory.getLogger(Subscriber.class);

	@Override
	public void run() {
		logger.info("Creating the driver");
		DesiredCapabilities caps = new DesiredCapabilities();
		caps.setCapability("binary", "/usr/bin/wires-0.3.0-linux64");
		caps.setCapability("resolution", "1280x800");
		WebDriver driver = new FirefoxDriver(caps);
		seed.setSubscription(new String());
		try {
			subscribeToTheGolfChannel(seed, driver);
			subscribeToForbes(seed, driver);


//			if (Math.random() < 0.35) {
//				subscribeToNYDailyNews(seed, driver);
//			}
//			if (Math.random() < 0.35) {
//				subscribeToSoccerWire(seed, driver);
//			}
//			if (Math.random() < 0.35) {
//				subscribeToTheGolfChannel(seed, driver);
//			}
//			if (Math.random() < 0.35) {
//				subscribeToDetroitDailyNews(seed, driver);
//			}
//			if (Math.random() < 0.35) {
//				subscribeToSanAntonioNews(seed, driver);
//			}
//			if (Math.random() < 0.35) {
//				subscribeToBostonMagazine(seed, driver);
//			}
//			if (Math.random() < 0.35) {
//				subscribeToTheHerald(seed, driver);
//			}
//			if (Math.random() < 0.35) {
//				subscribeToNBCNews(seed, driver);
//			}
//			if (Math.random() < 0.35) {
//				subscribeToDailyNews(seed, driver);
//			}
//			if (Math.random() < 0.35) {
//				subscribeToForbes(seed, driver);
//			}
			// if (Math.random() < 0.3) {
			// subscribeToMatterMark(seed, driver);
			// }
			// if (Math.random() < 0.3) {
			// subscribeFashionMagazine(seed, driver);
			// }
			// if (Math.random() < 0.3) {
			// subscribeToFetch(seed, driver);
			// }
			// if (Math.random() < 0.3) {
			// subscribeToReDef(seed, driver);
			// }
			// if (Math.random() < 0.3) {
			// subscribeToNyTimesRandom(seed, liveStyle, driver);
			// }
			// if (Math.random() < 0.3) {
			// subscribeToNyTimesRandom(seed, bits, driver);
			// }
			// if (Math.random() < 0.3) {
			// subscribeToNyTimesRandom(seed, cooking, driver);
			// }
			// if (Math.random() < 0.3) {
			// subscribeToNyTimesRandom(seed, news, driver);
			// }
			// if (Math.random() < 0.3) {
			// subscribeToNyTimesRandom(seed, headlines, driver);
			// }
			// if (Math.random() < 0.3) {
			// subscribeToNyTimesRandom(seed, afterNoon, driver);
			// }
			// if (Math.random() < 0.3) {
			// subscribeToNyTimesRandom(seed, today, driver);
			// }
			// if (Math.random() < 0.3) {
			// subscribeToNyTimesRandom(seed, firstDraft, driver);
			// }
			// if (Math.random() < 0.3) {
			// subscribeToNyTimesRandom(seed, dealBook, driver);
			// }
			// if (Math.random() < 0.3) {
			// subscribeToSkimm(seed, driver);
			// }
			// if (Math.random() < 0.3) {
			// subscribeToNyTimesRandom(seed, opinion, driver);
			// }
			// if (Math.random() < 0.3) {
			// subscribeToNyTimesRandom(seed, europe, driver);
			// }
			// if (Math.random() < 0.3) {
			// subscribeToNyTimesRandom(seed, asia, driver);
			// }
			// if (Math.random() < 0.3) {
			// subscribeToGolfSmith(seed, driver);
			// }
			JDBC.updateSubscription(seed);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			logger.info("Closing driver");
			driver.close();
			driver.quit();
		}
	}

	private void subscribeToSoccerWire(Seed seed, WebDriver driver) {
		String url = "http://www.soccerwire.com/category/news/leagues/mls/";
		String site = "SoccerWire,";
		try {
			logger.info("Subscribing " + seed.getUser() + " to " + site);
			driver.get(url);
			WebElement subscribeBtn = driver.findElement(By.partialLinkText("Subscribe Here"));
			subscribeBtn.click();
			WebElement email = driver.findElement(By.id("inputProp0"));
			email.clear();
			email.sendKeys(seed.getUser());
			WebElement checkbox1 = driver.findElement(By.name("subscriptions.interestCategories[0].selected"));
			checkbox1.click();
			if (Math.random() < 0.5) {
				WebElement checkbox2 = driver.findElement(By.name("subscriptions.interestCategories[1].selected"));
				checkbox2.click();
			}
			WebElement submit = driver.findElement(By.className("btn-primary"));
			Thread.sleep(1000);
			submit.click();
			seed.setSubscription(seed.getSubscription().concat(site));

		} catch (InterruptedException | NoSuchElementException e) {
			logger.info("Error with Seed: " + seed.getUser() + " in " + url);
		}catch (WebDriverException e) {
			logger.info("Element in " + url + "is not clickable. Please review" );
		}
	}

	private void subscribeToTheGolfChannel(Seed seed, WebDriver driver) {
		String url = "http://email.thegolfchannel.com/golfchan3/golfchan_reg.action";
		String site = "TheGolfChannel,";
		try {
			logger.info("Subscribing " + seed.getUser() + " to " + site);
			driver.get(url);
			WebElement email = driver.findElement(By.id("news-form_email"));
			email.clear();
			email.sendKeys(seed.getUser());
			List<WebElement> checkboxes = driver.findElements(By.className("customCheckbox"));
			for (int i = 0; i < checkboxes.size(); i++) {
				checkboxes.get(i).click();
			}
			WebElement submit = driver.findElement(By.className("submit-btn"));
			Thread.sleep(1000);
			submit.click();
			seed.setSubscription(seed.getSubscription().concat(site));
			Thread.sleep(1000);
		} catch (InterruptedException | NoSuchElementException e) {
			logger.info("Error with Seed: " + seed.getUser() + " in " + url);
			Seeder.getScreenShot(driver, seed.getUser()+"such");
			logger.info("Saving screenshot as /var/www/errors/"+seed.getUser()+"such" );

		}catch (WebDriverException e) {
			logger.info("Element in " + url + "is not clickable. Saving screenshot as /var/www/errors/"+seed.getUser() );
			Seeder.getScreenShot(driver, seed.getUser());


		}

	}

	private void subscribeToDetroitDailyNews(Seed seed, WebDriver driver) {
		String url = "https://account.detroitnews.com/newsletters/";
		String site = "DetroitDailyNews,";
		try {
			logger.info("Subscribing " + seed.getUser() + " to " + site);
			driver.get(url);
			WebElement email = driver.findElement(By.id("email"));
			email.clear();
			email.sendKeys(seed.getUser());
			WebElement confirmEmail = driver.findElement(By.id("email-confirm"));
			confirmEmail.clear();
			confirmEmail.sendKeys(seed.getUser());

			List<WebElement> checkboxes = driver.findElements(By.xpath("//input[@name='pubLists']"));
			for (int i = 0; i < checkboxes.size(); i++) {
				if (Math.random() < 0.4) {
					checkboxes.get(i).click();
				}
			}
			List<WebElement> checkboxesOffers = driver.findElements(By.xpath("//input[@name='categories']"));
			for (int i = 0; i < checkboxesOffers.size(); i++) {
				if (Math.random() < 0.3) {
					checkboxesOffers.get(i).click();
				}
			}
			WebElement submit = driver.findElement(By.xpath("//button[@name='action']"));
			Thread.sleep(1000);
			submit.click();
			seed.setSubscription(seed.getSubscription().concat(site));
			Thread.sleep(1000);
		} catch (InterruptedException | NoSuchElementException e) {
			logger.info("Error with Seed: " + seed.getUser() + " in " + url);
		}catch (WebDriverException e) {
			logger.info("Element in " + url + "is not clickable. Please review" );
		}

	}

	private void subscribeToNYDailyNews(Seed seed, WebDriver driver) {
		String url = "http://link.nydailynews.com/join/4xm/newslettersignup-desktop";
		String site = "NYDailyNews,";

		try {
			logger.info("Subscribing " + seed.getUser() + " to " + site);
			driver.get(url);
			WebElement email = driver.findElement(By.name("email"));
			email.clear();
			email.sendKeys(seed.getUser());
			WebElement checkbox = driver.findElement(By.id("nydn-offer"));
			checkbox.click();
			WebElement submit = driver.findElement(By.className("nydn-submit"));
			Thread.sleep(1000);
			submit.click();
			seed.setSubscription(seed.getSubscription().concat(site));

			Thread.sleep(1000);
		} catch (InterruptedException | NoSuchElementException e) {
			logger.info("Error with Seed: " + seed.getUser() + " in " + url);
		}catch (WebDriverException e) {
			logger.info("Element in " + url + "is not clickable. Please review" );
		}
	}

	private void subscribeToDailyNews(Seed seed, WebDriver driver) {
		String url = "http://www.dailynews.com/email_signup";
		String site = "DailyNews,";
		try {
			logger.info("Subscribing " + seed.getUser() + " to " + site);
			driver.get(url);
			WebElement email = driver.findElement(By.name("email"));
			email.clear();
			email.sendKeys(seed.getUser());

			WebElement zipcode = driver.findElement(By.name("zipcode"));
			zipcode.clear();
			CharSequence code = new String(Integer.toString(YahooRunnable.randInt(60000, 799999)));
			zipcode.sendKeys(code);

			List<WebElement> radioButtons = driver.findElements(By.name("print_subscriber"));
			radioButtons.get(1).click();
			List<WebElement> checkboxes = driver.findElements(By.xpath("//label[@class='nw_label']/input"));
			for (int i = 0; i < checkboxes.size(); i++) {
				if (Math.random() > 0.4) {
					checkboxes.get(i).click();
				}
			}
			Thread.sleep(2000);
			driver.findElement(By.xpath("//div[@class='nw_submit_contain']/input")).click();
			seed.setSubscription(seed.getSubscription().concat(site));

		} catch (InterruptedException | NoSuchElementException e) {
			logger.info("Error with Seed: " + seed.getUser() + " in " + url);
		}catch (WebDriverException e) {
			logger.info("Element in " + url + "is not clickable. Please review" );
		}

	}

	private void subscribeToSanAntonioNews(Seed seed, WebDriver driver) {
		String url = "http://www.mysanantonio.com/news/local/";
		String site = "SanAntonioNews,";
		try {
			logger.info("Subscribing " + seed.getUser() + " to " + site);
			driver.get(url);
			WebElement email = driver.findElement(By.name("email"));
			email.clear();
			email.sendKeys(seed.getUser());
			WebElement spursNation = driver.findElement(By.id("slid2"));
			spursNation.click();
			WebElement breakingNews = driver.findElement(By.id("slid3"));
			breakingNews.click();
			WebElement submit = driver.findElement(By.name("submit"));
			Thread.sleep(1000);
			submit.click();
			seed.setSubscription(seed.getSubscription().concat(site));
			Thread.sleep(2000);
		} catch (InterruptedException | NoSuchElementException e) {
			logger.info("Error with Seed: " + seed.getUser() + " in " + url);
		}catch (WebDriverException e) {
			logger.info("Element in " + url + "is not clickable. Please review" );
		}
	}

	private void subscribeToBostonMagazine(Seed seed, WebDriver driver) {
		String url = "http://www.bostonmagazine.com/newsletters/";
		String site = "BostonMagazine,";
		try {
			logger.info("Subscribing " + seed.getUser() + " to " + site);
			driver.get(url);
			WebElement email = driver.findElement(By.name("email"));
			email.clear();
			email.sendKeys(seed.getUser());

			List<WebElement> checkboxes = driver.findElements(By.xpath("//input[@name='newsletter_lists']"));
			for (int i = 0; i < checkboxes.size(); i++) {
				if (Math.random() > 0.4) {
					checkboxes.get(i).click();
				}
			}
			WebElement submit = driver.findElement(By.className("hubspot-button"));
			Thread.sleep(1000);
			submit.click();
			seed.setSubscription(seed.getSubscription().concat(site));
			Thread.sleep(2000);
		} catch (InterruptedException | NoSuchElementException e) {

			logger.info("Error with Seed: " + seed.getUser() + " in " + url);
		}catch (WebDriverException e) {
			logger.info("Element in " + url + "is not clickable. Please review" );
		}
	}

	private void subscribeToTheHerald(Seed seed, WebDriver driver) {
		String url = "https://affiliates.eblastengine.com/Widgets/EmailSignup.aspx?wcguid=3E8B8709-AF46-4F2C-AFBA-2D662DFCC337";
		String site = "TheHerald,";
		try {
			logger.info("Subscribing " + seed.getUser() + " to " + site);
			driver.get(url);
			WebElement email = driver.findElement(By.id("txtEmailAddress"));
			email.clear();
			email.sendKeys(seed.getUser());
			WebElement submit = driver.findElement(By.id("btnSave"));
			Thread.sleep(1000);
			submit.click();
			seed.setSubscription(seed.getSubscription().concat(site));
			Thread.sleep(1000);
		} catch (InterruptedException | NoSuchElementException e) {
			logger.info("Error with Seed: " + seed.getUser() + " in " + url);
		}
		catch (WebDriverException e) {
			logger.info("Element in " + url + "is not clickable. Please review" );
		}
	}

	private void subscribeToNBCNews(Seed seed, WebDriver driver) {
		String url = "http://www.nbcnews.com/";
		String site = "NBCNews,";
		try {
			logger.info("Subscribing " + seed.getUser() + " to " + site);
			driver.get(url);
			WebElement email = driver.findElement(By.className("j-email"));
			email.clear();
			email.sendKeys(seed.getUser());
			WebElement submit = driver.findElement(By.className("j-submit"));
			Thread.sleep(3000);
			submit.click();
			seed.setSubscription(seed.getSubscription().concat(site));
		} catch (InterruptedException | NoSuchElementException e) {
			logger.info("Error with Seed: " + seed.getUser() + " in " + url);
		}
		catch (WebDriverException e) {
			logger.info("Element in " + url + "is not clickable. Please review" );
		}
	}

	private void subscribeToForbes(Seed seed, WebDriver driver) {
		String url = "http://www.forbes.com/";
		String site = "Forbes,";
		try {
			logger.info("Subscribing " + seed.getUser() + " to " + site);
			driver.get(url);
			// Going 2 times so we can avoid Quote of the day
			Thread.sleep(3000);
			driver.get(url);
			Thread.sleep(1000);

			WebElement email = driver.findElement(By.id("Email"));
			email.clear();
			email.sendKeys(seed.getUser());
			WebElement submit = driver.findElement(By.className("mktoButtonRow"));
			Thread.sleep(1000);
			submit.click();
			seed.setSubscription(seed.getSubscription().concat(site));
		} catch (InterruptedException | NoSuchElementException e) {
			logger.info("Error with Seed: " + seed.getUser() + " in " + url);
		}
		catch (WebDriverException e) {
			logger.info("Element in " + url + "is not clickable. Saving screenshot as /var/www/errors/"+seed.getUser() );
			Seeder.getScreenShot(driver, seed.getUser());
			
		}

	}

	private void subscribeToNyTimesRandom(Seed seed, String url, WebDriver driver) {
		String site = "NYTimes,";
		logger.info("Subscribing " + seed.getUser() + " to " + site);
		driver.get(url);
		try {
			Thread.sleep(5000);
			List<WebElement> fields = driver.findElements(By.xpath("//div[@class='filedElements']/input"));
			if (fields.size() > 0) {
				fields.get(0).clear();
				fields.get(0).sendKeys(seed.getUser());
				driver.findElement(By.xpath("//button[@class='applicationButton']")).click();
				seed.setSubscription(seed.getSubscription().concat(site));
				Thread.sleep(2000);
			}
		} catch (NoSuchElementException | InterruptedException e) {
			logger.info("Error with Seed: " + seed.getUser() + " in " + url);
		}

	}

	// Works! :)
	private void subscribeToReDef(Seed seed, WebDriver driver) {
		String url = "http://link.mediaredefined.com/join/353/media-redefweb";
		String site = "ReDef,";

		try {
			logger.info("Subscribing " + seed.getUser() + " to " + site);
			driver.get(url);
			driver.findElement(By.name("email")).clear();
			driver.findElement(By.name("email")).sendKeys(seed.getUser());
			driver.findElement(By.xpath("//div[@id='button']/input")).click();
			seed.setSubscription(seed.getSubscription().concat(site));
			Thread.sleep(3000);
		} catch (NoSuchElementException | InterruptedException e) {
			logger.info("Error with Seed: " + seed.getUser() + " in " + url);
		}
	}

	// Works! :)
	private void subscribeToFetch(Seed seed, WebDriver driver) {
		String url = "http://thefetch.com/";
		String site = "TheFetch,";

		try {
			logger.info("Subscribing " + seed.getUser() + " to " + site);
			driver.get(url);
			driver.findElement(By.name("email")).clear();
			driver.findElement(By.name("email")).sendKeys(seed.getUser());
			driver.findElement(By.name("email")).submit();
			seed.setSubscription(seed.getSubscription().concat(site));
			Thread.sleep(1000);
		} catch (NoSuchElementException | InterruptedException e) {
			logger.info("Error with Seed: " + seed.getUser() + " in " + url);
		}
	}

	// Works! :)
	private void subscribeToSkimm(Seed seed, WebDriver driver) {
		String url = "http://www.theskimm.com/";
		String site = "TheSkimm,";

		try {
			logger.info("Subscribing " + seed.getUser() + " to " + site);
			driver.get(url);
			driver.findElement(By.name("email")).clear();
			driver.findElement(By.name("email")).sendKeys(seed.getUser());
			driver.findElement(By.name("email")).submit();
			seed.setSubscription(seed.getSubscription().concat(site));
			Thread.sleep(3000);
		} catch (InterruptedException | NoSuchElementException e) {
			logger.info("Error with Seed: " + seed.getUser() + " in " + url);
		}
	}

	// Works :)
	private void subscribeToMatterMark(Seed seed, WebDriver driver) {
		String url = "http://mattermark.com/app/Newsletter";
		String site = "Mattermark,";

		try {
			logger.info("Subscribing " + seed.getUser() + " to " + site);
			driver.get(url);
			driver.findElement(By.id("free_email")).sendKeys(seed.getUser());
			driver.findElement(By.id("free_email")).submit();
			seed.setSubscription(seed.getSubscription().concat(site));
			Thread.sleep(3000);
		} catch (InterruptedException | NoSuchElementException e) {
			logger.info("Error with Seed: " + seed.getUser() + " in " + url);
		}

	}

	// Works :)
	private void subscribeFashionMagazine(Seed seed, WebDriver driver) {
		String url = "http://www.fashionmagazine.com/";
		String site = "FashionMagazine,";

		try {
			logger.info("Subscribing " + seed.getUser() + " to " + site);
			driver.get(url);
			driver.findElement(By.name("input_1")).clear();
			driver.findElement(By.name("input_1")).sendKeys(seed.getUser());
			driver.findElement(By.name("input_1")).submit();
			if (driver.getPageSource().contains("Thanks")) {
				logger.info("Fashion Magazine Subscription succesful.");
			}
			seed.setSubscription(seed.getSubscription().concat(site));
			Thread.sleep(3000);
		} catch (InterruptedException | NoSuchElementException e) {
			logger.info("Error with Seed: " + seed.getUser() + " in " + url);
		}
	}

	// Works! :)
	private void subscribeToGolfSmith(Seed seed, WebDriver driver) {
		String url = "http://www.golfsmith.com/";
		String site = "GolfSmith,";

		try {
			logger.info("Subscribing " + seed.getUser() + " to " + site);
			driver.get(url);
			driver.findElement(By.name("email")).clear();
			driver.findElement(By.name("email")).sendKeys(seed.getUser());
			driver.findElement(By.id("submitAddress_footer")).submit();
			seed.setSubscription(seed.getSubscription().concat(site));
			Thread.sleep(3000);
		} catch (InterruptedException | NoSuchElementException e) {
			logger.info("Error with Seed: " + seed.getUser() + " in " + url);
		}
	}

	/**
	 * 
	 * @param fileName
	 * @param fileContent
	 */
	public static void writeToFile(String fileName, String fileContent) {
		try {
			logger.info("Writing page to: " + fileName);
			FileWriter writer = new FileWriter(fileName, false);
			writer.write(fileContent);

			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
