package me.tmanti.flowaboat.command;

import me.tmanti.flowaboat.Flowaboat;

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
        this.aliases.put(command.getName(), command.getName());
    }

    public void addAlias(Command command, String alias){
        this.aliases.put(alias, command.getName());
    }

    public Command getCommand(String name){
        return this.commands.get(name);
    }

    public List<Command> getCommands(){
        return new ArrayList<>(this.commands.values());
    }

    public boolean dispatch(String label, String[] args){
        return true;
    }

}
