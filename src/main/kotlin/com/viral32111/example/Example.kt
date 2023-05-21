package com.viral32111.example

import net.fabricmc.api.DedicatedServerModInitializer
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Suppress( "UNUSED" )
object Example: DedicatedServerModInitializer {

	// A logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	val LOGGER: Logger = LoggerFactory.getLogger( "example" )

	// This displays a message when the mod is initialised server-side.
	override fun onInitializeServer() {
		LOGGER.info( "The Example mod has initialized in the server environment." )
	}

}
