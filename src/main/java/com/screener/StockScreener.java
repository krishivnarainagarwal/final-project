package com.screener;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class StockScreener {

    /**
     * Filters stocks against user-defined upper/lower bounds and sorts matches
     * by lowest Price-to-Book ratio first to surface the deepest value candidates.
     */
    public List<Stock> screen(List<Stock> stocks, double maxPB, double maxPS,
                              double maxDE, double minMargin) {
        List<Stock> matches = new ArrayList<>();

        for (Stock s : stocks) {
            if (hasInvalidRatios(s)) {
                continue;
            }
            if (s.getPriceToBook() <= maxPB
                    && s.getPriceToSales() <= maxPS
                    && s.getDebtToEquity() <= maxDE
                    && s.getNetProfitMargin() >= minMargin) {
                matches.add(s);
            }
        }

        matches.sort(Comparator.comparingDouble(Stock::getPriceToBook));
        return matches;
    }

    private boolean hasInvalidRatios(Stock s) {
        return Double.isNaN(s.getPriceToBook())
                || Double.isNaN(s.getPriceToSales())
                || Double.isNaN(s.getDebtToEquity())
                || Double.isNaN(s.getNetProfitMargin());
    }
}
