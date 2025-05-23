package org.ebusahin.stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.ebusahin.pages.HomePage;
import org.ebusahin.pages.LoginPage;
import org.ebusahin.utilities.ConfigReader;
import org.ebusahin.utilities.DriverFactory;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

public class LoginStepDefinitions {
    LoginPage loginPage = new LoginPage();
    HomePage homePage = new HomePage();
    WebDriver driver = DriverFactory.getDriver();


    @Given("User is on {string} login page")
    public void userIsOnLoginPage(String page) {
        driver.get(ConfigReader.getProperty(page));
    }

    @And("When user logs in with username {string} and password {string}")
    public void whenUserLogsInWithUsernameAndPassword(String username, String password) {
        loginPage.usernameTextBox.sendKeys(username);
        loginPage.passwordTextBox.sendKeys(password);
        loginPage.loginButton.click();
    }

    @Then("The user should see an error message")
    public void thenTheUserShouldSeeASuccessMessage() {
        Assert.assertTrue(homePage.productsText.isDisplayed());
    }

    @Then("User closes the driver")
    public void userClosesTheDriver() {
        DriverFactory.quitDriver();
    }
}
