package tqs;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import java.time.Duration;
import java.util.List;

import io.github.bonigarcia.seljup.SeleniumJupiter;

@ExtendWith(SeleniumJupiter.class)
public class SearchHarryPotterTest {

    @Test
    public void testSearchHarryPotter(FirefoxDriver driver) {
        driver.get("https://cover-bookstore.onrender.com/");
        driver.manage().window().setSize(new Dimension(760, 722));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        driver.findElement(By.cssSelector("[data-testid=\"book-search-input\"]")).sendKeys("harry potter");
        driver.findElement(By.cssSelector("[data-testid=\"book-search-input\"]")).sendKeys(Keys.ENTER);

        List<WebElement> bookElements = driver.findElements(By.cssSelector("[data-testid='book-search-item']"));
        assertEquals(bookElements.size(), 1);

        String bookTitle = "";
        for (WebElement bookElement : bookElements) {
            WebElement bookTitleElement = bookElement.findElement(By.cssSelector("span.SearchList_bookTitle__1wo4a"));
            bookTitle = bookTitleElement.getText();
        }
        
        assertEquals(bookTitle, "Harry Potter and the Sorcerer\'s Stone");
    }
    
}
