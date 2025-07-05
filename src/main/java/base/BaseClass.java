package base;

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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Time;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class BaseClass {

    public WebDriver getWebDriver() {
        return webDriver;
    }

    public void setWebDriver(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    private static final Logger log = LoggerFactory.getLogger(BaseClass.class);
    protected static Properties properties;
    protected WebDriver webDriver;

    @BeforeSuite
    public void loadConfig() throws IOException {
        //Load the configuration file
        properties = new Properties();
        FileInputStream fileInputStream = new FileInputStream("src/main/resources/config.properties");
        properties.load(fileInputStream);
    }

    public void setupBrowser(){
        //Initialize the browser
        String browser = properties.getProperty("browser");
        if (browser.equalsIgnoreCase("chrome")) {
            webDriver = new ChromeDriver();
        } else if (browser.equalsIgnoreCase("firefox")) {
            webDriver = new FirefoxDriver();
        } else if (browser.equalsIgnoreCase("edge")) {
            webDriver = new EdgeDriver();
        } else{
            throw new IllegalArgumentException("Browser is not supported");
        }
    }

    public void browserConfiguration(){
        //Setup implicit wait
        int implicitWait = Integer.parseInt(properties.getProperty("implicitWait"));
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));

        //Maximize browser
        webDriver.manage().window().maximize();

        //Navigate to the URL
        try {
            webDriver.get(properties.getProperty("url"));
        }catch (Exception e) {
            log.error("Failed to navigate to the URL: " + e.getMessage() );
        }
    }

    @BeforeMethod
    public void setup(){
        log.info("Browser setup method called for" + this.getClass().getSimpleName());
        setupBrowser();
        browserConfiguration();
        setStaticWait(2);
    }

    @AfterMethod
    public void tearDown() {
        if (webDriver != null) {
            try {
                webDriver.quit();
            } catch (Exception e) {
                log.error("Failed to quit the browser: " + e.getMessage() );
            }
        }
    }

    public void setStaticWait(int seconds) {
        LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(seconds));
    }
}
