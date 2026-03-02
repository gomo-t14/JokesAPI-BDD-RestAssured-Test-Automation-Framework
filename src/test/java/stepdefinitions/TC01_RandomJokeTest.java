package stepdefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import stepdefinitions.SharedContext;

import java.util.List;

import static io.restassured.RestAssured.given;

public class TC01_RandomJokeTest{

    private static final Logger log = LogManager.getLogger(TC01_RandomJokeTest.class);
    private final SharedContext context;

    public TC01_RandomJokeTest(SharedContext context) {
        this.context = context;
    }

    @Given("the TC01 base URL is {string}")
    public void setBaseUrl(String baseUrl) {
        context.baseUrl = baseUrl;
        log.info("[TC01] Base URL set to: {}", baseUrl);
    }

    @When("I send a TC01 GET request to {string}")
    public void sendGetRequest(String endpoint) {
        log.info("[TC01] GET {}{}", context.baseUrl, endpoint);
        context.response = given()
                .baseUri(context.baseUrl)
                .header("Accept", "application/json")
                .when()
                .get(endpoint);
        log.debug("[TC01] Status: {} | Body: {}",
                context.response.getStatusCode(),
                context.response.getBody().asString());
    }

    @Then("the TC01 response status code should be {int}")
    public void verifyStatusCode(int expected) {
        int actual = context.response.getStatusCode();
        log.info("[TC01] Status code — expected: {} | actual: {}", expected, actual);
        Assert.assertEquals(actual, expected,
                "Unexpected HTTP status code. Body: " + context.response.getBody().asString());
    }

    @Then("the TC01 response should contain the following fields:")
    public void responseContainsFields(DataTable dataTable) {
        List<String> fields = dataTable.asList();
        fields = fields.subList(1, fields.size()); // skip header row "field"
        String body = context.response.getBody().asString();
        log.debug("[TC01] Checking fields: {}", fields);
        for (String field : fields) {
            Assert.assertTrue(body.contains("\"" + field + "\""),
                    "[TC01] Expected JSON field missing: " + field + " | Body: " + body);
        }
    }

    @Then("the TC01 response field {string} should not be empty")
    public void responseFieldNotEmpty(String field) {
        String value = context.response.jsonPath().getString(field);
        log.debug("[TC01] Field '{}' = '{}'", field, value);
        Assert.assertNotNull(value, "[TC01] Field '" + field + "' was null");
        Assert.assertFalse(value.isBlank(), "[TC01] Field '" + field + "' was blank");
    }

    @Then("the TC01 response field {string} should be an integer")
    public void responseFieldIsInteger(String field) {
        Object value = context.response.jsonPath().get(field);
        log.debug("[TC01] Field '{}' = '{}' | type: '{}'",
                field, value, value != null ? value.getClass().getSimpleName() : "null");
        Assert.assertNotNull(value, "[TC01] Field '" + field + "' was null");
        Assert.assertTrue(value instanceof Integer,
                "[TC01] Expected field '" + field + "' to be Integer but was: "
                        + value.getClass().getSimpleName());
    }
}

