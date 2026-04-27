package com.viral32111.events

import net.minecraft.advancements.Advancement
import net.minecraft.advancements.AdvancementType
import kotlin.jvm.optionals.getOrNull

/**
 * Gets the appropriate chat text for this advancement type (task, challenge, goal).
 * Example: 'has made the advancement', 'completed the challenge'.
 * @return The appropriate text for this advancement.
 * @since 0.3.6
 */
fun AdvancementType?.getText(): String? =
	when (this) {
		AdvancementType.TASK -> "has made the advancement"
		AdvancementType.CHALLENGE -> "completed the challenge"
		AdvancementType.GOAL -> "reached the goal"
		else -> null
	}

/**
 * Gets the friendly type for this advancement.
 * Example: 'Goal', 'Challenge'.
 * @return The appropriate text for this advancement.
 * @since 0.3.6
 */
fun AdvancementType?.getType(): String? =
	when (this) {
		AdvancementType.TASK -> "Advancement"
		AdvancementType.CHALLENGE -> "Challenge"
		AdvancementType.GOAL -> "Goal"
		else -> null
	}

/**
 * Gets the appropriate chat color for this advancement (purple for challenge, green otherwise).
 * @return The appropriate color for this advancement.
 * @since 0.3.6
 */
fun AdvancementType?.getColor(): Int =
	when (this) {
		AdvancementType.CHALLENGE -> 0xA700A7 // Challenge Purple
		else -> 0x54FB54 // Advancement Green
	}

/**
 * Gets the name of this advancement.
 * @since 0.5.0
 */
fun Advancement.getName(): String? = display.getOrNull()?.title?.string

/**
 * Gets the appropriate chat text for this advancement type (task, challenge, goal).
 * Example: 'has made the advancement', 'completed the challenge'.
 * @return The appropriate text for this advancement.
 * @see AdvancementType.getText
 * @since 0.3.6
 */
fun Advancement.getText(): String? = display.getOrNull()?.type?.getText()

/**
 * Gets the friendly type for this advancement.
 * Example: 'Goal', 'Challenge'.
 * @return The appropriate text for this advancement.
 * @see AdvancementType.getType
 * @since 0.3.6
 */
fun Advancement.getType(): String? = display.getOrNull()?.type?.getType()

/**
 * Gets the appropriate chat color for this advancement (purple for challenge, green otherwise).
 * @return The appropriate color for this advancement.
 * @see AdvancementType.getColor
 * @since 0.3.6
 */
fun Advancement.getColor(): Int? = display.getOrNull()?.type?.getColor()
