package page_factory;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.DefaultFieldDecorator;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;

import java.lang.reflect.Field;

public class CustomFieldDecorator extends DefaultFieldDecorator {

	public CustomFieldDecorator(ElementLocatorFactory factory) {
		super(factory);
	}

	@Override
	public Object decorate(ClassLoader loader, Field field) {
		Class<?> decoratableClass = decoratableClass(field);
		if (decoratableClass != null) {
			ElementLocator locator = factory.createLocator(field);
			if (locator == null) {
				return null;
			}
			return createElement(loader, locator, decoratableClass);
		}
		return super.decorate(loader, field);
	}

	private Class<?> decoratableClass(Field field) {
		Class<?> clazz = field.getType();
		// у элемента должен быть конструктор, принимающий WebElement
		try {
			clazz.getConstructor(WebElement.class);
		} catch (Exception e) {
			return null;
		}
		return clazz;
	}


	/**
	 * Создание элемента.
	 * Находит WebElement и передает его в кастомный класс
	 */
	protected <T> T createElement(ClassLoader loader,
								  ElementLocator locator, Class<T> clazz) {
		WebElement proxy = proxyForLocator(loader, locator);
		return createInstance(clazz, proxy);
	}

	/**
	 * Создает экземпляр класса,
	 * вызывая конструктор с аргументом WebElement
	 */
	private <T> T createInstance(Class<T> clazz, WebElement element) {
		try {
			return (T) clazz.getConstructor(WebElement.class)
					.newInstance(element);
		} catch (Exception e) {
			throw new AssertionError(
					"WebElement can't be represented as " + clazz
			);
		}
	}
}

