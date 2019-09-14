package elements;

import driver.DriverSingleton;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;

public class Alert {
	private FluentWait<WebDriver> wait;
	private WebDriver driver;
	private org.openqa.selenium.Alert alert;

	public Alert() {
		driver = DriverSingleton.getInstance().getDriver();
		wait = new FluentWait(driver).pollingEvery(Duration.ofMillis(100)).withTimeout(Duration.ofSeconds(10));
		this.alert = wait.until(ExpectedConditions.alertIsPresent());
	}

	public void setValueAndSubmit(String value) {
		alert.sendKeys(value);
		alert.accept();
	}

	public void closeAlert() {
		alert.dismiss();
	}

	public void textShouldBe(String expected) {
		String actual = alert.getText();
		SoftAssertions softAssertions = new SoftAssertions();
		softAssertions.assertThat(actual).isEqualToNormalizingNewlines(expected);
	}
}
