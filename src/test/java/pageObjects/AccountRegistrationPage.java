package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AccountRegistrationPage extends BasePage {

	//call super constructor which is BasePage constructor 
	public AccountRegistrationPage(WebDriver driver) {
		
		super(driver);
	}
	
	//locators
	@FindBy(xpath="//input[@id='input-firstname']" )
	WebElement txtFirstName;
	
	@FindBy(xpath="//input[@id='input-lastname']" )
	WebElement txtLastName;
	
	@FindBy(xpath="//input[@id='input-email']" )
	WebElement txtEmail;
	
	@FindBy(xpath="//input[@id='input-telephone']" )
	WebElement txtTelephone;
	
	@FindBy(xpath="//input[@id='input-password']" )
	WebElement txtPassword;
	
	@FindBy(xpath="//input[@id='input-confirm']" )
	WebElement txtConfirmPassword;
	
	@FindBy(xpath="//input[@name='agree']" )
	WebElement chkPolicy;
	
	@FindBy(xpath="//input[@value='Continue']" )
	WebElement btnContinue;

	
	@FindBy(xpath="//h1[normalize-space()='Your Account Has Been Created!']" )
	WebElement msgConfirmation;
	
	//actions on locators
	public void setFirstName(String firstName) {
		txtFirstName.sendKeys(firstName);
	}
	
	public void setLastName(String lastName) {
		txtLastName.sendKeys(lastName);
	}
	
	public void setEmail(String email) {
		txtEmail.sendKeys(email);
	}
	
	public void setTelephone(String telephone) {
		txtTelephone.sendKeys(telephone);
	}
	
	public void setPassword(String pwd) {
		txtPassword.sendKeys(pwd);
	}
	
	public void setConfrimPassword(String pwd) {
		txtConfirmPassword.sendKeys(pwd);
	}
	
	public void setPrivacyPolicy() {
		chkPolicy.click();
	}
	
	public void clickContinue() {
		btnContinue.click();
	}
	
	//Page object should not have validation, only test cases class should validate
	public String getConfirmationMsg() {
		try {
			return(msgConfirmation.getText());
		}
		catch(Exception e){
			return (e.getMessage());
		}
		
	}
	
	
}
