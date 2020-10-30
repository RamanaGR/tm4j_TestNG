# tm4j-testng-integration

A quick proof-of-concept for combining [TestNG listeners](https://testng.org/doc/documentation-main.html), custom annotations, & the [REST API provided by TM4J](https://support.smartbear.com/tm4j-cloud/api-docs/) to ship off automated test results as they occur. 

## Prerequisites

This proof-of-concept uses two different dedicated libraries (besides TestNG) managed by Maven:

1. [SendGrid's HTTP client for Java](https://github.com/sendgrid/java-http-client)
2. [antonsjava's json library](https://github.com/antonsjava/json)

(As well as inspiration for the custom `TestData` annotation from [letsrokk's tm4j-hooks](https://github.com/letsrokk/tm4j-hooks) library—thanks, Raghu!)

A TM4J-provided API key is also required to be configured as an environment variable for this to work as-is. An API key can be obtained pretty easily for a given project in JIRA by navigating to your profile and clicking the `Test Management for JIRA API Keys` option.

On Windows, the environment variable can be initialized from the CLI:

```
setx TM4J_API_KEY "YOUR_KEY_HERE"
```

## Usage

Integrating TM4J is easy! Just add a separate `@TestData` annotation with the relevant TM4J key to each `@Test` that needs to be added to the automated test cycle.

```
@TestData(key="QAT-T1")
```

Configure the `testng.xml` with whatever tests need to be run & then execute from the IDE or the command line (—the listeners are configured in `BaseTest` for any child tests to extend & can be run easily from the IDE, but are also integrated in the TestNG configuration file otherwise.)

## Sample Output

```
[RemoteTestNG] detected TestNG version 7.3.0
[DEBUG] TEST CYCLE: QAT-R47
[DEBUG] Connector assigned & configured!
====StatusChangeTest====
[DEBUG] Starting the test...
[DEBUG] TEST STATUS (REQUEST): {"projectKey":"QAT","testCaseKey":"QAT-T2",
"testCycleKey":"QAT-R47","statusName":"Fail","testScriptResults":[{"statusName":"Fail"}]}
[DEBUG] TEST STATUS (RESPONSE): 201
[DEBUG] Test failed, shipping result to connector for processing.
====LoginTest====
[DEBUG] Starting the test...
[DEBUG] Test completed!
[DEBUG] TEST STATUS (REQUEST): {"projectKey":"QAT","testCaseKey":"QAT-T1",
"testCycleKey":"QAT-R47","statusName":"Pass","testScriptResults":[{"statusName":"Pass"}]}
[DEBUG] TEST STATUS (RESPONSE): 201
[DEBUG] Test passed, shipping result to connector for processing.
PASSED: LoginTest
FAILED: StatusChangeTest
```

## The Laundry List

- [ ] Look into breaking out the hardcoded project key from `Connector.java` to some sort of separate configuration file so that multiple project types can be used in a single run—maybe just fold into the `TestData` custom annotation type?
