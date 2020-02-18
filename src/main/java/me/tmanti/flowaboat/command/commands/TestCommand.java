package me.tmanti.flowaboat.command.commands;

import me.tmanti.flowaboat.command.Command;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class TestCommand extends Command {
    public TestCommand() {
        super("test");
        super.setDescription("This is a test command!");
        super.setUsage("test");
        super.setArgumentRange(0, 0);
    }

    @Override
    public boolean execute(MessageReceivedEvent event, String identifier, String[] args) {
        return false;
    }
}
