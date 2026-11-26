package com.screener;

import java.util.List;

public class Main {
    private static final String DEFAULT_INPUT_PATH = "data/sample_stocks.csv";
    private static final String DEFAULT_OUTPUT_PATH = "output/screened_stocks.csv";

    public static void main(String[] args) {
        CsvReader csvReader = new CsvReader();
        RatioCalculator calculator = new RatioCalculator();
        StockScreener screener = new StockScreener();
        CsvExporter exporter = new CsvExporter();

        try {
            List<Stock> stocks = csvReader.readStocks(DEFAULT_INPUT_PATH);
            calculator.calculateAll(stocks);

            List<Stock> screened = screener.screen(stocks, 2.0, 2.0, 1.0, 5.0);
            exporter.export(screened, DEFAULT_OUTPUT_PATH);

            System.out.println("Screened " + screened.size() + " matching stocks. Report saved to " + DEFAULT_OUTPUT_PATH);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
