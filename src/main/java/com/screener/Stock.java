package com.screener;

public class Stock {
    private final String ticker;
    private final String companyName;
    private final double marketCap;
    private final double bookValue;
    private final double revenue;
    private final double netIncome;
    private final double totalDebt;

    // Calculated ratios default to NaN until RatioCalculator runs, so uncomputed
    // stocks can never accidentally pass numeric screening thresholds.
    private double priceToBook = Double.NaN;
    private double priceToSales = Double.NaN;
    private double debtToEquity = Double.NaN;
    private double netProfitMargin = Double.NaN;

    public Stock(String ticker, String companyName, double marketCap,
                 double bookValue, double revenue, double netIncome, double totalDebt) {
        this.ticker = ticker;
        this.companyName = companyName;
        this.marketCap = marketCap;
        this.bookValue = bookValue;
        this.revenue = revenue;
        this.netIncome = netIncome;
        this.totalDebt = totalDebt;
    }

    public String getTicker() { return ticker; }
    public String getCompanyName() { return companyName; }
    public double getMarketCap() { return marketCap; }
    public double getBookValue() { return bookValue; }
    public double getRevenue() { return revenue; }
    public double getNetIncome() { return netIncome; }
    public double getTotalDebt() { return totalDebt; }

    public double getPriceToBook() { return priceToBook; }
    public void setPriceToBook(double priceToBook) { this.priceToBook = priceToBook; }

    public double getPriceToSales() { return priceToSales; }
    public void setPriceToSales(double priceToSales) { this.priceToSales = priceToSales; }

    public double getDebtToEquity() { return debtToEquity; }
    public void setDebtToEquity(double debtToEquity) { this.debtToEquity = debtToEquity; }

    public double getNetProfitMargin() { return netProfitMargin; }
    public void setNetProfitMargin(double netProfitMargin) { this.netProfitMargin = netProfitMargin; }

    @Override
    public String toString() {
        return String.format("%s (%s) | MktCap: %.1fM | Rev: %.1fM",
                ticker, companyName, marketCap, revenue);
    }
}
