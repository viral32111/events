package com.viral32111.events.listener.server

import com.viral32111.events.Main
import net.minecraft.network.ClientConnection
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.ActionResult
import java.net.InetSocketAddress
import kotlin.math.roundToInt

/**
 * Runs when a player has joined the server.
 * @param connection The IP address & port number of the player that joined the server.
 * @param player The player that joined the server.
 * @return Pass to allow other callback listeners to be executed, fail to prevent their execution.
 * @see com.viral32111.events.callback.server.PlayerCanJoinCallback
 * @since 0.2.0
 */
fun playerJoinCallbackListener( connection: ClientConnection, player: ServerPlayerEntity ): ActionResult {

	// Cast the connection address to expose address & port properties.
	val address = connection.address as InetSocketAddress

	// The IP address & port number of the connecting player.
	val sourceIP = address.address.hostAddress
	val sourcePort = address.port

	// The player's username and unique identifier.
	val playerName = player.name.string
	val playerUUID = player.uuidAsString

	// The player's position (where they logged in).
	val playerPosX = player.pos.getX().roundToInt()
	val playerPosY = player.pos.getY().roundToInt()
	val playerPosZ = player.pos.getZ().roundToInt()

	// Display a console message with details of this event.
	// Example: Player 'viral32111' (a51dccb5-7ffa-426b-833b-1a9ce3a31446) joined from 127.0.0.1:56346 (at 62, 63, -135).
	Main.LOGGER.info( "Player '$playerName' ($playerUUID) joined from '$sourceIP:$sourcePort' at [$playerPosX, $playerPosY, $playerPosZ]." )

	// Pass to allow other listeners to execute.
	return ActionResult.PASS

}
