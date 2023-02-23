package org.example;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
public class DiskAnalyzer {

    private Path fileToWrite = Paths.get("src\\main\\java\\org\\example\\DiskAnalyzerOutput.txt");

    public void runDiskAnalyzer(String[] args) throws IOException {

        String letterToFind = "s";

        checkInput(args);
        switch (args[1]) {
            case "1":
                findMaxNumberOfLettersInFileName(args, letterToFind);
                break;
            case "2":
                findTopFiveSizeFilesInDirectory(args);
                break;
            case "3":
                countAverageFileSizeInDirectory(args);
                break;
            case "4":
                countFilesAndDirsByFirstLetter(args);
                break;
        }
    }
    private ArrayList<File> getFileCollectionFromDir(File dir, ArrayList<File> fileNamesInStrings) {

        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        //рекурсивный вызов
                        getFileCollectionFromDir(file, fileNamesInStrings);
                    } else {
                        fileNamesInStrings.add(file);
                    }
                }
            }
        }
        return fileNamesInStrings;
    }
    private ArrayList<File> getDirCollectionFromDir(File dir, ArrayList<File> dirNamesInStrings) {

        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        dirNamesInStrings.add(file);
                        getDirCollectionFromDir(file, dirNamesInStrings);
                    }
                }
            }
        }
        return dirNamesInStrings;
    }

    private void findMaxNumberOfLettersInFileName(String[] args, String letterToFind) throws IOException {

        ArrayList<File> files = new ArrayList<>();
        ArrayList<File> listOfFiles = getFileCollectionFromDir(new File(args[0]), files);
        int countLetter = 0;
        String fileWithMaxNumberOfLetters = null;

        for (File filename : listOfFiles
        ) {
            if (StringUtils.countMatches(filename.getName(), letterToFind) > countLetter) {
                countLetter = StringUtils.countMatches(filename.getName(), letterToFind);
                fileWithMaxNumberOfLetters = filename.getName();
            }
        }
        String result = "The file with maximum '" + letterToFind + "' letter occurrence in " + args[0] + " is: "
                + fileWithMaxNumberOfLetters + "\n";
        System.out.print(result);
        Files.writeString(fileToWrite, result);
    }
    private void findTopFiveSizeFilesInDirectory(String[] args) throws IOException {
        ArrayList<File> files = new ArrayList<>();
        ArrayList<File> listOfFiles = getFileCollectionFromDir(new File(args[0]), files);

        listOfFiles.sort((file1, file2) -> Long.compare(file2.length(), file1.length()));
        System.out.println("Top 5 file by size in " + args[0] + " :");
        Files.writeString(fileToWrite,"Top 5 file by size in " + args[0] + " :\n", StandardOpenOption.APPEND);
        listOfFiles.stream().limit(5).forEach(file -> {
            try {
                Files.writeString(fileToWrite, file.length() + " " + file.getName() + "\n", StandardOpenOption.APPEND);
                System.out.print(file.length() + " " + file.getName() + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    private void countAverageFileSizeInDirectory(String[] args) throws IOException {

        long sumSizeOfFiles = 0;

        ArrayList<File> files = new ArrayList<>();
        ArrayList<File> listOfFiles = getFileCollectionFromDir(new File(args[0]), files);

        //получаем общий размер файлов в каталоге
        for (File file : listOfFiles
        ) {
            sumSizeOfFiles += file.length();
        }

        String result = "Average file size in " + args[0] + " is: " +
                sumSizeOfFiles / listOfFiles.size() + "\n";
        System.out.print(result);
        Files.writeString(fileToWrite,result, StandardOpenOption.APPEND);
    }

    private void countFilesAndDirsByFirstLetter(String[] args) throws IOException {

        ArrayList<File> files = new ArrayList<>();
        ArrayList<File> dirs = new ArrayList<>();
        ArrayList<File> listOfFiles = getFileCollectionFromDir(new File(args[0]), files);
        ArrayList<File> listOfDirs = getDirCollectionFromDir(new File(args[0]), dirs);

        Map<Character, ArrayList<Integer>> filesAndDirsByFirstLetter = new HashMap<>();


        for (char key = 'A'; key <= 'Z'; key++) {

            ArrayList<Integer> tempCountOfFilesAndDirs = new ArrayList<>();
            tempCountOfFilesAndDirs.add(0);
            tempCountOfFilesAndDirs.add(0);

            int countFilesWithFirstLetter = 0;
            for (File file : listOfFiles
            ) {
                if (file.getName().toUpperCase().charAt(0) == key) {
                    countFilesWithFirstLetter++;
                }
            }

            int countDirsWithFirstLetter = 0;
            for (File file : listOfDirs
            ) {
                if (file.getName().toUpperCase().charAt(0) == key) {
                    countDirsWithFirstLetter++;
                }
            }

            tempCountOfFilesAndDirs.set(0, countFilesWithFirstLetter);
            tempCountOfFilesAndDirs.set(1, countDirsWithFirstLetter);

            filesAndDirsByFirstLetter.put(key, tempCountOfFilesAndDirs);
        }

        String result = "Number of Files and Dirs by the first letter:\n" + filesAndDirsByFirstLetter + "\n";
        System.out.print(result);
        Files.writeString(fileToWrite, result, StandardOpenOption.APPEND);
    }

    private void checkInput(String[] args) {

        int functionNumber = Integer.parseInt(args[1]);

        try {
            if (args.length != 2) {
                throw new Exception("Wrong number of arguments");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            if (functionNumber < 1 || functionNumber > 4) {
                throw new Exception("Wrong function number");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            if (!Files.exists(Paths.get(args[0]))) {
                throw new Exception("Wrong path");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            if (!Files.isDirectory(Paths.get(args[0]))) {
                throw new Exception("Not a directory");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
