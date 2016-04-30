/**
 * 
 */
package com.blackwolves.behavior;

import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author danigrane
 *
 */
public abstract class Human {
	
	public static final Logger logger = LoggerFactory.getLogger(Human.class);

	public abstract void type(WebElement input, String string);
	
}
