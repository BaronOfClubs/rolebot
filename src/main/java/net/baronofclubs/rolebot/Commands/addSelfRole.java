package net.baronofclubs.rolebot.Commands;

import net.baronofclubs.rolebot.Backend.Server;
import net.dv8tion.jda.core.entities.Role;

public class addSelfRole extends Command{

    private String prefix = "addSelfRole";

    public void run(Server server, Role role) {
        server.addSelfRole(role);
    }

}
