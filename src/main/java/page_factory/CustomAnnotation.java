package page_factory;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.support.pagefactory.Annotations;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

import static java.lang.String.format;


public class CustomAnnotation extends Annotations {
	private Field field;
	By locateBy;
	String xpath;

	public CustomAnnotation(Field field) {
		super(field);
		this.field = field;
	}

	@Override
	public By buildBy() {
		xpath findBy = field.getAnnotation(xpath.class);
		if (findBy == null) {
			xpath= Arrays.stream(StringUtils.splitByCharacterTypeCamelCase(getField().getName())).map(String::toLowerCase).collect(Collectors.joining("-"));
			return locateBy=By.xpath(format("//*[@qa-label='%s']",xpath));
		}

		// lets check if there are multiple parameters
		if (findBy.params().length>1) {
			Object[] objs = Arrays.copyOf(findBy.params(), findBy.params().length, Object[].class);
			xpath = format(findBy.xp(), objs);
		}
        else {
			xpath = format(findBy.xp(), findBy.param());
		}
		locateBy = By.xpath(xpath);
		if (locateBy == null) {
			throw new IllegalArgumentException("Cannot determine how to locate element " + field);
		}
		return locateBy;
	}
}
