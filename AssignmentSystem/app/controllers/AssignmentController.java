package controllers;

import controllers.akka.ServicesWebSocketActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import global.Global;
import no.ntnu.assignmentsystem.services.CodeProblemView;
import play.libs.Akka;
import play.libs.F;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.WebSocket;
import views.html.assignment.aProblem;
import views.html.assignment.allAssignments;

public class AssignmentController extends Controller {

    public static Result serveAllAssignments() {
//        return ok(allAssignments.render(Global.services.getAssignments("10")));
        return ok(allAssignments.render());
    }

    public static Result serveProblem(String aID, String pID) {
        CodeProblemView problem = (CodeProblemView)Global.services.getProblem("10", "4");
        return ok(aProblem.render(aID, pID, problem));
    }

    public static WebSocket<String> openEditorSocket(String pID) {
        ActorRef workspaceActor = Global.services.createEditor("10", "4");
        ActorRef webSocketActor = Akka.system().actorOf(Props.create(ServicesWebSocketActor.class, workspaceActor));

        return new WebSocket<String>() {
            public void onReady(WebSocket.In<String> in, WebSocket.Out<String> out) {
                webSocketActor.tell(new ServicesWebSocketActor.Init(in, out), ActorRef.noSender());
            }
        };
    }
}
