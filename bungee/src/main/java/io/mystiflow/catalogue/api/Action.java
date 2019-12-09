package io.mystiflow.catalogue.api;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * Information about a action to use for an {@link Message}
 */
@Data
@RequiredArgsConstructor
public class Action {

    public enum Type {

        GROUP,
        COMMAND
    }

    public Type getType() {
        return Type.valueOf(typeString);
    }

    /**
     * The action to perform
     */
    private final String action;
    /**
     * The type of action
     */
    @SerializedName("type")
    private final String typeString;
    /**
     * The amount of times to execute this action
     */
    private final int iterations;
    /**
     * The delay to add to this action
     */
    private Delay delay;
}
