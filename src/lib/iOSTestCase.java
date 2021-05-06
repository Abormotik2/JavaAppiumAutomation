package lib;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import junit.framework.TestCase;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.URL;
import java.time.Duration;

public class iOSTestCase extends TestCase {

  protected AppiumDriver driver;
  private final static String AppiumURL = "http://127.0.0.1:4723/wd/hub";

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    DesiredCapabilities capabilities = new DesiredCapabilities();
    capabilities.setCapability("platformName", "iOS");
    capabilities.setCapability("deviceName", "iPhone SE");
    capabilities.setCapability("platformVersion", "14.5");
    capabilities.setCapability("udid", "B4CDA41B-FD92-448F-AE94-253C638D07E5");
    capabilities.setCapability("app", new File("/Users/vladimirkv/IdeaProjects/JavaAppiumAutomation/apks/Wikipedia.app").getCanonicalPath());
    driver = new IOSDriver(new URL(AppiumURL), capabilities);
    this.rotateScreenPortrait();
  }

  @Override
  protected void tearDown() throws Exception {
    driver.quit();
    super.tearDown();
  }

  protected void rotateScreenPortrait() {
    driver.rotate(ScreenOrientation.PORTRAIT);
  }

  protected void rotateScreenLandscape() {
    driver.rotate(ScreenOrientation.LANDSCAPE);
  }

  protected void backgroundApp(int seconds) {
    driver.runAppInBackground(Duration.ofSeconds(seconds));
  }
}
