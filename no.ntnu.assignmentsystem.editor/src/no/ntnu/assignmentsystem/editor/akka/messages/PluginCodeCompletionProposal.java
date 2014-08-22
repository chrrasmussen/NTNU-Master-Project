package no.ntnu.assignmentsystem.editor.akka.messages;

import java.io.Serializable;

public class PluginCodeCompletionProposal implements Serializable {
	private static final long serialVersionUID = 1L;

	public final String completion;
	public final int score;
	
	public PluginCodeCompletionProposal(String completion, int score) {
		this.completion = completion;
		this.score = score;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof PluginCodeCompletionProposal) {
			PluginCodeCompletionProposal otherProposal = (PluginCodeCompletionProposal)obj;
			return this.completion.equals(otherProposal.completion);
		}
		else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		return completion;
	}
}
