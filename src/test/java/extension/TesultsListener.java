package extension;

import org.junit.platform.engine.TestExecutionResult;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestIdentifier;
import org.junit.platform.launcher.TestPlan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TesultsListener implements TestExecutionListener {
	// A list to hold your test cases.
	List<Map<String, Object>> testCases = new ArrayList<Map<String, Object>>();

	public void executionFinished(TestIdentifier testIdentifier, TestExecutionResult
			testExecutionResult) {
		if (testIdentifier.isTest()) {
			System.out.println("Execution finished: " + testIdentifier.getDisplayName() + " " +
					testExecutionResult.toString());
			String result = testExecutionResult.getStatus().toString();
			// Tesults requires result to be one of: [pass, fail, unknown]
			if (result == "SUCCESSFUL") {
				result = "pass";
			} else if (result == "FAILED") {
				result = "fail";
			} else {
				result = "unknown";
			}
			String reason = "";
			if (testExecutionResult.getThrowable().isPresent()) {
				reason = testExecutionResult.getThrowable().get().getMessage();
			}
			String suite = "";
			String separator = "class:";
			if (testIdentifier.getParentId().isPresent()) {
				suite = testIdentifier.getParentId().get();
				suite = suite.substring(suite.indexOf(separator) + separator.length(), suite.lastIndexOf("]"));
			}
			Map<String, Object> testCase = new HashMap<String, Object>();
			String name = testIdentifier.getDisplayName();
			if (name.indexOf("(") != -1) {
				name = name.substring(0, name.lastIndexOf("("));
			}
			testCase.put("name", name);
			testCase.put("result", result);
			testCase.put("suite", suite);
			testCase.put("desc", testIdentifier.getDisplayName());
			testCase.put("reason", reason);
			// (Optional) For uploading files:
			//List<String> files = new ArrayList<String>();
			//files.add("/path-to-files/test-name/img1.png");
			//files.add("/path-to-files/test-name/img2.png");
			//testCase.put("files", files);
			testCases.add(testCase);
		}
	}

	public void testPlanExecutionFinished(TestPlan testPlan) {
		// Map<String, Object> to hold your test results data.
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("target", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6IjIzNTYzZTIyLTY3OWItNGFmYi04NTNhLTQ4Mjc1NzZkNzQ2Ni0xNTY2MjE5MzQzNTM4IiwiZXhwIjo0MTAyNDQ0ODAwMDAwLCJ2ZXIiOiIwIiwic2VzIjoiYmM2MWI3YzQtNTg2ZS00ZTM2LWI2ZTUtZTBmODIwNzRjZjhlIiwidHlwZSI6InQifQ.2xwXfE38_gRs-gppQG4-N35VuyrY8s1Ty3txJtSFBYg");
		Map<String, Object> results = new HashMap<String, Object>();
		results.put("cases", testCases);
		data.put("results", results);
		// Upload
		System.out.println("Uploading results data to Tesults...");
//		Map<String, Object> response = Results.upload(data);
//		System.out.println("success: " + response.get("success"));
//		System.out.println("message: " + response.get("message"));
//		System.out.println("warnings: " + ((List<String>) response.get("warnings")).size());
//		System.out.println("errors: " + ((List<String>) response.get("errors")).size());
	}
}
