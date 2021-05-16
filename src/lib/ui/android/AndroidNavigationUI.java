package lib.ui.android;

import io.appium.java_client.AppiumDriver;
import lib.ui.NavigationUI;

public class AndroidNavigationUI extends NavigationUI {

    static {
        MY_LISTS_BUTTON_LINK = "xpath://android.widget.FrameLayout[@content-desc='My lists']";
        TAB_BAR_ELEMENT = "id:org.wikipedia:id/fragment_main_nav_tab_layout";
        LISTS_PAGE_TOOLBAR_TITLE = "xpath://*[@resource-id='org.wikipedia:id/single_fragment_toolbar']/*[@text='My lists']";
    }

    public AndroidNavigationUI(AppiumDriver driver) {
        super(driver);
    }
}
