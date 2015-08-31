package com.blackwolves.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:com/blackwolves/service/test-application-context-black-wolves-service.xml" })
public class ApplicationContextTest {
	
	@Test
	public void testApplicationContext() {
	}
}
