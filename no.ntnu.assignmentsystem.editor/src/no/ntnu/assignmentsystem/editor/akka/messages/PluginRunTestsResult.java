package no.ntnu.assignmentsystem.editor.akka.messages;

import java.io.Serializable;
import java.util.List;

public class PluginRunTestsResult implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public final List<PluginTestResult> testResults;
	
	public PluginRunTestsResult(List<PluginTestResult> testResults) {
		this.testResults = testResults;
	}
}
