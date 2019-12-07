package io.mystiflow.cmdcatalogue;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class CataloguePlugin {

    private static final List<CommandInstruction> catalogue = new ArrayList<>();

    public static void main(String[] args) {
        Gson gson = new Gson()
                .newBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(CommandInstruction.class, new CommandScriptAdapter())
                .create();

        CommandInstruction instruction = CommandInstruction.builder()
                .name("1223334444")
                .command(new Command("say 1", 1))
                .command(new Command("say 22", 2))
                .command(new Command("say 333", 3))
                .command(new Command("say 4444", 4))
                .build();

        CommandInstruction instruction2 = CommandInstruction.builder()
                .name("RebukeTheGloom")
                .command(new Command("weather clear", 1))
                .command(new Command("time set day", 1))
                .build();

        catalogue.add(instruction);
        catalogue.add(instruction2);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Instruction");
        String instructionName = scanner.next();
        Optional<CommandInstruction> optionalCommandInstruction = getCommandInstruction(instructionName);
        if (optionalCommandInstruction.isPresent()) {
            CommandInstruction commandInstruction = optionalCommandInstruction.get();
            for (Command command : commandInstruction.getCommands()) {
                System.out.println("Executing " + command.getCommand() + " for count of " + command.getCount());
            }
        }

        String json = gson.toJson(catalogue, Collection.class);
        System.out.println(json);
    }

    public static Optional<CommandInstruction> getCommandInstruction(String name) {
        return catalogue.stream().filter(commandInstruction -> commandInstruction.getName().equalsIgnoreCase(name)).findFirst();
    }
}
