package com.screener;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class CsvReader {

    /**
     * Reads all rows from a CSV file as raw string arrays.
     * Uses OpenCSV instead of String.split(",") so commas inside quoted
     * company names (e.g., "Apex Manufacturing, Inc.") do not break columns.
     */
    public List<String[]> readRawRows(String filePath) throws IOException, CsvException {
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            return reader.readAll();
        }
    }
}
