package no.ntnu.assignmentsystem.model.impl;

import java.io.File;
import java.nio.file.Files;

import no.ntnu.assignmentsystem.model.CodeProblem;

class SourceCodeFileImplHelper {
	public static String getSourceCode(SourceCodeFileImpl sourceCodeFileImpl) {
		CodeProblem codeProblem = (CodeProblem)sourceCodeFileImpl.eInternalContainer();
		
		File rootDirectory = new File(codeProblem.getRepoUrl());
		File file = new File(rootDirectory, sourceCodeFileImpl.getFilePath());
		
		try {
			return new String(Files.readAllBytes(file.toPath()));
		} catch (java.io.IOException e) {
			return null;
		}
	}
}
