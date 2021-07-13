package Base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import utilities.BrowserHelper;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class Base {

    public static final Logger logger = Logger.getLogger(Base.class.getName());
    protected static final String USER_DIR = "user.dir";

    protected WebDriver webDriver;
    protected BrowserHelper browserHelper;

    protected static Properties properties = null;
    public static final String BROWSER;

    static {
        properties = loadProperties();
        BROWSER = getBrowser();
    }


    public WebDriver initDriver() throws MalformedURLException {
        System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
        WebDriverManager.chromedriver().setup();
        webDriver = new ChromeDriver();

        webDriver.get(properties.getProperty("url"));
        webDriver.manage().window().maximize();
        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        webDriver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);

        return webDriver;
    }


    private static Properties loadProperties() {
        Properties prop = new Properties();

        String configFileName = "config.properties";
        String configDirectory = "/src/main/java/config/";

        String fileName = System.getProperty(USER_DIR) + configDirectory + configFileName;

        try (FileInputStream ip = new FileInputStream(fileName)) {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            URL urlResource = loader.getResource("log4j.properties");
            PropertyConfigurator.configure(urlResource);

            prop.load(ip);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return prop;
    }

    public static String getBrowser() {
        String browser = properties.getProperty("browser");

        if (isBlank(browser)) {
            throw new IllegalArgumentException("No browser is defined in config file");
        }

        return browser;
    }
}
