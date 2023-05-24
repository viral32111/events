# Events

This is a [Minecraft Fabric](https://fabricmc.net/) mod that adds callbacks using the [Fabric event system](https://fabricmc.net/wiki/tutorial:events) for the mixins I frequently use.

This mod does not do much by itself, other than log example messages for each callback. It is designed as a dependency for my other mods.

The mixins in this mod are accurate as of release **1.19.4**. Feel free to submit [pull requests](https://github.com/viral32111/example-mod/pulls) to keep them up-to-date if they break. See the [Gradle properties file](/gradle.properties) for the versions this mod was last built against.

## Background

I found it frustrating constantly searching through mapped Minecraft source code for the right function to hook into every time I need to write a mixin. They are not documented enough in my opinion, so this project serves as a place for me to house all the mixins I come across while developing my mods.

This mod was rewritten using Kotlin in May 2023. See [0.1.0](https://github.com/viral32111/example-mod/tree/0.1.0) for the Java version.

## License

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
