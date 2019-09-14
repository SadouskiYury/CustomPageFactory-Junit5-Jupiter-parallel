package pages;

import elements.multiple.DropDown;
import elements.simple.Icon;

public class DashboardPage {

	private Icon sideNavIconDashboard;

	private DropDown userProfileSelect;

	public void clickDashboardIconFromLeftNavBar(){
		sideNavIconDashboard.click();
	}

	public DashboardPage selectFacility(String nameOfFacility) {
		userProfileSelect.setOptions(nameOfFacility);
		return this;
	}

	public DashboardPage waitInvisibilityOfIcon() {
		sideNavIconDashboard.waitInvisibilityOfElement();
		return this;
	}


}
