package com.epam.ld.module2.testing.template;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@MockitoSettings(strictness = Strictness.STRICT_STUBS)
@ExtendWith(MockitoExtension.class)
public class TemplateGeneratorTest {
    private TemplateGenerator generator;
    private Map<String, String> values;

    @BeforeEach
    public void setup() {
        generator = new TemplateGenerator();
        values = new HashMap<>();
        values.put("subject", "Test email");
        values.put("body", "This is a test email.");
    }

    @Test
    public void testGenerate() throws Exception {
        String template = "Subject: #{subject}\n\n#{body}";

        String result = generator.generate(template, values);

        assertEquals("Subject: Test email\n\nThis is a test email.", result);
    }

    @Test
    public void testGenerateNullTemplate() {
        assertThrows(Exception.class, () -> generator.generate(null, values));
    }

    @Test
    public void testGenerateEmptyTemplate() {
        assertThrows(Exception.class, () -> generator.generate("", values));
    }

    @Test
    public void testGenerateNullValues() {
        assertThrows(Exception.class, () -> generator.generate("Template", null));
    }

    @Test
    public void testGenerateEmptyValues() {
        assertThrows(Exception.class, () -> generator.generate("Template", new HashMap<>()));
    }

    @Test
    public void testGenerateMissingPlaceholder() {
        String template = "Subject: #{subject}\n\n#{body}\n\n#{footer}";


    }
}