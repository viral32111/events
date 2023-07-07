package com.viral32111.events

import net.minecraft.advancement.Advancement
import net.minecraft.advancement.AdvancementFrame

/**
 * Gets the appropriate chat text for this advancement type (task, challenge, goal).
 * Example: 'has made the advancement', 'completed the challenge'.
 * @return The appropriate text for this advancement.
 * @since 0.3.6
 */
fun AdvancementFrame?.getText(): String? =
	when ( this ) {
		AdvancementFrame.TASK -> "has made the advancement"
		AdvancementFrame.CHALLENGE -> "completed the challenge"
		AdvancementFrame.GOAL -> "reached the goal"
		else -> null
	}

/**
 * Gets the friendly type for this advancement.
 * Example: 'Goal', 'Challenge'.
 * @return The appropriate text for this advancement.
 * @since 0.3.6
 */
fun AdvancementFrame?.getType(): String? =
	when ( this ) {
		AdvancementFrame.TASK -> "Advancement"
		AdvancementFrame.CHALLENGE -> "Challenge"
		AdvancementFrame.GOAL -> "Goal"
		else -> null
	}

/**
 * Gets the appropriate chat color for this advancement (purple for challenge, green otherwise).
 * @return The appropriate color for this advancement.
 * @since 0.3.6
 */
fun AdvancementFrame?.getColor(): Int =
	when ( this ) {
		AdvancementFrame.CHALLENGE -> 0xA700A7 // Challenge Purple
		else -> 0x54FB54 // Advancement Green
	}

/** @see getType */
fun Advancement.getText(): String? = display?.frame?.getText()

/** @see getType */
fun Advancement.getType(): String? = display?.frame?.getType()

/** @see getColor */
fun Advancement.getColor(): Int? = display?.frame?.getColor()
