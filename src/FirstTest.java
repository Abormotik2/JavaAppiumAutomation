import lib.CoreTestCase;
import lib.ui.*;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;

import java.util.List;

public class FirstTest extends CoreTestCase {

  private MainPageObject MainPageObject;

  protected void setUp() throws Exception {
    super.setUp();
    MainPageObject = new MainPageObject(driver);
  }

  @Test
  public void testSearch() {
    SearchPageObject searchPageObject = new SearchPageObject(driver);
    searchPageObject.initSearchInput();
    searchPageObject.typeSearchLine("Java");
    searchPageObject.waitForSearchResult("Object-oriented programming language");
  }

  @Test
  public void testCancelSearch() {
    SearchPageObject searchPageObject = new SearchPageObject(driver);
    searchPageObject.initSearchInput();
    searchPageObject.waitForCancelButtonToAppear();
    searchPageObject.clickCancelSearch();
    searchPageObject.waitForCancelButtonToDisappear();
  }

  @Test
  public void testSearchResultsAndCancel() {
    final String search_line = "Java";
    MainPageObject.waitForElementAndClick(
            By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
            "Cannot find 'Search Wikipedia' input",
            5
    );
    MainPageObject.waitForElementAndSendKeys(
            By.xpath("//*[contains(@text, 'Search…')]"),
            search_line,
            "Cannot find search input",
            5
    );
    MainPageObject.waitForNumberOfElementsToBeMoreThan(
            By.id("org.wikipedia:id/page_list_item_container"),
            1,
            "Count of articles less than expected",
            5
    );
    MainPageObject.waitForElementAndClick(
            By.id("org.wikipedia:id/search_close_btn"),
            "Cannot find X to cancel search",
            5
    );
    MainPageObject.waitForElementNotPresent(By.id("org.wikipedia:id/page_list_item_container"),
            "The search results is still displayed",
            5
    );
  }

  @Test
  public void testSearchForEachResults() {
    final String search_line = "Java";
    MainPageObject.waitForElementAndClick(
            By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
            "Cannot find 'Search Wikipedia' input",
            5
    );
    MainPageObject.waitForElementAndSendKeys(
            By.xpath("//*[contains(@text, 'Search…')]"),
            search_line,
            "Cannot find search input",
            5
    );
    List<WebElement> articleTitles = MainPageObject.waitForPresenceOfAllElementsLocated(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']" +
                    "//*[@resource-id='org.wikipedia:id/page_list_item_title']"),
            "Cannot find any article title",
            15);

    articleTitles.stream()
            .map(webElement -> webElement.getAttribute("text").toLowerCase())
            .forEachOrdered(articleTitle -> assertTrue(
                    "One or more titles do not have expected search text",
                    articleTitle.contains(search_line.toLowerCase())));
  }

  @Test
  public void testCompareArticleTitle() {
    SearchPageObject searchPageObject = new SearchPageObject(driver);
    searchPageObject.initSearchInput();
    searchPageObject.typeSearchLine("Java");
    searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");
    ArticlePageObject articlePageObject = new ArticlePageObject(driver);
    String article_title = articlePageObject.getArticleTitle();
    assertEquals(
            "We see unexpected title",
            "Java (programming language)",
            article_title);
  }

  @Test
  public void testSwipeArticle() {
    SearchPageObject searchPageObject = new SearchPageObject(driver);
    searchPageObject.initSearchInput();
    searchPageObject.typeSearchLine("Appium");
    searchPageObject.clickByArticleWithSubstring("Appium");
    ArticlePageObject articlePageObject = new ArticlePageObject(driver);
    articlePageObject.waitForTitleElement();
    articlePageObject.swipeToFooter();
  }

  @Test
  public void testSaveFirstArticleToMyList() {
    SearchPageObject searchPageObject = new SearchPageObject(driver);
    searchPageObject.initSearchInput();
    searchPageObject.typeSearchLine("Java");
    searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

    ArticlePageObject articlePageObject = new ArticlePageObject(driver);
    articlePageObject.waitForTitleElement();
    final String article_title = articlePageObject.getArticleTitle();
    final String name_of_folder = "Learning programming";
    articlePageObject.addArticleToMyList(name_of_folder);
    articlePageObject.closeArticle();

    NavigationUI navigationUI = new NavigationUI(driver);
    navigationUI.clickMyLists();

    MyListsPageObject myListsPageObject = new MyListsPageObject(driver);
    myListsPageObject.openFolderByName(name_of_folder);
    myListsPageObject.swipeByArticleToDelete(article_title);
  }

  @Test
  public void testSaveTwoArticlesToMyListAndDeleteAny() {
    final String search_first_line = "Java";
    MainPageObject.waitForElementAndClick(
            By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
            "Cannot find 'Search Wikipedia' input",
            5
    );
    MainPageObject.waitForElementAndSendKeys(
            By.xpath("//*[contains(@text, 'Search…')]"),
            search_first_line,
            "Cannot find search input",
            5
    );
    final String name_of_folder = "Learning programming";
    final String article_first_locator = "//*[@resource-id='org.wikipedia:id/page_list_item_container']" +
            "//*[@text='Object-oriented programming language']";
    MainPageObject.waitForElementAndClick(
            By.xpath(article_first_locator),
            "Cannot find 'Search Wikipedia' input",
            5
    );
    MainPageObject.waitForElementPresent(
            By.id("org.wikipedia:id/view_page_title_text"),
            "Cannot find article title",
            15
    );
    MainPageObject.waitForElementAndClick(
            By.xpath("//android.widget.ImageView[@content-desc='More options']"),
            "Cannot find button to open article options",
            5
    );
    MainPageObject.waitForElementLocated(
            By.xpath("//android.widget.ListView"),
            "Context menu doesn't found",
            15
    );
    MainPageObject.waitForElementAndClick(
            By.xpath("//*[@text='Add to reading list']"),
            "Cannot find option to add article to reading list",
            5
    );
    MainPageObject.waitForElementAndClick(
            By.id("org.wikipedia:id/onboarding_button"),
            "Cannot find 'Got it' tip overlay",
            5
    );
    MainPageObject.waitForElementAndClear(
            By.id("org.wikipedia:id/text_input"),
            "Cannot find input to set name of articles folder",
            5
    );
    MainPageObject.waitForElementAndSendKeys(
            By.id("org.wikipedia:id/text_input"),
            name_of_folder,
            "Cannot put text into articles folder input",
            5
    );
    MainPageObject.waitForElementAndClick(
            By.xpath("//*[@text='OK']"),
            "Cannot press OK button",
            5
    );
    final String expected_second_value = "JavaScript";
    final String article_second_locator = "//*[@resource-id='org.wikipedia:id/page_list_item_container']" +
            "//*[@text='Programming language']";
    MainPageObject.waitForElementAndClick(
            By.xpath("//*[@resource-id='org.wikipedia:id/menu_page_search']"),
            "Cannot find Search button",
            5
    );
    MainPageObject.waitForElementAndSendKeys(
            By.xpath("//*[contains(@text, 'Search…')]"),
            expected_second_value,
            "Cannot find search input",
            5
    );
    MainPageObject.waitForElementAndClick(
            By.xpath(article_second_locator),
            "Cannot find 'Search Wikipedia' input",
            5
    );
    MainPageObject.waitForElementAndClick(
            By.xpath("//android.support.v7.widget.LinearLayoutCompat//android.widget.ImageView[@content-desc='More options']"),
            "Cannot find button to open article options",
            5
    );
    MainPageObject.waitForElementLocated(
            By.xpath("//android.widget.ListView"),
            "Context menu doesn't found",
            15
    );
    MainPageObject.waitForElementAndClick(
            By.xpath("//*[@text='Add to reading list']"),
            "Cannot find option to add article to reading list",
            5
    );
    MainPageObject.waitForElementAndClick(
            By.xpath("//android.widget.TextView[@text='" + name_of_folder + "']"),
            "Cannot find created folder",
            5
    );
    MainPageObject.waitForElementAndClick(
            By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
            "Cannot close article, cannot find X link",
            5
    );
    MainPageObject.waitForElementAndClick(
            By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
            "Cannot find navigation button to My List",
            5
    );
    MainPageObject.waitForElementPresent(
            By.xpath("//*[@text='" + name_of_folder + "']"),
            "Cannot find created folder",
            5
    );
    MainPageObject.waitForElementAndClick(
            By.xpath("//*[@text='" + name_of_folder + "']"),
            "Cannot find created folder",
            5
    );
    MainPageObject.waitForNumberOfElementsToBeMoreThan(
            By.id("org.wikipedia:id/page_list_item_container"),
            1,
            "Count of articles less than expected",
            15
    );
    final String article_first_xpath = "//*[@text='Java (programming language)']";
    MainPageObject.swipeElementToLeft(
            By.xpath(article_first_xpath),
            "Cannot find saved article"
    );
    MainPageObject.waitForElementAndClick(
            By.xpath(article_second_locator.toLowerCase()),
            "Cannot find second added article",
            10
    );
    String actual_second_line = MainPageObject.waitForElementAndGetAttribute(
            By.id("org.wikipedia:id/view_page_title_text"),
            "text",
            "Cannot find title of article",
            15
    );
    assertEquals("Actual title not equals expected", expected_second_value, actual_second_line);
  }

  @Test
  public void testArticleTitlePresence() {
    final String search_line = "Reflection";
    final String title_xpath = "//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='" + search_line + "']";
    MainPageObject.waitForElementAndClick(
            By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
            "Cannot find 'Search Wikipedia' input",
            5
    );
    MainPageObject.waitForElementAndSendKeys(
            By.xpath("//*[contains(@text, 'Search…')]"),
            search_line,
            "Cannot find search input",
            5
    );
    MainPageObject.waitForNumberOfElementsToBeMoreThan(
            By.xpath(title_xpath),
            0,
            "Count of articles less than expected",
            15
    );
    MainPageObject.waitForElementClickableAndClick(
            By.xpath(title_xpath),
            "The element not clickable",
            10
    );
    MainPageObject.assertElementPresent(
            By.id("org.wikipedia:id/view_page_title_text"),
            "Article not found"
    );
  }

  @Test
  public void testAmountOfNotEmptySearch() {
    String search_line = "Linkin Park Diskography";
    MainPageObject.waitForElementAndClick(
            By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
            "Cannot find 'Search Wikipedia' input",
            5
    );
    MainPageObject.waitForElementAndSendKeys(
            By.xpath("//*[contains(@text, 'Search…')]"),
            search_line,
            "Cannot find search input",
            5
    );
    String search_result_locator = "//*[@resource-id='org.wikipedia:id/search_results_list']" +
            "/*[@resource-id='org.wikipedia:id/page_list_item_container']";

    MainPageObject.waitForElementPresent(
            By.xpath(search_result_locator),
            "Cannot find anything by the request " + search_line,
            15
    );
    int amount_of_search_results = MainPageObject.getAmountOfElements(
            By.xpath(search_result_locator)
    );
    assertTrue("We found too few results!",
            amount_of_search_results > 0);
  }

  @Test
  public void testAmountOfEmptySearch() {
    String search_line = "sadgftyzf";
    MainPageObject.waitForElementAndClick(
            By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
            "Cannot find 'Search Wikipedia' input",
            5
    );
    MainPageObject.waitForElementAndSendKeys(
            By.xpath("//*[contains(@text, 'Search…')]"),
            search_line,
            "Cannot find search input",
            5
    );
    String search_result_locator = "//*[@resource-id='org.wikipedia:id/search_results_list']" +
            "/*[@resource-id='org.wikipedia:id/page_list_item_container']";
    String empty_result_label = "//*[@text='No results found']";
    MainPageObject.waitForElementPresent(
            By.xpath(empty_result_label),
            "Cannot find empty result label by the request " + search_line,
            15
    );
    MainPageObject.assertElementNotPresent(
            By.xpath(search_result_locator),
            "We found some results by request " + search_line
    );
  }

  @Test
  public void testChangeScreenOrientationOnSearchResults() {
    String search_line = "Java";
    MainPageObject.waitForElementAndClick(
            By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
            "Cannot find 'Search Wikipedia' input",
            5
    );
    MainPageObject.waitForElementAndSendKeys(
            By.xpath("//*[contains(@text, 'Search…')]"),
            search_line,
            "Cannot find search input",
            5
    );
    MainPageObject.waitForElementAndClick(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']" +
                    "//*[@text='Object-oriented programming language']"),
            "Cannot find 'Object-oriented programming language' topic searching by " + search_line,
            5
    );
    String title_before_rotation = MainPageObject.waitForElementAndGetAttribute(
            By.id("org.wikipedia:id/view_page_title_text"),
            "text",
            "Cannot find title of article",
            15
    );
    driver.rotate(ScreenOrientation.LANDSCAPE);

    String title_after_rotation = MainPageObject.waitForElementAndGetAttribute(
            By.id("org.wikipedia:id/view_page_title_text"),
            "text",
            "Cannot find title of article",
            15
    );
    assertEquals(
            "Article title have been changed after screen rotation",
            title_before_rotation,
            title_after_rotation
    );
    driver.rotate(ScreenOrientation.PORTRAIT);

    String title_after_second_rotation = MainPageObject.waitForElementAndGetAttribute(
            By.id("org.wikipedia:id/view_page_title_text"),
            "text",
            "Cannot find title of article",
            15
    );
    assertEquals(
            "Article title have been changed after screen rotation",
            title_before_rotation,
            title_after_second_rotation
    );
  }

  @Test
  public void testCheckSearchArticleInBackground() {
    String search_line = "Java";
    MainPageObject.waitForElementAndClick(
            By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
            "Cannot find 'Search Wikipedia' input",
            5
    );
    MainPageObject.waitForElementAndSendKeys(
            By.xpath("//*[contains(@text, 'Search…')]"),
            search_line,
            "Cannot find search input",
            5
    );
    String present_element_locator = "//*[@resource-id='org.wikipedia:id/page_list_item_container']" +
            "//*[@text='Object-oriented programming language']";
    MainPageObject.waitForElementPresent(
            By.xpath(present_element_locator),
            "Cannot find 'Search Wikipedia' input",
            5
    );

    driver.runAppInBackground(2);

    MainPageObject.waitForElementPresent(
            By.xpath(present_element_locator),
            "Cannot find article after returning from background",
            5
    );
  }

  @Test
  public void testPlaceHolderText() {
    MainPageObject.waitForElementPresent(
            By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
            "Cannot find search input-field",
            5
    );
    MainPageObject.assertElementHasText(
            By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
            "Search Wikipedia",
            "The search field doesn't exist correct text"
    );
  }
}