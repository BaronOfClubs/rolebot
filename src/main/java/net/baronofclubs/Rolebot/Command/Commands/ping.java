package net.baronofclubs.Rolebot.Command.Commands;

import net.baronofclubs.Rolebot.Backend.Server;
import net.baronofclubs.Rolebot.Command.Command;
import net.baronofclubs.Rolebot.Command.CommandUtils;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Message;

public class ping extends CommandUtils implements Command {

    private String prefix = "ping";
    private String usage = "ping";
    private char delineator;
    private int requiredArgs = 0;

    @Override
    public String getPrefix() {
        return prefix;
    }

    @Override
    public String getUsage() {
        return usage;
    }

    @Override
    public char getArgDelineator() {
        return delineator;
    }

    @Override
    public int getArgNumber() {
        return requiredArgs;
    }

    @Override
    public Message runCommand(Server server, Message message) {
        MessageBuilder msgBuilder = new MessageBuilder();
        msgBuilder.append("pong!");

        return msgBuilder.build();
    }
}
