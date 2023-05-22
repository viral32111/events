package com.viral32111.events

import net.fabricmc.api.ModInitializer
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Suppress( "UNUSED" )
class Main: ModInitializer {

	// A logger writes text to the console & log file.
	// It is best practice to use your mod id as the logger's name, so it is clear which mod wrote messages.
	companion object {
		val LOGGER: Logger = LoggerFactory.getLogger( "events" )
	}

	/// Display a message when the mod is initialized.
	override fun onInitialize() {
		LOGGER.info( "Events initialized." )
	}

}
