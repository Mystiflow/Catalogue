package io.mystiflow.cmdcatalogue.command;

import com.google.common.base.Joiner;
import io.mystiflow.cmdcatalogue.CataloguePlugin;
import io.mystiflow.cmdcatalogue.api.Command;
import io.mystiflow.cmdcatalogue.api.CommandGroup;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;

import java.util.List;
import java.util.Optional;
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
                    sender.sendMessage(ChatColor.YELLOW + "/" + getName() + " execute {instruction}");
                    return;
                }

                Optional<CommandGroup> optionalInstruction = plugin.getCatalogue().getInstructions()
                        .stream()
                        .filter(commandInstruction -> commandInstruction.getName().equalsIgnoreCase(args[1]))
                        .findFirst();

                if (!optionalInstruction.isPresent()) {
                    sender.sendMessage(ChatColor.RED + "Command instruction '" + args[1] + "' not found");
                    return;
                }

                CommandGroup instruction = optionalInstruction.get();

                System.out.println(instruction.getCommands() + "A");
                List<Command> commands = instruction.getCommands();
                for (Command command : commands) {
                    sender.sendMessage("Dispatching " + command.getCommand() + " " + command.getIterations() + " times");
                    for (int i = 0; i < command.getIterations(); i++) {
                        plugin.getProxy().getPluginManager().dispatchCommand(sender, command.getCommand());
                    }
                }
                return;
            }

            if (args[0].equalsIgnoreCase("list")) {
                sender.sendMessage(ChatColor.YELLOW + "Command Instructions: " + (Joiner.on(", ").join(
                        plugin.getCatalogue().getInstructions()
                                .stream()
                                .map(CommandGroup::getName)
                                .collect(Collectors.toList()))));
                return;
            }
        }

        sender.sendMessage(ChatColor.YELLOW + "/" + getName() + " <execute|list>");
    }
}
