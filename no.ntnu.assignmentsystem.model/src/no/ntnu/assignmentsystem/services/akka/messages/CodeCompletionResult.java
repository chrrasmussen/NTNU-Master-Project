package no.ntnu.assignmentsystem.services.akka.messages;

import java.io.Serializable;
import java.util.List;

public class CodeCompletionResult implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public final List<CodeCompletionProposal> proposals;
	
	public CodeCompletionResult(List<CodeCompletionProposal> proposals) {
		this.proposals = proposals;
	}
}
