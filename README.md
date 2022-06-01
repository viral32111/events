# Example Mod

I find it annoying that Minecraft mixins are not documented enough, or at all in some cases, and I am fed up of searching through mapped Minecraft sources to find functions to hook into every single time, so this project serves as a place where I can document and demonstrate how to use all of the mixins I have come across while developing my Fabric mods (which are primarily server-side ones).

The mixins used in this repository are accurate as of release [`1.19-pre4`](https://maven.fabricmc.net/docs/yarn-1.19-pre4+build.2/). Feel free to submit [pull requests](https://github.com/viral32111/ExampleMod/pulls) to keep this project up-to-date if you discover a broken mixin.

See the [Gradle properties](/gradle.properties#L9-L15) and [Fabric mod metadata](/src/main/resources/fabric.mod.json#L34-L39) files for what versions this mod was last built against.

## Contents

* `PlayerManager`
  * [`checkCanJoin`](/src/main/java/net/fabricmc/example/mixin/PlayerManagerMixin.java#L22-L33) - A player attempts to join the server (before checks for whitelist, bans, etc.)
  * [`onPlayerConnect`](/src/main/java/net/fabricmc/example/mixin/PlayerManagerMixin.java#L36-L51) - A player successfully joins the server
  * [`remove`](/src/main/java/net/fabricmc/example/mixin/PlayerManagerMixin.java#L54-L64) - A player leaves the server
* `ServerPlayerEntity`
  * [`onDeath`](/src/main/java/net/fabricmc/example/mixin/ServerPlayerEntityMixin.java#L15) - A player dies

## License

This project is licensed under [The Unlicense](https://unlicense.org/), feel free to use it however you wish.
