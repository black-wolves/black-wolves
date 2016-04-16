package com.blackwolves.mail;

import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author gastondapice
 *
 */
public class Human {
	
	public static final Logger logger = LoggerFactory.getLogger(Human.class);

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
