package io.mystiflow.catalogue.api;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Information about a action to use for an {@link Message}
 */
@Data
@AllArgsConstructor
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
     * The type of command
     */
    @SerializedName("type")
    private final String typeString;
    /**
     * The amount of times to execute this command
     */
    private final int iterations;
}
