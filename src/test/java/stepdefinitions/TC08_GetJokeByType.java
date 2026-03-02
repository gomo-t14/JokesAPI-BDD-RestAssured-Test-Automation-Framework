package stepdefinitions;



import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

import java.util.List;

import static io.restassured.RestAssured.given;

public class TC08_GetJokeByType {

    private static final Logger log = LogManager.getLogger(TC08_GetJokeByType.class);
    private final SharedContext context;

    public TC08_GetJokeByType(SharedContext context) {
        this.context = context;
    }



    @When("I send a GET request to {string}")
    public void sendGetRequest(String endpoint) {
        log.info("[TC08] GET {}{}", context.baseUrl, endpoint);
        context.response = given()
                .baseUri(context.baseUrl)
                .header("Accept", "application/json")
                .when()
                .get(endpoint);
        log.debug("[TC08] Status: {} | Body: {}",
                context.response.getStatusCode(),
                context.response.getBody().asString());
    }

    @Then("the response status code should be {int}")
    public void verifyStatusCode(int expected) {
        int actual = context.response.getStatusCode();
        log.info("[TC08] Status code — expected: {} | actual: {}", expected, actual);
        Assert.assertEquals(actual, expected,
                "Unexpected HTTP status code. Body: " + context.response.getBody().asString());
    }

    @Then("the response field {string} should equal {string}")
    public void responseFieldEquals(String field, String expected) {
        Object actual = context.response.jsonPath().get(field);
        log.info("[TC08] Field '{}' — expected: '{}' | actual: '{}'", field, expected, actual);
        Assert.assertNotNull(actual,
                "[TC08] Field '" + field + "' was null. Body: "
                        + context.response.getBody().asString());
        Assert.assertEquals(actual.toString(), expected,
                "[TC08] Field '" + field + "' value mismatch");
    }

    @Then("the response should contain the following fields:")
    public void responseContainsFields(DataTable dataTable) {
        List<String> fields = dataTable.asList();
        fields = fields.subList(1, fields.size()); // skip header row "field"
        String body = context.response.getBody().asString();
        log.debug("[TC08] Checking fields: {}", fields);
        for (String field : fields) {
            Assert.assertTrue(body.contains("\"" + field + "\""),
                    "[TC08] Expected JSON field missing: " + field + " | Body: " + body);
        }
    }

    @Then("the response field {string} should not be empty")
    public void responseFieldNotEmpty(String field) {
        String value = context.response.jsonPath().getString(field);
        log.debug("[TC08] Field '{}' = '{}'", field, value);
        Assert.assertNotNull(value, "[TC08] Field '" + field + "' was null");
        Assert.assertFalse(value.isBlank(), "[TC08] Field '" + field + "' was blank");
    }

    @Then("the response field {string} should be an integer")
    public void responseFieldIsInteger(String field) {
        Object value = context.response.jsonPath().get(field);
        log.debug("[TC08] Field '{}' = '{}' | type: '{}'",
                field, value, value != null ? value.getClass().getSimpleName() : "null");
        Assert.assertNotNull(value, "[TC08] Field '" + field + "' was null");
        Assert.assertTrue(value instanceof Integer,
                "[TC08] Expected field '" + field + "' to be Integer but was: "
                        + value.getClass().getSimpleName());
    }
}