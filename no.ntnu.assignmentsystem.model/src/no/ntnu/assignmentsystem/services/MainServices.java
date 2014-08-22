package no.ntnu.assignmentsystem.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import no.ntnu.assignmentsystem.model.Student;
import no.ntnu.assignmentsystem.services.akka.EditorActor;
import no.ntnu.assignmentsystem.services.mapping.AssignmentViewFactory;
import no.ntnu.assignmentsystem.services.mapping.ProblemViewFactory;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl.Container;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class MainServices extends Container implements Services {
	private static final String courseId = "1";
	private static final String actorSystemName = "AssignmentSystem";

	private final ModelServices modelServices;
	private final ServicesPackage servicesPackage;
	
	private final ActorSystem actorSystem = ActorSystem.create(actorSystemName);
	
	public MainServices(ModelServices modelServices) {
		this.modelServices = modelServices;
		// Initialize services package
	    servicesPackage = ServicesPackage.eINSTANCE;
	    servicesPackage.eClass();
	}
	
	@Override
	public ActorRef createEditor(String userId, String problemId) {
		// TODO: Create a unique name for the actor (to be able to reuse it later)
		return actorSystem.actorOf(Props.create(EditorActor.class, modelServices, userId, problemId));
	}
	
	@Override
	public List<AssignmentView> getAssignments(String userId) {
		return modelServices.getCourse(courseId).map(
			course -> course.getAssignments().stream().map(
				AssignmentViewFactory::createAssignmentView
			).collect(Collectors.toList())
		).orElse(null);
	}
	
	@Override
	public List<ProblemView> getProblems(String userId, String assignmentId) {
		return modelServices.getParticipant(courseId, userId).flatMap(
			participant -> (Optional<List<ProblemView>>)modelServices.getAssignment(assignmentId).map(
				assignment -> assignment.getProblems().stream().map(
					problem -> ProblemViewFactory.createProblemView((Student)participant, problem)
				).collect(Collectors.toList())
			)
		).orElse(null);
	}
	
	@Override
	public ExtendedProblemView getProblem(String userId, String problemId) {
		return (ExtendedProblemView)modelServices.getParticipant(courseId, userId).flatMap(
			participant -> modelServices.getProblem(problemId).map(
				problem -> ProblemViewFactory.createExtendedProblemView((Student)participant, problem)
			)
		).orElse(null);
	}
	
	@Override
	public List<LeaderboardEntryView> getOverallLeaderboard() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LeaderboardEntryView> getAssignmentLeaderboard(String assignmentId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AchievementView> getAchievements(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String authenticate(String username, String password) {
		return modelServices.getUsers().stream().filter(
			user -> user.getEmail().equals(username) && user.getPassword().equals(password)
		).findAny().map(
			user -> user.getId()
		).orElse(null);
	}
}
