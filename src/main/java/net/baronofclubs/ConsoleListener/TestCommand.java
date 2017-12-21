package net.baronofclubs.ConsoleListener;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;

public class TestCommand implements ConsoleCommand {

    private final String TRIGGER = "TestCommand";
    private final String DESCRIPTION = "Just a test command. Returns nothing.";
    private final String USAGE = "TestCommand name=Domino Jones; task=Make a task list.;";
    private final Set<String> REQUIRED_ARGS = new HashSet<>();
    {
        REQUIRED_ARGS.add("name");
        REQUIRED_ARGS.add("task");
    }



    @Override
    public void run(HashMap<String, String> args) {
        System.out.println("COMMAND RAN SUCCESSFULLY:");
        StringJoiner argValueList = new StringJoiner("\n");
        for (String arg : REQUIRED_ARGS) {
            argValueList.add("ARG: " + arg + " VALUE: " + args.get(arg));
        }
        System.out.println(argValueList.toString());
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
