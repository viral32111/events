package com.viral32111.example

import com.viral32111.example.callback.PlayerCanJoinCallback
import com.viral32111.example.callback.PlayerJoinCallback
import com.viral32111.example.callback.PlayerLeaveCallback
import com.viral32111.example.listener.playerCanJoinCallbackListener
import com.viral32111.example.listener.playerJoinCallbackListener
import com.viral32111.example.listener.playerLeaveCallbackListener
import net.fabricmc.api.DedicatedServerModInitializer
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Suppress( "UNUSED" )
class Example: DedicatedServerModInitializer {

	// A logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	companion object {
		val LOGGER: Logger = LoggerFactory.getLogger( "example" )
	}

	// This displays a message when the mod is initialised server-side.
	override fun onInitializeServer() {
		LOGGER.info( "The Example mod has initialized in the server environment." )

		// Register callback listeners
		PlayerJoinCallback.EVENT.register( ::playerJoinCallbackListener )
		PlayerCanJoinCallback.EVENT.register( ::playerCanJoinCallbackListener )
		PlayerLeaveCallback.EVENT.register( ::playerLeaveCallbackListener )

	}

}
