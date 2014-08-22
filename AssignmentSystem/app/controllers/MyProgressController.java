package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.studentProgress.studentProgress;

public class MyProgressController extends Controller {
    public static Result serveStudentProgress() {
        return ok(studentProgress.render());
    }
}
