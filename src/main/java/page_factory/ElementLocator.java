package page_factory;

import org.openqa.selenium.WebElement;

import java.util.List;

public interface ElementLocator {

	WebElement findElement();

	List<WebElement> findElements();

	void waitInvisibilityOfElement();
}
