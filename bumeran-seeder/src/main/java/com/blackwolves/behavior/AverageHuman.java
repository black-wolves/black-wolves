/**
 * 
 */
package com.blackwolves.behavior;

import org.openqa.selenium.WebElement;

import com.blackwolves.seeder.BumeranSeeder;

/**
 * 
 * @author gastondapice
 *
 */
public class AverageHuman extends Human {
	
	public AverageHuman() {
		logger.info("AvergaHuman Created");
	}

	@Override
	public void type(WebElement input, String string) {
		char[] charArray = string.toCharArray();
		for (int i = 0; i < charArray.length; i++) {
		
			Character myCharacter =  new Character(charArray[i]);
			input.sendKeys(myCharacter.toString());
			try {
				Thread.sleep(BumeranSeeder.randInt(50, 300));
			} catch (InterruptedException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

}
