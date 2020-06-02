package com.mairwunnx.projectessentials.warps.commands

import com.mairwunnx.projectessentials.core.api.v1.MESSAGE_MODULE_PREFIX
import com.mairwunnx.projectessentials.core.api.v1.commands.CommandAPI
import com.mairwunnx.projectessentials.core.api.v1.commands.CommandBase
import com.mairwunnx.projectessentials.core.api.v1.extensions.getPlayer
import com.mairwunnx.projectessentials.core.api.v1.messaging.MessagingAPI
import com.mairwunnx.projectessentials.core.api.v1.messaging.ServerMessagingAPI
import com.mairwunnx.projectessentials.core.api.v1.permissions.hasPermission
import com.mairwunnx.projectessentials.warps.helpers.validateAndExecute
import com.mairwunnx.projectessentials.warps.warpsConfiguration
import com.mojang.brigadier.context.CommandContext
import net.minecraft.command.CommandSource

object DelWarpCommand : CommandBase(delWarpLiteral, false) {
    override val name = "del-warp"
    override fun process(context: CommandContext<CommandSource>) = 0.also {
        val name = CommandAPI.getString(context, "warp")
        fun out(status: String, vararg args: String) = MessagingAPI.sendMessage(
            context.getPlayer()!!, "${MESSAGE_MODULE_PREFIX}warps.delwarp.$status", args = *args
        )

        validateAndExecute(context, "ess.warp.remove", 0) { isServer ->
            if (isServer) {
                val result = warpsConfiguration.warps.removeIf { it.name == name }
                if (result) {
                    ServerMessagingAPI.response { "You've removed the warp $name." }
                } else {
                    ServerMessagingAPI.response { "Warp with name $name not exist." }
                }
            } else {
                warpsConfiguration.warps.find {
                    it.name == name
                }?.let { warp ->
                    if (warp.owner != context.getPlayer()!!.name.string) {
                        if (hasPermission(context.getPlayer()!!, "ess.warp.remove.other", 4)) {
                            warpsConfiguration.warps.removeIf { it.name == name }.also {
                                out("success").also { super.process(context) }
                            }
                        } else out("no_access", name)
                    } else {
                        warpsConfiguration.warps.removeIf { it.name == name }.also {
                            out("success").also { super.process(context) }
                        }
                    }
                } ?: run { out("not_exist", name) }
            }
        }
    }
}
