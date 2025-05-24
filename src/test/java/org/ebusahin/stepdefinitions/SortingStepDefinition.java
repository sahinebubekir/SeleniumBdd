package org.ebusahin.stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.eo.Se;
import org.ebusahin.pages.HomePage;
import org.ebusahin.utilities.ReusableMethods;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;

public class SortingStepDefinition {
    HomePage homePage = new HomePage();

    @And("User sorts products {string}")
    public void userSortsProducts(String sortType) {
        Select select = new Select(homePage.sortSelect);
        select.selectByVisibleText(sortType);
        ReusableMethods.hardWait(3);
    }

    @Then("User verifies the products are sorted {string}")
    public void userVerifiesTheProductsAreSorted(String sortingType) {
        ArrayList<Double> prices = new ArrayList<>();
        if (sortingType.equals("high to low")) {
            for (WebElement price : homePage.productPrices) {
                prices.add(Double.parseDouble(price.getText().substring(1)));
            }
            System.out.println("Prices List: " + prices);
            for (int i = 0; i < prices.size() - 1; i++) {
                if (i == prices.size() - 1) {
                    break;
                }
                System.out.println(prices.get(i) + " " + (i) + ".index " + prices.get(i + 1) + (i + 1) + ".index");
                Assert.assertTrue(prices.get(i) >= prices.get(i + 1));
            }
        } else if (sortingType.equals("low to high")) {
            for (WebElement price : homePage.productPrices) {
                prices.add(Double.parseDouble(price.getText().substring(1)));
            }
            System.out.println("Prices List: " + prices);
            for (int i = 0; i < prices.size() - 1; i++) {
                if (i == prices.size() - 1) {
                    break;
                }
                System.out.println(prices.get(i) + " " + (i) + ".index " + prices.get(i + 1) + (i + 1) + ".index");
                Assert.assertTrue(prices.get(i) <= prices.get(i + 1));
            }
        }

    }
}
