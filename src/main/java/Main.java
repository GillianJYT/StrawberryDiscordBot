import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;

import com.merakianalytics.orianna.Orianna;
import com.merakianalytics.orianna.types.common.Platform;

import java.io.File;

// https://ci.dv8tion.net/job/JDA/javadoc/
// run 'mvn heroku:deploy' in cmd line
// run 'mvn install', 'heroku local', ctrl+C stops local

public class Main {
    private static String token;
    public static JDA jda;
    public static final String specialCmd = "~";

    public static void main(String[] args) throws LoginException {
        // init
        token = System.getenv("TOKEN");
        jda = JDABuilder.createDefault(token).build();

//        Orianna.loadConfiguration(new File("/src/main/resources/config.json"));
        Orianna.Configuration config = new Orianna.Configuration();
        Orianna.loadConfiguration(config);
        Orianna.setRiotAPIKey(System.getenv("RIOT_API_KEY"));
        Orianna.setDefaultPlatform(Platform.NORTH_AMERICA);

        MessageListener.InitializeCommands();
        Moderator.InitializeCommands();
        League.InitializeCommands();

        // events
        jda.addEventListener(new MessageListener());
        jda.addEventListener(new Moderator());
        jda.addEventListener(new League());
        jda.addEventListener(new Valorant());

        // status
        jda.getPresence().setActivity(Activity.playing("~commands"));
    }
}
