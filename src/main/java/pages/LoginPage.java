package pages;

import actionDriver.ActionDriver;
import base.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends BaseClass {

    private ActionDriver actionDriver;

    private By usernameField = By.name("username");
    private By passwordField = By.name("password");
    private By logginButton = By.xpath("//button[contains(normalize-space(.), 'Login')]");
    private By errorMessage = By.xpath("//p[text()='Invalid credentials']");

    public LoginPage(WebDriver webDriver) {
        this.actionDriver = BaseClass.getActionDriver();
        PageFactory.initElements(webDriver, this); // optional, if using @FindBy
    }

    //Login
    public void login(String userName, String password) {
        actionDriver.sendKeys(usernameField, userName);
        actionDriver.sendKeys(passwordField, password);
        actionDriver.click(logginButton);
    }

    //Check error message
    public boolean checkErrorMessageDisplayed() {
        return actionDriver.isElementDisplayed(errorMessage);
    }

    //Retrieve text from error message
    public String getErrorMessage() {
        return actionDriver.getText(errorMessage);
    }

    //Verify error message
    public boolean verifyErrorMessage(String expectedErrorMessage) {
        return actionDriver.compareTwoTexts(errorMessage, expectedErrorMessage);
    }
}
