package io.mystiflow.cmdcatalogue.serialisation;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import io.mystiflow.cmdcatalogue.api.Command;

import java.lang.reflect.Type;

public class CommandAdapter implements JsonSerializer<Command>, JsonDeserializer<Command> {

    @Override
    public Command deserialize(JsonElement element, Type type, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject object = element.getAsJsonObject();

        return new Command(
                object.get("command").getAsString(),
                object.get("iterations").getAsInt()
        );
    }

    @Override
    public JsonElement serialize(Command command, Type type, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.addProperty("command", command.getCommand());
        object.addProperty("iterations", command.getIterations());
        return object;
    }
}
