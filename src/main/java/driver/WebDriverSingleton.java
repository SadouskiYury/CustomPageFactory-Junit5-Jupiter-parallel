package driver;

import org.openqa.selenium.WebDriver;

public class WebDriverSingleton {
	public static WebDriver driver;

	private WebDriverSingleton() {
	}

	public static void setDriver(WebDriver driver) {
		WebDriverSingleton.driver = driver;
	}

	public static WebDriver getDriver() {
		return driver;
	}
}
