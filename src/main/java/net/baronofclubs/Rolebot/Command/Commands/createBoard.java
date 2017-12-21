package net.baronofclubs.Rolebot.Command.Commands;

import net.baronofclubs.Rolebot.Backend.DecisionBoard;
import net.baronofclubs.Rolebot.Backend.Server;
import net.baronofclubs.Rolebot.Command.Command;
import net.baronofclubs.Rolebot.Command.CommandUtils;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Message;

public class createBoard extends CommandUtils implements Command {

    private String prefix = "createBoard";
    private String usage = "Usage: `addSelfRole Role Name`";
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
        String[] args = message.getContent().split(" ");

        messageBuilder.append("Board created. Preview: ");
        if (args.length < 3) {
            messageBuilder.append("Not enough args provided.");
            messageBuilder.append("\n");
            messageBuilder.append(usage);
        } else {
            DecisionBoard board = new DecisionBoard(server);
            board.setTitle(args[1]);
            board.setDescription(args[2]);
            board.addOption("Test3", server.getGuild().getEmotes().get(1));
            messageBuilder.setEmbed(board.getEmbed());
        }
        return messageBuilder.build();
    }
}
