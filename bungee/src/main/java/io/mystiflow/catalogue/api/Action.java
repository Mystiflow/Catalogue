package io.mystiflow.catalogue.api;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * Information about a action to use for an {@link Message}
 */
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Action implements Actionable {

    /**
     * The name of this action
     */
    private final String name;
    /**
     * The action to perform
     */
    private final String action;
    /**
     * The amount of times to execute this action
     */
    private final int iterations;
    /**
     * The delay to add to this action
     */
    private Delay delay;

    @Expose(deserialize = false, serialize = false)
    private final Map<String, Integer> activeDelays = new HashMap<>();
}
