package net.baronofclubs.Rolebot.Backend;

import net.baronofclubs.Rolebot.Commands.Command;
import net.baronofclubs.Rolebot.Utility.ResourceManager;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.managers.GuildController;
import net.dv8tion.jda.core.requests.restaction.ChannelAction;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.UUID;

public class Server {

    public Guild getGuild() {
        return guild;
    }

    private UUID serverId;
    private Guild guild;
    private GuildController guildController;
    private TextChannel consoleChannel;
    private LinkedList<Role> selfRoles;
    private LinkedList<RoleBoard> roleBoards;
    private LinkedList<Command> commands;

    public Server(Guild guild) {
        serverId = UUID.randomUUID();
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

    public LinkedList<Role> getSelfRoles() {
        return selfRoles;
    }

    public LinkedList<RoleBoard> getRoleBoards() {
        return roleBoards;
    }

    public void addRoleBoard() {
        RoleBoard rb = new RoleBoard(guild);
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
        ChannelAction consoleChannel = guildController.createTextChannel("Rolebot-console");//.queue();
        //consoleChannel = guild.getTextChannelsByName("Rolebot-console", false).get(0);

    }

    private void save() {
        SaveData saveData = new SaveData();
        ResourceManager.save(saveData, saveData.filename);

    }

    private void reload() {
        SaveData saveData = (SaveData) ResourceManager.load("server-" + serverId + ".ser");

    }

    private class SaveData implements Serializable {
        String filename;
        String guildId;
        String consoleChannelId;
        LinkedList<String> selfRolesIds;
        LinkedList<UUID> roleBoardsIds;
        LinkedList<UUID> commandsIds;

        public SaveData() {
            filename = "server-" + serverId + ".ser";
            this.guildId = guild.getId();
            consoleChannelId = consoleChannel.getId();
            for (Role role : selfRoles) {
                selfRolesIds.add(role.getId());
            }
            for (RoleBoard roleBoard : roleBoards) {
                roleBoardsIds.add(roleBoard.getRoleBoardId());
            }
            for (Command command : commands) {
                commandsIds.add(command.getId());
            }
        }

    }

}
