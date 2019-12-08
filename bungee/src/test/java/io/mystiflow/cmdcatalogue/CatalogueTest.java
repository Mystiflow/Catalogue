package io.mystiflow.cmdcatalogue;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import io.mystiflow.cmdcatalogue.api.Catalogue;
import io.mystiflow.cmdcatalogue.api.CommandGroup;
import io.mystiflow.cmdcatalogue.serialisation.CommandScriptAdapter;

import java.io.InputStreamReader;

public class CatalogueTest {

    static Gson gson;
    static Catalogue catalogue;

    public static void main(String[] args) {
        createDefaultCatalogue();
    }

    /**
     * Prints a JSON string with two basic command instructions
     */
    private static void createDefaultCatalogue() {
        gson = new Gson()
                .newBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(CommandGroup.class, new CommandScriptAdapter())
                .create();

        loadCatalogue();
    }

    private static void loadCatalogue() {
        try (InputStreamReader reader = new InputStreamReader(CatalogueTest.class.getResourceAsStream("/instructions.json"));
             JsonReader jsonReader = new JsonReader(reader)) {
            catalogue = gson.fromJson(jsonReader, Catalogue.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
