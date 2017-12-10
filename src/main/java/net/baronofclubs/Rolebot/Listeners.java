package net.baronofclubs.Rolebot;

import net.baronofclubs.Rolebot.Backend.Server;
import net.baronofclubs.Rolebot.Backend.Servers;
import net.baronofclubs.debug.Debug;
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
        // for each entry in CommandManagers command list, check to see if message startswith prefix string
    }

    public void onGenericMessageReaction(GenericMessageReactionEvent event) {
        debug("GenericMessageReaction: " + event.getReaction().getCount());
    }

    private static void debug(String debugText) {
        String actor = "Listeners";
        Debug.print(debugText, actor, Debug.Level.NONE);
    }

}
