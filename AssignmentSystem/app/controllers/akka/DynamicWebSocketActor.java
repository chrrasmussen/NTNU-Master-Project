package controllers.akka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.paranamer.ParanamerModule;

import java.io.IOException;
import java.util.Map;

public abstract class DynamicWebSocketActor extends WebSocketActor {
    private final ObjectMapper objectMapper = new ObjectMapper() {{
        configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        registerModule(new ParanamerModule());
    }};

    @Override
    protected void actorInputDispatcher(Object message) {
        System.out.println("[WS] Received message from actor:" + message);

        try {
            String type = getClassMapping().entrySet().stream().filter(
                entry -> entry.getValue().equals(message.getClass())
            ).findAny().get().getKey();

            MessageWrapper<?> messageWrapper = new MessageWrapper<>(type, message);
            String data = objectMapper.writeValueAsString(messageWrapper);

            sendData(data);
        } catch (JsonProcessingException e) {
            // TODO: Write error to client instead?
            e.printStackTrace();
        }
    }

    @Override
    protected void webSocketInputDispatcher(String data) {
        System.out.println("[WS] Received data from web socket:" + data);

        try {
            JsonNode rootNode = objectMapper.readTree(data);
            JsonNode typeNode = rootNode.get(MessageWrapper.typeKey);
            JsonNode dataNode = rootNode.get(MessageWrapper.dataKey);

            String type = typeNode.asText();
            Class clazz = getClassMapping().get(type);

            Object message = objectMapper.treeToValue(dataNode, clazz);

            sendMessage(message);
        } catch (IOException e) {
            // TODO: Write error to actor instead?
            e.printStackTrace();
        }
    }

    protected abstract void sendMessage(Object message);

    protected abstract void sendData(String data);

    protected abstract Map<String, Class> getClassMapping();

    public static class MessageWrapper<T> {
        public static final String typeKey = "type";
        public static final String dataKey = "data";

        public String type;
        public T data;

        public MessageWrapper(String type, T data) {
            this.type = type;
            this.data = data;
        }
    }
}
