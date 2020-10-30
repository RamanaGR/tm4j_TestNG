package com.edu.tm4j_testng_integration.utilities;

import com.sendgrid.Client;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import sk.antons.json.JsonFactory;
import sk.antons.json.JsonObject;
import sk.antons.json.JsonValue;
import sk.antons.json.parse.JsonParser;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Connector {
	private final String BASE_URI = "api.adaptavist.io";
	private final String TEST_CYCLE_ENDPOINT = "/tm4j/v2/testcycles";
	private final String TEST_EXECUTION_ENDPOINT = "/tm4j/v2/testexecutions";
	
	private String projectKey = "BR";
	private Client client;
	
	private String apiKey;
	private String testCaseKey;
	private String testCycleName;
	private String testCycleKey;
	
	public Connector() {
		client = new Client();
		
		// Reads environment variable from system, so must be
		// configured per-machine first:
		apiKey = System.getenv("TM4J_API_KEY"); 
		
		this.configureTestCycle();
	}
	
	/*
	 * Configure a new test cycle for use by the TM4J connector
	 * for the run of this test suite using TestNG.
	 */
	private void configureTestCycle() {
		// For the time being, generate the test cycle
		// name based on date-time in ISO 8601 format:
		String isoDatePattern = "yyyy-MM-dd'T'HH:mm:ss'Z'";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(isoDatePattern);
		testCycleName = simpleDateFormat.format(new Date());
		
		Request request = new Request();
		request.setBaseUri(BASE_URI);
		request.setEndpoint(TEST_CYCLE_ENDPOINT);
		request.setMethod(Method.POST);
		request.addHeader("Authorization", "Bearer " + apiKey);
		
		JsonObject requestData = JsonFactory.object()
			.add("projectKey", JsonFactory.stringLiteral(projectKey))
			.add("name", JsonFactory.stringLiteral(testCycleName));
		
		request.setBody(requestData.toCompactString());
		
		try {
			Response response = client.api(request);
			
			JsonValue responseData = JsonParser.parse(response.getBody());
			testCycleKey = responseData.asObject().first("key").toCompactString().replace("\"", "");
			
			System.out.println("[DEBUG] TEST CYCLE: " + testCycleKey); 
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/*
	 * Use the current test case key to add to the connector's
	 * test cycle and pass/fail based on teh status provided.
	 */
	public void setTestStatus(String status) {
		Request request = new Request();
		request.setBaseUri(BASE_URI);
		request.setEndpoint(TEST_EXECUTION_ENDPOINT);
		request.setMethod(Method.POST);
		request.addHeader("Authorization", "Bearer " + apiKey);
		
		// Always assuming a single test case step (per our
		// current template in TM4J):
		JsonObject requestData = JsonFactory.object()
			.add("projectKey", JsonFactory.stringLiteral(projectKey))
			.add("testCaseKey", JsonFactory.stringLiteral(testCaseKey))
			.add("testCycleKey", JsonFactory.stringLiteral(testCycleKey))
			.add("statusName", JsonFactory.stringLiteral(status))
			.add("testScriptResults", JsonFactory.array()
				.add(JsonFactory.object()
					.add("statusName", JsonFactory.stringLiteral(status))
				)
			);
		
		request.setBody(requestData.toCompactString());
		
		try {
			Response response = client.api(request);
			
			System.out.println("[DEBUG] TEST STATUS (REQUEST): " + request.getBody());
			System.out.println("[DEBUG] TEST STATUS (RESPONSE): " + response.getStatusCode());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void setTestCaseKey(String testCaseKey) {
		this.testCaseKey = testCaseKey;
	}
}
