package me.tmanti.flowaboat.utils;

public class Server {

    private String id;
    private String name;
    private String commandPrefix;

    public Server(String id, String name, String commandPrefix){
        this.id = id;
        this.name = name;
        this.commandPrefix = commandPrefix;
    }

    public String getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public String getCommandPrefix(){
        return this.commandPrefix;
    }
}
