package com.mairwunnx.projectessentials.warps.commands

import com.mairwunnx.projectessentials.core.api.v1.MESSAGE_MODULE_PREFIX
import com.mairwunnx.projectessentials.core.api.v1.commands.CommandAPI
import com.mairwunnx.projectessentials.core.api.v1.commands.CommandBase
import com.mairwunnx.projectessentials.core.api.v1.extensions.getPlayer
import com.mairwunnx.projectessentials.core.api.v1.messaging.MessagingAPI
import com.mairwunnx.projectessentials.core.api.v1.messaging.ServerMessagingAPI
import com.mairwunnx.projectessentials.warps.helpers.validateAndExecute
import com.mairwunnx.projectessentials.warps.teleportToWarp
import com.mairwunnx.projectessentials.warps.warpsConfiguration
import com.mojang.brigadier.context.CommandContext
import net.minecraft.command.CommandSource

object WarpCommand : CommandBase(warpLiteral, false) {
    override val name = "warp"
    override fun process(context: CommandContext<CommandSource>) = 0.also {
        fun out(status: String, vararg args: String) = MessagingAPI.sendMessage(
            context.getPlayer()!!, "${MESSAGE_MODULE_PREFIX}warps.warp.$status", args = *args
        )

        validateAndExecute(context, "ess.warp.teleport", 0) { isServer ->
            if (isServer) ServerMessagingAPI.throwOnlyPlayerCan() else {
                val warp = CommandAPI.getString(context, "warp")
                warpsConfiguration.warps.asSequence().find {
                    it.name == warp
                }?.let {
                    teleportToWarp(context.getPlayer()!!, it).also {
                        out("success", warp).also { super.process(context) }
                    }
                } ?: run { out("not_found", warp) }
            }
        }
    }
}
