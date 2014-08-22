package no.ntnu.assignmentsystem.services.akka.messages;

import java.io.Serializable;

public class CodeCompletion implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public final String fileId;
	public final int offset;
	
	public CodeCompletion(String fileId, int offset) {
		this.fileId = fileId;
		this.offset = offset;
	}
}
