package io.mystiflow.cmdcatalogue;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import io.mystiflow.cmdcatalogue.api.Catalogue;
import io.mystiflow.cmdcatalogue.api.Command;
import io.mystiflow.cmdcatalogue.api.CommandGroup;
import io.mystiflow.cmdcatalogue.command.CatalogueCommand;
import io.mystiflow.cmdcatalogue.serialisation.CommandAdapter;
import io.mystiflow.cmdcatalogue.serialisation.CommandScriptAdapter;
import lombok.Getter;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
                .registerTypeAdapter(CommandGroup.class, new CommandScriptAdapter())
                .registerTypeAdapter(Command.class, new CommandAdapter())
                .create();

        loadCatalogue();

        getProxy().getPluginManager().registerCommand(this, new CatalogueCommand(this));
    }

    private void loadCatalogue() {
        if (getDataFolder().exists() || !getDataFolder().mkdir()) {
            File file = new File(getDataFolder(), "instructions.json");
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
}
