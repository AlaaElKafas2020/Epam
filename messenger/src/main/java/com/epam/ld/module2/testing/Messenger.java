package com.epam.ld.module2.testing;

import com.epam.ld.module2.testing.template.TemplateGenerator;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Messenger {
    private final TemplateGenerator templateGenerator;

    public Messenger(TemplateGenerator templateGenerator) {
        this.templateGenerator = templateGenerator;
    }

    public void sendMessage(String to, String subject, String templatePath, String outputPath) throws Exception {
        Map<String, String> values = new HashMap<>();
        values.put("subject", subject);
        String messageBody = templateGenerator.generate(templatePath, values);
        if (outputPath == null) {
            System.out.printf("To: %s\nSubject: %s\n%s\n", to, subject, messageBody);
        } else {
            writeToFile(to, subject, messageBody, outputPath);
        }
    }


    private void writeToFile(String to, String subject, String messageBody, String outputPath) throws IOException {
        File outputFile = new File(outputPath);
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
        writer.write(String.format("To: %s\nSubject: %s\n%s\n", to, subject, messageBody));
        writer.close();
    }

    public void sendMessageFromConsole() throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("To: ");
        String to = reader.readLine();
        System.out.print("Subject: ");
        String subject = reader.readLine();
        System.out.print("Template path: ");
        String templatePath = reader.readLine();
        sendMessage(to, subject, templatePath, null);
    }

    public void sendMessageFromFile(String inputPath, String outputPath) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(inputPath));
        String to = reader.readLine();
        String subject = reader.readLine();
        String templatePath = reader.readLine();
        sendMessage(to, subject, templatePath, outputPath);
        reader.close();
    }
}
