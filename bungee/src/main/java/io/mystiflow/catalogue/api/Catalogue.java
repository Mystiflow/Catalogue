package io.mystiflow.catalogue.api;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;

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
        return messages.stream().filter(group -> group.getName().equalsIgnoreCase(name)).findAny();
    }
}
