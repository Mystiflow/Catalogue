package io.mystiflow.cmdcatalogue.serialisation;

import com.google.common.reflect.TypeToken;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import io.mystiflow.cmdcatalogue.api.Command;
import io.mystiflow.cmdcatalogue.api.CommandGroup;

import java.lang.reflect.Type;
import java.util.List;

public class CommandScriptAdapter implements JsonSerializer<CommandGroup>, JsonDeserializer<CommandGroup> {

    @Override
    public CommandGroup deserialize(JsonElement element, Type type, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject object = element.getAsJsonObject();

        return CommandGroup.builder()
                .name(object.get("name").getAsString())
                .commands(context.deserialize(object.get("commands"), new TypeToken<List<Command>>() {
                }.getType()))
                .build();
    }

    @Override
    public JsonElement serialize(CommandGroup instruction, Type type, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.addProperty("name", instruction.getName());
        object.add("commands", context.serialize(instruction.getCommands()));
        return object;
    }
}
