package com.viral32111.example;

import net.fabricmc.api.DedicatedServerModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Example implements DedicatedServerModInitializer {

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger( "example" );

	// This runs when the mod is initalised on the server.
	@Override
	public void onInitializeServer() {
		LOGGER.info( "The Example mod has initialized in the server environment." );
	}

}
