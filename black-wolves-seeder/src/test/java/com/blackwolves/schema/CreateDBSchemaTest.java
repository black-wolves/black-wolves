package com.blackwolves.schema;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:application-context.xml")
//Test always ignored, un-ignore only to recreate the database.
@Ignore
public class CreateDBSchemaTest {
	
	@Test
	public void applicationContextTest() {
	}
}
