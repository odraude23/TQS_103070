package tqs.backend.bddtests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.extension.ExtendWith;
import java.time.Duration;


@ExtendWith(SeleniumJupiter.class)
public class ReservationSteps {

    private WebDriver driver = new ChromeDriver();  

    @Given("I am on the home page")
    public void iAmOnTheHomePage() {
        driver.get("http://localhost:3030");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));  
    }

    @When("I select the restaurant, clicking on the view menus button")
    public void iSelectTheRestaurantClickingOnTheViewMenusButton() {
        WebElement viewMenusButton = driver.findElement(By.linkText("View Menus"));
        viewMenusButton.click();
    }

    @When("I click on the reservation button")
    public void iClickOnTheReservationButton() {
        WebElement reserveButton = driver.findElement(By.cssSelector(".mb-5:nth-child(2) .col-md-6:nth-child(1) .btn"));
        reserveButton.click();
    }

    @Then("I should confirm my reservation")
    public void iShouldConfirmMyReservation() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.alertIsPresent());  // Wait until the alert is present

        String alertText = driver.switchTo().alert().getText();
        assertTrue(alertText.contains("Reservation successful!"));
        driver.switchTo().alert().accept();  
    }

    @After
    public void closeBrowser() {
        driver.quit(); 
    }
}
