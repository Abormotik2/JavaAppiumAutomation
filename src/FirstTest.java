import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.net.URL;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FirstTest {

  private AndroidDriver<?> driver;

  @Before
  public void SetUp() throws Exception {
    DesiredCapabilities capabilities = new DesiredCapabilities();
    capabilities.setCapability("platformName", "Android");
    capabilities.setCapability("deviceName", "AndroidTestDevice");
    capabilities.setCapability("platformVersion", "8.0");
    capabilities.setCapability("automationName", "Appium");
    capabilities.setCapability("appPackage", "org.wikipedia");
    capabilities.setCapability("appActivity", ".main.MainActivity");
    capabilities.setCapability("app", new File("./apks/org.wikipedia.apk").getCanonicalPath());
    driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
  }

  @After
  public void TearDown() {
    driver.quit();
  }

  @Test
  public void testWikiSearchResults() {
    final String searchValue = "Java";
    checkSearchInputSteps(searchValue);
    waitForElementPresent(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']" +
                    "//*[@text='Object-oriented programming language']"),
            "Cannot find 'Object-oriented programming language' topic searching by 'Java'",
            15
    );
  }

  @Test
  public void testCancelSearch() {
    final String searchValue = "Java";
    checkSearchInputSteps(searchValue);
    waitForElementAndClear(By.id("org.wikipedia:id/search_src_text"),
            "Cannot find search field",
            5
    );
    waitForElementAndClick(
            By.id("org.wikipedia:id/search_close_btn"),
            "Cannot find X to cancel search",
            5
    );
    waitForElementNotPresent(By.id("org.wikipedia:id/search_close_btn"),
            "X is still present on the page",
            5
    );
  }

  @Test
  public void testSearchResultsAndCancel() {
    final String searchValue = "Java";
    checkSearchInputSteps(searchValue);
    waitForNumberOfElementsToBeMoreThan(
            By.id("org.wikipedia:id/page_list_item_container"),
            1,
            "Count of articles less than expected",
            5
    );
    waitForElementAndClick(
            By.id("org.wikipedia:id/search_close_btn"),
            "Cannot find X to cancel search",
            5
    );
    waitForElementNotPresent(By.id("org.wikipedia:id/page_list_item_container"),
            "The search results is still displayed",
            5
    );
  }

  @Test
  public void testSearchForEachResults() {
    final String searchValue = "Java";
    checkSearchInputSteps(searchValue);
    List<WebElement> articleTitles = waitForPresenceOfAllElementsLocated(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']" +
                    "//*[@resource-id='org.wikipedia:id/page_list_item_title']"),
            "Cannot find any article title",
            15);

    articleTitles.stream()
            .map(webElement -> webElement.getAttribute("text").toLowerCase())
            .forEachOrdered(articleTitle -> assertTrue(
                    "One or more titles do not have expected search text",
                    articleTitle.contains(searchValue.toLowerCase())));
  }

  @Test
  public void testCompareArticleTitle() {
    final String searchValue = "Java";
    checkSearchInputSteps(searchValue);
    waitForElementAndClick(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
            "Cannot find 'Search Wikipedia' input",
            5
    );
    WebElement title_element = waitForElementPresent(
            By.id("org.wikipedia:id/view_page_title_text"),
            "Cannot find article title",
            15
    );
    String article_title = title_element.getAttribute("text");
    assertEquals(
            "We see unexpected title",
            "Java (programming language)",
            article_title);
  }

  @Test
  public void testSwipeArticle() {
    final String searchValue = "Appium";
    checkSearchInputSteps(searchValue);
    waitForElementAndClick(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='Appium']"),
            "Cannot find 'Appium' article in search",
            5
    );
    waitForElementPresent(
            By.id("org.wikipedia:id/view_page_title_text"),
            "Cannot find article title",
            15
    );
    swipeUpToFindElement(
            By.xpath("//*[@text='View page in browser']"),
            "Cannot find the end of the article\"",
            20
    );
  }

  @Test
  public void testSaveFirstArticleToMyList() {
    final String searchValue = "Java";
    checkSearchInputSteps(searchValue);
    waitForElementAndClick(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']" +
                    "//*[@text='Object-oriented programming language']"),
            "Cannot find 'Search Wikipedia' input",
            5
    );
    waitForElementPresent(
            By.id("org.wikipedia:id/view_page_title_text"),
            "Cannot find article title",
            15
    );
    waitForElementAndClick(
            By.xpath("//android.widget.ImageView[@content-desc='More options']"),
            "Cannot find button to open article options",
            5
    );
    waitForElementAndClick(
            By.xpath("//*[@text='Add to reading list']"),
            "Cannot find option to add article to reading list",
            5
    );
    waitForElementAndClick(
            By.id("org.wikipedia:id/onboarding_button"),
            "Cannot find 'Got it' tip overlay",
            5
    );
    waitForElementAndClear(
            By.id("org.wikipedia:id/text_input"),
            "Cannot find input to set name of articles folder",
            5
    );
    String name_of_folder = "Learning programming";
    waitForElementAndSendKeys(
            By.id("org.wikipedia:id/text_input"),
            name_of_folder,
            "Cannot put text into articles folder input",
            5
    );
    waitForElementAndClick(
            By.xpath("//*[@text='OK']"),
            "Cannot press OK button",
            5
    );
    waitForElementAndClick(
            By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
            "Cannot close article, cannot find X link",
            5
    );
    waitForElementAndClick(
            By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
            "Cannot find navigation button to My List",
            5
    );
    waitForElementAndClick(
            By.xpath("//*[@text='" + name_of_folder + "']"),
            "Cannot find created folder",
            5
    );
    waitForNumberOfElementsToBeMoreThan(
            By.id("org.wikipedia:id/page_list_item_container"),
            0,
            "Count of articles less than expected",
            5
    );
    final String article_first_xpath = "//*[@text='Java (programming language)']";
    swipeElementToLeft(
            By.xpath(article_first_xpath),
            "Cannot find saved article"
    );
    waitForElementNotPresent(
            By.xpath(article_first_xpath),
            "Cannot delete saved article",
            15
    );
  }

  @Test
  public void testSaveTwoArticlesToMyListAndDeleteAny() {
    final String search_first_value = "Java";
    checkSearchInputSteps(search_first_value);
    waitForElementAndClick(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']" +
                    "//*[@text='Object-oriented programming language']"),
            "Cannot find 'Search Wikipedia' input",
            5
    );
    waitForElementPresent(
            By.id("org.wikipedia:id/view_page_title_text"),
            "Cannot find article title",
            15
    );
    waitForElementAndClick(
            By.xpath("//android.widget.ImageView[@content-desc='More options']"),
            "Cannot find button to open article options",
            5
    );
    waitForElementAndClick(
            By.xpath("//*[@text='Add to reading list']"),
            "Cannot find option to add article to reading list",
            5
    );
    waitForElementAndClick(
            By.id("org.wikipedia:id/onboarding_button"),
            "Cannot find 'Got it' tip overlay",
            5
    );
    waitForElementAndClear(
            By.id("org.wikipedia:id/text_input"),
            "Cannot find input to set name of articles folder",
            5
    );
    String name_of_folder = "Learning programming";
    waitForElementAndSendKeys(
            By.id("org.wikipedia:id/text_input"),
            name_of_folder,
            "Cannot put text into articles folder input",
            5
    );
    waitForElementAndClick(
            By.xpath("//*[@text='OK']"),
            "Cannot press OK button",
            5
    );
    waitForElementAndClick(
            By.xpath("//*[@resource-id='org.wikipedia:id/menu_page_search']"),
            "Cannot find Search button",
            5
    );
    final String expected_second_value = "JavaScript";
    final String article_second_xpath = "//*[@text='Programming language']";
    waitForElementAndSendKeys(
            By.xpath("//*[contains(@text, 'Search…')]"),
            expected_second_value,
            "Cannot find search input",
            5
    );
    waitForElementAndClick(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']" +
                    article_second_xpath),
            "Cannot find 'Search Wikipedia' input",
            5
    );
    waitForElementAndClick(
            By.xpath("//android.support.v7.widget.LinearLayoutCompat//android.widget.ImageView[@content-desc='More options']"),
            "Cannot find button to open article options",
            5
    );
    waitForElementPresent(
            By.xpath("//*[@text='Add to reading list']"),
            "Cannot find option to add article to reading list",
            10
    );
    waitForElementAndClick(
            By.xpath("//*[@text='Add to reading list']"),
            "Cannot find option to add article to reading list",
            10
    );
    waitForElementAndClick(
            By.xpath("//android.widget.TextView[@text='" + name_of_folder + "']"),
            "Cannot find created folder",
            5
    );
    waitForElementAndClick(
            By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
            "Cannot close article, cannot find X link",
            5
    );
    waitForElementAndClick(
            By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
            "Cannot find navigation button to My List",
            5
    );
    waitForElementPresent(
            By.xpath("//*[@text='" + name_of_folder + "']"),
            "Cannot find created folder",
            5
    );
    waitForElementAndClick(
            By.xpath("//*[@text='" + name_of_folder + "']"),
            "Cannot find created folder",
            5
    );
    waitForNumberOfElementsToBeMoreThan(
            By.id("org.wikipedia:id/page_list_item_container"),
            1,
            "Count of articles less than expected",
            15
    );
    final String article_first_xpath = "//*[@text='Java (programming language)']";
    swipeElementToLeft(
            By.xpath(article_first_xpath),
            "Cannot find saved article"
    );
    waitForElementAndClick(
            By.xpath(article_second_xpath.toLowerCase()),
            "Cannot find second added article",
            10
    );
    String actual_second_value = waitForElementAndGetAttribute(
            By.id("org.wikipedia:id/view_page_title_text"),
            "text",
            "Cannot find title of article",
            15
    );
    assertEquals("Actual title not equals expected", expected_second_value, actual_second_value);
  }

  @Test
  public void testArticleTitlePresence() {
    final String search_value = "Reflection";
    final String title_xpath = "//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='" + search_value + "']";
    checkSearchInputSteps(search_value);
    waitForNumberOfElementsToBeMoreThan(
            By.xpath(title_xpath),
            0,
            "Count of articles less than expected",
            15
    );
   waitForElementClickableAndClick(
           By.xpath(title_xpath),
           "The element not clickable",
           10
   );
    assertElementPresent(
            By.id("org.wikipedia:id/view_page_title_text"),
            "Article not found"
    );
  }

  @Test
  public void testAmountOfNotEmptySearch() {
    String search_line = "Linkin Park Diskography";
    checkSearchInputSteps(search_line);

    String search_result_locator = "//*[@resource-id='org.wikipedia:id/search_results_list']" +
            "/*[@resource-id='org.wikipedia:id/page_list_item_container']";

    waitForElementPresent(
            By.xpath(search_result_locator),
            "Cannot find anything by the request " + search_line,
            15
    );
    int amount_of_search_results = getAmountOfElements(
            By.xpath(search_result_locator)
    );
    assertTrue("We found too few results!",
            amount_of_search_results > 0);
  }

  @Test
  public void testAmountOfEmptySearch() {
    String search_line = "sadgftyzf";
    checkSearchInputSteps(search_line);
    String search_result_locator = "//*[@resource-id='org.wikipedia:id/search_results_list']" +
            "/*[@resource-id='org.wikipedia:id/page_list_item_container']";
    String empty_result_label = "//*[@text='No results found']";
    waitForElementPresent(
            By.xpath(empty_result_label),
            "Cannot find empty result label by the request " + search_line,
            15
    );
    assertElementNotPresent(
            By.xpath(search_result_locator),
            "We found some results by request " + search_line
    );
  }

  @Test
  public void testChangeScreenOrientationOnSearchResults() {
    String search_line = "Java";
    checkSearchInputSteps(search_line);
    waitForElementAndClick(
            By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']" +
                    "//*[@text='Object-oriented programming language']"),
            "Cannot find 'Object-oriented programming language' topic searching by " + search_line,
            5
    );
    String title_before_rotation = waitForElementAndGetAttribute(
            By.id("org.wikipedia:id/view_page_title_text"),
            "text",
            "Cannot find title of article",
            15
    );
    driver.rotate(ScreenOrientation.LANDSCAPE);

    String title_after_rotation = waitForElementAndGetAttribute(
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

    String title_after_second_rotation = waitForElementAndGetAttribute(
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
    checkSearchInputSteps(search_line);
    String present_element_locator = "//*[@resource-id='org.wikipedia:id/page_list_item_container']" +
            "//*[@text='Object-oriented programming language']";
    waitForElementPresent(
            By.xpath(present_element_locator),
            "Cannot find 'Search Wikipedia' input",
            5
    );

    driver.runAppInBackground(2);

    waitForElementPresent(
            By.xpath(present_element_locator),
            "Cannot find article after returning from background",
            5
    );
  }

  @Test
  public void testPlaceHolderText() {
    waitForElementPresent(
            By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
            "Cannot find search input-field",
            5
    );
    assertElementHasText(
            By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
            "Search Wikipedia",
            "The search field doesn't exist correct text"
    );
  }

  private void checkSearchInputSteps(String value) {
    waitForElementAndClick(
            By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
            "Cannot find 'Search Wikipedia' input",
            5
    );
    waitForElementAndSendKeys(
            By.xpath("//*[contains(@text, 'Search…')]"),
            value,
            "Cannot find search input",
            5
    );
  }

  private void assertElementHasText(By by, String expected_text, String error_message) {
    String actual_text = driver.findElement(by).getAttribute("text");
    assertEquals(
            error_message,
            expected_text,
            actual_text);
  }

  private boolean assertElementPresent(By by, String errorMessage) {
    if (getAmountOfElements(by) > 0) {
      return true;
    } else {
      throw new AssertionError(errorMessage);
    }
  }

  private void assertElementNotPresent(By by, String error_message) {
    int amount_of_elements = getAmountOfElements(by);
    if (amount_of_elements > 0) {
      String default_message = "An element " + by.toString() + " supposed to be not present";
      throw new AssertionError(default_message + " " + error_message);
    }
  }

  private WebElement waitForElementPresent(By by, String error_message) {
    return waitForElementPresent(by, error_message, 5);
  }

  private WebElement waitForElementPresent(By by, String error_message, long timeoutInSeconds) {
    WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
    wait.withMessage(error_message + "\n");
    return wait.until(
            ExpectedConditions.presenceOfElementLocated(by));
  }

  private boolean waitForElementNotPresent(By by, String error_message, long timeoutInSeconds) {
    WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
    wait.withMessage(error_message + "\n");
    return wait.until(
            ExpectedConditions.invisibilityOfElementLocated(by)
    );
  }

  private List<WebElement> waitForNumberOfElementsToBeMoreThan(By by, int num, String error_message, long timeoutInSeconds) {
    WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
    wait.withMessage(error_message + "\n");
    return wait.until(
            ExpectedConditions.numberOfElementsToBeMoreThan(by, num)
    );
  }

  private List<WebElement> waitForPresenceOfAllElementsLocated(By by, String error_message, long timeoutInSeconds) {
    WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
    wait.withMessage(error_message + "\n");
    return wait.until(
            ExpectedConditions.presenceOfAllElementsLocatedBy(by)
    );
  }

  private WebElement waitForElementClickable(By by, String error_message, long timeoutInSeconds) {
    WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
    wait.withMessage(error_message);
    return wait.until(ExpectedConditions.elementToBeClickable(by));
  }

  private WebElement waitForElementAndClick(By by, String error_message, long timeoutInSeconds) {
    WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
    element.click();
    return element;
  }

  private WebElement waitForElementAndSendKeys(By by, String value, String error_message, long timeoutInSeconds) {
    WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
    element.sendKeys(value);
    return element;
  }

  private WebElement waitForElementAndClear(By by, String error_message, long timeoutInSeconds) {
    WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
    element.clear();
    return element;
  }

  private String waitForElementAndGetAttribute(By by, String attribute, String error_message, long timeoutInSeconds) {
    WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
    return element.getAttribute(attribute);
  }

  private WebElement waitForElementClickableAndClick(By by, String error_message, long timeoutInSeconds) {
    WebElement element = waitForElementClickable(by, error_message, timeoutInSeconds);
    element.click();
    return element;
  }

  private int getAmountOfElements(By by) {
    List elements = driver.findElements(by);
    return elements.size();
  }

  protected void swipeUp(int timeOfSwipe) {
    TouchAction action = new TouchAction(driver);
    Dimension size = driver.manage().window().getSize();
    int x = size.width / 2;
    int start_y = (int) (size.height * 0.8);
    int end_y = (int) (size.height * 0.2);
    action
            .press(x, start_y)
            .waitAction(timeOfSwipe)
            .moveTo(x, end_y)
            .release()
            .perform();
  }

  protected void swipeUpQuick() {
    swipeUp(200);
  }

  protected void swipeUpToFindElement(By by, String error_message, int maxSwipes) {
    int already_swiped = 0;
    while (driver.findElements(by).size() == 0) {
      if (already_swiped > maxSwipes) {
        waitForElementPresent(by, "Cannot find element by swiping up. \n" + error_message, 0);
        return;
      }
      swipeUpQuick();
      ++already_swiped;
    }
  }

  protected void swipeElementToLeft(By by, String error_message) {
    WebElement element = waitForElementPresent(by, error_message, 20);
    int left_x = element.getLocation().getX();
    int right_x = left_x + element.getSize().getWidth();
    int upper_y = element.getLocation().getY();
    int lower_y = upper_y + element.getSize().getHeight();
    int middle_y = (upper_y + lower_y) / 2;
    TouchAction action = new TouchAction(driver);
    action
            .press(right_x, middle_y)
            .waitAction(300)
            .moveTo(left_x, middle_y)
            .release()
            .perform();
  }
}