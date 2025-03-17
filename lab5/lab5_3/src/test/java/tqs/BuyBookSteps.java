package tqs;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.seljup.SeleniumJupiter;
import java.time.Duration;

@ExtendWith(SeleniumJupiter.class)
public class BuyBookSteps {
    private final WebDriver driver = new FirefoxDriver();

    @Given("I am on https:\\/\\/cover-bookstore.onrender.com home page")
    public void setup() {
        driver.get("https://cover-bookstore.onrender.com");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
    }

    @When("I search for {string} in the search bar")
    public void searchBook(String bookTitle) {
        WebElement searchBar = driver.findElement(By.cssSelector("[data-testid=\"book-search-input\"]"));
        searchBar.sendKeys(bookTitle);
        searchBar.sendKeys(Keys.ENTER);
    }

    @When("I click on the book titled {string}")
    public void clickBook(String bookTitle) {
        List<WebElement> bookElements = driver.findElements(By.cssSelector("[data-testid='book-search-item']"));

        for (WebElement bookElement : bookElements) {
            WebElement bookTitleElement = bookElement.findElement(By.cssSelector("span.SearchList_bookTitle__1wo4a"));
            
            if (bookTitleElement.getText().equals(bookTitle)) {
                bookTitleElement.click();
                break;
            }
        }
    }

    @Then("I should get {int} book")
    public void checkBookCount(int count) {
        List<WebElement> bookElements = driver.findElements(By.cssSelector("[data-testid='book-search-item']"));
        assertEquals(count, bookElements.size());
    }

    @Then("I should be in the book details page for {string}")
    public void checkPage(String title) {
        WebElement bookTitleElement = driver.findElement(By.cssSelector("span.BookDetails_bookTitle__1eJ1S"));
        assertEquals(title, bookTitleElement.getText());
    }

    @After()
    public void closeBrowser() {
        driver.quit();
    }
}
