package elements;

import org.openqa.selenium.WebElement;

public class Element implements IElement{
	public WebElement webElement;
	private String sds;

	public Element(WebElement webElement) {
		this.webElement = webElement;
	}


	public void waitInvisibilityOfElement() {
	}

}
