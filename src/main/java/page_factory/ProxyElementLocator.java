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


public class ProxyElementLocator implements ElementLocator {
	private FluentWait<WebDriver> wait;
	private By locateBy;
	private String xpath;
	private WebDriver driver;


	public ProxyElementLocator(WebDriver driver) {
		wait = new FluentWait(driver).pollingEvery(Duration.ofMillis(100)).withTimeout(Duration.ofSeconds(15)).ignoring(NoSuchElementException.class);
		this.driver = driver;
	}


	@Override
	public WebElement findElement() {
		return wait.until(ExpectedConditions.elementToBeClickable(locateBy));
	}

	@Override
	public List<WebElement> findElements() {
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locateBy));
	}

	public ElementLocator createLocator(Field field) {
		xpath findBy = field.getAnnotation(xpath.class);
		if (findBy == null) {
			xpath = Arrays.stream(StringUtils.splitByCharacterTypeCamelCase(field.getName())).map(String::toLowerCase).collect(Collectors.joining("-"));
			locateBy = By.xpath(format("//*[@qa-label='%s']", xpath));
			return this;
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
			throw new IllegalArgumentException("Cannot determine how to locate element " + field);
		}
		return this;
	}

	public void waitInvisibilityOfElement() {
		wait.until(ExpectedConditions.invisibilityOfElementLocated(locateBy));
	}
}

