import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MessageListener extends ListenerAdapter {

    private static final List<String> Cmds = new ArrayList<>();
    private static final List<String> CmdDescriptions = new ArrayList<>();

    public static void InitializeCommands()
    {
        Cmds.add("chan");
        Cmds.add("mute");
        Cmds.add("unmute");

        // descriptions
        CmdDescriptions.add("bot replys with something special :)");
        CmdDescriptions.add("mutes everyone in the same call");
        CmdDescriptions.add("unmutes everyone in the same call");
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Message msg = event.getMessage();
        String content = msg.getContentRaw();
        Member member;
        MessageChannel channel = event.getChannel();

        // ~commands --> outputs all commands
        if (content.equals(Main.specialCmd + "commands"))
        {
            EmbedBuilder emb = new EmbedBuilder();

            emb.setTitle("Strawbery Bot Commands");
            emb.setColor(0xfc0366);

            for (int i = 0; i < Cmds.size(); i++) {
                emb.addField(Main.specialCmd + Cmds.get(i), CmdDescriptions.get(i), false);
            }

            for (int i = 0; i < Moderator.Cmds.size(); i++) {
                emb.addField(Main.specialCmd + Moderator.Cmds.get(i) + " @MEMBER", Moderator.CmdDescriptions.get(i), false);
            }

            for (int i =0; i < League.Cmds.size(); i++) {
                emb.addField(Main.specialCmd + League.Cmds.get(i) + " IGN", League.CmdDescriptions.get(i), false);
            }

            channel.sendMessage(emb.build()).queue();
            emb.clear();
        }
        // ~chan --> outputs "Chan is gae"
        else if(content.equals(Main.specialCmd + Cmds.get(0))) {
            channel.sendMessage("Chan is gae").queue();
        }
        // mute everyone in same call
        else if (content.equals(Main.specialCmd + Cmds.get(1))) {
            if (!msg.getMember().hasPermission(Permission.VOICE_MUTE_OTHERS)) { // check mute permission
                channel.sendMessage("You don't have permission to " + Cmds.get(1)).queue();
            }
            else {
                List<Member> members = msg.getMember().getVoiceState().getChannel().getMembers();
                ExecutorService service = Executors.newCachedThreadPool();

                members.forEach(m -> {
                    service.execute(() -> {
                        m.mute(true).queue();
                        System.out.println("muted");
                    });
                });


//                for (Member m : members) {
//                    m.mute(true).queue();
//                }
            }
        }
        // unmute everyone in same call
        else if (content.equals(Main.specialCmd + Cmds.get(2))) {
            if (!msg.getMember().hasPermission(Permission.VOICE_MUTE_OTHERS)) { // check mute permission
                channel.sendMessage("You don't have permission to " + Cmds.get(1)).queue();
            }
            else {
                List<Member> members = msg.getMember().getVoiceState().getChannel().getMembers();

                ExecutorService service = Executors.newCachedThreadPool();

                members.forEach(m -> {
                    service.execute(() -> {
                        m.mute(false).queue();
                        System.out.println("unmuted");
                    });
                });
            }
        }

    }
}
