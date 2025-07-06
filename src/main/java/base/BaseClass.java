package base;

import actionDriver.ActionDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class BaseClass {

    private static final Logger log = LoggerFactory.getLogger(BaseClass.class);
    protected static Properties properties;
    protected static WebDriver webDriver;
    private static ActionDriver actionDriver;

    //Getter method for webDriver - singleton
    public static WebDriver getWebDriver() {
        if (webDriver == null) {
            log.info("Web Driver is not Initialized");
            throw new IllegalStateException("Web Driver is not Initialized");
        }
        return webDriver;
    }

    //Getter method for actionDriver - singleton
    public static ActionDriver getActionDriver() {
        if (actionDriver == null) {
            log.info("Action Driver is not Initialized");
            throw new IllegalStateException("Action Driver is not Initialized");
        }
        return actionDriver;
    }

    //Setter method for webDriver
    public void setWebDriver(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    //Getter method for config.properties
    public static Properties getConfigProp() {
        return properties;
    }

    @BeforeSuite
    public void loadConfig() throws IOException {
        //Load the configuration file
        properties = new Properties();
        FileInputStream fileInputStream = new FileInputStream("src/main/resources/config.properties");
        properties.load(fileInputStream);
    }

    public void initializeBrowser() {
        //Initialize the browser
        String browser = properties.getProperty("browser");
        if (browser.equalsIgnoreCase("chrome")) {
            webDriver = new ChromeDriver();
        } else if (browser.equalsIgnoreCase("firefox")) {
            webDriver = new FirefoxDriver();
        } else if (browser.equalsIgnoreCase("edge")) {
            webDriver = new EdgeDriver();
        } else {
            throw new IllegalArgumentException("Browser is not supported");
        }
    }

    public void browserConfiguration() {
        //Setup implicit wait
        int implicitWait = Integer.parseInt(properties.getProperty("implicitWait"));
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));

        //Maximize browser
        webDriver.manage().window().maximize();

        //Navigate to the URL
        try {
            webDriver.get(properties.getProperty("url"));
        } catch (Exception e) {
            log.error("Failed to navigate to the URL: " + e.getMessage());
        }
    }

    @BeforeMethod
    public void setup() {
        log.info("Browser setup method called for" + this.getClass().getSimpleName());
        initializeBrowser();
        browserConfiguration();
        setStaticWait(2);

        //Add singleton pattern for action driver - changes are in login page
        if (actionDriver == null) {
            actionDriver = new ActionDriver(webDriver);
            log.info("Action Driver Instance Created");
        }
    }

    @AfterMethod
    public void tearDown() {
        if (webDriver != null) {
            try {
                webDriver.quit();
            } catch (Exception e) {
                log.error("Failed to quit the browser: " + e.getMessage());
            }
        }
        webDriver = null;
        actionDriver = null;
    }

    public void setStaticWait(int seconds) {
        LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(seconds));
    }
}
