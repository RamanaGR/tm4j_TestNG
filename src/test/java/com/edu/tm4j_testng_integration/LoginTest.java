package com.edu.tm4j_testng_integration;

import org.testng.Assert;
import org.testng.annotations.Test;

/*
 * Sample login test, configured to always pass for
 * illustration purposes.
 */
public class LoginTest extends BaseTest {
    @Test
    @TestData(key = "BR-T10")
    public void LoginTest() {
        System.out.println("====LoginTest====");
        System.out.println("[DEBUG] Starting the test...");

        // Do relevant test stuff here.
        Assert.assertTrue(true);

        System.out.println("[DEBUG] Test completed!");
    }
}
