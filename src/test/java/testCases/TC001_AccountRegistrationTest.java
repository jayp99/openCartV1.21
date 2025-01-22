package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.TestBase;

public class TC001_AccountRegistrationTest extends TestBase {

	// TEST test case should have validation
	@Test(groups= {"Regression", "Master"})
	public void verify_account_registration() throws InterruptedException {

		logger.info("*** Starting TC001 AccountRegistrationTest ***");
		try {
			HomePage hp = new HomePage(driver);
			hp.clickMyAccount();
			logger.info("*** Clicked on My Account link ***");
			hp.clickRegister();
			logger.info("*** Clicked on Register ***");

			AccountRegistrationPage arp = new AccountRegistrationPage(driver);

			logger.info("*** Providing customer details ***");
			arp.setFirstName(randomString().toUpperCase());
			arp.setLastName(randomString().toUpperCase());
			arp.setEmail(randomString() + "@myemail.com");
			arp.setTelephone(randomNumbers());

			String pwd = randomAlphaNumeric();

			arp.setPassword(pwd);
			arp.setConfrimPassword(pwd);

			arp.setPrivacyPolicy();
			arp.clickContinue();

			logger.info("*** Validating account created message ***");
			String confMsg = arp.getConfirmationMsg();
			
			if(confMsg.equals("Your Account Has Been Created!")) {
				Assert.assertTrue(true);
			}
			else {
				logger.error("Test failed...");
				logger.debug("Debug logs...");
				Assert.assertTrue(false);
				
			}
			//Assert.assertEquals(confMsg, "Your Account Has Been Created!");
		}

		catch (Exception e) {
			//fails entire test when there is any exception
			Assert.fail();
		}
		
		logger.info("*** Finished TC001_AccountRegistrationTest ***");
	}
}
