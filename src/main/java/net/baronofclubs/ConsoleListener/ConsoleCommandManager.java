package net.baronofclubs.ConsoleListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringJoiner;

public class ConsoleCommandManager {

    private static HashMap<String, ConsoleCommand> commandMap = new HashMap<>();

    public static HashMap<String, ConsoleCommand> getCommandMap() {
        return commandMap;
    }

    private static void registerDefaultCommands() {
        registerCommand(new TestCommand());
        registerCommand(new HelpCommand());
    }

    public static void registerCommand(ConsoleCommand command) {
        commandMap.put(command.getTrigger(), command);
    }

    public static void checkLine(String line) {
        registerDefaultCommands();
        ConsoleCommand command;

        String trigger = getTrigger(line);

        if(isRegisteredCommand(trigger)) {
            command = getCommandFromTrigger(trigger);
            if (commandRequiresArgs(command)) {
                if (hasArgs(line)) {
                    HashMap<String, String> argMap = parseArgs(line);
                    if (hasRequiredArgs(argMap, command)) {
                        command.run(argMap);
                    } else {
                        StringJoiner errorMessage = new StringJoiner(", ");
                        for (String missingArg : getMissingArgs(argMap, command)) {
                            errorMessage.add(missingArg);
                        }
                        System.out.println(command.getUsage());
                        System.out.println("MISSING ARGS: " + errorMessage.toString() + ".");
                    }
                } else {
                    System.out.println(command.getUsage());
                }
            } else {
                command.run(null);
            }
        }
    }

    private static HashMap<String, String> parseArgs(String line) {
        HashMap<String, String> argMap = new HashMap<>();
        String argString = getArgString(line);
        String[] argArray = argString.split(";");
        for (String arg : argArray) {
            argMap.put(arg.substring(0, arg.indexOf('=')).trim(), arg.substring(arg.indexOf('=') + 1).trim());
        }

        return argMap;
    }

    private static String getTrigger(String line) {
        if (hasArgs(line)) {
            return line.substring(0, line.indexOf(' '));
        }
        return line.trim();
    }

    private static boolean isRegisteredCommand(String trigger) {
        return commandMap.keySet().contains(trigger);
    }

    private static ConsoleCommand getCommandFromTrigger(String trigger) {
        return commandMap.get(trigger);
    }

    private static boolean commandRequiresArgs(ConsoleCommand command) {
        return !command.getRequiredArgs().isEmpty();
    }

    private static boolean hasRequiredArgs(HashMap<String, String> argMap, ConsoleCommand command) {
        return argMap.keySet().containsAll(command.getRequiredArgs());
    }

    private static ArrayList<String> getMissingArgs(HashMap<String, String> argMap, ConsoleCommand command) {
        ArrayList<String> missingArgList = new ArrayList<>();
        for (String requiredArg : command.getRequiredArgs()) {
            if (!argMap.keySet().contains(requiredArg)) {
                missingArgList.add(requiredArg);
            }
        }
        return missingArgList;
    }

    private static boolean hasArgs(String line) {
        return line.contains(" ") && line.contains("=") && line.contains(";");
    }

    private static String getArgString(String line) {
        return line.substring(line.indexOf(' ')).trim();
    }

}
