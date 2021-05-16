package lib.ui;

import io.appium.java_client.AppiumDriver;

abstract public class NavigationUI extends MainPageObject {

    protected static String
            MY_LISTS_BUTTON_LINK,
            TAB_BAR_ELEMENT,
            LISTS_PAGE_TOOLBAR_TITLE;

    public NavigationUI(AppiumDriver driver) {
        super(driver);
    }

    public void clickMyLists() {
        this.waitForElementLocated(TAB_BAR_ELEMENT, "Cannot find tab bar panel", 15);
        this.waitForElementAndClick(
                MY_LISTS_BUTTON_LINK,
                "Cannot find navigation button to My List",
                5);
        this.waitForElementPresent(
                LISTS_PAGE_TOOLBAR_TITLE,
                "Cannot find list folders with articles",
                15);
    }
}
