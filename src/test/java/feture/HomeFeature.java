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
		page.init(MyQBHomePage.class).openExpantionPanel()
				.fillUserCredential("login", "password")
				.navigateToDashboardPage();

		page.init(DashboardPage.class).clickDashboardIconFromLeftNavBar();
	}
}
