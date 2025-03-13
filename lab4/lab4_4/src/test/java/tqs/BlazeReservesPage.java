package tqs;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class BlazeReservesPage {
    private WebDriver driver;
    private final String url = "https://blazedemo.com/reserve.php";

    @FindBy(className="table")
    private WebElement tableOfFlights;

    private List<WebElement> flights;

    public String getUrl() {
        return url;
    }

    public BlazeReservesPage(WebDriver driver) {
        this.driver = driver;
        driver.get(url);
        PageFactory.initElements(driver, this);
        WebElement listOfFlights = tableOfFlights.findElement(By.tagName("tbody"));
        flights = listOfFlights.findElements(By.tagName("tr"));
    }

    public BlazePurchasePage selectFlight(int number) {
        for (WebElement flight : flights) {
            List<WebElement> columns = flight.findElements(By.tagName("td"));

            if (columns.get(1).getText().equals(String.valueOf(number))) {
                
                columns.get(0).findElement(By.tagName("input")).click();
                return new BlazePurchasePage(this.driver);
            }
        }

        return null;
    }
}
