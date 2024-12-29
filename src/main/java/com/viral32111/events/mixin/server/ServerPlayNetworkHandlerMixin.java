package com.viral32111.events.mixin.server;

import com.viral32111.events.callback.server.PlayerChatMessageCallback;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayNetworkHandler.class)
public class ServerPlayNetworkHandlerMixin {

	// The player these events are for
	@Shadow
	public ServerPlayerEntity player;

	/**
	 * Runs when a player has sent a chat message.
	 * This will run for signed & unsigned messages. Signed messages have a valid .signature().
	 * @param packet Data about the chat message. Includes content, signature, etc.
	 * @see com.viral32111.events.callback.server.PlayerChatMessageCallback
	 * @since 0.1.0
	 */
	@Inject(method = "onChatMessage", at = @At("TAIL"))
	private void onChatMessage(ChatMessageC2SPacket packet, CallbackInfo callbackInfo) {

		// Invoke all listeners of this mixin's callback.
		// No need to check the listener results as this mixin cannot be cancelled.
		PlayerChatMessageCallback.Companion.getEVENT().invoker().interact(player, packet);

	}

}
