package Base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import utilities.BrowserHelper;

public class PageBase extends Base {

    public PageBase(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
        this.browserHelper = new BrowserHelper(webDriver);

        browserHelper.waitForPageToLoad();
    }
}
