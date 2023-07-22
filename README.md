# Events

[![CI](https://github.com/viral32111/events/actions/workflows/ci.yml/badge.svg)](https://github.com/viral32111/events/actions/workflows/ci.yml)
[![CodeQL](https://github.com/viral32111/events/actions/workflows/codeql.yml/badge.svg)](https://github.com/viral32111/events/actions/workflows/codeql.yml)
![GitHub tag (with filter)](https://img.shields.io/github/v/tag/viral32111/events?label=Latest)
![GitHub repository size](https://img.shields.io/github/repo-size/viral32111/events?label=Size)
![GitHub release downloads](https://img.shields.io/github/downloads/viral32111/events/total?label=Downloads)
![GitHub commit activity](https://img.shields.io/github/commit-activity/m/viral32111/events?label=Commits)

This is a [Minecraft Fabric](https://fabricmc.net/) mod that adds callbacks using the [Fabric event system](https://fabricmc.net/wiki/tutorial:events) for the mixins I frequently use.

This mod does not do much by itself, other than log example messages for each callback. It is designed as a dependency for my other mods.

The mixins in this mod are accurate as of release **1.20.1**. Feel free to submit [pull requests](https://github.com/viral32111/example-mod/pulls) to keep them up-to-date if they break. See the [Gradle properties file](/gradle.properties) for the versions this mod was last built against.

## 📜 Background

I found it frustrating constantly searching through mapped Minecraft source code for the right function to hook into every time I need to write a mixin. They are not documented enough in my opinion, so this project serves as a place for me to house all the mixins I come across while developing my mods.

## 📅 History

This project was originally a demonstration of how to use common mixins in Java around June 2022. See [tag 0.1.0](https://github.com/viral32111/example-mod/tree/0.1.0).

It was later rewritten using [Kotlin for Fabric](https://github.com/FabricMC/fabric-language-kotlin) around May 2023. See [0.2.0](https://github.com/viral32111/example-mod/tree/0.2.0).

Finally, it was converted into an API that other mods can depend on. See [main](https://github.com/viral32111/example-mod/tree/main).

## 🖥️ Callbacks

Documentation for each callback is written within the source code as comments.

* Server
  * [`PlayerCanJoinCallback`](/src/main/kotlin/com/viral32111/events/callback/server/PlayerCanJoinCallback.kt) - When a player attempts to join the server.
  * [`PlayerJoinCallback`](/src/main/kotlin/com/viral32111/events/callback/server/PlayerJoinCallback.kt) - When a player has joined the server.
  * [`PlayerLeaveCallback`](/src/main/kotlin/com/viral32111/events/callback/server/PlayerLeaveCallback.kt) - When a player has left the server.
  * [`PlayerDeathCallback`](/src/main/kotlin/com/viral32111/events/callback/server/PlayerDeathCallback.kt) - When a player has died.
  * [`PlayerChatMessageCallback`](/src/main/kotlin/com/viral32111/events/callback/server/PlayerChatMessageCallback.kt) - When a player has sent a chat message.
  * [`PlayerEnterPortalCallback`](/src/main/kotlin/com/viral32111/events/callback/server/PlayerEnterPortalCallback.kt) - When a player is about to change dimension.
  * [`PlayerGainExperienceCallback`](/src/main/kotlin/com/viral32111/events/callback/server/PlayerGainExperienceCallback.kt) - When a player has gained experience.
  * [`PlayerCompleteAdvancementCallback`](/src/main/kotlin/com/viral32111/events/callback/server/PlayerGainExperienceCallback.kt) - When a player has completed an advancement (including challenges & goals).

## 📥 Usage

<a href="https://modrinth.com/mod/fabric-api/"><img src="https://github.com/viral32111/discord-relay/assets/19510403/2e0d32ee-b4aa-4d93-9388-4a45639c4a96" height="48" alt="Requires Fabric API"></a>
<a href="https://modrinth.com/mod/fabric-language-kotlin"><img src="https://github.com/viral32111/discord-relay/assets/19510403/ab7b8cbb-ff80-4359-8fc9-13a2cf62c4bf" height="48" alt="Requires Fabric Language Kotlin"></a>
<br>

Firstly, add this repository's Gradle package registry to your Gradle configuration.

Gradle Groovy:
```groovy
repositories {
  maven {
    url = uri( "https://maven.pkg.github.com/viral32111/events" )
    credentials {
      username = project.findProperty( "gpr.user" ) ?: System.getenv( "USERNAME" )
      password = project.findProperty( "gpr.key" ) ?: System.getenv( "TOKEN" )
    }
  }
}
```

Kotlin DSL:

```kotlin
repositories {
  maven {
    url = uri( "https://maven.pkg.github.com/viral32111/events" )
    credentials {
      username = project.findProperty( "gpr.user" ) as String? ?: System.getenv( "USERNAME" )
      password = project.findProperty( "gpr.key" ) as String? ?: System.getenv( "TOKEN" )
    }
  }
}
```

Next, add this mod as a dependency in your Gradle configuration.

Gradle Groovy:

```groovy
dependencies {
  implementation "com.viral32111.events:0.3.5"
}
```

Kotlin DSL:
```kotlin
dependencies {
  implementation( "com.viral32111.events", "events", "0.3.5" )
}
```

Finally, import this mod's package in your source code.

Java:
```java
import com.viral32111.events.callback.*;
import com.viral32111.events.callback.server.*;
import com.viral32111.events.callback.client.*;
```

Kotlin:
```kotlin
import com.viral32111.events.callback.*
import com.viral32111.events.callback.server.*
import com.viral32111.events.callback.client.*
```

## ⚖️ License

Copyright (C) 2022-2023 [viral32111](https://viral32111.com).

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program. If not, see https://www.gnu.org/licenses.
