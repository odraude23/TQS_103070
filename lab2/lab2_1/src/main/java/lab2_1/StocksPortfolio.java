package lab2_1;

import java.util.ArrayList;
import java.util.List;

class StocksPortfolio {
    private IStockmarketService stockmarket;
    private List<Stock> stocks;

    public StocksPortfolio(IStockmarketService stockmarket) {
        this.stockmarket = stockmarket;
        this.stocks = new ArrayList<>();
    }

    public void addStock(Stock stock) {
        stocks.add(stock);
    }

    public double totalValue() {
        double total = 0.0;
        for (Stock stock : stocks) {
            total += stock.getQuantity() * stockmarket.lookUpPrice(stock.getLabel());
        }
        return total;
    }

    /**
    * @param topN the number of most valuable stocks to return
    * @return a list with the topN most valuable stocks in the portfolio
    */
    public List<Stock> mostValuableStocks(int topN) {
        return stocks.stream()
                .sorted((s1, s2) -> Double.compare(
                        s2.getQuantity() * stockmarket.lookUpPrice(s2.getLabel()),
                        s1.getQuantity() * stockmarket.lookUpPrice(s1.getLabel())
                ))
                .limit(topN)
                .toList();
    }
}
