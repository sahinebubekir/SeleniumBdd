package org.ebusahin.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

import java.util.List;

public class HomePage extends BasePage {

    @FindBy(xpath = "//*[text()='Products']")
    public WebElement productsText;

    @FindBy(id = "add-to-cart-sauce-labs-backpack")
    public WebElement addToCartButton;

    @FindBy(className = "shopping_cart_badge")
    public WebElement shoppingCartCount;

    @FindBy(className = "shopping_cart_link")
    public WebElement shoppingCartButton;

    @FindBy(xpath = "//select[@class='product_sort_container']")
    public WebElement sortSelect;

    @FindBy(className = "inventory_item_price")
    public List<WebElement> productPrices;
}