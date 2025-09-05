package utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class JsonDataReader {
    private static final String TESTDATA_PATH = "testdata/";

    public static JSONObject getTestData(String fileName) {
        try {
            ClassLoader loader = JsonDataReader.class.getClassLoader();
            InputStream input = loader.getResourceAsStream(TESTDATA_PATH + fileName);

            if (input == null) {
                throw new RuntimeException("Test data file not found: " + fileName);
            }

            JSONParser parser = new JSONParser();
            return (JSONObject) parser.parse(new InputStreamReader(input));
        } catch (Exception e) {
            throw new RuntimeException("Failed to read JSON file: " + fileName, e);
        }
    }
}
