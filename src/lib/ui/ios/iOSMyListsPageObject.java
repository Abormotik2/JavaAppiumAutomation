package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.MyListsPageObject;

public class iOSMyListsPageObject extends MyListsPageObject {

    static {
        ARTICLE_BY_TITLE_TPL = "xpath://XCUIElementTypeStaticText[contains(@name,'{TITLE}')]";
        SYNC_SAVED_ARTICLES = "id:Sync your saved articles?";
        CLOSE_SYNC_BUTTON = "id:Close";
    }

    public iOSMyListsPageObject(AppiumDriver driver) {
        super(driver);
    }
}
