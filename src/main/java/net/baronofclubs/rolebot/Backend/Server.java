package net.baronofclubs.rolebot.Backend;

import net.baronofclubs.rolebot.Commands.Command;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.managers.GuildController;
import net.dv8tion.jda.core.requests.restaction.ChannelAction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.UUID;

public class Server {

    public Guild getGuild() {
        return guild;
    }

    private Guild guild;
    private GuildController guildController;
    private TextChannel consoleChannel;
    private LinkedList<Role> selfRoles;
    private LinkedList<RoleBoard> roleBoards;
    private LinkedList<Command> commands;

    public Server(Guild guild) {
        this.guild = guild;
        this.guildController = new GuildController(guild);
    }

    public boolean addSelfRole(Role role) {
        if (!selfRoles.contains(role)) {
            selfRoles.add(role);
            return true;
        }
        return false;
    }

    public boolean isSelfRole(Role role) {
        return selfRoles.contains(role);
    }

    public boolean removeSelfRole(Role role) {
        if (selfRoles.contains(role)) {
            selfRoles.remove(role);
            return true;
        }
        return false;
    }

    public LinkedList<RoleBoard> getRoleBoards() {
        return roleBoards;
    }

    public void addRoleBoard() {
        RoleBoard rb = new RoleBoard();
    }

    public void removeRoleBoard(UUID roleboardId) {
        for (RoleBoard roleBoard :
                roleBoards) {
            if (roleBoard.roleBoardId.equals(roleboardId)) {
                roleBoards.remove(roleBoard);
            }
        }
    }

    public void createConsole() {
        guildController.createTextChannel("rolebot-console").queue();
        //consoleChannel = guild.getTextChannelsByName("rolebot-console", false).get(0);
    }

    private class RoleBoard {
        UUID roleBoardId;
        Message message;
        HashMap<Role, Emote> reactionRoleMap;

        public RoleBoard() {
            roleBoardId = UUID.randomUUID();
            this.message = message;
        }

        public boolean addRoleEmotePair(Role role, Emote emote) {
            if (!reactionRoleMap.containsKey(role) && !reactionRoleMap.containsValue(emote)) {
                reactionRoleMap.put(role, emote);
                return true;
            }
            return false;
        }

        public boolean removeRole(Role role) {
            if (reactionRoleMap.containsKey(role)) {
                reactionRoleMap.remove(role);
                return true;
            }
            return false;
        }

    }

}
