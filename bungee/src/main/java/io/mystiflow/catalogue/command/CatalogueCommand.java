package io.mystiflow.catalogue.command;

import com.google.common.base.Joiner;
import io.mystiflow.catalogue.CataloguePlugin;
import io.mystiflow.catalogue.api.Action;
import io.mystiflow.catalogue.api.Delay;
import io.mystiflow.catalogue.api.Message;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class CatalogueCommand extends net.md_5.bungee.api.plugin.Command {

    private final CataloguePlugin plugin;

    public CatalogueCommand(CataloguePlugin plugin) {
        super("bcatalogue");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length != 0) {
            if (args[0].equalsIgnoreCase("execute")) {
                if (args.length == 1) {
                    sender.sendMessage(ChatColor.YELLOW + "/" + getName() + " execute {message}");
                    return;
                }

                Optional<Message> optionalMessage = plugin.getCatalogue().getMessage(args[1]);

                if (!optionalMessage.isPresent()) {
                    sender.sendMessage(ChatColor.RED + "Message '" + args[1] + "' not found");
                    return;
                }

                executeMessage(sender, optionalMessage.get());
                return;
            }

            if (args[0].equalsIgnoreCase("list")) {
                sender.sendMessage(ChatColor.YELLOW + "Messages: " + (Joiner.on(", ").join(
                        plugin.getCatalogue().getMessages()
                                .stream()
                                .map(Message::getName)
                                .collect(Collectors.toList()))));
                return;
            }

            if (args[0].equalsIgnoreCase("reload")) {
                plugin.reloadCatalogue();
                sender.sendMessage(ChatColor.YELLOW + "Reloaded catalogue from config");
                return;
            }
        }

        sender.sendMessage(ChatColor.YELLOW + "/" + getName() + " <execute|list|reload>");
    }

    private void executeMessage(CommandSender sender, Message group) {
        List<Action> actions = group.getActions();
        for (Action action : actions) {
            Runnable runnable = () -> {
                if (action.getType() == Action.Type.COMMAND) {
                    for (int i = 0; i < action.getIterations(); i++) {
                        plugin.getProxy().getPluginManager().dispatchCommand(sender, action.getAction());
                    }
                } else if (action.getType() == Action.Type.GROUP) {
                    plugin.getCatalogue().getMessage(action.getAction()).ifPresent(commandGroup ->
                            {
                                for (int i = 0; i < action.getIterations(); i++) {
                                    executeMessage(sender, commandGroup);
                                }
                            }
                    );
                }
            };

            Delay delay = action.getDelay();
            if (delay == null) {
                runnable.run();
            } else {
                plugin.getProxy().getScheduler().schedule(
                        plugin, runnable, delay.getDelay() * 50L, delay.getRepeatDelay() * 50L, TimeUnit.MILLISECONDS
                );
            }
        }
    }
}
