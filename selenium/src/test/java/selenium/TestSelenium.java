/**
 * 
 */
package selenium;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.blackwolves.selenium.seeder.Seeder;
import com.blackwolves.selenium.up.Up;

/**
 * @author gaston.dapice
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:application-context.xml" })
public class TestSelenium {
	
	@Autowired
	private Up selenium;
	
	@Autowired
	private Seeder seeder;

	/**
	 * Test the sendCampaign method
	 */
	@Test
	@Ignore
	public void testSendCampaign(){
		selenium.sendCampaign();
	}
	
	/**
	 * Checks mails
	 */
	@Test
	public void testCheckMail() {
		Seeder.checkMail();
	}
	
	@Test
	@Ignore
	public void testSuscriptions()
	{
		seeder.suscribeToNewsletters();
	}
}
