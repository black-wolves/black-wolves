package com.blackwolves.persistence.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @author gastondapice
 *
 */
@Component("applicationContextProvider")
public class ApplicationContextProvider {
	
	private static ApplicationContext applicationContext;

	@Autowired
	public ApplicationContextProvider(ApplicationContext applicationContext) {
		
		if (ApplicationContextProvider.applicationContext == null) {
			ApplicationContextProvider.applicationContext = applicationContext;
		}
	}
	
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}
}
