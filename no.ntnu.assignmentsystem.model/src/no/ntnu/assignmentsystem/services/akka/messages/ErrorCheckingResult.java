package no.ntnu.assignmentsystem.services.akka.messages;

import java.io.Serializable;
import java.util.List;

public class ErrorCheckingResult implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public final List<ProblemMarkersFile> files;
	
	public ErrorCheckingResult(List<ProblemMarkersFile> files) {
		this.files = files;
	}
}
