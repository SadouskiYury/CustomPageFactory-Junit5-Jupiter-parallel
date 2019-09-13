package feture;

import extension.CustomNameGenerator;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import pages.DashboardPage;
import pages.MyQBHomePage;


@DisplayNameGeneration(CustomNameGenerator.class)
public class HomeFeature extends BaseTest {

	@Test
	public void firstTestExample() {
		homePage.openExpantionPanel()
				.fillUserCredential("login", "password")
				.navigateToDashboardPage();

		dashboardPage.selectFacility("AutotestFacility_AddDevice_WithEthernetGatewayHub")
				.clickDashboardIconFromLeftNavBar();
	}
}
