package controllers.akka;

import akka.actor.ActorRef;
import no.ntnu.assignmentsystem.services.akka.messages.*;

import java.util.HashMap;
import java.util.Map;

public class ServicesWebSocketActor extends DynamicWebSocketActor {
    private final ActorRef workspaceActor;

    public ServicesWebSocketActor(ActorRef workspaceActor) {
        this.workspaceActor = workspaceActor;
    }

    @Override
    protected void sendMessage(Object message) {
        System.out.println("[WS] Sending message:" + message);
        workspaceActor.tell(message, getSelf());
    }

    @Override
    protected void sendData(String data) {
        System.out.println("[WS] Sending data:" + data);
        out.write(data);
    }

    @Override
    protected Map<String, Class> getClassMapping() {
        Map<String, Class> classMapping = new HashMap<>();
        classMapping.put("notifyOnReady", NotifyOnReady.class);
        classMapping.put("ready", Ready.class);
        classMapping.put("runMain", RunMain.class);
        classMapping.put("runMainResult", RunMainResult.class);
        classMapping.put("runTests", RunTests.class);
        classMapping.put("runTestsResult", RunTestsResult.class);
        classMapping.put("codeCompletion", CodeCompletion.class);
        classMapping.put("codeCompletionResult", CodeCompletionResult.class);
        classMapping.put("updateSourceCode", UpdateSourceCode.class);
        classMapping.put("errorCheckingResult", ErrorCheckingResult.class);

        return classMapping;
    }
}
