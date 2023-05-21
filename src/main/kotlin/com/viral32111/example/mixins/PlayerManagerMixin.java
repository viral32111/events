package com.viral32111.example.mixins;

import com.mojang.authlib.GameProfile;
import com.viral32111.example.Example;
import net.minecraft.network.ClientConnection;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

@Mixin( PlayerManager.class )
public class PlayerManagerMixin {

	// Runs when a player is attempting to join the server (before checks for ban, whitelist, etc.).
	@Inject( method = "checkCanJoin", at = @At( "HEAD" ) )
	private void checkCanJoin( SocketAddress socketAddress, GameProfile profile, CallbackInfoReturnable<Text> callbackInfo ) {

		// Downcast the source socket address to expose address and port properties.
		InetSocketAddress address = ( InetSocketAddress ) socketAddress;

		// The player's account username and unique identifier.
		String playerName = profile.getName();
		String playerUUID = profile.getId().toString();

		// The IP address of the connecting player, and port number of the connection
		String sourceIP = address.getAddress().getHostAddress();
		int sourcePort = address.getPort();

		// Print a message to the server's console with details of this event.
		Example.INSTANCE.getLOGGER().info( String.format( "Player '%s' (%s) is attempting to join from %s:%d.", playerName, playerUUID, sourceIP, sourcePort ) );

		/* Examples:
		 - Player 'viral32111' (a51dccb5-7ffa-426b-833b-1a9ce3a31446) is attempting to join from 127.0.0.1:56346.
		 */

	}

	// Runs when a player joins the server.
	@Inject( method = "onPlayerConnect", at = @At( "TAIL" )  )
	private void onPlayerConnect( ClientConnection connection, ServerPlayerEntity player, CallbackInfo callbackInfo ) {

		// Downcast the source socket address to expose address and port properties.
		InetSocketAddress address = ( InetSocketAddress ) connection.getAddress();

		// The player's account username and unique identifier.
		String playerName = player.getName().getString();
		String playerUUID = player.getUuidAsString();

		// The IP address of the connecting player, and port number of the connection
		String sourceIP = address.getAddress().getHostAddress();
		int sourcePort = address.getPort();

		// The player's position of where they logged in.
		double playerPosX = player.getPos().getX();
		double playerPosY = player.getPos().getY();
		double playerPosZ = player.getPos().getZ();

		// Print a message to the server's console with details of this event.
		Example.INSTANCE.getLOGGER().info( String.format( "Player '%s' (%s) joined from %s:%d, and spawned at %.0f, %.0f, %.0f.", playerName, playerUUID, sourceIP, sourcePort, playerPosX, playerPosY, playerPosZ ) );

		/* Examples:
		 - Player 'viral32111' (a51dccb5-7ffa-426b-833b-1a9ce3a31446) joined from 127.0.0.1:56346, and spawned at 62, 63, -135.
		 */

	}

	// Runs when a player has left the server.
	@Inject( method = "remove", at = @At( "TAIL" ) )
	private void remove( ServerPlayerEntity player, CallbackInfo callbackInfo ) {

		// The player's account username and unique identifier.
		String playerName = player.getName().getString();
		String playerUUID = player.getUuidAsString();

		// The player's position of where they logged out.
		double playerPosX = player.getPos().getX();
		double playerPosY = player.getPos().getY();
		double playerPosZ = player.getPos().getZ();

		// Print a message to the server's console with details of this event.
		Example.INSTANCE.getLOGGER().info( String.format( "Player '%s' (%s) left, while at %.0f, %.0f, %.0f.", playerName, playerUUID, playerPosX, playerPosY, playerPosZ ) );

		/* Examples:
		 - Player 'viral32111' (a51dccb5-7ffa-426b-833b-1a9ce3a31446) left, while at 74, 63, -136.
		 */

	}

}
