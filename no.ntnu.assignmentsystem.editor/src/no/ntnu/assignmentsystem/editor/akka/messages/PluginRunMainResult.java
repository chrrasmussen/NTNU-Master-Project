package no.ntnu.assignmentsystem.editor.akka.messages;

import java.io.Serializable;

public class PluginRunMainResult implements Serializable {
	private static final long serialVersionUID = 1L;

	public String output;
	
	public PluginRunMainResult(String result) {
		this.output = result;
	}
}
