package io.mystiflow.catalogue;

import io.mystiflow.catalogue.api.Catalogue;
import io.mystiflow.catalogue.api.CatalogueLoader;
import io.mystiflow.catalogue.loader.json.JsonCatalogueLoader;
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
    private CatalogueLoader defaultLoader;
    @Getter
    private Catalogue catalogue;

    @Override
    public void onEnable() {
        CataloguePlugin.plugin = this;




        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }

        File jsonCatalogueFile = new File(getDataFolder(), "catalogue.json");

        // Copy file from JAR to plugin folder
        if (!jsonCatalogueFile.exists()) {
            try (InputStream in = getResourceAsStream(jsonCatalogueFile.getName())) {
                Files.copy(in, jsonCatalogueFile.toPath());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        defaultLoader = new JsonCatalogueLoader(jsonCatalogueFile);
        try {
            catalogue = defaultLoader.load();
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
}
