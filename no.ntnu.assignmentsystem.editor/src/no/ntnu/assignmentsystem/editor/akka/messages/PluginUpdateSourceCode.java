package no.ntnu.assignmentsystem.editor.akka.messages;

import java.io.Serializable;

public class PluginUpdateSourceCode implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public final String packageName;
	public final String fileName;
	public final String sourceCode;
	
	public PluginUpdateSourceCode(String packageName, String fileName, String sourceCode) {
		this.packageName = packageName;
		this.fileName = fileName;
		this.sourceCode = sourceCode;
	}
}
