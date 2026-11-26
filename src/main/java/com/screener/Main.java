package com.screener;

import java.io.File;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final String DEFAULT_INPUT_PATH = "data/sample_stocks.csv";
    private static final String DEFAULT_CSV_OUTPUT = "output/screened_stocks.csv";
    private static final String DEFAULT_HTML_OUTPUT = "output/report.html";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String inputPath = args.length > 0
                ? args[0]
                : promptString(scanner, "Input CSV path", DEFAULT_INPUT_PATH);

        File inputFile = new File(inputPath);
        if (!inputFile.exists() || !inputFile.isFile()) {
            System.err.println("Error: Input file not found at '" + inputPath + "'.");
            return;
        }

        double maxPB = promptDouble(scanner, "Max Price-to-Book (P/B)", 2.0);
        double maxPS = promptDouble(scanner, "Max Price-to-Sales (P/S)", 2.0);
        double maxDE = promptDouble(scanner, "Max Debt-to-Equity (D/E)", 1.0);
        double minMargin = promptDouble(scanner, "Min Net Profit Margin (%)", 5.0);

        CsvReader csvReader = new CsvReader();
        RatioCalculator calculator = new RatioCalculator();
        StockScreener screener = new StockScreener();
        CsvExporter csvExporter = new CsvExporter();
        ChartExporter chartExporter = new ChartExporter();

        try {
            List<Stock> stocks = csvReader.readStocks(inputPath);
            if (stocks.isEmpty()) {
                System.err.println("Error: No valid stock records found in '" + inputPath + "'.");
                return;
            }

            calculator.calculateAll(stocks);
            List<Stock> screened = screener.screen(stocks, maxPB, maxPS, maxDE, minMargin);

            printSummary(screened);
            csvExporter.export(screened, DEFAULT_CSV_OUTPUT);
            chartExporter.exportHtmlReport(screened, DEFAULT_HTML_OUTPUT);

            System.out.println("\nCSV report saved to:  " + DEFAULT_CSV_OUTPUT);
            System.out.println("HTML chart saved to:  " + DEFAULT_HTML_OUTPUT);
        } catch (Exception e) {
            System.err.println("Error processing stocks: " + e.getMessage());
        }
    }

    private static String promptString(Scanner scanner, String label, String defaultValue) {
        System.out.printf("%s [%s]: ", label, defaultValue);
        if (!scanner.hasNextLine()) {
            return defaultValue;
        }
        String input = scanner.nextLine().trim();
        return input.isEmpty() ? defaultValue : input;
    }

    private static double promptDouble(Scanner scanner, String label, double defaultValue) {
        while (true) {
            System.out.printf("%s [%.2f]: ", label, defaultValue);
            if (!scanner.hasNextLine()) {
                return defaultValue;
            }
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                return defaultValue;
            }
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("  Invalid number. Please enter a numeric value or press Enter for default.");
            }
        }
    }

    private static void printSummary(List<Stock> screened) {
        System.out.println("\n=== Screened Results (" + screened.size() + " matches, sorted by P/B) ===");
        if (screened.isEmpty()) {
            System.out.println("No companies matched your criteria.");
            return;
        }
        System.out.printf("%-6s | %-25s | %6s | %6s | %6s | %7s%n",
                "Ticker", "Company", "P/B", "P/S", "D/E", "Margin%");
        System.out.println("------------------------------------------------------------------------");
        for (Stock s : screened) {
            System.out.printf("%-6s | %-25s | %6.2f | %6.2f | %6.2f | %6.1f%%%n",
                    s.getTicker(), s.getCompanyName(), s.getPriceToBook(),
                    s.getPriceToSales(), s.getDebtToEquity(), s.getNetProfitMargin());
        }
    }
}
