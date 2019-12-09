package io.mystiflow.catalogue;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import io.mystiflow.catalogue.api.Catalogue;
import io.mystiflow.catalogue.api.Message;
import io.mystiflow.catalogue.serialisation.MessageAdapter;

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
        gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(Message.class, new MessageAdapter())
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
