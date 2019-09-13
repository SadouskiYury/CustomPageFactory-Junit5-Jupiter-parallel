package page_factory;

import elements.Element;
import elements.IElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import page_factory.InvocationHandler.ListElementInvocationHandler;
import page_factory.InvocationHandler.ElementInvocationHandler;

import java.lang.reflect.*;
import java.util.List;


public class CustomFieldDecorator {
	private WebDriver driver;

	public CustomFieldDecorator() {
	}


	public Object decorate(ClassLoader loader, Field field) {
		Class<Element> decoratableClass = decoratableClass(field);
		if (decoratableClass != null) {
			IElementLocator locator = new ElementLocator(field);
			if (List.class.isAssignableFrom(field.getType())) {
				return createList(loader, locator, decoratableClass);
			}
			return createElement(loader, locator, decoratableClass);
		}
		return null;
	}

	/**
	 * Возвращает декорируемый класс поля,
	 * либо null если класс не подходит для декоратора
	 */

	@SuppressWarnings("unchecked")
	private Class<Element> decoratableClass(Field field) {
		Class<?> clazz = field.getType();
		// для списка обязательно должна быть задана аннотация
		if (List.class.isAssignableFrom(clazz) && field.getAnnotation(xpath.class) != null) {
			// Список должен быть параметризирован
			Type genericType = field.getGenericType();
			if (!(genericType instanceof ParameterizedType)) {
				return null;
			}
			// получаем класс для элементов списка
			clazz = (Class<?>) ((ParameterizedType) genericType).
					getActualTypeArguments()[0];
		}
		if (IElement.class.isAssignableFrom(clazz)) {
			return (Class<Element>) clazz;
		} else {
			return null;
		}
	}

	/**
	 * Создание элемента.
	 * Находит WebElement и передает его в кастомный класс
	 */
	protected IElement createElement(ClassLoader loader,
									 IElementLocator locator, Class<Element> clazz) {
		WebElement proxy = proxyForLocator(loader, locator);
		return createInstance(clazz, proxy);
	}

	/**
	 * Создает экземпляр класса,
	 * вызывая конструктор с аргументом WebElement
	 */
	private Element createInstance(Class<Element> clazz, WebElement element) {
		try {
			return clazz.getConstructor(WebElement.class)
					.newInstance(element);
		} catch (Exception e) {
			throw new AssertionError(
					"WebElement can't be represented as " + clazz
			);
		}
	}

	/**
	 * Создание экземпляра прокси Элемента
	 */
	private WebElement proxyForLocator(ClassLoader loader, IElementLocator locator) {
		InvocationHandler handler = new ElementInvocationHandler(locator);
		WebElement proxy = (WebElement) Proxy.newProxyInstance(loader, new Class[]{WebElement.class}, handler);
		return proxy;
	}

	/**
	 * Создание прокси списка Элементов
	 */
	@SuppressWarnings("unchecked")
	protected List<Element> createList(ClassLoader loader,
										IElementLocator locator,
										Class<Element> clazz) {
		InvocationHandler handler =
				new ListElementInvocationHandler(locator, clazz);
		List<Element> elements =
				(List<Element>) Proxy.newProxyInstance(
						loader, new Class[]{List.class}, handler);
		return elements;
	}

}


