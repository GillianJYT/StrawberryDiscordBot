import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;

// https://ci.dv8tion.net/job/JDA/javadoc/

public class Main{
    private static String token;

    public static void main(String[] args) throws LoginException {
        // init
        token = System.getenv("TOKEN");
        JDA jda = JDABuilder.createDefault(token).build();

        MessageListener.InitializeCommands();
        Moderator.InitializeCommands();

        // events
        jda.addEventListener(new MessageListener());
        jda.addEventListener(new Moderator());

        // status
        jda.getPresence().setActivity(Activity.playing("~commands"));
    }
}
