package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class WelcomePageObject extends MainPageObject {
    private static final String
            LEARN_MORE_ABOUT_WIKIPEDIA_LINK = "Learn more about Wikipedia",
            NEXT_BUTTON = "Next",
            NEW_WAYS_TO_EXPLORE_TEXT = "New ways to explore",
            ADD_OR_EDIT_PREFERRED_LANG_LINK = "Add or edit preferred languages",
            LEARN_MORE_ABOUT_DATA_COLLECTED_LINK = "Learn more about data collected",
            GET_STARTED_BUTTON = "Get started",
            SKIP_BUTTON = "Skip";

    public WelcomePageObject(AppiumDriver driver) {
        super(driver);
    }

    public void waitForLearnMoreLink() {
        this.waitForElementPresent(By.id(LEARN_MORE_ABOUT_WIKIPEDIA_LINK), "Cannot find 'Learn more about Wikipedia' link", 10);
    }

    public void clickNextButton() {
        this.waitForElementClickableAndClick(By.id(NEXT_BUTTON), "Cannot find and click 'Next' link", 10);
    }

    public void waitForNewWayToExploreText() {
        this.waitForElementPresent(By.id(NEW_WAYS_TO_EXPLORE_TEXT), "Cannot find 'New ways to explore' link", 10);
    }

    public void waitForAddOrEditPreferredLangText() {
        this.waitForElementPresent(By.id(ADD_OR_EDIT_PREFERRED_LANG_LINK), "Cannot find 'Add or edit preferred languages' link", 10);
    }

    public void waitForLearnMoreAboutDataCollectedText() {
        this.waitForElementPresent(By.id(LEARN_MORE_ABOUT_DATA_COLLECTED_LINK), "Cannot find 'Learn more about data collected' link", 10);
    }



    public void clickGetStartedButton() {
        this.waitForElementClickableAndClick(By.id(GET_STARTED_BUTTON), "Cannot find and click 'Get started' link", 10);
    }

    public void clickSkip() {
        this.waitForElementClickableAndClick(By.id(SKIP_BUTTON), "Cannot find 'Skip' link", 5);
    }
}