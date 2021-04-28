package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class SearchPageObject extends MainPageObject {

  private static final String
          SEARCH_INIT_ELEMENT = "//*[contains(@text, 'Search Wikipedia')]",
          SEARCH_INPUT = "//*[contains(@text, 'Search…')]",
          SEARCH_CANCEL_BUTTON = "org.wikipedia:id/search_close_btn",
          SEARCH_RESULT_BY_SUBSTRING_TPL = "//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='{SUBSTRING}']",
          SEARCH_RESULT_LIST_ITEM = "org.wikipedia:id/page_list_item_container",
          SEARCH_RESULT_TITLE_TPL = "//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='{TITLE}']",
          SEARCH_RESULT_ELEMENT = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']",
          SEARCH_EMPTY_RESULT_ELEMENT = "//*[@text='No results found']",
          SEARCH_LIST_ELEMENT = "org.wikipedia:id/page_list_item_container",
          SEARCH_BUTTON = "//*[@resource-id='org.wikipedia:id/menu_page_search']";

  public SearchPageObject(AppiumDriver driver) {
    super(driver);
  }

  /* TEMPLATES METHODS */
  private static String getResultSearchElement(String substring) {
    return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
  }

  private static String getResultSearchElementTitle(String articleTitle) {
    return SEARCH_RESULT_TITLE_TPL.replace("{TITLE}", articleTitle);
  }
  /* TEMPLATES METHODS */

  public void initSearchInput() {
    this.waitForElementAndClick(
            By.xpath(SEARCH_INIT_ELEMENT),
            "Cannot find and click search init element", 5);
    this.waitForElementPresent(By.xpath(SEARCH_INIT_ELEMENT),
            "Cannot find search input after clicking search init element");
  }

  public void waitForCancelButtonToAppear() {
    this.waitForElementPresent(
            By.id(SEARCH_CANCEL_BUTTON),
            "Cannot find search cancel button",
            5);
  }

  public void waitForCancelButtonToDisappear() {
    this.waitForElementNotPresent(
            By.id(SEARCH_CANCEL_BUTTON),
            "Search cancel button is still present",
            5);
  }

  public void waitForClickSearchButton(){
    this.waitForElementAndClick(
            By.xpath(SEARCH_BUTTON),
            "Cannot find Search button",
            5
    );
  }

  public void clickCancelSearch() {
    this.waitForElementAndClick(
            By.id(SEARCH_CANCEL_BUTTON),
            "Cannot find and click search cancel button",
            5
    );
  }

  public void typeSearchLine(String search_line) {
    this.waitForElementAndSendKeys(
            By.xpath(SEARCH_INPUT),
            search_line,
            "Cannot find and type into search input", 5);
  }

  public void waitForSearchResult(String substring) {
    String search_result_xpath = getResultSearchElement(substring);
    this.waitForElementPresent(
            By.xpath(search_result_xpath),
            "Cannot find search result with substring " + substring);
  }

  public void clickByArticleWithSubstring(String substring) {
    String search_result_xpath = getResultSearchElement(substring);
    this.waitForElementAndClick(
            By.xpath(search_result_xpath),
            "Cannot find and click search result with substring " + substring,
            10);
  }

  public int getAmountOfFoundArticles() {
    this.waitForElementPresent(
            By.xpath(SEARCH_RESULT_ELEMENT),
            "Cannot find anything by the request",
            15
    );
    return this.getAmountOfElements(By.xpath(SEARCH_RESULT_ELEMENT)
    );
  }

  public void waitForEmptyResultsLabel() {
    this.waitForElementPresent(
            By.xpath(SEARCH_EMPTY_RESULT_ELEMENT),
            "Cannot find empty result element",
            15
    );
  }

  public void assertThereIsNoResultOfSearch() {
    this.assertElementNotPresent(By.xpath(SEARCH_RESULT_ELEMENT), "We supposed not to find any results");
  }

  public void assertSearchPlaceHolderText(String search_line) {
    this.waitForElementPresent(
            By.xpath(SEARCH_INIT_ELEMENT),
            "Cannot find search input-field",
            5
    );
    this.assertElementHasText(
            By.xpath(SEARCH_INIT_ELEMENT),
            search_line,
            "The search field doesn't exist correct text"
    );
  }

  public void waitForSearchResultsNotEmpty(int count_of_elements) {
    this.waitForNumberOfElementsToBeMoreThan(
            By.id(SEARCH_LIST_ELEMENT),
            count_of_elements,
            "The search results more than " + count_of_elements + " or isn't displayed",
            5
    );
  }

  public void waitForSearchResultsNotDisplayed() {
    this.waitForElementNotPresent(
            By.id(SEARCH_LIST_ELEMENT),
            "The search results is still displayed",
            5
    );
  }

  public void waitForNumberOfResultsMoreThan(int count_of_results) {
    this.waitForNumberOfElementsToBeMoreThan(
            By.id(SEARCH_RESULT_LIST_ITEM),
            count_of_results,
            "Count of results less than expected",
            15);
  }

  public void waitFotClickByArticleWithTitle(String article_title) {
    String article_title_xpath = getResultSearchElementTitle(article_title);
    this.waitForElementClickableAndClick(
            By.xpath(article_title_xpath),
            "Article with title not clickable",
            5);
  }
}
