package no.ntnu.assignmentsystem.editor.akka;

import java.util.ArrayList;
import java.util.List;

import no.ntnu.assignmentsystem.editor.akka.messages.PluginTestResult;

import org.eclipse.jdt.junit.TestRunListener;
import org.eclipse.jdt.junit.model.ITestCaseElement;
import org.eclipse.jdt.junit.model.ITestRunSession;

public class AkkaTestRunListener extends TestRunListener {
	public interface Delegate {
		void runTestsResult(AkkaTestRunListener listener, List<PluginTestResult> testResults);
	}
	
	private final Delegate delegate;
	
	private List<PluginTestResult> testResults;
	
	public AkkaTestRunListener(Delegate delegate) {
		this.delegate = delegate;
	}
	
	public void sessionLaunched(ITestRunSession session) {
//		System.out.println("TestRunListener[1]: " + session);
	}
	
	public void sessionStarted(ITestRunSession session) {
//		System.out.println("TestRunListener[2]: " + session);
		testResults = new ArrayList<PluginTestResult>();
	}
	
	public void sessionFinished(ITestRunSession session) {
//		System.out.println("TestRunListener[3]: " + session);
		delegate.runTestsResult(this, testResults);
		testResults = null;
	}
	
	public void testCaseStarted(ITestCaseElement testCaseElement) {
//		System.out.println("TestRunListener[4]: " + testCaseElement);
	}
	
	public void testCaseFinished(ITestCaseElement testCaseElement) {
//		System.out.println("TestRunListener[5]: " + testCaseElement);
		PluginTestResult.Status status = convertResultStatus(testCaseElement.getTestResult(false));
		testResults.add(new PluginTestResult(testCaseElement.getTestMethodName(), status));
	}
	
	
	// --- Private methods ---
	
	private static PluginTestResult.Status convertResultStatus(ITestCaseElement.Result testCaseElementResult) {
		if (testCaseElementResult.equals(ITestCaseElement.Result.OK)) {
			return PluginTestResult.Status.OK;
		}
		else if (testCaseElementResult.equals(ITestCaseElement.Result.IGNORED)) {
			return PluginTestResult.Status.Ignored;
		}
		else {
			return PluginTestResult.Status.Failed;
		}
	}
}
