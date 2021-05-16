package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.NavigationUI;

public class iOSNavigationUI extends NavigationUI {

    static {
        MY_LISTS_BUTTON_LINK = "id:Saved";
        TAB_BAR_ELEMENT = "id:Tab Bar";
        LISTS_PAGE_TOOLBAR_TITLE = "xpath://XCUIElementTypeToolbar[@name='Toolbar']//XCUIElementTypeStaticText[@name='Saved']";
    }

    public iOSNavigationUI(AppiumDriver driver) {
        super(driver);
    }
}
