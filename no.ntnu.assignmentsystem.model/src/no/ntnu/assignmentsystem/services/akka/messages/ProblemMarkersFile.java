package no.ntnu.assignmentsystem.services.akka.messages;

import java.io.Serializable;
import java.util.List;

public class ProblemMarkersFile implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public final String fileId;
	public final List<ProblemMarker> problemMarkers;
	
	public ProblemMarkersFile(String fileId, List<ProblemMarker> problemMarkers) {
		this.fileId = fileId;
		this.problemMarkers = problemMarkers;
	}
}
