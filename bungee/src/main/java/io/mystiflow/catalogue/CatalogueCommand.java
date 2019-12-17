package io.mystiflow.catalogue;

import com.google.common.base.Joiner;
import io.mystiflow.catalogue.api.Action;
import io.mystiflow.catalogue.api.Actionable;
import io.mystiflow.catalogue.api.Delay;
import io.mystiflow.catalogue.api.Message;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

//TODO: Modularise this class
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
                    plugin.getDefaultLoader().save();
                    sender.sendMessage(ChatColor.YELLOW + "Saved catalogue to config");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                return;
            }

            if (args[0].equalsIgnoreCase("reload")) {
                try {
                    plugin.getDefaultLoader().load();
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
        sender.sendMessage(ChatColor.BLUE + "Executing action '" + action.getAction() + ":" + action.getAction()
                + " for " + action.getIterations() + " iterations");
    }

    private static final Function<String, Actionable> ACTIONABLE_CONVERTER = new Function<String, Actionable>() {
        @Override
        public Actionable apply(String input) {
            Actionable actionable = CataloguePlugin.getPlugin().getCatalogue().getAction(input).orElse(null);
            if (actionable == null) actionable = CataloguePlugin.getPlugin().getCatalogue().getMessage(input).orElse(null);
            return actionable;
        }
    };
    private void execute(CommandSender sender, Message message) {
        List<Actionable> actions = message.getActions().stream().map(ACTIONABLE_CONVERTER).collect(Collectors.toList());

        for (Actionable actionable : actions) {
            if (actionable instanceof Message) {
                Message message1 = (Message) actionable;
                execute(sender, message1);
            } else if (actionable instanceof Action) {
                Action action = (Action) actionable;
                Runnable runnable = () -> {
                    for (int i = 0; i < action.getIterations(); i++) {
                        plugin.getProxy().getPluginManager().dispatchCommand(sender, action.getAction());
                    }

                    inform(sender, action);
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
                if (previousDelayId != null && previousDelayId != -1) {
                    plugin.getProxy().getScheduler().cancel(previousDelayId);
                }
            }
        }
    }
}
