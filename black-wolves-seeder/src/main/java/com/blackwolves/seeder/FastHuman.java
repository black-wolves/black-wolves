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
	}

	@Override
	public void type(WebElement input, String string) {
			input.sendKeys(string);
	}

}
