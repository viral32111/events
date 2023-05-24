package com.viral32111.events.callback.server

import com.mojang.authlib.GameProfile
import net.fabricmc.fabric.api.event.Event
import net.fabricmc.fabric.api.event.EventFactory
import net.minecraft.util.ActionResult
import java.net.SocketAddress

/**
 * Callback for when a player attempts to join the server (before ban & whitelist checks).
 * @see com.viral32111.events.mixin.server.PlayerManagerMixin.checkCanJoin
 * @see com.viral32111.events.listener.server.playerCanJoinCallbackListener
 * @since 0.2.0
 */
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

	/**
	 * Runs when a player attempts to join the server (before ban & whitelist checks).
	 * @param socketAddress The IP address & port number of the connecting player. Should be cast to InetSocketAddress.
	 * @param gameProfile The profile of the connecting player. Includes username, UUID, etc.
	 * @return Pass to allow the player to join, fail to prevent them joining.
	 * @see PlayerCanJoinCallback
	 * @since 0.2.0
	 */
	fun interact( socketAddress: SocketAddress, gameProfile: GameProfile ): ActionResult
}
