package com.mairwunnx.projectessentials.warps.commands

import com.mairwunnx.projectessentials.cooldown.essentials.CommandsAliases
import com.mairwunnx.projectessentials.core.extensions.isPlayerSender
import com.mairwunnx.projectessentials.core.helpers.throwOnlyPlayerCan
import com.mairwunnx.projectessentials.core.helpers.throwPermissionLevel
import com.mairwunnx.projectessentials.warps.EntryPoint
import com.mairwunnx.projectessentials.warps.EntryPoint.Companion.hasPermission
import com.mairwunnx.projectessentials.warps.models.WarpModelUtils
import com.mairwunnx.projectessentials.warps.sendMessage
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.builder.LiteralArgumentBuilder.literal
import com.mojang.brigadier.context.CommandContext
import net.minecraft.command.CommandSource
import net.minecraft.command.Commands
import org.apache.logging.log4j.LogManager

object DelWarpCommand {
    private val aliases = listOf(
        "delwarp", "edelwarp", "removewarp", "eremovewarp"
    )
    private val logger = LogManager.getLogger()

    fun register(dispatcher: CommandDispatcher<CommandSource>) {
        logger.info("Register \"/delwarp\" command")
        applyCommandAliases()

        aliases.forEach { command ->
            dispatcher.register(
                literal<CommandSource>(command).then(
                    Commands.argument(
                        "warp name", StringArgumentType.string()
                    ).executes {
                        return@executes execute(it)
                    }
                )
            )
        }
    }

    private fun applyCommandAliases() {
        if (EntryPoint.cooldownsInstalled) {
            CommandsAliases.aliases["delwarp"] = aliases.toMutableList()
        }
    }

    private fun execute(c: CommandContext<CommandSource>): Int {
        if (c.isPlayerSender()) {
            val player = c.source.asPlayer()
            if (hasPermission(player, "ess.warp.remove")) {
                val warpName = StringArgumentType.getString(c, "warp name")
                WarpModelUtils.warpModel.warps.forEach {
                    if (it.name == warpName && it.owner != player.name.string) {
                        sendMessage(c.source, "remove_not_access")
                        return 0
                    }
                }
                val warp = WarpModelUtils.warpModel.warps.first {
                    it.name == warpName
                }
                WarpModelUtils.warpModel.warps.remove(warp).let { result ->
                    if (result) {
                        sendMessage(c.source, "remove.success", warpName)
                        logger.info("Executed command \"/delwarp\" from ${player.name.string}")
                        return 0
                    } else {
                        sendMessage(c.source, "not_found", warpName)
                    }
                }
            } else {
                sendMessage(c.source, "remove.restricted")
                throwPermissionLevel(player.name.string, "delwarp")
            }
        } else {
            throwOnlyPlayerCan("delwarp")
        }
        return 0
    }
}
