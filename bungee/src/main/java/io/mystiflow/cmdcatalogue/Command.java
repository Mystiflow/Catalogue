package io.mystiflow.cmdcatalogue;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Command {

    private final String command;
    private final int count;
}
