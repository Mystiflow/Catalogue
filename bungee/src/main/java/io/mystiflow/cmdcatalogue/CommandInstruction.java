package io.mystiflow.cmdcatalogue;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.List;

@Builder
@Data
public class CommandInstruction {

    private final String name;
    @Singular
    private final List<Command> commands;
}
