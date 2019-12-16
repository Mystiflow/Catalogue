package io.mystiflow.catalogue.api;

import com.google.gson.stream.JsonReader;
import io.mystiflow.catalogue.CataloguePlugin;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.util.List;
import java.util.Optional;

/**
 * Class to store and manage the directory of messages
 */
@Data
@Builder(builderClassName = "Builder")
public class Catalogue {

    /**
     * List of messages stored by this catalogue
     */
    @Singular
    private final List<Message> messages;

    public Optional<Message> getMessage(String name) {
        return messages.stream().filter(message -> message.getName().equalsIgnoreCase(name)).findAny();
    }
}
