package no.ntnu.assignmentsystem.editor;

import no.ntnu.assignmentsystem.editor.akka.AkkaCompletionRequestor;
import no.ntnu.assignmentsystem.editor.jdt.WorkspaceManager;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

public class TestHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		System.out.println("Testing system");
		
		WorkspaceManager projectManager = new WorkspaceManager("PluginProject");
		
		String packageName = "stateandbehavior";
		String fileName = "Main.java";
		String sourceCode = "package stateandbehavior;\n" +
			"public class Main {\n" +
			"    public static void main(String[] args) {\n" +
			"        System.out.println(\"Hello, World3!\");\n" +
			"    }\n" +
			"}\n";
		try {
			projectManager.updateSourceCode(packageName, fileName, sourceCode);

			projectManager.codeCompletion(packageName, fileName, 110, new AkkaCompletionRequestor(null)); // Code completes after: System.out.
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
}
