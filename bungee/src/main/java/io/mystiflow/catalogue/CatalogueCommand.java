package io.mystiflow.catalogue;

import com.google.common.base.Joiner;
import io.mystiflow.catalogue.CataloguePlugin;
import io.mystiflow.catalogue.api.Action;
import io.mystiflow.catalogue.api.Delay;
import io.mystiflow.catalogue.api.Message;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;

import java.io.IOException;
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

                execute(sender, optionalMessage.get());
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

            if (args[0].equalsIgnoreCase("save")) {
                try {
                    plugin.saveCatalogue();
                    sender.sendMessage(ChatColor.YELLOW + "Saved catalogue to config");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                return;
            }

            if (args[0].equalsIgnoreCase("reload")) {
                try {
                    plugin.reloadCatalogue();
                    sender.sendMessage(ChatColor.YELLOW + "Reloaded catalogue to config");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                return;
            }
        }

        sender.sendMessage(ChatColor.YELLOW + "/" + getName() + " <execute|list|reload|save>");
    }

    private void inform(CommandSender sender, Action action) {
        sender.sendMessage(ChatColor.BLUE + "Executing '" + action.getType().name() + ":" + action.getAction()
                + " for " + action.getIterations() + " iterations");
    }

    private void execute(CommandSender sender, Message message) {
        List<Action> actions = message.getActions();
        for (Action action : actions) {
            Runnable runnable = () -> {
                if (action.getType() == Action.Type.COMMAND) {
                    for (int i = 0; i < action.getIterations(); i++) {
                        plugin.getProxy().getPluginManager().dispatchCommand(sender, action.getAction());
                    }
                    inform(sender, action);
                } else if (action.getType() == Action.Type.MESSAGE) {
                    plugin.getCatalogue().getMessage(action.getAction()).ifPresent(message1 ->
                            {
                                inform(sender, action);
                                for (int i = 0; i < action.getIterations(); i++) {
                                    execute(sender, message1);
                                }
                            }
                    );
                }
            };

            int taskID;
            Delay delay = action.getDelay();
            if (delay == null) {
                runnable.run();
                taskID = -1;
            } else {
                taskID = plugin.getProxy().getScheduler().schedule(
                        plugin, runnable, delay.getDelay() * 50L, delay.getRepeatDelay() * 50L, TimeUnit.MILLISECONDS
                ).getId();
            }

            Integer previousDelayId = action.getActiveDelays().put("$" + sender.getName(), taskID);
            plugin.getProxy().getScheduler().cancel(previousDelayId);
        }
    }
}
