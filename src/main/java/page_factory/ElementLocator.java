package page_factory;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.lang.reflect.Field;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

public class ElementLocator implements IElementLocator {
	protected FluentWait<WebDriver> wait;
	private final By locatedBy;
	private String xpath;
	private WebDriver driver;


	public ElementLocator(WebDriver driver, Field field) {
		wait = new FluentWait(driver).pollingEvery(Duration.ofMillis(100)).withTimeout(Duration.ofSeconds(25)).ignoring(NoSuchElementException.class);
		this.driver = driver;
		this.locatedBy = createLocator(field);
	}


	@Override
	public WebElement findElement() {
		return wait.until(ExpectedConditions.elementToBeClickable(locatedBy));
	}

	@Override
	public List<WebElement> findElements() {
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locatedBy));
	}

	@Override
	public WebElement findElementWithoutWait() {
		return driver.findElement(locatedBy);
	}


	private By createLocator(Field field) {
		By locateBy;
		xpath findBy = field.getAnnotation(xpath.class);
		if (findBy == null) {
			xpath = Arrays.stream(StringUtils.splitByCharacterTypeCamelCase(field.getName())).map(String::toLowerCase).collect(Collectors.joining("-"));
			locateBy = By.xpath(format("//*[@qa-label='%s']", xpath));
			return locateBy;
		}
		// create By with multiple parameters
		if (findBy.params().length > 1) {
			Object[] objs = Arrays.copyOf(findBy.params(), findBy.params().length, Object[].class);
			xpath = format(findBy.xp(), objs);
		} else {
			xpath = format(findBy.xp(), findBy.param());
		}
		locateBy = By.xpath(xpath);
		if (locateBy == null) {
			throw new IllegalArgumentException("Cannot determine how to locate elements.element " + field);
		}
		return locateBy;
	}

	@Override
	public String toString() {
		return "WebElement {locatedBy=" + locatedBy;
	}
}

