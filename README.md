# Example

I find it annoying that Minecraft Fabric mixins are not documented enough, or at all in some cases, and I am fed up of searching through mapped Minecraft sources to find functions to hook into every single time, so this project serves as a place where I can document and demonstrate how to use all of the mixins I have come across while developing my Fabric mods (which are primarily server-side ones).

The mixins used in this repository are accurate as of release [`1.19.2`](https://fabricmc.net/develop/). Feel free to submit [pull requests](https://github.com/viral32111/ExampleMod/pulls) to keep this project up-to-date if you have suggestions for new mixins to add, or encounter an existing one that is broken.

See the [Gradle properties](/gradle.properties#L9-L15) and [Fabric mod metadata](/src/main/resources/fabric.mod.json#L34-L39) files for what versions this mod was last built against.

## Contents

* `PlayerManager`
  * [`checkCanJoin`](/src/main/java/com/viral32111/example/mixin/PlayerManagerMixin.java#L22-L43) - A player attempts to join the server (before checks for whitelist, bans, etc.).
  * [`onPlayerConnect`](/src/main/java/com/viral32111/example/mixin/PlayerManagerMixin.java#L46-L72) - A player has joined the server.
  * [`remove`](/src/main/java/com/viral32111/example/mixin/PlayerManagerMixin.java#L75-L94) - A player has left the server.
* `ServerPlayerEntity`
  * [`onDeath`](/src/main/java/com/viral32111/example/mixin/ServerPlayerEntityMixin.java#L16-L61) - A player has died.
* `ServerPlayNetworkHandler`
  * [`onChatMessage`](/src/main/java/com/viral32111/example/mixin/ServerPlayNetworkHandlerMixin.java#L25-L41) - A player has sent a chat message (signed or unsigned).
  * [`onRequestChatPreview`](/src/main/java/com/viral32111/example/mixin/ServerPlayNetworkHandlerMixin.java#L45-L58) - A player has typed a character in their chatbox.
* `PlayerAdvancementTracker`
  * TODO: [`grantCriterion`]() - A player gained an advancement.

## License

This project is licensed under [The Unlicense](https://unlicense.org/), feel free to use it however you wish.
