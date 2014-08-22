package no.ntnu.assignmentsystem.services.akka.mapping;

import java.util.ArrayList;
import java.util.List;

import no.ntnu.assignmentsystem.editor.akka.messages.PluginRunTestsResult;
import no.ntnu.assignmentsystem.editor.akka.messages.PluginTestResult;
import no.ntnu.assignmentsystem.services.akka.messages.RunTestsResult;
import no.ntnu.assignmentsystem.services.akka.messages.TestResult;

public class RunTestsResultMapper {
	public static RunTestsResult createRunTestsResult(PluginRunTestsResult pluginRunTestsResult) {
		List<TestResult> testResults = new ArrayList<TestResult>();
		for (PluginTestResult pluginTestResult : pluginRunTestsResult.testResults) {
			testResults.add(createTestResult(pluginTestResult));
		}
		
		return new RunTestsResult(testResults);
	}
	
	
	// --- Private methods ---
	
	private static TestResult createTestResult(PluginTestResult pluginTestResult) {
		String methodName = pluginTestResult.methodName;
		TestResult.Status status = createTestResultStatus(pluginTestResult.status);
		
		return new TestResult(methodName, status);
	}
	
	private static TestResult.Status createTestResultStatus(PluginTestResult.Status status) {
		switch (status) {
		case OK:
			return TestResult.Status.OK;
		case Ignored:
			return TestResult.Status.Ignored;
		default:
			return TestResult.Status.Failed;
		}
	}
}
