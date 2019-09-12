package driver;

import org.openqa.selenium.WebDriver;

public class DriverSingleton {
	private org.openqa.selenium.WebDriver driver;
	private static DriverSingleton instance = null;

	private DriverSingleton(WebDriver driver) {
		this.driver = driver;
	}

	public static void setDriver(WebDriver driver) {
		instance = new DriverSingleton(driver);
	}

	public static DriverSingleton getInstance() {
		if (instance == null) {
			System.out.println("Before get Instance, you should set up driver");
			throw new NullPointerException();
		}
		return instance;
	}

	public org.openqa.selenium.WebDriver getDriver() {
		return driver;
	}

	public void closeDriver() {
		driver.quit();
	}
}
