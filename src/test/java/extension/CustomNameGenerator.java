package extension;


import org.apache.commons.lang.StringUtils;
import org.junit.jupiter.api.DisplayNameGenerator;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

public class CustomNameGenerator implements DisplayNameGenerator {
	@Override
	public String generateDisplayNameForClass(Class<?> testClass) {
		return this.replaceUnderscores(generateDisplayNameForClass(testClass.getName()));
	}

	@Override
	public String generateDisplayNameForNestedClass(Class<?> nestedClass) {
		return this.replaceUnderscores(nestedClass.getSimpleName());
	}

	@Override
	public String generateDisplayNameForMethod(Class<?> testClass, Method testMethod) {
		return this.replaceUnderscores(replaceUnderscores((testMethod.getName())) + DisplayNameGenerator.parameterTypesAsString(testMethod));
	}

	private String replaceUnderscores(String name) {
		return StringUtils.capitalize(Arrays.stream(StringUtils.splitByCharacterTypeCamelCase(name)).map(String::toLowerCase).collect(Collectors.joining(" ")));
	}

	public String generateDisplayNameForClass(String name) {
		int lastDot = name.lastIndexOf(46);
		return name.substring(lastDot + 1);
	}

}

