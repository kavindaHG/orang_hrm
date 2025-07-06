package tests;

import base.BaseClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;


public class LoginTest extends BaseClass {

    private static final Logger log = LoggerFactory.getLogger(LoginTest.class);
    private LoginPage loginPage;
    private HomePage homePage;

    @BeforeMethod
    public void testSetup() {
        loginPage = new LoginPage(webDriver);
        homePage = new HomePage(webDriver);
    }

    @Test(priority = 1)
    public void validLoginTest() {
        System.out.println(getClass().getSimpleName() + " method is called");
        loginPage.login("admin", "admin123");
        Assert.assertTrue(homePage.isAdminTabVisible(),
                "Admin tab should be visible after successful login");
        setStaticWait(2);
    }

    @Test(priority = 0)
    public void invalidLoginTest() {
        System.out.println(getClass().getSimpleName() + " method is called");
        loginPage.login("admin1", "admin1");
        String expectedErrorMessage = "Invalid credentials";
        Assert.assertTrue(loginPage.verifyErrorMessage(expectedErrorMessage),
                "Text matching failed");
        setStaticWait(2);
    }
}
