package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.MyListsPageObject;
import lib.ui.NavigationUI;
import lib.ui.SearchPageObject;
import org.junit.Test;

public class MyListsTests extends CoreTestCase {

  @Test
  public void testSaveFirstArticleToMyList() {
    SearchPageObject searchPageObject = new SearchPageObject(driver);
    searchPageObject.initSearchInput();
    searchPageObject.typeSearchLine("Java");
    searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

    ArticlePageObject articlePageObject = new ArticlePageObject(driver);
    articlePageObject.waitForTitleElement();
    String article_title = articlePageObject.getArticleTitle();
    String name_of_folder = "Learning programming";
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
    String name_of_folder = "Learning programming";
    SearchPageObject searchPageObject = new SearchPageObject(driver);
    searchPageObject.initSearchInput();
    String first_search_line = "Java";
    searchPageObject.typeSearchLine(first_search_line);
    searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

    ArticlePageObject articlePageObject = new ArticlePageObject(driver);
    articlePageObject.waitForTitleElement();
    String first_article_title = articlePageObject.getArticleTitle();
    articlePageObject.addArticleToMyList(name_of_folder);
    String second_article_title = "Programming language";
    searchPageObject.waitForClickSearchButton();
    String second_search_line = "JavaScript";
    searchPageObject.typeSearchLine(second_search_line);
    searchPageObject.clickByArticleWithSubstring(second_article_title);
    articlePageObject.addSecondArticleToMyList(name_of_folder);
    articlePageObject.closeArticle();

    NavigationUI navigationUI = new NavigationUI(driver);
    navigationUI.clickMyLists();

    MyListsPageObject myListsPageObject = new MyListsPageObject(driver);
    myListsPageObject.openFolderByName(name_of_folder);
    searchPageObject.waitForSearchResultsNotEmpty(1);
    myListsPageObject.swipeByArticleToDelete(first_article_title);
    searchPageObject.clickByArticleWithSubstring(second_article_title.toLowerCase());

    String actual_second_search_line = articlePageObject.getArticleTitle();
    assertEquals("Actual title not equals expected", second_search_line, actual_second_search_line);
  }
}
