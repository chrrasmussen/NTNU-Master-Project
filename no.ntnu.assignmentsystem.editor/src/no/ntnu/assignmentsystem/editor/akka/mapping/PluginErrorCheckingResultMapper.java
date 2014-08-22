package no.ntnu.assignmentsystem.editor.akka.mapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.eclipse.core.resources.IMarker;

import no.ntnu.assignmentsystem.editor.akka.messages.PluginProblemMarker;
import no.ntnu.assignmentsystem.editor.akka.messages.PluginErrorCheckingResult;
import no.ntnu.assignmentsystem.editor.akka.messages.PluginProblemMarkersFile;
import no.ntnu.assignmentsystem.editor.jdt.WorkspaceManager.Listener;

public class PluginErrorCheckingResultMapper {
	public static PluginErrorCheckingResult createErrorCheckingResult(Listener.ProblemMarkersFile[] files) {
		List<PluginProblemMarkersFile> problemMarkersFiles = new ArrayList<>();
		for (Listener.ProblemMarkersFile file : files) {
			problemMarkersFiles.add(createProblemMarkersFile(file));
		}
		
		return new PluginErrorCheckingResult(problemMarkersFiles);
	}
	
	
	// --- Private methods ---
	
	public static PluginProblemMarkersFile createProblemMarkersFile(Listener.ProblemMarkersFile file) {
		List<PluginProblemMarker> problemMarkers = createProblemMarkers(file.markers);
		return new PluginProblemMarkersFile(file.packageName, file.fileName, problemMarkers);
	}
	
	private static List<PluginProblemMarker> createProblemMarkers(IMarker[] markers) {
		List<PluginProblemMarker> problemMarkers = new ArrayList<>();
		Arrays.stream(markers).map(PluginErrorCheckingResultMapper::createProblemMarker).forEach(marker -> {
			problemMarkers.add(marker);
		});
		
		return problemMarkers;
	}
	
	private static PluginProblemMarker createProblemMarker(IMarker marker) {
		int lineNumber = marker.getAttribute(IMarker.LINE_NUMBER, -1);
		String message = marker.getAttribute(IMarker.MESSAGE, "");
		int start = marker.getAttribute(IMarker.CHAR_START, -1);
		int end = marker.getAttribute(IMarker.CHAR_END, -1);
		int markerSeverity = marker.getAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
		PluginProblemMarker.Severity severity = createSeverity(markerSeverity);
		
		return new PluginProblemMarker(lineNumber, message, start, end, severity);
	}
	
	private static PluginProblemMarker.Severity createSeverity(int severity) {
		switch (severity) {
		case IMarker.SEVERITY_WARNING:
			return PluginProblemMarker.Severity.Warning;
		case IMarker.SEVERITY_INFO:
			return PluginProblemMarker.Severity.Info;
		default:
			return PluginProblemMarker.Severity.Error;
		}
	}
}
