package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.ArticlePageObject;
import lib.ui.MyListsPageObject;
import lib.ui.NavigationUI;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListsPageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

public class MyListsTests extends CoreTestCase {

    private static final String name_of_folder = "Learning programming";

    @Test
    public void testSaveFirstArticleToMyList() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        articlePageObject.waitForTitleElement();
        String article_title = articlePageObject.getArticleTitle();
        if (Platform.getInstance().isAndroid()) {
            articlePageObject.addArticleToMyList(name_of_folder);
            articlePageObject.closeArticle();
        } else {
            articlePageObject.addArticleToMySavedList();
            articlePageObject.closeArticleAndReturnToTheMainPage();
        }

        NavigationUI navigationUI = NavigationUIFactory.get(driver);
        navigationUI.clickMyLists();

        MyListsPageObject myListsPageObject = MyListsPageObjectFactory.get(driver);
        if (Platform.getInstance().isAndroid()) myListsPageObject.openFolderByName(name_of_folder);
        else myListsPageObject.closeSyncSavedArticlesPopUpWindow();

        myListsPageObject.swipeByArticleToDelete(article_title);
    }

    @Test
    public void testSaveTwoArticlesToMyListAndDeleteAny() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        String first_search_line = "Java";
        searchPageObject.typeSearchLine(first_search_line);
        searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
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

        NavigationUI navigationUI = NavigationUIFactory.get(driver);
        navigationUI.clickMyLists();

        MyListsPageObject myListsPageObject = MyListsPageObjectFactory.get(driver);
        myListsPageObject.openFolderByName(name_of_folder);
        searchPageObject.waitForSearchResultsNotEmpty(1);
        myListsPageObject.swipeByArticleToDelete(first_article_title);
        searchPageObject.clickByArticleWithSubstring(second_article_title.toLowerCase());

        String actual_second_search_line = articlePageObject.getArticleTitle();
        assertEquals("Actual title not equals expected", second_search_line, actual_second_search_line);
    }
}
