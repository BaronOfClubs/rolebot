package net.baronofclubs.Rolebot.Command.Commands;

import net.baronofclubs.Rolebot.Backend.Server;
import net.baronofclubs.Rolebot.Command.Command;
import net.baronofclubs.Rolebot.Command.CommandUtils;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.Role;

public class listSelfRoles extends CommandUtils implements Command {

    private String prefix = "listSelfRoles";
    private String usage = "listSelfRoles";
    private int requiredArgs = 0;
    private char argDelineator = ';';

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
        return argDelineator;
    }

    @Override
    public int getArgNumber() {
        return requiredArgs;
    }

    @Override
    public Message runCommand(Server server, Message message) {
        MessageBuilder messageBuilder = new MessageBuilder();

        for (Role role : server.getSelfRoles()) {
            messageBuilder.append("`" + role.getName() + "` ");
        }

        return messageBuilder.build();
    }
}
