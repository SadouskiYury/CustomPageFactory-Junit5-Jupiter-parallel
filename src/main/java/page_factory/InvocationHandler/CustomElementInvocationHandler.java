package page_factory.InvocationHandler;

import org.openqa.selenium.WebElement;
import page_factory.ElementLocator;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CustomElementInvocationHandler implements InvocationHandler {
	private final ElementLocator locator;

	public CustomElementInvocationHandler(ElementLocator locator) {
		this.locator = locator;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] objects) throws Throwable {
		WebElement element;

		if ("waitInvisibilityOfElement".equals(method.getName())) {
			locator.waitInvisibilityOfElement();
		}
			element = this.locator.findElement();
		try {
			return method.invoke(element, objects);
		} catch (InvocationTargetException var6) {
			throw var6.getCause();
		}
	}
}
