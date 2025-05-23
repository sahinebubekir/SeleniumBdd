package org.ebusahin.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CheckoutPreviewPage extends BasePage{

    @FindBy(id = "finish")
    public WebElement finishButton;

    @FindBy(id = "cancel")
    public WebElement cancelButton;
}
