package tests;

import base.BaseClass;
import org.testng.annotations.Test;

import java.sql.SQLOutput;


public class LoginTEst extends BaseClass {

    @Test
    public void dummy () throws InterruptedException {
        String t = webDriver.getTitle();
        assert t.equals("OrangeHRM") : "Test Failed - Actual Title: " + t;
        System.out.println(t + "**** Passed");
    }
}
