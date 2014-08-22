package no.ntnu.assignmentsystem.services;

import java.io.File;

import no.ntnu.assignmentsystem.model.ModelLoader;
import no.ntnu.assignmentsystem.model.impl.XmiModelLoader;
import no.ntnu.assignmentsystem.services.akka.messages.CodeCompletion;
import no.ntnu.assignmentsystem.services.akka.messages.CodeCompletionProposal;
import no.ntnu.assignmentsystem.services.akka.messages.CodeCompletionResult;
import no.ntnu.assignmentsystem.services.akka.messages.ErrorCheckingResult;
import no.ntnu.assignmentsystem.services.akka.messages.NotifyOnReady;
import no.ntnu.assignmentsystem.services.akka.messages.ProblemMarker;
import no.ntnu.assignmentsystem.services.akka.messages.ProblemMarkersFile;
import no.ntnu.assignmentsystem.services.akka.messages.Ready;
import no.ntnu.assignmentsystem.services.akka.messages.RunMainResult;
import no.ntnu.assignmentsystem.services.akka.messages.RunTestsResult;
import no.ntnu.assignmentsystem.services.akka.messages.TestResult;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;

public class Main {
	public static void main(String[] args) {
		ModelLoader modelLoader = new XmiModelLoader(new File("model/UoD.xmi"));
		ModelServices modelServices = new ModelServices(modelLoader);
		Services services = new MainServices(modelServices);
		
//		System.out.println(services.getAssignments("userId"));
//		System.out.println(services.getProblem("userId", "3")); // QuizProblem
//		System.out.println(services.getProblem("10", "4")); // CodeProblem
		
//		System.out.println(services.authenticate("christir@stud.ntnu.no", "christir"));
		
		ActorSystem testActorSystem = ActorSystem.create();
		
		ActorRef editorActor = services.createEditor("10", "4");
		testActorSystem.actorOf(Props.create(TestActor.class, editorActor));
	}
	
	public static class TestActor extends UntypedActor {
		private final ActorRef editorActor;
		
		public TestActor(ActorRef editorActor) {
			this.editorActor = editorActor;
		}
		
		@Override
		public void preStart() throws Exception {
			super.preStart();
			
			editorActor.tell(new NotifyOnReady(), getSelf());
//			editorActor.tell(new RunMain(), getSelf());
//			editorActor.tell(new RunTests(), getSelf());
		}
		
		@Override
		public void onReceive(Object message) throws Exception {
			System.out.println(getSelf() + ": Received message:" + message);
			
			if (message instanceof Ready) {
				handleReady();
			}
			else if (message instanceof RunMainResult) {
				handleRunMainResult((RunMainResult)message);
			}
			else if (message instanceof RunTestsResult) {
				handleRunTestsResult((RunTestsResult)message);
			}
			else if (message instanceof CodeCompletionResult) {
				handleCodeCompletionResult((CodeCompletionResult)message);
			}
			else if (message instanceof ErrorCheckingResult) {
				handleErrorCheckingResult((ErrorCheckingResult)message);
			}
		}
		
		private void handleReady() {
			System.out.println("Editor ready");
//			String sourceCode = "package stateandbehavior;\n" +
//					"public class Main {\n" +
//					"    public static void main(String[] args) {\n" +
//					"        System.out.printlns(\"Hello, World3!\");\n" +
//					"    }\n" +
//					"}\n";
//			editorActor.tell(new UpdateSourceCode("5", sourceCode), getSelf());
			editorActor.tell(new CodeCompletion("5", 0), getSelf());
//			editorActor.tell(new RunMain(), getSelf());
		}
		
		private void handleRunMainResult(RunMainResult result) {
			System.out.println(result.output);
		}
		
		private void handleRunTestsResult(RunTestsResult result) {
			for (TestResult testResult : result.testResults) {
				System.out.println(testResult.methodName + "-" + testResult.status.name());
			}
		}
		
		private void handleCodeCompletionResult(CodeCompletionResult result) {
			for (CodeCompletionProposal proposal : result.proposals) {
				System.out.println(proposal.completion);
			}
		}
		
		private void handleErrorCheckingResult(ErrorCheckingResult result) {
			System.out.println("ErrorCheckingResult");
			for (ProblemMarkersFile file : result.files) {
				System.out.println("Found problems in:" + file.fileId);
				for (ProblemMarker problemMarker : file.problemMarkers) {
					System.out.println(problemMarker.lineNumber + " " + problemMarker.description + " " + problemMarker.type);					
				}
			}
			System.out.println("---");
		}
	}
}
