package io.mystiflow.catalogue.api;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * Information about a action to use for an {@link Message}
 */
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Action {

    public enum Type {

        MESSAGE,
        COMMAND
    }

    /**
     * The action to perform
     */
    private final String action;
    /**
     * The type of action
     */
    private final Type type;
    /**
     * The amount of times to execute this action
     */
    private final int iterations;
    /**
     * The delay to add to this action
     */
    private Delay delay;
}
