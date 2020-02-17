package me.tmanti.flowaboat.listeners;

import me.tmanti.flowaboat.Flowaboat;
import me.tmanti.flowaboat.utils.Server;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageHandler extends ListenerAdapter {

    private Flowaboat flowaboat;

    public MessageHandler(Flowaboat flowaboat){
        this.flowaboat = flowaboat;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event){
        Server server = flowaboat.getServerManager().getServerById(event.getGuild().getId());
        Message msg = event.getMessage();
        if(msg.getContentRaw().startsWith(server.getCommandPrefix())){
            String decodeString = msg.getContentRaw().substring(server.getCommandPrefix().length());
            System.out.println(decodeString);
        }
    }

}