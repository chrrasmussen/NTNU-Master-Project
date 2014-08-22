package no.ntnu.assignmentsystem.services.akka.messages;

import java.io.Serializable;

public class TestResult implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public enum Status {
		OK,
		Failed,
		Ignored
	}
	
	public final String methodName;
	public final Status status;
	
	public TestResult(String methodName, Status status) {
		this.methodName = methodName;
		this.status = status;
	}
}
