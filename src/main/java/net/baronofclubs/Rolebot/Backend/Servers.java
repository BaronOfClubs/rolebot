package net.baronofclubs.Rolebot.Backend;

import net.dv8tion.jda.core.entities.Guild;

import java.util.LinkedList;

public class Servers {

    private static LinkedList<Server> serverList = new LinkedList<>();

    public Servers() {
    }

    public static void addServer(Server server) {
        serverList.add(server);
    }

    public static Server getServer(Guild guild) {
        for (Server server : serverList) {
            if (server.getGuild().equals(guild)) {
                return server;
            }
        }
        return null;
    }

}
