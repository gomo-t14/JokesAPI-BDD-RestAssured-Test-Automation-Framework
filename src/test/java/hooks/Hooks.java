package hooks;


import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Hooks {

    private static final Logger log = LogManager.getLogger(Hooks.class);

    @Before
    public void beforeScenario(Scenario scenario) {
        log.info("================================================");
        log.info("START: {} | Tags: {}", scenario.getName(), scenario.getSourceTagNames());
        log.info("================================================");
    }

    @After
    public void afterScenario(Scenario scenario) {
        log.info("================================================");
        log.info("END: {} | Status: {}", scenario.getName(), scenario.getStatus());
        log.info("================================================");
    }
}