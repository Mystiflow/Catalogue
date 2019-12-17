package io.mystiflow.catalogue.loader.json.adapter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import io.mystiflow.catalogue.api.Delay;

import java.lang.reflect.Type;

public class DelayAdapter implements JsonSerializer<Delay>, JsonDeserializer<Delay> {

    @Override
    public Delay deserialize(JsonElement element, Type type, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject object = element.getAsJsonObject();

        return Delay.builder()
                .delay(object.get("delay").getAsLong())
                .repeatDelay(object.get("repeatDelay").getAsLong())
                .build();
    }

    @Override
    public JsonElement serialize(Delay delay, Type type, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.addProperty("delay", delay.getDelay());
        object.addProperty("repeatDelay", delay.getRepeatDelay());
        return object;
    }
}
