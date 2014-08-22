package no.ntnu.assignmentsystem.editor.akka.messages;

import java.io.Serializable;

public class PluginCodeCompletion implements Serializable {
	private static final long serialVersionUID = 1L;

	public final String packageName;
	public final String fileName;
	public final int offset;
	
	public PluginCodeCompletion(String packageName, String fileName, int offset) {
		this.packageName = packageName;
		this.fileName = fileName;
		this.offset = offset;
	}
}
