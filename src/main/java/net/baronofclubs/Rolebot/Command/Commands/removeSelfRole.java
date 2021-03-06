package net.baronofclubs.Rolebot.Command.Commands;

import net.baronofclubs.Rolebot.Backend.Server;
import net.baronofclubs.Rolebot.Command.Command;
import net.baronofclubs.Rolebot.Command.CommandUtils;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.Role;

public class removeSelfRole extends CommandUtils implements Command {

    private String prefix = "removeSelfRole";
    private String usage = "removeSelfRole Role Name";
    private int requiredArgs = 1;
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
        String args = message.getContent().split(" ", 2)[1];

        if (args.isEmpty()) {
            messageBuilder.append("No Roles provided.");
            messageBuilder.append("\n");
            messageBuilder.append(usage);
        } else if (server.getSelfRoles().isEmpty()) {
            messageBuilder.append("No Roles found.");
        } else {
            boolean roleFound = false;
            for (Role role : server.getSelfRoles()) {
                if (role.getName().equalsIgnoreCase(args)) {
                    roleFound = true;
                    server.removeSelfRole(role);
                    messageBuilder.append("Removed `" + role.getName() + "` from SelfRoles");
                    server.save();
                }
            }
            if (!roleFound) {
                messageBuilder.append("No Roles found.");
            }

        }

        return messageBuilder.build();
    }
}
