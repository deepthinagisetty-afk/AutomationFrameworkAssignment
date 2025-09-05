package utils;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CSVUtils {

    /**
     * Reads a CSV file and identifies duplicate rows, ignoring the header row.
     *
     * @param filePath the path to the CSV file
     * @return a list of duplicate rows found in the CSV file
     */
    public static List<String> findDuplicatesInCSV(String filePath) {
        List<String> duplicates = new ArrayList<>();
        Set<String> uniqueLines = new HashSet<>();
        Set<String> duplicateSet = new HashSet<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isHeader = true;

            while ((line = reader.readLine()) != null) {
                if (isHeader) { // skip header row
                    isHeader = false;
                    continue;
                }
                if (!uniqueLines.add(line)) {
                    duplicateSet.add(line);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        duplicates.addAll(duplicateSet);
        return duplicates;
    }


    public static void writeCSV(String filePath, List<String> header, List<List<String>> rows) {

        File file = new File(filePath);
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }
        try (FileWriter writer = new FileWriter(filePath)) {

            // Write header
            if (header != null && !header.isEmpty()) {
                writer.append(String.join(",", header));
                writer.append("\n");
            }

            // Write rows
            for (List<String> row : rows) {
                writer.append(String.join(",", row));
                writer.append("\n");
            }

            System.out.println("✅ CSV file generated at: " + filePath);
            if (file.exists()) {
                ExtentCucumberAdapter.getCurrentStep()
                        .log(Status.INFO,
                                "All Links under footer are captured: <a href='" + file.getAbsolutePath() + "' target='_blank'>Open File</a>");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
