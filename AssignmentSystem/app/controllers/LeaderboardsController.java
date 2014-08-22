package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.leaderboards.leaderboards;

public class LeaderboardsController extends Controller {
    public static Result serveLeaderboards() {
        return ok(leaderboards.render());
    }
}
