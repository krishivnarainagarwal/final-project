package com.screener;

import java.util.List;

public class Main {
    private static final String DEFAULT_INPUT_PATH = "data/sample_stocks.csv";

    public static void main(String[] args) {
        CsvReader csvReader = new CsvReader();

        try {
            List<Stock> stocks = csvReader.readStocks(DEFAULT_INPUT_PATH);
            System.out.println("Successfully loaded " + stocks.size() + " valid stocks:");
            for (Stock stock : stocks) {
                System.out.println("  " + stock);
            }
        } catch (Exception e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
        }
    }
}
