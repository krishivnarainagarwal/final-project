# Java Financial Ratio Screener

A lightweight Java CLI application that ingests raw stock market CSV files, cleans malformed rows, calculates four core financial ratios, filters and sorts companies against user-defined investment thresholds, and exports clean reports.

## Problem Statement
Retail investors and analysts often rely on messy CSV exports where missing values, malformed numbers, or divide-by-zero edge cases produce misleading financial ratios. This tool automates data cleaning, accurate ratio computation, and repeatable screening from the command line.

## Prerequisites
* **Java 17+** (`java -version`)
* **Apache Maven 3.8+** (`mvn -version`)

## Quickstart (Fresh Terminal)
~~~bash
git clone https://github.com/krishivnarainagarwal/final-project.git
cd final-project
mvn compile exec:java
~~~
When prompted, press **Enter** at each prompt to run with the bundled `data/sample_stocks.csv` and default screening thresholds, or enter your own values.

You can also pass a custom CSV path directly:
~~~bash
mvn compile exec:java -Dexec.args="data/sample_stocks.csv"
~~~

## Calculated Ratios
1. **Price-to-Book (P/B)** = `Market Cap / Book Value`
2. **Price-to-Sales (P/S)** = `Market Cap / Revenue`
3. **Debt-to-Equity (D/E)** = `Total Debt / Book Value`
4. **Net Profit Margin (%)** = `(Net Income / Revenue) * 100`

*Note: Companies with zero or negative denominators (e.g., `Book Value <= 0`) are assigned `NaN` rather than `0.0` so distressed firms never falsely pass low-multiple value screens.*

## Example Output
Running with the default thresholds (`P/B <= 2.0`, `P/S <= 2.0`, `D/E <= 1.0`, `Margin >= 5.0%`) produces `output/screened_stocks.csv`:
~~~csv
"ticker","company_name","market_cap_m","price_to_book","price_to_sales","debt_to_equity","net_profit_margin_pct"
"INDU","Harbor Steelworks","520.00","1.08","0.58","0.44","7.30"
"BANK","First Regional Bancorp","510.00","1.11","1.65","0.39","18.71"
"MCRB","Midwest Bearings Corp","180.00","1.20","0.75","0.30","9.17"
~~~

## Changes from Original Ideation
* **Dropped Inngest:** Inngest does not provide an official Java SDK and requires external cloud keys, which would break offline reproducibility on a fresh terminal. Replaced with a zero-dependency local Maven CLI runner.

## Roadmap
### MVP (Do)
- [x] Ingest raw CSV files using OpenCSV
- [x] Clean malformed/missing rows without crashing
- [x] Compute P/B, P/S, D/E, and Net Profit Margin with divide-by-zero protection
- [x] Filter by user-defined CLI thresholds and sort by P/B
- [x] Export screened results to `output/screened_stocks.csv`

### Schedule Features
- [ ] Export interactive TradingView Lightweight Charts HTML report (`output/report.html`)
