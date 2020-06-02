package com.mairwunnx.projectessentials.warps.commands

import com.mairwunnx.projectessentials.core.api.v1.MESSAGE_MODULE_PREFIX
import com.mairwunnx.projectessentials.core.api.v1.commands.CommandAPI
import com.mairwunnx.projectessentials.core.api.v1.commands.CommandBase
import com.mairwunnx.projectessentials.core.api.v1.extensions.currentDimensionId
import com.mairwunnx.projectessentials.core.api.v1.extensions.getPlayer
import com.mairwunnx.projectessentials.core.api.v1.messaging.MessagingAPI
import com.mairwunnx.projectessentials.core.api.v1.messaging.ServerMessagingAPI
import com.mairwunnx.projectessentials.warps.configurations.WarpsConfigurationModel
import com.mairwunnx.projectessentials.warps.helpers.validateAndExecute
import com.mairwunnx.projectessentials.warps.warpsConfiguration
import com.mojang.brigadier.context.CommandContext
import net.minecraft.command.CommandSource

object SetWarpCommand : CommandBase(setWarpLiteral, false) {
    override val name = "set-warp"
    override fun process(context: CommandContext<CommandSource>) = 0.also {
        fun out(status: String, vararg args: String) = MessagingAPI.sendMessage(
            context.getPlayer()!!, "${MESSAGE_MODULE_PREFIX}warps.setwarp.$status", args = *args
        )

        validateAndExecute(context, "ess.warp.set", 0) { isServer ->
            if (isServer) {
                ServerMessagingAPI.throwOnlyPlayerCan()
            } else {
                val name = CommandAPI.getString(context, "warp")
                warpsConfiguration.warps.asSequence().find {
                    it.name == name
                }?.let { out("exist", name) } ?: run {
                    with(context.getPlayer()!!) {
                        warpsConfiguration.warps.add(
                            WarpsConfigurationModel.Warp(
                                name, this.name.string,
                                currentDimensionId, position.x, position.y, position.z,
                                rotationYaw, rotationPitch
                            )
                        ).also { out("success").also { super.process(context) } }
                    }
                }
            }
        }
    }
}
