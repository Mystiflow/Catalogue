package io.mystiflow.catalogue.serialisation;

import com.google.common.reflect.TypeToken;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import io.mystiflow.catalogue.api.Action;
import io.mystiflow.catalogue.api.Message;

import java.lang.reflect.Type;
import java.util.List;

public class MessageAdapter implements JsonSerializer<Message>, JsonDeserializer<Message> {

    @Override
    public Message deserialize(JsonElement element, Type type, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject object = element.getAsJsonObject();

        Message message = Message.builder()
                .name(object.get("name").getAsString())
                .actions(context.deserialize(object.get("actions"), new TypeToken<List<Action>>() {
                }.getType()))
                .build();

        if (message.getActions().stream()
                .anyMatch(action -> action.getType() == Action.Type.MESSAGE && action.getAction().equals(message.getName()))) {
            throw new JsonParseException("Message '" + message.getName() + "' has itself as an action");
        }

        return message;
    }

    @Override
    public JsonElement serialize(Message message, Type type, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.addProperty("name", message.getName());
        object.add("actions", context.serialize(message.getActions()));
        return object;
    }
}
