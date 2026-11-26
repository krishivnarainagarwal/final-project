package com.screener;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ChartExporter {

    /**
     * Generates a standalone HTML report using TradingView Lightweight Charts via CDN.
     * Generating a static HTML file avoids requiring Node.js or a local web server,
     * so anyone can open output/report.html directly in a browser.
     */
    public void exportHtmlReport(List<Stock> stocks, String outputPath) throws IOException {
        File outFile = new File(outputPath);
        File parentDir = outFile.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }

        StringBuilder dataArray = new StringBuilder();
        StringBuilder tickerMap = new StringBuilder();
        StringBuilder tableRows = new StringBuilder();

        for (int i = 0; i < stocks.size(); i++) {
            Stock s = stocks.get(i);
            int index = i + 1;
            dataArray.append(String.format("{ time: %d, value: %.2f },%n", index, s.getPriceToBook()));
            tickerMap.append(String.format("%d: '%s',%n", index, escapeJs(s.getTicker())));
            tableRows.append(String.format(
                    "<tr><td>%s</td><td>%s</td><td>%.2f</td><td>%.2f</td><td>%.2f</td><td>%.1f%%</td></tr>%n",
                    escapeHtml(s.getTicker()), escapeHtml(s.getCompanyName()),
                    s.getPriceToBook(), s.getPriceToSales(), s.getDebtToEquity(), s.getNetProfitMargin()));
        }

        String html = """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                  <meta charset="UTF-8" />
                  <title>Screened Stocks Report</title>
                  <script src="https://unpkg.com/lightweight-charts@4.2.0/dist/lightweight-charts.standalone.production.js"></script>
                  <style>
                    body { font-family: -apple-system, BlinkMacSystemFont, sans-serif; max-width: 900px; margin: 32px auto; padding: 0 16px; color: #1f2937; }
                    #chart { width: 100%; height: 360px; border: 1px solid #e5e7eb; border-radius: 8px; }
                    table { width: 100%; border-collapse: collapse; margin-top: 24px; }
                    th, td { border-bottom: 1px solid #e5e7eb; padding: 10px 12px; text-align: left; }
                    th { background: #f9fafb; }
                  </style>
                </head>
                <body>
                  <h1>Screened Stocks — Price-to-Book (P/B)</h1>
                  <div id="chart"></div>
                  <table>
                    <thead>
                      <tr><th>Ticker</th><th>Company</th><th>P/B</th><th>P/S</th><th>D/E</th><th>Margin</th></tr>
                    </thead>
                    <tbody>
                """ + tableRows + """
                    </tbody>
                  </table>
                  <script>
                    const tickers = {
                """ + tickerMap + """
                    };
                    const chart = LightweightCharts.createChart(document.getElementById('chart'), {
                      layout: { background: { color: '#ffffff' }, textColor: '#1f2937' },
                      timeScale: {
                        tickMarkFormatter: (time) => tickers[time] || ''
                      }
                    });
                    const series = chart.addHistogramSeries({ color: '#2563eb' });
                    series.setData([
                """ + dataArray + """
                    ]);
                    chart.timeScale().fitContent();
                  </script>
                </body>
                </html>
                """;

        try (FileWriter writer = new FileWriter(outFile)) {
            writer.write(html);
        }
    }

    private String escapeJs(String value) {
        return value.replace("'", "\\'");
    }

    private String escapeHtml(String value) {
        return value.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }
}
