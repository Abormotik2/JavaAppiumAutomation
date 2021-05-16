package lib.ui;

import io.appium.java_client.AppiumDriver;

public class WelcomePageObject extends MainPageObject {
    private static final String
            LEARN_MORE_ABOUT_WIKIPEDIA_LINK = "id:Learn more about Wikipedia",
            NEXT_BUTTON = "id:Next",
            NEW_WAYS_TO_EXPLORE_TEXT = "id:New ways to explore",
            ADD_OR_EDIT_PREFERRED_LANG_LINK = "id:Add or edit preferred languages",
            LEARN_MORE_ABOUT_DATA_COLLECTED_LINK = "id:Learn more about data collected",
            GET_STARTED_BUTTON = "id:Get started",
            SKIP_BUTTON = "id:Skip";

    public WelcomePageObject(AppiumDriver driver) {
        super(driver);
    }

    public void waitForLearnMoreLink() {
        this.waitForElementPresent(LEARN_MORE_ABOUT_WIKIPEDIA_LINK, "Cannot find 'Learn more about Wikipedia' link", 10);
    }

    public void clickNextButton() {
        this.waitForElementClickableAndClick(NEXT_BUTTON, "Cannot find and click 'Next' link", 10);
    }

    public void waitForNewWayToExploreText() {
        this.waitForElementPresent(NEW_WAYS_TO_EXPLORE_TEXT, "Cannot find 'New ways to explore' link", 10);
    }

    public void waitForAddOrEditPreferredLangText() {
        this.waitForElementPresent(ADD_OR_EDIT_PREFERRED_LANG_LINK, "Cannot find 'Add or edit preferred languages' link", 10);
    }

    public void waitForLearnMoreAboutDataCollectedText() {
        this.waitForElementPresent(LEARN_MORE_ABOUT_DATA_COLLECTED_LINK, "Cannot find 'Learn more about data collected' link", 10);
    }

    public void clickGetStartedButton() {
        this.waitForElementClickableAndClick(GET_STARTED_BUTTON, "Cannot find and click 'Get started' link", 10);
    }

    public void clickSkip() {
        this.waitForElementClickableAndClick(SKIP_BUTTON, "Cannot find 'Skip' button link", 5);
    }
}