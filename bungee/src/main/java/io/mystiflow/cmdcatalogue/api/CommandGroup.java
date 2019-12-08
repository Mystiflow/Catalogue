package io.mystiflow.cmdcatalogue.api;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.Singular;

import java.util.List;

/**
 * Set of instructions for running {@link Command}s.
 */
@Builder
@Data
public class CommandGroup {

    /**
     * The name of this group
     */
    @NonNull
    private final String name;
    /**
     * The commands to execute
     */
    @Singular
    private final List<Command> commands;
}
