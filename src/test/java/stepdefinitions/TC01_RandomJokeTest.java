package stepdefinitions;


import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static org.testng.Assert.assertEquals;

public class TC01_RandomJokeTest {

    private final SharedContext context;

    // Constructor for dependency injection via PicoContainer
    public TC01_RandomJokeTest(SharedContext context) {
        this.context = context;
    }

    @Given("the base URL is {string}")
    public void setBaseUrl(String url) {
        context.baseUrl = url;
    }

    @When("I send a GET request to {string}")
    public void sendGetRequest(String endpoint) {
        context.response = RestAssured
                .given()
                .baseUri(context.baseUrl)
                .when()
                .get(endpoint)
                .then()
                .extract()
                .response();
    }

    @Then("the response status code should be {int}")
    public void verifyStatusCode(int statusCode) {
        assertEquals(context.response.getStatusCode(), statusCode);
    }
}

