package org.ebusahin.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {

    @FindBy(xpath = "//*[text()='Products']")
    public WebElement productsText;

    @FindBy(id = "add-to-cart-sauce-labs-backpack")
    public WebElement addToCartButton;

    @FindBy(className = "shopping_cart_badge")
    public WebElement shoppingCartCount;

    @FindBy(className = "shopping_cart_link")
    public WebElement shoppingCartButton;


}