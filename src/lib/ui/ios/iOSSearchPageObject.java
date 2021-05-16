package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.SearchPageObject;

public class iOSSearchPageObject extends SearchPageObject {

    static {
        SEARCH_INIT_ELEMENT = "xpath://XCUIElementTypeSearchField[@name='Search Wikipedia']";
        SEARCH_INPUT = "xpath://XCUIElementTypeSearchField[@label='Search Wikipedia']";
        SEARCH_CANCEL_BUTTON = "id:Cancel";
        SEARCH_RESULT_BY_SUBSTRING_TPL = "xpath://XCUIElementTypeCell//XCUIElementTypeStaticText[contains(@value,'{SUBSTRING}')]";
        SEARCH_RESULT_LIST_ITEM = "xpath://XCUIElementTypeCollectionView[@visible='true']//XCUIElementTypeCell";
        SEARCH_RESULT_TITLE_TPL = "xpath://XCUIElementTypeCollectionView[@visible='true']" +
                "//XCUIElementTypeCell//XCUIElementTypeStaticText[@value='{TITLE}'][1]";
        SEARCH_RESULT_LIST = "xpath://XCUIElementTypeCollectionView[@visible='true']";
        SEARCH_EMPTY_RESULT_ELEMENT = "id:No results found";
        SEARCH_RESULT_LIST_ELEMENT = "xpath://XCUIElementTypeCollectionView[@visible='true']" +
                "//XCUIElementTypeCell//XCUIElementTypeStaticText[@value][1]";
        SEARCH_BUTTON = "id:Search Wikipedia";
        SEARCH_RESULT_BY_SUBSTRING_TITLE_AND_DESCRIPTION_TPL =
                "xpath://XCUIElementTypeCollectionView[@visible='true']//XCUIElementTypeCell" +
                        "[.//XCUIElementTypeStaticText[@value='{ARTICLE_TITLE}']]" +
                        "[.//XCUIElementTypeStaticText[contains(@value,'{ARTICLE_DESCRIPTION}')]]";
    }

    public iOSSearchPageObject(AppiumDriver driver) {
        super(driver);
    }
}
