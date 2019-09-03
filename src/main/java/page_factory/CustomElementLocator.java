package page_factory;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.AbstractAnnotations;
import org.openqa.selenium.support.pagefactory.AjaxElementLocator;

public class CustomElementLocator extends AjaxElementLocator {

	public CustomElementLocator(SearchContext context, int timeOutInSeconds, AbstractAnnotations annotations) {
		super(context, timeOutInSeconds, annotations);
	}

	@Override
	protected boolean isElementUsable(WebElement element) {
		return element.isDisplayed();
	}
}
