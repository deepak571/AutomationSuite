package Base;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import pages.FlightStatusPage;
import utilities.BrowserHelper;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestBase extends Base {

    protected FlightStatusPage flightStatusPage;


    @BeforeMethod(groups = "setup")
    public void setUp(ITestResult result) throws MalformedURLException {
        webDriver = initDriver();
        browserHelper = new BrowserHelper(webDriver);
        flightStatusPage= new FlightStatusPage(webDriver);
    }

    @AfterMethod(groups = "teardown")
    public void teardown(ITestResult result) throws IOException {
        if (result.getStatus() == ITestResult.FAILURE) {
            logger.info("Test Case FAILED");
                String methodName = result.getMethod().getMethodName();
                this.takeScreenshot(methodName, webDriver);
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            logger.info("Test Case PASSED");
        } else if (result.getStatus() == ITestResult.SKIP) {
            logger.info("Test Case SKIPPED");
        }
        logger.info("Closing the browser");
        webDriver.manage().deleteAllCookies();
        webDriver.quit();
    }

    private void takeScreenshot(String testCaseName, WebDriver webDriver) throws IOException {
        String pathname;

        File scrFile = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);

        String shopFolderName = "screenshots";
        String dateFail = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String timeFail = new SimpleDateFormat("HHmmss").format(new Date());

        String folderName = System.getProperty(USER_DIR) + File.separator + "screenshots" + File.separator + dateFail + File.separator + shopFolderName;
        File folder = new File(folderName);

        if (!folder.exists()) {
            folder.mkdir();
        }
        pathname = folderName + File.separator + testCaseName + "_" + timeFail + ".jpg";

        try {
            FileUtils.copyFile(scrFile, new File(pathname));

        } catch (IOException e) {
            logger.info("Error encountered in taking screenshot");
            logger.trace(e);
        }

    }

}
