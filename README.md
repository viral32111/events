# Example Mod

I find it frustrating that [Minecraft mixins](https://github.com/SpongePowered/Mixin/wiki/Mixins-on-Minecraft-Forge) are not documented enough, and I am fed up with constantly searching through mapped Minecraft sources for the right function. Thus, this project serves as a place for me to document and demonstrate how to use the mixins I come across while developing my Fabric mods.

The mixins in this repository are accurate as of release `1.19.4`. Feel free to submit [pull requests](https://github.com/viral32111/example-mod/pulls) to keep this project up-to-date if mixins break or to document new ones.

See the [Gradle properties](/gradle.properties) and [Fabric mod metadata](/src/main/resources/fabric.mod.json) files for the versions this mod was last built against.

## Mixins

* `PlayerManager`
  * [`checkCanJoin`](/src/main/java/com/viral32111/example/mixin/PlayerManagerMixin.java#L22-L43) - A player attempts to join the server (before checks for whitelist, bans, etc.).
  * [`onPlayerConnect`](/src/main/java/com/viral32111/example/mixin/PlayerManagerMixin.java#L46-L72) - A player has joined the server.
  * [`remove`](/src/main/java/com/viral32111/example/mixin/PlayerManagerMixin.java#L75-L94) - A player has left the server.
* `ServerPlayerEntity`
  * [`onDeath`](/src/main/java/com/viral32111/example/mixin/ServerPlayerEntityMixin.java#L16-L61) - A player has died.
* `ServerPlayNetworkHandler`
  * [`onChatMessage`](/src/main/java/com/viral32111/example/mixin/ServerPlayNetworkHandlerMixin.java#L22-L38) - A player has sent a chat message (signed or unsigned).
* `PlayerAdvancementTracker`
  * TODO: [`grantCriterion`]() - A player gained an advancement.

## Building

Set the `MINECRAFT_FABRIC_SERVER_PATH` Gradle variable to the absolute path of your local Minecraft Fabric server, then run the `copyJarToServer` Gradle task to build the project and automatically copy the JAR file into your server's mods directory.

## License

This project is licensed under [The Unlicense](https://unlicense.org/), feel free to use it however you wish.
