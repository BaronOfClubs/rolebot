package net.baronofclubs.Rolebot.Commands;

import java.util.UUID;

public abstract class Command {

    private UUID commandId;

    public Command() {
        commandId = UUID.randomUUID();
    }

    public UUID getId() {
        return commandId;
    }
}
