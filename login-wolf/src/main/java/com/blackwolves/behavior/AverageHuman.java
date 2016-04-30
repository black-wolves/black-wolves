/**
 * 
 */
package com.blackwolves.behavior;

import org.openqa.selenium.WebElement;

import com.blackwolves.mail.Login;

/**
 * @author danigrane
 *
 */
public class AverageHuman extends Human {
	
	public AverageHuman() {
	}

	@Override
	public void type(WebElement input, String string) {
		char[] charArray = string.toCharArray();
		for (int i = 0; i < charArray.length; i++) {
		
			Character myCharacter =  new Character(charArray[i]);
			input.sendKeys(myCharacter.toString());
			try {
				Thread.sleep(Login.randInt(50, 300));
			} catch (InterruptedException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

}
