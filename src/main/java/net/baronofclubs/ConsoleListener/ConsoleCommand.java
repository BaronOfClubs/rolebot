package net.baronofclubs.ConsoleListener;

import java.util.HashMap;
import java.util.Set;

public interface ConsoleCommand {

    void run(HashMap<String, String> args);
    String getTrigger();
    String getDescription();
    String getUsage();
    Set<String> getRequiredArgs();

}
