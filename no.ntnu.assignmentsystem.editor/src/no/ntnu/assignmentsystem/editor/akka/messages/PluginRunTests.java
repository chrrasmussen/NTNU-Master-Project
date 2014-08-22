package no.ntnu.assignmentsystem.editor.akka.messages;

import java.io.Serializable;

public class PluginRunTests implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public final String qualifiedClassName;
	
	public PluginRunTests(String qualifiedClassName) {
		this.qualifiedClassName = qualifiedClassName;
	}
}
