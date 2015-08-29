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
public class FastHuman extends Human {

	
	public FastHuman() {
		super();
		logger.info("Fast Human Created");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void type(WebElement input, String seed, WebDriver driver) throws InterruptedException {
			input.sendKeys(seed);
	}

}
