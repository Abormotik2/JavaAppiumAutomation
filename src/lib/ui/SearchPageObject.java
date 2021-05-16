package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

abstract public class SearchPageObject extends MainPageObject {

    protected static String
            SEARCH_INIT_ELEMENT,
            SEARCH_INPUT,
            SEARCH_CANCEL_BUTTON,
            SEARCH_RESULT_BY_SUBSTRING_TPL,
            SEARCH_RESULT_LIST_ITEM,
            SEARCH_RESULT_TITLE_TPL,
            SEARCH_RESULT_LIST,
            SEARCH_EMPTY_RESULT_ELEMENT,
            SEARCH_RESULT_LIST_ELEMENT,
            SEARCH_BUTTON,
            SEARCH_RESULT_BY_SUBSTRING_TITLE_AND_DESCRIPTION_TPL;

    public SearchPageObject(AppiumDriver driver) {
        super(driver);
    }

    /* TEMPLATES METHODS */
    private static String getResultSearchElement(String substring) {
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }

    private static String getResultSearchElementTitle(String article_title) {
        return SEARCH_RESULT_TITLE_TPL.replace("{TITLE}", article_title);
    }

    private static String getArticleWithTitleAndDescription(String article_title, String article_description) {
        return SEARCH_RESULT_BY_SUBSTRING_TITLE_AND_DESCRIPTION_TPL.replace("{ARTICLE_TITLE}", article_title)
                .replace("{ARTICLE_DESCRIPTION}", article_description);
    }
    /* TEMPLATES METHODS */

    public void initSearchInput() {
        this.waitForElementAndClick(
                SEARCH_INIT_ELEMENT,
                "Cannot find and click search init element", 5);
        this.waitForElementPresent(SEARCH_INIT_ELEMENT,
                "Cannot find search input after clicking search init element");
    }

    public void waitForCancelButtonToAppear() {
        this.waitForElementPresent(
                SEARCH_CANCEL_BUTTON,
                "Cannot find search cancel button",
                5);
    }

    public void waitForCancelButtonToDisappear() {
        this.waitForElementNotPresent(
                SEARCH_CANCEL_BUTTON,
                "Search cancel button is still present",
                5);
    }

    public void waitForClickSearchButton() {
        this.waitForElementAndClick(
                SEARCH_BUTTON,
                "Cannot find Search button",
                5
        );
    }

    public void clickCancelSearch() {
        this.waitForElementAndClick(
                SEARCH_CANCEL_BUTTON,
                "Cannot find and click search cancel button",
                5
        );
    }

    public void typeSearchLine(String search_line) {
        this.waitForElementAndSendKeys(
                SEARCH_INPUT,
                search_line,
                "Cannot find and type into search input", 5);
    }

    public void waitForSearchResult(String substring) {
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementPresent(
                search_result_xpath,
                "Cannot find search result with substring " + substring);
    }

    public void clickByArticleWithSubstring(String substring) {
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementAndClick(
                search_result_xpath,
                "Cannot find and click search result with substring " + substring,
                10);
    }

    public int getAmountOfFoundArticles() {
        this.waitForElementPresent(
                SEARCH_RESULT_LIST_ELEMENT,
                "Cannot find anything by the request",
                15
        );
        return this.getAmountOfElements(SEARCH_RESULT_LIST_ELEMENT);
    }

    public void waitForEmptyResultsLabel() {
        this.waitForElementPresent(
                SEARCH_EMPTY_RESULT_ELEMENT,
                "Cannot find empty result element",
                15
        );
    }

    public void assertThereIsNoResultOfSearch() {
        this.assertElementNotPresent(SEARCH_RESULT_LIST, "We supposed not to find any results");
    }

    public void assertSearchPlaceHolderText(String search_line) {
        this.waitForElementPresent(
                SEARCH_INIT_ELEMENT,
                "Cannot find search input-field",
                5
        );
        this.assertElementHasText(
                SEARCH_INIT_ELEMENT,
                search_line,
                "The search field doesn't exist correct text"
        );
    }

    public void waitForSearchResultsNotEmpty(int count_of_elements) {
        this.waitForNumberOfElementsToBeMoreThan(
                SEARCH_RESULT_LIST_ELEMENT,
                count_of_elements,
                "The search results more than " + count_of_elements + " or isn't displayed",
                5
        );
    }

    public void waitForSearchResultsNotDisplayed() {
        this.waitForElementNotPresent(
                SEARCH_RESULT_LIST_ELEMENT,
                "The search results is still displayed",
                5
        );
    }

    public List<WebElement> waitForPresenceOfAllResults() {
        return this.waitForPresenceOfAllElementsLocated(
                SEARCH_RESULT_LIST_ELEMENT,
                "Cannot find any result",
                15);
    }

    public void waitForNumberOfResultsMoreThan(int count_of_results) {
        this.waitForNumberOfElementsToBeMoreThan(
                SEARCH_RESULT_LIST_ITEM,
                count_of_results,
                "Count of results less than expected",
                15);
    }

    public void waitFotClickByArticleWithTitle(String article_title) {
        String article_title_xpath = getResultSearchElementTitle(article_title);
        this.waitForElementClickableAndClick(
                article_title_xpath,
                "Article with title not clickable",
                5);
    }

    public void waitForElementByTitleAndDescription(String article_title, String article_description) {
        String articleWithTitleAndDescriptionXpath = getArticleWithTitleAndDescription(article_title, article_description);
        this.waitForElementPresent(
                articleWithTitleAndDescriptionXpath,
                "Cannot find article with title and description",
                15
        );
    }

    public Map<String, String> setExpectedMapOfArticlesWithTitleAndDescription(List<String> article_titles, List<String> article_descriptions) {
        return IntStream.range(0, article_titles.size()).boxed().collect(Collectors.toMap(article_titles::get, article_descriptions::get, (a, b) -> b));
    }
}
