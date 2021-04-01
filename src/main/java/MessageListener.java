import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.internal.utils.PermissionUtil;

import java.util.ArrayList;
import java.util.List;

public class MessageListener extends ListenerAdapter {
    private static final String specialCmd = "~";

    private static final List<String> Cmds = new ArrayList<>();

    public static void InitializeCommands()
    {
        Cmds.add("chan");
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Message msg = event.getMessage();
        String content = msg.getContentRaw();
        Member member;
        MessageChannel channel = event.getChannel();

        if (content.equals(specialCmd + "commands")) // outputs all commands
        {
            StringBuilder list = new StringBuilder("Commands:\n");

            for (String cmd : Cmds) {
                list.append("\t" + specialCmd).append(cmd).append("\n");
            }

            for (String cmd : Moderator.Cmds) {
                list.append("\t" + specialCmd).append(cmd).append("@MEMBER\n");
            }

            channel.sendMessage(list.toString()).queue();
        }
        else if(content.equals(specialCmd + Cmds.get(0))) // says "Chan is gae"
        {
            channel.sendMessage("Chan is gae").queue();
        }

    }
}
