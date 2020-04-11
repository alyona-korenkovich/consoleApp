package ru.spbstu;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

class cutTest {
    private String ls = System.lineSeparator();
    private Path inputFile = Paths.get("src/main/resources/inputSE.txt");
    private Path outputFile = Paths.get("src/main/resources/outputSE.txt");
    private ByteArrayOutputStream out = new ByteArrayOutputStream();
    private PrintStream output = new PrintStream(out);

    @Test
    void cutFile() throws IOException {
        cut.main(new String[]{"-w", "-o", outputFile.toString(), "-r", "-2", inputFile.toString()});
        BufferedReader reader = new BufferedReader(new FileReader(outputFile.toString()));
        String str = reader.readLine();
        assertEquals("Шаганэ ты моя,", str);
        str = reader.readLine();
        assertEquals("Потому, что я", str);
        str = reader.readLine();
        assertEquals("Я готов рассказать", str);
        str = reader.readLine();
        assertEquals("Про волнистую рожь", str);
        str = reader.readLine();
        assertEquals("Шаганэ ты моя,", str);
        reader.close();
    }

    @Test
    void cutConsole() {
        System.setOut(output);
        ByteArrayInputStream in = new ByteArrayInputStream(("Я сразу смазал карту будня," + ls +
                "плеснувши краску из стакана;" + ls + "я показал на блюде студня" + ls +
                "косые скулы океана.").getBytes());
        System.setIn(in);
        cut.main(new String[]{"-c", "-r", "1-4"});
        assertEquals(" сра" + ls + "лесн" + ls + " пок" + ls + "осые" + ls, out.toString());
    }

    @Test
    void wrongRange() {
        System.setErr(output);
        cut.main(new String[]{"-c", "-r", "-"});
        assertEquals("Use correct format of range: number-number, -number or number-." + ls, out.toString());
    }

    @Test
    void wrongRange2() {
        System.setErr(output);
        cut.main(new String[]{"-w", "-r", "6-2"});
        assertEquals("The start of the range has to be less than the end." + ls, out.toString());
    }
}