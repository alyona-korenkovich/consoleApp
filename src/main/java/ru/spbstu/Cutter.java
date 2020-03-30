package ru.spbstu;

import java.io.*;
import java.util.ArrayList;

public class Cutter {
    Integer startOfTheRange;
    Integer endOfTheRange;

    public Cutter(int startOfTheRange, int endOfTheRange){
        this.startOfTheRange = startOfTheRange;
        this.endOfTheRange = endOfTheRange;
    }

    public String cutByChars(String str) {
        StringBuilder stringBuilder = new StringBuilder();
        int x = Math.min(endOfTheRange, str.length() - 1);
        for (int j = startOfTheRange; j <= x; j++) {
            stringBuilder.append(str.charAt(j));
        }
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }

    public String cutByWords(String str) {
        StringBuilder stringBuilder = new StringBuilder();
        String[] arr = str.split(" ");
        int x = Math.min(endOfTheRange, arr.length - 1);
        for (int j = startOfTheRange; j <= x; j++) {
            stringBuilder.append(arr[j]).append(" ");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }

    public String transformIn(ArrayList<String> strings, boolean flag) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String i: strings) {
            if (flag) { stringBuilder.append(cutByChars(i)); }
            else {stringBuilder.append(cutByWords(i)); }
            }
        return stringBuilder.toString();
    }

    public String transformInput(File input, boolean flag) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        String curStr;
        BufferedReader reader;
        try {
            reader = new BufferedReader((new FileReader(input)));
            while ((curStr = reader.readLine()) != null) {
                if (flag) { stringBuilder.append(cutByChars(curStr)); }
                else { stringBuilder.append(cutByWords(curStr)); }
            }
            reader.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return stringBuilder.toString();
    }
}