package elements;


import driver.DriverSingleton;
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

	public void textToBePresent(String string) {
		wait.until(ExpectedConditions.textToBe(xpath, string));
	}

}
