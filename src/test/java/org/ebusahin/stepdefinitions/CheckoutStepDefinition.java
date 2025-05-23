package org.ebusahin.stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.ebusahin.pages.CheckoutCompletePage;
import org.ebusahin.pages.CheckoutPage;
import org.ebusahin.pages.CheckoutPreviewPage;
import org.ebusahin.utilities.ConfigReader;
import org.ebusahin.utilities.ReusableMethods;
import org.junit.Assert;

public class CheckoutStepDefinition {
    CheckoutPage checkoutPage = new CheckoutPage();
    CheckoutPreviewPage checkoutPreviewPage = new CheckoutPreviewPage();
    CheckoutCompletePage checkoutCompletePage = new CheckoutCompletePage();

    @And("User gives firstname {string}")
    public void userGivesFirstname(String firstName) {
        checkoutPage.firstNameTextBox.sendKeys(firstName);
    }

    @And("User gives lastname {string}")
    public void userGivesLastname(String lastName) {
        checkoutPage.lastNameTextBox.sendKeys(lastName);
    }

    @And("User clicks on continute button")
    public void userClicksOnContinuteButton() throws InterruptedException {
        checkoutPage.continueButton.click();
        ReusableMethods.hardWait(4);
    }

    @And("user gives postcode {string}")
    public void userGivesPostcode(String postCode) {
        checkoutPage.postalCodeTextBox.sendKeys(postCode);
    }

    @And("User clicks on finish button")
    public void userClicksOnFinishButton() {
        checkoutPreviewPage.finishButton.click();
    }

    @Then("User verifies successful message {string}")
    public void userVerifiesSuccessfulMessage(String expectedMessageProperties) {
        Assert.assertEquals(ConfigReader.getProperty(expectedMessageProperties), checkoutCompletePage.successfulMessage.getText());
        ReusableMethods.hardWait(3);
    }
}
