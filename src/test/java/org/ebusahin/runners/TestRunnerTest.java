package org.ebusahin.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {
                "pretty",
                "html:target/default-cucumber-reports.html",
                "json:target/json-reports/cucumber.json",
                "junit:target/xml-reports/cucumber.xml"
        },
        features = "src/test/resources/features",
        glue = "org.ebusahin.stepdefinitions",
//        tags = "@wip",
        dryRun = false
)
public class TestRunnerTest {
}
