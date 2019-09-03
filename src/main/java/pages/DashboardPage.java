package pages;

import elements.Icon;
import org.openqa.selenium.WebDriver;

public class DashboardPage extends BasePage {

	private Icon sideNavIconDashboard;

	public DashboardPage(WebDriver driver) {
		super(driver);
	}

	public void clickDashboardIconFromLeftNavBar(){
		sideNavIconDashboard.click();
	}
}
