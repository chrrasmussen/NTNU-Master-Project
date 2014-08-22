package no.ntnu.assignmentsystem.editor.akka.messages;

import java.io.Serializable;

public class PluginProblemMarker implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public enum Severity {
		Error,
		Warning,
		Info
	}
	
	public final int lineNumber;
	public final String message;
	public final int start;
	public final int end;
	public final Severity severity;

	public PluginProblemMarker(int lineNumber, String message, int start, int end, Severity severity) {
		this.lineNumber = lineNumber;
		this.message = message;
		this.start = start;
		this.end = end;
		this.severity = severity;
	}
}
