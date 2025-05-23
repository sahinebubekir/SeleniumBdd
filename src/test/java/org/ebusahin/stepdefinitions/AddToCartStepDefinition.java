package org.ebusahin.stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.ebusahin.pages.HomePage;
import org.ebusahin.pages.ShoppingCartPage;
import org.ebusahin.utilities.DriverFactory;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AddToCartStepDefinition {

    HomePage homePage = new HomePage();
    ShoppingCartPage shoppingCartPage = new ShoppingCartPage();
    WebDriver driver;

    @And("User adds {string} to cart")
    public void userAddsToCart(String prouctName) {
        driver = DriverFactory.getDriver();
        driver.findElement(By.id("add-to-cart-" + prouctName)).click();
    }

    @Then("User verifies cart count is {int}")
    public void userVerifiesCartCountIs(int expectedCartCount) {
        int actualCartCount = Integer.parseInt(homePage.shoppingCartCount.getText());
        Assert.assertEquals(expectedCartCount, actualCartCount);
    }

    @Then("User clicks on shopping cart")
    public void userClicksOnShoppingCart() throws InterruptedException {
        homePage.shoppingCartButton.click();
        Thread.sleep(3000);
    }

    @And("User clicks on checkout button")
    public void userClicksOnCheckoutButton() {
        shoppingCartPage.checkoutButton.click();
    }
}
