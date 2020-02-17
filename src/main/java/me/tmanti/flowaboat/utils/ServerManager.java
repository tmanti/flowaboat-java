package me.tmanti.flowaboat.utils;

import me.tmanti.flowaboat.Flowaboat;

import java.util.HashMap;

public class ServerManager {

    private HashMap<String, Server> serverMap;

    public ServerManager(){
        this.serverMap = new HashMap<>();
    }

    public ServerManager(HashMap<String, Server> serverList){
        this.serverMap = serverList;
    }

    public void addServer(String id, Server server){
        this.serverMap.put(id, server);
    }

    public Server getServerById(String id){
        return this.serverMap.get(id);
    }

}
