package ru.spbstu;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class cut {
    @Option(name = "-c", metaVar = "IndentC", usage = "Set indentation in characters")
    private boolean characterIndentation;

    @Option(name = "-w", metaVar = "IndentW", usage = "Set indentation in words", forbids = {"-c"})
    private boolean wordIndentation;

    @Option(name = "-o", metaVar = "OutputFileName", usage = "Set output file name")
    private File outputFileName;

    @Argument(required = true, metaVar = "Range", usage = "Set range (num-num, -num or num-)")
    private String range;

    @Argument(metaVar = "InputFileName", index = 1, usage = "Set input file name")
    private File inputFileName;

    public static void main(String[] args) {
        new cut().launch(args);
    }

    int startOfRange = 0;
    int endOfRange = 0;
    public void isCorrect(String range) {
        if (!Pattern.matches("\\d*-\\d*", range)) {
            System.err.println("Use correct format of range: number-number, -number or number-.");
        }
        String[] strings = range.split("-");
        startOfRange = (strings[0].equals("")) ? 0 : Integer.parseInt(strings[0]);
        endOfRange = (strings.length == 1) ? Integer.MAX_VALUE : Integer.parseInt(strings[1]);
        if (startOfRange > endOfRange) { System.err.println("The start of the range has to be less than the end."); }
    }

    private void launch(String[] args) {
        CmdLineParser parser = new CmdLineParser(this);

        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("cut [-c|-w] [-o ofile] range [file]");
            parser.printUsage(System.err);
        }

        if (!(characterIndentation | wordIndentation)) { System.err.println("Use either -c or -w option");}
        isCorrect(range);
        Cutter cutter = new Cutter(startOfRange, endOfRange);

        try {
            String result;
            if (inputFileName == null) {
                Scanner input = new Scanner(System.in);
                System.out.println ("Enter your text. To finish, enter /exit on a new line");
                ArrayList<String> in = new ArrayList<>();
                String curStr = "";
                while (!curStr.equals("/exit")) {
                    curStr = input.nextLine();
                    in.add(curStr);
                }
                in.remove(in.size() - 1);
                result = cutter.transformIn(in, characterIndentation);
            } else {
                result = cutter.transformInput(inputFileName, characterIndentation);
            }

            if (outputFileName == null) {
                System.out.println(result);
            } else {
                try (FileWriter o = new FileWriter(outputFileName);
                    BufferedWriter writer = new BufferedWriter(o)) {
                    writer.write(result);
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}

