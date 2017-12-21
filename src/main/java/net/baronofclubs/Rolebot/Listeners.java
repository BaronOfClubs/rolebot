package net.baronofclubs.Rolebot;

import net.baronofclubs.Debug.Debug;
import net.baronofclubs.Rolebot.Backend.Server;
import net.baronofclubs.Rolebot.Backend.Servers;
import net.baronofclubs.Rolebot.Command.CommandManager;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.guild.GuildJoinEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.react.GenericMessageReactionEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class Listeners extends ListenerAdapter {

    public void onGuildJoin(GuildJoinEvent event) {
        debug("Guild Joined: " + event.getGuild().getName());
        Server server = new Server(event.getGuild());
        Servers.addServer(server);
        server.createConsole();
    }

    public void onMessageReceived(MessageReceivedEvent event) {
        debug("Message received: " + event.getAuthor().getName() + ": " + event.getMessage().getContent());
        Server server = Servers.getServer(event.getGuild());
        Message message = event.getMessage();
        String messageContent = event.getMessage().getContent();

        for(String prefix : CommandManager.getCommands().keySet()) {
            if (messageContent.startsWith(prefix)) {
                Message msg = CommandManager.getCommands().get(prefix).runCommand(server, message);
                event.getTextChannel().sendMessage(msg).queue();
            }
        }

        /*Consumer<Message> callback = (response) -> System.out.printf("Sent Message %s\n", response);
        event.getTextChannel().sendMessage(message).queue(callback);*/
        // TODO: Stop if author is bot
    }

    public void onGenericMessageReaction(GenericMessageReactionEvent event) {
        debug("GenericMessageReaction: " + event.getReaction().getCount());
    }

    private static void debug(String debugText) {
        String actor = "Listeners";
        Debug.print(debugText, actor, Debug.Level.NONE);
    }

}
