package com.viral32111.events.mixin.server;

import com.mojang.authlib.GameProfile;
import com.viral32111.events.callback.server.PlayerCanJoinCallback;
import com.viral32111.events.callback.server.PlayerJoinCallback;
import com.viral32111.events.callback.server.PlayerLeaveCallback;
import net.minecraft.network.ClientConnection;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.net.SocketAddress;

@Mixin( PlayerManager.class )
public class PlayerManagerMixin {

	/**
	 * Runs when a player attempts to join the server (before ban & whitelist checks).
	 * @param socketAddress The IP address & port number of the connecting player. Should be cast to InetSocketAddress.
	 * @param gameProfile The profile of the connecting player. Includes username, UUID, etc.
	 * @param callbackInfo Return text to prevent the player from joining.
	 * @see com.viral32111.events.callback.server.PlayerCanJoinCallback
	 * @since 0.1.0
	 */
	@Inject( method = "checkCanJoin", at = @At( "HEAD" ), cancellable = true )
	private void checkCanJoin( SocketAddress socketAddress, GameProfile gameProfile, CallbackInfoReturnable<Text> callbackInfo ) {

		// Invoke all listeners of this mixin's callback
		ActionResult actionResult = PlayerCanJoinCallback.Companion.getEVENT().invoker().interact( socketAddress, gameProfile );

		// Prevent the player from joining if any of the listeners return a failure
		if ( actionResult == ActionResult.FAIL ) {
			callbackInfo.setReturnValue( Text.of( "Go away!" ) );
		}

	}

	/**
	 * Runs when a player has joined the server.
	 * @param connection The IP address & port number of the player that joined the server.
	 * @param player The player that joined the server.
	 * @see com.viral32111.events.callback.server.PlayerJoinCallback
	 * @since 0.1.0
	 */
	@Inject( method = "onPlayerConnect", at = @At( "TAIL" )  )
	private void onPlayerConnect( ClientConnection connection, ServerPlayerEntity player, CallbackInfo callbackInfo ) {

		// Invoke all listeners of this mixin's callback
		// No need to check the listener results as this mixin cannot be cancelled
		PlayerJoinCallback.Companion.getEVENT().invoker().interact( connection, player );

	}

	/**
	 * Runs when a player has left the server.
	 * @param player The player that left the server.
	 * @see com.viral32111.events.callback.server.PlayerJoinCallback
	 * @since 0.1.0
	 */
	@Inject( method = "remove", at = @At( "TAIL" ) )
	private void remove( ServerPlayerEntity player, CallbackInfo callbackInfo ) {

		// Invoke all listeners of this mixin's callback
		// No need to check the listener results as this mixin cannot be cancelled
		PlayerLeaveCallback.Companion.getEVENT().invoker().interact( player );

	}

}
