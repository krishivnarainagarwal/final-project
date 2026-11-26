package com.screener;

import java.util.Arrays;
import java.util.List;

public class Main {
    private static final String DEFAULT_INPUT_PATH = "data/sample_stocks.csv";

    public static void main(String[] args) {
        CsvReader csvReader = new CsvReader();

        try {
            List<String[]> rows = csvReader.readRawRows(DEFAULT_INPUT_PATH);
            System.out.println("Loaded " + rows.size() + " rows from " + DEFAULT_INPUT_PATH + ":");
            for (String[] row : rows) {
                System.out.println(Arrays.toString(row));
            }
        } catch (Exception e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
        }
    }
}
