package pages;

import actionDriver.ActionDriver;
import base.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

    private ActionDriver actionDriver;

    private By dashBoardRole = By.xpath("//span[text() = 'Admin']");
    private By userIdButton = By.className("oxd-userdropdown-name");
    private By logOutButton = By.xpath("//a[text()='Logout'");
    private By logo = By.xpath("//div[@class='oxd-brand-banner']//img");

    public HomePage(WebDriver webDriver) {
        this.actionDriver = BaseClass.getActionDriver();
        PageFactory.initElements(webDriver, this); // optional, if using @FindBy
    }

    //Verify Admin tab is available
    public boolean isAdminTabVisible() {
        return actionDriver.isElementDisplayed(dashBoardRole);
    }

    //Verify orangeHRM logo
    public boolean isOrangeLogoVisible() {
        return actionDriver.isElementDisplayed(logo);
    }

    //Verify logout
    public void logOut() {
        actionDriver.click(userIdButton);
        actionDriver.click(logOutButton);
    }
}
