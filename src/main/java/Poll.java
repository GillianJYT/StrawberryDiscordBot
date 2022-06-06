import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Poll  extends ListenerAdapter {
    private static final String specialCmd = "~";
    public static final List<String> Cmds = new ArrayList<>();
    public static final List<String> CmdDescriptions = new ArrayList<>();

    public static void InitializeCommands()
    {
        Cmds.add("poll");

        // descriptions
        CmdDescriptions.add("creates a poll with provided comma-separated options (format: ~poll QUESTION, OPTION1..OPTION9)");
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Message msg = event.getMessage();
        String content = msg.getContentRaw();
        MessageChannel channel = event.getChannel();

        // ~poll QUESTION OPTIONS
        if(content.startsWith(specialCmd + Cmds.get(0)))
        {
            String[] number_emotes = new String[] {":one:", ":two:", ":three:", ":four:",
                    ":five:", ":six:", ":seven:", ":eight:", ":nine:"};
            String[] components = content.substring(6).split(",");

            EmbedBuilder emb = new EmbedBuilder();

            emb.setTitle(components[0]);
            emb.setColor(Color.cyan);

            for (int i = 1; i  < components.length; i++) {
                emb.addField("", number_emotes[i - 1] + components[i], false);
            }

            channel.sendMessage(emb.build()).queue(message -> {
                for (int i = 1; i  < components.length; i++) {
                    String emoji = "U+003" + i + "U+FE0FU+20E3";
                    message.addReaction(emoji).queue();
                }
            });
        }
    }
}
