package net.baronofclubs.Rolebot.Backend;

import net.baronofclubs.Rolebot.RolebotMain;
import net.baronofclubs.Rolebot.Utility.ResourceManager;
import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.managers.GuildController;

import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.UUID;
import java.util.function.Consumer;

public class Server extends ResourceManager.SaveFile {

    public Guild getGuild() {
        return guild;
    }

    private UUID serverId;
    private Guild guild;
    private transient GuildController guildController;

    private TextChannel consoleChannel;
    private LinkedList<Role> selfRoles;
    private LinkedList<RoleBoard> roleBoards;
    private LinkedList<String> commands;

    public Server(Guild guild) {
        serverId = UUID.randomUUID();
        this.guild = guild;
        this.guildController = new GuildController(guild);
        // this.consoleChannel = createConsoleChannel();
        this.selfRoles = new LinkedList<>();
        this.roleBoards = new LinkedList<>();
        this.commands = new LinkedList<>();
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

    public Channel getConsoleChannel() {
        return consoleChannel;
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
        guildController = new GuildController(guild);
        if (guild.getTextChannelsByName("rolebot-console", false).isEmpty()) {
            Consumer<Channel> onSuccess = (channel) -> consoleChannel = (TextChannel) channel;
            guildController.createTextChannel("rolebot-console").queue(onSuccess);
        }
        //consoleChannel = guild.getTextChannelsByName("Rolebot-console", false).get(0);

    }

    public void save() {
        setFilename("server-" + getGuild().getId());
        setDirectory(Paths.get(RolebotMain.DEFAULT_PATH));
        setExtension(ResourceManager.FileType.JSON);

        ResourceManager.FileSaver.saveFile(this);
    }
}
