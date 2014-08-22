package no.ntnu.assignmentsystem.editor.akka.messages;

import java.io.Serializable;
import java.util.List;

public class PluginProblemMarkersFile implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public final String packageName;
	public final String fileName;
	public final List<PluginProblemMarker> problemMarkers;
	
	public PluginProblemMarkersFile(String packageName, String fileName, List<PluginProblemMarker> problemMarkers) {
		this.packageName = packageName;
		this.fileName = fileName;
		this.problemMarkers = problemMarkers;
	}
}
