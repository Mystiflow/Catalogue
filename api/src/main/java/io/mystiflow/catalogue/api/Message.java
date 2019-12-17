package io.mystiflow.catalogue.api;

import lombok.Getter;
import lombok.NonNull;

import java.util.List;

/**
 * Set of instructions for running {@link Action}s.
 */
@Getter
public class Message implements Actionable {

    /**
     * The name of this message
     */
    @NonNull
    private final String name;
    /**
     * The actions to execute
     */
    private final List<String> actions;

    public Message(String name, List<String> actions) {
        this.name = name;
        this.actions = actions;
    }
}
