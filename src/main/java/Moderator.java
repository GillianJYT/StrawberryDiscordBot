import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.List;

public class Moderator  extends ListenerAdapter {
    public static final List<String> Cmds = new ArrayList<>();
    public static final List<String> CmdDescriptions = new ArrayList<>();

    public static void InitializeCommands() {
        Cmds.add("mute");
        Cmds.add("unmute");

        // descriptions
        CmdDescriptions.add("mutes mentioned person");
        CmdDescriptions.add("unmutes mentioned person");
    }

    public void onMessageReceived(MessageReceivedEvent event) {
        Message msg = event.getMessage();
        String content = msg.getContentRaw();
        MessageChannel channel = event.getChannel();
        List<Member> mentionedMembers = msg.getMentionedMembers();
        List<Role> mentionedRoles = msg.getMentionedRoles();

        Member member;

        // ~mute @MEMBER
        if(content.startsWith(Main.specialCmd + Cmds.get(0) + " @"))
        {
            if (mentionedMembers.isEmpty()) { // check that there is a mentioned user
                channel.sendMessage("You did not specify the user or role \n example: " + Main.specialCmd + Cmds.get(0) + " @MEMBER").queue();
            }
            else if (!mentionedMembers.get(0).getVoiceState().inVoiceChannel()) { // check if user is connected to voice
                channel.sendMessage("@" + mentionedMembers.get(0).getNickname() + " is not connected to voice.").queue();
            }
            else if (!msg.getMember().hasPermission(Permission.VOICE_MUTE_OTHERS)) { // check mute permission
                channel.sendMessage("You don't have permission to " + Cmds.get(0)).queue();
            }
            else {
                member = msg.getMentionedMembers().get(0);
                // mute
                member.mute(true).queue();
            }
        }
        // ~unmute @MEMBER
        else if(content.startsWith(Main.specialCmd + Cmds.get(1) + " @")) // unmutes mentioned user
        {
            if (mentionedMembers.isEmpty()) { // check that there is a mentionedMembers user
                channel.sendMessage("You did not specify the user \n example: " + Main.specialCmd + Cmds.get(1) + " @MEMBER").queue();
            }
            else if (!mentionedMembers.get(0).getVoiceState().inVoiceChannel()) { // check if user is connected to voice
                channel.sendMessage("@" + mentionedMembers.get(0).getNickname() + " is not connected to voice.").queue();
            }
            else if (!msg.getMember().hasPermission(Permission.VOICE_MUTE_OTHERS)) { // check mute permission
                channel.sendMessage("You don't have permission to " + Cmds.get(1)).queue();
            }
            else {
                member = msg.getMentionedMembers().get(0);
                // unmute
                member.mute(false).queue();
            }
        }
    }
}
