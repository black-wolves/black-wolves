/**
 * 
 */
package com.blackwolves.selenium.seeder;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * @author danigrane
 *
 */
public class AverageHuman extends Human {
	
	public AverageHuman() {
		// TODO Auto-generated constructor stub
		logger.info("AvergaHuman Created");
	}

	@Override
	public void type(WebElement input, String seed, WebDriver driver) throws InterruptedException {
		char[] charArray = seed.toCharArray();
		for (int i = 0; i < charArray.length; i++) {
		
			Character myCharacter =  new Character(charArray[i]);
			input.sendKeys(myCharacter.toString());
			Thread.sleep(YahooRunnable.randInt(50, 300));
		}
	}

}
