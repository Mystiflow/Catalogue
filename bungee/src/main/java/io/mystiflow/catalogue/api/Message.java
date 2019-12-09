package io.mystiflow.catalogue.api;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.Singular;

import java.util.List;

/**
 * Set of instructions for running {@link Action}s.
 */
@Builder
@Data
public class Message {

    /**
     * The name of this group
     */
    @NonNull
    private final String name;
    /**
     * The actions to execute
     */
    @Singular
    private final List<Action> actions;
}