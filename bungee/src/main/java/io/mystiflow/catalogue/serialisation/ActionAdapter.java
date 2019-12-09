package io.mystiflow.catalogue.serialisation;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import io.mystiflow.catalogue.api.Action;

import java.lang.reflect.Type;

public class ActionAdapter implements JsonSerializer<Action>, JsonDeserializer<Action> {

    @Override
    public Action deserialize(JsonElement element, Type type, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject object = element.getAsJsonObject();

        return new Action(
                object.get("action").getAsString(),
                object.get("type").getAsString(),
                object.get("iterations").getAsInt()
        );
    }

    @Override
    public JsonElement serialize(Action action, Type type, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.addProperty("action", action.getAction());
        object.addProperty("type", action.getTypeString());
        object.addProperty("iterations", action.getIterations());
        return object;
    }
}
