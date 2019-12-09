package io.mystiflow.catalogue.api;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(builderClassName = "Builder")
public final class Delay {

    /**
     * Delay in Minecraft game ticks
     */
    private final long delay;
    /**
     * Repeating delay in Minecraft game ticks
     */
    @lombok.Builder.Default
    private final long repeatDelay = -1L;

    public boolean isRepeating() {
        return repeatDelay != -1L;
    }
}
