package feture;

import extension.CustomNameGenerator;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import pages.DashboardPage;
import pages.MyQBHomePage;

@DisplayNameGeneration(CustomNameGenerator.class)
public class CopyHomeFeature extends BaseTest {

	@Test
	public void firstTestExample() {
		page.init(MyQBHomePage.class).openExpantionPanel()
				.fillUserCredential("autotestexecution+dev5555555555@gmail.com", "M8h76536")
				.navigateToDashboardPage();

		page.init(DashboardPage.class).clickDashboardIconFromLeftNavBar();
	}
}
