package controllers.akka;

import akka.actor.UntypedActorWithStash;
import akka.japi.Procedure;
import play.mvc.WebSocket;

public abstract class WebSocketActor extends UntypedActorWithStash {
    protected WebSocket.In<String> in;
    protected WebSocket.Out<String> out;

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Init) {
            handleInit((Init)message);

            unstashAll();
            getContext().become(onReceiveAfterInit, false);
        }
        else {
            stash();
        }
    }

    Procedure<Object> onReceiveAfterInit = message -> {
        actorInputDispatcher(message);
    };

    private void handleInit(Init init) {
        in = init.in;
        out = init.out;

        in.onMessage(this::webSocketInputDispatcher);
    }

    protected abstract void actorInputDispatcher(Object message);

    protected abstract void webSocketInputDispatcher(String data);

    public static class Init {
        public final WebSocket.In<String> in;
        public final WebSocket.Out<String> out;

        public Init(WebSocket.In<String> in, WebSocket.Out<String> out) {
            this.in = in;
            this.out = out;
        }
    }
}

