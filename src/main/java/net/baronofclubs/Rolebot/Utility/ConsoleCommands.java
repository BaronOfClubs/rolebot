package net.baronofclubs.Rolebot.Utility;

import net.baronofclubs.ConsoleListener.ConsoleCommand;
import net.baronofclubs.Rolebot.RolebotMain;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;

import java.util.*;

public class ConsoleCommands {

    public static class ChatByConsole implements ConsoleCommand {

        private final String TRIGGER = "/say";
        private final String DESCRIPTION = "Chat via console.";
        private final String USAGE = "/say guild=Guild Name; channel=channel-name; text=Hello World!;";
        private final Set<String> REQUIRED_ARGS = new HashSet<>();
        {
            REQUIRED_ARGS.add("guild");
            REQUIRED_ARGS.add("channel");
            REQUIRED_ARGS.add("text");
        }

        @Override
        public void run(HashMap<String, String> args) {
            try {
                List<Guild> guildsList = RolebotMain.getJDA().getGuildsByName(args.get("guild"), true);
                Guild guild = guildsList.get(0);
                List<TextChannel> channelsList = guild.getTextChannelsByName(args.get("channel"), true);
                TextChannel channel = channelsList.get(0);
                channel.sendMessage(args.get("text")).queue();
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Guild or channel not found. Please try again.");
            }
        }

        @Override
        public String getTrigger() {
            return TRIGGER;
        }

        @Override
        public String getDescription() {
            return DESCRIPTION;
        }

        @Override
        public String getUsage() {
            if (!getRequiredArgs().isEmpty()) {
                StringJoiner argList = new StringJoiner(", ");
                for (String requiredArg : getRequiredArgs()) {
                    argList.add(requiredArg);
                }
                return "USAGE: " + USAGE + "\n>>>REQUIRED ARGS: " + argList.toString() + ".";
            }
            return "USAGE: " + USAGE;
        }

        @Override
        public Set<String> getRequiredArgs() {
            return REQUIRED_ARGS;
        }

    }

}
