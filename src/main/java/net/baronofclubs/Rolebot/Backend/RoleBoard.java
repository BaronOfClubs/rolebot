package net.baronofclubs.Rolebot.Backend;

import net.baronofclubs.Rolebot.Utility.ResourceManager;
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
    Message message;
    HashMap<Role, Emote> reactionRoleMap;

    public RoleBoard(Guild guild) {
        roleBoardId = UUID.randomUUID();
        this.guild = guild;
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

    public UUID getRoleBoardId() {
        return roleBoardId;
    }

    public void save() {
        SaveData saveData = new SaveData();
        ResourceManager.save(saveData, saveData.filename);
    }

    private class SaveData implements Serializable {
        String filename;
        UUID id;
        String guildId;
        String messageId;
        HashMap<String, String> reactionIdRoleIdMap;

        public SaveData() {
            filename = "roleboard-" + id.toString() + ".ser";
            id = roleBoardId;
            guildId = guild.getId();
            messageId = message.getId();
            for (Role role : reactionRoleMap.keySet()) {
                reactionIdRoleIdMap.put(role.getId(), reactionRoleMap.get(role).getId());
            }
        }
    }
}
