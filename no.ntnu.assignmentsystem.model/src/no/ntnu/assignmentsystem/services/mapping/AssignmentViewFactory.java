package no.ntnu.assignmentsystem.services.mapping;

import no.ntnu.assignmentsystem.model.Assignment;
import no.ntnu.assignmentsystem.services.AssignmentView;

public class AssignmentViewFactory extends BaseViewFactory {
	public static AssignmentView createAssignmentView(Assignment assignment) {
		AssignmentView assignmentView = getFactory().createAssignmentView();
		Mapper.copyAttributes(assignment, assignmentView);
		return assignmentView;
	}
}
