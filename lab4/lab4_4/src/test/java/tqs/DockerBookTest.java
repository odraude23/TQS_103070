package tqs;

import static org.assertj.core.api.Assertions.assertThat;
import static io.github.bonigarcia.seljup.BrowserType.CHROME;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import io.github.bonigarcia.seljup.DockerBrowser;
import io.github.bonigarcia.seljup.SeleniumJupiter;

@ExtendWith(SeleniumJupiter.class)
public class DockerBookTest {
    
    @Test
    void bookTest(@DockerBrowser(type = CHROME, vnc = true) WebDriver driver) {
        BlazeHomePage BlazeHomePage = new BlazeHomePage(driver);
		BlazeHomePage.setFromPort("Portland");
		BlazeHomePage.setToPort("London");

		BlazeReservesPage reserves_page = BlazeHomePage.findFlights();
		assertThat(driver.getTitle()).isEqualTo("BlazeDemo - reserve");
		assertThat(driver.getCurrentUrl()).isEqualTo(reserves_page.getUrl());
		
		BlazePurchasePage purchase_page = reserves_page.selectFlight(43);
		assertThat(driver.getTitle()).isEqualTo("BlazeDemo Purchase");
		assertThat(driver.getCurrentUrl()).isEqualTo(purchase_page.getUrl());
		purchase_page.setInputName("Eduardo");
		purchase_page.setAddress("Rua dos mares");
		purchase_page.setCity("Aveiro");
		purchase_page.setState("Aveiro");
		purchase_page.setZipCode("3830-500");
		purchase_page.setCardType("American Express");
		purchase_page.setCreditCardNumber("123456778");
		purchase_page.setCreditCardMonth("1");
		purchase_page.setCreditCardYear("2002");
		purchase_page.setNameOnCard("Eduardo Lopes");
		purchase_page.toggleRememberMe();
		purchase_page.purchaseFlight();

		assertThat(driver.getTitle()).isEqualTo("BlazeDemo Confirmation");
		assertThat(driver.getCurrentUrl()).isEqualTo("https://blazedemo.com/confirmation.php");
    }
}
