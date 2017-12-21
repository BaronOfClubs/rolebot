package net.baronofclubs.ConsoleListener;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;

public class HelpCommand implements ConsoleCommand {

    private final String TRIGGER = "help";
    private final String DESCRIPTION = "This command";
    private final String USAGE = "help";
    private final Set<String> REQUIRED_ARGS = new HashSet<>();



    @Override
    public void run(HashMap<String, String> args) {
        System.out.println("Currently registered commands:");
        System.out.println("==============================");
        StringJoiner commands = new StringJoiner("\n");
        for (String consoleCommand : ConsoleCommandManager.getCommandMap().keySet()) {
            ConsoleCommand command = ConsoleCommandManager.getCommandMap().get(consoleCommand);
            commands.add(command.getTrigger());
            commands.add(">>>" + command.getDescription());
            commands.add(">>>" + command.getUsage());
        }
        System.out.println(commands.toString());
        System.out.println("===============================");
    }

    @Override
    public String getTrigger() {
        return TRIGGER;
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

    @Override
    public String getUsage() {
        if (!getRequiredArgs().isEmpty()) {
            StringJoiner argList = new StringJoiner(", ");
            for (String requiredArg : getRequiredArgs()) {
                argList.add(requiredArg);
            }
            return "USAGE: " + USAGE + "\n>>>REQUIRED ARGS: " + argList.toString() + ".";
        }
        return "USAGE: " + USAGE;
    }

    @Override
    public Set<String> getRequiredArgs() {
        return REQUIRED_ARGS;
    }

}
