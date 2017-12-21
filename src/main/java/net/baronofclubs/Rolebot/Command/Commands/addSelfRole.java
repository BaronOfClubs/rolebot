package net.baronofclubs.Rolebot.Command.Commands;

import net.baronofclubs.Rolebot.Backend.Server;
import net.baronofclubs.Rolebot.Command.Command;
import net.baronofclubs.Rolebot.Command.CommandUtils;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.Role;

import java.util.List;

public class addSelfRole extends CommandUtils implements Command {

    private String prefix = "addSelfRole";
    private String usage = "Usage: `addSelfRole Role Name`";
    private int requiredArgs = 1;
    private char delineator;

    public addSelfRole() {

    }

    @Override
    public Message runCommand(Server server, Message message) {
        MessageBuilder messageBuilder = new MessageBuilder();
        String args = message.getContent().split(" ", 2)[1];

        if (args.isEmpty()) {
            messageBuilder.append("No Roles provided.");
            messageBuilder.append("\n");
            messageBuilder.append(usage);
        } else {
            List<Role> roleList = server.getGuild().getRolesByName(args, true);
            if (roleList.isEmpty()) {
                messageBuilder.append("No Roles found.");
            } else if (roleList.size() > 1) {
                messageBuilder.append("Found the following roles:");
                messageBuilder.append("\n");
                for (Role role : roleList) {
                    messageBuilder.append(role.getAsMention() + "\n");
                }
                messageBuilder.append("Please delete one of the above roles.");
            } else if (server.getSelfRoles().contains(roleList.get(0))) {
                messageBuilder.append("`" + roleList.get(0).getName() + "` is already a SelfRole");
            } else if (roleList.size() == 1) {
                server.addSelfRole(roleList.get(0));
                messageBuilder.append("Added `" + roleList.get(0).getName() + "` to SelfRoles.");
                server.save();
            }
        }
        return messageBuilder.build();
    }

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
}
