package container;

import elements.Element;
import elements.IElement;
import elements.Icon;
import org.openqa.selenium.WebElement;
import page_factory.xpath;

import java.util.List;

public class DropDown extends Element implements IElement {
	@xpath(xp = "//div[contains(@class,'option')]")
	private List<Element> options;
	private Icon userProfileSelect;

	public DropDown(WebElement webElement) {
		super(webElement);

	}

	public void setOptions(String parametr) {
		userProfileSelect.click();
		options.get(1).webElement.click();
	}

}
