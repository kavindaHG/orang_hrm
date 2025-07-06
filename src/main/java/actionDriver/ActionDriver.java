package actionDriver;

import base.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class ActionDriver {

    private static final Logger log = LoggerFactory.getLogger(ActionDriver.class);
    private WebDriver webDriver;
    private WebDriverWait wait;

    public ActionDriver(WebDriver webDriver) {
        this.webDriver = webDriver;
        int explicitWait = Integer.parseInt(BaseClass.getConfigProp().getProperty("explicitWait"));
        this.wait = new WebDriverWait(webDriver, Duration.ofSeconds(explicitWait));
    }

    //Click
    public void click(By by) {
        try {
            waitForEventToBeClickable(by);
            webDriver.findElement(by).click();
        } catch (Exception e) {
            log.error("Element is not clickable: " + e.getMessage());
            System.out.println(("Element is not clickable: " + e.getMessage()));
        }
    }

    //Clear and enter text
    public void sendKeys(By by, String value) {
        try {
            waitForElementToBeVisible(by);
            webDriver.findElement(by).clear();
            webDriver.findElement(by).sendKeys(value);
        } catch (Exception e) {
            log.error("Cannot send keys to the element: " + e.getMessage());
            System.out.println(("Cannot send keys to the element: " + e.getMessage()));
        }
    }

    //get Text
    public String getText(By by) {
        try {
            waitForElementToBeVisible(by);
            return webDriver.findElement(by).getText();
        } catch (Exception e) {
            log.error("Unable to get the text: " + e.getMessage());
            System.out.println(("Unable to get the text: " + e.getMessage()));
            return "";
        }
    }

    //Compare two texts
    public boolean compareTwoTexts(By by, String expectedText) {
        try {
            waitForElementToBeVisible(by);
            String actualText = webDriver.findElement(by).getText();
            System.out.println(actualText);
            if (expectedText.equals(actualText)) {
                log.info("Actual text successfully matches with the expected text");
                System.out.println(("Actual text successfully matches with the expected text"));
                return true;
            } else {
                log.info("Actual text not matched with the expected text");
                System.out.println(("Actual text not matched with the expected text"));
                return false;
            }
        } catch (Exception e) {
            log.info("unable to compare texts");
            System.out.println("unable to compare texts" + e.getMessage());
            return false;
        }
    }

    //Element is displayed
    public boolean isElementDisplayed(By by) {
        try {
            waitForElementToBeVisible(by);
            boolean isDisplayed = webDriver.findElement(by).isDisplayed();
            if (isDisplayed) {
                log.info("Element is visible");
                System.out.println(("Element is visible"));
                return isDisplayed;
            } else {
                log.info("Element is not visible");
                System.out.println(("Element is not visible"));
                return isDisplayed;
            }
        } catch (Exception e) {
            log.info("Element cannot be displayed" + e.getMessage());
            System.out.println(("Element cannot be displayed" + e.getMessage()));
            return false;
        }
    }

    //Wait for the page to load
    private void waitForPageToLoad(int timeoutInSec) {
        try {
            wait.withTimeout(Duration.ofSeconds(timeoutInSec)).until(WebDriver ->
                    ((JavascriptExecutor) WebDriver).executeScript("" +
                            "return document.readyState").equals("complete"));
            System.out.println("Page loaded successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Scroll to element
    public void scrollToElement(By by) {
        try {
            JavascriptExecutor javascriptExecutor = (JavascriptExecutor) webDriver;
            WebElement element = webDriver.findElement(by);
            javascriptExecutor.executeScript("arguments[0], scrollIntoView(true)", element);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //wait for event to be clickable
    private void waitForEventToBeClickable(By by) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(by));
        } catch (Exception e) {
            log.error("Element is not clickable: " + e.getMessage());
            System.out.println(("Element is not clickable: " + e.getMessage()));
        }
    }

    //wait for element to be visible
    private void waitForElementToBeVisible(By by) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        } catch (Exception e) {
            log.error("Element is not visible: " + e.getMessage());
            System.out.println(("Element is not visible: " + e.getMessage()));
        }
    }
}
