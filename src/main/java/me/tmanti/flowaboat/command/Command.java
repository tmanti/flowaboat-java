package me.tmanti.flowaboat.command;

public abstract class Command {

    private final String name;
    private String description;
    private String usage;
    private int minArguments = 0;
    private int maxArguments = 0;

    public Command(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public String getDescription(){
        return this.description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getUsage(){
        return this.usage;
    }

    public void setUsage(String usage){
        this.usage = usage;
    }

    public int getMinArguments(){
        return this.minArguments;
    }

    public int getMaxArguments() {
        return maxArguments;
    }

    public void setArgumentRange(int min, int max) {
        this.minArguments = min;
        this.maxArguments = max;
    }

}
