package ru.spbstu;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.*;
import java.util.regex.Pattern;

public class cut {
    @Option(name = "-c", metaVar = "IndentC", usage = "Set indentation in characters")
    private boolean characterIndentation;

    @Option(name = "-w", metaVar = "IndentW", usage = "Set indentation in words", forbids = {"-c"})
    private boolean wordIndentation;

    @Option(name = "-o", metaVar = "OutputFileName", usage = "Set output file name")
    private File outputFileName;

    @Option(name = "-r", required = true, metaVar = "Range", usage = "Set range (num-num, -num or num-)")
    private String range;

    @Argument(metaVar = "InputFileName", usage = "Set input file name")
    private File inputFileName;

    public static void main(String[] args) {
        new cut().launch(args);
    }

    int startOfRange = 0;
    int endOfRange = 0;

    public boolean isCorrect(String range) {
        if (!Pattern.matches("^(\\d*)-(\\d*)$", range) | Pattern.matches("^(-*)$", range)) {
            System.err.println("Use correct format of range: number-number, -number or number-.");
            return false;
        } else {
            String[] strings = range.split("-");
            startOfRange = (strings[0].equals("")) ? 0 : Integer.parseInt(strings[0]);
            endOfRange = (strings.length == 1) ? Integer.MAX_VALUE : Integer.parseInt(strings[1]);
            if (startOfRange > endOfRange) {
                System.err.println("The start of the range has to be less than the end.");
                return false;
            }
            return true;
        }
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

        if (!(characterIndentation | wordIndentation)) {
            System.err.println("Use either -c or -w option");
        }
        if (!isCorrect(range)) {
            return;
        }
        Cutter cutter = new Cutter(startOfRange, endOfRange);

        try {
            BufferedReader reader;
            BufferedWriter writer;
            if (outputFileName == null) {
                writer = new BufferedWriter(new OutputStreamWriter(System.out));
            } else {
                writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFileName)));
            }
            if (inputFileName == null) {
                reader = new BufferedReader(new InputStreamReader(System.in));
            } else {
                reader = new BufferedReader(new FileReader(inputFileName));
            }
            cutter.transformInput(reader, characterIndentation, writer);
            writer.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}

