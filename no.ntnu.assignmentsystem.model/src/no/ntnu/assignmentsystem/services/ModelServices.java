package no.ntnu.assignmentsystem.services;

import java.util.List;
import java.util.Optional;

import no.ntnu.assignmentsystem.model.Assignment;
import no.ntnu.assignmentsystem.model.Course;
import no.ntnu.assignmentsystem.model.ModelLoader;
import no.ntnu.assignmentsystem.model.Participant;
import no.ntnu.assignmentsystem.model.Problem;
import no.ntnu.assignmentsystem.model.SourceCodeFile;
import no.ntnu.assignmentsystem.model.User;

import org.eclipse.emf.ecore.EObject;

public class ModelServices {
	private final ModelLoader modelLoader;
	
	public ModelServices(ModelLoader modelLoader) {
		this.modelLoader = modelLoader;
	}
	
	public Optional<? extends SourceCodeFile> getSourceCodeFile(String fileId) {
		return Optional.ofNullable((SourceCodeFile)findObject(fileId));
	}
	
	public Optional<? extends Problem> getProblem(String problemId) {
		return Optional.ofNullable((Problem)findObject(problemId));
	}
	
	public Optional<Assignment> getAssignment(String assignmentId) {
		return Optional.ofNullable((Assignment)findObject(assignmentId));
	}
	
	public Optional<Course> getCourse(String courseId) {
		return Optional.ofNullable((Course)findObject(courseId));
	}

	public Optional<? extends Participant> getParticipant(String courseId, String userId) {
		return getCourse(courseId).flatMap(
			course -> course.getParticipants().stream().filter(
				participant -> getUser(userId).equals(Optional.ofNullable(participant.getUser()))
			).findAny()
		);
	}
	
	public Optional<User> getUser(String userId) {
		return Optional.ofNullable((User)findObject(userId));
	}
	
	public List<User> getUsers() {
		return modelLoader.getUoD().getUsers();
	}
	
	
	// --- Private methods ---
	
	private EObject findObject(String id) {
		return modelLoader.findObject(id);
	}
}
