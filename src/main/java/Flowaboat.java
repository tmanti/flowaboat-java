import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.graalvm.compiler.replacements.Log;

import javax.security.auth.login.LoginException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Flowaboat {

    private JDA JDA;

    private static Flowaboat instance;

    public Flowaboat(){
        instance = this;
        try {
            this.initJDA();
        } catch (LoginException e){
            e.printStackTrace();
        }
    }

    public static Flowaboat getInstance(){
        return instance;
    }

    private void initJDA() throws LoginException {
        JDABuilder jdaBuilder = new JDABuilder("token");
        jdaBuilder = this.registerListeners(jdaBuilder);
        JDA = jdaBuilder.build();
    }

    private JDABuilder registerListeners(JDABuilder jdaBuilder){
        jdaBuilder.addEventListeners();
        return jdaBuilder;
    }
}
