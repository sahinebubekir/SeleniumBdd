package org.ebusahin.stepdefinitions;


import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.ebusahin.utilities.DriverFactory;

public class Hooks {

    @Before
    public void setUp(Scenario scenario) {
        String browserName = "";
        for (String tag : scenario.getSourceTagNames()) {
            System.out.println(tag);
            if (tag.equalsIgnoreCase("@chrome")) {
                browserName = "chrome";
            } else if (tag.equalsIgnoreCase("@firefox")) {
                browserName = "firefox";
            } else if (tag.equalsIgnoreCase("@safari")) {
                browserName = "safari";
            }
        }

        DriverFactory.initDriver(browserName);
    }

    @After
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}
