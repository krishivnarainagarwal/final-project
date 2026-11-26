package com.screener;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {
    private static final int EXPECTED_COLUMNS = 7;

    /**
     * Parses a financial CSV into Stock objects, skipping malformed rows.
     * Uses OpenCSV instead of String.split(",") so commas inside quoted
     * company names (e.g., "Apex Manufacturing, Inc.") do not break columns.
     */
    public List<Stock> readStocks(String filePath) throws IOException, CsvException {
        List<Stock> stocks = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            List<String[]> rows = reader.readAll();

            // Start at index 1 to skip the CSV header row.
            for (int i = 1; i < rows.size(); i++) {
                String[] row = rows.get(i);
                int lineNumber = i + 1;

                if (row.length < EXPECTED_COLUMNS) {
                    System.err.println("Skipping line " + lineNumber + ": expected "
                            + EXPECTED_COLUMNS + " columns, found " + row.length);
                    continue;
                }

                try {
                    String ticker = row[0].trim();
                    String companyName = row[1].trim();
                    if (ticker.isEmpty() || companyName.isEmpty()) {
                        System.err.println("Skipping line " + lineNumber + ": missing ticker or company name");
                        continue;
                    }

                    double marketCap = parseRequiredDouble(row[2]);
                    double bookValue = parseRequiredDouble(row[3]);
                    double revenue = parseRequiredDouble(row[4]);
                    double netIncome = parseRequiredDouble(row[5]);
                    double totalDebt = parseRequiredDouble(row[6]);

                    stocks.add(new Stock(ticker, companyName, marketCap, bookValue, revenue, netIncome, totalDebt));
                } catch (NumberFormatException e) {
                    // Log and skip corrupted rows (e.g., "N/A" or blank cells) rather than
                    // aborting the entire batch, since real-world financial exports are messy.
                    System.err.println("Skipping line " + lineNumber + " (" + row[0].trim()
                            + "): invalid numeric value.");
                }
            }
        }

        return stocks;
    }

    private double parseRequiredDouble(String rawValue) {
        String trimmed = rawValue == null ? "" : rawValue.trim();
        if (trimmed.isEmpty() || trimmed.equalsIgnoreCase("N/A")) {
            throw new NumberFormatException("Missing or N/A value");
        }
        return Double.parseDouble(trimmed);
    }
}
