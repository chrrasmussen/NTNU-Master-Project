package no.ntnu.assignmentsystem.editor.akka.messages;

import java.io.Serializable;

public class PluginTestResult implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public enum Status {
		OK,
		Failed,
		Ignored
	}
	
	public final String methodName;
	public final Status status;
	
	public PluginTestResult(String methodName, Status status) {
		this.methodName = methodName;
		this.status = status;
	}
}
