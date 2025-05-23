package org.ebusahin.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CheckoutCompletePage extends BasePage{

    @FindBy(xpath = "//h2")
    public WebElement successfulMessage;
}
