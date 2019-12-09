package io.mystiflow.catalogue.api;

import lombok.Data;

@Data
public final class Delay {

    private final long delay;
    private final long repeatDelay;

    public Delay(long delay) {
        this(delay, -1);
    }

    public boolean isRepeating() {
        return repeatDelay != -1L;
    }
}