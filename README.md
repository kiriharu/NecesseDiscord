# Necesse Discord

## About

Necesse Discord - simple server mod to link you server with your Discord server.

Current features:
* Links in game chat with Discord.
* Send death messages to Discord.
* Send join and leave message to Discord.
* Show online, days and server TPS in bot rich presence.

All of this features can be enabled or disabled in config!

## Configuration

Configuration stored in `cfg/mods/kiriharu.necessediscord.cfg`. It appears after first mod run.
In rare cases it may be not created (idk why), so you need to create it with your hands.

<details>
    <summary>Default config</summary>

```
{
  SETTINGS = {
    token = Enter token here,
    channelId = Enter channelId here,
    enableChatMessages = true,
    enableConnectMessages = true,
    enableDisconnectMessages = true,
    enableDeathMessages = true,
    enableDiscordActivity = true
  }
}
```
</details>

## Installing

Currently, installing is a little complicated, because I have troubles to build mod with external dependencies.

1. Download last mod release from [releases](https://github.com/kiriharu/NecesseDiscord/releases).
2. Move it to mods server folder
3. [Download JDA dependency](https://github.com/kiriharu/NecesseDiscord/blob/master/libs/JDA-5.0.1-withDependencies.jar).
4. Create folder called "vendor" and move downloaded dependency to it.
5. Add vendor dir and server jar to classpath and run server:
    ```shell
    java -cp "Server.jar:vendor/*" StartServer 
    ```
    After `StartServer` you can add server params, like `-motd` and others.
6. Change `token` and `channelId` in `cfg/mods/kiriharu.necessediscord.cfg`.
7. Restart your server.

### Warning

Java `-cp` option doesn't work with `-jar` option. You can see details in this [page](https://docs.oracle.com/javase/7/docs/technotes/tools/solaris/java.html#jar)
>When you use this option, the JAR file is the source of all user classes, and other user class path settings are ignored. 

