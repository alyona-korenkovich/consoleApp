package ru.spbstu;

import java.io.*;

public class Cutter {
    Integer startOfTheRange;
    Integer endOfTheRange;

    public Cutter(int startOfTheRange, int endOfTheRange) {
        this.startOfTheRange = startOfTheRange;
        this.endOfTheRange = endOfTheRange;
    }

    public String cutByChars(String str) {
        StringBuilder stringBuilder = new StringBuilder();
        int x = Math.min(endOfTheRange, str.length() - 1);
        for (int j = startOfTheRange; j <= x; j++) {
            stringBuilder.append(str.charAt(j));
        }
        stringBuilder.append(System.lineSeparator());
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
        stringBuilder.append(System.lineSeparator());
        return stringBuilder.toString();
    }

    public void transformInput(BufferedReader reader, Cut.Indentation ind, BufferedWriter writer) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        String curStr;
        try {
            while ((curStr = reader.readLine()) != null) {
                switch (ind) {
                    case CHARS: stringBuilder.append(cutByChars(curStr)); break;
                    case WORDS: stringBuilder.append(cutByWords(curStr)); break;
                    }
                }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        writer.write(stringBuilder.toString());
    }
}