package org.ebusahin.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ShoppingCartPage extends BasePage{

    @FindBy(id = "checkout")
    public WebElement checkoutButton;

    @FindBy(id = "continue-shopping")
    public WebElement continueShoppingButton;

    @FindBy(id = "remove-sauce-labs-backpack")
    public WebElement removeButton;
}
