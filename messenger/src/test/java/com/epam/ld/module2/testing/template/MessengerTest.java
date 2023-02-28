package com.epam.ld.module2.testing.template;

import com.epam.ld.module2.testing.Messenger;
import org.junit.Rule;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;


public class MessengerTest {

    private final ByteArrayOutputStream consoleOut = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Mock
    private TemplateGenerator templateGenerator;

    private Messenger messenger;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        System.setOut(new PrintStream(consoleOut));
        messenger = new Messenger(templateGenerator);
    }

    @Test
    public void testSendMessage_consoleMode() throws Exception {
        String to = "alaa@example.com";
        String subject = "Test Email";
        String message = "Hello Alaa, this is a test email!";
        String templatePath = "messenger/src/test/resources/template.txt";//"path/to/template.txt";

        Map<String, String> templateValues = new HashMap<>();
        templateValues.put("subject", subject);
        when(templateGenerator.generate(templatePath, templateValues))
                .thenReturn(message);

        messenger.sendMessage(to, subject, templatePath, null);

        String expectedOutput = String.format("To: %s%nSubject: %s%n%s%n", to, subject, message);
        Assertions.assertEquals(expectedOutput, consoleOut.toString());
    }

    @ParameterizedTest
    @CsvSource({
            "path/to/missing_template.txt, subject, java.nio.file.NoSuchFileException",
            "path/to/template.txt, , java.lang.IllegalArgumentException"
    })
    @DisplayName("Throws exception when required template variables are missing")
    public void testSendMessage_missingTemplateVariables(String templatePath, String subject, Class<Exception> expectedException) throws Exception {
        when(templateGenerator.generate(anyString(), any()))
                .thenThrow(expectedException);

        Assertions.assertThrows(expectedException, () ->
                messenger.sendMessage("john@example.com", subject, templatePath, null));
    }

    @Nested
    @DisplayName("File mode tests")
    class FileModeTests {

        private Path tempDir;

        @BeforeEach
        public void setUp() throws IOException {
            tempDir = Files.createTempDirectory("messenger-test");
        }

        @ParameterizedTest
        @CsvSource({
                "path/to/missing_template.txt, subject, java.nio.file.NoSuchFileException",
                "path/to/template.txt, , java.lang.IllegalArgumentException"
        })
        @DisplayName("Throws exception when required template variables are missing")
        public void testSendMessage_missingTemplateVariables(String templatePath, String subject, Class<Exception> expectedException) throws Exception {
            when(templateGenerator.generate(anyString(), any()))
                    .thenThrow(expectedException);

            Assertions.assertThrows(expectedException, () ->
                    messenger.sendMessage("alaa@example.com", subject, templatePath, tempDir.resolve("output.txt").toString()));
        }
    }
    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test
    void testSendMessage_outputToFile() throws Exception {
        // Arrange
        String to = "alaa@example.com";
        String subject = "Test subject";
        String templatePath = "src/main/resources/template.txt";
        String outputPath = tempFolder.newFile("output.txt").getAbsolutePath();
        Map<String, String> variables = new HashMap<>();
        variables.put("name", "Alaa");
        variables.put("message", "Hello");

        when(templateGenerator.generate(eq(templatePath), eq(variables)))
                .thenReturn("Dear #{name},\n\n#{message}\n\nSincerely,\nThe team");

        // Act
        messenger.sendMessage(to, subject, templatePath, outputPath);

        // Assert
        String expectedOutput = String.format("To: %s\nSubject: %s\nDear John,\n\nHello\n\nSincerely,\nThe team\n", to, subject);
        BufferedReader reader = new BufferedReader(new FileReader(outputPath));
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line).append("\n");
        }
        String actualOutput = builder.toString();
        assertEquals(expectedOutput, actualOutput);
    }

}