package tqs;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class BlazeHomePage {
    private WebDriver driver;
    private final String url = "https://blazedemo.com/";

    @FindBy(name = "toPort")
    private WebElement toPort;

    @FindBy(name = "fromPort")
    private WebElement fromPort;

    @FindBy(css = "input[value=\'Find Flights\']")
    private WebElement findFlights;

    public String getUrl() {
        return url;
    }

    public BlazeHomePage(WebDriver driver) {
        this.driver = driver;
        driver.get(url);
        PageFactory.initElements(driver, this);
    }

    public void setToPort(String to) {
        toPort.findElement(By.cssSelector("[value=\'" + to + "\']")).click();
    }

    public void setFromPort(String from) {
        fromPort.findElement(By.cssSelector("[value=\'" + from + "\']")).click();
    }

    public BlazeReservesPage findFlights() {
        findFlights.click();
        BlazeReservesPage reserves = new BlazeReservesPage(this.driver);
        return reserves;
    }
}
