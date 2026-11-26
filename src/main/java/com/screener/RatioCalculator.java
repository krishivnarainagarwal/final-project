package com.screener;

import java.util.List;

public class RatioCalculator {

    /**
     * Calculates core valuation and leverage ratios for each stock.
     * Non-positive denominators return Double.NaN instead of 0.0 or Infinity
     * so distressed companies (e.g., zero book value) cannot falsely pass
     * "less than X" value screens.
     */
    public void calculateAll(List<Stock> stocks) {
        for (Stock stock : stocks) {
            stock.setPriceToBook(safeDivide(stock.getMarketCap(), stock.getBookValue()));
            stock.setPriceToSales(safeDivide(stock.getMarketCap(), stock.getRevenue()));
            stock.setDebtToEquity(safeDivide(stock.getTotalDebt(), stock.getBookValue()));

            double marginRatio = safeDivide(stock.getNetIncome(), stock.getRevenue());
            stock.setNetProfitMargin(Double.isNaN(marginRatio) ? Double.NaN : marginRatio * 100.0);
        }
    }

    private double safeDivide(double numerator, double denominator) {
        if (denominator <= 0.0) {
            return Double.NaN;
        }
        return numerator / denominator;
    }
}
