package com.blackwolves.persistence;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:com/blackwolves/persistence/create-schema-context.xml")
//Test always ignored, un-ignore only to recreate the database.
//@Ignore
public class CreateDBSchemaTest {

	@Test
	@Transactional
	public void applicationContextTest() {
	}
}
