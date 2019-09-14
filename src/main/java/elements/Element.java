package elements;


import driver.DriverSingleton;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;


public class Element implements IElement {
	protected WebElement webElement;
	private FluentWait<WebDriver> wait;
	private WebDriver driver;
	private By xpath;


	public Element(WebElement webElement) {
		this.webElement = webElement;
		driver = DriverSingleton.getInstance().getDriver();
		wait = new FluentWait(driver).pollingEvery(Duration.ofMillis(100)).withTimeout(Duration.ofSeconds(14));
		xpath = By.xpath(webElement.toString().substring(50));

	}


	public void waitInvisibilityOfElement() {
		wait.until(ExpectedConditions.invisibilityOf(webElement));
	}

	public void softAssertTextShouldBe(String expected){
		SoftAssertions softAssertions=new SoftAssertions();
		String actual =webElement.getText();
		softAssertions.assertThat(actual).isEqualToNormalizingNewlines(expected);
	}

	public Boolean attributeContains(String attribute, String value){
		return webElement.getAttribute(attribute).contains(value);
	}

	public Boolean attributeEquals(String attribute, String value){
		return webElement.getAttribute(attribute).equals(value);
	}
}
