package me.tmanti.flowaboat.command;

import me.tmanti.flowaboat.Flowaboat;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.requests.restaction.MessageAction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommandHandler {

    private final Flowaboat flowaboat;

    private HashMap<String, Command> commands;
    private HashMap<String, String> aliases;

    public CommandHandler(Flowaboat flowaboat){
        this.flowaboat = flowaboat;
        this.commands = new HashMap<>();
        this.aliases = new HashMap<>();
    }

    public void addCommand(Command command){
        this.commands.put(command.getName(), command);
        this.addAlias(command);
    }

    private void addAlias(Command command){
        this.addAlias(command, command.getName());
    }

    public void addAlias(Command command, String alias){
        this.aliases.put(alias, command.getName());
    }

    public Command getCommand(String name){
        return this.commands.get(aliases.get(name));
    }

    public List<Command> getCommands(){
        return new ArrayList<>(this.commands.values());
    }

    public void dispatch(MessageReceivedEvent event, String label, String[] args){
        Command command = this.getCommand(label);
        if(command != null){
            if(command.getMinArguments() <= args.length && args.length <= command.getMaxArguments()) {
                command.execute(event, label, args);
            } else {
                this.getHelp(event, command);
            }
        } else{
            getHelp(event);
        }
    }

    public MessageAction getHelp(MessageReceivedEvent event){
        return event.getChannel().sendMessage("AHHHHH");
    }

    public MessageAction getHelp(MessageReceivedEvent event, Command command){
        return event.getChannel().sendMessage(command.getUsage());
    }

}
