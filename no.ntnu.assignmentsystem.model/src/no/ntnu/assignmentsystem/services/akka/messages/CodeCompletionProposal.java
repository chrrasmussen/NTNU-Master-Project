package no.ntnu.assignmentsystem.services.akka.messages;

import java.io.Serializable;

public class CodeCompletionProposal implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public final String completion;
	public final int score;
	
	public CodeCompletionProposal(String completion, int score) {
		this.completion = completion;
		this.score = score;
	}
}
