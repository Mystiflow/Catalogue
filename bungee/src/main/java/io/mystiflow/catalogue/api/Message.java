package io.mystiflow.catalogue.api;

import io.mystiflow.catalogue.CataloguePlugin;
import lombok.Getter;
import lombok.NonNull;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Set of instructions for running {@link Action}s.
 */
@Getter
public class Message implements Actionable {

    /**
     * The name of this message
     */
    @NonNull
    private final String name;
    /**
     * The actions to execute
     */
    private final List<Actionable> actionables;

    public Message(String name, List<String> actionables) {
        this.name = name;
        this.actionables = actionables.stream().map(s -> MESSAGE_OR_ACTION.apply(s)).collect(Collectors.toList()); //TODO MESSY
    }

    private static Function<String, Actionable> MESSAGE_OR_ACTION = new Function<String, Actionable>() {
        @Override
        public Actionable apply(String s) {
            Actionable actionable = CataloguePlugin.getPlugin().getCatalogue().getAction(s).orElse(null);
            if (actionable == null) actionable = CataloguePlugin.getPlugin().getCatalogue().getMessage(s).orElse(null);
            return actionable;
        }
    };
}
