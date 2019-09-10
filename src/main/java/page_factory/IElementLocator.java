package page_factory;

import org.openqa.selenium.WebElement;

import java.util.List;

public interface IElementLocator {

	WebElement findElement();

	List<WebElement> findElements();

	WebElement findElementWithoutWait();
}
