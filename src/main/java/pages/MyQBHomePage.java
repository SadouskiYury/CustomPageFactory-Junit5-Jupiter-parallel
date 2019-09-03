package pages;

import elements.Button;
import elements.Input;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import page_factory.xpath;

public class MyQBHomePage extends BasePage {
	private String baaseURI="https://www-dev.myqbusiness.com/";

	public MyQBHomePage(WebDriver driver) {
		super(driver);
	}

	@xpath(xp = "//button[@id='nav-expander' and normalize-space()='Log In']")
	private Button button;

	@xpath(xp = "//*[@id='%s']",param = "signup")
	private Button buttonLogIn;

	@xpath(xp = "//*[@id ='Email']")
	private Input inputEmail;

	@FindBy(id = "Password")
	private Input inputPassword;


	public MyQBHomePage openExpantionPanel(){
		driver.get(baaseURI);
		button.click();
		return this;
	}

	public MyQBHomePage fillUserCredential(String email, String password){
		inputEmail.typeText(email);
		inputPassword.typeText(password);
		return this;
	}

	public void navigateToDashboardPage() {
		buttonLogIn.click();

//		}
	}

}
