package org.ebusahin.stepdefinitions;


import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.ebusahin.utilities.DriverFactory;

import java.util.Collection;
import java.util.Set;

public class Hooks {

    @Before
    public void setUp(Scenario scenario) {
        String browserName = null;

        // Öncelikle tag'lerden browser adı alın
        Collection<String> tags = scenario.getSourceTagNames();
        for (String tag : tags) {
            if (tag.equalsIgnoreCase("@chrome")) {
                browserName = "chrome";
                break;
            } else if (tag.equalsIgnoreCase("@firefox")) {
                browserName = "firefox";
                break;
            } else if (tag.equalsIgnoreCase("@safari")) {
                browserName = "safari";
                break;
            }
        }

        // Eğer tag bulunamazsa, sistem özelliğinden alın (örn: -Dbrowser=chrome)
        if (browserName == null) {
            browserName = System.getProperty("browser", "chrome"); // default: chrome
        }

        System.out.println(">>> Browser to launch: " + browserName);
        DriverFactory.initDriver(browserName);
    }

    @After
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}