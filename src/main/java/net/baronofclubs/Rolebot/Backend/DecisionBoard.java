package net.baronofclubs.Rolebot.Backend;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Emote;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.TextChannel;

import java.util.HashMap;

public class DecisionBoard {

    private Server server;
    private TextChannel channel;
    private Message message;

    private String title;
    private String description;
    private HashMap<String, Emote> options;

    public DecisionBoard(Server server) {
        this.server = server;
        options = new HashMap<>();
    }

    public MessageEmbed getEmbed() {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle(title);
        builder.setDescription(description);
        StringBuilder fieldBuilder = new StringBuilder();
        for (String optionDescription : options.keySet()) {
            fieldBuilder.append(options.get(optionDescription).getAsMention());
            fieldBuilder.append(" : ");
            fieldBuilder.append (optionDescription);
            fieldBuilder.append("\n");
        }
        String fieldContent = fieldBuilder.toString();
        builder.addField("Options", fieldContent, false);
        return builder.build();
    }

    public void addOption(String description, Emote emote) {
        options.put(description, emote);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
