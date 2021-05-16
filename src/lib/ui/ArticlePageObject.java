package lib.ui;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import org.openqa.selenium.WebElement;

import java.util.List;

abstract public class ArticlePageObject extends MainPageObject {

    protected static String
            TITLE,
            LIST_TITLES,
            FOOTER_ELEMENT,
            OPTIONS_BUTTON,
            OPTIONS_MENU,
            OPTIONS_ADD_TO_MY_LIST_BUTTON,
            ADD_TO_MY_LIST_OVERLAY,
            MY_LIST_NAME_INPUT,
            MY_LIST_OK_BUTTON,
            CLOSE_ARTICLE_BUTTON,
            BACK_BUTTON,
            CREATED_FOLDER;

    public ArticlePageObject(AppiumDriver driver) {
        super(driver);
    }

    private static String getCreatedFolderTitle(String name_of_folder) {
        return CREATED_FOLDER.replace("{NAME_OF_FOLDER}", name_of_folder);
    }

    public WebElement waitForTitleElement() {
        return this.waitForElementPresent(
                TITLE,
                "Cannot find article title on page!",
                15);
    }

    public List<WebElement> waitForAllTitlesElements() {
        return this.waitForPresenceOfAllElementsLocated(
                LIST_TITLES,
                "Cannot find any article title",
                15);
    }

    public String getArticleTitle() {
        WebElement title_element = waitForTitleElement();
        if (Platform.getInstance().isAndroid()) return title_element.getAttribute("text");
        else return title_element.getAttribute("name");
    }

    public void swipeToFooter() {
        if (Platform.getInstance().isAndroid()) {
            this.swipeUpToFindElement(
                    FOOTER_ELEMENT,
                    "Cannot find the end of article",
                    20
            );
        } else {
            this.swipeUpTillElementAppear(
                    FOOTER_ELEMENT,
                    "Cannot find the end of article",
                    20);
        }
    }

    public void addArticleToMyList(String name_of_folder) {
        this.waitForElementAndClick(
                OPTIONS_BUTTON,
                "Cannot find button to open article options",
                5
        );
        this.waitForElementLocated(
                OPTIONS_MENU,
                "Cannot find context menu",
                15);
        this.waitForElementAndClick(
                OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "Cannot find option to add article to reading list",
                5
        );
        this.waitForElementAndClick(
                ADD_TO_MY_LIST_OVERLAY,
                "Cannot find 'Got it' tip overlay",
                5
        );
        this.waitForElementAndClear(
                MY_LIST_NAME_INPUT,
                "Cannot find input to set name of articles folder",
                5
        );
        this.waitForElementAndSendKeys(
                MY_LIST_NAME_INPUT,
                name_of_folder,
                "Cannot put text into articles folder input",
                5
        );
        this.waitForElementAndClick(
                MY_LIST_OK_BUTTON,
                "Cannot press OK button",
                5
        );
    }

    public void addSecondArticleToMyList(String name_of_folder) {
        this.waitForElementAndClick(
                OPTIONS_BUTTON,
                "Cannot find button to open article options",
                5
        );
        this.waitForElementLocated(
                OPTIONS_MENU,
                "Cannot find context menu",
                15);
        this.waitForElementAndClick(
                OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "Cannot find option to add article to reading list",
                5
        );
        String created_folder = getCreatedFolderTitle(name_of_folder);
        this.waitForElementAndClick(
                created_folder,
                "Cannot find created folder",
                5
        );
    }

    public void addArticleToMySavedList() {
        this.waitForElementClickableAndClick(
                OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "Cannot find option to add article to reading list",
                5);
    }

    public void closeArticle() {
        this.waitForElementAndClick(
                CLOSE_ARTICLE_BUTTON,
                "Cannot close article, cannot find X link",
                5
        );
    }

    public void assertArticleTitlePresence() {
        this.assertElementPresent(
                TITLE,
                "Article not found"
        );
    }

    public void closeArticleAndReturnToTheMainPage() {
        this.waitForElementClickableAndClick(BACK_BUTTON, "Кнопка возврата на главную страницу не найдена или недоступна для действий.", 5);
    }
}
