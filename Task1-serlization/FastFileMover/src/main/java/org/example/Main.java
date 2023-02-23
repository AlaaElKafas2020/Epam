package org.example;
import java.io.File;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Write the path of the file to read (for example enter 'C:\\Users\\AlaaEssam\\Desktop\\output.tx'");
        String pathRead = scanner.nextLine();
        File pathReadFile = new File(pathRead);
        System.out.print("Write the path of the directory to write (for example enter 'C:\\'");
        String pathWrite = scanner.nextLine();
        File pathWriteFile = new File(pathWrite);
        FastFileMover.movingDirectory(pathReadFile, pathWriteFile);
//        FastFileMover.movingDirectoryUsingBuffer(pathReadFile, pathWriteFile);
//        FastFileMover.movingDerectoryFileChannel(pathReadFile, pathWriteFile);
//        FastFileMover.movingDerectoryNIO2(pathReadFile, pathWriteFile);
        scanner.close();
    }
}