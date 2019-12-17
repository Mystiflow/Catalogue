package io.mystiflow.catalogue.api.loader.json.adapter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import io.mystiflow.catalogue.api.Action;
import io.mystiflow.catalogue.api.Delay;

import java.lang.reflect.Type;

public class ActionAdapter implements JsonSerializer<Action>, JsonDeserializer<Action> {

    @Override
    public Action deserialize(JsonElement element, Type type, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject object = element.getAsJsonObject();

        Action action = new Action(
                object.get("name").getAsString(),
                object.get("action").getAsString(),
                object.get("iterations").getAsInt()
        );
        if (object.has("delay")) {
            action.setDelay(context.deserialize(object.get("delay"), Delay.class));
        }
        return action;
    }

    @Override
    public JsonElement serialize(Action action, Type type, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.addProperty("name", action.getName());
        object.addProperty("action", action.getAction());
        object.addProperty("iterations", action.getIterations());
        if (action.getDelay() != null) {
            object.add("delay", context.serialize(action.getDelay(), Delay.class));
        }
        return object;
    }
}
