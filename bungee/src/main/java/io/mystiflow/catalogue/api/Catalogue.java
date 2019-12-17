package io.mystiflow.catalogue.api;

import lombok.Data;

import java.util.List;
import java.util.Optional;

/**
 * Class to store and manage the directory of messages
 */
@Data
public class Catalogue {

    /**
     * List of actions stored by this catalogue
     */
    private final List<Action> actions;
    /**
     * List of messages stored by this catalogue
     */
    private final List<Message> messages;

    public Catalogue(final List<Action> actions, final List<Message> messages) {
        this.actions = actions;
        this.messages = messages;
    }

    public Optional<Message> getMessage(String name) {
        return messages.stream().filter(message -> message.getName().equalsIgnoreCase(name)).findAny();
    }

    public Optional<Action> getAction(String name) {
        return actions.stream().filter(action -> action.getName().equalsIgnoreCase(name)).findAny();
    }
}
