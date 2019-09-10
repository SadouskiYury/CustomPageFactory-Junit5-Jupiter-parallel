package page_factory.InvocationHandler;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import page_factory.IElementLocator;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ElementInvocationHandler implements InvocationHandler {
	private final IElementLocator locator;

	public ElementInvocationHandler(IElementLocator locator) {
		this.locator = locator;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] objects) throws Throwable {
		WebElement element;
		String nameMethod = method.getName();
		switch (nameMethod) {
			case "isDisplayed":
			case "isEnable":
				try {
					element = this.locator.findElementWithoutWait();
				} catch (NoSuchElementException ex) {
					return Boolean.valueOf(false);
				}
				break;
			case "toString":
				return "Proxy element for: " + this.locator.toString();
			default:
				element = this.locator.findElement();
				break;
		}
		try {
			return method.invoke(element, objects);
		} catch (InvocationTargetException var6) {
			throw var6.getCause();
		}
	}
}

