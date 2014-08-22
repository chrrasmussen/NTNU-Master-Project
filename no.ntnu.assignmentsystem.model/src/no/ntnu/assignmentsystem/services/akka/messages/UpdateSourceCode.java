package no.ntnu.assignmentsystem.services.akka.messages;

import java.io.Serializable;

public class UpdateSourceCode implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public final String fileId;
	public final String sourceCode;
	
	public UpdateSourceCode(String fileId, String sourceCode) {
		this.fileId = fileId;
		this.sourceCode = sourceCode;
	}
}
