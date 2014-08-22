package no.ntnu.assignmentsystem.services.akka.mapping;

import java.util.ArrayList;
import java.util.List;
import no.ntnu.assignmentsystem.editor.akka.messages.PluginErrorCheckingResult;
import no.ntnu.assignmentsystem.editor.akka.messages.PluginProblemMarker;
import no.ntnu.assignmentsystem.editor.akka.messages.PluginProblemMarkersFile;
import no.ntnu.assignmentsystem.services.akka.messages.ErrorCheckingResult;
import no.ntnu.assignmentsystem.services.akka.messages.ProblemMarker;
import no.ntnu.assignmentsystem.services.akka.messages.ProblemMarkersFile;

public class ErrorCheckingResultMapper {
	public interface IdRetriever {
		String getId(String packageName, String fileName);
	}
	
	public static ErrorCheckingResult createErrorCheckingResult(IdRetriever idRetriever, PluginErrorCheckingResult result) {
		List<ProblemMarkersFile> problemMarkersFiles = new ArrayList<>();
		for (PluginProblemMarkersFile file : result.files) {
			problemMarkersFiles.add(createProblemMarkersFile(idRetriever, file));
		}
		
		return new ErrorCheckingResult(problemMarkersFiles);
	}
	
	
	// --- Private methods ---
	
	private static ProblemMarkersFile createProblemMarkersFile(IdRetriever idRetriever, PluginProblemMarkersFile file) {
		String fileId = idRetriever.getId(file.packageName, file.fileName);
		List<ProblemMarker> problemMarkers = createProblemMarkers(file.problemMarkers);
		return new ProblemMarkersFile(fileId, problemMarkers);
	}
	
	private static List<ProblemMarker> createProblemMarkers(List<PluginProblemMarker> markers) {
		List<ProblemMarker> problemMarkers = new ArrayList<>();
		markers.stream().map(ErrorCheckingResultMapper::createProblemMarker).forEach(marker -> {
			problemMarkers.add(marker);
		});
		
		return problemMarkers;
	}
	
	private static ProblemMarker createProblemMarker(PluginProblemMarker marker) {
		int lineNumber = marker.lineNumber;
		ProblemMarker.Type type = createType(marker.severity);
		String description = marker.message;
		
		return new ProblemMarker(lineNumber, type, description);
	}
	
	private static ProblemMarker.Type createType(PluginProblemMarker.Severity severity) {
		switch (severity) {
		case Warning:
			return ProblemMarker.Type.Warning;
		case Info:
			return ProblemMarker.Type.Information;
		default:
			return ProblemMarker.Type.Error;
		}
	}
}
