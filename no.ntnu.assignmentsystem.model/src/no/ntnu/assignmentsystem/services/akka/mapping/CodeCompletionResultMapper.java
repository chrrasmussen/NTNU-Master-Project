package no.ntnu.assignmentsystem.services.akka.mapping;

import java.util.ArrayList;
import java.util.List;

import no.ntnu.assignmentsystem.editor.akka.messages.PluginCodeCompletionProposal;
import no.ntnu.assignmentsystem.editor.akka.messages.PluginCodeCompletionResult;
import no.ntnu.assignmentsystem.services.akka.messages.CodeCompletionProposal;
import no.ntnu.assignmentsystem.services.akka.messages.CodeCompletionResult;

public class CodeCompletionResultMapper {
	public static CodeCompletionResult createCodeCompletionResult(PluginCodeCompletionResult pluginCodeCompletionResult) {
		List<CodeCompletionProposal> proposals = new ArrayList<>();
		for (PluginCodeCompletionProposal pluginCodeCompletionProposal : pluginCodeCompletionResult.proposals) {
			proposals.add(createCodeCompletionProposal(pluginCodeCompletionProposal));
		}
		
		return new CodeCompletionResult(proposals);
	}
	
	
	// --- Private methods ---
	
	private static CodeCompletionProposal createCodeCompletionProposal(PluginCodeCompletionProposal pluginCodeCompletionProposal) {
		String completion = pluginCodeCompletionProposal.completion;
		int score = pluginCodeCompletionProposal.score;
		
		return new CodeCompletionProposal(completion, score);
	}
}
