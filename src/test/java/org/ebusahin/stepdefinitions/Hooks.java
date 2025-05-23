package org.ebusahin.stepdefinitions;


import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.ebusahin.utilities.DriverFactory;

import java.util.Collection;
import java.util.Set;

public class Hooks {

    @Before
    public void setUp() {
        DriverFactory.initDriver();
    }

    @After
    public void tearDown() {
        DriverFactory.closeDriver();
    }
}