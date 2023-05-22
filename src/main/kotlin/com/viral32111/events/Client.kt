package com.viral32111.events

import net.fabricmc.api.ClientModInitializer

@Suppress( "UNUSED" )
class Client: ClientModInitializer {

	// Display a message when the mod is initialized on the client.
	override fun onInitializeClient() {
		Main.LOGGER.info( "Events initialized in the client-side environment." )
	}

}
