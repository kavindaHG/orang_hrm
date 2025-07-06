package tests;

import base.BaseClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;

public class HomeTest extends BaseClass {

    private static final Logger log = LoggerFactory.getLogger(LoginTest.class);
    private LoginPage loginPage;
    private HomePage homePage;

    @BeforeMethod
    public void testSetup() {
        loginPage = new LoginPage(webDriver);
        homePage = new HomePage(webDriver);
    }

    @Test
    public void verifyLogo() {
        loginPage.login("admin", "admin123");
        Assert.assertTrue(homePage.isOrangeLogoVisible(), "Logo is not visible");
    }
}
