package stepdefinitions;

import io.cucumber.java.en.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

import java.util.List;

import static io.restassured.RestAssured.given;

public class TC04_GetCountNumberOfJokes {

    private static final Logger log = LogManager.getLogger(TC04_GetCountNumberOfJokes.class);
    private final SharedContext context;

    public TC04_GetCountNumberOfJokes(SharedContext context) {
        this.context = context;
    }

    @Given("the base URL is {string}")
    public void setBaseUrl(String baseUrl) {
        context.baseUrl = baseUrl;
        log.info("[TC04] Base URL set to: {}", baseUrl);
    }

    @When("I send a GET request to {string}")
    public void sendGetRequest(String endpoint) {
        log.info("[TC04] GET {}{}", context.baseUrl, endpoint);
        context.response = given()
                .baseUri(context.baseUrl)
                .when()
                .get(endpoint);
        log.debug("[TC04] Status: {} | Body: {}",
                context.response.getStatusCode(),
                context.response.getBody().asString());
    }

    @Then("the response status code should be {int}")
    public void verifyStatusCode(int expected) {
        int actual = context.response.getStatusCode();
        log.info("[TC04] Status code — expected: {} | actual: {}", expected, actual);
        Assert.assertEquals(actual, expected,
                "Unexpected HTTP status code. Body: " + context.response.getBody().asString());
    }

    @Then("the response array length should be {int}")
    public void verifyArrayLength(int expected) {
        List<?> list = context.response.jsonPath().getList("$");
        log.info("[TC04] Array length — expected: {} | actual: {}", expected, list.size());
        Assert.assertEquals(list.size(), expected,
                "[TC04] Response array length mismatch");
    }
}