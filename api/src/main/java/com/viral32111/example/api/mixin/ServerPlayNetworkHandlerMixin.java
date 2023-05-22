package com.viral32111.example.api.mixin;

import com.viral32111.example.api.callback.PlayerChatMessageCallback;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin( ServerPlayNetworkHandler.class )
public class ServerPlayNetworkHandlerMixin {

	// The player these events are for
	@Shadow
	public ServerPlayerEntity player;

	// Runs when a player has sent a message in chat.
	// NOTE: This will run for signed & unsigned messages, but signed messages will have .signature()-related properties.
	@Inject( method = "onChatMessage", at = @At( "TAIL" ) )
	private void onChatMessage( ChatMessageC2SPacket packet, CallbackInfo callbackInfo ) {

		// Invoke all listeners of this mixin's callback
		// No need to check the listener results as this mixin cannot be cancelled
		PlayerChatMessageCallback.Companion.getEVENT().invoker().interact( player, packet );

	}

}
