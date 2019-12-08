package io.mystiflow.cmdcatalogue.api;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Information about a command to use for an {@link CommandGroup}
 */
@Data
@AllArgsConstructor
public class Command {

    /**
     * The command to execute
     */
    private final String command;
    /**
     * The amount of times to execute this command
     */
    private final int iterations;
}
