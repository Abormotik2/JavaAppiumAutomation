package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SearchTests extends CoreTestCase {

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
  public void testAmountOfNotEmptySearch() {
    SearchPageObject searchPageObject = new SearchPageObject(driver);
    searchPageObject.initSearchInput();
    String search_line = "Linkin Park Diskography";
    searchPageObject.typeSearchLine(search_line);
    int amount_of_search_results = searchPageObject.getAmountOfFoundArticles();
    assertTrue("We found too few results!",
            amount_of_search_results > 0);
  }

  @Test
  public void testAmountOfEmptySearch() {
    SearchPageObject searchPageObject = new SearchPageObject(driver);
    searchPageObject.initSearchInput();
    String search_line = "sadgftyzf";
    searchPageObject.typeSearchLine(search_line);
    searchPageObject.waitForEmptyResultsLabel();
    searchPageObject.assertThereIsNoResultOfSearch();
  }

  @Test
  public void testSearchResultsAndCancel() {
    SearchPageObject searchPageObject = new SearchPageObject(driver);
    searchPageObject.initSearchInput();
    searchPageObject.typeSearchLine("Java");
    searchPageObject.waitForSearchResultsNotEmpty(1);
    searchPageObject.clickCancelSearch();
    searchPageObject.waitForSearchResultsNotDisplayed();
  }

  @Test
  public void testSearchForEachResults() {
    SearchPageObject searchPageObject = new SearchPageObject(driver);
    String search_line = "Java";
    searchPageObject.initSearchInput();
    searchPageObject.typeSearchLine(search_line);

    ArticlePageObject articlePageObject = new ArticlePageObject(driver);
    List<WebElement> articleTitles = articlePageObject.waitForAllTitlesElements();
    articleTitles.stream()
            .map(webElement -> webElement.getAttribute("text").toLowerCase())
            .forEachOrdered(articleTitle -> assertTrue(
                    "One or more titles do not have expected search text",
                    articleTitle.contains(search_line.toLowerCase())));
  }

  @Test
  public void testSearchForPlaceHolderText() {
    SearchPageObject searchPageObject = new SearchPageObject(driver);
    searchPageObject.assertSearchPlaceHolderText("Search Wikipedia");
  }
}
