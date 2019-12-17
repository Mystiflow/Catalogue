package io.mystiflow.catalogue.api.loader.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import io.mystiflow.catalogue.api.Action;
import io.mystiflow.catalogue.api.Catalogue;
import io.mystiflow.catalogue.api.CatalogueLoader;
import io.mystiflow.catalogue.api.Delay;
import io.mystiflow.catalogue.api.Message;
import io.mystiflow.catalogue.api.loader.json.adapter.ActionAdapter;
import io.mystiflow.catalogue.api.loader.json.adapter.DelayAdapter;
import io.mystiflow.catalogue.api.loader.json.adapter.MessageAdapter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;

public class JsonCatalogueLoader implements CatalogueLoader {

    private final Gson gson;
    private final File file;

    public JsonCatalogueLoader(File file) {
        this.file = file;
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(Message.class, new MessageAdapter())
                .registerTypeAdapter(Action.class, new ActionAdapter())
                .registerTypeAdapter(Delay.class, new DelayAdapter())
                .create();
    }

    @Override
    public Catalogue load() throws IOException {
        try (InputStreamReader reader = new InputStreamReader(new FileInputStream(file));
             JsonReader jsonReader = new JsonReader(reader)) {
            return gson.fromJson(jsonReader, Catalogue.class);
        }
    }

    @Override
    public void save() throws IOException {
        try (Writer writer = new FileWriter(file)) {
            gson.toJson(this, writer);
        }
    }
}
