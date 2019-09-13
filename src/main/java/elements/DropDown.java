package elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.UnexpectedTagNameException;

import java.util.List;

public class DropDown extends Element{


	public DropDown(WebElement webElement) {
		super(webElement);
	}


	public void setOptions(String nameOfFacility) {
		if (webElement.getTagName().contains("select")) {
			webElement.click();
			List<WebElement> listFacility = webElement.findElements(By.xpath("//div[contains(@class,'ng-option')]"));
			listFacility.stream().filter(s -> s.getText().equals(nameOfFacility)).findFirst().get().click();

		} else {
			throw new UnexpectedTagNameException("select", webElement.getTagName());
		}
	}
}
