package com.viral32111.example.listener

import com.viral32111.example.Example
import net.minecraft.network.ClientConnection
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.ActionResult
import java.net.InetSocketAddress

// Runs when a player has joined the server.
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
	val playerPosX = player.pos.getX()
	val playerPosY = player.pos.getY()
	val playerPosZ = player.pos.getZ()

	// Display a console message with details of this event.
	// Example: Player 'viral32111' (a51dccb5-7ffa-426b-833b-1a9ce3a31446) joined from 127.0.0.1:56346 (at 62, 63, -135).
	Example.LOGGER.info( "Player '%s' (%s) joined from %s:%d (at %.0f, %.0f, %.0f).".format( playerName, playerUUID, sourceIP, sourcePort, playerPosX, playerPosY, playerPosZ ) )

	// Pass to allow other listeners to execute.
	return ActionResult.PASS

}
