# Microcap Equity & CSV Valuation Parser

## Project Goal
Analyzing microcap equity valuations requires manually digging through raw financial exports and spreadsheets to identify anomalies and calculate custom ratios. This Java tool parses raw financial CSVs, automatically calculates specific valuation frameworks, and flags targets that meet strict numerical parameters, automating the tedious data-cleaning phase of financial research.

## MVP Scope (Do Quadrant)
* Parse large raw CSV files containing financial and stock market data.
* Calculate 3-4 core financial ratios (e.g., Price/Book, custom revenue multiples).
* Filter data based on built-in investment parameters and export a new, sorted CSV report formatted for TradingView.

## Future Plans (Schedule Quadrant - V2.0+)
* Build an interactive GUI for adjusting custom metrics without editing code.
* Integrate a live market data API to automatically pull daily updates.
* Implement historical backtesting functionality to simulate 5-year performance of filtered targets.

## Tech Stack
* **Language:** Java
* **Libraries:** OpenCSV (parsing), TradingView (visualization), Inngest (job scheduling), java.io (file handling)
