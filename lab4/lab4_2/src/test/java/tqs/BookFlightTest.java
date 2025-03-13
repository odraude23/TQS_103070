package tqs;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.seljup.SeleniumJupiter;

@ExtendWith(SeleniumJupiter.class)
public class BookFlightTest {
    
    @Test
    public void bookFlightTest(FirefoxDriver driver) {
        driver.get("https://blazedemo.com/");
        driver.manage().window().setSize(new Dimension(760, 720));
        driver.findElement(By.name("fromPort")).click();
        {
            WebElement dropdown = driver.findElement(By.name("fromPort"));
            dropdown.findElement(By.xpath("//option[. = 'Portland']")).click();
        }
        driver.findElement(By.cssSelector(".form-inline:nth-child(1) > option:nth-child(4)")).click();
        driver.findElement(By.name("toPort")).click();
        {
            WebElement dropdown = driver.findElement(By.name("toPort"));
            dropdown.findElement(By.xpath("//option[. = 'London']")).click();
        }
        driver.findElement(By.cssSelector(".form-inline:nth-child(4) > option:nth-child(3)")).click();
        driver.findElement(By.cssSelector(".btn-primary")).click();
        driver.findElement(By.cssSelector("tr:nth-child(2) .btn")).click();
        driver.findElement(By.id("inputName")).click();
        driver.findElement(By.id("inputName")).sendKeys("Eduardo");
        driver.findElement(By.id("address")).click();
        driver.findElement(By.id("address")).sendKeys("Rua dos mares");
        driver.findElement(By.id("city")).click();
        driver.findElement(By.id("city")).sendKeys("Aveiro");
        driver.findElement(By.cssSelector(".control-group:nth-child(5) > .controls")).click();
        driver.findElement(By.id("state")).click();
        driver.findElement(By.id("state")).sendKeys("Aveiro");
        driver.findElement(By.id("zipCode")).click();
        driver.findElement(By.id("zipCode")).sendKeys("3830-500");
        driver.findElement(By.id("cardType")).click();
        {
            WebElement dropdown = driver.findElement(By.id("cardType"));
            dropdown.findElement(By.xpath("//option[. = 'American Express']")).click();
        }
        driver.findElement(By.cssSelector("option:nth-child(2)")).click();
        driver.findElement(By.id("creditCardNumber")).click();
        driver.findElement(By.id("creditCardNumber")).sendKeys("123456778");
        driver.findElement(By.id("creditCardMonth")).click();
        driver.findElement(By.id("creditCardMonth")).sendKeys("1");
        driver.findElement(By.id("creditCardYear")).click();
        driver.findElement(By.id("creditCardYear")).sendKeys("2002");
        driver.findElement(By.id("nameOnCard")).click();
        driver.findElement(By.id("nameOnCard")).sendKeys("Eduardo Lopes");
        driver.findElement(By.cssSelector(".btn-primary")).click();
        assertThat(driver.getTitle()).isEqualTo("BlazeDemo Confirmation");
    }
}
