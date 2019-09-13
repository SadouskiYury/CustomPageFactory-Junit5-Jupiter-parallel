package page_factory.InvocationHandler;

import elements.Element;
import elements.IElement;
import org.openqa.selenium.WebElement;
import page_factory.IElementLocator;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ListElementInvocationHandler implements InvocationHandler {
	private final Class<Element> clazz;
	private final IElementLocator locator;

	public ListElementInvocationHandler(IElementLocator locator,
										Class<Element> clazz) {
		this.locator = locator;
		this.clazz = clazz;
	}

	public Object invoke(Object object, Method method,
						 Object[] objects) throws Throwable {
		// Находит список WebElement и обрабатывает каждый его элемент,
		// возвращает новый список с элементами кастомного класса
		List<WebElement> elements = locator.findElements();
		List<IElement> customs = new ArrayList<IElement>();

		for (WebElement element : elements) {
			customs.add(createInstance(clazz, element));
		}
		try {
			return method.invoke(customs, objects);
		} catch (InvocationTargetException e) {
			throw e.getCause();
		}
	}

	private Element createInstance(Class<Element> clazz,
									WebElement element) {
		try {
			return clazz.getConstructor(WebElement.class).
					newInstance(element);
		} catch (Exception e) {
			throw new AssertionError(
					"WebElement can't be represented as " + clazz
			);
		}
	}
}