package elements;

import org.openqa.selenium.WebElement;

public class Icon extends Element implements IElement{
	public Icon(WebElement webElement) {
		super(webElement);
	}
	public void click() {
		webElement.click();
	}
}
