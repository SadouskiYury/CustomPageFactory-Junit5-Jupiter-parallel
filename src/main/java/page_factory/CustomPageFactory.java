package page_factory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.pagefactory.FieldDecorator;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;


public class CustomPageFactory {

	protected WebDriver driver;
	private final int timeOutInSeconds=10;

	public CustomPageFactory(WebDriver driver) {
		this.driver = driver;
	}


	public  <T> T init ( Class<T> pageClassToProxy) {
		T page = instantiatePage(driver, pageClassToProxy);
		initElements(new CustomFieldDecorator(new CustomElementFactory(driver,timeOutInSeconds)), page);
		return page;
	}


	private void initElements(FieldDecorator decorator, Object page) {
		for (Class proxyIn = page.getClass(); proxyIn != Object.class; proxyIn = proxyIn.getSuperclass()) {
			proxyFields(decorator, page, proxyIn);
		}

	}

	private static void proxyFields(FieldDecorator decorator, Object page, Class<?> proxyIn) {
		Field[] fields = proxyIn.getDeclaredFields();
		Field[] var4 = fields;
		int var5 = fields.length;

		for (int var6 = 0; var6 < var5; ++var6) {
			Field field = var4[var6];
			Object value = decorator.decorate(page.getClass().getClassLoader(), field);
			if (value != null) {
				try {
					field.setAccessible(true);
					field.set(page, value);
				} catch (IllegalAccessException var10) {
					throw new RuntimeException(var10);
				}
			}
		}

	}

	private <T> T instantiatePage(WebDriver driver, Class<T> pageClassToProxy) {
		try {
			try {
				Constructor<T> constructor = pageClassToProxy.getConstructor(WebDriver.class);
				return constructor.newInstance(driver);
			} catch (NoSuchMethodException var3) {
				return pageClassToProxy.newInstance();
			}
		} catch (ReflectiveOperationException var4) {
			throw new RuntimeException(var4);
		}
	}

}
