package elements;

import org.openqa.selenium.WebElement;

public class Icon extends Element {
	public Icon(WebElement webElement) {
		super(webElement);
	}
	public void click() {
		webElement.click();
	}
}
