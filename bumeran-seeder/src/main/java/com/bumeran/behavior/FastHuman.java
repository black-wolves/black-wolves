/**
 * 
 */
package com.bumeran.behavior;

import org.openqa.selenium.WebElement;

/**
 * 
 * @author gastondapice
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
