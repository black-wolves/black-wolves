/**
 * 
 */
package com.blackwolves.seeder;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

/**
 * @author danigrane
 *
 */
public class DumbHuman extends Human {

	public DumbHuman() {
	}

	@Override
	public void type(WebElement input, String string) {
		char[] charArray = string.toCharArray();
		for (int i = 0; i < charArray.length; i++) {

			try {
				Character myCharacter = new Character(charArray[i]);
				if (YahooRunnable.throwDice()) {
					String typo = RandomStringUtils.random(1, "abcdefghijklmnopqrstuvwxyz");
					input.sendKeys(typo);
					Thread.sleep(YahooRunnable.randInt(500, 1800));
					input.sendKeys(Keys.BACK_SPACE);
				}
				input.sendKeys(myCharacter.toString());
			
				Thread.sleep(YahooRunnable.randInt(50, 300));
			} catch (InterruptedException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

}
