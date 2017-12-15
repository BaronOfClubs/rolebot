package net.baronofclubs.Rolebot.Command;

import net.baronofclubs.Rolebot.Backend.Server;
import net.dv8tion.jda.core.entities.Message;

public interface Command {

    String getPrefix();
    Message runCommand(Server server, Message message);

}
