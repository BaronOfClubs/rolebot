package net.baronofclubs.Rolebot.Commands;

import net.baronofclubs.Rolebot.Backend.Server;
import net.dv8tion.jda.core.entities.Role;

public class addSelfRole extends Command{

    private String prefix = "addSelfRole";

    public void run(Server server, Role role) {
        server.addSelfRole(role);
    }

}
