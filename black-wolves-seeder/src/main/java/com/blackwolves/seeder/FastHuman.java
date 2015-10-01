/**
 * 
 */
package com.blackwolves.seeder;

import org.openqa.selenium.WebElement;

/**
 * @author danigrane
 *
 */
public class FastHuman extends Human {

	
	public FastHuman() {
		super();
		logger.info("Fast Human Created");
	}

	@Override
	public void type(WebElement input, String string) {
			input.sendKeys(string);
	}

}
