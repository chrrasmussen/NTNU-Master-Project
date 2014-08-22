package no.ntnu.assignmentsystem.editor.akka.messages;

import java.io.Serializable;
import java.util.List;

public class PluginCodeCompletionResult implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public final List<PluginCodeCompletionProposal> proposals;
	
	public PluginCodeCompletionResult(List<PluginCodeCompletionProposal> proposals) {
		this.proposals = proposals;
	}
}
