package no.ntnu.assignmentsystem.services.mapping;

import java.io.File;

import no.ntnu.assignmentsystem.model.CodeProblem;
import no.ntnu.assignmentsystem.model.Problem;
import no.ntnu.assignmentsystem.model.SourceCodeFile;
import no.ntnu.assignmentsystem.model.Student;
import no.ntnu.assignmentsystem.services.CodeProblemView;
import no.ntnu.assignmentsystem.services.ExtendedProblemView;
import no.ntnu.assignmentsystem.services.ProblemView;
import no.ntnu.assignmentsystem.services.SourceCodeFileView;


public class ProblemViewFactory extends BaseViewFactory {
	public static ProblemView createProblemView(Student student, Problem problem) {
		ProblemView problemView = getFactory().createProblemView();
		mapProblemViewProperties(problemView, student, problem);
		return problemView;
	}
	
	public static ExtendedProblemView createExtendedProblemView(Student student, Problem problem) {
		ExtendedProblemView problemView = createCodeProblemView(student, (CodeProblem)problem);
		mapProblemViewProperties(problemView, student, problem);
		return problemView;
	}
	
	
	// --- Private methods ---
	
	private static void mapProblemViewProperties(ProblemView problemView, Student student, Problem problem) {
		Mapper.copyAttributes(problem, problemView);
		
		// TODO: Map progress
	}
	
	private static CodeProblemView createCodeProblemView(Student student, CodeProblem codeProblem) {
		CodeProblemView codeProblemView = getFactory().createCodeProblemView();
		
		codeProblem.getSourceCodeFiles().forEach(sourceCodeFile -> {
			if (sourceCodeFile.isVisible()) {
				SourceCodeFileView sourceCodeFileView = getFactory().createSourceCodeFileView();
				
				Mapper.copyAttributes(sourceCodeFile, sourceCodeFileView);
				
				sourceCodeFileView.setSourceCode(getSourceCode(student, sourceCodeFile));
				
				String fileName = new File(sourceCodeFile.getFilePath()).getName();
				sourceCodeFileView.setTitle(fileName);
				
				codeProblemView.getSourceCodeFiles().add(sourceCodeFileView);
			}
		});
		
		return codeProblemView;
	}
	
	private static String getSourceCode(Student student, SourceCodeFile sourceCodeFile) {
		return student.getSourceCodeFiles().stream().filter(
			modifiedSourceCodeFile -> modifiedSourceCodeFile.getOriginalSourceCodeFile().getId().equals(sourceCodeFile.getId())
		).findAny().map(
			modifiedSourceCodeFile -> modifiedSourceCodeFile.getSourceCode()
		).orElseGet(
			() -> sourceCodeFile.getSourceCode()
		);
	}
}
