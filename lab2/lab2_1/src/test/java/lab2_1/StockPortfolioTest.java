package lab2_1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class StockPortfolioTest {

    @Mock
    IStockmarketService stockMarket;

    @InjectMocks
    StocksPortfolio portfolio;
    
    @DisplayName("Test total value")
    @Test
    void totalValueTest() {
        when(stockMarket.lookUpPrice("EBAY")).thenReturn(4.0);
        when(stockMarket.lookUpPrice("AAPL")).thenReturn(2.0);
        when(stockMarket.lookUpPrice("IBM")).thenReturn(5.0);

        portfolio.addStock(new Stock("EBAY", 2));
        portfolio.addStock(new Stock("AAPL", 3));

        double result = portfolio.totalValue();

        assertEquals(14.0, result);
        //test with hamcrest
        assertThat(result, equalTo(14.0));
        verify(stockMarket, times(2)).lookUpPrice(anyString());
    }

    @DisplayName("Test most valuable stocks")
    @Test
    void mostValuableStocksTest() {
        when(stockMarket.lookUpPrice("EBAY")).thenReturn(4.0);
        when(stockMarket.lookUpPrice("AAPL")).thenReturn(2.0);
        when(stockMarket.lookUpPrice("IBM")).thenReturn(5.0);

        portfolio.addStock(new Stock("EBAY", 2));
        portfolio.addStock(new Stock("AAPL", 3));
        portfolio.addStock(new Stock("IBM", 1));

        var result = portfolio.mostValuableStocks(2);

        /* this test will fail because there are only 3 stocks in the portfolio
        var result = portfolio.mostValuableStocks(4);
        assertEquals(4, result.size());   // expected 4 but was 3
        */

        /* same thing testing with -1 and 0
        var result = portfolio.mostValuableStocks(-1);
        assertEquals(0, result.size());   // IllegalArgumentException: -1
        var result = portfolio.mostValuableStocks(0);
        assertEquals(0, result.size());   // works but not the expected result
        */ 

        assertEquals(2, result.size());
        assertEquals("EBAY", result.get(0).getLabel());
        assertEquals("AAPL", result.get(1).getLabel());
        verify(stockMarket, times(4)).lookUpPrice(anyString());
    }
}
