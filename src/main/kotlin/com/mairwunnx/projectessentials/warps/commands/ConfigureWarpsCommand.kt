package com.mairwunnx.projectessentials.warps.commands

import com.mairwunnx.projectessentials.core.api.v1.MESSAGE_CORE_PREFIX
import com.mairwunnx.projectessentials.core.api.v1.commands.CommandAPI
import com.mairwunnx.projectessentials.core.api.v1.commands.CommandBase
import com.mairwunnx.projectessentials.core.api.v1.extensions.getPlayer
import com.mairwunnx.projectessentials.core.api.v1.extensions.isPlayerSender
import com.mairwunnx.projectessentials.core.api.v1.extensions.playerName
import com.mairwunnx.projectessentials.core.api.v1.messaging.MessagingAPI
import com.mairwunnx.projectessentials.core.api.v1.messaging.ServerMessagingAPI
import com.mairwunnx.projectessentials.core.api.v1.permissions.hasPermission
import com.mairwunnx.projectessentials.warps.warpsSettingsConfiguration
import com.mojang.brigadier.context.CommandContext
import net.minecraft.command.CommandSource
import org.apache.logging.log4j.LogManager

object ConfigureWarpsCommand : CommandBase(configureWarpsLiteral, false) {
    override val name = "configure-warps"

    fun playSoundOnTeleport(context: CommandContext<CommandSource>): Int {
        validate(
            context,
            "ess.configure.warps.play-sound-on-teleport",
            "play-sound-on-teleport"
        ) {
            val value = CommandAPI.getBool(context, "value")
            val oldValue = warpsSettingsConfiguration.playSoundOnTeleport
            warpsSettingsConfiguration.playSoundOnTeleport = value
            changed(
                context, "play-sound-on-teleport", oldValue.toString(), value.toString()
            ).also { super.process(context) }
        }
        return 0
    }

    fun showEffectOnTeleport(context: CommandContext<CommandSource>): Int {
        validate(
            context,
            "ess.configure.warps.show-effect-on-teleport",
            "show-effect-on-teleport"
        ) {
            val value = CommandAPI.getBool(context, "value")
            val oldValue = warpsSettingsConfiguration.showEffectOnTeleport
            warpsSettingsConfiguration.showEffectOnTeleport = value
            changed(
                context, "show-effect-on-teleport", oldValue.toString(), value.toString()
            ).also { super.process(context) }
        }
        return 0
    }

    fun addResistanceEffect(context: CommandContext<CommandSource>): Int {
        validate(
            context,
            "ess.configure.warps.add-resistance-effect",
            "add-resistance-effect"
        ) {
            val value = CommandAPI.getBool(context, "value")
            val oldValue = warpsSettingsConfiguration.addResistanceEffect
            warpsSettingsConfiguration.addResistanceEffect = value
            changed(
                context, "add-resistance-effect", oldValue.toString(), value.toString()
            ).also { super.process(context) }
        }
        return 0
    }

    fun resistanceEffectDuration(context: CommandContext<CommandSource>): Int {
        validate(
            context,
            "ess.configure.warps.resistance-effect-duration",
            "resistance-effect-duration"
        ) {
            val value = CommandAPI.getInt(context, "value")
            val oldValue = warpsSettingsConfiguration.resistanceEffectDuration
            warpsSettingsConfiguration.resistanceEffectDuration = value
            changed(
                context, "resistance-effect-duration", oldValue.toString(), value.toString()
            ).also { super.process(context) }
        }
        return 0
    }

    private fun validate(
        context: CommandContext<CommandSource>,
        node: String,
        setting: String,
        action: (isServer: Boolean) -> Unit
    ) = context.getPlayer()?.let {
        if (hasPermission(it, node, 4)) {
            action(false)
        } else {
            MessagingAPI.sendMessage(
                context.getPlayer()!!,
                "$MESSAGE_CORE_PREFIX.configure.restricted",
                args = *arrayOf(setting)
            )
        }
    } ?: run { action(true) }

    private fun changed(
        context: CommandContext<CommandSource>,
        setting: String,
        oldValue: String,
        value: String
    ) = if (context.isPlayerSender()) {
        LogManager.getLogger().info(
            "Setting name `$setting` value changed by ${context.playerName()} from `$oldValue` to $value"
        )
        MessagingAPI.sendMessage(
            context.getPlayer()!!,
            "$MESSAGE_CORE_PREFIX.configure.successfully",
            args = *arrayOf(setting, oldValue, value)
        )
    } else {
        ServerMessagingAPI.response {
            "Setting name `$setting` value changed from `$oldValue` to $value"
        }
    }
}
