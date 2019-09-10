package feture;

import extension.CustomNameGenerator;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(CustomNameGenerator.class)
public class CopyHomeFeature extends BaseTest {

	@Test
	@Tag("Copy Home Test")
	public void firstTestExample() {
		homePage.openExpantionPanel()
				.fillUserCredential("autotestexecution+dev5555555555@gmail.com", "M8h76536")
				.navigateToDashboardPage();

		dashboardPage.selectFacility("AutotestFacility_AddDevice_WithEthernetGatewayHub")
				.waitInvisibilityOfIcon();

	}
}
