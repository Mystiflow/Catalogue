package io.mystiflow.cmdcatalogue;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.util.Collection;

public class CommandScriptAdapter implements JsonSerializer<CommandInstruction>, JsonDeserializer<CommandInstruction> {

    @Override
    public CommandInstruction deserialize(JsonElement element, Type type, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject object = element.getAsJsonObject();

        return CommandInstruction.builder()
                .name(object.get("name").getAsString())
                .commands(context.deserialize(object.get("commands"), Collection.class))
                .build();
    }

    @Override
    public JsonElement serialize(CommandInstruction commandInstruction, Type type, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.addProperty("name", commandInstruction.getName());
        object.add("commands", context.serialize(commandInstruction.getCommands()));
        return object;
    }
}
