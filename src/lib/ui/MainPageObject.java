package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.Assert.*;

public class MainPageObject {

  protected AppiumDriver driver;

  public MainPageObject(AppiumDriver driver) {
    this.driver = driver;
  }

  public void assertElementHasText(By by, String expected_text, String error_message) {
    String actual_text = driver.findElement(by).getAttribute("text");
    assertEquals(
            error_message,
            expected_text,
            actual_text);
  }

  public void assertElementPresent(By by, String error_message) {
    int amount_of_elements = getAmountOfElements(by);
    if (amount_of_elements < 1) {
      String default_message = "An element " + by.toString() + " supposed to be present";
      throw new AssertionError(default_message + " " + error_message);
    }
  }

  public void assertElementNotPresent(By by, String error_message) {
    int amount_of_elements = getAmountOfElements(by);
    if (amount_of_elements > 0) {
      String default_message = "An element " + by.toString() + " supposed to be not present";
      throw new AssertionError(default_message + " " + error_message);
    }
  }

  public WebElement waitForElementPresent(By by, String error_message) {
    return waitForElementPresent(by, error_message, 5);
  }

  public WebElement waitForElementPresent(By by, String error_message, long timeoutInSeconds) {
    WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
    wait.withMessage(error_message + "\n");
    return wait.until(
            ExpectedConditions.presenceOfElementLocated(by));
  }

  public boolean waitForElementNotPresent(By by, String error_message, long timeoutInSeconds) {
    WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
    wait.withMessage(error_message + "\n");
    return wait.until(
            ExpectedConditions.invisibilityOfElementLocated(by)
    );
  }

  public List<WebElement> waitForNumberOfElementsToBeMoreThan(By by, int num, String error_message, long timeoutInSeconds) {
    WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
    wait.withMessage(error_message + "\n");
    return wait.until(
            ExpectedConditions.numberOfElementsToBeMoreThan(by, num)
    );
  }

  public List<WebElement> waitForPresenceOfAllElementsLocated(By by, String error_message, long timeoutInSeconds) {
    WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
    wait.withMessage(error_message + "\n");
    return wait.until(
            ExpectedConditions.presenceOfAllElementsLocatedBy(by)
    );
  }

  public WebElement waitForElementClickable(By by, String error_message, long timeoutInSeconds) {
    WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
    wait.withMessage(error_message);
    return wait.until(ExpectedConditions.elementToBeClickable(by));
  }

  public WebElement waitForElementLocated(By by, String error_message, long timeoutInSeconds) {
    WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
    wait.withMessage(error_message);
    return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
  }

  public WebElement waitForElementAndClick(By by, String error_message, long timeoutInSeconds) {
    WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
    element.click();
    return element;
  }

  public WebElement waitForElementAndSendKeys(By by, String value, String error_message, long timeoutInSeconds) {
    WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
    element.sendKeys(value);
    return element;
  }

  public WebElement waitForElementAndClear(By by, String error_message, long timeoutInSeconds) {
    WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
    element.clear();
    return element;
  }

  public WebElement waitForElementClickableAndClick(By by, String error_message, long timeoutInSeconds) {
    WebElement element = waitForElementClickable(by, error_message, timeoutInSeconds);
    element.click();
    return element;
  }

  public int getAmountOfElements(By by) {
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
            .press(PointOption.point(x, start_y))
            .waitAction(WaitOptions.waitOptions(Duration.ofMillis(timeOfSwipe)))
            .moveTo(PointOption.point(x, end_y))
            .release()
            .perform();
  }

  protected void swipeUpQuick() {
    swipeUp(200);
  }

  public void swipeUpToFindElement(By by, String error_message, int maxSwipes) {
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

  public void swipeElementToLeft(By by, String error_message) {
    WebElement element = waitForElementPresent(by, error_message, 20);
    int left_x = element.getLocation().getX();
    int right_x = left_x + element.getSize().getWidth();
    int upper_y = element.getLocation().getY();
    int lower_y = upper_y + element.getSize().getHeight();
    int middle_y = (upper_y + lower_y) / 2;
    TouchAction action = new TouchAction(driver);
    action
            .press(PointOption.point(right_x, middle_y))
            .waitAction(WaitOptions.waitOptions(Duration.ofMillis(300)))
            .moveTo(PointOption.point(left_x, middle_y))
            .release()
            .perform();
  }
}
