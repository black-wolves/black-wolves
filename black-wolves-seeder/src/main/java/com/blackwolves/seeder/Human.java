/**
 * 
 */
package com.blackwolves.seeder;

import java.util.List;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * @author danigrane
 *
 */
public abstract class Human {
	
	protected static final Logger logger = LogManager.getLogger(Human.class.getName());

	public abstract void type(WebElement input, String string) throws InterruptedException;
	
	public String generateRandomBody(WebDriver driver, WebDriverWait wait) throws InterruptedException{
		WebElement a = driver.findElement(By.id("yucs-home_link"));
		logger.info("Cicking this link: " + a.getAttribute("href"));
		Actions newTab = new Actions(driver);
		newTab.keyDown(Keys.SHIFT).click(a).keyUp(Keys.SHIFT).build().perform();
		Thread.sleep(YahooRunnable.randInt(7000,10000));
		
		// handle windows change
		String base = driver.getWindowHandle();
		Set<String> set = driver.getWindowHandles();

		driver.switchTo().window((String) set.toArray()[1]);

		WebElement main = driver.findElement(By.id("Main"));
		List<WebElement> elements = main.findElements(By.tagName("a"));
		int randomLinkNo = YahooRunnable.randInt(0, elements.size() - 1);
		a = elements.get(randomLinkNo);
		logger.info("Clicking link");
		a.click();
		logger.info("Waiting");
		Thread.sleep(YahooRunnable.randInt(7000,10000));
		logger.info("Getting elements p");
		elements = driver.findElements(By.tagName("p"));
		logger.info("Random p");
		randomLinkNo = YahooRunnable.randInt(0, elements.size() - 1);
		logger.info("Getting body");
		String body = elements.get(randomLinkNo).getText();

		set.remove(base);
		assert set.size() == 1;
		driver.switchTo().window((String) set.toArray()[0]);

		// close the window
		driver.close();
		driver.switchTo().window(base);

		// handle windows change and switch back to the main window
		Thread.sleep(YahooRunnable.randInt(1500, 2500));
		for (String winHandle : driver.getWindowHandles()) {
			driver.switchTo().window(winHandle);
		}
		logger.info("body: " + body);
		return body;
	}

}
