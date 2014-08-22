package no.ntnu.assignmentsystem.services.akka.messages;

import java.io.Serializable;
import java.util.List;

public class RunTestsResult implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public final List<TestResult> testResults;
	
	public RunTestsResult(List<TestResult> testResults) {
		this.testResults = testResults;
	}
}
