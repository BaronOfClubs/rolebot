package net.baronofclubs.Rolebot.Backend;

import net.dv8tion.jda.core.entities.Emote;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.Role;

import java.io.Serializable;
import java.util.HashMap;
import java.util.UUID;

class RoleBoard implements Serializable {
    UUID roleBoardId;
    Guild guild;
    transient Message message;
    String messageId;
    HashMap<Role, Emote> reactionRoleMap;

    public RoleBoard(Guild guild) {
        roleBoardId = UUID.randomUUID();
        this.guild = guild;
        //this.message = message;
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

    public UUID getRoleBoardId() {
        return roleBoardId;
    }
}
