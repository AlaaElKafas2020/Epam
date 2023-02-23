package org.example;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class RunDiskAnalyzer {
    public static void main(String[] args) {

        DiskAnalyzer app = new DiskAnalyzer();

        try {
            args = new String[]{"C:\\Windows\\System32\\", "1"};
            app.runDiskAnalyzer(args);

            args = new String[]{"C:\\Windows\\System32\\", "2"};
            app.runDiskAnalyzer(args);

            args = new String[]{"C:\\Windows\\System32\\", "3"};
            app.runDiskAnalyzer(args);

            args = new String[]{"C:\\Windows\\System32\\", "4"};
            app.runDiskAnalyzer(args);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}