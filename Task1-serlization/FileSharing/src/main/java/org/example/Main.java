package org.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Date;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        // Set up the database connection information
        String url = "jdbc:mysql://localhost:3306/ems";
        String username = "root";
        String password = "root";

        // Create a new FileDAO instance
        FileDAO fileDAO = new FileDAO(url, username, password);

        // Set up a file to test with path =C:\Users\AlaaEssam\Desktop\Task1-serlization\FileSharing\src\main\resources\test.txt
        File testFile = new File("C:\\Users\\AlaaEssam\\Desktop\\Task1-serlization\\FileSharing\\src\\main\\resources\\test.txt");
        byte[] data;
        try {
            data = Files.readAllBytes(testFile.toPath());
        } catch (IOException e) {
            System.out.println("Error reading test file: " + e.getMessage());
            return;
        }

        // Save the test file to the database
        try {
            fileDAO.saveFile(testFile.getName(), testFile.length(), data, new Date(System.currentTimeMillis()));
        } catch (SQLException e) {
            System.out.println("Error saving file: " + e.getMessage());
            return;
        }

        // Retrieve the file from the database
        File retrievedFile;
        try {
            retrievedFile = fileDAO.getFile(1);
        } catch (SQLException | IOException e) {
            System.out.println("Error retrieving file: " + e.getMessage());
            return;
        }

        // Print out the contents of the retrieved file
        try {
            System.out.println(new String(Files.readAllBytes(retrievedFile.toPath())));
        } catch (IOException e) {
            System.out.println("Error printing retrieved file: " + e.getMessage());
        }
    }
}
