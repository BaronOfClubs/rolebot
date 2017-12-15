package net.baronofclubs.Rolebot.Command.Commands;

import net.baronofclubs.Rolebot.Backend.Server;
import net.baronofclubs.Rolebot.Command.Command;
import net.baronofclubs.Rolebot.Command.CommandUtils;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Message;

public class ping extends CommandUtils implements Command {

    public ping() {

    }

    @Override
    public String getPrefix() {
        String prefix = "ping";
        return prefix;
    }

    @Override
    public Message runCommand(Server server, Message message) {
        MessageBuilder msgBuilder = new MessageBuilder();
        msgBuilder.append("pong! ");

        return msgBuilder.build();
    }
}
