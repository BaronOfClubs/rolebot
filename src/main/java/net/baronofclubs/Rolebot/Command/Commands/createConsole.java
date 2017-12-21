package net.baronofclubs.Rolebot.Command.Commands;

import net.baronofclubs.Rolebot.Backend.Server;
import net.baronofclubs.Rolebot.Command.Command;
import net.baronofclubs.Rolebot.Command.CommandUtils;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;

import java.util.List;

public class createConsole extends CommandUtils implements Command {

    private String prefix = "createConsole";
    private String consoleChannelName = "rolebot-console";
    private int requiredArgs = 0;
    private char delineator = ';';

    @Override
    public String getPrefix() {
        return prefix;
    }

    @Override
    public String getUsage() {
        return null;
    }

    @Override
    public char getArgDelineator() {
        return 0;
    }

    @Override
    public int getArgNumber() {
        return 0;
    }

    @Override
    public Message runCommand(Server server, Message message) {

        List<TextChannel> consoleChannels = server.getGuild().getTextChannelsByName(consoleChannelName, false);
        MessageBuilder messageBuilder = new MessageBuilder();

        if (consoleChannels.isEmpty()) {
            server.createConsole();
            messageBuilder.append("Created console channel.");
        } else {
            messageBuilder.append("Console already exists:" + "\n");
            for (TextChannel channel : consoleChannels) {
                messageBuilder.append(channel.getAsMention());
                messageBuilder.append("\n");
            }
        }

        return messageBuilder.build();
    }
}
