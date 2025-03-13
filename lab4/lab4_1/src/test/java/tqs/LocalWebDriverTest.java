package tqs;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.seljup.SeleniumJupiter;

@ExtendWith(SeleniumJupiter.class)
public class LocalWebDriverTest {

    @Test
    public void testWithFirefox(FirefoxDriver driver) {
        driver.get("http://www.seleniumhq.org/");
        assertTrue(driver.getTitle().startsWith("Selenium"));
    }

}
