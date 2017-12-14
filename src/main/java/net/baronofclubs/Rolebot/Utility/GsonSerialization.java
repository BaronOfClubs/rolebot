package net.baronofclubs.Rolebot.Utility;

import com.google.gson.*;
import net.baronofclubs.Rolebot.RolebotMain;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.TextChannel;

import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.time.ZonedDateTime;
import java.util.UUID;

public class GsonSerialization {

    public static class UUIDSerialization implements JsonSerializer<UUID>, JsonDeserializer<UUID> {

        @Override
        public JsonElement serialize(UUID uuid, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(uuid.toString());
        }

        @Override
        public UUID deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return UUID.fromString(jsonElement.getAsString());
        }
    }

    public static class ZonedDateTimeSerialization implements JsonSerializer<ZonedDateTime>, JsonDeserializer<ZonedDateTime> {

        @Override
        public JsonElement serialize(ZonedDateTime zonedDateTime, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(zonedDateTime.toString());
        }

        @Override
        public ZonedDateTime deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return ZonedDateTime.parse(jsonElement.getAsString());
        }
    }

    public static class CharsetSerialization implements JsonSerializer<Charset>, JsonDeserializer<Charset> {

        @Override
        public JsonElement serialize(Charset charset, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(charset.name());
        }

        @Override
        public Charset deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return Charset.forName(jsonElement.getAsString());
        }
    }

    public static class GuildSerialization implements JsonSerializer<Guild>, JsonDeserializer<Guild> {

        @Override
        public JsonElement serialize(Guild guild, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(guild.getId());
        }

        @Override
        public Guild deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return RolebotMain.getJDA().getGuildById(jsonElement.getAsString());
        }
    }

    public static class RoleSerialization implements JsonSerializer<Role>, JsonDeserializer<Role> {

        @Override
        public JsonElement serialize(Role role, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(role.getId());
        }

        @Override
        public Role deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return RolebotMain.getJDA().getRoleById(jsonElement.getAsString());
        }
    }

    public static class TextChannelSerialization implements JsonSerializer<TextChannel>, JsonDeserializer<TextChannel> {

        @Override
        public JsonElement serialize(TextChannel textChannel, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(textChannel.getId());
        }

        @Override
        public TextChannel deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return RolebotMain.getJDA().getTextChannelById(jsonElement.getAsString());
        }
    }



}
