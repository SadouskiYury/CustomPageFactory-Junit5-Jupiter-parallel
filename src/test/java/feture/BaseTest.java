package feture;

import com.epam.reportportal.junit5.ReportPortalExtension;
import io.github.bonigarcia.seljup.SeleniumExtension;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import page_factory.CustomPageFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;


@ExtendWith(ReportPortalExtension.class)
public class BaseTest {
    private WebDriver driver;
    public CustomPageFactory page;
    private static int count=1;
    private static final Logger LOGGER = LogManager.getLogger(BaseTest.class);

    @RegisterExtension
    static SeleniumExtension seleniumExtension = new SeleniumExtension();
//    static  TestLauncher tl = new TestLauncher();

    @BeforeAll
    static void setupAll() {
        seleniumExtension.getConfig().setBrowserSessionTimeoutDuration("5m0s");
//        tl.LaunchTests();
    }

    @AfterAll
    static void afterAll() {

    }

    @BeforeEach
    public void classLevelSetup(ChromeDriver driver, TestInfo testInfo) throws InterruptedException {
        LOGGER.info(testInfo.getDisplayName());
//        System.out.println( testInfo.getTestMethod());
        System.out.println("Test :"+count+" " + Thread.currentThread().getName()+"\nSession Id: "+driver.getSessionId());
        count++;
        this.driver=driver;
        page = new CustomPageFactory(driver);
    }

    @AfterEach
    public void teardown () {
        driver.quit();

    }
}
