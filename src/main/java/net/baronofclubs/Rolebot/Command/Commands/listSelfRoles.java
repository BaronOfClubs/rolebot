package net.baronofclubs.Rolebot.Command.Commands;

import net.baronofclubs.Rolebot.Backend.Server;
import net.baronofclubs.Rolebot.Command.Command;
import net.baronofclubs.Rolebot.Command.CommandUtils;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.Role;

public class listSelfRoles extends CommandUtils implements Command {

    String prefix = "listSelfRoles";

    @Override
    public String getPrefix() {
        return prefix;
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
