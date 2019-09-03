package page_factory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.pagefactory.Annotations;
import org.openqa.selenium.support.pagefactory.DefaultElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;

import java.lang.reflect.Field;

public class CustomElementFactory implements ElementLocatorFactory {
	private final WebDriver driver;
	private final int timeOutInSeconds;

	public CustomElementFactory(WebDriver driver, int timeOutInSeconds) {
		this.driver = driver;
		this.timeOutInSeconds = timeOutInSeconds;
	}


	@Override
	public ElementLocator createLocator(Field field) {
		FindBy annotation = field.getAnnotation(FindBy.class);
		if (annotation != null) {
			return new DefaultElementLocator(driver, new Annotations(field));
		}
		return new CustomElementLocator(driver,timeOutInSeconds, new CustomAnnotation(field));
	}

}

