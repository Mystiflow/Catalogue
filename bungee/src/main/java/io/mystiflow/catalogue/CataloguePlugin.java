package io.mystiflow.catalogue;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.mystiflow.catalogue.api.Action;
import io.mystiflow.catalogue.api.Catalogue;
import io.mystiflow.catalogue.api.Delay;
import io.mystiflow.catalogue.api.Message;
import io.mystiflow.catalogue.serialisation.ActionAdapter;
import io.mystiflow.catalogue.serialisation.DelayAdapter;
import io.mystiflow.catalogue.serialisation.MessageAdapter;
import lombok.Getter;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class CataloguePlugin extends Plugin {

    @Getter
    private static CataloguePlugin plugin;
    @Getter
    private Gson gson;
    @Getter
    private Catalogue catalogue;
    private File catalogueFile;

    @Override
    public void onEnable() {
        CataloguePlugin.plugin = this;

        gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(Message.class, new MessageAdapter())
                .registerTypeAdapter(Action.class, new ActionAdapter())
                .registerTypeAdapter(Delay.class, new DelayAdapter())
                .create();

        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }

        catalogueFile = new File(getDataFolder(), "messages.json");

        // Copy file from JAR to plugin folder
        if (!catalogueFile.exists()) {
            try (InputStream in = getResourceAsStream(catalogueFile.getName())) {
                Files.copy(in, catalogueFile.toPath());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        try {
            catalogue = Catalogue.load(catalogueFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        getProxy().getPluginManager().registerCommand(this, new CatalogueCommand(this));
        getProxy().getPluginManager().registerListener(this, new CatalogueListener(this));
    }

    @Override
    public void onDisable() {
        CataloguePlugin.plugin = null;
    }

    public void reloadCatalogue() throws IOException {
        catalogue = Catalogue.load(catalogueFile);
    }

    public void saveCatalogue() throws IOException {
        catalogue.save(catalogueFile);
    }
}
