package net.baronofclubs.Rolebot.Command;

import java.util.HashMap;
import java.util.Map;

public class CommandManager {

    private static Map<String, Command> commands = new HashMap<>();

    public static void addCommand(Command command) {
        commands.put(command.getPrefix(), command);
    }

    public static Map<String, Command> getCommands() {
        return commands;
    }

}
