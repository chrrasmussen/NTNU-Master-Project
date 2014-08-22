package no.ntnu.assignmentsystem.services.coderunner;

import java.io.File;

public class StartPluginCommands {
	private static final String startPluginCommandFormat = "java -jar %s -consoleLog -noSplash -noExit -data %s -application %s %s"; // Placeholders: launcher, workspace directory, plugin name, extra parameters
	
	private final File launcher;
	private final String pluginName;
	
	public StartPluginCommands(File launcher, String pluginName) {
		this.launcher = launcher;
		this.pluginName = pluginName;
	}
	
	public String getStartPluginCommand(File workspaceDirectory, String remoteAddress) {
		return String.format(startPluginCommandFormat, launcher, workspaceDirectory, pluginName, remoteAddress);
	}
}
