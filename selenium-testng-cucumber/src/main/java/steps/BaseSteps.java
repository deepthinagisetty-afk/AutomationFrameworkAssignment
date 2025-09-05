package steps;

import org.json.simple.JSONObject;
import utils.JsonDataReader;

public class BaseSteps {
    protected JSONObject testData;

    public BaseSteps() {
        String dataFile = System.getProperty("testdata.json", "data.json");
        this.testData = JsonDataReader.getTestData(dataFile);
    }
}
