import com.merakianalytics.orianna.types.common.Season;
import com.merakianalytics.orianna.types.common.Tier;
import com.merakianalytics.orianna.types.core.spectator.CurrentMatch;
import com.merakianalytics.orianna.types.core.spectator.Player;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.merakianalytics.orianna.types.core.championmastery.ChampionMasteries;
import com.merakianalytics.orianna.types.core.summoner.Summoner;
import com.merakianalytics.orianna.types.core.championmastery.ChampionMastery;
import com.merakianalytics.orianna.types.common.Region;


public class League extends ListenerAdapter {
    public static final List<String> Cmds = new ArrayList<>();
    public static final List<String> CmdDescriptions = new ArrayList<>();

    public static void InitializeCommands() {
        Cmds.add("live");
        Cmds.add("mastery");

        // descriptions
        CmdDescriptions.add("displays live League stats [broken]");
        CmdDescriptions.add("displays highest 3 champion masteries");
    }

    public void onMessageReceived(MessageReceivedEvent event) {
        Message msg = event.getMessage();
        String content = msg.getContentRaw();
        MessageChannel channel = event.getChannel();
        String ign;
        Tier rank;

        if(content.startsWith(Main.specialCmd + Cmds.get(0)))
        {
            if (content.length() < Cmds.get(0).length() + 2) { // check that there is an ign
                channel.sendMessage("You did not specify the ign \n example: " + Main.specialCmd + Cmds.get(0) + " IGN").queue();
            }
            else {
                ign = content.substring(Cmds.get(0).length() + 2);
//                try {
                    Summoner s = Summoner.named(ign).withRegion(Region.NORTH_AMERICA).get();

                    if (s.isInGame()) {
                        CurrentMatch currentGame = s.getCurrentMatch();
                        if (currentGame.exists()) {
                            StringBuilder build = new StringBuilder();

                            // title
                            build.append(ign).append("'s Current Game:\n\n");

                            // teammates
                            build.append("Blue Team:\n");
                            for (final Player player : currentGame.getBlueTeam().getParticipants()) {
                                build.append(player.getSummoner().getName()).append("\n");

//                                try {
//                                    rank = player.getSummoner().getHighestTier(Season.getLatest());
//                                    build.append(rank);
//                                }
//                                catch (Exception e) {
//                                    System.out.println(e.getMessage());
//                                    build.append("--no current rank--");
//                                }
//
//                                build.append("\n");
                            }

                            // enemies
                            build.append("\nRed Team:\n");
                            for (final Player player : currentGame.getRedTeam().getParticipants()) {
                                build.append(player.getSummoner().getName()).append("\n");

//                                try {
//                                    rank = player.getSummoner().getHighestTier(Season.getLatest());
//                                    build.append(rank);
//                                }
//                                catch (Exception e) {
//                                    build.append("--no current rank--");
//                                }
//
//                                build.append("\n");
                            }

                            channel.sendMessage(build).queue();
                        }
                        else {
                            channel.sendMessage("Summoner is not currently in a game.").queue();
                        }
                    }
                    else {
                        channel.sendMessage("Summoner does not exist.").queue();
                    }
            }
        }
        else if(content.startsWith(Main.specialCmd + Cmds.get(1)))
        {
            if (content.length() < Cmds.get(1).length() + 2) { // check that there is an ign
                channel.sendMessage("You did not specify the ign \n example: " + Main.specialCmd + Cmds.get(0) + " IGN").queue();
            }
            else {
                ign = content.substring(Cmds.get(1).length() + 2).trim();

                try {
                    Summoner s = Summoner.named(ign).withRegion(Region.NORTH_AMERICA).get();

                    ChampionMasteries masteries = s.getChampionMasteries();
                    List<String> names = masteries.stream().map((ChampionMastery mastery) -> mastery.getChampion().getName()).collect(Collectors.toList());

                    StringBuilder build = new StringBuilder();
                    for (int i = 0; i < 3; i++) {
                        build.append(names.get(i)).append("\n");
                    }

                    channel.sendMessage(build).queue();
                }
                catch (Exception e) {
                    channel.sendMessage("Summoner does not exist.");
                }
            }
        }
    }
}
