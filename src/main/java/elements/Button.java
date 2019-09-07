package elements;

import org.openqa.selenium.WebElement;

public class Button extends Element implements IElement {

	public Button(WebElement webElement) {
		super(webElement);
	}

	public void click() {
		webElement.click();

	}
}
