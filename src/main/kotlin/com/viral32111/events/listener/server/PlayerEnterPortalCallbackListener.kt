package com.viral32111.events.listener.server

import com.viral32111.events.Main
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.ActionResult
import kotlin.math.roundToInt

/**
 * Runs before a player travels through a portal.
 * @param player The player that is travelling through a portal.
 * @param destinationWorld The world the destination dimension is in.
 * @return Pass to allow the player to change dimension, fail to prevent the player from changing dimension.
 * @see com.viral32111.events.callback.server.PlayerEnterPortalCallback
 * @since 0.3.4
 */
fun playerEnterPortalCallbackListener( player: ServerPlayerEntity, destinationWorld: ServerWorld ): ActionResult {

	// The player's username & unique identifier.
	val playerName = player.name.string
	val playerUUID = player.uuidAsString

	// The player's position at the time of travelling.
	val playerPosX = player.pos.getX().roundToInt()
	val playerPosY = player.pos.getY().roundToInt()
	val playerPosZ = player.pos.getZ().roundToInt()

	// The player's current dimension & dimension they are travelling to.
	val currentDimension = player.serverWorld.registryKey
	val currentDimensionName = "${ currentDimension.value.namespace }:${ currentDimension.value.path }"
	val destinationDimension = destinationWorld.registryKey
	val destinationDimensionName = "${ destinationDimension.value.namespace }:${ destinationDimension.value.path }"

	// Display a console message with details of this event.
	// Example: Player 'viral32111' (a51dccb5-7ffa-426b-833b-1a9ce3a31446) is travelling from dimension 'minecraft:overworld' to 'minecraft:nether' at 62, 63, -135.
	Main.LOGGER.info( "Player '${ playerName }' (${ playerUUID }) is travelling from dimension '${ currentDimensionName }' to dimension '${ destinationDimensionName }' at [${ playerPosX }, ${ playerPosY }, ${ playerPosZ }]." )

	// Pass to allow other listeners to execute.
	// ActionResult.FAIL will prevent the player from joining (and other listeners from executing).
	return ActionResult.PASS

}
