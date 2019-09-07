package elements;

import org.openqa.selenium.WebElement;

public class Input extends Element implements IElement{
	public Input(WebElement webElement) {
		super(webElement);
	}

	public void typeText(String text){
		webElement.sendKeys(text);
	}
}
