package elements.simple;

import elements.Element;
import org.openqa.selenium.WebElement;

public class Input extends Element {
	public Input(WebElement webElement) {
		super(webElement);
	}

	public void typeText(String text){
		webElement.sendKeys(text);
	}
}
