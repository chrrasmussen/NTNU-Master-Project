package no.ntnu.assignmentsystem.editor.akka.messages;

import java.io.Serializable;
import java.util.List;

public class PluginErrorCheckingResult implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public final List<PluginProblemMarkersFile> files;
	
	public PluginErrorCheckingResult(List<PluginProblemMarkersFile> files) {
		this.files = files;
	}
}
