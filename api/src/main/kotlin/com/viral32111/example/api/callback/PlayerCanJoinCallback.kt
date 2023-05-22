package com.viral32111.example.api.callback

import com.mojang.authlib.GameProfile
import net.fabricmc.fabric.api.event.Event
import net.fabricmc.fabric.api.event.EventFactory
import net.minecraft.util.ActionResult
import java.net.SocketAddress

fun interface PlayerCanJoinCallback {
	companion object {
		val EVENT: Event<PlayerCanJoinCallback> = EventFactory.createArrayBacked( PlayerCanJoinCallback::class.java ) { listeners ->
			PlayerCanJoinCallback { socketAddress, gameProfile ->
				for ( listener in listeners ) {
					val result = listener.interact( socketAddress, gameProfile )
					if ( result != ActionResult.PASS ) return@PlayerCanJoinCallback result
				}

				return@PlayerCanJoinCallback ActionResult.PASS
			}
		}
	}

	fun interact( socketAddress: SocketAddress, gameProfile: GameProfile ): ActionResult
}
