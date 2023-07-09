package com.viral32111.events.listener.server

import com.viral32111.events.Main
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.ActionResult

/**
 * Runs when a player has sent a chat message.
 * @param player The player who sent the chat message.
 * @param packet Data about the chat message. Includes content, signature, etc.
 * @return Pass to allow other callback listeners to be executed, fail to prevent their execution.
 * @see com.viral32111.events.callback.server.PlayerCanJoinCallback
 * @since 0.2.0
 */
fun playerChatMessageCallbackListener( player: ServerPlayerEntity, packet: ChatMessageC2SPacket ): ActionResult {

	// The player's username and unique identifier.
	val playerName: String = player.name.string
	val playerUUID: String = player.uuidAsString

	// The content of the chat message.
	val messageContent = packet.chatMessage()

	// Is the message signed?
	val isSigned = packet.signature()?.data?.isNotEmpty()

	// Display a console message with details of this event.
	Main.LOGGER.info( "Player '$playerName' ($playerUUID) sent ${ if ( isSigned == true ) "signed" else "unsigned" } chat message '$messageContent'." )

	// Pass to allow other listeners to execute.
	return ActionResult.PASS

}
