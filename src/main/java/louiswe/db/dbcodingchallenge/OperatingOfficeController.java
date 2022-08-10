package louiswe.db.dbcodingchallenge;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@RestController
public class OperatingOfficeController {
    private static CsvReader reader;

    private static final Logger LOG = LoggerFactory.getLogger(OperatingOfficeController.class);

    @RequestMapping(value = "/betriebsstelle/{shortcut}", method = RequestMethod.GET, produces = "application/json")
    ResponseEntity<Object> getOperationOffice(@PathVariable String shortcut) {
        List<String> record = reader.getRecordForIdentifier(shortcut.toUpperCase());

        if (record == null) {
            HashMap<String, Object> response = new HashMap<>();
            response.put("message", "not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        HashMap<String, String> responseBody = new HashMap<>();
        responseBody.put("Name", record.get(2));
        responseBody.put("Kurzname", record.get(3));
        responseBody.put("Typ", record.get(4));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    // Read CSV file only once on startup so the requests will be as fast as possible
    @PostConstruct
    public void init() throws IOException {
        CsvReader reader = new  CsvReader("data.csv");
        reader.readFile(1, true);
        OperatingOfficeController.reader = reader;
        LOG.info("Initialized OperatingOfficeController");
    }
}
