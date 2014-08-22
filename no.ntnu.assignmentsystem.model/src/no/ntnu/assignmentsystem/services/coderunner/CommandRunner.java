package no.ntnu.assignmentsystem.services.coderunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.SequenceInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

public class CommandRunner {
	private static final int normalExitCode = 0;
	
	private final RuntimeExecutor executor;
	
	public CommandRunner(RuntimeExecutor executor) {
		this.executor = executor;
	}
	
	public Process[] runCommands(String[] commands) throws Exception {
		List<Process> processes = new ArrayList<>();
		for (String command : commands) {
			Process process = executor.exec(command);
			processes.add(process);
		}
		
		return (Process[])processes.toArray(new Process[processes.size()]);
	}
	
	public String runCommandsAndWait(String[] commands) throws Exception {
		List<Process> processes = new ArrayList<>();
		for (String command : commands) {
			Process process = executor.exec(command);
			processes.add(process);
			
			if (process.waitFor() != normalExitCode) {
				break;
			}
		}
		
		return getOutputFromProcesses(processes);
	}
	
	
	// --- Private methods ---
	
	private static String getOutputFromProcesses(List<Process> processes) throws IOException {
		List<InputStream> inputStreams = processes.stream().flatMap(
			process -> Arrays.asList(process.getErrorStream(), process.getInputStream()).stream()
		).collect(Collectors.toList());
		
		InputStream combinedStream = new SequenceInputStream(new Vector<InputStream>(inputStreams).elements());
		return getStringFromInputStream(combinedStream);
	}
	
	private static String getStringFromInputStream(InputStream inputStream) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		
		return reader.lines().reduce("", (concatenatedText, nextLine) -> concatenatedText + System.lineSeparator() + nextLine);
	}
}
