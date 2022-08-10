package louiswe.db.dbcodingchallenge;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class CsvReaderTests {

    @Test
    public void testThrowsFileNotFoundError() {
        Assertions.assertThrows(NoSuchFileException.class, () -> {
            new CsvReader("testFileNotFound");
        });
    }

    @Test
    public void testAssertCanReadFile() {
        List<String> headers = Arrays.asList("PLC",
                                             "RL100-Code",
                                             "RL100-Langname",
                                             "RL100-Kurzname",
                                             "Typ Kurz",
                                             "Typ Lang",
                                             "Betriebszustand",
                                             "Datum ab",
                                             "Datum bis",
                                             "Niederlassung",
                                             "Regionalbereich",
                                             "Letzte Änderung");
        CsvReader reader;
        try {
            reader = new CsvReader("data.csv");
            reader.readFile(1, true);
            Assertions.assertEquals(headers, reader.getHeaders());
            Assertions.assertEquals("Losheimergraben", reader.getRecordForIdentifier("XBLG").get(2));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
