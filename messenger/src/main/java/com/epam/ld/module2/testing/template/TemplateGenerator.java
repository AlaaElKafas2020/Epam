package com.epam.ld.module2.testing.template;


import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TemplateGenerator {
    private static final String PLACEHOLDER_REGEX = "#\\{(.+?)\\}";

    public String generate(String template, Map<String, String> values) throws Exception {
        if (template == null || template.isEmpty()) {
            throw new Exception("Template cannot be null or empty");
        }

        if (values == null || values.isEmpty()) {
            throw new Exception("Values cannot be null or empty");
        }

        StringBuilder result = new StringBuilder(template);
        Pattern pattern = Pattern.compile(PLACEHOLDER_REGEX);
        Matcher matcher = pattern.matcher(template);

        while (matcher.find()) {
            String placeholder = matcher.group(1);
            String value = values.get(placeholder);

            if (value == null) {
                throw new Exception("Value for placeholder " + placeholder + " not found");
            }

            result.replace(matcher.start(), matcher.end(), value);
            matcher.reset(result);
        }

        return result.toString();
    }
}
