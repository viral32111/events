package com.viral32111.events

import com.viral32111.events.callback.server.*
import com.viral32111.events.listener.server.*
import net.fabricmc.api.DedicatedServerModInitializer

@Suppress( "UNUSED" )
class Server: DedicatedServerModInitializer {

	// Runs when the mod has initialized on the server...
	override fun onInitializeServer() {
		Main.LOGGER.info( "Events initialized in the server-side environment." )

		// Register example listeners for server callbacks
		PlayerJoinCallback.EVENT.register( ::playerJoinCallbackListener )
		PlayerCanJoinCallback.EVENT.register( ::playerCanJoinCallbackListener )
		PlayerLeaveCallback.EVENT.register( ::playerLeaveCallbackListener )
		PlayerDeathCallback.EVENT.register( ::playerDeathCallbackListener )
		PlayerChatMessageCallback.EVENT.register( ::playerChatMessageCallbackListener )
	}

}
