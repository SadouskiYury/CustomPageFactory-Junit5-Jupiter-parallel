package page_factory;

import elements.IElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsElement;
import org.openqa.selenium.interactions.Locatable;
import org.openqa.selenium.support.pagefactory.FieldDecorator;
import page_factory.InvocationHandler.CustomElementInvocationHandler;
import page_factory.InvocationHandler.CustomListElementInvocationHandler;

import java.lang.reflect.*;
import java.util.List;

public class CustomFieldDecorator implements FieldDecorator {
	private WebDriver driver;

	public CustomFieldDecorator(WebDriver driver) {
		this.driver = driver;
	}

	@Override
	public Object decorate(ClassLoader loader, Field field) {
		Class<IElement> decoratableClass = decoratableClass(field);
		if (decoratableClass != null) {
			ElementLocator locator = new ProxyElementLocator(driver).createLocator(field);
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
	private Class<IElement> decoratableClass(Field field) {
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
			return (Class<IElement>) clazz;
		} else {
			return null;
		}
	}

	/**
	 * Создание элемента.
	 * Находит WebElement и передает его в кастомный класс
	 */
	protected IElement createElement(ClassLoader loader,
									 ElementLocator locator, Class<IElement> clazz) {
		WebElement proxy = proxyForLocator(loader, locator);
		return createInstance(clazz, proxy);
	}

	/**
	 * Создает экземпляр класса,
	 * вызывая конструктор с аргументом WebElement
	 */
	private IElement createInstance(Class<IElement> clazz, WebElement element) {
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
	private WebElement proxyForLocator(ClassLoader loader, ElementLocator locator) {
		InvocationHandler handler = new CustomElementInvocationHandler(locator);
		WebElement proxy = (WebElement) Proxy.newProxyInstance(loader, new Class[]{WebElement.class, WrapsElement.class, Locatable.class}, handler);
		return proxy;
	}

	/**
	 * Создание прокси списка Элементов
	 */
	@SuppressWarnings("unchecked")
	protected List<IElement> createList(ClassLoader loader,
										ElementLocator locator,
										Class<IElement> clazz) {
		InvocationHandler handler =
				new CustomListElementInvocationHandler(locator, clazz);
		List<IElement> elements =
				(List<IElement>) Proxy.newProxyInstance(
						loader, new Class[]{List.class}, handler);
		return elements;
	}

}


