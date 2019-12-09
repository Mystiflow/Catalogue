package io.mystiflow.cmdcatalogue.api;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Information about a command to use for an {@link CommandGroup}
 */
@Data
@AllArgsConstructor
public class Command {

    public enum Type {

        GROUP, COMMAND
    }

    public Type getType() {
        return Type.valueOf(typeString);
    }

    /**
     * The command to execute
     */
    private final String command;
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
