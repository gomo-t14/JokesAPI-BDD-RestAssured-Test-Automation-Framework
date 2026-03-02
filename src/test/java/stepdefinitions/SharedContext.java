package stepdefinitions;

import io.restassured.response.Response;

/**
 * Picocontainer shared state.
 * Injected into every step class so baseUrl and response
 * are accessible across all step definition files without static variables.
 */
public class SharedContext {
    public String   baseUrl;
    public Response response;
}