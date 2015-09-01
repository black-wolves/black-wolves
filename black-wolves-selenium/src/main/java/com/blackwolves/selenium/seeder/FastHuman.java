/**
 * 
 */
package com.blackwolves.selenium.seeder;

import org.openqa.selenium.WebElement;

/**
 * @author danigrane
 *
 */
public class FastHuman extends Human {

	
	public FastHuman() {
		super();
		logger.info("Fast Human Created");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void type(WebElement input, String string) throws InterruptedException {
			input.sendKeys(string);
	}

}
