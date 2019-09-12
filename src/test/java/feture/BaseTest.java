package feture;

import com.epam.reportportal.junit5.ReportPortalExtension;
import driver.DriverSingleton;
import io.github.bonigarcia.seljup.SeleniumExtension;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.openqa.selenium.chrome.ChromeDriver;
import page_factory.CustomPageFactory;
import pages.DashboardPage;
import pages.MyQBHomePage;

@ExtendWith(ReportPortalExtension.class)
public class BaseTest {
    public CustomPageFactory pageFactory;
    public MyQBHomePage homePage;
    public DashboardPage dashboardPage;
    private String baaseURI = "https://www-dev.myqbusiness.com/";

//		private static final Logger LOGGER = LogManager.getLogger(BaseTestUI.class);


    @RegisterExtension
    static SeleniumExtension seleniumExtension = new SeleniumExtension();

    @BeforeAll
    static void setupAll() {
        seleniumExtension.getConfig().setBrowserSessionTimeoutDuration("5m0s");
    }

    @AfterAll
    static void afterAll() {

    }

    @BeforeEach
    public void classLevelSetup(ChromeDriver driver, TestInfo testInfo) throws InterruptedException {
        Thread.sleep(1000);
        System.out.println("Test :" + testInfo.getDisplayName() + " " + Thread.currentThread().getName() + "\nSession Id: " + driver.getSessionId());
        pageFactory = new CustomPageFactory();
        DriverSingleton.setDriver(driver);
        initPages();
        driver.get(baaseURI);
    }

    @AfterEach
    public void teardown() {
        DriverSingleton.getInstance().closeDriver();
    }

    private void initPages() {
        homePage = pageFactory.init(MyQBHomePage.class);
        dashboardPage = pageFactory.init(DashboardPage.class);
    }
}
