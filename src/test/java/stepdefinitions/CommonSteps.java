package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static io.restassured.RestAssured.given;

public class CommonSteps {

    private static final Logger log = LogManager.getLogger(CommonSteps.class);
    private final SharedContext context;

    public CommonSteps(SharedContext context) {
        this.context = context;
    }

    // ONE definition of this step — shared by all feature files
    @Given("the base URL is {string}")
    public void setBaseUrl(String baseUrl) {
        context.baseUrl = baseUrl;
        log.info("Base URL set to: {}", baseUrl);
    }

    // ONE definition of this step — shared by all feature files
    @When("I send a GET request to {string}")
    public void sendGetRequest(String endpoint) {
        log.info("GET {}{}", context.baseUrl, endpoint);
        context.response = given()
                .baseUri(context.baseUrl)
                .header("Accept", "application/json")
                .when()
                .get(endpoint);
        log.debug("Status: {} | Body: {}",
                context.response.getStatusCode(),
                context.response.getBody().asString());
    }
}