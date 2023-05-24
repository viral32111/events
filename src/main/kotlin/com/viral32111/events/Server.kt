package com.viral32111.events

import com.viral32111.events.callback.server.*
import com.viral32111.events.listener.server.*
import net.fabricmc.api.DedicatedServerModInitializer
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@Suppress( "UNUSED" )
class Server: DedicatedServerModInitializer {

	// Runs when the mod has initialized on the server...
	override fun onInitializeServer() {
		Main.LOGGER.info( "Events initialized in the server-side environment." )

		// Register all server callback listeners
		registerCallbackListeners()
		registerServerLifecycleCallbackListeners()
	}

	//
	/**
	 * Registers example listeners for our own callbacks.
	 * @see com.viral32111.events.callback
	 * @since 0.3.0
	 */
	private fun registerCallbackListeners() {
		PlayerJoinCallback.EVENT.register( ::playerJoinCallbackListener )
		PlayerCanJoinCallback.EVENT.register( ::playerCanJoinCallbackListener )
		PlayerLeaveCallback.EVENT.register( ::playerLeaveCallbackListener )
		PlayerDeathCallback.EVENT.register( ::playerDeathCallbackListener )
		PlayerChatMessageCallback.EVENT.register( ::playerChatMessageCallbackListener )
	}

	/**
	 * Registers example listeners for server lifecycle callbacks provided by the Fabric API.
	 * @see net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
	 * @since 0.3.1
	 */
	private fun registerServerLifecycleCallbackListeners() {

		// https://maven.fabricmc.net/docs/fabric-api-0.81.3+1.20/net/fabricmc/fabric/api/event/lifecycle/v1/ServerLifecycleEvents.ServerStarting.html
		// NOTE: Server IP address & port number are null when this is called.
		ServerLifecycleEvents.SERVER_STARTING.register { server ->
			Main.LOGGER.info( "Server '${ server.serverIp }:${ server.serverPort }' (${ server.serverModName }) starting at ${ getDateTime() }..." )
		}

		// https://maven.fabricmc.net/docs/fabric-api-0.81.3+1.20/net/fabricmc/fabric/api/event/lifecycle/v1/ServerLifecycleEvents.ServerStarted.html
		ServerLifecycleEvents.SERVER_STARTED.register { server ->
			Main.LOGGER.info( "Server '${ server.serverIp }:${ server.serverPort }' (${ server.serverModName }) started at ${ getDateTime() }." )
		}

		// https://maven.fabricmc.net/docs/fabric-api-0.81.3+1.20/net/fabricmc/fabric/api/event/lifecycle/v1/ServerLifecycleEvents.ServerStopping.html
		ServerLifecycleEvents.SERVER_STOPPING.register { server ->
			Main.LOGGER.info( "Server '${ server.serverIp }:${ server.serverPort }' (${ server.serverModName }) stopping at ${ getDateTime() }..." )
		}

		// https://maven.fabricmc.net/docs/fabric-api-0.81.3+1.20/net/fabricmc/fabric/api/event/lifecycle/v1/ServerLifecycleEvents.ServerStopped.html
		ServerLifecycleEvents.SERVER_STOPPED.register { server ->
			Main.LOGGER.info( "Server '${ server.serverIp }:${ server.serverPort }' (${ server.serverModName }) stopped at ${ getDateTime() }." )
		}

	}

	/**
	 * Gets the current localised date & time.
	 * @param format The [format](https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html) to return the date & time in.
	 * @return The formatted date & time.
	 * @since 0.3.1
	 */
	private fun getDateTime( format: String = "dd/MM/yyyy HH:mm:ss Z" ) = ZonedDateTime.now().format( DateTimeFormatter.ofPattern( format ) )

}
