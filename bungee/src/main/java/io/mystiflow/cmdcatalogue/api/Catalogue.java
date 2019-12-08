package io.mystiflow.cmdcatalogue.api;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Singular;

import java.util.List;
import java.util.Optional;

/**
 * Class to store and manage a list of instructions
 */
@RequiredArgsConstructor
@Data
@Builder(builderClassName = "Builder")
public class Catalogue {

    /**
     * List of instructions stored by this catalogue
     */
    @Singular
    private final List<CommandGroup> instructions;

    public Optional<CommandGroup> getInstruction(String name) {
        return instructions.stream().filter(group -> group.getName().equalsIgnoreCase(name)).findAny();
    }
}
