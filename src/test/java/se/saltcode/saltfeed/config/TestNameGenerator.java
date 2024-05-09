package se.saltcode.saltfeed.config;

import org.junit.jupiter.api.DisplayNameGenerator;

import java.lang.reflect.Method;

public class TestNameGenerator extends DisplayNameGenerator.Standard {
    @Override
    public String generateDisplayNameForClass(Class<?> testClass) {
        return replaceCamelCase(super.generateDisplayNameForNestedClass(testClass))
                .replace("test", "");
    }

    @Override
    public String generateDisplayNameForNestedClass(Class<?> nestedClass) {
        return replaceCamelCase(super.generateDisplayNameForNestedClass(nestedClass));
    }

    @Override
    public String generateDisplayNameForMethod(Class<?> testClass, Method testMethod) {
        return this.replaceCamelCase(testMethod.getName()) +
                DisplayNameGenerator.parameterTypesAsString(testMethod);
    }

    private String replaceCamelCase(String camelCase) {
        StringBuilder result = new StringBuilder();
        result.append(camelCase.charAt(0));
        for (int i = 1; i < camelCase.length(); i++) {
            if (Character.isUpperCase(camelCase.charAt(i))) {
                result.append(' ');
                result.append(Character.toLowerCase(camelCase.charAt(i)));
            } else {
                result.append(camelCase.charAt(i));
            }
        }
        return result.toString();
    }
}
