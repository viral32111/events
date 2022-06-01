package com.viral32111.example.mixin;

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

	// Runs when a player attempts to join the server...
	@Inject( method = "checkCanJoin", at = @At( "HEAD" ) ) // Must run before checks for ban, whitelist, etc.
	private void checkCanJoin( SocketAddress socketAddress, GameProfile profile, CallbackInfoReturnable<Text> callbackInfo ) {
		InetSocketAddress address = ( InetSocketAddress ) socketAddress;

		String playerName = profile.getName(); // Account Username
		String playerUUID = profile.getId().toString(); // Account Unique Identifier

		String sourceIP = address.getAddress().getHostAddress(); // Connecting IP Address
		int sourcePort = address.getPort(); // Connecting Port Number

		Example.LOGGER.info( String.format( "Player '%s' (%s) attempted to join from %s:%d.", playerName, playerUUID, sourceIP, sourcePort ) );
	}

	// Runs when a player sucessfully joins the server...
	@Inject( method = "onPlayerConnect", at = @At( "TAIL" )  )
	private void onPlayerConnect( ClientConnection connection, ServerPlayerEntity player, CallbackInfo callbackInfo ) {
		InetSocketAddress address = ( InetSocketAddress ) connection.getAddress();

		String playerName = player.getName().getString(); // Account Username
		String playerUUID = player.getUuidAsString(); // Account Unique Identifier

		String sourceIP = address.getAddress().getHostAddress(); // Connecting IP Address
		int sourcePort = address.getPort(); // Connecting Port Number

		double positionX = player.getPos().x; // Current X Coordinate
		double positionY = player.getPos().y; // Current Y Coordinate
		double positionZ = player.getPos().z; // Current Z Coordinate

		Example.LOGGER.info( String.format( "Player '%s' (%s) joined from %s:%d, and spawned at %.0f, %.0f, %.0f.", playerName, playerUUID, sourceIP, sourcePort, positionX, positionY, positionZ ) );
	}

	// Runs when a player leaves the server...
	@Inject( method = "remove", at = @At( "TAIL" ) )
	private void remove( ServerPlayerEntity player, CallbackInfo callbackInfo ) {
		String playerName = player.getName().getString(); // Account Username
		String playerUUID = player.getUuidAsString(); // Account Unique Identifier

		double positionX = player.getPos().x; // Current X Coordinate
		double positionY = player.getPos().y; // Current Y Coordinate
		double positionZ = player.getPos().z; // Current Z Coordinate

		Example.LOGGER.info( String.format( "Player '%s' (%s) left, while at %.0f, %.0f, %.0f.", playerName, playerUUID, positionX, positionY, positionZ ) );
	}

}
