import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.List;

public class Moderator  extends ListenerAdapter {
    private static final String specialCmd = "~";
    public static final List<String> Cmds = new ArrayList<>();

    public int count = 0;

    public static void InitializeCommands() {
        Cmds.add("mute");
        Cmds.add("unmute");
    }

    public void onMessageReceived(MessageReceivedEvent event) {
        Message msg = event.getMessage();
        String content = msg.getContentRaw();
        MessageChannel channel = event.getChannel();
        List<Member> mentioned = msg.getMentionedMembers();

        Member member;

        if(content.startsWith(specialCmd + Cmds.get(count))) // mutes mentioned user
        {

            if (mentioned.isEmpty()) {
                channel.sendMessage("You did not specify the user \n example:" + specialCmd + Cmds.get(count) + " @MEMBER").queue();
            }
            else {
                member = msg.getMentionedMembers().get(0);
                if (msg.getMember().hasPermission(Permission.VOICE_MUTE_OTHERS))
                {
                    // mute
//                    event.getGuild().mute(member, true).queue();
                    member.mute(true).queue();
                }
                else
                {
                    // error
                    channel.sendMessage("You don't have permission to" + Cmds.get(count)).queue();
                }
            }

            count++;
        }
        else if(content.startsWith(specialCmd + Cmds.get(count))) // unmutes mentioned user
        {
            if (!mentioned.isEmpty()) {
                member = msg.getMentionedMembers().get(0);
                if (msg.getMember().hasPermission(Permission.VOICE_MUTE_OTHERS))
                {
                    // unmute
                    member.mute(false).queue();
                }
                else
                {
                    // error
                    channel.sendMessage("You don't have permission to" + Cmds.get(count)).queue();
                }
            }
            else {
                channel.sendMessage("You did not specify the user \n example:" + specialCmd + Cmds.get(count) + " @MEMBER").queue();
            }
        }
    }
}
