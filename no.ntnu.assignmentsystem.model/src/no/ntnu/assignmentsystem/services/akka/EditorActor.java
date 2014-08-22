package no.ntnu.assignmentsystem.services.akka;

import java.io.File;

import no.ntnu.assignmentsystem.editor.akka.messages.PluginCodeCompletion;
import no.ntnu.assignmentsystem.editor.akka.messages.PluginCodeCompletionResult;
import no.ntnu.assignmentsystem.editor.akka.messages.PluginErrorCheckingResult;
import no.ntnu.assignmentsystem.editor.akka.messages.PluginReady;
import no.ntnu.assignmentsystem.editor.akka.messages.PluginRunMain;
import no.ntnu.assignmentsystem.editor.akka.messages.PluginRunMainResult;
import no.ntnu.assignmentsystem.editor.akka.messages.PluginRunTests;
import no.ntnu.assignmentsystem.editor.akka.messages.PluginRunTestsResult;
import no.ntnu.assignmentsystem.editor.akka.messages.PluginUpdateSourceCode;
import no.ntnu.assignmentsystem.model.CodeProblem;
import no.ntnu.assignmentsystem.model.ImplementationFile;
import no.ntnu.assignmentsystem.model.SourceCodeFile;
import no.ntnu.assignmentsystem.model.TestFile;
import no.ntnu.assignmentsystem.services.ModelServices;
import no.ntnu.assignmentsystem.services.akka.mapping.CodeCompletionResultMapper;
import no.ntnu.assignmentsystem.services.akka.mapping.ErrorCheckingResultMapper;
import no.ntnu.assignmentsystem.services.akka.mapping.RunTestsResultMapper;
import no.ntnu.assignmentsystem.services.akka.messages.CodeCompletion;
import no.ntnu.assignmentsystem.services.akka.messages.NotifyOnReady;
import no.ntnu.assignmentsystem.services.akka.messages.Ready;
import no.ntnu.assignmentsystem.services.akka.messages.RunMain;
import no.ntnu.assignmentsystem.services.akka.messages.RunMainResult;
import no.ntnu.assignmentsystem.services.akka.messages.RunTests;
import no.ntnu.assignmentsystem.services.akka.messages.UpdateSourceCode;
import no.ntnu.assignmentsystem.services.coderunner.CommandRunner;
import no.ntnu.assignmentsystem.services.coderunner.DefaultRuntimeExecutor;
import no.ntnu.assignmentsystem.services.coderunner.StartPluginCommands;
import akka.actor.ActorRef;
import akka.actor.Address;
import akka.actor.UntypedActorWithStash;
import akka.japi.Procedure;
import akka.remote.RemoteActorRefProvider;

public class EditorActor extends UntypedActorWithStash {
	private final ModelServices modelServices;
	private final String userId;
	private final String problemId;
	
	private final CommandRunner commandRunner = new CommandRunner(new DefaultRuntimeExecutor());
	private final StartPluginCommands startPluginCommands = new StartPluginCommands(
		new File("/Applications/Eclipse/plugins/org.eclipse.equinox.launcher_1.3.0.v20140415-2008.jar"), // Christian's laptop
//		new File("C:/eclipse/plugins/org.eclipse.equinox.launcher_1.3.0.v20140224-1459.jar"), // David's stationary computer
//		new File("C:/eclipse/plugins/org.eclipse.equinox.launcher_1.3.0.v20140415-2008.jar"), // David's laptop
		"no.ntnu.assignmentsystem.editor.BackgroundApplication"
	);
	
	private ActorRef pluginActor;
	private ActorRef consumerActor;
	
	public EditorActor(ModelServices modelServices, String userId, String problemId) {
		this.modelServices = modelServices;
		this.userId = userId;
		this.problemId = problemId;
	}
	
	@Override
	public void preStart() throws Exception {
		System.out.println(getSelf() + ": Pre-start");
		
		startPlugin();
	}
	
	@Override
	public void onReceive(Object message) throws Exception {
		if (message instanceof PluginReady) {
			System.out.println("Set PluginActor to:" + getSender());
			pluginActor = getSender();
			
			bootstrapPlugin();
		}
		else if (message instanceof NotifyOnReady) {
			System.out.println("Set consumer to:" + getSender());
			consumerActor = getSender();
		}
		else {
			stash();
		}
		
		if (pluginActor != null && consumerActor != null) {
			unstashAll();
			getContext().become(onReceiveWhenReady, false);
			
			consumerActor.tell(new Ready(), getSelf());
		}
	}
	
	private Procedure<Object> onReceiveWhenReady = message -> {
		System.out.println(getSelf() + ": Received message:" + message);
		
		// From consumer
		if (message instanceof RunMain) {
			handleRunMain((RunMain)message);
		}
		else if (message instanceof RunTests) {
			handleRunTests((RunTests)message);
		}
		else if (message instanceof UpdateSourceCode) {
			handleUpdateSourceCode((UpdateSourceCode)message);
		}
		else if (message instanceof CodeCompletion) {
			handleCodeCompletion((CodeCompletion)message);
		}
		
		// From plugin
		else if (message instanceof PluginRunMainResult) {
			handlePluginRunMainResult((PluginRunMainResult)message);
		}
		else if (message instanceof PluginRunTestsResult) {
			handlePluginRunTestsResult((PluginRunTestsResult)message);
		}
		else if (message instanceof PluginErrorCheckingResult) {
			handlePluginErrorCheckingResult((PluginErrorCheckingResult)message);
		}
		else if (message instanceof PluginCodeCompletionResult) {
			handleUpdatePluginCodeCompletionResult((PluginCodeCompletionResult)message);
		}
		
		else {
			unhandled(message);
		}
	};
	
	
	// --- Handlers ---
	
	private void handleRunMain(RunMain runMain) {
		modelServices.getProblem(problemId).ifPresent(problem -> {
			CodeProblem codeProblem = (CodeProblem)problem;
			ImplementationFile mainImplementationFile = codeProblem.getMainImplementationFile();
			
			pluginActor.tell(new PluginRunMain(getQualifiedClassName(mainImplementationFile)), getSelf());
		});
	}
	
	private void handleRunTests(RunTests runTests) {
		modelServices.getProblem(problemId).ifPresent(problem -> {
			CodeProblem codeProblem = (CodeProblem)problem;
			codeProblem.getSourceCodeFiles().stream().filter(
				sourceCodeFile -> sourceCodeFile instanceof TestFile
			).findAny().ifPresent(sourceCodeFile -> {
				pluginActor.tell(new PluginRunTests(getQualifiedClassName(sourceCodeFile)), getSelf());
			});
		});
	}
	
	private void handleUpdateSourceCode(UpdateSourceCode updateSourceCode) {
		modelServices.getSourceCodeFile(updateSourceCode.fileId).ifPresent(sourceCodeFile -> {
			// TODO: Save to model
			pluginActor.tell(new PluginUpdateSourceCode(sourceCodeFile.getPackageName(), getFileName(sourceCodeFile), updateSourceCode.sourceCode), getSelf());
		});
	}
	
	private void handleCodeCompletion(CodeCompletion codeCompletion) {
		modelServices.getSourceCodeFile(codeCompletion.fileId).ifPresent(sourceCodeFile -> {
			pluginActor.tell(new PluginCodeCompletion(sourceCodeFile.getPackageName(), getFileName(sourceCodeFile), codeCompletion.offset), getSelf());
		});
	}
	
	private void handlePluginRunMainResult(PluginRunMainResult pluginRunMainResult) {
		consumerActor.tell(new RunMainResult(pluginRunMainResult.output), getSelf());
	}
	
	private void handlePluginRunTestsResult(PluginRunTestsResult pluginRunTestsResult) {
		consumerActor.tell(RunTestsResultMapper.createRunTestsResult(pluginRunTestsResult), getSelf());
	}
	
	private void handlePluginErrorCheckingResult(PluginErrorCheckingResult pluginErrorCheckingResult) {
		consumerActor.tell(ErrorCheckingResultMapper.createErrorCheckingResult(this::getId, pluginErrorCheckingResult), getSelf());
	}
	
	private void handleUpdatePluginCodeCompletionResult(PluginCodeCompletionResult pluginCodeCompletionResult) {
		consumerActor.tell(CodeCompletionResultMapper.createCodeCompletionResult(pluginCodeCompletionResult), getSelf());
	}
	
	
	// --- Private methods ---
	
	private void startPlugin() throws Exception {
		File tempFile = File.createTempFile("AssignmentSystem-", "");
		tempFile.delete();
		tempFile.mkdir();
		
		String command = startPluginCommands.getStartPluginCommand(tempFile, getRemoteAddressString());
		if (System.getenv().get("debug") != null || System.getProperty("debug") != null) {
			System.out.println("Run command: " + command); // TODO: Remove
		}
		else {
			commandRunner.runCommands(new String[] {command});
		}
	}
	
	private void bootstrapPlugin() {
		modelServices.getProblem(problemId).ifPresent(problem -> {
			CodeProblem codeProblem = (CodeProblem)problem;
			codeProblem.getSourceCodeFiles().stream().forEach(sourceCodeFile -> {
				pluginActor.tell(new PluginUpdateSourceCode(sourceCodeFile.getPackageName(), getFileName(sourceCodeFile), sourceCodeFile.getSourceCode()), getSelf());
			});
		});
	}
	
	private String getRemoteAddressString() {
		Address address;
		if (context().system().provider() instanceof RemoteActorRefProvider) {
			address = ((RemoteActorRefProvider)context().provider()).transport().addresses().head();
		} else {
		    throw new UnsupportedOperationException("Need RemoteActorRefProvider");
		}
		
		return getSelf().path().toStringWithAddress(address);
	}
	
	private String getId(String packageName, String fileName) {
		CodeProblem codeProblem = (CodeProblem)modelServices.getProblem(problemId).get();
		
		return codeProblem.getSourceCodeFiles().stream().filter(
			sourceCodeFile -> sourceCodeFile.getPackageName().equals(packageName) && getFileName(sourceCodeFile).equals(fileName)
		).findAny().map(
			sourceCodeFile -> sourceCodeFile.getId()
		).get();
	}
	
	private static String getQualifiedClassName(SourceCodeFile sourceCodeFile) {
		return String.format("%s.%s", sourceCodeFile.getPackageName(), getClassName(sourceCodeFile));
	}
	
	private static String getFileName(SourceCodeFile sourceCodeFile) {
		return new File(sourceCodeFile.getFilePath()).getName();
	}
	
	private static String getClassName(SourceCodeFile sourceCodeFile) {
		return getFileName(sourceCodeFile).replace(".java", "");
	}
}
