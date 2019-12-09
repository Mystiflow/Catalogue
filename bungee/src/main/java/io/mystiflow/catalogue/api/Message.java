package io.mystiflow.catalogue.api;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.Singular;

import java.util.List;

/**
 * Set of instructions for running {@link Action}s.
 */
@Data
@Builder(builderClassName = "Builder")
public class Message {

    /**
     * The name of this message
     */
    @NonNull
    private final String name;
    /**
     * The actions to execute
     */
    @Singular
    private final List<Action> actions;
}
