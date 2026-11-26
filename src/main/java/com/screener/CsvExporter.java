package com.screener;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CsvExporter {

    /**
     * Writes screened stocks to a CSV report using OpenCSV so company names
     * containing commas are properly quoted in the output file.
     */
    public void export(List<Stock> stocks, String outputPath) throws IOException {
        File outFile = new File(outputPath);
        File parentDir = outFile.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }

        try (CSVWriter writer = new CSVWriter(new FileWriter(outFile))) {
            writer.writeNext(new String[]{
                "ticker", "company_name", "market_cap_m", "price_to_book",
                "price_to_sales", "debt_to_equity", "net_profit_margin_pct"
            });

            for (Stock s : stocks) {
                writer.writeNext(new String[]{
                    s.getTicker(),
                    s.getCompanyName(),
                    String.format("%.2f", s.getMarketCap()),
                    String.format("%.2f", s.getPriceToBook()),
                    String.format("%.2f", s.getPriceToSales()),
                    String.format("%.2f", s.getDebtToEquity()),
                    String.format("%.2f", s.getNetProfitMargin())
                });
            }
        }
    }
}
