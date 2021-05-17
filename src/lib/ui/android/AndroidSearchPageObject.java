package lib.ui.android;

import io.appium.java_client.AppiumDriver;
import lib.ui.SearchPageObject;

public class AndroidSearchPageObject extends SearchPageObject {

    static {
        SEARCH_INIT_ELEMENT = "xpath://*[contains(@text, 'Search Wikipedia')]";
        SEARCH_INPUT = "xpath://*[contains(@text, 'Search…')]";
        SEARCH_CANCEL_BUTTON = "id:org.wikipedia:id/search_close_btn";
        SEARCH_RESULT_BY_SUBSTRING_TPL = "xpath://*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='{SUBSTRING}']";
        SEARCH_RESULT_LIST_ITEM = "id:org.wikipedia:id/page_list_item_container";
        SEARCH_RESULT_TITLE_TPL = "xpath://*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='{TITLE}']";
        SEARCH_RESULT_LIST = "xpath://*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";
        SEARCH_EMPTY_RESULT_ELEMENT = "xpath://*[@text='No results found']";
        SEARCH_RESULT_LIST_ELEMENT = "id:org.wikipedia:id/page_list_item_title";
        SEARCH_BUTTON = "xpath://*[@resource-id='org.wikipedia:id/menu_page_search']";
        SEARCH_RESULT_BY_SUBSTRING_TITLE_AND_DESCRIPTION_TPL =
                "xpath://*[@resource-id='org.wikipedia:id/page_list_item_container']" +
                        "[.//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='{ARTICLE_TITLE}']]" +
                        "[.//*[@resource-id='org.wikipedia:id/page_list_item_description'][@text='{ARTICLE_DESCRIPTION}']]";
    }

    public AndroidSearchPageObject(AppiumDriver driver) {
        super(driver);
    }
}
