/**
 * 
 */
package com.blackwolves.selenium.seeder;

import org.openqa.selenium.WebElement;

/**
 * @author danigrane
 *
 */
public class AverageHuman extends Human {
	
	public AverageHuman() {
		logger.info("AvergaHuman Created");
	}

	@Override
	public void type(WebElement input, String string) throws InterruptedException {
		char[] charArray = string.toCharArray();
		for (int i = 0; i < charArray.length; i++) {
		
			Character myCharacter =  new Character(charArray[i]);
			input.sendKeys(myCharacter.toString());
			Thread.sleep(YahooRunnable.randInt(50, 300));
		}
	}

}
