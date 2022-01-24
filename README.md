# Strawberry - Discord Bot

General Discord bot programmed in Java.

Command Prefix: ~ (tilda)

## General Commands

| command | action | requirements |
|-|-|-|
| ~commands | shows all commands | |
| ~mute | mutes everyone in the same call | <ul><li>user must be in a voice channel</li> <li>user must have mute permissions</li></ul> |
| ~unmute | unmutes everyone in the same call | <ul><li>user must be in a voice channel</li> <li>user must have mute permissions</li><ul> |
| ~mute @MEMBER | mutes mentioned person | <ul><li>mentioned person must be in a voice channel</li> <li>user must have mute permissions</li><ul> |
| ~unmute @MEMBER | unmutes mentioned person | <ul><li>mentioned person must be in a voice channel</li> <li>user must have mute permissions</li><ul> |

## League of Legends Commands

| command | action | requirements |
|-|-|-|
| ~live SUMMONER [broken] | displays stats of live game | <ul><li>SUMMONER must exist in NA region</li><li>SUMMONER must be in a live game</li></ul> |
| ~mastery SUMMONER | displays top 3 mastery champions | <ul><li>SUMMONER must exist in NA region</li></ul> |

## In development

* mute and unmute commands are not fully optimized
* adding League of Legends commands

## Notes

* The League of Legends commands require a Riot API Key.
