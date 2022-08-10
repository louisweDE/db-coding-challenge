package louiswe.db.dbcodingchallenge;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class CsvReader {
    private BufferedReader reader;
    private List<String> headers;
    private HashMap<Object, List<String>> records;


    public CsvReader(String fileName) throws IOException {
        this.reader = Files.newBufferedReader(Path.of(fileName), StandardCharsets.UTF_8);
    }

    public void readFile(int identifierIndex) throws IOException {
        readFile(identifierIndex, true);
    }

    public void readFile(int identifierIndex, boolean hasHeader) throws IOException {
        String[] line;
        if (hasHeader && headers == null) {
            headers = new ArrayList<>();
            headers.addAll(Arrays.asList(this.readLine()));
        }
        this.records = new HashMap<>();
        while (true) {
            if ((line = this.readLine()) == null) {
                break;
            }
            records.put(line[identifierIndex], Arrays.asList(line));
        }
    }

    public int getIndexForKey(Object key) {
        return headers.indexOf(key);
    }

    public List<String> getHeaders() {
        return headers;
    }

    public List<String> getRecordForIdentifier(Object identifier) {
        return records.get(identifier);
    }

    private String[] readLine() throws IOException {
        String line = this.reader.readLine();
        return line == null ? null : line.split(";");
    }
}
