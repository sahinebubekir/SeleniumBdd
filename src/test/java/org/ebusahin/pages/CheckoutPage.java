package org.ebusahin.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CheckoutPage extends BasePage{


    @FindBy(id = "first-name")
    public WebElement firstNameTextBox;

    @FindBy(id = "last-name")
    public WebElement lastNameTextBox;

    @FindBy(id = "postal-code")
    public WebElement postalCodeTextBox;

    @FindBy(id = "continue")
    public WebElement continueButton;
}
