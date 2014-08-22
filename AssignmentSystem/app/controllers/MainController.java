package controllers;

import play.Routes;
import play.mvc.Controller;
import play.mvc.Result;

import views.html.assignment.aProblem;
import views.html.assignment.allAssignments;

public class MainController extends Controller {

    public static Result index() {
//        Services services = new ServicesImpl(new File("../AssignmentSystem.model/model/UoD.xmi"));
//        List<CourseView> courses = services.getCourses();
//        return ok(views.html.index.render(courses.get(0).getTitle()));
        return redirect(routes.AssignmentController.serveAllAssignments());
    }

    /* Expose the routes to Javascript */
    public static Result javascriptRoutes() {
        response().setContentType("text/javascript");
        return ok(
            Routes.javascriptRouter("jsRoutes",
                routes.javascript.AssignmentController.openEditorSocket()
            )
        );
    }

}
