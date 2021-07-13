package utilities;

import Base.Base;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class BrowserHelper extends Base {

    public static final Logger logger = Logger.getLogger(BrowserHelper.class.getName());

    public BrowserHelper(WebDriver webDriver) {
        this.webDriver = webDriver;
        logger.debug("wait_helper : " + this.webDriver.hashCode());
    }

    public WebElement waitForElementClickable(WebElement element) {
        WebDriverWait wait = new WebDriverWait(webDriver, 30);
        wait.ignoring(ElementClickInterceptedException.class);
        wait.ignoring(StaleElementReferenceException.class);
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }


    public void waitForPageToLoad() {
        WebDriverWait wait = new WebDriverWait(webDriver, 30);

        wait.until(wdriver -> ((JavascriptExecutor) webDriver).executeScript(
                "return document.readyState"
        ).equals("complete"));
    }

    public boolean waitForAjax() {
        WebDriverWait wait = new WebDriverWait(webDriver, 30);

        ExpectedCondition<Boolean> jQueryHasLoaded = driver -> {
            try {
                return ((Long) ((JavascriptExecutor) webDriver).executeScript("return jQuery.active") == 0);
            } catch (Exception e) {
                // no jQuery present
                return true;
            }
        };

        ExpectedCondition<Boolean> javascriptHasLoaded = driver -> ((JavascriptExecutor) webDriver)
                .executeScript("return document.readyState")
                .toString().equals("complete");

        return wait.until(jQueryHasLoaded) && wait.until(javascriptHasLoaded);
    }

    public boolean isDisplayed(WebElement element) {
        try {
            element.isDisplayed();
            logger.info("element is displayed.." + element);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void scrollIntoViewIfNeeded(WebElement element) {
            executeScript("arguments[0].scrollIntoViewIfNeeded(true)", element);
            logger.info("scrolled to: " + element);
    }

    private void executeScript(String script, Object... args) {
        JavascriptExecutor exe = (JavascriptExecutor) webDriver;
        exe.executeScript(script, args);
    }

    public void smallWait(final long delayInMilliseconds) {
        try {
            Thread.sleep(delayInMilliseconds);
        } catch (InterruptedException e) {
            logger.warn("Wait a bit method was interrupted.", e);

            Thread.currentThread().interrupt();
        }
    }

    public void waitForElementVisible(WebElement locator) {
        WebDriverWait wait = getWait(30, 5);
        wait.until(ExpectedConditions.visibilityOf(locator));
    }

    private WebDriverWait getWait(int timeOutInSeconds, int pollingEveryInMiliSec) {
        WebDriverWait wait = new WebDriverWait(webDriver, timeOutInSeconds);
        wait.pollingEvery(pollingEveryInMiliSec, TimeUnit.MILLISECONDS);
        wait.ignoring(NoSuchElementException.class);
        wait.ignoring(ElementNotVisibleException.class);
        wait.ignoring(StaleElementReferenceException.class);
        wait.ignoring(NoSuchFrameException.class);
        wait.ignoring(ElementClickInterceptedException.class);
        return wait;
    }


}
