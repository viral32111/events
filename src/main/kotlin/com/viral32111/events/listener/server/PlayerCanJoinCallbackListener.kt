package com.viral32111.events.listener.server

import com.mojang.authlib.GameProfile
import com.viral32111.events.Main
import net.minecraft.util.ActionResult
import java.net.InetSocketAddress
import java.net.SocketAddress

/**
 * Runs when a player attempts to join the server (before ban & whitelist checks).
 * @param socketAddress The IP address & port number of the connecting player. Should be cast to InetSocketAddress.
 * @param gameProfile The profile of the connecting player. Includes username, UUID, etc.
 * @return Pass to allow the player to join, fail to prevent them joining.
 * @see com.viral32111.events.callback.server.PlayerCanJoinCallback
 * @since 0.2.0
 */
fun playerCanJoinCallbackListener( socketAddress: SocketAddress, gameProfile: GameProfile ): ActionResult {

	// Cast the socket address to expose address and port properties.
	val address = socketAddress as InetSocketAddress

	// The IP address & port number of the connecting player.
	val sourceIP = address.address.hostAddress
	val sourcePort = address.port

	// The player's username and unique identifier.
	val playerName = gameProfile.name
	val playerUUID = gameProfile.id.toString()

	// Display a console message with details of this event.
	// Example: Player 'viral32111' (a51dccb5-7ffa-426b-833b-1a9ce3a31446) is attempting to join from 127.0.0.1:56346.
	Main.LOGGER.info( "Player '${ playerName }' (${ playerUUID }) is attempting to join from '${ sourceIP }:${ sourcePort }'." )

	// Pass to allow other listeners to execute.
	// ActionResult.FAIL will prevent the player from joining (and other listeners from executing).
	return ActionResult.PASS

}
