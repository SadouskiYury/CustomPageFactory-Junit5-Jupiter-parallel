package page_factory;


import lombok.Getter;
import org.openqa.selenium.WebDriver;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
@Getter
public class CustomPageFactory {

	private WebDriver driver;

	public CustomPageFactory(WebDriver webDriver) {
		this.driver = webDriver;
	}

	public <T> T init(Class<T> pageClassToProxy) {
		T page=instantiatePage(pageClassToProxy);
		initElements(new CustomFieldDecorator(driver), page);
		return page;
	}


	private void initElements(CustomFieldDecorator decorator, Object page) {
		for (Class proxyIn = page.getClass(); proxyIn != Object.class; proxyIn = proxyIn.getSuperclass()) {
			proxyFields(decorator, page, proxyIn);
		}

	}

	private void proxyFields(CustomFieldDecorator decorator, Object page, Class<?> proxyIn) {
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

	private <T> T instantiatePage(Class<T> pageClassToProxy) {
		try {
			try {
				Constructor<T> constructor = pageClassToProxy.getConstructor();
				return constructor.newInstance();
			} catch (NoSuchMethodException var3) {
				return pageClassToProxy.newInstance();
			}
		} catch (ReflectiveOperationException var4) {
			throw new RuntimeException(var4);
		}
	}

}
