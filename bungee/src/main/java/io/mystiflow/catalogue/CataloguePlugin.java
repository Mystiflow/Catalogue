package io.mystiflow.catalogue;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import io.mystiflow.catalogue.api.Action;
import io.mystiflow.catalogue.api.Catalogue;
import io.mystiflow.catalogue.api.Delay;
import io.mystiflow.catalogue.api.Message;
import io.mystiflow.catalogue.command.CatalogueCommand;
import io.mystiflow.catalogue.serialisation.ActionAdapter;
import io.mystiflow.catalogue.serialisation.DelayAdapter;
import io.mystiflow.catalogue.serialisation.MessageAdapter;
import lombok.Getter;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;
import java.nio.file.Files;

public class CataloguePlugin extends Plugin {

    @Getter
    private Gson gson;
    @Getter
    private Catalogue catalogue;

    @Override
    public void onEnable() {
        gson = new Gson()
                .newBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(Message.class, new MessageAdapter())
                .registerTypeAdapter(Action.class, new ActionAdapter())
                .registerTypeAdapter(Delay.class, new DelayAdapter())
                .create();

        reloadCatalogue();

        getProxy().getPluginManager().registerCommand(this, new CatalogueCommand(this));
    }

    public void reloadCatalogue() {
        if (getDataFolder().exists() || !getDataFolder().mkdir()) {
            File file = new File(getDataFolder(), "messages.json");
            if (!file.exists()) {
                try (InputStream in = getResourceAsStream(file.getName())) {
                    Files.copy(in, file.toPath());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

            try (InputStreamReader reader = new InputStreamReader(new FileInputStream(file));
                 JsonReader jsonReader = new JsonReader(reader)) {
                catalogue = gson.fromJson(jsonReader, Catalogue.class);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void saveCatalogue() {
        File file = new File(getDataFolder(), "messages.json");
        try {
            try (Writer writer = new FileWriter(file)) {
                gson.toJson(catalogue, writer);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
