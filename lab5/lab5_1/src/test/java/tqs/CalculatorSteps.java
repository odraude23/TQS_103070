package tqs;

import static java.lang.invoke.MethodHandles.lookup;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CalculatorSteps {
    static final Logger log = getLogger(lookup().lookupClass());
    private Calcuator calc;

    @Given("a calculator I just turned on")
    public void setup() {
        calc = new Calcuator();
    }

    @When("I add {int} and {int}")
    public void add(int x, int y) {
        log.debug("Adding {} and {}", x, y);
        calc.push(x);
        calc.push(y);
        calc.push("+");
    }

    @When("I subtract {int} by {int}")
    public void subtract(int x, int y) {
        log.debug("Subtracting {} from {}", x, y);
        calc.push(x);
        calc.push(y);
        calc.push("-");
    }

    @When("I multiply {int} by {int}")
    public void multiply(int x, int y) {
        log.debug("Multiplying {} by {}", x, y);
        calc.push(x);
        calc.push(y);
        calc.push("*");
    }

    @When("I divide {int} by {int}")
    public void divide(int x, int y) {
        log.debug("Dividing {} by {}", x, y);
        calc.push(x);
        calc.push(y);
        calc.push("/");
    }

    @Then("the result is {int}")
    public void result(int expected) {
        int result = calc.value().intValue();
        log.debug("Result is {}, expected {}", result, expected);
        assertEquals(expected, result);
    }

    @Then("the result is infinity")
    public void resultInfinity() {
        Number result = calc.value();
        log.debug("Result is {}, expected {}", result, "infinity");
        assertEquals(Double.POSITIVE_INFINITY, result);
    }
}
