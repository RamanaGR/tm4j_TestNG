package com.edu.tm4j_testng_integration.listeners;


import com.edu.tm4j_testng_integration.BaseTest.TestData;
import com.edu.tm4j_testng_integration.utilities.Connector;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class IntegrationListener extends TestListenerAdapter {
    private Connector connector;

    @Override
    public void onTestFailure(ITestResult result) {
        this.connector = ((Connector) result.getTestContext().getAttribute("connector"));
        configureTestResult(result, "Fail");

        System.out.println("[DEBUG] Test failed, shipping result to connector for processing.");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        this.connector = ((Connector) result.getTestContext().getAttribute("connector"));
        configureTestResult(result, "Pass");

        System.out.println("[DEBUG] Test passed, shipping result to connector for processing.");
    }

    private void configureTestResult(ITestResult result, String verdict) {
        TestData test = result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(TestData.class);
        this.connector.setTestCaseKey(test.key());
        this.connector.setTestStatus(verdict);
    }
}
