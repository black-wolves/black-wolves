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
//		String[] args = {"10.10.1.1", "danielsaulino03@yahoo.com,wolf2015."};
//		String[] args = {"10.10.1.1", "eonhmxb@yahoo.com,Sx4z3daYKrxeE5&"};
//		String[] args = {"10.10.1.1", "azuwqaglw@yahoo.com,Bs%wfXf29qJj#$x"};
		String[] args = {"10.10.1.1", "jepvhfn@yahoo.com,T9&Gsa95ky3PQgc"};
		
		Seeder.main(args);
	}
	
	@Test
	@Ignore
	public void testSuscriptions()
	{
		seeder.suscribeToNewsletters();
	}
}
