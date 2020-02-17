package me.tmanti.flowaboat;

import me.tmanti.flowaboat.listeners.MessageHandler;
import me.tmanti.flowaboat.utils.BotSettings;
import me.tmanti.flowaboat.utils.ServerManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;

public class Flowaboat {

    private JDA JDA;

    private static Flowaboat instance;
    private BotSettings settings;

    private MessageHandler messageHandler;
    private ServerManager serverManager;

    public Flowaboat(){
        instance = this;
        this.serverManager =  new ServerManager();
        //TODO: init/loading with json
        this.settings = new BotSettings("token");
        try {
            this.initJDA(this.settings);
        } catch (LoginException e){
            e.printStackTrace();
        }
    }

    public static Flowaboat getInstance(){
        return instance;
    }

    private void initJDA(BotSettings settings) throws LoginException {
        JDABuilder jdaBuilder = new JDABuilder(settings.getToken());
        registerListeners(jdaBuilder);
        JDA = jdaBuilder.build();
    }

    private void registerListeners(JDABuilder jdaBuilder){

        this.messageHandler = new MessageHandler(this);

        jdaBuilder.addEventListeners(this.messageHandler);
    }

    public MessageHandler getMessageHandler(){
        return this.messageHandler;
    }

    public ServerManager getServerManager(){
        return this.serverManager;
    }

    public BotSettings getSettings(){
        return this.settings;
    }
}
