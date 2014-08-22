package no.ntnu.assignmentsystem.services.akka.messages;

import java.io.Serializable;

public class ProblemMarker implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public enum Type {
		Error,
		Warning,
		Information
	}

	public final int lineNumber;
	public final Type type;
	public final String description;
	
	public ProblemMarker(int lineNumber, Type type, String description) {
		this.lineNumber = lineNumber;
		this.type = type;
		this.description = description;
	}
}
