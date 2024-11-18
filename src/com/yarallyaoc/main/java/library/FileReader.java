package com.yarallyaoc.main.java.library;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileReader {
    public static List<String> readFile(String fileName) {
        try {
            File myObj = new File("src/com/yarallyaoc/main/resources/" + fileName);
            Scanner myReader = new Scanner(myObj);
            List<String> lines = new ArrayList<String>();
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();
                lines.add(line);
            }
            myReader.close();
            return lines;
        } catch (FileNotFoundException e) {
            System.out.println("FILE NOT FOUND");
        }
        return null;
    }
}
