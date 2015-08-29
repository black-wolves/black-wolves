/**
 * 
 */
package com.blackwolves.selenium.seeder;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * @author danigrane
 *
 */
public abstract class Human {
	
	protected static final Logger logger = LogManager.getLogger(Human.class.getName());

	public abstract void type(WebElement input,String seed, WebDriver driver) throws InterruptedException;
	
	

}
