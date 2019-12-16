package io.mystiflow.catalogue.loader;

import com.google.gson.stream.JsonReader;
import io.mystiflow.catalogue.CataloguePlugin;
import io.mystiflow.catalogue.api.Catalogue;
import io.mystiflow.catalogue.api.CatalogueLoader;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;

@RequiredArgsConstructor
public class JsonCatalogueLoader implements CatalogueLoader {

    private final File file;

    @Override
    public Catalogue load() throws IOException {
        try (InputStreamReader reader = new InputStreamReader(new FileInputStream(file));
             JsonReader jsonReader = new JsonReader(reader)) {
            return CataloguePlugin.getPlugin().getGson().fromJson(jsonReader, Catalogue.class);
        }
    }

    @Override
    public void save() throws IOException {
        try (Writer writer = new FileWriter(file)) {
            CataloguePlugin.getPlugin().getGson().toJson(this, writer);
        }
    }
}
