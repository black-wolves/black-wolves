/**
 * 
 */
package com.bumeran.behavior;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import com.bumeran.seeder.BumeranSeeder;

/**
 * 
 * @author gastondapice
 *
 */
public class DumbHuman extends Human {

	public DumbHuman() {
		logger.info("DumbHuman Created");
	}

	@Override
	public void type(WebElement input, String string) {
		char[] charArray = string.toCharArray();
		for (int i = 0; i < charArray.length; i++) {

			try {
				Character myCharacter = new Character(charArray[i]);
				if (BumeranSeeder.throwDice()) {
					logger.info("Human make mistakes");
					String typo = RandomStringUtils.random(1, "abcdefghijklmnopqrstuvwxyz");
					input.sendKeys(typo);
					Thread.sleep(BumeranSeeder.randInt(500, 1800));
					input.sendKeys(Keys.BACK_SPACE);
				}
				input.sendKeys(myCharacter.toString());
			
				Thread.sleep(BumeranSeeder.randInt(50, 300));
			} catch (InterruptedException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

}
