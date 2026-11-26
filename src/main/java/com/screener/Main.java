package com.screener;

import java.util.List;

public class Main {
    private static final String DEFAULT_INPUT_PATH = "data/sample_stocks.csv";

    public static void main(String[] args) {
        CsvReader csvReader = new CsvReader();
        RatioCalculator calculator = new RatioCalculator();

        try {
            List<Stock> stocks = csvReader.readStocks(DEFAULT_INPUT_PATH);
            calculator.calculateAll(stocks);

            System.out.println("Calculated ratios for " + stocks.size() + " stocks:");
            for (Stock s : stocks) {
                System.out.printf("  %-5s | P/B: %5.2f | P/S: %5.2f | D/E: %5.2f | Margin: %5.1f%%%n",
                        s.getTicker(), s.getPriceToBook(), s.getPriceToSales(),
                        s.getDebtToEquity(), s.getNetProfitMargin());
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
